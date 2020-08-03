package com.Teachers.HaziraKhataByGk.note.dataSource;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.Teachers.HaziraKhataByGk.HelperClassess.UtilsCommon;
import com.Teachers.HaziraKhataByGk.Listener.CommonCallback;
import com.Teachers.HaziraKhataByGk.Model.Notes;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gk Emon on 7/21/2020.
 */
public class RemoteDataSourceImp implements RemoteNoteSource {


    @Override
    public MutableLiveData<List<Notes>> getNotes(DatabaseReference databaseReference) {
        MutableLiveData<List<Notes>> mutableNotes=new MutableLiveData<>();
        List<Notes> notes = new ArrayList<>();
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                try {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        notes.add(snapshot.getValue(Notes.class));
                    }
                    mutableNotes.setValue(notes);
                }catch (Exception e){
                    UtilsCommon.handleError(e);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                UtilsCommon.handleError(databaseError.toException());
            }
        });

        return mutableNotes;
    }

    @Override
    public Notes getNotesByUID(DatabaseReference databaseReference) {
        return null;
    }

    @Override
    public void addNote(DatabaseReference databaseReference,Notes notes, CommonCallback commonCallback) {

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                commonCallback.onSuccess();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                commonCallback.onFailure(databaseError.getMessage());
            }
        });

        databaseReference.child(notes.getUid()).setValue(notes);
    }


}
