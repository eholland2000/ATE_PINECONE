package application;

import java.util.ArrayList;

public class Store extends Location {
	private ArrayList<Product> cart = new ArrayList<Product>();
		
	public Store(String storeID, String storeSM) 
	{
		super(storeID, storeSM);
		// TODO Auto-generated constructor stub
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
	private boolean updateProducts(ArrayList<Product> cart2)
	{
		for(int x = 0; x < cart2.size(); x++)
		{
			this.setInProduct( cart2.get(x), -1* cart2.get(x).getQuantity() );		// Quantity *-1 to reduce total quantity in store | NOT USED FOR REFUNDS
			
		}
		return true;
	}
	
	public void printInLevels()
	{
		for(int x =0; x < this.inventoryIn.size(); x++)
		{
			System.out.println(this.inventoryIn.get(x).getName() + " has " + this.inventoryIn.get(x).getQuantity());
		}
	}
	public void printParLevels()
	{
		for(int x =0; x < this.inventoryPar.size(); x++)
		{
			System.out.println(this.inventoryPar.get(x).getName() + " has " + this.inventoryPar.get(x).getQuantity());
		}
	}
	public String buildCart(int SKU)
	{
		/*
		 * Essentially the POS add item function
		 * on call the item passed is checked against store inventory
		 * Does not update inventory until order is placed
		 */
		for(int i = 0; i < this.inventoryPar.size(); i++)
		{
			/*
			 * Check first if the product exists within the store
			 */
			if( this.inventoryIn.get(i).getSKU() == SKU && this.inventoryIn.get(i).getQuantity() > 0)
			{
				for(int x = 0; x < cart.size(); x++)
				{
					/*
					 * Checks if the product is in the cart | if so quantity is increased
					 */
					if( this.cart.get(x).getSKU() == SKU )
					{
						Product p = this.inventoryIn.get(x);
						p.setQuantity( p.getQuantity() + 1);		// increases quantity by 1
						
						this.cart.add(p);
						return "Success: Product quantity was updated";
					}				
					
				}
				Product p = this.inventoryIn.get(i);
				p.setQuantity(1);
				return "Success: Product was added to the cart";
			}
		}
		return "Error: We could not find this Product";
	}
	public String placeOrder(String payment)
	{
		// Payment is a credit card input in "<Number(16)>, <expire date in 'MM/YY'>, <pin(3)>" format | see processPayment()
		if( processPayment(payment) )
		{
			if( updateProducts( this.cart ))
			{
				return "Success: enjoy your purcahse";
			}
		}
		return "Error: something went wrong, please try again";
	}
	public String addCustomer(String name, String cell, String address)
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
