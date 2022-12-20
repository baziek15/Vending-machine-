package com.techelevator.view;

import org.junit.Assert;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class VendingMachineTest {

    VendingMachine vendingMachine = new VendingMachine();



    @Test
    public void testCandy() {



    }



    @Test
    public void testfeedMoney() {
        vendingMachine.feedMoney(20);
        Assert.assertEquals(20, vendingMachine.getBalance(), 9);
    }


    @Test
    public void getBalance() {
        vendingMachine.feedMoney(20);
//    assertEquals(10,vendingMachine);
        assertEquals(20.0, vendingMachine.getBalance());
//    assertEquals(11, Example1.addFive(6))
        vendingMachine.feedMoney(5);
        assertEquals(25.0, vendingMachine.getBalance());
        vendingMachine.feedMoney(-500);
        assertEquals(25.0, vendingMachine.getBalance());

    }






}