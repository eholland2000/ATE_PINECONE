package application;

import java.util.ArrayList;
import java.util.HashMap;

public class Cart {
	private ArrayList<Product> products;
	private HashMap<Integer, Integer> quantity;
	public Cart() {
		quantity = new HashMap<Integer, Integer>();
		products = new ArrayList<Product>();
		
	}

	public void addProduct(Product p, int quantity) {
		products.add(p);
		this.quantity.put(p.getSKU(), quantity);
	}
	public void removeProduct(Product p, int quantity) {
		products.remove(p);
		this.quantity.remove(p.getSKU());
	}
	public String printCart() {
		String total = "";
		total += "---------INVENTORY REPORT---------\n";
		total += "ITEM: QUANTITY\n";
		for (int i = 0; i < products.size(); i++) {
			total += products.get(i).getName() + ": " + quantity.get(products.get(i).getSKU()) + "\n";
		}
		
		return total;
	}
	
	public HashMap<Integer, Integer> getQuantities() {
		return this.quantity;
	}
	public ArrayList<Product> getProducts() {
		return this.products;
	}
	
	public void flushCart() {
		quantity = new HashMap<Integer, Integer>();
		products = new ArrayList<Product>();
	}
	public Double getTotalPrice() {
		Double runningTotal = 0.00;
		for (int i = 0; i < products.size(); i++) {
			runningTotal += products.get(i).getPrice() * quantity.get(products.get(i).getSKU());
		}
		return runningTotal;
	}
	
	
}
