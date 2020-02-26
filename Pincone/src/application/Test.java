package application;

import java.awt.Component;
import java.io.IOException;
import javax.swing.*;


public class Test {
	@SuppressWarnings("static-access")			// to get IDE to not complain about weird static variable
	public static void main(String[] args)
	{
		boolean appRunning = true;
		
		while (appRunning) {
			Object[] options = {"SM","WSM","E","HQ", "Turn off"};
			int n = JOptionPane.showOptionDialog(null, "Would you like some green eggs to go with that ham?",
						"A Silly Question",
						JOptionPane.YES_NO_CANCEL_OPTION,
						JOptionPane.QUESTION_MESSAGE,
						null,
						options,
						options[2]);
			switch (n) {
			case 0:
				Object[] SMOptions = {"Add item inventory", "Set item inventory", "Logout"};
				//functionNo cuz we provide them functions here
				int functionNo = JOptionPane.showOptionDialog(null, "Greetings, Store Manager. Select an option",
						"A Silly Question",
						JOptionPane.YES_NO_CANCEL_OPTION,
						JOptionPane.QUESTION_MESSAGE,
						null,
						SMOptions,
						SMOptions[2]);
				break;
			case 1:
				Object[] WMOptions = {"Add item inventory", "Set item inventory", "Logout"};
				functionNo = JOptionPane.showOptionDialog(null, "Greetings, Warehouse manager. Select an option",
						"A Silly Question",
						JOptionPane.YES_NO_CANCEL_OPTION,
						JOptionPane.QUESTION_MESSAGE,
						null,
						WMOptions,
						WMOptions[2]);
				break;
			case 2:
				Object[] employeeOptions = {"", "Logout"};
				functionNo = JOptionPane.showOptionDialog(null, "Greetings, employee. Select an option",
						"A Silly Question",
						JOptionPane.YES_NO_CANCEL_OPTION,
						JOptionPane.QUESTION_MESSAGE,
						null,
						employeeOptions,
						employeeOptions[2]);
				break;
			case 3:
				Objcet[] HQOptions = {"Add a store location", "Set store location item inventory", "Logout"};
				functionNo = JOptionPane.showOptionDialog(null, "Greetings, HQ. Select an option",
						"A Silly Question",
						JOptionPane.YES_NO_CANCEL_OPTION,
						JOptionPane.QUESTION_MESSAGE,
						null,
						HQOptions,
						HQOptions[2]);
				break;
			case 4:
				appRunning = false;
				break;
			}
		}
		
		/*
		 * funky way to access the store, but ensures the StoreManager is editing the store they own
		 * 
		 * if set methods aren't called by sm.store, then the sm.sendOrder() will submit the request with its empty store
		 * 
		 * Products are not globally defined (yet) | create a new Product when using product methods that require a product
		 */
		Store s1 = new Store("1", "SM1");
		StoreManager sm = new StoreManager(s1);
		
		// Par must be set first
		sm.store.setParProduct(new Product(0, 20, 10, "ass", "see name"), 8);	// always return true ( will  not return false unless misused )
		sm.store.setParProduct(new Product(1, 1, 0, "tost"), 69);
		sm.store.setParProduct(new Product(2, 4.20, 420, "weed", "smelly"), 420);
		
		// Products can't be added until a Par is set
		sm.store.setInProduct(new Product(0, 20, 8, "ass", "see name"), 5);		// always return true ( will  not return false unless misused )
		sm.store.setInProduct(new Product(1, 1, 0, "tost"), 3);
		sm.store.setInProduct(new Product(2, 4.20, 0, "weed", "smelly"), 0);	
		sm.store.setInProduct(new Product(2, 4.20, 0, "weed", "smelly"), 10);	// updates the passed product to the amount
		sm.store.setInProduct(new Product(2, 4.20, 0, "weed", "smelly"), 80);	
		sm.store.setInProduct(new Product(2, 4.20, 0, "weed", "smelly"), -8);	// passing negative values will reduce the amount in store (idk if useful)
		
		sm.store.printInLevels();		
		sm.store.printParLevels();		
		
		sm.store.buildCart(1);			// has a String return that can be used to return error messages to the user
		sm.store.buildCart(1);
		sm.store.buildCart(1);
		sm.store.buildCart(1);
		
		sm.store.printCart();
		
		System.out.println(sm.store.placeOrder("1111222233334444,01/18,420"));	// send payment as "<Number(16)>, <expire date in 'MM/YY'>, <pin(3)>" for credit, cash returns always true (not tracked)
																				// placing an order automatically updates the inventory levels
		
		sm.store.printInLevels();		// notice level change to negative, and employees can "over-sell" products if they physically have them (results from poorly entered data)

			
		try {
			System.out.println(sm.sendOrder());				// does not preserve old copy | can update to unique named files with "Order Form "+ date.txt
		} catch (IOException e) {
			// should not occur
			e.printStackTrace();
		}
	}
}
