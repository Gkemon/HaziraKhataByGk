package com.Teachers.HaziraKhataByGk.Listener;

public abstract class CommonCallback <T> implements CallBackContract<T> {
    @Override
    public void onFailure(String r) {

    }

    @Override
    public void onSuccess() {

    }

    @Override
    public void onSuccess(T response) {

    }
}
interface CallBackContract<T>{
    void onSuccess(T response);
    void onSuccess();
    void onFailure(String r);
}
