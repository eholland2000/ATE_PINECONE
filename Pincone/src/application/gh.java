package application;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.JTable;
import java.awt.SystemColor;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.event.ActionListener;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.JSpinner;
import javax.swing.JTextPane;

public class gh extends JFrame {

	private JPanel contentPane;
	private JTable table;
	private JTextField txtSku;
	private JTable total;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					gh frame = new gh();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public gh() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 960, 720);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel panel = new JPanel();
		panel.setBorder(new LineBorder(null, 5));
		panel.setBackground(SystemColor.info);
		panel.setLayout(null);
		contentPane.add(panel);
		
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
				// Enter valid payment | or cancel to return to add item view
				while ( true ) {
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
						
						System.out.println(mon + "|" + year + "   " + today);
						
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
								String[][] cart = Cart.cartToPrint();
								for( String[] line : cart )
								{
									// Compiles cart to reciept
									reciept += line[1] +" -@"+ line[2] +" : $"+ line[3] +" each for subtotal $"+ line[4] + "\n";
								}
								reciept += "-------- ---------------";
								reciept += "\nTotal: $" + Cart.total;
								JOptionPane.showMessageDialog(null, reciept + "\n\n" + GUI.currentStore().checkoutCart(Cart.products));
								
								Cart.flushCart();
								model.setRowCount(1);		// removes all old rows | keeps header (index = 0)
								
								modelTotal.removeRow(0);	// removes old row
								modelTotal.addRow(new Object[] {"Total", "$" + Cart.total} );
								
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
				Cart.flushCart();
				model.setRowCount(1);		// removes all old rows | keeps header (index = 0)
				
				modelTotal.removeRow(0);	// removes old row
				modelTotal.addRow(new Object[] {"Total", "$" + Cart.total} );
				
				txtSku.setValue(0);			// resets spinners
				spinner.setValue(1);
				
				panel.revalidate();
				panel.repaint();
			}
		});
		btnCancel.setBounds(770, 444, 154, 23);
		panel.add(btnCancel);
	}
}
