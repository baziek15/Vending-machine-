package com.techelevator.view;

import java.text.DecimalFormat;
public abstract class Item {

    private String name;
    private double price;
    private int numberOfItems = 5;


    public Item(String name, double price) {
        this.name = name;
        this.price = price;

    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public abstract String getSound();

    public String toString() {
        DecimalFormat myFormat = new DecimalFormat("#.00");
        String str = String.format(" %1$-20s %2$-10s %3$s", name, "$" + myFormat.format(price), this.getNumberOfItems());
        return str;
    }

    public int getNumberOfItems() {
        return numberOfItems;
    }

    public boolean isAvailableToPurchase() {
        if(this.numberOfItems >= 1) {
            return true;
        }
        return false;
    }

    public void purchaseItem() {
        numberOfItems -= 1;

    }
}
