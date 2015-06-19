package com.djunior.indycrawlerapp;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

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


public class MainComponent extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("IndyCrawlerApp", "OnCreate() begin");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_component);
        Log.d("IndyCrawlerApp", "OnCreate() calling getEvents");
        getEvents();
        Log.d("IndyCrawlerApp", "OnCreate() getEvents called");
    }


    private void getEvents(){
        Log.d("IndyCrawllerApp","getEvent(), creating NetworkWrapper");
        try {
            Log.d("IndyCrawlerApp","Calling networkwrapper");
            String serverResponse = new NetworkWrapper().execute("http://10.10.1.35:8080/axis2/services/IndyCrawler/getEvents").get();
            Log.d("IndyCrawllerApp","ServerResponse = " + serverResponse);
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
