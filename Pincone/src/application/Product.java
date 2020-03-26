package application;

import java.util.ArrayList;

public class Product {
	/*
	 * Is a Holder object for each store with a valid STORE_PRODUCT entry
	 */
	private int SKU;		// product PK
	private int stockPar;	// amount expected to be in the store ( defined by HQ : used for auto-reorder )
	private int stockIn;	// amount physcially in the store
	private double price;   // $$
	private	String name = "";	// common name
	private String desc;	// description of the name
	
	// 	static private ArrayList<Product> products = new ArrayList<Product>(); moved to Catalog to track default values
	
	public Product(int SKU, double price, String name, String desc) 
	{
		
		this.SKU = SKU;
		this.price = price;
		this.name = name;
		this.desc = desc;
	}
	public Product(int SKU, double price, String name) 
	{
		// can be entered without a description of the product 
		this.SKU = SKU;
		this.price = price;
		this.name = name;
		this.desc = "";
	}
	public int getSKU()
	{
		return this.SKU;
	}
	public int getStockPar() {
		return stockPar;
	}
	public int getStockIn() {
		return stockIn;
	}
	public double getPrice()
	{
		return this.price;
	}
	public String getName()
	{
		return this.name;
	}
	public String getDesc()
	{
		return this.desc;
	}
	
	public Object[] toArray()
	{
		return new Object[] { this.SKU, this.name, this.price };
	}
	
	
	public void setStockIn(int stockIn) {
		this.stockIn = stockIn;
	}
	public void setStockPar(int stockPar) {
		this.stockPar = stockPar;
	}
	
	public static Product getProductBySKU(int SKU) {
		return Catalog.getProductBySKU( SKU );
	}

	//Just in case
	public boolean equals(Product p) {
		if (this.SKU == p.getSKU() && this.name == p.getName() && this.price == p.getPrice() && this.desc == p.getDesc())
			return true;
		
		return false;
	}
	
}
