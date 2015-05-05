package com.example.lnreddy.yamba;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import winterwell.jtwitter.Twitter;

/**
 * Created by lnreddy on 28/04/15.
 */
public class YambaApp extends Application implements SharedPreferences.OnSharedPreferenceChangeListener {

    static final String TAG = "YambaApp";
    Twitter twitter;
    SharedPreferences prefs;
    StatusData statusData;

    @Override
    public void onCreate() {
        super.onCreate();

        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        prefs.registerOnSharedPreferenceChangeListener(this);

        statusData = new StatusData(this);

        Log.d(TAG, "OnCreate");
    }


    public Twitter getTwitter(){
        Log.d(TAG,"getTwitter is called");
        if (twitter == null) {
            Log.d(TAG,"Username is now:"+prefs.getString("username", ""));
            String username = prefs.getString("username", "");
            String password = prefs.getString("password", "");
            String server = prefs.getString("server", "http://yamba.marakana.com/api");
            Log.d(TAG,"Server is "+server);

            twitter = new Twitter(username, password);
            twitter.setAPIRootUrl(server);
        }
        return twitter;
    }



    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
        twitter = null;
        Log.d(TAG,"onSharedPreferenceChanged for key:"+s);
    }
}
