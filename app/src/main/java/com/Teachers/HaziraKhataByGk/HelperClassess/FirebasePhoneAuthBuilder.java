package com.Teachers.HaziraKhataByGk.HelperClassess;

import android.app.Activity;
import android.os.CountDownTimer;

import com.Teachers.HaziraKhataByGk.Listener.CommonCallback;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class FirebasePhoneAuthBuilder {
    private static FirebasePhoneAuthBuilder firebasePhoneAuthBuilder;
    public boolean isNewUser;
    public CountDownTimer cTimer;
    String phoneNumber;
    String pin;
    CommonCallback<Long> timerCallBack;
    CommonCallback<Boolean> verificationCallBack;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    private Activity activity;
    private String mVerificationId;
    private PhoneAuthProvider.ForceResendingToken mResendToken;
    private CommonCallback<Boolean> newUserCallBack;
    private CommonCallback<Boolean> codeGettingCallBack;

    public static FirebasePhoneAuthBuilder getInstance() {

        if (firebasePhoneAuthBuilder == null) {
            return firebasePhoneAuthBuilder = new FirebasePhoneAuthBuilder();
        } else return firebasePhoneAuthBuilder;
    }

    public void startCountDownTimer() {
        stopTimer();


        cTimer = new CountDownTimer(120000, 1000) {

            public void onTick(long millisUntilFinished) {
                timerCallBack.onWait((int) millisUntilFinished / 1000);
            }

            public void onFinish() {
                timerCallBack.onSuccess();
                timerCallBack = null;

                DialogUtils.showInfoAlertDialog("পিন নাম্বার আসেনি?", "যদি পিন নাম্বার না এসে " +
                        "থাকে তাহলে কি আবার পিন নাম্বার পাঠাবো আমরা?", activity, new CommonCallback() {
                    @Override
                    public void onSuccess() {
                        resendVerificationCode(phoneNumber, mResendToken);
                    }
                });


            }
        };
        cTimer.start();


    }

    public void build() {


        if (!ConnectivityChecker.isConnected(activity)) {
            verificationCallBack.onFailure("No active internet connection available.");
        } else {

            if (!validatePhoneNumber()) {
                verificationCallBack.onFailure("Invalid phone number.");
                return;
            }

            if (UtilsCommon.isValideString(pin)) {
                verifyPhoneNumberWithCode(mVerificationId, pin);
                return;
            }


            if (timerCallBack != null) {
                startCountDownTimer();
            }


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
                    codeGettingCallBack.onSuccess();
                    mVerificationId = verificationId;
                    mResendToken = token;
                }
            };

            startPhoneNumberVerification(phoneNumber);
        }


    }

    void stopTimer() {
        if (cTimer != null) {
            cTimer.cancel();
            timerCallBack.onWait(120);
        }
    }

    public FirebasePhoneAuthBuilder setPhoneNumber(String phoneNumber) {

        if (!phoneNumber.contains("+880"))
            phoneNumber = "+880" + phoneNumber;

        this.phoneNumber = phoneNumber;
        return this;
    }

    public FirebasePhoneAuthBuilder setPin(String pin) {
        this.pin = pin;
        return this;
    }

    public FirebasePhoneAuthBuilder setTimerCallBack(CommonCallback<Long> timerCallBack) {
        this.timerCallBack = timerCallBack;
        return this;
    }

    public FirebasePhoneAuthBuilder setVerificationCallBack(CommonCallback<Boolean> verificationCallBack) {
        this.verificationCallBack = verificationCallBack;
        return this;
    }

    public FirebasePhoneAuthBuilder setCodeGettingCallBack(CommonCallback<Boolean> codeGettingCallBack) {
        this.codeGettingCallBack = codeGettingCallBack;
        return this;
    }

    public FirebasePhoneAuthBuilder setActivity(Activity activity) {
        this.activity = activity;
        return this;
    }

    public FirebasePhoneAuthBuilder setNewUserCallBack(CommonCallback<Boolean> newUserCallBack) {
        this.newUserCallBack = newUserCallBack;
        return this;
    }

    private boolean validatePhoneNumber() {

        if (UtilsCommon.isValidPhoneNumber(phoneNumber)) {
            verificationCallBack.onFailure("Enter phone number.");
            return false;
        }
        return true;
    }

    private void signInWithPhoneAuthCredential(final PhoneAuthCredential credential) {


        FirebaseAuth.getInstance().signInWithCredential(credential).addOnCompleteListener(activity, task -> {


            if (task.isSuccessful()) {
                UtilsCommon.debugLog("Phone auth success");

                if (newUserCallBack != null)
                    if (Objects.requireNonNull(task.getResult()).getAdditionalUserInfo().isNewUser()) {
                        newUserCallBack.onSuccess(true);
                    } else {
                        newUserCallBack.onSuccess(false);
                    }

                verificationCallBack.onSuccess();

            } else {
                UtilsCommon.debugLog("Phone auth failed.Error : " + Objects.requireNonNull(task.getException()).getLocalizedMessage());
                if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                    verificationCallBack.onFailure("Invalid code.");
                }
            }
        });
    }


    private void startPhoneNumberVerification(String phoneNumber) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNumber,        // Phone number to verify
                120,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                activity,               // Activity (for callback binding)
                mCallbacks);        // OnVerificationStateChangedCallbacks
    }

    private void verifyPhoneNumberWithCode(String verificationId, String code) {

        if (ConnectivityChecker.isConnected(activity) && UtilsCommon.isValideString(verificationId)) {
            PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);
            signInWithPhoneAuthCredential(credential);
        }

    }

    private void resendVerificationCode(String phoneNumber,
                                        PhoneAuthProvider.ForceResendingToken token) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNumber,        // Phone number to verify
                120,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                activity,               // Activity (for callback binding)
                mCallbacks,         // OnVerificationStateChangedCallbacks
                token);             // ForceResendingToken from callbacks
    }


}
