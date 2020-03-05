package application;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.HashMap;

/*
 * A store needs:
 * 	- Fully stock levels
 * 	- Current stock levels
 * 	- Store manager
 */
public class Store {
	private static ArrayList<Store> stores = new ArrayList<Store>();
	private StoreManager storeManager;
	private ArrayList<Product> products;
	//Our keys on the hashmap are the SKUs of the products.
	private HashMap<Integer, Integer> fullStock;
	private HashMap<Integer, Integer> currentStock;
	
	public Store() {
		products = new ArrayList<Product>();
		fullStock = new HashMap<Integer, Integer>();
		currentStock = new HashMap<Integer, Integer>();
		stores.add(this);
	}
	
	public static void addStore(Store s) {
		stores.add(s);
	}
	
	public static boolean removeStore(Store s) {
		return stores.remove(s);
	}
	
	public static ArrayList<Store> getStores() {
		return stores;
	}
	
	public void setManager(StoreManager sm) {
		this.storeManager = sm;
	}
	
	public StoreManager getManager() {
		return this.storeManager;
	}
	
	public void addNewProduct(Product p, int fullStockQuantity, int currentStockQuantity) {
		products.add(p);
		fullStock.put(p.getSKU(), fullStockQuantity);
		currentStock.put(p.getSKU(), currentStockQuantity);
	}
	
	public void updateCurrentStock(Product p, int currentStockQuantity) {
		currentStock.replace(p.getSKU(), currentStockQuantity);
	}
	
	public void updateFullStock(Product p, int fullStockQuantity) {
		fullStock.replace(p.getSKU(), fullStockQuantity);
	}
	
	public boolean removeProduct(Product p) {
		fullStock.remove(p.getSKU());
		currentStock.remove(p.getSKU());
		return products.remove(p);
	}
	public String placeOrder(String paymentInfo, Cart c) {
		checkoutCart(c);
		String[] card = paymentInfo.split(",");
				
		try {
		if( card[0].length() == 16 )
		{
			if( card[1].length() == 5 )
			{
				if( card[2].length() == 3 )
				{
					return "Payment successfully processed.";
				} } }
		} catch ( IndexOutOfBoundsException e) {
			return "Error, please try again."; 
		}
		return "Error, please try again.";
	}
	public void checkoutCart(Cart c) {
		ArrayList<Product> p = c.getProducts();
		HashMap<Integer, Integer> q = c.getQuantities();
		//get the products and subtract the quantity of the cart from our currentStock hashmap
		for (int i = 0; i < p.size(); i++) {
			currentStock.replace(p.get(i).getSKU(), currentStock.get(p.get(i).getSKU()) - q.get(p.get(i).getSKU()));
		}
	}
	
	public String printInventory() {
		String total = "";
		
		total += "---------INVENTORY REPORT---------\n";
		total += "ITEM | CURRENT STOCK | EXPECTED STOCK\n";
		for (int i = 0; i < products.size(); i++) {
			total += products.get(i).getName() + ": " + currentStock.get(products.get(i).getSKU()) + ", " + currentStock.get(products.get(i).getSKU()) + "\n";
		}
		
		return total;
	}
	
	
}
