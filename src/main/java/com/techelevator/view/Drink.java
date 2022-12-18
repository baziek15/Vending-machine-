package com.techelevator.view;

public class Drink extends Item{
    public Drink(String name, double price) {
        super(name, price);
    }


    public String getSound() {
        return "Glug Glug, Yum!";
    }

}
