package com.djunior.IndyCrawlerUtils.Event;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author djunior
 */
@XmlRootElement(name = "event")
//@XmlType(propOrder = {"name","locationId","price","url","startDate","endDate"})
public class Event implements java.io.Serializable{
    private static final String dateFormatStr = "yyyy-MM-dd HH:mm:ss";
    private String name,description,price,url;
    private int eventId,locationId;
    private Date startDate,endDate;
    
    public Event(){
        locationId = 0;
        name = "";
        description = "";
        startDate = new Date();
        endDate = new Date();
        price = "";
        url = "";
        eventId = 0;
    }
    
    // Constructor used when creating a new Event. In this case, eventId is not needed
    // because it will be given a value by the database
    public Event(int _locationId, String _name, String _description, Date _startDate, Date _endDate, String _price, String _url){
        eventId = 0;
        locationId = _locationId;
        name = _name;
        description = _description;
        startDate = _startDate;
        endDate = _endDate;
        price = _price;
        url = _url;

    }
    
    //Constructor used when fetching Events from the database
    public Event(int _eventId, int _locationId, String _name, String _description, Date _startDate, Date _endDate, String _price, String _url){
        eventId = _eventId;
        locationId = _locationId;
        name = _name;
        description = _description;
        startDate = _startDate;
        endDate = _endDate;
        price = _price;
        url = _url;
    }
    
    public void setName(String n){
        name = n;
    }
    
    @XmlElement
    public String getName(){
        return name;
    }
    
    public void setDescription(String d){
        description = d;
    }
    
    @XmlElement
    public String getDescription(){
        return description;
    }

    public String getDate(){
        if (startDate.equals(endDate)){
            return getStartDate();
        }
        return getStartDate() + " a " + getEndDate();
    }
    
    public String getStartDate(){
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        return dateFormat.format(startDate);
    }
    
    public String getEndDate(){
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        return dateFormat.format(endDate);
    }
    
    public String getStartTime(){
        DateFormat dateFormat = new SimpleDateFormat("HH:mm");
        return dateFormat.format(startDate);
    }
    
    public String getEndTime(){
         DateFormat dateFormat = new SimpleDateFormat("HH:mm");
        return dateFormat.format(endDate);       
    }
    
    public void setStartDateTime(String startDateTime){
        DateFormat dateFormat = new SimpleDateFormat(dateFormatStr);
        try {
            startDate = dateFormat.parse(startDateTime);
        } catch (ParseException e){
            startDate = new Date();
        }
    }
    
    @XmlElement
    public String getStartDateTime(){
        DateFormat dateFormat = new SimpleDateFormat(dateFormatStr);
        return dateFormat.format(startDate);
    }

    public void setEndDateTime(String endDateTime){
        DateFormat dateFormat = new SimpleDateFormat(dateFormatStr);
        try {
            startDate = dateFormat.parse(endDateTime);
        } catch (ParseException e){
            startDate = new Date();
        }
    }
    
    @XmlElement
    public String getEndDateTime(){
        DateFormat dateFormat = new SimpleDateFormat(dateFormatStr);
        return dateFormat.format(endDate);
    }
    
    public void setPrice(String p){
        price = p;
    }
    
    @XmlElement
    public String getPrice(){
        return price;
    }
    
    public void setUrl(String u){
        url = u;
    }
    
    @XmlElement
    public String getUrl(){
        return url;
    }
    
    public void setEventId(int id){
        eventId = id;
    }
    
    @XmlAttribute(name="eventId")
    public int getEventId(){
        return eventId;
    }
    
    public void setLocationId(int id){
        locationId = id;
    }
    
    @XmlElement
    public int getLocationId(){
        return locationId;
    }
}
