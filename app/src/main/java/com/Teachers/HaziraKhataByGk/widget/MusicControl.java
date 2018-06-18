package com.Teachers.HaziraKhataByGk.widget;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;

/**
 * Created by uy on 4/2/2018.
 */

public class MusicControl {

    private static MusicControl sInstance;
    private Context mContext;
    private MediaPlayer mp;
    public Uri defaultRingone = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
    long [] vibration ={1000,2000,3000};
    public MusicControl(Context context) {
        mContext = context;
    }

    public static MusicControl getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new MusicControl(context);
        }
        return sInstance;
    }

    public void playMusic() {
        mp = new MediaPlayer();
        try{
            mp.setDataSource(mContext, defaultRingone);
            mp.setAudioStreamType(AudioManager.STREAM_ALARM);
            mp.prepare();
            mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    mp.release();
                }
            });
            mp.start();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public void stopMusic() {
        try {
            mp.reset();
            mp.prepare();
            mp.stop();
            mp.release();
            mp=null;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

}
