package com.Teachers.HaziraKhataByGk.service;


import android.app.NotificationManager;

/**
 * Created by Gk Emon on 5/31/2020.
 */
public class ForegroundServiceBuilder {
    public String notificationContent, notificationTitle, channelName, channelID;
    public int requestCode, importance = NotificationManager.IMPORTANCE_DEFAULT,notificationID;
    public boolean autoCancel;

    private ForegroundServiceBuilder() {
    }

    public static FinalStep newInstance() {
        return new Builder();
    }

    public String getNotificationContent() {
        return notificationContent;
    }

    public String getNotificationTitle() {
        return notificationTitle;
    }

    public String getChannelName() {
        return channelName;
    }

    public String getChannelID() {
        return channelID;
    }

    public int getRequestCode() {
        return requestCode;
    }

    public int getImportance() {
        return importance;
    }

    public boolean getAutoCancel() {
        return autoCancel;
    }

    public interface FinalStep {
        ForegroundServiceBuilder build();

        FinalStep notificationContent(String notificationContent);

        FinalStep notificationTitle(String notificationTitle);

        FinalStep channelName(String channelName);

        FinalStep channelID(String channelID);

        FinalStep requestCode(int requestCode);

        FinalStep importance(int importance);

        FinalStep autoCancel(boolean autoCancel);
        FinalStep notificationID(int notificationID);
    }


    private static final class Builder implements FinalStep {
        private String notificationContent;
        private String notificationTitle;
        private String channelName;
        private String channelID;
        private int requestCode;
        private int importance;
        private boolean autoCancel;
        private int notificationID;

        public FinalStep notificationContent(String notificationContent) {
            this.notificationContent = notificationContent;
            return this;
        }

        public FinalStep notificationTitle(String notificationTitle) {
            this.notificationTitle = notificationTitle;
            return this;
        }

        public FinalStep channelName(String channelName) {
            this.channelName = channelName;
            return this;
        }

        public FinalStep channelID(String channelID) {
            this.channelID = channelID;
            return this;
        }

        public FinalStep requestCode(int requestCode) {
            this.requestCode = requestCode;
            return this;
        }

        public FinalStep importance(int importance) {
            this.importance = importance;
            return this;
        }

        public FinalStep autoCancel(boolean autoCancel) {
            this.autoCancel = autoCancel;
            return this;
        }
        public FinalStep notificationID(int notificationID){
            this.notificationID = notificationID;
            return this;
        }

        public ForegroundServiceBuilder build() {
            ForegroundServiceBuilder theObject = new ForegroundServiceBuilder();
            theObject.notificationContent = notificationContent;
            theObject.notificationTitle = notificationTitle;
            theObject.channelName = channelName;
            theObject.channelID = channelID;
            theObject.requestCode = requestCode;
            theObject.importance = importance;
            theObject.autoCancel = autoCancel;
            theObject.notificationID=notificationID;
            return theObject;
        }
    }
}
