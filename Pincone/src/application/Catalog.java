package application;

import java.util.ArrayList;

public class Catalog {
	/*
	 * helper class to track HQ entered products
	 */
	static private ArrayList<Product> products = new ArrayList<Product>();
	public Catalog()
	{
	}
	public static ArrayList<Product> getProducts()
	{
		return products;
	}
	public static void addCatalogProduct( Product p )
	{
		products.add(p);
	}
	public Catalog( Product p )
	{
		products.add( p );
	}
	public static Product getProductBySKU(int SKU) {
		for (int i = 0; i < products.size(); i++) {
			if (products.get(i).getSKU() == SKU)
			{
				Product p = products.get(i);
				return p;
			}
		}
		return null;
	}
}
