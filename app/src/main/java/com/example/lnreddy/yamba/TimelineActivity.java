package com.example.lnreddy.yamba;

import android.database.Cursor;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;


public class TimelineActivity extends ActionBarActivity {


    static final String[] FROM = {StatusData.C_USER,StatusData.C_CREATED_AT, StatusData.C_TEXT};
    static final int[] TO = {R.id.text_user, R.id.text_created_at,R.id.text_text};

    ListView list;
    Cursor cursor;
    SimpleCursorAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.timeline);

        list = (ListView) findViewById(R.id.list);

        cursor = ((YambaApp)getApplication()).statusData.query();

        adapter = new SimpleCursorAdapter(this,R.layout.row,cursor,FROM,TO);
        adapter.setViewBinder(VIEW_BINDER);
        list.setAdapter(adapter);

    }

    static final SimpleCursorAdapter.ViewBinder VIEW_BINDER = new SimpleCursorAdapter.ViewBinder(){

        @Override
        public boolean setViewValue(View view, Cursor cursor, int columnIndex) {

            if(view.getId()!=R.id.text_created_at) return false;

            long time = cursor.getLong(cursor.getColumnIndex(StatusData.C_CREATED_AT));
            CharSequence relativeTime = DateUtils.getRelativeTimeSpanString(time);
            ((TextView)view).setText(relativeTime);

            return true;
        }
    };

}
