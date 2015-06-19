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
public class CrawlerParserException extends Exception{
    public CrawlerParserException() { super(); }
    public CrawlerParserException(String url,String message){

    }
    public CrawlerParserException(String url) { 
        super("Could not parse " + url); 
    }
    public CrawlerParserException(String message, Throwable cause) { super(message, cause); }
    public CrawlerParserException(Throwable cause) { super(cause); }
}
