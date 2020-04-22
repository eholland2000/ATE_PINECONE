package application;

import java.util.ArrayList;

public class CustomerOrder {
	private String orderID;
	private String orderDateTime;
	private String status;
	private String address1;
	private String address2;
	private String city;
	private String state;
	private String zip;
	private ArrayList<Product> items;
	
	public CustomerOrder() {
		orderID = "";
		orderDateTime = "";
		status = "";
		address1 = "";
		address2 = "";
		zip = "";
		state = "";
		items = new ArrayList<>();
	}
	
	public String getOrderID() {
		return orderID;
	}
	public String getOrderDateTime() {
		return orderDateTime;
	}
	public String getStatus() {
		return status;
	}
	public String getAddress1() {
		return address1;
	}
	public String getAddress2() {
		return address2;
	}
	public String getCity() {
		return city;
	}
	public String getZip() {
		return zip;
	}
	public String getState() {
		return state;
	}
	public ArrayList<Product> getItems() {
		return items;
	}
	public void setOrderID(String id) {
		this.orderID = id;
	}
	public void setOrderDateTime(String date) {
		this.orderDateTime = date;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public void setAddress1(String address) {
		this.address1 = address;
	}
	public void setAddress2(String address) {
		this.address2 = address;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public void setZip(String zip) {
		this.zip = zip;;
	}
	public void setState(String state) {
		this.state = state;
	}
	public void setItems(ArrayList<Product> items) {
		this.items = items;
	}
	public void addProduct(Product p) {
		items.add(p);
	}
	
}
