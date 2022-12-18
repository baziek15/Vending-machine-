package com.techelevator;

import com.techelevator.view.Item;
import com.techelevator.view.Menu;
import com.techelevator.view.SalesReport;
import com.techelevator.view.VendingMachine;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.Set;
public class VendingMachineCLI  {

	private static final String MAIN_MENU_OPTION_DISPLAY_ITEMS = "Display Vending Machine Items";
	private static final String MAIN_MENU_OPTION_PURCHASE = "Purchase";
	private static final String MAIN_MENU_OPTION_EXIT = "EXIT";
	private static final String MAIN_MENU_OPTION_HIDDEN_SALES_REPORT = "(Hidden) Sales Report";
	private static final String[] MAIN_MENU_OPTIONS = {MAIN_MENU_OPTION_DISPLAY_ITEMS, MAIN_MENU_OPTION_PURCHASE, MAIN_MENU_OPTION_EXIT, MAIN_MENU_OPTION_HIDDEN_SALES_REPORT};
	private static final String SECOND_MENU_1 = "Feed Money";
	private static final String SECOND_MENU_2 = "Select Product";
	private static final String SECOND_MENU_3 = "Finish Transaction";
	private static final String[] SECOND_MENU_OPTIONS = {SECOND_MENU_1, SECOND_MENU_2, SECOND_MENU_3};

	List<Item> purchasedObjects = new ArrayList<Item>(); //INITIALISATION LIST TO CONTAIN PRODUCTS

	private Menu menu;

	public VendingMachineCLI(Menu menu) {
		this.menu = menu;
	}

	public void run() throws IOException {
	VendingMachine vendingMachine = new VendingMachine();
		File file = vendingMachine.getInputFile();
		Map<String, Item> inventoryMap = vendingMachine.getInventory(file);

		while (true) {
			String choice = (String) menu.getChoiceFromOptions(MAIN_MENU_OPTIONS, vendingMachine.getBalance());

			if (choice.equals(MAIN_MENU_OPTION_DISPLAY_ITEMS)) {
				// display vending machine items

				while (true) {
					String[] productArray = new String[inventoryMap.size()];
					int ctr = 0;
					Set<Map.Entry<String, Item>> entrySet = inventoryMap.entrySet();
					for (Entry<String, Item> entry : entrySet) {
						String key = entry.getKey();
						Item value = entry.getValue();
						productArray[ctr] = key + " " + value;
						ctr++;
					}
					menu.displayMenuOptionsForItems(productArray);
					break;
				}

			} else if (choice.equals(MAIN_MENU_OPTION_PURCHASE)) {
				// do purchase
				while (true) {
					String choice2 = (String) menu.getChoiceFromOptions(SECOND_MENU_OPTIONS, vendingMachine.getBalance());
					if (choice2.toUpperCase() == "R") {
						break;
					}
					if (choice2.equals(SECOND_MENU_1)) {
						while (true) {
							try {
								System.out.println("             -------------------------------------------------------------\n");
								System.out.println("         Enter amount you would like to feed or Enter(R) when you are done feeding the machine  \n");
								System.out.println("             -------------------------------------------------------------\n");
								Scanner in = new Scanner(System.in);
								String input = in.nextLine();

								if (input.toUpperCase().equals("R")) {
									break;    // dealing with an input error
								} else {
									double amountEntered = Double.parseDouble(input);
									vendingMachine.feedMoney(amountEntered);
									System.out.println("Current balance $" + vendingMachine.getBalance());
								}
							} catch (NumberFormatException e) {
								System.out.println("Machine only accepts: $1's, $2's, $5's, & $10's keke"); // : needs escaped!
							}
						}

					} else if (choice2.equals(SECOND_MENU_2)) {
						while (true) {

							System.out.println("\n Please enter the item you would like to purchase or (R)eturn to previous menu --> \n");

							while (true) {
								String[] productArray = new String[inventoryMap.size()];
								int ctr = 0;
								Set<Map.Entry<String, Item>> entrySet = inventoryMap.entrySet();
								for (Entry<String, Item> entry : entrySet) {
									String key = entry.getKey();
									Item value = entry.getValue();
									productArray[ctr] = key + " " + value;
									ctr++;
								}
								menu.displayMenuOptionsForItems(productArray);
								break;
							}

							Scanner in = new Scanner(System.in);
							String input = in.nextLine();
							String input_Upper_case = input.toUpperCase();

							try {

								if (input.equalsIgnoreCase("R")) {
									break;

								} else if (inventoryMap.containsKey(input_Upper_case)) {
									if (inventoryMap.get(input).isAvailableToPurchase() && vendingMachine.balance >= inventoryMap.get(input).getPrice()) {
										inventoryMap.get(input).purchaseItem();
										purchasedObjects.add(inventoryMap.get(input));
										vendingMachine.balance -= inventoryMap.get(input).getPrice();
										vendingMachine.log(inventoryMap.get(input).getName(),(vendingMachine.balance + inventoryMap.get(input).getPrice()), vendingMachine.balance);

										System.out.println("         " + inventoryMap.get(input).getName() + "  Purchased at :  $" + inventoryMap.get(input).getPrice());
										System.out.println("                     Actual balance :" + vendingMachine.balance);
										System.out.println("        ---------------------------------------------------------------------");

									} else if (!inventoryMap.get(input).isAvailableToPurchase()) {
										System.out.println("                                     SOLD OUT!");
										break;
									} else {
										System.out.println("Insufficient Funds, Please make a deposit    ");
										break;
									}

								} else {
									System.out.println("      << INVALID OPTION PLEASE TRY AGAIN  >>");
									break;
								}
							} catch (NullPointerException e) {
								System.out.println("        ---------------------------------------------------------------------");
								System.out.print("\n                 PLEASE ENTER A VALIDE ITEM CODE , TRY AGAIN\n");
								System.out.println("        ---------------------------------------------------------------------");


							}
						}
					} else if (choice2.equals(SECOND_MENU_3)) {
						vendingMachine.returnChange();
						vendingMachine.logFile();
 				       	vendingMachine.balance = 0;
						System.out.println("Final balance: $" + vendingMachine.balance);
						System.out.println("");

						for (Item product : purchasedObjects) {
							String sound = product.getSound();
							System.out.println(sound);
						}
						break;
					}
				}
			} else if (choice.equals(MAIN_MENU_OPTION_EXIT)) {
				// do
				System.out.println();
				System.out.println("                 ........It's been a pleasure to serve you ......................");
				System.exit(0);


			} else if (choice.equals(MAIN_MENU_OPTION_HIDDEN_SALES_REPORT)) {
				// sales report
				SalesReport.report(inventoryMap);
			}
		}
	}

	public static void main(String[] args) throws IOException{
		Menu menu = new Menu(System.in, System.out);
		VendingMachineCLI cli = new VendingMachineCLI(menu);
		System.out.println("\n        ----------------------------------------------------");
		System.out.println("    Welcome to the  Module 1 Capstone - Vending Machine Software\n");
		System.out.println("                 Kevin BAZIE     &    Yongkon Hahn ");
		System.out.println("\n        ----------------------------------------------------");
		try {
			cli.run();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
}