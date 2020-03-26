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
	private static Store store = new Store(0);				// the current store being edited : testing with new
	private static WareHouse warehouse = new WareHouse(0);	// the current warehouse being edited : testing with new

	
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
		
		// Cataloged items with default values : determined by HQ
		new Catalog ( new Product(0, 20.00, "Winter Hat", "Fluffy hat with puffball") );	
		new Catalog ( new Product(1, 10.00, "Gloves") );
		new Catalog ( new Product(2, 99.99, "Coat", "Waterproof, windproof, and very warm") );
		
		// Products to be added to a store
		Product hat    = new Product(0, 20.00, "Winter Hat", "Fluffy hat with puffball");
		Product gloves = new Product(1, 10.00, "Gloves");
		Product coat   = new Product(2, 99.99, "Coat", "Waterproof, windproof, and very warm");
		
		store.addNewProduct(hat, 10, 10);
		store.addNewProduct(gloves, 50, 40);
		store.addNewProduct(coat, 20, 2);
		
	    HeadQuarters.populatePending();			//dummy data 

		// ----- Base holder -----
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 960, 720);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(51, 51, 51));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		
		JButton btnLogOut = new JButton("Log Out");
		btnLogOut.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				contentPane.remove(visiblePane);
				
				visiblePane = login();
				visiblePane.setBounds(5, 5, 934, 635);
				visiblePane.revalidate();

				contentPane.add(visiblePane);
				contentPane.revalidate();
				contentPane.repaint();
			}
		});
		btnLogOut.setBounds(10, 647, 89, 23);
		contentPane.add(btnLogOut);
		
		
		// ----- Operated Pane -----
		
		visiblePane = login();
		visiblePane.setBounds(5, 5, 934, 635);
		contentPane.add(visiblePane);
		visiblePane.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		/*
		JButton employee = new JButton("Employee log in [test button]");
		visiblePane.add(employee);
		employee.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				previousPane = visiblePane;			// previous is the view left from
				contentPane.remove(visiblePane);
				
				visiblePane = PanelBuilder.POS();
				visiblePane.setBounds(5, 5, 934, 635);
				visiblePane.revalidate();

				contentPane.add(visiblePane);
				contentPane.revalidate();
				contentPane.repaint();
			}
		});
		*/
	}
	
	private JPanel login() {
		/*
		 * Selection of profile buttons
		 * 	Temporary log in to each kind of profile
		 * 	No log in integrity
		 */
		JPanel toReturn = new JPanel();
		toReturn.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JButton storeManager = new JButton("Store Manager log in [test button]");
		toReturn.add(storeManager);
		storeManager.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				previousPane = visiblePane;			// previous is the view left from
				contentPane.remove(visiblePane);
				
				visiblePane = PanelBuilder.managerView(store);
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
				/*
				previousPane = visiblePane;			// previous is the view left from
				contentPane.remove(visiblePane);
				
				visiblePane = PanelBuilder.hq(0);
				visiblePane.setBounds(5, 5, 934, 635);
				visiblePane.revalidate();

				contentPane.add(visiblePane);
				contentPane.revalidate();
				contentPane.repaint();
				*/
			}
		});
		
		return toReturn;
	}


	public static Store currentStore() {
		return store;
	}
}
