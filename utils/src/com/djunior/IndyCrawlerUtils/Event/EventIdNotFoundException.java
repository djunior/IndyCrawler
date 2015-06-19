package com.djunior.IndyCrawlerUtils.Event;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 *
 * @author djunior
 */
public class EventIdNotFoundException extends Exception{
    
    public EventIdNotFoundException(){
        super();
    }
    
    public EventIdNotFoundException(int eventId){ 
        super("Event " + eventId + " not found");
    }
    
    public EventIdNotFoundException(String message, Throwable cause) { super(message, cause); }
    public EventIdNotFoundException(Throwable cause) { super(cause); }
    
}
