package application;

import java.util.ArrayList;

public class HeadQuarters {
	static ArrayList <Store> Stores = Store.getStores();				// 	all stores from launcher
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
		Pending = new ArrayList <Cart>();
		
		Cart cart = new Cart( "W0-S0" );
		Product hat    = new Product(0, 20.00, "Winter Hat", "Fluffy hat with puffball");
		Product gloves = new Product(1, 10.00, "Gloves");
		Product coat   = new Product(2, 99.99, "Coat", "Waterproof, windproof, and very warm");
		
		Cart cart1 = new Cart( "W0-S9" );
		
		cart.setProduct(hat, 50);
		cart.setProduct(gloves, 50);
		cart.setProduct(coat, 50);
		
		cart1.setProduct(hat, 20);
		cart1.setProduct(gloves, 2130);
		cart1.setProduct(coat, 5);

		Pending.add( cart  );
		Pending.add( cart1 );
	}
	static ArrayList<Cart> getPending( int ID ) 
	{
		ArrayList<Cart> wareHouse = new ArrayList<Cart>();
		for( Cart cIL : Pending )
		{
			if( cIL.getFrom().equals( "W"+ID)  )
			{
				// sends back Cart object
				wareHouse.add( cIL );
			}
		}
		return wareHouse;
	}
	public static void removePendingOrder( String OrderId ) {
		int index = 0;
		for( Cart c : Pending )
		{
			if( c.getOrderID().equals(OrderId) )
			{
				Pending.remove(index);
			}
			index++;
		}		
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
