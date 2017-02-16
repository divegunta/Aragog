package com.aragog;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.aragog.datamodel.Item;

public class AragogLeg {
    // We'll use a fake USER_AGENT so the web server thinks the robot is a normal web browser.
    private static final String USER_AGENT = "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/535.1 (KHTML, like Gecko) Chrome/13.0.782.112 Safari/535.1";

    private Collection<String> titles = new ArrayList<String>();
    private Collection<String> prices = new ArrayList<String>();
    private Collection<Item> items = new ArrayList<Item>();

    /**
     * This performs all the work. It makes an HTTP request, checks the response, and then gathers
     * up the title and price on the page.
     * 
     * @param url
     *            - The URL to visit
     * @throws IOException 
     */
    public Collection<Item> crawl(String url) throws IOException {
        try {
            Connection connection = Jsoup.connect(url).userAgent(USER_AGENT);
            Document htmlDocument = connection.get();
            if (connection.response().statusCode() == 200) // 200 is the HTTP OK status code
                                                           // indicating that everything is great.
            {
                System.out.println("Visiting Received web page at " + url);
            }
            if (!connection.response().contentType().contains("text/html")) {
                // Any other type other than text/html is not supported.
                throw new IOException(" Failure Retrieved something other than HTML");
            }
            
            Elements liElements = htmlDocument.select("p.result-info");
            for (Element li : liElements) {
                titles = new ArrayList<String>();
                prices = new ArrayList<String>();
                
                Elements titleElements = li.select("a.result-title");
                for (Element title : titleElements) {
                    titles.add(title.text());
                }
                Elements priceElements = htmlDocument.select("span.result-price");
                System.out.println("Found (" + priceElements.size() + ") links");
                for (Element priceElement : priceElements) {
                    prices.add(priceElement.text());
                }
                Item item = new Item(titles.iterator().next(),Double.parseDouble(prices.iterator().next()));
                items.add(item);
            }
            return items;
        } catch (IOException ioe) {
            // HttpRequest was not successful
            throw ioe;
        }
    }
}
