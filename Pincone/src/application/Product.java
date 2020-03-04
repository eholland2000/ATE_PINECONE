package application;

import java.util.ArrayList;

public class Product {
	private int SKU;		// product PK
	private double price;   // $$
	private	String name;	// common name
	private String desc;	// description of the name
	private static ArrayList<Product> products;
	
	
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
	}

	public void addProduct(Product p) {
		products.add(p);
	}
	public boolean removeProduct(Product p) {
		return products.remove(p);
	}
	public ArrayList<Product> getProducts() {
		return this.products;
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
}
