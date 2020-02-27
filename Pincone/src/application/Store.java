package application;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Formatter;

public class Store {
	private ArrayList<Product> cart = new ArrayList<Product>();
	static ArrayList<Store> storeList;
	
	private String storeID;			// store PK
	private String storeSM;			// SM FPK
	private String wareID;			// warehouse FK | used for closest supplier location | calculated
	
	ArrayList<Product> inventoryIn = new ArrayList<Product>();		// currently at the location | mutable ( SM/ WSM/ POS )
	ArrayList<Product> inventoryPar = new ArrayList<Product>();		// what should be at the location | fixed ( can be changed by HQ )
		
	
	public Store(String storeID, String storeSM) 
	{
		this.storeID = storeID;
		this.storeSM = storeSM;
	}
	public ArrayList<Store> getStores() 
	{
		return storeList;
	}
	public void addStore(Store e)
	{
		storeList.add(e);
	}
	
	// ----- INVENTORY -----
	private boolean updateProducts(ArrayList<Product> cart2)
	{
		for(int x = 0; x < cart2.size(); x++)
		{
			this.setInProduct( cart2.get(x), -1* cart2.get(x).getQuantity() );		// Quantity *-1 to reduce total quantity in store | NOT USED FOR REFUNDS
		}
		return true;
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
					System.out.println( "SKU :" + this.inventoryIn.get(i).getSKU() + " @ " + this.inventoryIn.get(i).getQuantity() + "  -reduced by-  " + amount);
					
					int level = this.inventoryIn.get(i).getQuantity();
					this.inventoryIn.get(i).setQuantity(level + amount);	// '+' because amount is -
					return true;
				}					
				if( amount >= 0 )
				{
					/*
					 * Positive amounts are assumed to be retrieved from a count/ order and will be updated to the amount passed
					 */
					System.out.println( "SKU :" + this.inventoryIn.get(i).getSKU() + " @ " + this.inventoryIn.get(i).getQuantity() + "  -set to -  " + amount);

					this.inventoryIn.get(i).setQuantity(amount);
					// update arrays correctly
					return true;
				}
			}
		}
		return false;
	}
	public ArrayList<Product> orderStoreProduct()
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
			
			Product orderProduct = new Product(orderSKU, orderPrice, orderAmount, orderName, "");	// creates a temp object to be added to the order report | desc is excluded 
			
			order.add(orderProduct);		// adds the needed product
		}
				
		return order;						// returns the list of requested products
	}
	
	// ----- PRINTERS -----
	public String printInLevels()
	{
		String s = "===== In  Levels =====\n";
		for(int x =0; x < this.inventoryIn.size(); x++)
		{
			s += (this.inventoryPar.get(x).getSKU() + ": " + this.inventoryIn.get(x).getName() + 
									"\t has \t" + this.inventoryIn.get(x).getQuantity()) + "\n";
		}
		s += "===== ========== =====\n";
		
		System.out.println(s);
		return s;
	}
	public String printParLevels()
	{
		String s = "===== Par Levels =====\n";
		for(int x =0; x < this.inventoryPar.size(); x++)
		{
			s += (this.inventoryPar.get(x).getSKU() + ": " + this.inventoryPar.get(x).getName() + 
									"\t is parred at \t" + this.inventoryPar.get(x).getQuantity()) + "\n";
		}
		s += "===== ========== =====\n";

		System.out.println(s);
		return s;
	}
	public String printCart()
	{
		int itemCount = 0;
		for (int a = 0; a < cart.size(); a++) {
			itemCount += this.cart.get(a).getQuantity();
		}
		String combined = "===== =====Item Count: " + itemCount + "===== =====\n";
		for(int x = 0; x < this.cart.size(); x++)
		{
			combined += this.cart.get(x).getName() + " (x" + this.cart.get(x).getQuantity() + ")\n";
		}
		combined += "===== ===================== =====\n";
		return combined;
	}
	public double totalPrice() {
		double sum = 0.0;
		for (int a = 0; a < this.cart.size(); a++) {
			sum += cart.get(a).getPrice() * cart.get(a).getQuantity();
		}	
		return sum;
	}
	public String totalPriceString() {
		StringBuilder builder = new StringBuilder();
		Formatter fmt = new Formatter(builder);
		fmt.format("%.2f", totalPrice());
		return builder.toString();
	}
	// ----- CART -----
	public void flushCart() 
	{
		cart.removeAll(cart);
	}
	public String buildCart(int SKU)
	{
		/*
		 * POS add item function
		 * on call the item passed is checked against store inventory
		 * Does not update inventory until order is placed
		 */
		for(int i = 0; i < this.inventoryPar.size(); i++)
		{
			/*
			 * Check first if the product exists within the store
			 */
			if( this.inventoryIn.get(i).getSKU() == SKU )
			{
				for(int x = 0; x < cart.size(); x++)
				{
					/*
					 * Checks if the product is in the cart | if so quantity is increased
					 */
					if( this.cart.get(x).getSKU() == SKU )
					{
						Product p = this.cart.get(x);
						p.setQuantity( p.getQuantity() + 1);		// increases quantity by 1
						this.cart.set(x, p);
						
						return "Success: Product quantity was updated";
					}				
					
				}
				Product p = this.inventoryIn.get(i);
				Product p1 = new Product(p.getSKU(), p.getPrice(), 1, p.getName(), p.getDesc());		// adds 1 of the item ( starts the cart )
				this.cart.add(p1);
				return "Success: Product was added to the cart";
			}
		}
		return "Error: We could not find this Product";
	}
	private boolean processPayment(String c)
	{
		/*
		 * Card assumed to be entered in "<Number(16)>, <expire date in 'MM/YY'>, <pin(3)>"
		 * if card number length entered is not 16 || pin length is not 3 they payment is not accepted
		 * expire date is not checking for valid date, as long as there is one supplied in <MM/YY> format
		 */
		String[] card = c.split(",");
		
		try {
		if( card[0].length() == 16 )
		{
			if( card[1].length() == 5 )
			{
				if( card[2].length() == 3 )
				{
					return true;
				} } }
		} catch ( IndexOutOfBoundsException e) {
			return false; 
		}
		return false;
	}
	public String placeOrder(String payment) throws Exception
	{
		// POS function to process an Order
		// Payment is a credit card input in "<Number(16)>, <expire date in 'MM/YY'>, <pin(3)>" format | see processPayment()
		if( processPayment(payment) )
		{
			if( updateProducts( this.cart ))
			{
				return "Success! Thank you for shopping at FastFit! + \n" + this.printCart() + "\nTotal: $" + totalPriceString();
			}
		}
		throw new Exception();
	}
	// ----- MISC -----
	public String addCustomer(String name, String cell)
	{
		/*
		 * name : "first last"
		 * cell : xxx-xxx-xxxx
		 * addy : Street city, ST, postal code
		 * 
		 * Dummy method, data is not saved 
		 */
		
		return new String(name + " was sucessfully added");
	}
}
