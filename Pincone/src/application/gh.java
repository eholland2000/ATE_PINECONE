package application;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.SpinnerModel;

public class gh extends JFrame {

	private JPanel contentPane;

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
		panel.setName("HQ");
		panel.setBorder(new LineBorder(null, 5));
		panel.setBackground(SystemColor.info);
		panel.setLayout(null);
		contentPane.add(panel);
		
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
						
						//Store currentStore = stores.get( new Integer((int)txtStoreId.getValue()) );
						Store currentStore = new Store(1);
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
		button2.setBounds(687, 602, 200, 23);
		panel.add(button2);
		
		JButton revalidateStore = new JButton("Revalidate Store");
		revalidateStore.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					Store currentStore = stores.get( new Integer((int) txtStoreId.getValue()) );
					
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
		});
		revalidateStore.setBounds(216, 21, 113, 23);
		panel.add(revalidateStore);
		
		JLabel lblSelectAWarehouse = new JLabel("Select a Warehouse:");
		lblSelectAWarehouse.setBounds(364, 15, 107, 35);
		panel.add(lblSelectAWarehouse);
		
		JSpinner txtStoreId_1 = new JSpinner((SpinnerModel) null);
		txtStoreId_1.setBounds(468, 22, 80, 20);
		panel.add(txtStoreId_1);
		
		JButton revalidateWarehouse = new JButton("Revalidate Warehouse");
		revalidateWarehouse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					WareHouse currentWare = WareHouse.getWarehouseByID( new Integer((int) txtStoreId.getValue()) );
					
					model.setRowCount(1);
					
				    for( Product product : currentWare.getProducts() )
				    {
				    	model.addRow( new Object [] {product.getSKU(), product.getName(), product.getStockIn(), product.getStockPar(), 0} );
				    }
			        
					JOptionPane.showMessageDialog(null, "Success!");
				} catch (NumberFormatException e1) {
					JOptionPane.showMessageDialog(null, "Error. One of your inputs was invalid. Please try again");
				}
			}
		});
		revalidateWarehouse.setBounds(554, 21, 141, 23);
		panel.add(revalidateWarehouse);
		
		JButton productView = new JButton("View Products");
		productView.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		productView.setBounds(774, 21, 113, 23);
		panel.add(productView);
		
		JButton newProduct = new JButton("Create New Product");
		newProduct.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		newProduct.setBounds(200, 602, 150, 23);
		panel.add(newProduct);
		
		return panel;
		
	}
}
