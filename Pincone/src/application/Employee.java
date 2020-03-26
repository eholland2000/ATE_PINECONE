package application;

import java.util.ArrayList;

public class Employee {
	String firstName;
	String lastName;
	String DOB;
	int ID;
	Cart cart;
	
	public Employee(String firstName, String lastName, String DOB, int id, Store s) {
		this.firstName 	= firstName;
		this.lastName 	= lastName;
		this.DOB 		= DOB;
		this.ID 		= id;
		this.cart       = new Cart( "S"+ s.getStoreID() + "-.C" );

		HeadQuarters.employees.add(this);

	}
	public Employee( Store s ) 
	{
		this.firstName 	= "John";
		this.lastName 	= "Doe";
		this.DOB		= "1970-01-01";
		this.ID 		= 73;
		this.cart       = new Cart( "S"+ s.getStoreID() + "-C" );
		
		HeadQuarters.employees.add(this);
	}
	
	public static boolean removeEmployee(Employee e) {
		return HeadQuarters.employees.remove(e);
	}
}
