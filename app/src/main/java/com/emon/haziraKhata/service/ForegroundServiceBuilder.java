package com.emon.haziraKhata.service;


import android.app.NotificationManager;
import android.widget.RemoteViews;

/**
 * Created by Gk Emon on 5/31/2020.
 */
public class ForegroundServiceBuilder {
    public String notificationContent, notificationTitle, channelName, channelID;
    public RemoteViews remoteViews;
    public int requestCode, importance = NotificationManager.IMPORTANCE_DEFAULT, notificationID;
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

    public RemoteViews getRemoteViews() {
        return remoteViews;
    }

    public int getNotificationID() {
        return notificationID;
    }

    public interface FinalStep {
        ForegroundServiceBuilder build();

        FinalStep notificationContent(String notificationContent);

        FinalStep notificationTitle(String notificationTitle);

        FinalStep channelName(String channelName);

        FinalStep channelID(String channelID);

        FinalStep remoteViews(RemoteViews remoteViews);

        FinalStep requestCode(int requestCode);

        FinalStep importance(int importance);

        FinalStep notificationID(int notificationID);

        FinalStep autoCancel(boolean autoCancel);
    }


    private static final class Builder implements FinalStep {
        private String notificationContent;
        private String notificationTitle;
        private String channelName;
        private String channelID;
        private RemoteViews remoteViews;
        private int requestCode;
        private int importance;
        private int notificationID;
        private boolean autoCancel;

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

        public FinalStep remoteViews(RemoteViews remoteViews) {
            this.remoteViews = remoteViews;
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

        public FinalStep notificationID(int notificationID) {
            this.notificationID = notificationID;
            return this;
        }

        public FinalStep autoCancel(boolean autoCancel) {
            this.autoCancel = autoCancel;
            return this;
        }

        public ForegroundServiceBuilder build() {
            ForegroundServiceBuilder theObject = new ForegroundServiceBuilder();
            theObject.notificationContent = notificationContent;
            theObject.notificationTitle = notificationTitle;
            theObject.channelName = channelName;
            theObject.channelID = channelID;
            theObject.remoteViews = remoteViews;
            theObject.requestCode = requestCode;
            theObject.importance = importance;
            theObject.notificationID = notificationID;
            theObject.autoCancel = autoCancel;
            return theObject;
        }
    }
}
