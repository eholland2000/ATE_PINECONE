package application;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.border.EmptyBorder;
import javax.swing.BoxLayout;
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
				
				int result = JOptionPane.showConfirmDialog(null, pane, "Please Enter Item Count in Store", JOptionPane.OK_CANCEL_OPTION);
				if( result == JOptionPane.OK_OPTION )
				{
					// TODO edit HQ pending orders
					model.setRowCount(1);		// removes all old rows | keeps header (index = 0)
				} 
			}
		});
		button.setBounds(40, 602, 150, 23);
		panel.add(button);
	}
}
