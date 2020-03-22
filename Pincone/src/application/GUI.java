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
	static Store store = new Store(0);		// the current store being edited : testing with

	
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
		
		new Product(0, 20.00, "Winter Hat", "Fluffy hat with puffball");	
		new Product(1, 10.00, "Gloves");
		new Product(2, 99.99, "Coat", "Waterproof, windproof, and very warm");
		
		store.addNewProduct(Product.getProductBySKU(0), 10, 10);
		store.addNewProduct(Product.getProductBySKU(1), 50, 40);
		store.addNewProduct(Product.getProductBySKU(2), 20, 2);

		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 960, 720);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(51, 51, 51));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		visiblePane = new JPanel();
		contentPane.add(visiblePane, BorderLayout.NORTH);
		visiblePane.setLayout(new BorderLayout(0, 0));
		
		
		JButton btnRepaintPanel = new JButton("Employee log in [test button]");
		visiblePane.add(btnRepaintPanel, BorderLayout.NORTH);
		btnRepaintPanel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				System.out.println("Product exists at <" + Product.getProductBySKU(2) + "> at time of button press");
				
				previousPane = visiblePane;			// previous is the view left from
				contentPane.remove(visiblePane);
				
				visiblePane = PanelBuilder.POS();
				contentPane.add(visiblePane);
				contentPane.revalidate();
			}
		});
	}

	public static Store currentStore() {
		return store;
	}
}
