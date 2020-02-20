package application;

import java.io.IOException;

public class Test {
	Product[] dummyIn = 
						{	//int SKU, int price, int quan, String name, String desc
								new Product(0, 20, 0, "ass", "see name"),
								new Product(1, 1, 0, "tost"),
								new Product(2, 4.20, 0, "weed", "smelly")
						};
	Product[] dummyPar=
						{	//int SKU, int price, int quan, String name, String desc
								new Product(0, 20, 10, "ass", "see name"),
								new Product(1, 1, 69, "tost"),
								new Product(2, 4.20, 420, "weed", "smelly")
						};
	
	public static void main(String[] args)
	{
		Store s1 = new Store("1", "SM1");
		StoreManager sm = new StoreManager(s1);
		
		s1.setInProduct(new Product(0, 20, 0, "ass", "see name"), 0);
		s1.setInProduct(new Product(1, 1, 0, "tost"), 0);
		s1.setInProduct(new Product(2, 4.20, 0, "weed", "smelly"), 0);
		
		s1.setParProduct(new Product(0, 20, 10, "ass", "see name"), 8);
		s1.setParProduct(new Product(1, 1, 0, "tost"), 69);
		s1.setParProduct(new Product(2, 4.20, 420, "weed", "smelly"), 420);
		
		s1.printInLevels();
		s1.printParLevels();
		
		try {
			sm.sendOrder();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
