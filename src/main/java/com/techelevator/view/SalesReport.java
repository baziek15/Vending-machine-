package com.techelevator.view;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;


public class SalesReport {
    private static final Integer INITIAL_NUMBER_OF_ITEM = 5;

    public static void report(Map<String, Item> inventoryMap) {
        LocalDateTime localDateTime = LocalDateTime.now();
        String dateTime = localDateTime.format(DateTimeFormatter.ofPattern("MM-dd-yyyy_HH-mm-ss"));

        double totalSale = 0;
        System.out.println("*** Sales Report (" +dateTime+ ") Created. ***");

        try (PrintWriter reportWriter = new PrintWriter("log/" + dateTime)) {
            for (Map.Entry<String, Item> item : inventoryMap.entrySet()) {

                String  reportLine = String.format("%1$-20s | %2$3s", item.getValue().getName(), (INITIAL_NUMBER_OF_ITEM - item.getValue().getNumberOfItems()));
                reportWriter.println(reportLine);
                if (item.getValue().getNumberOfItems() < 5) {
                    totalSale += (INITIAL_NUMBER_OF_ITEM - item.getValue().getNumberOfItems()) * item.getValue().getPrice();
                }
            }
            reportWriter.println("**TOTAL SALES**" + dollarFormat(totalSale));
        } catch (FileNotFoundException e) {
            System.err.println(e.getMessage());
        }
    }
    public static String dollarFormat(double dollar) {
        return " $"+String.format("%.2f", dollar);
    }
}
