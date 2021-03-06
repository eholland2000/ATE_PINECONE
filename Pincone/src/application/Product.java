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
	private static ArrayList<Product> products = new ArrayList<Product>(); 
	
	// 	static private ArrayList<Product> products = new ArrayList<Product>(); moved to Catalog to track default values
	
	public Product() 
	{
		this.SKU = 0;
		this.name = "";
		this.stockIn = 0;
	}
	public Product( Product p )
	{
		// create product from catalog item
		// "Initializes from template"		& applies StockPar to new object
		new Product(p.getSKU(), p.getPrice(), p.getName(), p.getDesc()).setStockPar( p.getStockPar() );
	}
	public Product(int SKU, String name, int stockIn) 
	{
		//Product initializer for customer order
		this.SKU = SKU;
		this.name = name;
		this.stockIn = stockIn;
	}
	public Product(int SKU, double price, String name) 
	{
		// can be entered without a description of the product 
		this.SKU = SKU;
		this.price = price;
		this.name = name;
		this.desc = "";
	}
	public Product(int SKU, double price, String name, String desc) 
	{
		this.SKU = SKU;
		this.price = price;
		this.name = name;
		this.desc = desc;
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
	
	public void setSKU(int SKU) {
		this.SKU = SKU;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setStockIn(int stockIn) {
		this.stockIn = stockIn;
	}
	public void setStockPar(int stockPar) {
		this.stockPar = stockPar;
	}
	
	static public void setCatalog(Product p)
	{
		/*
		 * Default product
		 */
		products.add(p);
	}
	//Just in case
	public boolean equals(Product p) {
		if (this.SKU == p.getSKU() && this.name == p.getName() && this.price == p.getPrice() && this.desc == p.getDesc())
			return true;
		
		return false;
	}
	
}
