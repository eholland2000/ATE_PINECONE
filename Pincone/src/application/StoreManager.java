package application;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/*
 *
 * Sees store reports
 */
public class StoreManager extends Employee {
	private Store store;
	
	public StoreManager(Store store) {
		super();
		this.store = store;
		//this.store.setManager(this);
	}
	public Store getStore() {
		return this.store;
	}
}
