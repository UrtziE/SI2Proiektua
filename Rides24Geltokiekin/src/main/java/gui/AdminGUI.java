package gui;

/**
 * @author Software Engineering teachers
 */


import javax.swing.*;

import domain.Admin;
import domain.Driver;
import businessLogic.BLFacade;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Locale;
import java.util.ResourceBundle;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.BorderLayout;


public class AdminGUI extends JFrame {
	
    private Admin admin;
	private static final long serialVersionUID = 1L;

	private JPanel jContentPane = null;
	private JButton jButtonCreateQuery = null;
	private JButton jButtonQueryQueries = null;

    private static BLFacade appFacadeInterface;
	
	public static BLFacade getBusinessLogic(){
		return appFacadeInterface;
	}
	 
	public static void setBussinessLogic (BLFacade afi){
		appFacadeInterface=afi;
	}
	protected JLabel jLabelSelectOption;
	private JButton backButton;
	private JPanel panel1;
	private JPanel panel2;
	private JLabel lblAdmin;
	private JButton btnErreklamazioak;
	
	/**
	 * This is the default constructor
	 */
	public AdminGUI(Admin a) {
		super();

		admin=a;
		
		
		this.setSize(526, 220);
		
		
		jContentPane = new JPanel();
		
		
		
		setContentPane(jContentPane);
		jContentPane.setLayout(new BorderLayout(0, 0));
		
		panel1 = new JPanel();
		jContentPane.add(panel1, BorderLayout.NORTH);
		
		lblAdmin = new JLabel("Admin"); //$NON-NLS-1$ //$NON-NLS-2$
		lblAdmin.setFont(new Font("Tahoma", Font.PLAIN, 17));
		lblAdmin.setHorizontalAlignment(SwingConstants.CENTER);
		panel1.add(lblAdmin);
		
		
		panel2 = new JPanel();
		jContentPane.add(panel2);
		
		
		
		jButtonQueryQueries = new JButton();
		panel2.add(jButtonQueryQueries);
		jButtonQueryQueries.setText(ResourceBundle.getBundle("Etiquetas").getString("MainGUI.QueryRides"));
		jButtonQueryQueries.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				JFrame a = new FindRidesGUI();

				a.setVisible(true);
			}
		});
		
		
		

		backButton = new JButton(); //$NON-NLS-1$ //$NON-NLS-2$
		backButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				setVisible(false);
				
			}
		});
		backButton.setText(ResourceBundle.getBundle("Etiquetas").getString("DriverGUI.LogOut"));
		jContentPane.add(backButton, BorderLayout.SOUTH);
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(1);
			}
		});
		
		
		
		
		JButton btnMezuak = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Mezuak.Mezuak"));
		panel2.add(btnMezuak);
		btnMezuak.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame a= new MezuakGUI(admin);
				a.setVisible(true);
			}
		});
		
		btnErreklamazioak = new JButton(ResourceBundle.getBundle("Etiquetas").getString("ErreklamazioakGUI.Erreklamazioak")); //$NON-NLS-1$ //$NON-NLS-2$ ResourceBundle.getBundle("Etiquetas").getString("AdminGUI.Erreklamazioak")
		panel2.add(btnErreklamazioak);
		btnErreklamazioak.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame a= new AdminErreklamazioakGUI(admin);
				a.setVisible(true);
			}
		});
		
	}
	
	
} 

