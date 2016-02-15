package com.djunior.indycrawlerapp;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.djunior.IndyCrawlerUtils.Event.Event;

public class EventDescription extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_event_description);

        Intent intent = getIntent();
        Event e = (Event) intent.getSerializableExtra(MainComponent.EXTRA_MESSAGE);
        Log.d("IndyCrawlerApp", "Event name: " + e.getName());

        TextView nameView = (TextView) findViewById(R.id.eventName);
        nameView.setText(e.getName());

        TextView dateView = (TextView) findViewById(R.id.date);
        dateView.append(e.getDate());

        TextView hourView = (TextView) findViewById(R.id.workingHours);
        if (e.getStartTime().equals("00:00") && e.getEndTime().equals("00:00")) {
            hourView.setVisibility(View.GONE);
        } else if (e.getStartTime().equals(e.getEndTime())) {
            hourView.append(e.getStartTime());
        } else {
            hourView.append(e.getStartTime() + " a " + e.getEndTime());
        }

        TextView descriptionView = (TextView) findViewById(R.id.description);
        descriptionView.setText(e.getDescription());

        descriptionView.setMovementMethod(new ScrollingMovementMethod());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_event_description, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
