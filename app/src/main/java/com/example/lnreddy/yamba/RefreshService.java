package com.example.lnreddy.yamba;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import java.util.List;

import winterwell.jtwitter.Twitter;
import winterwell.jtwitter.TwitterException;


/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p/>
 * TODO: Customize class - update intent actions and extra parameters.
 */
public class RefreshService extends IntentService {

    static final String TAG = "RefreshService";
    public RefreshService() {
        super(TAG);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "OnCreate");

    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            StatusData statusData = ((YambaApp)getApplication()).statusData;

            try {
                List<Twitter.Status> timeline = ((YambaApp)getApplication()).getTwitter().getPublicTimeline();

                for (Twitter.Status status : timeline) {

                    statusData.insert(status);
                    Log.d(TAG, String.format("%s: %s", status.user.name, status.text));
                }
            } catch (TwitterException e) {
                e.printStackTrace();
                Log.d(TAG, "Failed to access twitter",e);
            }
            Log.d(TAG,"OnHandleIntent");

        }
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "OnDestroy");
        super.onDestroy();
    }


}
