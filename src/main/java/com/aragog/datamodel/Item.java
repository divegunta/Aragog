package com.aragog.datamodel;

public class Item {

    private String title;

    private double price;

    public Item() {
        this(null, 0.0);
    }

    public Item(Item item) {
        this(item != null ? item.getTitle() : null, item != null ? item.getPrice() : 0.0);
    }

    public Item(String title, double price) {
        super();
        this.title = title != null ? title : "";
        this.price = price;
    }

    public String getTitle() {
        return title;
    }

    public double getPrice() {
        return price;
    }
}
