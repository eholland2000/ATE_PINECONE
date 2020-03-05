package application;

import java.util.ArrayList;

public class Employee {
	public static ArrayList<Employee> employees = new ArrayList<Employee>();
	private String firstName;
	private String lastName;
	private String DOB;
	private Cart cart;
	public Employee(String firstName, String lastName, String DOB) {
		employees.add(this);
		this.firstName = firstName;
		this.lastName = lastName;
		this.DOB = DOB;
	}
	
	public Employee() {
		employees.add(this);
		this.firstName = "John";
		this.lastName = "Doe";
		this.DOB = "1970-01-01";
	}
	
	public static void addEmployee(Employee e) {
		employees.add(e);
	}
	public static boolean removeEmployee(Employee e) {
		return employees.remove(e);
	}
	public static ArrayList<Employee> getEmployees() {
		return employees;
	}
	

}
