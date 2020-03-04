package application;

import java.util.ArrayList;

public class Employee {
	public static ArrayList<Employee> employees;
	public void addEmployee(Employee e) {
		employees.add(e);
	}
	public boolean removeEmployee(Employee e) {
		return employees.remove(e);
	}
	public ArrayList<Employee> getEmployees() {
		return this.employees;
	}
	

}
