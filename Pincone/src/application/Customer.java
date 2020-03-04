package application;

import java.util.ArrayList;

public class Customer {
	private static ArrayList<Customer> customers;
	private String firstName;
	private String lastName;
	private String DOB;
	public Customer(String firstName, String lastName, String DOB) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.DOB = DOB;
	}
	public void addCustomer(Customer customer) {
		customers.add(customer);
	}
	public ArrayList<Customer> getCustomers() {
		return this.customers;
	}
	public boolean removeCustomer(Customer customer) {
		return customers.remove(customer);
	}

}
