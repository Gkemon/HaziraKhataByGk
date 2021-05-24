package com.emon.haziraKhata.Listener;

interface CallBackContract<T> {
    void onSuccess(T response);

    void onSuccess();

    void onWait();

    void onWait(int sec);

    void onFailure(String r);
    void onFailure(Throwable throwable);
}

public abstract class CommonCallback<T> implements CallBackContract<T> {
    @Override
    public void onFailure(Throwable throwable) {

    }

    @Override
    public void onFailure(String r) {

    }

    @Override
    public void onSuccess() {

    }

    @Override
    public void onWait() {

    }

    @Override
    public void onWait(int sec) {

    }


    @Override
    public void onSuccess(T response) {

    }
}
