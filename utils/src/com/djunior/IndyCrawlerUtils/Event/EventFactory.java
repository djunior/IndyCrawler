/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.djunior.IndyCrawlerUtils.Event;

import java.io.InputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

/**
 *
 * @author djunior
 */
public class EventFactory {
    
    public static Event createEvent(ResultSet eventData) throws SQLException, ParseException {
        String name = eventData.getString("name");
        String description = eventData.getString("description");
        String locationid = eventData.getString("locationId");
        String price = eventData.getString("price");
        String url = eventData.getString("url");
        String startDate = eventData.getString("startDate");
        String endDate = eventData.getString("endDate");

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date startDateTime = dateFormat.parse(startDate);
        Date endDateTime = dateFormat.parse(endDate);

        return new Event(Integer.parseInt(locationid),name,description,startDateTime,endDateTime,price,url);
    }

    public static Event createEvent(InputStream xml) throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(Event.class);
        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
        Event event = (Event) jaxbUnmarshaller.unmarshal(xml);
        return event;
    }
    
    public static EventListResponse createEventList(InputStream xml) throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(EventListResponse.class);
        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
        EventListResponse eventList = (EventListResponse) jaxbUnmarshaller.unmarshal(xml);
        return eventList;      
    }
    
}
