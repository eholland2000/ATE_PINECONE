package application;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/*
 *
 * Sees store reports
 */
public class StoreManager {
	private Store store;
	
	public StoreManager(Store store) {
		this.store = store;
		store.setManager(this);
		//this.store.setManager(this);
	}
	public Store getStore() {
		return this.store;
	}
}
