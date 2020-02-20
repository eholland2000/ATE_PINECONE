package application;

import java.util.ArrayList;

public class Location {
	private String storeID;			// store PK
	private String storeSM;			// SM FPK
	private String wareID;			// warehouse FK | used for closest supplier location | calculated
	
	ArrayList<Product> inventoryIn = new ArrayList<Product>();		// currently at the location | mutable ( SM/ WSM/ POS )
	ArrayList<Product> inventoryPar = new ArrayList<Product>();	// what should be at the location | fixed ( can be changed by HQ )
		
	public Location(String storeID, String storeSM)
	{
		this.storeID = storeID;
		this.storeSM = storeSM;
	}
	
	public void setParProduct(Product p, int amount)
	{
		/*
		 *  used by HQ to set the par level of an item at a location
		 *  Does not check integrity of amount input
		 */
		for(int i = 0; i < this.inventoryPar.size(); i++)
		{
			/*
			 * Check first if the product exists
			 */
			if( this.inventoryPar.get(i).getSKU() == p.getSKU() )
			{
				// if the supplied SKU exist in the store's inventory the amount is updated
				System.out.print( "SKU :" + this.inventoryPar.get(i).getSKU() + " @ " + this.inventoryPar.get(i).getQuantity() + "  -changed to-  ");
						
				p.setQuantity(amount);				// updates amount
				this.inventoryPar.set(i, p);		// adds updated product quantity | removes old instance | does not change in levels as the product already exists in store 
				
				System.out.println( this.inventoryPar.get(i).getQuantity() );
				return;
			} 
		}
		/*
		 * Else; just adds the new product
		 */
		p.setQuantity(amount); 			// updates amount to the sent value
		this.inventoryPar.add(p);		// adds updated amount to par
		
		// creates new p1 to be added to inventory | otherwise the same variable is in both and wont allow for them to be independent
		Product p1 = new Product(p.getSKU(), p.getPrice(), 0, p.getName(), p.getDesc());
		this.inventoryIn.add(p1);		// adds to in
		
		
		System.out.println( "SKU :" + p.getSKU() + " @ " + p.getQuantity() + "  -added to Par level-  ");
	}
	public boolean setInProduct(Product p, int amount)
	{
		/*
		 * Used by SM/ WSM/ E (as POS) to update current in-store inventory
		 * REQUIRED: Product exists in store
		 */
		for(int i = 0; i < this.inventoryPar.size(); i++)
		{
			// finds the Product
			if( this.inventoryIn.get(i).getSKU() == p.getSKU() )
			{
				if( amount < 0 )
				{
					/*
					 * negative values passed reduce the quantity by the amount
					 */
					int level = this.inventoryIn.get(i).getQuantity();
					this.inventoryIn.get(i).setQuantity(level + amount);	// '+' because amount is -
					return true;
				}					
				if( amount >= 0 )
				{
					/*
					 * Positive amounts are assumed to be retrieved from a count/ order and will be updated to the amount passed
					 */
					this.inventoryIn.get(i).setQuantity(amount);
					// update arrays correctly
					return true;
				}
			}
		}
		return false;
	}
	public ArrayList<Product> order()
	{
		/*
		 * Calculates the difference between Par-In and returns the result
		 */
		ArrayList<Product> order = new ArrayList<Product>();
		for(int i = 0; i < this.inventoryPar.size(); i++)
		{
			int orderSKU		 = this.inventoryIn.get(i).getSKU();
			double orderPrice	 = this.inventoryIn.get(i).getPrice();
			String orderName	 = this.inventoryIn.get(i).getName();
			int orderAmount 	 = this.inventoryPar.get(i).getQuantity() - this.inventoryIn.get(i).getQuantity();
			
			System.out.println("*** " + orderAmount);

			Product orderProduct = new Product(orderSKU, orderPrice, orderAmount, orderName, "");	// creates a temp object to be added to the order report | desc is excluded 
			
			order.add(orderProduct);		// adds the needed product
		}
				
		return order;						// returns the list of requested products
	}
	

	public static void main( String[] args )
	{
		Location l = new Location("001", "SM-001");
		Product p  = new Product(0001, 10, 0, "ass");		// product not added by default
		
		l.setParProduct(p, 5);
		l.setParProduct(p, 0);
		l.setParProduct(p, -8);

	}
}
