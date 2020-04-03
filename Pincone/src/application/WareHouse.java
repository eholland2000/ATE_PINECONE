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
}
