package com.djunior.IndyCrawlerUtils;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 *
 * @author djunior
 */
public class DBConnector {
    public static String status;
    private static String DATABASE_NAME = "db_hello";
    private static String MYSQL_SERVER = "localhost:3306";
    private static String MYSQL_USERNAME = "";
    private static String MYSQL_PASSWORD = "";
    
    public DBConnector() {
        status = "Not connected to DB";
        
        String result = "";
        Properties prop = new Properties();
        InputStream input = getClass().getClassLoader().getResourceAsStream("db.properties");
       if (input != null){
           try {
            prop.load(input);
           } catch (IOException e){
            return;
           }
       }
       
       DATABASE_NAME = prop.getProperty("DATABASE");
       MYSQL_SERVER = prop.getProperty("SERVER") + ":" + prop.getProperty("PORT");
       MYSQL_USERNAME = prop.getProperty("USER");
       MYSQL_PASSWORD = prop.getProperty("PASSWORD");
       System.out.println("DATABASE_NAME: " + DATABASE_NAME);
       System.out.println("MYSQL_SERVER: " + MYSQL_SERVER);
       System.out.println("MYSQL_USERNAME: " + MYSQL_USERNAME);
       System.out.println("MYSQL_PASSWORD: " + MYSQL_PASSWORD);
    }
    
    public static java.sql.Connection getSQLConnection() {
        System.out.println("[DBConnector] getSQLConnection() started");
        Connection connection = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            String url = "jdbc:mysql://" + MYSQL_SERVER + "/" + DATABASE_NAME;
            connection = DriverManager.getConnection(url,MYSQL_USERNAME,MYSQL_PASSWORD);
            status = "Connected to DB!";
            return connection;
        } catch (ClassNotFoundException e) {
            System.out.println("[DBConnector] ClassNotFoundException");
            System.out.println(e.getMessage());
            status = "Could not connect to DB, ClassNotFoundException\n" + e.getMessage();
            return null;
        } catch (SQLException e) {
            System.out.println("[DBConnector] SQLException");
            System.out.println(e.getMessage());
            status = "Could not connect to DB, SQLException\n" + e.getMessage();
            return null;
        }
    }
    
    public static String getStatusMessage(){
        return status;
    }
    
    public static boolean closeConnection(){
        try {
            getSQLConnection().close();
            status = "Not connected to DB";
            return true;
        } catch (SQLException e){
            status = "Could not disconnect to DB\n" + e.getMessage();
            return false;
        }
    }
    
    public static java.sql.Connection restartConnection(){
        closeConnection();
        return getSQLConnection();
    }
    
    
}
