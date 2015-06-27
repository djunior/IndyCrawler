/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.djunior.indycrawler;

import com.djunior.IndyCrawlerUtils.Event.Event;
import com.djunior.IndyCrawlerUtils.Event.EventListResponse;
import com.djunior.crawler.CCBB.CCBBCrawler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 *
 * @author djunior
 */
public class MainCrawler {
    public static final Logger logger = LoggerFactory.getLogger(MainCrawler.class);
    private static final List<Event> eventList = new ArrayList<>();
    private static final Properties prop = new Properties();
    
    public static void addEvent(Event e){
        eventList.add(e);
    }

    public static void main(String[] args) throws Exception {
        int numberOfCrawlers = 5;
        String crawlStorageFolder = ".";
        String outputFilePath = "./eventList.xml";
        String proxy_host = "";
        int proxy_port = 8080;
        
        try (InputStream input = MainCrawler.class.getResourceAsStream("crawler.properties")) {
            prop.load(input);
            crawlStorageFolder = prop.getProperty("CRAWL_STORAGE_FOLDER");
            numberOfCrawlers = Integer.parseInt(prop.getProperty("NUMBER_OF_CRAWLERS"));
            outputFilePath = prop.getProperty("OUTPUT_FILE");
            proxy_host = prop.getProperty("PROXY_HOST");
            String port = prop.getProperty("PROXY_PORT");
            if (port.isEmpty())
                proxy_port=0;
            else
                proxy_port = Integer.parseInt(port);
        } catch (IOException e){
            logger.warn("Caught IOException " + e.getMessage());
        } catch (NullPointerException e) {
            logger.warn("Caught NullPointerException " + e.getMessage());
        }
        
        CrawlConfig config = new CrawlConfig();

        config.setCrawlStorageFolder(crawlStorageFolder);

        if (! proxy_host.isEmpty()){
            config.setProxyHost(proxy_host);
            config.setProxyPort(proxy_port);
        }

        config.setPolitenessDelay(1000);
        config.setMaxDepthOfCrawling(2);
        config.setMaxPagesToFetch(1000);
        config.setIncludeBinaryContentInCrawling(false);
        config.setResumableCrawling(false);

        PageFetcher pageFetcher = new PageFetcher(config);
        RobotstxtConfig robotstxtConfig = new RobotstxtConfig();
        RobotstxtServer robotstxtServer = new RobotstxtServer(robotstxtConfig, pageFetcher);
        CrawlController controller = new CrawlController(config, pageFetcher, robotstxtServer);

        controller.addSeed("http://culturabancodobrasil.com.br/portal/rio-de-janeiro/");

        controller.start(CCBBCrawler.class, numberOfCrawlers);

        logger.info("Found {} events",eventList.size());
        logger.info("Saving eventList to {}",outputFilePath);
        
        // Saving event list to xml
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(new File(outputFilePath).getAbsoluteFile()))) {
            bw.write(new EventListResponse(eventList).toXml());
        } catch (IOException e) {
            logger.error("MainCrawler caught IOException while saving output file: {}",outputFilePath);
            logger.error(e.getMessage());
        }

    }
 
}
