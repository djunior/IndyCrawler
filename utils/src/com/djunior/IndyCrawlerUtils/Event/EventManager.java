package com.djunior.IndyCrawlerUtils.Event;

import com.djunior.IndyCrawlerUtils.DBConnector;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Class that manages all Event's found by the crawlers.
 * This class interfaces with the database, and load all Event IDs found
 * when it is first created. As the crawlers find new Events, the new Event IDs
 * are checked to see if they are not duplicates, and if not, the Event data is
 * stored in the database.
 * 
 * @author djunior
 */
public class EventManager {
    private static final DBConnector db = new DBConnector();
    
    // Adds an Event the data base, filtering for duplicates
    public static void addEvent(Event event){
        addEventToDB(event);
    }
    
    //Loads an Event from the database
    private static Event loadEventFromDB(int eventId) throws EventIdNotFoundException,ParseException {
        java.sql.Connection dbConnection = db.getSQLConnection();
        try {
            Statement getEventStm = dbConnection.createStatement();
            String getEventQuery = String.format("select * from tb_Events where eventId=\"%d\"", eventId);
            ResultSet eventData = getEventStm.executeQuery(getEventQuery);
            if (! eventData.next()) {
                dbConnection.close();
                throw new EventIdNotFoundException(eventId);
            }
            
            dbConnection.close();
            return EventFactory.createEvent(eventData);
            
        } catch (SQLException e) {
            throw new EventIdNotFoundException(eventId);
        }
    }
    
    // Checks if the event exits in the EventIdSet. If not, throw an EventIdNotFoundException
    // If so, loads the object from the DB.
    public static Event getEvent(int eventId) throws EventIdNotFoundException, ParseException{
        Event e = loadEventFromDB(eventId);
        return e;
    }
    
    // Saves an Event in the DB
    private static void addEventToDB(Event event){
        java.sql.Connection dbConnection = db.getSQLConnection();
        try {
            Statement insertEventStm = dbConnection.createStatement();
            String insertEventQuery = String.format("INSERT INTO tb_Events(name,description,startDate,endDate,url,locationId,price) VALUES(\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",%d,\"%s\")",
                    event.getName(), event.getDescription(), event.getStartDateTime(), event.getEndDateTime(), event.getUrl(), event.getLocationId(), event.getPrice());
            int row = insertEventStm.executeUpdate(insertEventQuery);
            System.out.println("[EventManager.addEventToDB] row = " + row);
            dbConnection.close();
        } catch (SQLException e){
            System.out.println("[EventManager.addEventToDB] caught SQL Exception: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    public static List<Event> getEventsSince(Date date) throws SQLException{
        List<Event> eventList = new ArrayList<>();
        
        DateFormat dateFormat = new SimpleDateFormat("yyyMMdd");
        String currentDateStr = dateFormat.format(date);
        
        java.sql.Connection dbConnection = db.getSQLConnection();

        Statement getEventStm = dbConnection.createStatement();
        String getEventQuery = String.format("select * from tb_Events where DATE(endDate) > '%s'", currentDateStr);
        ResultSet eventDataList = getEventStm.executeQuery(getEventQuery);

        while(eventDataList.next()) {
            try {
                Event event = EventFactory.createEvent(eventDataList);
                eventList.add(event);
            } catch (ParseException | SQLException e) {
                //Dont add event to EventList
            }
            
        }
        dbConnection.close();
        return eventList;
    }
    
    public static List<Event> getEventsFromLocation(int locationId) throws SQLException{
        List<Event> eventList = new ArrayList<>();
        
        java.sql.Connection dbConnection = db.getSQLConnection();

        Statement getEventStm = dbConnection.createStatement();
        String getEventQuery = String.format("select * from tb_Events where locationId=%d", locationId);
        ResultSet eventDataList = getEventStm.executeQuery(getEventQuery);

        while(eventDataList.next()) {
            try {
                Event event = EventFactory.createEvent(eventDataList);
                eventList.add(event);
            } catch (ParseException | SQLException parseEx) {
                //Dont add event to EventList
            }
        }
        
        dbConnection.close();
        return eventList;
    }
    
    public static List<Event> getAllEvents() throws SQLException{
        List<Event> eventList = new ArrayList<>();
        java.sql.Connection dbConnection = db.getSQLConnection();
        
        Statement getEventStm = dbConnection.createStatement();
        String getEventQuery = "select * from tb_Events";
        ResultSet eventDataList = getEventStm.executeQuery(getEventQuery);
        while(eventDataList.next()) {
            try {
                Event event = EventFactory.createEvent(eventDataList);
                eventList.add(event);
            } catch (ParseException | SQLException parseEx) {
                //Dont add event to EventList
            }
        }
        return eventList;
    }
    
}
