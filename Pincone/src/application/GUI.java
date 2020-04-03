package application;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.FlowLayout;
import javax.swing.JLayeredPane;
import java.awt.CardLayout;
import java.awt.Color;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;

public class GUI extends JFrame {

	private JPanel contentPane;
	private JPanel visiblePane;
	private JPanel previousPane;
	Store store = new Store(0);				// the current store being edited : testing with new
	WareHouse warehouse = new WareHouse(0);	// the current warehouse being edited : testing with new
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI frame = new GUI();
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
	public GUI() {
		// https://stackoverflow.com/questions/41851429/how-to-switch-cards-from-a-button-on-a-card
		
		// ----- Base holder -----
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 960, 720);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(51, 51, 51));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		// ----- ----- ----- -----
		// Defined by log in user ID prefix 
		
				Product hat    = new Product(0, 20.00, "Winter Hat", "Fluffy hat with puffball");
				Product gloves = new Product(1, 10.00, "Gloves");
				Product coat   = new Product(2, 99.99, "Coat", "Waterproof, windproof, and very warm");
				
				Product.setCatalog(hat);
				Product.setCatalog(gloves);
				Product.setCatalog(coat);
				
				store.addNewProduct(hat, 10, 10);
				store.addNewProduct(gloves, 50, 40);
				store.addNewProduct(coat, 20, 2);
				
				Product hat1    = new Product(0, 20.00, "Winter Hat", "Fluffy hat with puffball");
				Product gloves1 = new Product(1, 10.00, "Gloves");
				Product coat1   = new Product(2, 99.99, "Coat", "Waterproof, windproof, and very warm");
				
				warehouse.addNewProduct(hat1, 200, 200);
				warehouse.addNewProduct(gloves1, 300, 300);
				warehouse.addNewProduct(coat1, 500, 500);
				
				
				
				
	    HeadQuarters.populatePending();			//dummy data [ creates 3 pending orders <W0-S0> <W0-S9> <W1-S0> ]
		
		JButton btnLogOut = new JButton("Log Out");
		btnLogOut.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				contentPane.remove(visiblePane);
				
				if( visiblePane.getName().equals("LOGIN") )
				{
					// closes window
					System.exit(0);
				} else {
					visiblePane = login();
					visiblePane.setBounds(5, 5, 934, 635);
					visiblePane.revalidate();
	
					contentPane.add(visiblePane);
					contentPane.revalidate();
					contentPane.repaint();
				}
			}
		});
		btnLogOut.setBounds(10, 647, 89, 23);
		contentPane.add(btnLogOut);
		
		
		// ----- Operation Pane -----
		visiblePane = login();					// Local
		visiblePane.setBounds(5, 5, 934, 635);
		contentPane.add(visiblePane);
		visiblePane.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
	}
	
	private JPanel login() {
		/*
		 * Selection of profile buttons
		 * 	Temporary log in to each kind of profile
		 * 	No log in integrity
		 */
		JPanel toReturn = new JPanel();
		toReturn.setName("LOGIN");
		toReturn.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JButton storeManager = new JButton("Store Manager log in [test button]");
		toReturn.add(storeManager);
		storeManager.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				previousPane = visiblePane;			// previous is the view left from
				contentPane.remove(visiblePane);
				
				visiblePane = PanelBuilder.managerView( store );
				visiblePane.setBounds(5, 5, 934, 635);
				visiblePane.revalidate();

				contentPane.add(visiblePane);
				contentPane.revalidate();
				contentPane.repaint();				
			}
		});
		
		JButton employee = new JButton("Employee log in [test button]");
		toReturn.add(employee);
		employee.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				previousPane = visiblePane;			// previous is the view left from
				contentPane.remove(visiblePane);
				
				visiblePane = PanelBuilder.POS( store );
				visiblePane.setBounds(5, 5, 934, 635);
				visiblePane.revalidate();

				contentPane.add(visiblePane);
				contentPane.revalidate();
				contentPane.repaint();
			} 
		});
		
		JButton btnWarehouse = new JButton("Warehouse log in [test button]");
		toReturn.add(btnWarehouse);
		btnWarehouse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				previousPane = visiblePane;			// previous is the view left from
				contentPane.remove(visiblePane);
				
				visiblePane = PanelBuilder.warehouse( warehouse );
				visiblePane.setBounds(5, 5, 934, 635);
				visiblePane.revalidate();

				contentPane.add(visiblePane);
				contentPane.revalidate();
				contentPane.repaint();
			}
		});
		
		JButton hq = new JButton("Head Quarters log in [test button]");
		toReturn.add(hq);
		hq.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Create product
				
				//Edit product
				//Store view
				//Warehouse view
				//Logout
				
				previousPane = visiblePane;			// previous is the view left from
				contentPane.remove(visiblePane);
				
				visiblePane = PanelBuilder.hq( Store.getStores() , WareHouse.getWarehouses());
				visiblePane.setBounds(5, 5, 934, 635);
				visiblePane.revalidate();

				contentPane.add(visiblePane);
				contentPane.revalidate();
				contentPane.repaint();
				
			}
		});
		return toReturn;
	}
}
