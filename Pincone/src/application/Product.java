package application;

import java.util.ArrayList;

public class Product {
	private int SKU;		// product PK
	private double price;   // $$
	private	String name;	// common name
	private String desc;	// description of the name
	private static ArrayList<Product> products = new ArrayList<Product>();
	
	
	public Product(int SKU, double price, String name, String desc) 
	{
		products.add(this);
		this.SKU = SKU;
		this.price = price;
		this.name = name;
		this.desc = desc;
	}
	public Product(int SKU, double price, String name) 
	{
		// can be entered without a description of the product 
		products.add(this);
		this.SKU = SKU;
		this.price = price;
		this.name = name;
		this.desc = "";
	}

	public static Product getProductBySKU(int SKU) {
		for (int i = 0; i < products.size(); i++) {
			if (products.get(i).getSKU() == SKU)
				return products.get(i);
		}
		return null;
	}
	/* You should never have to use this function
	public static void addProduct(Product p) {
		products.add(p);
	}
	*/
	public static boolean removeProduct(Product p) {
		return products.remove(p);
	}
	public static ArrayList<Product> getProducts() {
		return products;
	}
	public int getSKU()
	{
		return this.SKU;
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
	//Just in case
	public boolean equals(Product p) {
		if (this.SKU == p.getSKU() && this.name == p.getName() && this.price == p.getPrice() && this.desc == p.getDesc())
			return true;
		
		return false;
	}
}
