package application;

import java.io.IOException;
import javax.swing.*;

public class Test {
	@SuppressWarnings("static-access")			// to get IDE to not complain about weird static variable
	public static void main(String[] args)
	{
		Store s1 = new Store("1", "SM1");
		StoreManager sm = new StoreManager(s1);
		boolean appRunning = true;
		
		// adds dummy inventory
		
		sm.store.setParProduct(new Product(0, 20, 10, "hat", "see name"), 8);	// always return true ( will  not return false unless misused )
		sm.store.setParProduct(new Product(1, 1, 0, "glove"), 69);
		sm.store.setParProduct(new Product(2, 4.20, 420, "Coat", "smelly"), 420);
		
		while (appRunning) {
			/*
			 * I want to make this into a JFrame Object instead of a series of JOptionPanes
			 * 
			 * Launcher.java : New class file will fetch data from DB, Populates application with Product templates + Store InInventory levels
			 * 
			 * Application Launch/ Run sequence
			 *  1) Launcher.java							// holds "main( String[] args)"
			 *  2) Launcher calls fetch request from DB		
			 *  3) Populates retrieved into Product/ StorePar ArrayList		// held in Launcher
			 *  4) JFrame built
			 *  5) Log-in Page
			 *  	StoreManager
			 *  		Assign POS	:	Employee login function ( TODO implement )
			 *  		
			 *  	WareHouseStoreManager
			 *  	HeadQuarters
			 *  	
			 *  
			 */
			Object[] options = {"SM","WSM","E","HQ", "Turn off"};
			int n = JOptionPane.showOptionDialog(null, "Select your company role:",
						"A Silly Question",
						JOptionPane.YES_NO_CANCEL_OPTION,
						JOptionPane.QUESTION_MESSAGE,
						null,
						options,
						options[2]);
			switch (n) {
			case 0:
				boolean runningSM = true;
				while (runningSM) {
					Object[] SMOptions = {"Add item inventory", "Set item inventory", "Logout"};
					//functionNo cuz we provide them functions here
					int functionNo = JOptionPane.showOptionDialog(null, "Greetings, Store Manager. Select an option",
							"A Silly Question",
							JOptionPane.YES_NO_CANCEL_OPTION,
							JOptionPane.QUESTION_MESSAGE,
							null,
							SMOptions,
							SMOptions[2]);
					switch (functionNo) {
					case 0:
						//(sku, price, quantity, name, description), inventory
						//sm.store.setParProduct(new Product(0, 20, 10, "ass", "see name"), 8);
						JTextField sku = new JTextField(5);
						JTextField price = new JTextField(5);
						JTextField name = new JTextField(5);
						JTextField description = new JTextField(5);
						JTextField inventory = new JTextField(5);
						JTextField quantity = new JTextField(5);
						  
						JPanel p = new JPanel();
						p.setLayout(new BoxLayout(p, BoxLayout.PAGE_AXIS));
						p.add(new JLabel("SKU:"));
						p.add(sku);
						p.add(new JLabel("Price:"));
						p.add(price);
						p.add(new JLabel("Name: "));
						p.add(name);
						p.add(new JLabel("Description"));
						p.add(description);
						p.add(new JLabel("Quantity in Store:"));
						p.add(quantity);
						p.add(new JLabel("Total in Inventory: "));
						p.add(inventory);
						
						int result = JOptionPane.showConfirmDialog(null, p, 
						         "Please Enter Item Information", JOptionPane.OK_CANCEL_OPTION);
						if (result == JOptionPane.OK_OPTION) {
						   System.out.println("sku: " + sku.getText());
						   System.out.println("price: " + price.getText());
						  
						   try {
							   sm.store.setInProduct(new Product(Integer.parseInt(sku.getText()), Double.parseDouble(price.getText()), 
								   Integer.parseInt(quantity.getText()), name.getText(), description.getText()), 
								   Integer.parseInt(inventory.getText()));
							   
							   JOptionPane.showMessageDialog(null, "Product successfully added!");
						   } catch (NumberFormatException e) {
							   JOptionPane.showMessageDialog(null, "Error");
						   }
						}
						  
						//(sku, price, quantity, name, description), inventory
						//sm.store.setParProduct(new Product(0, 20, 10, "ass", "see name"), 8);
						break;
					case 1:
						// used this as a test button 
						sm.store.printParLevels();
						sm.store.printInLevels();
						break;
					case 2:
						runningSM = false;
						break;
					}
				}
				break;
			case 1:
				boolean runningWM = true;
				while (runningWM) {
					Object[] WMOptions = {"Add item inventory par at this store", "Set item inventory par at this store", "Logout"};
					int functionNo = JOptionPane.showOptionDialog(null, "Greetings, Warehouse manager. Select an option",
							"A Silly Question",
							JOptionPane.YES_NO_CANCEL_OPTION,
							JOptionPane.QUESTION_MESSAGE,
							null,
							WMOptions,
							WMOptions[2]);
					switch (functionNo) {
					case 2:
						runningWM = false;
						break;
					}
				}
				break;
			case 2:
				boolean runningEmployee = true;
				while (runningEmployee) {
					Object[] employeeOptions = {"Build Your Cart", "Check Cart", "Checkout", "Logout"};
					int functionNo = JOptionPane.showOptionDialog(null, "Greetings, employee. Select an option",
							"A Silly Question",
							JOptionPane.YES_NO_CANCEL_OPTION,
							JOptionPane.QUESTION_MESSAGE,
							null,
							employeeOptions,
							employeeOptions[1]);
					switch (functionNo) {
					case 0:
						JTextField sku = new JTextField(5);
						JPanel p = new JPanel();
						p.setLayout(new BoxLayout(p, BoxLayout.PAGE_AXIS));
						p.add(new JLabel("SKU:"));
						p.add(sku);
						int result = JOptionPane.showConfirmDialog(null, p, 
						         "Please Enter the SKU of the item you want to add", JOptionPane.OK_CANCEL_OPTION);
						if (result == JOptionPane.OK_OPTION) {
						   // done as System.out for debugging purposes; method call is still functional as normal
							System.out.println(sm.store.buildCart(Integer.parseInt(sku.getText())));
							JOptionPane.showMessageDialog(null, "Product successfully added to cart!");
						}
						sm.store.printCart();
						break;
					case 1:
						JOptionPane.showMessageDialog(null, sm.store.printCart());
						//command to flush cart
						sm.store.flushCart();
						break;
					case 2:
						try {
							//sm.store.placeOrder(payment);
							JOptionPane.showMessageDialog(null, sm.sendOrder());				// does not preserve old copy | can update to unique named files with "Order Form "+ date.txt
						} catch (IOException e) {
							// should not occur
							JOptionPane.showMessageDialog(null, "Error in sending order");
							e.printStackTrace();
						}
					case 3:
						runningEmployee = false;
						break;
					}
				}
				break;
			case 3:
				boolean runningHQ = true;
				while (runningHQ) {
					Object[] HQOptions = {"Add a store location", "Set store location item inventory", "Logout"};
					int functionNo = JOptionPane.showOptionDialog(null, "Greetings, HQ. Select an option",
							"A Silly Question",
							JOptionPane.YES_NO_CANCEL_OPTION,
							JOptionPane.QUESTION_MESSAGE,
							null,
							HQOptions,
							HQOptions[2]);
					switch (functionNo) {
					case 0:
						break;
					case 2:
						runningHQ = false;
						break;
					}
				}
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
		
		// Par must be set first
		//(sku, price, quantity, name, description), inventory
		/*
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
		*/
	}
}
