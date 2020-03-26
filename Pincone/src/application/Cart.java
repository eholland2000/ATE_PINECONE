package application;

import java.util.ArrayList;
import java.util.HashMap;

public class Cart {
	private static ArrayList<Cart> orders = new ArrayList<Cart>();			// every order taken. Voided transactions are still kept record of. Returns shown as negative
	private String orderCode = "";											// "<PATH ID><FROM ID>"-"<PATH ID><TO ID>"
	private String fromCode  = "";											// "<PATH ID><FROM ID>"
	private String toCode    = "";											// "<PATH ID><TO ID>"
	private static int orderIDIndex = 0;									// ID counters
	
	ArrayList<Product> products = new ArrayList<Product>();					// uses Stock In to track quantity
	public double total = 0;												// tracks cart total
	public int orderID  = 0;
	
	public Cart( String path )
	{
		/* ***** PATH ID *****
		 * S   | STORE
		 * C   | CUSTOMER
		 * W   | WAREHOUSE
		 * O   | ONLINE 
		 * 
		 * "<PATH ID><FROM ID>"-"<PATH ID><TO ID>"
		 */
		this.orderCode = path;										// "<PATH ID><FROM ID>"-"<PATH ID><TO ID>"
		this.fromCode  = path.substring(0, path.indexOf('-'));		// "<PATH ID><FROM ID>"
		this.toCode    = path.substring(path.indexOf('-') + 1);		// "<PATH ID><TO ID>"
		this.total	   = 0;
		this.orderID = orderIDIndex++;		// = then ++ | allows for 1 employee : to use 1 cart : many times a day( regardless of time )
		orders.add(this);
	}
	public Cart getCartFrom( String fromCode ) throws NullPointerException
	{
		for( Cart c : orders)
		{
			if( c.fromCode.equals(fromCode) )
				return c;
		}
		return null;
	}
	public String getOrderID()
	{
		return this.orderCode;
	}
	public String getFrom()
	{
		return this.fromCode;
	}
	public String getTo()
	{
		return this.toCode;
	}
	static public int getOrderIDIndex()
	{
		return orderIDIndex;
	}
	public void setProduct(Product p, int quantity) {
		// sets a products quantity regardless of prior entry
		p.setStockIn(quantity);	// updates value of product to be added
		int at = 0;
		while( at < this.products.size() )
		{
			if( this.products.get(at).getSKU() == p.getSKU() )
			{
				this.products.remove(at);
				this.products.add(at, p);
				return;
			}
			at++;
		}
		this.products.add(p);
	}
	public String[][] cartToPrint() {
		// returns a row value for an item 
		// [ SKU ][ NAME ][ QUANTITY ][ @ AMOUNT ][ LINE TOTAL ]
		String[][] row = new String[this.products.size()][5];		// [row][col] 
		total     = 0;		// resets total amount
		int insertRow = 0;
		for( Product at : this.products )
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
	
	public void flushCart( String path )
	{
		// Initializes a new cart to be used
		Cart n = new Cart( path );
		this.products = new ArrayList<Product>();
		this.orderCode= n.orderCode;
		this.fromCode = n.fromCode;
		this.toCode   = n.toCode;
		this.orderID  = n.orderID;
		this.total    = 0;
	}
	public Cart isEqual(String path)	throws NullPointerException
	{
		for( Cart c : orders )
		{
			if(c.getOrderID().equals(path))
				return c;
		}
		return null;
	}
	/*
	 * DEPRECIATED : CREATE NEW CART OBJECT ON WIPE | WILL BE WRITTEN OUT TO DB FILE ON CLOSE
	public void flushCart() {
		this.total = 0.00;
		this.products = new ArrayList<Product>();
	}*/
}
