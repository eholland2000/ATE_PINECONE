package application;

import java.util.ArrayList;

/*
 * A store needs:
 * 	- Fully stock levels
 * 	- Current stock levels
 * 	- Store manager
 */
public class Store {
	private static ArrayList<Store> stores = new ArrayList<Store>();	// ref to all store objects
	private int storeID;												// used to ID + get from tables
	private StoreManager storeManager;									// Ref to sm object
	private ArrayList<Employee> employees = new ArrayList<Employee>();	// workers in store | can only access their store POS
	private ArrayList<Product> products = new ArrayList<Product>();		// Holds values for Product objects ( see MSRB / Sales Prices : ignore base price if(alt price set)) 
																		// not static as each Store object can have a different Product list
	
	/*
	 * DEPRECIATED 
	// Our keys on the hashmap are the SKUs of the products.
	private HashMap<Integer, Integer> fullStock;
	private HashMap<Integer, Integer> currentStock;
	
	*/
	public Store(int storeID) {
		this.storeID = storeID;
				
		// products = FromTable.getStoreProduct(storeID);		// from database
		stores.add(this);							// + to list for indexing
	}
	public Product getProductBySKU(int SKU) {
		for (int i = 0; i < this.products.size(); i++) {
			if (this.products.get(i).getSKU() == SKU)
				return this.products.get(i);
		}
		return null;
	}
	public ArrayList<Product> getProducts() {
		return products;
	}
	
	public static void addStore(Store s) {
		stores.add(s);
	}
	public static boolean removeStore(Store s) {
		return stores.remove(s);
	}
	public static ArrayList<Store> getStores() {
		return stores;
	}
	public static Store getStoreByID(int storeID) {
		for (int i = 0; i < stores.size(); i++) {
			if (stores.get(i).getStoreID() == storeID)
				return stores.get(i);
		}
		return null;
	}
	
	public Employee getEmployee( int id )	throws NullPointerException
	{
		for( Employee e : this.employees )
		{
			if( e.ID == id )
				return e;
		}
		return null;
	}
	public int getStoreID() {
		return this.storeID;
	}
	public void setManager(StoreManager sm) {
		this.storeManager = sm;
	}
	public StoreManager getManager() {
		return this.storeManager;
	}
	
	public void addNewProduct(Product p, int fullStockQuantity, int currentStockQuantity) {
			
		if( getProductBySKU(p.getSKU()) == null )
		{ 	// makes a new entry
			p.setStockIn(currentStockQuantity);
			p.setStockPar(fullStockQuantity);
			products.add(p);
		} else {
			updateCurrentStock( p, currentStockQuantity );
		}
	}
	public void updateCurrentStock(Product p, int currentStockQuantity) {
		getProductBySKU(p.getSKU())  .setStockIn(currentStockQuantity);
	}
	public void updateFullStock(Product p, int fullStockQuantity) {
		getProductBySKU(p.getSKU())  .setStockPar(fullStockQuantity);
	}
	public String checkoutCart(ArrayList<Product> cart) {
		/*
		 * Reduces inventory by the static Cart.products
		 */
		for( Product inStore : this.products )
		{	// gets each Product
			for( int x = 0; x < cart.size(); x++)
			{	// check each product against each item in cart
				if( cart.get(x).getSKU() == inStore.getSKU()) 
				{
					/*
					 *  gets Product object in the store
					 *  reduces stockIn level by the amount in the cart
					 *  
					 *  WARNING : if the amount sold > inStock, the sale will process regardless ( as product physically exists, but is not in system )
					 */
					System.out.println(inStore.getStockIn() + "\t\t\t" +  cart.get(x).getStockIn());
					updateCurrentStock( inStore, inStore.getStockIn() - cart.get(x).getStockIn() );
				}
			}
		}
		return "Thank you, enjoy your purchase!";
	}
	
	public String[][] inventoryToPrint() {
		/*
		 * send 2D array to build a row involving a store with JTable
		 * 
		 * [ SKU ] [ NAME ] [ EXPECTED STOCK ] [ CURRENT STOCK ] [ PRICE ]
		 * 
		 */
		String[][] row = new String[this.products.size()][5];		// [row][col]
		
		int insertRow = 0;
		for( Product at : this.products )
		{
			row[insertRow][0] = at.getSKU() + "";
			row[insertRow][1] = at.getName();
			row[insertRow][2] = at.getStockPar() + "";
			row[insertRow][3] = at.getStockIn()  + "";
			row[insertRow][4] = at.getPrice() + "";
			insertRow++;
		}
		
		return row;
	}
	public String[][] createRestockOrder() {
		// [ SKU ][ NAME ][ ORDERING ]
		String[][] row = new String[this.products.size()][3];		// [row][col]
		
		int insertRow = 0;
		for( Product at : this.products )
		{
			row[insertRow][0] = at.getSKU() + "";
			row[insertRow][1] = at.getName();
			row[insertRow][2] = (at.getStockPar() - at.getStockIn())+  "";	// auto orders to the Par-in difference
			insertRow++;
		}
		
		return row;
	}
}
