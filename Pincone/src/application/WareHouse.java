package application;

import java.util.ArrayList;

public class WareHouse {
	/*
	 * pending.get( OrderID );
	 * pending.get( getOrderBy(id) ).get( item Line );
	 */
	private int whID;												// Unique
	private ArrayList<Cart> pending = new ArrayList<Cart>();		// Fetched from HQ on creation 
	private static ArrayList<WareHouse> warehouses = new ArrayList<WareHouse>();
	private ArrayList<Product> products = new ArrayList<Product>();
	
	public Product getProductBySKU(int SKU) {
		for (int i = 0; i < this.products.size(); i++) {
			if (this.products.get(i).getSKU() == SKU)
				return this.products.get(i);
		}
		return null;
	}
	public ArrayList<Product> getProducts() {
		return products;
	}
	public static ArrayList<WareHouse> getWarehouses() {
		return warehouses;
	}
	public void updateCurrentStock(Product p, int currentStockQuantity) {
		getProductBySKU(p.getSKU())  .setStockIn(currentStockQuantity);
	}
	public void updateFullStock(Product p, int fullStockQuantity) {
		getProductBySKU(p.getSKU())  .setStockPar(fullStockQuantity);
	}
	public WareHouse( int id )
	{
		this.whID = id;
		this.pending = HeadQuarters.getPending( this.whID );		
		warehouses.add(this);
	}
	public ArrayList<Cart> getPending() 
	{
		return pending;
	}
	public boolean completeOrder(String OrderId)
	{
		if( HeadQuarters.removePendingOrder( OrderId ) )
		{
			return true; 
		}
		return false;
	}
	public void addNewProduct(Product p, int fullStockQuantity, int currentStockQuantity) {
		
		if( getProductBySKU(p.getSKU()) == null )
		{ 	// makes a new entry
			p.setStockIn(currentStockQuantity);
			p.setStockPar(fullStockQuantity);
			products.add(p);
		} else {
			updateCurrentStock( p, currentStockQuantity );
		}
	}
	public void revalidatePending()
	{
		// testing
		this.pending = HeadQuarters.getPending(this.whID);
	}
	public int getwhID()
	{
		return this.whID;
	}
	public void pendingAdd(ArrayList<Cart> pending) 
	{
		// testing
		this.pending = pending;
	}
	public static WareHouse getWarehouseByID(int whID) {
		for (int i = 0; i < warehouses.size(); i++) {
			if (warehouses.get(i).getwhID() == whID)
				return warehouses.get(i);
		}
		return null;
	}
	
}





