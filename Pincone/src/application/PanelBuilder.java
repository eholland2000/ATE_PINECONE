package application;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

public class PanelBuilder {
	/*
	 * JFrame defined to fixed resolution > Layouts are absolute ( lazy )
	 */
	static public JPanel login()
	{
		/*
		 * Selection of profile buttons
		 * 	Temporary log in to each kind of profile
		 * 	No log in integrity
		 */
		JPanel panel = new JPanel();
		panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JButton swap = new JButton("swap");
		panel.add(swap);
		swap.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		
		
		JButton b = new JButton("Bitch");
		panel.add(b);
		b.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.out.println("!^#@@");
			}
		});
		
		
		return panel;
	}
	static public JPanel POS()
	{
		JPanel panel = new JPanel();
		panel.setBorder(new LineBorder(null, 5));
		panel.setBackground(SystemColor.info);
		panel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Employee: ");
		lblNewLabel.setBounds(30, 15, 124, 35);
		panel.add(lblNewLabel);
		
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
		
		JSpinner spinner = new JSpinner(new SpinnerNumberModel(1, 0, 100, 1) );
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
					int sku = new Integer((int)txtSku.getValue());
					int amt = new Integer((int)spinner.getValue());
					
					Product p = Product.getProductBySKU( sku );

					Cart.setProduct(p, amt);
					
					model.setRowCount(1);		// removes all old rows | keeps header (index = 0)
					
					String[][] inCart = Cart.cartToPrint();
					System.out.println(inCart.length); 
					for(int i = 0; i < inCart.length; i++)
					{
						model.addRow(inCart[i]);
					}
					
					// updates price total
					modelTotal.removeRow(0);
					modelTotal.addRow(new Object[] {"Total", "$" + Cart.total} );

					txtSku.setValue(0);
					spinner.setValue(1);
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
		
		
		JButton btnCheckOot = new JButton("Check Oot");
		btnCheckOot.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// pop up
				boolean checkingOut = true;
				while (checkingOut) {
					try {
						/*
						 * TODO Break up inputs into 
						 * 	Card number
						 * 	EXP date
						 * 	CV number/ Pin		
						 * for better readibility 
						 */
						JTextField cardNum = new JTextField(16);
						JTextField expDate = new JTextField(5);
						JTextField securityCode = new JPasswordField(3);
						Object[] message = {
								"Enter the 16-Digit Card Number:", cardNum,
								"Enter the Expiration Date: (MM/YY)", expDate,
								"Enter the 3-Digit Security Code:", securityCode
						};
						//String cardNum = JOptionPane.showInputDialog(null, sm.store.printCart() + "\n, Enter the 16-digit Card Number:");
						//String expDate = JOptionPane.showInputDialog(null, sm.store.printCart() + "\n, Enter the Expiration Date:  <'MM/YY'>");
						//String securityCode = JOptionPane.showInputDialog(null, sm.store.printCart() + "\n, Enter the 3-digit Security Code:");
						int option = JOptionPane.showConfirmDialog(null, message, "Payment", JOptionPane.OK_CANCEL_OPTION);
						if (option == JOptionPane.OK_OPTION) {
							String input = cardNum.getText() + "," + expDate.getText()  + "," + securityCode.getText();
							JOptionPane.showMessageDialog(null, GUI.currentStore().checkoutCart(Cart.products));
							checkingOut = false;
						} else {
							checkingOut = false;
						}
						// does not preserve old copy | can update to unique named files with "Order Form "+ date.txt
					} catch (Exception e) {
						// should not occur
						JOptionPane.showMessageDialog(null, "Error in sending order");
						e.printStackTrace();
					}
				}
				
				Cart.flushCart();
				model.setRowCount(1);		// removes all old rows | keeps header (index = 0)
				
				modelTotal.removeRow(0);	// removes old row
				modelTotal.addRow(new Object[] {"Total", "$" + Cart.total} );
				
				txtSku.setValue(0);		// resets spinners
				spinner.setValue(1);
				
				panel.revalidate();
				panel.repaint();
			}
		});
		btnCheckOot.setBounds(522, 444, 120, 23);
		panel.add(btnCheckOot);
		
		return panel;
	}
	
	/*
dat	public JPanel managerView()
	{
		/*
		 * View
		 * 	List of all products in store			// call STORE_PRODUCT
		 * Function
		 * 	Edit in-store Product level				
		 * Notes
		 * 	Re-stock is automatically sent | 
		 *
	}
	public JPanel hqView(int x)
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
		 *
	}
	
	*/
	
}










