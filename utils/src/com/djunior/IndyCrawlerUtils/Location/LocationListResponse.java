/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.djunior.IndyCrawlerUtils.Location;

import java.io.StringWriter;
import java.util.List;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author djunior
 */
@XmlRootElement(name = "locationList")
public class LocationListResponse {
    @XmlElement(name="location")
    List<Location> locationList;
    
    public LocationListResponse(){
        
    }
    
    public LocationListResponse(List<Location> list){
        locationList = list;
    }
    
    public String toXml(){
        java.io.StringWriter sw = new StringWriter();
        try {
            JAXBContext context = JAXBContext.newInstance(LocationListResponse.class);
            Marshaller m = context.createMarshaller();

            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            m.marshal(this, sw);
            return sw.toString();
        } catch (JAXBException e) {
            return "";
        }
    }
    
}
