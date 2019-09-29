package com.Teachers.HaziraKhataByGk.HelperClassess;

import android.app.Activity;
import android.os.CountDownTimer;
import android.text.TextUtils;

import com.Teachers.HaziraKhataByGk.Listener.CommonCallback;
import com.Teachers.HaziraKhataByGk.Routine.RoutineItmBuilder;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.Objects;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;

public class FirebasePhoneAuthBuilder {
    String phoneNumber;
    CommonCallback<Long> timerCallBack;
    CommonCallback<Boolean> verificationCallBack;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    private Activity activity;
    private String mVerificationId;
    public boolean isNewUser;
    private PhoneAuthProvider.ForceResendingToken mResendToken;
    private CommonCallback<Boolean> newUserCallBack;
    public CountDownTimer cTimer = null;


    public void startCountDownTimer(){
        stopTimer();

        cTimer = new CountDownTimer(60000, 1000) {

            public void onTick(long millisUntilFinished) {
                timerCallBack.onWait((int)millisUntilFinished/1000);
            }

            public void onFinish() {
                timerCallBack.onSuccess();
            }
        };

        cTimer.start();
    }

    public void build(){


        if(!ConnectivityChecker.isConnected(activity))
        {
            verificationCallBack.onFailure("No active internet connection available.");
        }
        else {

            if(!validatePhoneNumber()) return;

            startCountDownTimer();

            mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                @Override
                public void onVerificationCompleted(PhoneAuthCredential credential) {

                    UtilsCommon.debugLog("Credential: " + credential.toString());
                    signInWithPhoneAuthCredential(credential);
                    stopTimer();

                }

                @Override
                public void onVerificationFailed(FirebaseException e) {


                    UtilsCommon.debugLog("Phone auth error : " + e.getLocalizedMessage());
                    stopTimer();

                    if (e instanceof FirebaseAuthInvalidCredentialsException) {
                        verificationCallBack.onFailure("Invalid phone number.");
                    } else if (e instanceof FirebaseTooManyRequestsException) {
                        verificationCallBack.onFailure("Quota exceeded.");
                    }
                }

                @Override
                public void onCodeSent(String verificationId,
                                       PhoneAuthProvider.ForceResendingToken token) {
                    UtilsCommon.debugLog("onCodeSent:" + verificationId);
                    mVerificationId = verificationId;
                    mResendToken = token;
                }
            };

            startPhoneNumberVerification(phoneNumber);
        }


    }

    void stopTimer() {
        if(cTimer!=null)
            cTimer.cancel();
    }

    public FirebasePhoneAuthBuilder setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public FirebasePhoneAuthBuilder setTimerCallBack(CommonCallback<Long> timerCallBack){
        this.timerCallBack =timerCallBack;
        return this;
    }
    public FirebasePhoneAuthBuilder setVerificationCallBack(CommonCallback<Boolean> verificationCallBack){
        this.verificationCallBack=verificationCallBack;
        return this;
    }
    public FirebasePhoneAuthBuilder setActivity(Activity activity){
        this.activity=activity;
        return this;
    }
    public FirebasePhoneAuthBuilder setNewUserCallBack(CommonCallback<Boolean> newUserCallBack){
        this.newUserCallBack=newUserCallBack;
        return this;
    }
    public static FirebasePhoneAuthBuilder getInstance(){
        return  new FirebasePhoneAuthBuilder();
    }


    private boolean validatePhoneNumber() {

        if (TextUtils.isEmpty(phoneNumber)) {
            verificationCallBack.onFailure("Enter phone number.");
            return false;
        }
        return true;
    }

    private void signInWithPhoneAuthCredential(final PhoneAuthCredential credential) {


        FirebaseAuth.getInstance().signInWithCredential(credential).addOnCompleteListener(activity, task -> {


                    if (task.isSuccessful()) {
                        UtilsCommon.debugLog("Phone auth success");

                        if(Objects.requireNonNull(task.getResult()).getAdditionalUserInfo().isNewUser()){
                            newUserCallBack.onSuccess(true);
                        }else {
                            newUserCallBack.onSuccess(false);
                        }

                        verificationCallBack.onSuccess();

                    } else {
                        UtilsCommon.debugLog("Phone auth failed.Error : "+ Objects.requireNonNull(task.getException()).getLocalizedMessage());
                        if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                            verificationCallBack.onFailure("Invalid code.");
                        }
                    }
                });
    }


    private void startPhoneNumberVerification(String phoneNumber) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNumber,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                activity,               // Activity (for callback binding)
                mCallbacks);        // OnVerificationStateChangedCallbacks
    }

    private void verifyPhoneNumberWithCode(String verificationId, String code) {

        if(ConnectivityChecker.isConnected(activity)&& verificationId != null){
            PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);
            signInWithPhoneAuthCredential(credential);
        }

    }

    private void resendVerificationCode(String phoneNumber,
                                        PhoneAuthProvider.ForceResendingToken token) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNumber,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                activity,               // Activity (for callback binding)
                mCallbacks,         // OnVerificationStateChangedCallbacks
                token);             // ForceResendingToken from callbacks
    }


}
