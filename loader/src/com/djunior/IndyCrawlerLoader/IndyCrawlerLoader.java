/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.djunior.IndyCrawlerLoader;

import com.djunior.IndyCrawlerUtils.Event.Event;
import com.djunior.IndyCrawlerUtils.Event.EventFactory;
import com.djunior.IndyCrawlerUtils.Event.EventListResponse;
import com.djunior.IndyCrawlerUtils.Event.EventManager;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import javax.xml.bind.JAXBException;

/**
 *
 * @author djunior
 */
public class IndyCrawlerLoader {
    private static final Properties prop;
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        String fileName = "/Users/djunior/eventList.xml";
        try {
            FileInputStream xmlStream = new FileInputStream(fileName);
            System.out.println("Getting EventListResponse from xml");
            EventListResponse eventListResponse = EventFactory.createEventList(xmlStream);
            System.out.println("GOt eventList");
            List<Event> list = eventListResponse.eventList;
            for (int i = 0; i < list.size(); i++){
                Event e = list.get(i);
                System.out.println("Event index " + i);
                System.out.println("Name: " + e.getName());
                System.out.println("Date: " + e.getDate());
                EventManager.addEvent(e);
            }
            xmlStream.close();
        } catch (FileNotFoundException e) {
            System.out.println("Caught FileNotFoundException " + e.getMessage());
        } catch (JAXBException e) {
            System.out.println("Caught JAXBException " + e.getMessage());
        } catch (IOException e) {
            System.out.println("Caught IOException " + e.getMessage());
        }
//        createEventList
        
        
    }
    
}
