package com.aragog;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import com.aragog.datamodel.GetItemsRequest;
import com.aragog.datamodel.Item;
import com.aragog.jdbc.JDBCManager;

public class Aragog {
    private static final int MAX_ITEMS_TO_RETRIEVE = 100;
    
    private static int count = 0;
    
    private String nextUrl(String url) {
        String nextUrl;
        count = count + 100;
        nextUrl = url +"?s=" +count;  
        return nextUrl;
    }

    /**
     * Gets the item information and stores it in the database.
     * 
     * @param url
     *            - The url to get the item information
     * @throws SQLException 
     * 
     */
    public void getItemInformation(String url){
        Collection<Item> items = new ArrayList<Item>();
        JDBCManager jdbcManager = new JDBCManager();
        
        while(items.size() < MAX_ITEMS_TO_RETRIEVE) {
            String currentUrl;
            AragogLeg leg = new AragogLeg();
            if (items.isEmpty()) {
                currentUrl = url;
            } else {
                currentUrl = nextUrl(url);
            }
            try{
                items.addAll(leg.crawl(currentUrl));
            } catch(IOException ioe){
                System.out.println(ioe);
                break;
            }
        }
        if(items.size() > 0){
            try {
                jdbcManager.insertItems(items);
            } catch (SQLException e) {
                System.out.println(e);
            }
        }
        
    }
    
    /**
     * Searches item based on request
     * @param request - request used for searching the items
     * @return Items that match the request
     */
    public Collection<Item> searchItem(GetItemsRequest request){
        Collection<Item> items = new ArrayList<Item>();
        JDBCManager jdbcManager = new JDBCManager();
        try {
            items = jdbcManager.getItemsByCriteria(request);
        } catch (SQLException e) {
            System.out.println(e);
        }
        return items;
    }
}
