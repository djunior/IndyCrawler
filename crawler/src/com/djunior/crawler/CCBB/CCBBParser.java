/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.djunior.crawler.CCBB;

import com.djunior.IndyCrawlerUtils.Event.Event;
import com.djunior.indycrawler.CrawlerInvalidHtmlException;
import com.djunior.indycrawler.CrawlerInvalidLocationException;
import com.djunior.indycrawler.CrawlerParserException;
import com.djunior.indycrawler.MainCrawler;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;

/**
 *
 * @author djunior
 */
public class CCBBParser {
    static final int CCBB_ID = 001;
    static final Logger logger = MainCrawler.logger;
    
    public static Event parse(String url, String html) throws IOException,CrawlerParserException{
        Document doc = Jsoup.parse(html,url);
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        
        String locale = doc.body().getElementById("local").text();
        logger.info("parsing: {}",url);
        logger.debug("Locale: {}",locale);
        if (! locale.contains("RJ"))
            throw new CrawlerInvalidLocationException(doc.location());
        
        String name = doc.body().getElementsByClass("nome-evento").text();
        String date = doc.body().getElementsByClass("data-evento").text();
        String price = doc.body().getElementsByClass("ingresso").first().text().replace("Ingresso ","");
        String description = doc.body().getElementsByClass("saiba-mais-sobre").text();
        String hour = doc.body().getElementsByClass("horario").text().replace("Horário de ", "");
        String startDateStr, endDateStr, startHourStr, endHourStr;
        Date startDate,endDate;
        
        logger.debug("Name: {}",name);
        logger.debug("Date: {}",date);
        logger.debug("Hour: {}",hour);
        
        if (name.equals(""))
            throw new CrawlerInvalidHtmlException(url,"name");
        else if(date.equals(""))
            throw new CrawlerInvalidHtmlException(url,"date");
        
        if (hour.equals("")){
            startHourStr = "00:00";
            endHourStr = "00:00";
        } else {
            if (hour.contains("às")){
                String[] workingHours = hour.split(" às ");
                startHourStr = workingHours[0].replace("h",":");
                endHourStr = workingHours[1].replace("h",":");
            } else {
                startHourStr = hour.replace("h",":");
                endHourStr = startHourStr;
            }
        }
        
        if (date.contains("a")){
            String[] dates = date.split(" a ");
            startDateStr = dates[0].replace(".","/") + "/" + currentYear;
            endDateStr = dates[1].replace(".","/") + "/" + currentYear;
        } else {
            startDateStr = date.replace(".","/") + "/" + currentYear;
            endDateStr = startDateStr;
        }
        
        try {
            startDate = dateFormat.parse(startDateStr + " " + startHourStr);
            endDate = dateFormat.parse(endDateStr + " " + endHourStr);
        } catch (ParseException e){
            throw new CrawlerParserException(url,e);
        }
        
        return new Event(CCBB_ID,name,description,startDate,endDate,price,url);
            
    }
    
}
