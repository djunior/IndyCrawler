package com.djunior.indycrawlerapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import com.djunior.IndyCrawlerUtils.Event.Event;

import com.djunior.indycrawlerapp.EventDescription;

public class MainComponent extends ActionBarActivity {
    public final static String EXTRA_MESSAGE = "com.djunior.indycrawlerapp.MESSAGE";
    SimpleAdapter adapter;
    ArrayList<HashMap<String,String>> list = new ArrayList<HashMap<String,String>>();
    List<Event> eventList = new ArrayList<>();

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

        AdapterView.OnItemClickListener listener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> av, View v, int position, long id) {
                Log.d("IndyCrawlerApp", "Position " + position);
                sendMessage(eventList.get(position));
            }
        };

        listView.setOnItemClickListener(listener);
        getEvents();
    }

    public void sendMessage(Event e){
        Intent intent = new Intent(this, EventDescription.class);
        intent.putExtra(EXTRA_MESSAGE,e);
        startActivity(intent);
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

            NetworkWrapper nw = new NetworkWrapper();
            String serverResponse = nw.execute("http://10.10.1.34:8080/axis2/services/IndyCrawlerWeb/getEvents").get();
            @SuppressLint("NewApi") InputStream in = new ByteArrayInputStream(serverResponse.getBytes(StandardCharsets.UTF_8));
            Log.d("IndyCrawlerApp","ServerResponse = " + serverResponse);
            DocumentBuilderFactory dbf = DocumentBuilderFactory
                    .newInstance();
            DocumentBuilder builder;
            builder = dbf.newDocumentBuilder();
            Document mainDoc = builder.parse(in);
            NodeList nodeList = mainDoc.getElementsByTagName("ns:return");
            for (int i = 0; i < nodeList.getLength(); i++){
                System.out.println("Creating event");
                Event e = new Event();
                System.out.println("Getting child nodes");
                NodeList childNodes = nodeList.item(i).getChildNodes();
                System.out.println("child node length " + childNodes.getLength());
                for (int j = 0; j < childNodes.getLength();j++ ){
                    String name = childNodes.item(j).getNodeName();
                    String value = childNodes.item(j).getTextContent();
                    System.out.println("Name:" + name);
                    System.out.println("Value:" + value);
                    if (name.equals("ax25:name")) {
                        e.setName(value);
                    } else if(name.equals("ax25:description")){
                        e.setDescription(value);
                    } else if(name.equals("ax25:endDateTime")) {
                        e.setEndDateTime(value);
                    } else if(name.equals("ax25:startDateTime")) {
                        e.setStartDateTime(value);
                    } else if(name.equals("ax25:price")){
                        e.setPrice(value);
                    } else if(name.equals("ax25:locationId")){
                        e.setLocationId(Integer.parseInt(value));
                    } else if(name.equals("ax25:eventId")){
                        e.setEventId(Integer.parseInt(value));
                    } else if(name.equals("ax25:url")){
                        e.setUrl(value);
                    }
                }

                eventList.add(e);
                addEvent(e.getName(), "CCBB - " + e.getDate());
            }

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
