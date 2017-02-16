package com.aragog;

import com.aragog.Aragog;

public class AragogTest {
    /**
     * This test creates a Aragog (which creates Aragog legs) and crawls the web.
     * 
     * @param args
     *            - not used
     */
    public static void main(String[] args) {
        Aragog aragog = new Aragog();
        aragog.getTitlesAndPrices("https://newyork.craigslist.org/search/bka");
    }
}
