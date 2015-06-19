/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.djunior.indycrawler;

/**
 *
 * @author djunior
 */
public class CrawlerInvalidLocationException extends CrawlerParserException{
    private static final String baseMessage = "Event at a invalid location ";
    public CrawlerInvalidLocationException() { super(); }
    public CrawlerInvalidLocationException(String location) { 
        super(baseMessage + location); 
    }
    public CrawlerInvalidLocationException(String location, Throwable cause) 
    { 
        super(baseMessage + location, cause); 
    }
    public CrawlerInvalidLocationException(Throwable cause) { super(cause); }
}
