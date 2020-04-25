package application;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EmptyBorder;
import java.awt.GridLayout;

import javax.swing.JOptionPane;

import java.awt.Color;

import javax.swing.JButton;
import javax.swing.JComponent;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class GUI extends JFrame {

	private JPanel contentPane;
	private JPanel visiblePane;
	Store store = new Store(0); // the current store being edited : testing with new
	WareHouse warehouse = new WareHouse(0); // the current warehouse being edited : testing with new

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					// Set System L&F
					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

					GUI frame = new GUI();
					frame.setVisible(true);
				} catch (UnsupportedLookAndFeelException e) {
					// handle exception
				} catch (ClassNotFoundException e) {
					// handle exception
				} catch (InstantiationException e) {
					// handle exception
				} catch (IllegalAccessException e) {
					// handle exception
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
		setResizable(false);
		// https://stackoverflow.com/questions/41851429/how-to-switch-cards-from-a-button-on-a-card

		// ----- Base holder -----
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 960, 720);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(51, 51, 51));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		visiblePane = contentPane;
		// ----- ----- ----- -----
		// Defined by log in user ID prefix

		Product hat = new Product(0, 20.00, "Winter Hat", "Fluffy hat with puffball");
		Product gloves = new Product(1, 10.00, "Gloves");
		Product coat = new Product(2, 99.99, "Coat", "Waterproof, windproof, and very warm");

		Catalog.addCatalogProduct(hat);
		Catalog.addCatalogProduct(gloves);
		Catalog.addCatalogProduct(coat);

		store.addNewProduct(hat, 10, 10);
		store.addNewProduct(gloves, 50, 40);
		store.addNewProduct(coat, 20, 2);

		Product hat1 = new Product(0, 20.00, "Winter Hat", "Fluffy hat with puffball");
		Product gloves1 = new Product(1, 10.00, "Gloves");
		Product coat1 = new Product(2, 99.99, "Coat", "Waterproof, windproof, and very warm");

		warehouse.addNewProduct(hat1, 200, 200);
		warehouse.addNewProduct(gloves1, 300, 300);
		warehouse.addNewProduct(coat1, 500, 500);

		HeadQuarters.populatePending(); // dummy data [ creates 3 pending orders <W0-S0> <W0-S9> <W1-S0> ]

		// Log in Pop-up
		JTextField textID = new JTextField();
		JPasswordField textPass = new JPasswordField();

		JPanel componentPanel = new JPanel();
		componentPanel.setLayout(new GridLayout(2, 2));
		componentPanel.add(new JLabel("ID:"));
		componentPanel.add(textID);
		componentPanel.add(new JLabel("Pass:"));
		componentPanel.add(textPass);

		while (true) {
			// public static int showOptionDialog(Component parentComponent, Object message,
			// String title, int optionType, int messageType,
			// Icon icon, Object[] options, Object initialValue)
			int result = JOptionPane.showOptionDialog(contentPane, new JComponent[] { componentPanel },
					"FF-2000 | Login", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null,
					new String[] { "Login", "Shutdown" }, 0);

			if (result == 0) {
				String pass = "";
				for (char a : textPass.getPassword()) {
					pass += a;
				}
				;
				visiblePane = login(textID.getText(), pass);
				visiblePane.setBounds(5, 5, 934, 635);
				visiblePane.revalidate();
				
				try {
					/*
					 * can't add parent to parent
					 * Reverts to login page 
					 */
					contentPane.add(visiblePane);
					contentPane.revalidate();
					contentPane.repaint();
	
					textID.setText("");
					textPass.setText("");
	
					break;
				} catch(IllegalArgumentException e) {
					e.printStackTrace(); //shhhh
				}
			}
			if (result == 1 || result == JOptionPane.CLOSED_OPTION) {
				System.exit(0);
				break;
			}
		}
		JButton btnLogOut = new JButton("Log Out");
		btnLogOut.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				contentPane.remove(visiblePane);
				contentPane.remove(btnLogOut);

				contentPane.revalidate();
				contentPane.repaint();

				// public static int showOptionDialog(Component parentComponent, Object message,
				// String title, int optionType, int messageType,
				// Icon icon, Object[] options, Object initialValue)
				int result = JOptionPane.showOptionDialog(contentPane, new JComponent[] { componentPanel },
						"FF-2000 | Login", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null,
						new String[] { "Login", "Shutdown" }, 0);

				if (result == 0) {
					String pass = "";
					for (char a : textPass.getPassword()) {
						pass += a;
					}
					;
					visiblePane = login(textID.getText(), pass);
					visiblePane.setBounds(5, 5, 934, 635);
					visiblePane.revalidate();
					
					contentPane.add(btnLogOut);
					contentPane.add(visiblePane);
					contentPane.revalidate();
					contentPane.repaint();

					textID.setText("");
					textPass.setText("");

				}
				if (result == 1 || result == JOptionPane.CLOSED_OPTION) {
					System.exit(0);
				}
			}
		});
		btnLogOut.setBounds(840, 647, 89, 23);
		contentPane.add(btnLogOut);
	}

	private JPanel login(String id, String pass) {
		/*
		 * Selection of profile buttons Temporary log in to each kind of profile No log
		 * in integrity
		 */
		// p.add(new JLabel(Store.printAllStoreInventories())); // ???

		/*
		 * HQ-0 S-0 SM-0 WM-0
		 */
		if (id.contains("-")) {
			id = id.toUpperCase();
			
			if (pass.equals("purduePete") || pass.equals("@")) {
				String is = id.substring(0, id.indexOf('-')).toUpperCase();

				if (is.equals("SM")) {
					return PanelBuilder.managerView(store);

				} else if (is.equals("E")) {
					return PanelBuilder.POS(store, id);

				} else if (is.equals("WH")) {
					return PanelBuilder.warehouse(warehouse);

				} else if (is.equals("HQ")) {
					return PanelBuilder.hq(Store.getStores(), WareHouse.getWarehouses());

				}
			}
		}

		return visiblePane;
	}
}
