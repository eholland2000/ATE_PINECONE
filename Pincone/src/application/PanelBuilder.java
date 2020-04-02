package application;

import java.awt.Color;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

public class PanelBuilder {
	/*
	 * JFrame defined to fixed resolution > Layouts are absolute ( lazy )
	 */
	static boolean refund = false;
	static public JPanel POS( Store store )
	{
		// Instance of, make POS( Employee object and use it's )
		JPanel panel = new JPanel();
		panel.setName("STORE");
		panel.setBorder(new LineBorder(null, 5));
		panel.setBackground(SystemColor.info);
		panel.setLayout(null);
		
		
		// TODO move to actual 
		Employee test  = new Employee( store );
		String label   ="Employee ID " + test.ID + ": " + test.getLastName() + ", " + test.getFirstName();
		Cart storeCart = test.cart;
		
		JLabel lblOrderId = new JLabel(label);
		lblOrderId.setBounds(30, 15, 224, 35);
		panel.add(lblOrderId);
		
		label = "Transaction code : " + storeCart.getOrderID();
		JLabel lblOrderID = new JLabel(label);
		lblOrderID.setBounds(363, 15, 209, 35);
		panel.add(lblOrderID);
		
		JPanel pane = new JPanel();
		pane.setBackground(new Color(102, 102, 102));
		pane.setBounds(30, 61, 482, 435);
		panel.add(pane);
		pane.setLayout(null);
		
		
		// maps table values
		DefaultTableModel model = new DefaultTableModel(){
		    @Override
		    public boolean isCellEditable(int row, int column)
		    {
		        return column == 2 && row > 0;
		    }
		};  
		model.addColumn("SKU");
		model.addColumn("NAME");
		model.addColumn("QUANTITY");
		model.addColumn("AMOUNT");
		model.addColumn("LINE TOTAL");
		
		DefaultTableColumnModel columnModel = new DefaultTableColumnModel(); 	
			int[] columnsWidth = { 10, 100, 50, 50, 80 };									// DEFINES WIDTH
	        for( int i = 0; i < columnsWidth.length; i++ ) {							
	    		columnModel.addColumn(new TableColumn(i, columnsWidth[i]));
	        }
	        model.addRow( new Object [] {"SKU", "NAME", "QUANTITY", "AMOUNT", "LINE TOTAL"});	// HEADER ROW
		
	    
	    JTable table = new JTable(model);
		table.setBounds(10, 11, 462, 361);
		table.setShowVerticalLines(false);
		pane.add(table);
		
		table.setColumnModel(columnModel);
		
		DefaultTableModel modelTotal = new DefaultTableModel(){
		    @Override
		    public boolean isCellEditable(int row, int column)
		    {
		        return false;
		    }
		}; 
		modelTotal.addColumn("SKU");
		modelTotal.addColumn("NAME");
		modelTotal.addRow(new Object[] {"Total", "$0.00"} );
		DefaultTableColumnModel columnModelT = new DefaultTableColumnModel(); 	
		columnModelT.addColumn(new TableColumn(0, 100));
		columnModelT.addColumn(new TableColumn(1));
		
		JTable total = new JTable(modelTotal);
		total.setBounds(246, 383, 226, 16);
		pane.add(total);
		
		total.setColumnModel(columnModelT);
		
		
		
		JSpinner txtSku = new JSpinner(new SpinnerNumberModel(1, 0, 2, 1) );	// Because SKUs are incremental (design choice) spinner limits input to only valid inputs [ TODO: changes with data integrity ] 
		txtSku.setBounds(674, 61, 86, 20);
		panel.add(txtSku);
		
		JSpinner spinner = new JSpinner(new SpinnerNumberModel(1, 0, 500, 1) );
		spinner.setBounds(770, 61, 55, 20);
		panel.add(spinner);
		
		JLabel lblEnterSku = new JLabel("Enter SKU");
		lblEnterSku.setBounds(577, 65, 70, 14);
		panel.add(lblEnterSku);
		
		JButton btnAddCol = new JButton("add item");
		btnAddCol.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				pane.remove(table);
				
				try { 
					if( !refund )
					{	// transaction is a sale
						int sku = new Integer((int)txtSku.getValue());
						int amt = new Integer((int)spinner.getValue());
						
						Product pSuper = Product.getProductBySKU( sku );		// gets default assigned values
						Product p	   = new Product(sku, pSuper.getPrice(), pSuper.getName(), pSuper.getDesc());	// item in cart; not a unique item in the system
						storeCart.setProduct(p, amt);								// adds to cart
						
						model.setRowCount(1);		// removes all old rows | keeps header (index = 0)
						
						String[][] inCart = storeCart.cartToPrint();
						for(int i = 0; i < inCart.length; i++)
						{
							model.addRow(inCart[i]);
						}
						
						// updates price total
						modelTotal.removeRow(0);
						modelTotal.addRow(new Object[] {"Total", "$" + storeCart.total} );
	
						spinner.setValue(1);
					} else {
						// transaction is a refund | values made negative
						int sku = new Integer((int)txtSku.getValue());
						int amt = new Integer((int)spinner.getValue());
						
						Product pSuper = Product.getProductBySKU( sku );		// gets default assigned values
						Product p	   = new Product(sku, (-pSuper.getPrice()), pSuper.getName(), pSuper.getDesc());	// item in cart; not a unique item in the system
						storeCart.setProduct(p, (-amt));								// adds to cart
						
						model.setRowCount(1);		// removes all old rows | keeps header (index = 0)
						
						String[][] inCart = storeCart.cartToPrint();
						for(int i = 0; i < inCart.length; i++)
						{
							model.addRow(inCart[i]);
						}
						
						// updates price total
						modelTotal.removeRow(0);
						modelTotal.addRow(new Object[] {"Total", "$" + -storeCart.total} );
	
						spinner.setValue(1);
					}
				} catch ( NumberFormatException | NullPointerException e ) {
					txtSku.setValue("Invalid");
				}
				
				pane.add(table);
				panel.revalidate();
				panel.repaint();
			}
		});
		btnAddCol.setBounds(835, 61, 89, 23);
		panel.add(btnAddCol);
		
		JButton btnAddCustomer = new JButton("Add Customer");
		btnAddCustomer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JTextField name   = new JTextField(25);
				JTextField addres = new JTextField(50);
				JTextField phone  = new JTextField(10);
				Object[] options = new Object[] { "Name", name,
												  "Address", addres,
												  "Phone", phone};
				int r = JOptionPane.showConfirmDialog(null, options, "Enter Customer Information", JOptionPane.OK_CANCEL_OPTION);
				if( r == JOptionPane.OK_OPTION ) {
					if( name.getText().length() > 0 && addres.getText().length() > 0 )
						JOptionPane.showMessageDialog(null, "New Customer added successfully");
					else 
						JOptionPane.showMessageDialog(null, "Please fill all fields");
				} 
			}
		});
		btnAddCustomer.setBounds(522, 494, 120, 23);
		panel.add(btnAddCustomer);
			
		JButton btnToggleRefund = new JButton("Refund Mode");
		btnToggleRefund.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if( !refund )
				{ 	// refund will process on submission
					pane.setBackground(new Color(255, 102, 102));
					refund = true;
				} else {			
					pane.setBackground(new Color(102, 102, 102));
					refund = false;
					
				}
				// resets POS view
				storeCart.flushCart("S"+ store.getStoreID() + "-C");		// creates new cart outside of object, then retrieved
				
				model.setRowCount(1);		// removes all old rows | keeps header (index = 0)
				modelTotal.removeRow(0);	// removes old row
				modelTotal.addRow(new Object[] {"Total", "$0.00"} );
			}
		});
		btnToggleRefund.setBounds(522, 524, 120, 23);
		panel.add(btnToggleRefund);
		
		JButton btnCheckOot = new JButton("Check Oot");
		btnCheckOot.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				/*
				 * Any entry customer reward program check
				 */
				JTextField lookup = new JTextField(10);
				Object[] options = new Object[] { "Search Customer by Phone", lookup };
				
				int r = JOptionPane.showConfirmDialog(null, options, "Rewards", JOptionPane.OK_CANCEL_OPTION);
				if( lookup.getText().length() > 0 )
				{
					// customer look up always returns false
					JOptionPane.showMessageDialog(null, "Unable to find a Customer with that number");
				}

				while ( true ) {
					/*
					 * Enter valid payment | or cancel to return to add item view
					 */
					JTextField cardNum = new JTextField(16);
					JTextField expDate = new JTextField(5);
					JTextField securityCode = new JPasswordField(3);
					Object[] message = {
							"Enter the 16-Digit Card Number:", cardNum,
							"Enter the Expiration Date: (MM/YYYY)", expDate,
							"Enter the 3-Digit Security Code:", securityCode
					};

					int option = 1;					// return from payment | allows cancel/ exit to exit payment
					/*
					 * is payment entered valid?
					 */
					option = JOptionPane.showConfirmDialog(null, message, "Payment", JOptionPane.OK_CANCEL_OPTION);		
					
					try{
						new BigInteger(cardNum.getText().replaceAll("\\s", ""));			// all numbers | no spaces	: 16 nums > int ^lim
						Integer.parseInt(securityCode.getText().replaceAll("\\s", ""));		// all numbers | no spaces
						
						// gets entered MM/YYYY
						String date = expDate.getText();
						int mon = Integer.parseInt(date.substring(0, date.indexOf('/')));
						int year = Integer.parseInt(date.substring(date.indexOf('/') + 1));

						// gets local MM/YYYY
						DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/yyyy");
						String today = formatter.format(LocalDate.now());
						int tMon = Integer.parseInt(today.substring(0, today.indexOf('/')));
						int tYear = Integer.parseInt(today.substring(today.indexOf('/') + 1));
						
						if ( option == JOptionPane.OK_OPTION ) 
						{
							if( year <= tYear || (mon <= tMon && year == tYear) )
							{ 	// date invalid 
								throw new NumberFormatException("Date Invalid");
							} 
							
							if ( cardNum.getText().length() == 16 &&  securityCode.getText().length() == 3 ) {
								// card length & pin length test | date | valid entry
									// "Reciept"
								String reciept = "";
								String[][] cart = storeCart.cartToPrint();
								for( String[] line : cart )
								{
									// Compiles cart to reciept
									reciept += line[1] +" -@"+ line[2] +" : $"+ line[3] +" each for subtotal $"+ line[4] + "\n";
								}
								reciept += "-------- ---------------";
								if( !refund )
									reciept += "\nTotal: $" + storeCart.total;
								else
									reciept += "\nTotal: $" + -storeCart.total;
								JOptionPane.showMessageDialog(null, reciept + "\n\n" + store.checkoutCart( storeCart.getProducts() ));
								
								storeCart.flushCart("S"+ store.getStoreID() + "-C");		// creates new cart outside of object, then retrieved
								
								model.setRowCount(1);		// removes all old rows | keeps header (index = 0)
								
								modelTotal.removeRow(0);	// removes old row
								modelTotal.addRow(new Object[] {"Total", "$0.00"} );
								
								txtSku.setValue(0);			// resets spinners
								spinner.setValue(1);
								
								panel.revalidate();
								panel.repaint();
								break;
							}
						}
					} catch( NumberFormatException e ) {
						//do nothing, returned to payment pop
					}
					if( option == JOptionPane.CANCEL_OPTION || option == JOptionPane.CLOSED_OPTION )
					{	// canceled
						break;
					}
				}
			}
		});
		btnCheckOot.setBounds(522, 444, 120, 23);
		panel.add(btnCheckOot);
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				storeCart.flushCart("S"+ store.getStoreID() + "-C");		// creates new cart outside of object, then retrieved
				
				model.setRowCount(1);		// removes all old rows | keeps header (index = 0)
				
				modelTotal.removeRow(0);	// removes old row
				modelTotal.addRow(new Object[] {"Total", "$0.00"} );
				
				spinner.setValue(1);
				
				panel.revalidate();
				panel.repaint();
			}
		});
		btnCancel.setBounds(770, 444, 154, 23);
		panel.add(btnCancel);
		
		return panel;
	}
	static public JPanel managerView(Store s)
	{
		/*
		 * View
		 * 	List of all products in store			// call STORE_PRODUCT
		 * Function
		 * 	Edit in-store Product level				
		 * Notes
		 * 	Re-stock is automatically sent | 
		 */
		JPanel panel = new JPanel();
		panel.setName("MANAGER");
		panel.setBorder(new LineBorder(null, 5));
		panel.setBackground(SystemColor.info);
		panel.setLayout(null);
		
		String label   = "Store Location: S" +s.getStoreID();
		JLabel lblOrderId = new JLabel(label);
		lblOrderId.setBounds(30, 15, 224, 35);
		panel.add(lblOrderId);
		
		JPanel pane = new JPanel();
		pane.setBackground(new Color(102, 102, 102));
		pane.setBounds(30, 61, 857, 530);
		panel.add(pane);
		pane.setLayout(null);
		
		
		// maps table values
		DefaultTableModel model = new DefaultTableModel(){
		    @Override
		    public boolean isCellEditable(int row, int column)
		    {
		    	// TODO Manager can edit product from table cell | by calling pop-up
		    	// does not save on close
		        // return column == 4 && row > 0;
		    	return false;
		    }
		};  
		model.addColumn("SKU");
		model.addColumn("NAME");
		model.addColumn("PRICE");
		model.addColumn("PAR LEVEL");
		model.addColumn("AMOUNT IN STORE");
		
		DefaultTableColumnModel columnModel = new DefaultTableColumnModel(); 	
			int[] columnsWidth = { 10, 200, 100, 80, 80 };									// DEFINES WIDTH
	        for( int i = 0; i < columnsWidth.length; i++ ) {							
	    		columnModel.addColumn(new TableColumn(i, columnsWidth[i]));
	        }
	        model.addRow( new Object [] {"SKU", "NAME", "PRICE", "PAR LEVEL", "AMOUNT IN STORE"});// HEADER ROW
		
	    // Puppets from store object
	    for( Product p : s.getProducts() )
	    {
	    	model.addRow( new Object [] {p.getSKU(), p.getName(), p.getPrice(), p.getStockPar(), p.getStockIn()} );
	    }
	    
	    JTable table = new JTable(model);
		table.setBounds(10, 11, 837, 508);
		table.setShowVerticalLines(false);
		pane.add(table);
		
		table.setColumnModel(columnModel);
		
		JButton btnEditCount = new JButton("Edit Count");
		btnEditCount.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JTextField sku = new JTextField(5);
				JTextField currentStock = new JTextField(5);
				  
				JPanel pane = new JPanel();
				pane.setLayout(new BoxLayout(pane, BoxLayout.PAGE_AXIS));
				pane.add(new JLabel("SKU:"));
				pane.add(sku);
				pane.add(new JLabel("Current Quantity: "));
				pane.add(currentStock);
				int result = JOptionPane.showConfirmDialog(null, pane, "Please Enter Item Count in Store", JOptionPane.OK_CANCEL_OPTION);
				
				if (result == JOptionPane.OK_OPTION) {
				   try {
					   
					   if( s.getProductBySKU( Integer.parseInt(sku.getText()) ) != null )
					   {	
						   // item exists in the store
						   s.updateCurrentStock(s.getProductBySKU( Integer.parseInt(sku.getText()) ), Integer.parseInt(currentStock.getText()));
						   
						   JOptionPane.showMessageDialog(null, "Product updated!");
					   } else {
						   // Product does not exist,  wrong pop-up called
						   JOptionPane.showMessageDialog(null, "Error, Could not find product with that SKU");
					   }
					   
					   model.setRowCount(1);		// removes all old rows | keeps header (index = 0)
						
					   for( Product p : s.getProducts() ) {
					    	model.addRow( new Object [] {p.getSKU(), p.getName(), p.getPrice(), p.getStockPar(), p.getStockIn()} );
					   }
				   } catch (NumberFormatException e) {
					   JOptionPane.showMessageDialog(null, "Error, Not a Number!");
				   }
				}
			}
				
		});
		btnEditCount.setBounds(40, 602, 150, 23);
		panel.add(btnEditCount);
		
		return panel;
	}
	public static JPanel warehouse( WareHouse w ) 
	{
		JPanel panel = new JPanel();
		panel.setName("WAREHOUSE");
		panel.setBorder(new LineBorder(null, 5));
		panel.setBackground(SystemColor.info);
		panel.setLayout(null);
		
		String label   = "Warehose location: W"+ w.getID();
		JLabel lblOrderId = new JLabel(label);
		lblOrderId.setBounds(30, 15, 224, 35);
		panel.add(lblOrderId);
		
		JPanel pane = new JPanel();
		pane.setBackground(new Color(102, 102, 102));
		pane.setBounds(30, 61, 857, 530);
		panel.add(pane);
		pane.setLayout(null);
		
		
		// maps table values
		DefaultTableModel model = new DefaultTableModel(){
		    @Override
		    public boolean isCellEditable(int row, int column)
		    {
		        return column == 4 && row > 0;
		    }
		};  
		model.addColumn("ORDER ID");
		model.addColumn("SKU");
		model.addColumn("NAME");
		model.addColumn("AMOUNT ON ORDER");
		model.addColumn("SEND BY");
		
		DefaultTableColumnModel columnModel = new DefaultTableColumnModel(); 	
			int[] columnsWidth = { 10, 50, 200, 100, 100 };											// DEFINES WIDTH
	        for( int i = 0; i < columnsWidth.length; i++ ) {							
	    		columnModel.addColumn(new TableColumn(i, columnsWidth[i]));
	        }
	        model.addRow( new Object [] {"ORDER ID", "SKU", "NAME", "AMOUNT ON ORDER", "SEND BY"});	// HEADER ROW
        
	    w.revalidatePending();							// updates warehouse to hq values data
		ArrayList<Cart> pending = w.getPending();
		System.out.println(w.getID() + "  has " + pending.size());
		
		for(Cart c : pending)
		{
			String[][] inCart = c.cartToPrint();		// each Cart( order request )
			for(int i = 0; i < inCart.length; i++)
			{
				System.out.println(c.getOrderID()+"|" + inCart[i][0] + "| " + inCart[i][1] + "  -  " + inCart[i][2]);
				String[] line = new String[] { c.getOrderID(), inCart[i][0], inCart[i][1], inCart[i][2], "EOD"};
				model.addRow(line);
			}
		}
	        
	    JTable table = new JTable(model);
		table.setBounds(10, 11, 837, 508);
		table.setShowVerticalLines(false);
		pane.add(table);
		
		table.setColumnModel(columnModel);
		
		JButton button = new JButton("Complete Order");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JPanel pane = new JPanel();
				JTextField orderID = new JTextField(5);
				pane.setLayout(new BoxLayout(pane, BoxLayout.PAGE_AXIS));
				pane.add(new JLabel("Order ID: "));
				pane.add(orderID);
				
				int result = JOptionPane.showConfirmDialog(null, pane, "Please Enter OrderId", JOptionPane.OK_CANCEL_OPTION);
				if( result == JOptionPane.OK_OPTION )
				{
					String OrderId = orderID.getText().toUpperCase();
					if( w.completeOrder(OrderId) )
					{
						w.revalidatePending();							// updates values from list
						ArrayList<Cart> pending = w.getPending();		// gets updated list
						model.setRowCount(1);
					
						for(Cart c : pending)
						{
							String[][] inCart = c.cartToPrint();		// each Cart( order request )
							for(int i = 0; i < inCart.length; i++)
							{
								String[] line = new String[] { c.getOrderID(), inCart[i][0], inCart[i][1], inCart[i][3], "EOD"};
								model.addRow(line);
							}
						}
					} else {
						JOptionPane.showConfirmDialog(null, "Could not find an Order with that OrderId", "Error", JOptionPane.ERROR_MESSAGE);
					}
				} 
			}
		});
		button.setBounds(40, 602, 150, 23);
		panel.add(button);
		
		return panel;
	}
	
	public static JPanel hq(ArrayList<Store> stores)
	{
		/* x 	: used for switch case on what to return
		 *
		 *
		 * View
		 * 	1) List of all Stores & Warehouses
		 * 	2) List of all default Products			// calls STORE_PRODUCT( where STORE_ID = null )	| defaults
		 * 	3) List of Pending store orders
		 * 	4) List of Sent store orders
		 * Function
		 * 	1) > add Store/ warehouse
		 * 		- Pop up Dialog
		 * 	2) > add Product
		 * 		- Pop up Dialog
		 * 	3) > confirm order 
		 * 		- button ( removes order request from list | calls respective warehouse object and reduces product by amount )
		 * 		- functionally a warehouse shopping cart
		 */ 
		JPanel panel = new JPanel();
		panel.setName("HQ");
		panel.setBorder(new LineBorder(null, 5));
		panel.setBackground(SystemColor.info);
		panel.setLayout(null);
		
		JLabel lblChooseStore = new JLabel("Select a Store:");
		lblChooseStore.setBounds(30, 15, 100, 35);
		panel.add(lblChooseStore);
		
		JSpinner txtStoreId = new JSpinner(new SpinnerNumberModel(0, 0, 0, 1) );	// Because SKUs are incremental (design choice) spinner limits input to only valid inputs [ TODO: changes with data integrity ] 
		txtStoreId.setBounds(130, 23, 80, 20);
		panel.add(txtStoreId);
		
		JPanel pane = new JPanel();
		pane.setBackground(new Color(102, 102, 102));
		pane.setBounds(30, 61, 857, 530);
		panel.add(pane);
		pane.setLayout(null);
		
		
		// maps table values
		DefaultTableModel model = new DefaultTableModel(){
		    @Override
		    public boolean isCellEditable(int row, int column)
		    {
		        return column == 4 && row > 0;
		    }
		};  
		model.addColumn("SKU");
		model.addColumn("NAME");
		model.addColumn("AMOUNT IN STORE");
		model.addColumn("PAR LEVEL");
		model.addColumn("RESTOCK ORDERED");
		
		DefaultTableColumnModel columnModel = new DefaultTableColumnModel(); 	
			int[] columnsWidth = { 10, 50, 50, 50, 50 };											// DEFINES WIDTH
	        for( int i = 0; i < columnsWidth.length; i++ ) {							
	    		columnModel.addColumn(new TableColumn(i, columnsWidth[i]));
	        }
	        model.addRow( new Object [] {"SKU", "NAME", "AMOUNT IN STORE", "PAR LEVEL", "PENDING DELIVERY" });	// HEADER ROW
		
		    for( Product p : stores.get(0).getProducts() )
		    {
		    	model.addRow( new Object [] {p.getSKU(), p.getName(), p.getStockIn(), p.getStockPar(), 0} );
		    }
	        
	    JTable table = new JTable(model);
		table.setBounds(10, 11, 837, 508);
		table.setShowVerticalLines(false);
		pane.add(table);
		
		table.setColumnModel(columnModel);
		
		JButton button = new JButton("Adjust Par Levels");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JPanel pane = new JPanel();
				JTextField orderID = new JTextField(5);
				pane.setLayout(new BoxLayout(pane, BoxLayout.PAGE_AXIS));
				pane.add(new JLabel("Order ID: "));
				pane.add(orderID);
				
				JTextField sku = new JTextField(5);
				JTextField fullyStocked = new JTextField(5);
				  
				JPanel p = new JPanel();
				p.setLayout(new BoxLayout(p, BoxLayout.PAGE_AXIS));
				p.add(new JLabel("SKU:"));
				p.add(sku);
				p.add(new JLabel("New Expected Fully Stocked Quantity"));
				p.add(fullyStocked);
				
				
				int result = JOptionPane.showConfirmDialog(null, p, 
				         "Update Item stock", JOptionPane.OK_CANCEL_OPTION);
				if ( result == JOptionPane.OK_OPTION ) {
					try {
						Store currentStore = stores.get( new Integer((int)txtStoreId.getValue()) );
						
						currentStore.updateFullStock( currentStore.getProductBySKU(Integer.parseInt(sku.getText())), Integer.parseInt(fullyStocked.getText()) );
						
						model.setRowCount(1);
						
					    for( Product product : currentStore.getProducts() )
					    {
					    	model.addRow( new Object [] {product.getSKU(), product.getName(), product.getStockIn(), product.getStockPar(), 0} );
					    }
				        
						JOptionPane.showMessageDialog(null, "Success!");
					} catch (NumberFormatException e) {
						JOptionPane.showMessageDialog(null, "Error. One of your inputs was invalid. Please try again");
					}
				}
				
			}
		});
		button.setBounds(40, 602, 150, 23);
		panel.add(button);

		
		JButton button2 = new JButton("Create Store Restock Order");
		button2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JPanel p = new JPanel();
				p.add(new JLabel("Are you sure you want to restock Store " + stores.get(0).getStoreID() + "?"));
				
				int result = JOptionPane.showConfirmDialog(null, p, "Confirm Store Restock", JOptionPane.OK_CANCEL_OPTION);
				if (result == JOptionPane.OK_OPTION) 
				{
					Store currentStore = stores.get( new Integer((int)txtStoreId.getValue()) );	// TODO test this
						
					
					Cart c = new Cart("W0-S" + currentStore.getStoreID() );		// TODO: add way for HQ store change
					for( Product p1 : Store.getStoreByID(currentStore.getStoreID()).getProducts() )	// gets Store by static Store list
				    {
						if( p1.getStockPar() - p1.getStockIn() > 0 )
						{
							// if Product needs to be re-stocked  |  AUTO-ORDER amount is > 0 
							// adds item by "AUTO-ORDER" value to orderCart
							Product pSuper = Product.getProductBySKU( p1.getSKU() );		// gets default assigned values
							Product lProd  = new Product(pSuper.getSKU(), pSuper.getPrice(), pSuper.getName(), pSuper.getDesc());	// item in cart; not a unique item in the system
							
					    	c.setProduct( lProd, p1.getStockPar() - p1.getStockIn() );		// Updates value in cart relative to amount in store
						}
				    }
					// updates Pending orders | COMPOSITE KEY = <FROM>-<TO>-<orderIDInedx>
					HeadQuarters.addPending(c);
					
					// updates row values | StockIn updated on Pending sent by WareHouse
					model.setRowCount(1);
				    for( Product product : currentStore.getProducts() )
				    {
				    	if( product.getStockPar() - product.getStockIn() > 0 ) 
				    	{
				    		// if not negative 
				    		model.addRow( new Object [] {product.getSKU(), product.getName(), product.getStockIn(), product.getStockPar(), product.getStockPar() - product.getStockIn()} );
				    	} else {
				    		// 0 otherwise 
				    		model.addRow( new Object [] {product.getSKU(), product.getName(), product.getStockIn(), product.getStockPar(), 0} );
				    	}
				    }
				    
				    
				    String reciept = "ORDER_ID : W0-S" + currentStore.getStoreID() +"\n"; // TODO: add wh to each STORE or hardcode to W0
					String[][] cart = c.cartToPrint();				// [ SKU ][ NAME ][ QUANTITY ][ @ AMOUNT ][ LINE TOTAL ]
					for( String[] line : cart )
					{
						// Compiles cart to receipt
						reciept += line[0] +"|"+ line[1] + " @ "+ line[2] + "\n";
					}
					reciept += "-------- ---------------";
					JOptionPane.showMessageDialog(null, reciept);
				}
				
			}
		});
		button2.setBounds(200, 602, 200, 23);
		panel.add(button2);
		
		return panel;
		
		//return null;
		
	}
	
	
	//Overloading
	public static JPanel hq(ArrayList<WareHouse> warehouses)
	{
		/* x 	: used for switch case on what to return
		 *
		 *
		 * View
		 * 	1) List of all Stores & Warehouses
		 * 	2) List of all default Products			// calls STORE_PRODUCT( where STORE_ID = null )	| defaults
		 * 	3) List of Pending store orders
		 * 	4) List of Sent store orders
		 * Function
		 * 	1) > add Store/ warehouse
		 * 		- Pop up Dialog
		 * 	2) > add Product
		 * 		- Pop up Dialog
		 * 	3) > confirm order 
		 * 		- button ( removes order request from list | calls respective warehouse object and reduces product by amount )
		 * 		- functionally a warehouse shopping cart
		 */ 
		JPanel panel = new JPanel();
		panel.setBorder(new LineBorder(null, 5));
		panel.setBackground(SystemColor.info);
		panel.setLayout(null);
		
		JLabel lblChooseStore = new JLabel("Select a Warehouse:");
		lblChooseStore.setBounds(30, 15, 100, 35);
		panel.add(lblChooseStore);
		
		JSpinner txtStoreId = new JSpinner(new SpinnerNumberModel(0, 0, 0, 1) );	// Because SKUs are incremental (design choice) spinner limits input to only valid inputs [ TODO: changes with data integrity ] 
		txtStoreId.setBounds(130, 23, 80, 20);
		panel.add(txtStoreId);
		
		JPanel pane = new JPanel();
		pane.setBackground(new Color(102, 102, 102));
		pane.setBounds(30, 61, 857, 530);
		panel.add(pane);
		pane.setLayout(null);
		
		
		// maps table values
		DefaultTableModel model = new DefaultTableModel(){
		    @Override
		    public boolean isCellEditable(int row, int column)
		    {
		        return column == 4 && row > 0;
		    }
		};  
		model.addColumn("SKU");
		model.addColumn("NAME");
		model.addColumn("AMOUNT IN WAREHOUSE");
		model.addColumn("PAR LEVEL");
		model.addColumn("RESTOCK ORDERED");
		
		DefaultTableColumnModel columnModel = new DefaultTableColumnModel(); 	
			int[] columnsWidth = { 10, 50, 50, 50, 50 };											// DEFINES WIDTH
	        for( int i = 0; i < columnsWidth.length; i++ ) {							
	    		columnModel.addColumn(new TableColumn(i, columnsWidth[i]));
	        }
	        model.addRow( new Object [] {"SKU", "NAME", "AMOUNT IN WAREHOUSE", "PAR LEVEL", "RESTOCK ORDERED" });	// HEADER ROW
		
		    for( Product p : stores.get(0).getProducts() )
		    {
		    	model.addRow( new Object [] {p.getSKU(), p.getName(), p.getStockIn(), p.getStockPar(), 0} );
		    }
	        
	    JTable table = new JTable(model);
		table.setBounds(10, 11, 837, 508);
		table.setShowVerticalLines(false);
		pane.add(table);
		
		table.setColumnModel(columnModel);
		
		JButton button = new JButton("Adjust Par Levels");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JPanel pane = new JPanel();
				JTextField orderID = new JTextField(5);
				pane.setLayout(new BoxLayout(pane, BoxLayout.PAGE_AXIS));
				pane.add(new JLabel("Order ID: "));
				pane.add(orderID);
				
				JTextField sku = new JTextField(5);
				JTextField fullyStocked = new JTextField(5);
				  
				JPanel p = new JPanel();
				p.setLayout(new BoxLayout(p, BoxLayout.PAGE_AXIS));
				p.add(new JLabel("SKU:"));
				p.add(sku);
				p.add(new JLabel("New Expected Fully Stocked Quantity"));
				p.add(fullyStocked);
				
				
				int result = JOptionPane.showConfirmDialog(null, p, 
				         "Update Item stock", JOptionPane.OK_CANCEL_OPTION);
				if ( result == JOptionPane.OK_OPTION ) {
					try {
						Store currentStore = stores.get( new Integer((int)txtStoreId.getValue()) );	// TODO test this
						
						currentStore.updateFullStock(currentStore.getProductBySKU(Integer.parseInt(sku.getText())), 
															     Integer.parseInt(fullyStocked.getText()));
						
						model.setRowCount(1);
						
					    for( Product product : currentStore.getProducts() )
					    {
					    	model.addRow( new Object [] {product.getSKU(), product.getName(), product.getStockIn(), product.getStockPar(), 0} );
					    }
				        
						JOptionPane.showMessageDialog(null, "Success!");
					} catch (NumberFormatException e) {
						JOptionPane.showMessageDialog(null, "Error. One of your inputs was invalid. Please try again");
					}
				}
				
			}
		});
		button.setBounds(40, 602, 150, 23);
		panel.add(button);

		
		JButton button2 = new JButton("Create Store Restock Order");
		button2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JPanel p = new JPanel();
				p.add(new JLabel("Are you sure you want to restock Store " + stores.get(0).getStoreID() + "?"));
				int result = JOptionPane.showConfirmDialog(null, p, 
				         "Confirm Store Restock", JOptionPane.OK_CANCEL_OPTION);
				if (result == JOptionPane.OK_OPTION) {
					Store currentStore = stores.get( new Integer((int)txtStoreId.getValue()) );	// TODO test this
					
					currentStore.getProductBySKU(0).setStockIn(1233213);
					
					model.setRowCount(1);
					
				    for( Product product : currentStore.getProducts() )
				    {
				    	if( product.getStockPar() - product.getStockIn() > 0 ) 
				    	{
				    		// if not negative 
				    		model.addRow( new Object [] {product.getSKU(), product.getName(), product.getStockIn(), product.getStockPar(), product.getStockPar() - product.getStockIn()} );
				    	} else {
				    		model.addRow( new Object [] {product.getSKU(), product.getName(), product.getStockIn(), product.getStockPar(), 0} );
				    	}
				    }
					JOptionPane.showMessageDialog(null, "Success!");
				}
				
			}
		});
		button2.setBounds(200, 602, 200, 23);
		panel.add(button2);
		
		return panel;
		
		//return null;
		
	}
	
	
	
}










