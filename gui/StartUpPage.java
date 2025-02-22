package gui;

import java.sql.*;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class StartUpPage extends JFrame {

	private JPanel contentPane;
	private JTextField companyName;
	private JTextField adminUsername;
	private JTextField adminPassword;
	private JTextField logopath;
	private String companylogopath =null;
	private Connection myConn;

	private final Component verticalStrut = Box.createVerticalStrut(20);
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Connection myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/InventoryMgtSys","root","achyut");
//					Statement mystmt = myConn.createStatement();
//					ResultSet myResult = mystmt.executeQuery("Select * from products");
//					while(myResult.next()){
//						System.out.println(myResult.getString("name")+" , "+ myResult.getString("price"));
//					}
					StartUpPage frame = new StartUpPage();
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
	public StartUpPage() {
		setIconImage(
				Toolkit.getDefaultToolkit().getImage("C:\\Users\\Dev\\workspace\\InventoryMgtSys\\rsz_easyInlogo.png"));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		JPanel titlePanel = new JPanel();
		contentPane.add(titlePanel, BorderLayout.NORTH);

		JLabel lblEasyInventory = new JLabel("- Easy Inventory");
		lblEasyInventory.setFont(new Font("Times New Roman", Font.BOLD, 20));
		ImageIcon iconEI = new ImageIcon("C:\\Users\\Dev\\workspace\\InventoryMgtSys\\lib\\rsz_easyInlogo.png");
		lblEasyInventory.setIcon(iconEI);
		titlePanel.add(lblEasyInventory);

		JPanel adminPanel = new JPanel();
		contentPane.add(adminPanel, BorderLayout.CENTER);
		adminPanel.setLayout(new BoxLayout(adminPanel, BoxLayout.Y_AXIS));

		JPanel panel_2 = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel_2.getLayout();
		flowLayout.setAlignment(FlowLayout.RIGHT);
		adminPanel.add(panel_2);

		JLabel lblNewLabel = new JLabel("Enter Company Name");
		panel_2.add(lblNewLabel);

		companyName = new JTextField();
		panel_2.add(companyName);
		companyName.setColumns(20);

		Component horizontalStrut = Box.createHorizontalStrut(10);
		panel_2.add(horizontalStrut);

		JPanel panel_3 = new JPanel();
		FlowLayout flowLayout_1 = (FlowLayout) panel_3.getLayout();
		flowLayout_1.setAlignment(FlowLayout.RIGHT);
		adminPanel.add(panel_3);

		JLabel lblCompanyUserName = new JLabel("Company Admin Username");
		panel_3.add(lblCompanyUserName);

		adminUsername = new JTextField();
		adminUsername.setColumns(20);
		panel_3.add(adminUsername);

		Component horizontalStrut_1 = Box.createHorizontalStrut(10);
		panel_3.add(horizontalStrut_1);

		JPanel panel_4 = new JPanel();
		FlowLayout flowLayout_2 = (FlowLayout) panel_4.getLayout();
		flowLayout_2.setAlignment(FlowLayout.RIGHT);
		adminPanel.add(panel_4);

		JLabel lblCompanyName = new JLabel("Company Admin Password");
		panel_4.add(lblCompanyName);

		adminPassword = new JTextField();
		adminPassword.setColumns(20);
		panel_4.add(adminPassword);

		Component horizontalStrut_2 = Box.createHorizontalStrut(10);
		panel_4.add(horizontalStrut_2);

		JPanel panel = new JPanel();
		FlowLayout flowLayout_3 = (FlowLayout) panel.getLayout();
		flowLayout_3.setAlignment(FlowLayout.RIGHT);
		adminPanel.add(panel);

		JLabel lblCompanyLogo = new JLabel("Company logo");
		panel.add(lblCompanyLogo);

		logopath = new JTextField();
		logopath.setColumns(12);
		panel.add(logopath);

		JButton btnNewButton = new JButton("Browser");
		
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser fileDialog = new JFileChooser();
				int returnVal = fileDialog.showOpenDialog(contentPane);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					java.io.File file = fileDialog.getSelectedFile();
					companylogopath = file.getAbsolutePath();
					logopath.setText(companylogopath);
					
				} else {
					JOptionPane.showMessageDialog(null, "Select Company Logo [ format - .jpg or .png only]");
				}
			}
		});
		panel.add(btnNewButton);

		Component horizontalStrut_3 = Box.createHorizontalStrut(10);
		panel.add(horizontalStrut_3);
		
		JPanel controlBotton = new JPanel();
		FlowLayout flowLayout_4 = (FlowLayout) controlBotton.getLayout();
		flowLayout_4.setAlignment(FlowLayout.RIGHT);
		contentPane.add(controlBotton, BorderLayout.SOUTH);
		
		JButton Reset = new JButton("Reset");
		Reset.setFont(new Font("Tahoma", Font.BOLD, 13));
		Reset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				companyName.setText("");
				adminUsername.setText("");
				adminPassword.setText("");
				logopath.setText("");
			}
		});
		controlBotton.add(Reset);
		
		Component horizontalStrut_4 = Box.createHorizontalStrut(20);
		controlBotton.add(horizontalStrut_4);
		
		JButton next = new JButton("Next");
		next.setFont(new Font("Tahoma", Font.BOLD, 13));
		next.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		controlBotton.add(next);
		
		Component horizontalStrut_5 = Box.createHorizontalStrut(20);
		controlBotton.add(horizontalStrut_5);
		
		JButton cancel = new JButton("Cancel");
		cancel.setFont(new Font("Tahoma", Font.BOLD, 13));
		cancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		controlBotton.add(cancel);
		
		Component horizontalStrut_6 = Box.createHorizontalStrut(20);
		controlBotton.add(horizontalStrut_6);
	}
}
