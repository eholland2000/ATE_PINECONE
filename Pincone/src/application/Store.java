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
	private StoreManager storeManager;
	private ArrayList<Product> fullStock;
	private ArrayList<Product> currentStock;
	public Store(ArrayList<Product> fullStock, ArrayList<Product> currentStock, StoreManager storeManager) {
		this.fullStock = fullStock;
		this.currentStock = currentStock;
		this.storeManager = storeManager;
	}
}
