package application;

import java.util.ArrayList;

public class HeadQuarters {
	static ArrayList<Employee> employees = new ArrayList<Employee>();		// 	all employess from launcher
	static ArrayList<WareHouse> warehouse = new ArrayList<WareHouse>();
	
	private static ArrayList <Cart> Pending = new ArrayList <Cart>();		//	all orders WH > Store
	/*
	 * pending.get( OrderID );
	 * pending.get( getOrderBy(id) ).get( item Line );
	 */
	static void populatePending()
	{
		//TODO : dummy cart
		// "<PATH ID><FROM ID>"-"<PATH ID><TO ID>"
		// Pending = new ArrayList <Cart>();
		
		Cart cart = new Cart( "W0-S0" );
		Product hat    = new Product(0, 20.00, "Winter Hat", "Fluffy hat with puffball");
		Product gloves = new Product(1, 10.00, "Gloves");
		Product coat   = new Product(2, 99.99, "Coat", "Waterproof, windproof, and very warm");
		
		Product hat1    = new Product(0, 20.00, "Winter Hat", "Fluffy hat with puffball");
		Product gloves1 = new Product(1, 10.00, "Gloves");
		Product coat1   = new Product(2, 99.99, "Coat", "Waterproof, windproof, and very warm");
		
		Cart cart1 = new Cart( "W0-S9" );
		
		/*
		 * <in warehouse 0>
		 * W0-S0-0
		 * 
		 * W0-S9-1
		 * W0-S0-3	< IF RESTOCK SENT BY HQ >
		 */
		Cart cart2 = new Cart( "W2-S9" );		// <W2-S9-2> dummy value DOES NOT APPEAR IN WAREHOUSE 0 pending orders
												// proof that Warehouse view only shows pending orders for its matching ID <W#>
		
		
		cart.setProduct(hat, 50);
		cart.setProduct(gloves, 50);
		cart.setProduct(coat, 50);
		
		cart1.setProduct(hat1, 1);
		cart1.setProduct(gloves1, 2130);
		cart1.setProduct(coat1, 5);

		Pending.add( cart  );
		Pending.add( cart1 );
		Pending.add( cart2 );					// test WH-id
	}
	static void addPending( Cart c )
	{
		// adds new pending warehouse order
		Pending.add(c);
	}
	static ArrayList<Cart> getPending( int ID ) 
	{
		ArrayList<Cart> wareHouse = new ArrayList<Cart>();
		for( Cart cIL : Pending )	// Line value of an Order to be sent to store
		{
			if( cIL.getFrom().equals( "W"+ID)  )
			{
				// sends back Cart object
				wareHouse.add( cIL );
			}
		}
		return wareHouse;
	}
	public static boolean removePendingOrder( String OrderId ) {
		int index = 0;
		for( Cart c : Pending )
		{
			if( c.getOrderID().equals(OrderId) )
			{
				Pending.remove(index);
				return true;
			}
			index++;
		}
		return false;
	}
	
	
	
	
	
 	/*
	 * Populated data object
	 *  Holds all information relating to every store/ warehouse
	 *  HQ > Master Product List > Store/Warehouse > Products per Store | Store Managers > Employees
	 *  
	 * 
	 * Required table view of all stores
	 *  selecting store brings up IMS view
	 *  from IMS view HQ can edit values to Par levels only
	 *  Committing the changes will be pushed on next launch from store || on an order request
	 * 
	 * HQ is also sent pending orders to approve
	 *  HQ retrieves them from the table	( can be calculated | see StoreManager.sendOrder)
	 *  approveOrder() sends the order forum to the supplier
	 *  
	 * HQ credentials can login at any store location
	 * 
	 * Warehouses are treated as Stores, just with bigger par levels & less staff
	 */

}
