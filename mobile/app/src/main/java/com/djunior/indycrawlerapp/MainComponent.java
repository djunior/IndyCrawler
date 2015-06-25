package com.djunior.indycrawlerapp;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import org.apache.http.client.HttpClient;
import org.apache.http.conn.HttpHostConnectException;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.ResponseHandler;
import org.apache.http.impl.client.BasicResponseHandler;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;


public class MainComponent extends ActionBarActivity {

    SimpleAdapter adapter;
    ArrayList<HashMap<String,String>> list = new ArrayList<HashMap<String,String>>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("IndyCrawlerApp", "OnCreate() begin");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_component);

        ListView listView = (ListView) findViewById(R.id.listView);

        adapter=new SimpleAdapter(this, list,
                R.layout.eventlist_element,
                new String[] { "name","info" },
                new int[] {R.id.name_entry, R.id.info_entry});

        listView.setAdapter(adapter);

        addEvent("Evento 1", "CCBB - 24/05/2015 a 26/07/2016");
        addEvent("Evento 2","MAC - 24/05/2015 a 26/07/2016");
        addEvent("Evento 3","MAR - 24/05/2015 a 26/07/2016");
        addEvent("Evento 4","MAM - 24/05/2015 a 26/07/2016");
    }

    private void addEvent(String name, String info){
        HashMap<String,String> event = new HashMap<>();
        event.put("name",name);
        event.put("info",info);
        list.add(event);
        adapter.notifyDataSetChanged();
    }

    private void getEvents(){
        Log.d("IndyCrawlerApp","getEvent(), creating NetworkWrapper");
        try {
            Log.d("IndyCrawlerApp","Calling networkwrapper");
            String serverResponse = new NetworkWrapper().execute("http://10.10.1.35:8080/axis2/services/IndyCrawler/getEvents").get();
            Log.d("IndyCrawlerApp","ServerResponse = " + serverResponse);

            // TODO - Parse response and build event list
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_component, menu);
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
