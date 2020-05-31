package com.Teachers.HaziraKhataByGk.service;

import android.content.Intent;

/**
 * Created by Gk Emon on 5/31/2020.
 */
public interface BaseForeGroundServiceNavigator {
    void setUpForegroundBuilder();
    void notifyNotificationContent(String content);
}
