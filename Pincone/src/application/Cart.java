package application;

import java.util.ArrayList;
import java.util.HashMap;

public class Cart {
	static ArrayList<Product> products = new ArrayList<Product>() ;		// uses Stock In to track quantity 
	static double total = 0;												// tracks cart total
	
	static public void setProduct(Product p, int quantity) {
		// sets a products quantity regardless of prior entry
		p.setStockIn(quantity);	// updates value of product to be added
		int at = products.indexOf(p);
		
		products.remove(p);		// removes prior instance of 
		
		if( at != -1 )			// if existed prior
			products.add(at, p);
		else 					// Appends to end
			products.add(p);
	}
	static public String[][] cartToPrint() {
		// returns a row value for an item 
		// [ SKU ][ NAME ][ QUANTITY ][ @ AMOUNT ][ LINE TOTAL ]
		String[][] row = new String[products.size()][5];		// [row][col] 
		total     = 0;		// resets total amount
		int insertRow = 0;
		for( Product at : products )
		{
			row[insertRow][0] = at.getSKU() + "";
			row[insertRow][1] = at.getName();
			row[insertRow][2] = at.getStockIn() + "";
			row[insertRow][3] = at.getPrice()  + "";
			row[insertRow][4] = (at.getPrice() * at.getStockIn()) +"";
			insertRow++;
			total += (at.getPrice() * at.getStockIn());
		}
		// end total
		
		return row;
	}
	public static void flushCart() {
		total = 0.00;
		products = new ArrayList<Product>();
	}
	
}
