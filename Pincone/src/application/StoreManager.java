package application;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class StoreManager {
	static Store store;	
	
	private boolean populateStore()
	{
		//TODO retrieve store information from table
		System.out.println("Adding store inventory....");
		// TODO populate
		System.out.println("Success.");
		System.out.println("Updating Par levels.......");
		// TODO populate from HQ par level table
		System.out.println("Success.");
		return true;
	}
	public String sendOrder() throws IOException
	{
		/*
		 * creates a txt file order request | not robustly tested
		 */
		ArrayList<Product> toOrder = store.order();
		File f = new File("Order Request.txt");
		FileWriter fw = new FileWriter(f);
		
		for(int x = 0; x < toOrder.size(); x++)
		{
			System.out.println(toOrder.get(x).getName());
			fw.write(toOrder.get(x).getName() + "\t\t@ " + toOrder.get(x).getQuantity() + " units, \tfor $" + toOrder.get(x).getPrice() + " each\n" );
		}
		fw.flush();
		fw.close();

		return "Order was sent";						// not sent anywhere. toOrder can be used for outputs
	}
	
	public StoreManager(String storeID, String smID)
	{
		store = new Store(storeID, smID);		// Initializes the store
		populateStore();						
	}
	public StoreManager(Store s)
	{
		store = s;		// Initializes the store
		populateStore();						
	}
}
