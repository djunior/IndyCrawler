package com.djunior.indycrawlerapp;

import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.conn.HttpHostConnectException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by djunior on 06/06/15.
 */
public class NetworkWrapper extends AsyncTask<String, Void, String> {

    protected String doInBackground(String... params){
        Log.d("IndyCrawlerApp", "getEvents() called!");
        String urlString = params[0];
//                "http://10.10.1.35:8080/axis2/services/IndyCrawler/getEvents";
        Log.d("IndyCrawlerA" +
                "pp", "getEvents() trying to connect to url: " + urlString);

        BufferedReader reader = null;
        URL url;
        String s = "";

        try
        {
            Log.d("IndyCrawlerApp","Creting new URL");
            url = new URL(urlString);
            Log.d("IndyCrawlerApp","Opening URL Connection");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            Log.d("IndyCrawlerApp","StatusCode: " + con.getResponseCode());
            Log.d("IndyCrawlerApp","Creating input stream");
            InputStream in = con.getInputStream();
            Log.d("IndyCrawlerApp","Creating InputStreamReader");
            InputStreamReader inReader = new InputStreamReader(in);
            Log.d("IndyCrawlerApp","Creating buffered reader");
            reader = new BufferedReader(inReader);
            Log.d("IndyCrawlerApp","Reading buffer lines");
            String line;
            while ((line = reader.readLine()) != null) {
                s = s + line;
            }
            Log.d("IndyCrawlerApp","Closing buffered reader");
            reader.close();

            Log.d("IndyCrawlerApp", "getEvents() server response:");
            Log.d("IndyCrawlerApp",s);
            return s;

        }
        catch (MalformedURLException e) {
            Log.d("IndyCrawlerApp","Caught MalformedURLException " + e.getMessage());
            e.printStackTrace();
        }
        catch(HttpHostConnectException e){
            Log.d("IndyCrawlerApp","Caught HttpHostConnectException " + e.getMessage());
            e.printStackTrace();
        }
        catch (IOException e){
            Log.d("IndyCrawlerApp","Caught IOException " + e.getMessage());
            e.printStackTrace();
        }
        catch(Exception e)
        {
            Log.d("IndyCrawlerApp","Caught default exception " + e.getMessage());
            e.printStackTrace();
        }
        return "";
    }

}
