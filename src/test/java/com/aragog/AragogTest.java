package com.aragog;

import java.util.Collection;

import com.aragog.Aragog;
import com.aragog.datamodel.GetItemsRequest;
import com.aragog.datamodel.Item;

public class AragogTest {
    /**
     * This test creates a Aragog (which creates Aragog legs) and crawls the web.
     * 
     * @param args
     *            - not used
     */
    public static void main(String[] args) {
        Aragog aragog = new Aragog();
        aragog.getItemInformation("https://newyork.craigslist.org/search/bka");
        
        GetItemsRequest getItemRequest = new GetItemsRequest("Book", 10);
        Collection<Item> items = aragog.searchItem(getItemRequest);
        for(Item item: items){
            System.out.println("Item Title:"+ item.getTitle()+"Item Price:" +item.getPrice());
        }
    }
}
