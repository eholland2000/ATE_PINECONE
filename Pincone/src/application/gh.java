package application;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
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
import java.awt.event.ActionListener;
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
		contentPane.add(panel, BorderLayout.CENTER);		// to remove
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
		DefaultTableModel model = new DefaultTableModel(); 
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
		
		table = new JTable(model);
		table.setBounds(10, 11, 462, 361);
		table.setShowVerticalLines(false);
		pane.add(table);
		
		table.setColumnModel(columnModel);
		
		DefaultTableModel modelTotal = new DefaultTableModel(){
		    @Override
		    public boolean isCellEditable(int row, int column)
		    {
		        // fix this? | removes cell editing 
		        return false;
		    }
		}; 
		modelTotal.addColumn("SKU");
		modelTotal.addColumn("NAME");
		modelTotal.addRow(new Object[] {"Total", "ball"} );
		DefaultTableColumnModel columnModelT = new DefaultTableColumnModel(); 	
		columnModelT.addColumn(new TableColumn(0, 100));
		columnModelT.addColumn(new TableColumn(1));
		
		total = new JTable(modelTotal);
		total.setBounds(246, 383, 226, 16);
		
		pane.add(total);
		
		total.setColumnModel(columnModelT);

		
		
		
		
		
		JButton btnAddCol = new JButton("add item");
		btnAddCol.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				pane.remove(table);
		        model.addRow( new Object [] {"SKU", "NAME", "QUANTITY", "AMOUNT", "LINE TOTAL"});	// HEADER ROW
				pane.add(table);
		        
				panel.revalidate();
				panel.repaint();
			}
		});
		btnAddCol.setBounds(835, 61, 89, 23);
		panel.add(btnAddCol);
		
		txtSku = new JTextField();
		txtSku.setBounds(674, 61, 86, 20);
		panel.add(txtSku);
		txtSku.setColumns(10);
		
		JSpinner spinner = new JSpinner(new SpinnerNumberModel(0, 0, 100, 1) );
		spinner.setBounds(770, 61, 55, 20);
		panel.add(spinner);
		
		JLabel lblEnterSku = new JLabel("Enter SKU");
		lblEnterSku.setBounds(577, 65, 70, 14);
		panel.add(lblEnterSku);
		
		JButton btnCheckOot = new JButton("Check Oot");
		btnCheckOot.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		btnCheckOot.setBounds(522, 444, 89, 23);
		panel.add(btnCheckOot);
	}
}
