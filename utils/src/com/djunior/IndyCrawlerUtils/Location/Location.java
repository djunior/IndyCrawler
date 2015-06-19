/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.djunior.IndyCrawlerUtils.Location;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author djunior
 */
@XmlRootElement(name = "location")
public class Location {
    private final String name, fullName, address;
    private final int locationId;
    
    public Location(){
        name = "";
        fullName = "";
        address = "";
        locationId = 0;
    }
    
    public Location(int _locationId, String _name, String _fullName, String _address){
        locationId = _locationId;
        name = _name;
        fullName = _fullName;
        address = _address;
    }
    
    @XmlAttribute(name = "id")
    public int getLocationId(){
        return locationId;
    }
    
    @XmlElement
    public String getName(){
        return name;
    }
    
    @XmlElement
    public String getFullName(){
        return fullName;
    }
    
    @XmlElement
    public String getAddress(){
        return address;
    }
    
    
}
