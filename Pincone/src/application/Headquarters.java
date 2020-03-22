package application;

import java.util.ArrayList;

public class HeadQuarters {
	ArrayList <Store> Stores = new ArrayList <Store>();
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
