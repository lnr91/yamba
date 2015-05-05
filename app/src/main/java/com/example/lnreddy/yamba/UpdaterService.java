package com.example.lnreddy.yamba;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import java.util.List;

import winterwell.jtwitter.Twitter;
import winterwell.jtwitter.TwitterException;

/**
 * Created by lnreddy on 23/04/15.
 */
public class UpdaterService extends Service {

    static final String TAG = "UpdaterService";
    static final String DELAY = "10";
    Boolean running;
    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "OnCreate");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "OnStartCommand");
        running =true;

        new Thread() {
            public void run() {
                try {
                    while (running) {
                        List<Twitter.Status> timeline = ((YambaApp)getApplication()).getTwitter().getPublicTimeline();

                        for (Twitter.Status status : timeline) {
                            Log.d(TAG, String.format("%s: %s", status.user.name, status.text));
                        }

                        int delay = Integer.parseInt(((YambaApp) getApplication()).prefs.getString("delay",DELAY));
                        Thread.sleep(delay);
                    }

                } catch (TwitterException e) {
                    e.printStackTrace();
                    Log.d(TAG,"Failed because of twitter network error");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    Log.d(TAG,"Updater interrupted");
                }
            }
        }.start();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "OnDestroy");
        running = false;
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
