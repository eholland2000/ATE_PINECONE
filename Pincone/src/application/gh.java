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
import javax.swing.border.CompoundBorder;
import javax.swing.JPasswordField;

public class gh extends JFrame {

	private JPanel contentPane;
	private JTextField textId;
	private JTextField textPass;
	private JPasswordField passwordField;

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
		contentPane.setBackground(new Color(0, 51, 51));
		contentPane.setBorder(new CompoundBorder());
		setContentPane(contentPane);
		contentPane.setLayout(null);
		contentPane.setSize(200, 100);
		
		textId = new JTextField();
		textId.setBounds(95, 11, 170, 20);
		contentPane.add(textId);
		textId.setColumns(10);
		
		textPass = new JTextField();
		textPass.setBounds(95, 42, 170, 20);
		contentPane.add(textPass);
		textPass.setColumns(10);
		
		JLabel lblId = new JLabel("ID");
		lblId.setForeground(new Color(255, 255, 255));
		lblId.setBounds(24, 14, 46, 14);
		contentPane.add(lblId);
		
		JLabel lblPass = new JLabel("Pass");
		lblPass.setForeground(new Color(255, 255, 255));
		lblPass.setBounds(24, 45, 46, 14);
		contentPane.add(lblPass);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(120, 60, 6, 20);
		contentPane.add(passwordField);
		
		
	}
}
