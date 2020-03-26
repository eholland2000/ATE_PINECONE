package application;

import java.io.IOException;
import java.util.ArrayList;

import javax.swing.*;

public class Test { }
	//@SuppressWarnings("static-access")			// to get IDE to not complain about weird static variable
	/*public static void main(String[] args) {
	
		/*
		 * Sequence of events
		 * - Program asks which user you are
		 * 		* Employee
		 * 		* Store Manager
		 * 		* HQ
		 * 		* Warehouse
		 * 		* Shutdown
		 * - On selecting employee, it asks you to type in username and storename. (we will automatically populate these instances)
		 * - 
		 *
		
	
		boolean appRunning = true;
		Store store = new Store(0);
		StoreManager storeManager = new StoreManager(store);
		Employee employee = new Employee();
		HeadQuarters hq = new HeadQuarters();
		Customer customer = new Customer();
		storeCart cart = new storeCart();
		
		//This is really janky. Fix in next sprint
		ArrayList<String> storeRestockReports = new ArrayList<String>();
		
		
		
		store.addNewProduct(new Product(0, 20.00, "Winter Hat", "Fluffy hat with puffball"), 90, 90);	// always return true ( will  not return false unless misused )
		store.addNewProduct(new Product(1, 10.00, "Gloves"), 10, 10);
		store.addNewProduct(new Product(2, 99.99, "Coat", "Waterproof, windproof, and very warm"), 200, 200);
		
		while (appRunning) {
			Object[] options = {"Store Manager","WareHouse","Store Employee","HQ", "Shut Down"};
			int n = JOptionPane.showOptionDialog(null, "Click a role to login:",
					"FastFit Information System",
					JOptionPane.YES_NO_CANCEL_OPTION,
					JOptionPane.QUESTION_MESSAGE,
					null,
					options,
					options[4]);
				
				switch (n) {
				case 0: // Store Manager
					boolean runningSM = true;
					while (runningSM) {
						Object[] SMOptions = {"Add Item Inventory", "Print Inventory Report", "Logout"};
						//functionNo cuz we provide them functions here
						int functionNo = JOptionPane.showOptionDialog(null, "Greetings, Store Manager. Select an option",
								"FastFit Information System - Store Manager",
								JOptionPane.YES_NO_CANCEL_OPTION,
								JOptionPane.QUESTION_MESSAGE,
								null,
								SMOptions,
								SMOptions[2]);
						switch (functionNo) {
						case 0: // Add Item Inventory
							//(sku, price, quantity, name, description), inventory
							//sm.store.setParProduct(new Product(0, 20, 10, "ass", "see name"), 8);
							JTextField sku = new JTextField(5);
							JTextField price = new JTextField(5);
							JTextField name = new JTextField(5);
							JTextField description = new JTextField(5);
							JTextField fullStock = new JTextField(5);
							JTextField currentStock = new JTextField(5);
							  
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
							p.add(new JLabel("Expected Fully Stocked Quantity: "));
							p.add(fullStock);
							p.add(new JLabel("Current Quantity: "));
							p.add(currentStock);
							int result = JOptionPane.showConfirmDialog(null, p, 
							         "Please Enter Item Information", JOptionPane.OK_CANCEL_OPTION);
							if (result == JOptionPane.OK_OPTION) {
							   System.out.println("sku: " + sku.getText());
							   System.out.println("price: " + price.getText());
							  
							   try {
								   store.addNewProduct(new Product(Integer.parseInt(sku.getText()), Double.parseDouble(price.getText()), 
									    name.getText(), description.getText()), Integer.parseInt(fullStock.getText()), Integer.parseInt(currentStock.getText()));
								   
								   JOptionPane.showMessageDialog(null, "Product successfully added!");
							   } catch (NumberFormatException e) {
								   JOptionPane.showMessageDialog(null, "Error. One of your inputs are invalid!");
							   }
							}
							  
							break;
						case 1: //Print Inventory Report
							// used this as a test button 
							JOptionPane.showMessageDialog(null, store.printInventory());
							break;
						case 2: //Logout
							runningSM = false;
							break;
						}
					}
					break;
				case 1: //WH Manager
					boolean runningWM = true;
					while (runningWM) {
						Object[] WMOptions = {"View Open Store Orders", "Logout"};
						int functionNo = JOptionPane.showOptionDialog(null, "Greetings, Warehouse manager. Select an option",
								"A Silly Question",
								JOptionPane.YES_NO_CANCEL_OPTION,
								JOptionPane.QUESTION_MESSAGE,
								null,
								WMOptions,
								WMOptions[1]);
						switch (functionNo) {
						case 0:
							String r = "";
							for (int i = 0; i < storeRestockReports.size(); i++) {
								r += storeRestockReports.get(i);
							}
							JOptionPane.showMessageDialog(null, r);
						case 1:
							runningWM = false;
							break;
						}
					}
					break;
				case 2: //Store Employee
					boolean runningEmployee = true;
					while (runningEmployee) {
						Object[] employeeOptions = {"Add Items to cart", "View Cart", "Checkout", "Logout"};
						int functionNo = JOptionPane.showOptionDialog(null, "Greetings, Employee. \nCurrent Cart: \n\n" + cart.printCart()
																			 + "\nTotal: $" + cart.getTotalPrice(),
								"FastFit Information System - Store Employee",
								JOptionPane.YES_NO_CANCEL_OPTION,
								JOptionPane.QUESTION_MESSAGE,
								null,
								employeeOptions,
								employeeOptions[1]);
						switch (functionNo) {
						case 0: //Add Items
							JTextField sku = new JTextField(5);
							JTextField quantity = new JTextField(5);
							JPanel p = new JPanel();
							p.setLayout(new BoxLayout(p, BoxLayout.PAGE_AXIS));
							p.add(new JLabel("SKU:"));
							p.add(sku);
							p.add(new JLabel("Quantity:"));
							p.add(quantity);
							int result = JOptionPane.showConfirmDialog(null, p, 
							         "Please Enter the SKU of the item and its quantity you want to add", JOptionPane.OK_CANCEL_OPTION);
							if (result == JOptionPane.OK_OPTION) {
							   // done as System.out for debugging purposes; method call is still functional as normal
								try {
									Product product = Product.getProductBySKU(Integer.parseInt(sku.getText()));
									cart.addProduct(product, Integer.parseInt(quantity.getText()));
									JOptionPane.showMessageDialog(null, "Product successfully added to cart!");
								} catch ( NumberFormatException e ) {
									JOptionPane.showMessageDialog(null, "Please enter a valid SKU");
								}
							}
							break;
						case 1: // View Cart
							JOptionPane.showMessageDialog(null, cart.printCart());
							break;
						case 2: // Checkout
							boolean checkingOut = true;
							while (checkingOut) {
								try {
									/*
									 * TODO Break up inputs into 
									 * 	Card number
									 * 	EXP date
									 * 	CV number/ Pin		
									 * for better readibility 
									 *
									JTextField cardNum = new JTextField(16);
									JTextField expDate = new JTextField(5);
									JTextField securityCode = new JPasswordField(3);
									Object[] message = {
											"Enter the 16-Digit Card Number:", cardNum,
											"Enter the Expiration Date: (MM/YY)", expDate,
											"Enter the 3-Digit Security Code:", securityCode
									};
									//String cardNum = JOptionPane.showInputDialog(null, sm.store.printCart() + "\n, Enter the 16-digit Card Number:");
									//String expDate = JOptionPane.showInputDialog(null, sm.store.printCart() + "\n, Enter the Expiration Date:  <'MM/YY'>");
									//String securityCode = JOptionPane.showInputDialog(null, sm.store.printCart() + "\n, Enter the 3-digit Security Code:");
									int option = JOptionPane.showConfirmDialog(null, message, "Payment", JOptionPane.OK_CANCEL_OPTION);
									if (option == JOptionPane.OK_OPTION) {
										String input = cardNum.getText() + "," + expDate.getText()  + "," + securityCode.getText();
										JOptionPane.showMessageDialog(null, store.placeOrder(input, cart));
										cart.flushCart();
										checkingOut = false;
									} else {
										checkingOut = false;
									}
									// does not preserve old copy | can update to unique named files with "Order Form "+ date.txt
								} catch (Exception e) {
									// should not occur
									JOptionPane.showMessageDialog(null, "Error in sending order");
									e.printStackTrace();
								}
							}
							break;
						case 3:
							runningEmployee = false;
							break;
						}
					}
					break;
				case 3:
					boolean runningHQ = true;
					while (runningHQ) {
						Object[] HQOptions = {"Current Inventory Levels", "Adjust Fully Stocked Levels", "Send Store Report to Warehouse", "Logout"};
						int functionNo = JOptionPane.showOptionDialog(null, "Greetings, HQ. Select an option",
								"A Silly Question",
								JOptionPane.YES_NO_CANCEL_OPTION,
								JOptionPane.QUESTION_MESSAGE,
								null,
								HQOptions,
								HQOptions[2]);
						switch (functionNo) {
						case 0:
							JOptionPane.showMessageDialog(null, Store.printAllStoreInventories());
							break;
						case 1:
							JTextField sku = new JTextField(5);
							JTextField storeID = new JTextField(5);
							JTextField fullyStocked = new JTextField(5);
							  
							JPanel p = new JPanel();
							p.setLayout(new BoxLayout(p, BoxLayout.PAGE_AXIS));
							//p.add(new JLabel(Store.printAllStoreInventories()));
							p.add(new JLabel("SKU:"));
							p.add(sku);
							p.add(new JLabel("Store ID:"));
							p.add(storeID);
							p.add(new JLabel("New Expected Fully Stocked Quantity"));
							p.add(fullyStocked);
							int result = JOptionPane.showConfirmDialog(null, p, 
							         "Update Item stock", JOptionPane.OK_CANCEL_OPTION);
							if (result == JOptionPane.OK_OPTION) {
								if (Store.updateStoreFullStock(Integer.parseInt(storeID.getText()), Integer.parseInt(sku.getText()), Integer.parseInt(fullyStocked.getText()))) {
									JOptionPane.showMessageDialog(null, "Success!");
								} else {
									JOptionPane.showMessageDialog(null, "Error. One of your inputs was invalid. Please try again");
								}
							}
							break;
						case 2:
							p = new JPanel();
							//p.add(new JLabel(Store.printAllStoreInventories()));
							storeID = new JTextField(5);
							p.add(new JLabel("Input store ID that requires restocking:"));
							p.add(storeID);
							result = JOptionPane.showConfirmDialog(null, p, 
							         "Send Store Report to Warehouse", JOptionPane.OK_CANCEL_OPTION);
							if (result == JOptionPane.OK_OPTION) {
								//This is very janky and not OOP. We will need to fix this in the next sprint.
								Store s = Store.getStoreByID(Integer.parseInt(storeID.getText()));
								s.createRestockOrder();
								storeRestockReports.add(s.createRestockOrder());
								JOptionPane.showMessageDialog(null, "Success!");
							}
							break;
						case 3:
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
	}
	
	//OLD PSVM
	/*
	public static void main(String[] args)
	{
		Store s1 = new Store("1", "SM1");
		StoreManager sm = new StoreManager(s1);
		boolean appRunning = true;
		
		// adds dummy inventory
		
		sm.store.setParProduct(new Product(0, 20.00, 90, "Winter Hat", "Fluffy hat with puffball"), 80);	// always return true ( will  not return false unless misused )
		sm.store.setParProduct(new Product(1, 10.00, 10, "Gloves"), 70);
		sm.store.setParProduct(new Product(2, 99.99, 200, "Coat", "Waterproof, windproof, and very warm"), 100);
		
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
			 *//*
			Object[] options = {"Store Manager","WareHouse","Store Employee","HQ", "Shut Down"};
			int n = JOptionPane.showOptionDialog(null, "Select your company role:",
						"FastFit Information System",
						JOptionPane.YES_NO_CANCEL_OPTION,
						JOptionPane.QUESTION_MESSAGE,
						null,
						options,
						options[2]);
			switch (n) {
			case 0: // Store Manager
				boolean runningSM = true;
				while (runningSM) {
					Object[] SMOptions = {"Add Item Inventory", "Print Inventory Report", "Logout"};
					//functionNo cuz we provide them functions here
					int functionNo = JOptionPane.showOptionDialog(null, "Greetings, Store Manager. Select an option",
							"FastFit Information System - Store Manager",
							JOptionPane.YES_NO_CANCEL_OPTION,
							JOptionPane.QUESTION_MESSAGE,
							null,
							SMOptions,
							SMOptions[2]);
					switch (functionNo) {
					case 0: // Add Item Inventory
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
					case 1: //Print Inventory Report
						// used this as a test button 
						JOptionPane.showMessageDialog(null, sm.store.printInLevels() + "\n" + sm.store.printParLevels());
						break;
					case 2: //Logout
						runningSM = false;
						break;
					}
				}
				break;
			case 1: //WH Manager
				boolean runningWM = true;
				while (runningWM) {
					Object[] WMOptions = {"", "", "Logout"};
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
			case 2: //Store Employee
				boolean runningEmployee = true;
				while (runningEmployee) {
					Object[] employeeOptions = {"Add Items", "View Cart", "Checkout", "Logout"};
					int functionNo = JOptionPane.showOptionDialog(null, "Greetings, Employee. \nCurrent Cart: \n\n" + sm.store.printCart()
																		 + "\nTotal: $" + sm.store.totalPriceString(),
							"FastFit Information System - Store Employee",
							JOptionPane.YES_NO_CANCEL_OPTION,
							JOptionPane.QUESTION_MESSAGE,
							null,
							employeeOptions,
							employeeOptions[1]);
					switch (functionNo) {
					case 0: //Add Items
						JTextField sku = new JTextField(5);
						JPanel p = new JPanel();
						p.setLayout(new BoxLayout(p, BoxLayout.PAGE_AXIS));
						p.add(new JLabel("SKU:"));
						p.add(sku);
						int result = JOptionPane.showConfirmDialog(null, p, 
						         "Please Enter the SKU of the item you want to add", JOptionPane.OK_CANCEL_OPTION);
						if (result == JOptionPane.OK_OPTION) {
						   // done as System.out for debugging purposes; method call is still functional as normal
							try {
								System.out.println(sm.store.buildCart(Integer.parseInt(sku.getText())));
								JOptionPane.showMessageDialog(null, "Product successfully added to cart!");
							} catch ( NumberFormatException e ) {
								JOptionPane.showMessageDialog(null, "Please enter a valid SKU");
							}
						}
						sm.store.printCart();
						break;
					case 1: // View Cart
						JOptionPane.showMessageDialog(null, sm.store.printCart());
						break;
					case 2: // Checkout
						boolean checkingOut = true;
						while (checkingOut) {
							try {
								/*
								 * TODO Break up inputs into 
								 * 	Card number
								 * 	EXP date
								 * 	CV number/ Pin		
								 * for better readibility 
								 *//*
								JTextField cardNum = new JTextField(16);
								JTextField expDate = new JTextField(5);
								JTextField securityCode = new JPasswordField(3);
								Object[] message = {
										"Enter the 16-Digit Card Number:", cardNum,
										"Enter the Expiration Date: (MM/YY)", expDate,
										"Enter the 3-Digit Security Code:", securityCode
								};
								//String cardNum = JOptionPane.showInputDialog(null, sm.store.printCart() + "\n, Enter the 16-digit Card Number:");
								//String expDate = JOptionPane.showInputDialog(null, sm.store.printCart() + "\n, Enter the Expiration Date:  <'MM/YY'>");
								//String securityCode = JOptionPane.showInputDialog(null, sm.store.printCart() + "\n, Enter the 3-digit Security Code:");
								int option = JOptionPane.showConfirmDialog(null, message, "Payment", JOptionPane.OK_CANCEL_OPTION);
								if (option == JOptionPane.OK_OPTION) {
									String input = cardNum.getText() + "," + expDate.getText()  + "," + securityCode.getText();
									JOptionPane.showMessageDialog(null, sm.store.placeOrder(input));
									sm.store.flushCart();
									checkingOut = false;
								} else {
									checkingOut = false;
								}
								// does not preserve old copy | can update to unique named files with "Order Form "+ date.txt
							} catch (Exception e) {
								// should not occur
								JOptionPane.showMessageDialog(null, "Error in sending order");
								e.printStackTrace();
							}
						}
						break;
					case 3:
						runningEmployee = false;
						break;
					}
				}
				break;
			case 3:
				boolean runningHQ = true;
				while (runningHQ) {
					Object[] HQOptions = {"Add item inventory par at this store", "Set item inventory par at this store", "Add a store location", "Set store location item inventory", "Logout"};
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
		*
	//} END OLD PSVM
}
*/