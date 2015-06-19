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
public class CrawlerInvalidHtmlException extends CrawlerParserException{
    public CrawlerInvalidHtmlException() { super(); }
    public CrawlerInvalidHtmlException(String url,String message){
        super("Html " + url + " is invalid, missing attribute: " + message);
    }
    public CrawlerInvalidHtmlException(String url) { 
        super("Html " + url + " is invalid");
    }
    public CrawlerInvalidHtmlException(String message, Throwable cause) { super(message, cause); }
    public CrawlerInvalidHtmlException(Throwable cause) { super(cause); }
}
