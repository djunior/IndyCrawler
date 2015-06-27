/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.djunior.crawler.CCBB;

import com.djunior.IndyCrawlerUtils.Event.Event;
import com.djunior.indycrawler.CrawlerParserException;
import com.djunior.indycrawler.MainCrawler;
import java.util.Set;
import java.util.regex.Pattern;

import org.apache.http.Header;

import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.parser.HtmlParseData;
import edu.uci.ics.crawler4j.url.WebURL;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * @author djunior
 */
public class CCBBCrawler extends WebCrawler{
    
  private static final Pattern IMAGE_EXTENSIONS = Pattern.compile(".*\\.(bmp|gif|jpg|png)$");
  private static final String fileNameMask = "ccbb_%d.html";
  private int hitCount = 0;
  
  public boolean shouldVisit(Page referringPage, WebURL url) {
    String href = url.getURL().toLowerCase();
    // Ignore the url if it has an extension that matches our defined set of image extensions.
    if (IMAGE_EXTENSIONS.matcher(href).matches()) {
      return false;
    }

    return (href.startsWith("http://culturabancodobrasil.com.br/") && ! href.contains("wp-includes") && ! href.contains("wp-content") );
            
  }
  
  private boolean shouldParse(String url){
      return ! ( 
                url.contains("feed") || 
                url.contains("categoria") || 
                url.contains("rio-de-janeiro") || 
                url.contains("noticias") || 
                url.contains("parcerias-bb") || 
                url.contains("wp-includes") || 
                url.contains("wp-content") || 
                url.contains("distrito-federal") || 
                url.contains("sao-paulo") || 
                url.contains("belo-horizonte") || 
                url.contains("comment"));
  }

  @Override
  public void visit(Page page) {
    int docid = page.getWebURL().getDocid();
    String url = page.getWebURL().getURL();
    String domain = page.getWebURL().getDomain();
    String path = page.getWebURL().getPath();
    String subDomain = page.getWebURL().getSubDomain();
    String parentUrl = page.getWebURL().getParentUrl();
    String anchor = page.getWebURL().getAnchor();
    Event event;

    logger.debug("Docid: {}", docid);
    logger.debug("URL: {}", url);
    logger.debug("Domain: '{}'", domain);
    logger.debug("Sub-domain: '{}'", subDomain);
    logger.debug("Path: '{}'", path);
    logger.debug("Parent page: {}", parentUrl);
    logger.debug("Anchor text: {}", anchor);
    
    if (page.getParseData() instanceof HtmlParseData) {
        HtmlParseData htmlParseData = (HtmlParseData) page.getParseData();
        String text = htmlParseData.getText();
        String html = htmlParseData.getHtml();
        Set<WebURL> links = htmlParseData.getOutgoingUrls();

        logger.debug("Text length: {}", text.length());
        logger.debug("Html length: {}", html.length());
        logger.debug("Number of outgoing links: {}", links.size());
        
        if (shouldParse(url)){
            try {
                event = CCBBParser.parse(url,html);
                MainCrawler.addEvent(event);
            } catch (CrawlerParserException e){
                logger.warn("CCBBCrawler caught CrawlerParserException Exception: {}", e.getMessage());
            } catch (IOException e){
                logger.warn("CCBBCrawler caught IOException: {}",e.getMessage());
            }
                 
       }
    }

    Header[] responseHeaders = page.getFetchResponseHeaders();
    if (responseHeaders != null) {
      logger.debug("Response headers:");
      for (Header header : responseHeaders) {
//        logger.debug("\t{}: {}", header.getName(), header.getValue());
      }
    }

    logger.debug("=============");
  }
}
