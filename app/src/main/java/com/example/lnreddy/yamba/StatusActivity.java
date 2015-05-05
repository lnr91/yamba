package com.example.lnreddy.yamba;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import winterwell.jtwitter.Twitter;
import winterwell.jtwitter.TwitterException;


public class StatusActivity extends ActionBarActivity {

    static final String TAG = "StatusActivity";
    EditText editStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.status);

        editStatus = (EditText) findViewById(R.id.edit_status);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_status, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        Intent intent = new Intent(this,UpdaterService.class);
        Intent intent_refresh = new Intent(this,RefreshService.class);

        int id = item.getItemId();

        switch(id){
            case R.id.item_start_service:
                startService(intent);
                return true;
            case R.id.item_stop_service:
                stopService(intent);
                return true;
            case R.id.item_refresh_timeline:
                startService(intent_refresh);
                return true;
            case R.id.item_prefs:
                startActivity(new Intent(this,PrefsActivity.class));
            default:
                return false;
        }
    }

    public void onClick(View view) {
        final String statusText = editStatus.getText().toString();
        Log.d(TAG,"onCLicked with text:"+statusText);

        new PostToTwitter().execute(statusText);

    }

    class PostToTwitter extends AsyncTask<String,Void,String> {

        @Override
        protected String doInBackground(String... strings) {
            try {
                ((YambaApp)getApplication()).getTwitter().setStatus(strings[0]);
                Log.d(TAG, "Successfully posted: " + strings[0]);
                return "Successfully posted: " +strings[0];
            } catch (TwitterException e) {
                Log.e(TAG,"Died",e);
                e.printStackTrace();
                return "Failed Posting: "+strings[0];
            }
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            Toast.makeText(StatusActivity.this,result,Toast.LENGTH_LONG).show();
        }
    }

}
