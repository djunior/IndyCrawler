/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.djunior.IndyCrawlerUtils.Location;

import com.djunior.IndyCrawlerUtils.DBConnector;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author djunior
 */
public class LocationManager {
    private static final DBConnector db = new DBConnector();
    
    // Location factory method
    private static Location createLocation(ResultSet data) throws SQLException {
        int locationId = Integer.parseInt(data.getString("locationId"));
        String name = data.getString("name");
        String fullName = data.getString("fullName");
        String address = data.getString("address");
        
        return new Location(locationId,name,fullName,address);
    }
    
    public static Location getLocation(int locationId) throws SQLException {
        Statement getLocationStm = db.getSQLConnection().createStatement();
        String getLocationQuery = String.format("select * from tb_Locations where locationId=%d",locationId);
        ResultSet locationData = getLocationStm.executeQuery(getLocationQuery);
        return createLocation(locationData);
    }
    
    public static List<Location> getAllLocations() throws SQLException{
        List<Location> locationList = new ArrayList<>();
        
        Statement getLocationStm = db.getSQLConnection().createStatement();
        String getLocationQuery = "select * from tb_Locations";
        ResultSet locationData = getLocationStm.executeQuery(getLocationQuery);
        
        while(locationData.next()) {
            Location l = createLocation(locationData);
            locationList.add(l);
        }
        
        return locationList;     
    }
    
    
}
