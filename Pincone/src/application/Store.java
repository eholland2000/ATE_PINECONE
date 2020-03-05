package application;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Formatter;

/*
 * A store needs:
 * 	- Fully stock levels
 * 	- Current stock levels
 * 	- Store manager
 */
public class Store {
	private ArrayList<Store> stores;
	private StoreManager storeManager;
	private ArrayList<Product> fullStock;
	private ArrayList<Product> currentStock;
	public Store(ArrayList<Product> fullStock, ArrayList<Product> currentStock, StoreManager storeManager) {
		this.fullStock = fullStock;
		this.currentStock = currentStock;
		this.storeManager = storeManager;
	}
	public void addStore(Store s) {
		this.stores.add(s);
	}
	public boolean removeStore(Store s) {
		return this.stores.remove(s);
	}
	public ArrayList<Store> getStores() {
		return this.stores;
	}
}
