package application;

import java.util.ArrayList;

public class Customer {
	private static ArrayList<Customer> customers = new ArrayList<Customer>();
	private String firstName;
	private String lastName;
	private String number;
	private String DOB;
	private Cart cart;
	
	public Customer(String firstName, String lastName, String DOB) {
		customers.add(this);
		this.firstName = firstName;
		this.lastName = lastName;
		this.DOB = DOB;
	}
	public Customer() {
		customers.add(this);
		this.firstName = "Jane";
		this.lastName = "Doe";
		this.DOB = "2038-19-01";
	}
	public static void addCustomer(Customer customer) {
		customers.add(customer);
	}
	public static ArrayList<Customer> getCustomers() {
		return customers;
	}
	public static boolean removeCustomer(Customer customer) {
		return customers.remove(customer);
	}

}
