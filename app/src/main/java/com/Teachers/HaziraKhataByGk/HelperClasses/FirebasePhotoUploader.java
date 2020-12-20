package com.Teachers.HaziraKhataByGk.HelperClasses;

import android.app.Activity;
import android.app.ProgressDialog;
import android.net.Uri;

import com.Teachers.HaziraKhataByGk.Listener.CommonCallback;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;


public class FirebasePhotoUploader {
    private static FirebasePhotoUploader firebasePhotoUploader;
    private String fileName;
    private String serverStorageFolderName;
    private CommonCallback<Uri> commonCallback;
    private Activity activity;
    private Uri uri;
    private DatabaseReference targetDatabaseRef;

    public static FirebasePhotoUploader getBuilder() {
        if (firebasePhotoUploader == null) {
            firebasePhotoUploader = new FirebasePhotoUploader();
            return firebasePhotoUploader;
        } else return firebasePhotoUploader;
    }

    public FirebasePhotoUploader setTargetDatabaseRef(DatabaseReference targetDatabaseRef) {
        this.targetDatabaseRef = targetDatabaseRef;
        return this;
    }

    public FirebasePhotoUploader setUri(Uri uri) {
        this.uri = uri;
        return this;
    }

    public FirebasePhotoUploader setActivity(Activity activity) {
        this.activity = activity;
        return this;
    }

    public FirebasePhotoUploader setFileName(String fileName) {
        this.fileName = fileName;
        return this;
    }


    public FirebasePhotoUploader setServerStorageFolderName(String url) {
        this.serverStorageFolderName = url;
        return this;
    }

    public FirebasePhotoUploader setCallBack(CommonCallback<Uri> commonCallback) {
        this.commonCallback = commonCallback;
        return this;
    }

    public void build() {
        uploadImage(uri);
    }

    void uploadImage(Uri file) {


        if (file != null) {

            try {
                final ProgressDialog progressDialog = new ProgressDialog(activity);
                progressDialog.setTitle("Uploading...");
                progressDialog.setCancelable(false);
                progressDialog.show();


                FirebaseStorage storage = FirebaseStorage.getInstance();

                final StorageReference riversRef =
                        storage.getReference().child(serverStorageFolderName + "/" + fileName);

                //For replacing
                riversRef.delete();

                riversRef.putFile(file).addOnProgressListener(taskSnapshot -> {

                    double progress = ((taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount()) * 100);
                    commonCallback.onWait((int) progress);
                    progressDialog.setMessage("Uploaded " + (int) progress + "%");

                }).addOnSuccessListener(taskSnapshot -> {

                    riversRef.getDownloadUrl().addOnSuccessListener(uri -> {
                        targetDatabaseRef.setValue(uri.toString())
                                .addOnSuccessListener(aVoid -> commonCallback.onSuccess(uri))
                                .addOnFailureListener(e -> commonCallback.onFailure(e.getLocalizedMessage()));

                    }).addOnFailureListener(e -> {
                        commonCallback.onFailure("Not stored in server for network problem");
                        progressDialog.dismiss();
                    });
                }).addOnFailureListener(e -> {
                    commonCallback.onFailure("Upload failed: " + e.getMessage());
                    progressDialog.dismiss();

                })
                        .addOnPausedListener(taskSnapshot ->
                                commonCallback.onWait()
                        );
            } catch (Exception e) {

            }

        }
    }
}
