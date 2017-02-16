package com.aragog;

import java.io.IOException;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class Aragog {
    private static final int MAX_TITLES_TO_RETRIEVE = 100;
    
    private static int count = 0;
    
    private Set<String> titles = new HashSet<String>();
    
    private Set<String> prices = new HashSet<String>();

    private String nextUrl(String url) {
        String nextUrl;
        count = count + 100;
        nextUrl = url +"?s=" +count;  
        return nextUrl;
    }

    /**
     * Launching point for the Aragog's functionality. Internally it creates aragog legs
     * that make an HTTP request and parse the response (the web page).
     * 
     * @param url
     *            - The starting point of the aragog
     * 
     */
    public void getTitlesAndPrices(String url){
        while(titles.size() < MAX_TITLES_TO_RETRIEVE) {
            String currentUrl;
            AragogLeg leg = new AragogLeg();
            if (titles.isEmpty()) {
                currentUrl = url;
            } else {
                currentUrl = nextUrl(url);
            }
            try{
                leg.crawl(currentUrl);
            } catch(IOException ioe){
                System.out.println(ioe);
                break;
            }
            titles.addAll(leg.getTitles());
            prices.addAll(leg.getPrices());
        }
    }
}
