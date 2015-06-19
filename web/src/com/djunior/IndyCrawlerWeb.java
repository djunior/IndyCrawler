/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.djunior;

import com.djunior.IndyCrawlerUtils.Event.Event;
import com.djunior.IndyCrawlerUtils.Event.EventListResponse;
import com.djunior.IndyCrawlerUtils.Event.EventManager;
import com.djunior.IndyCrawlerUtils.Location.Location;
import com.djunior.IndyCrawlerUtils.Location.LocationManager;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

public class IndyCrawlerWeb {
    
    public Location[] getLocations() throws SQLException{
        List<Location> locationList = LocationManager.getAllLocations();
        Location[] locationArray = new Location[locationList.size()];
        locationList.toArray(locationArray);
        return locationArray;
    }
    
    public Event[] getEvents() throws SQLException{
        List<Event> eventList = EventManager.getAllEvents();
        Event[] eventArray = new Event[eventList.size()];
        eventList.toArray(eventArray);

        return eventArray;
    }
    
    public Event[] getEventsFromLocation(String locationId) throws SQLException{
        int id = Integer.parseInt(locationId);

        List<Event> eventList = EventManager.getEventsFromLocation(id);
        
        Event[] eventArray = new Event[eventList.size()];
        eventList.toArray(eventArray);
        return eventArray;
    }
    
    public Event[] getEventsSinceDate(String startDate) throws SQLException {
        
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Date date;
        try {
            date = dateFormat.parse(startDate);            
        } catch (ParseException ex) {
            date = new Date();
        }

        List<Event> eventList = EventManager.getEventsSince(date);
        Event[] eventArray = new Event[eventList.size()];
        eventList.toArray(eventArray);
        return eventArray;
    }
    
}
