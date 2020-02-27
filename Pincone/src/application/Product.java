package application;

public class Product {
	private int SKU;		// product PK
	private double price;   // $$
	private int quantity;	// amount in the system
	private	String name;	// common name
	private String desc;	// description of the name
	
	
	public Product(int SKU, double price, int quan, String name, String desc) 
	{
		this.SKU = SKU;
		this.price = price;
		this.quantity = quan;
		
		this.name = name;
		this.desc = desc;
	}
	public Product(int SKU, double price, int quan, String name) 
	{
		// can be entered without a description of the product 
		this.SKU = SKU;
		this.price = price;
		this.quantity = quan;
		
		this.name = name;
	}

	public int getSKU()
	{
		return this.SKU;
	}
	public int getQuantity()
	{
		return this.quantity;
	}
	public double getPrice()
	{
		return this.price;
	}
	public String getName()
	{
		return this.name;
	}
	public String getDesc()
	{
		return this.desc;
	}

	public void setQuantity( int q )
	{
		this.quantity = q;
	}
}
