package gui;

/**
 * @author Software Engineering teachers
 */


import javax.swing.*;

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


public class DriverGUI extends JFrame {
	
    private Driver driver;
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
	private JRadioButton rdbtnNewRadioButton;
	private JRadioButton rdbtnNewRadioButton_1;
	private JRadioButton rdbtnNewRadioButton_2;
	private JPanel panel;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private JButton BackButton;
	private JButton btnDiruaAtera;
	private JButton ErreserbakIkusiButton;
	private JPanel panel_1;
	private JPanel panel_2;
	private JLabel lblDriver;
	private JButton kotxeaSortubtn;
	private JButton btnMyRides;
	
	/**
	 * This is the default constructor
	 */
	public DriverGUI(Driver d) {
		super();

		driver=d;
		
		// this.setSize(271, 295);
		this.setSize(526, 220);
		/*
		jLabelSelectOption = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("MainGUI.SelectOption"));
		jLabelSelectOption.setFont(new Font("Tahoma", Font.BOLD, 13));
		jLabelSelectOption.setForeground(Color.BLACK);
		jLabelSelectOption.setHorizontalAlignment(SwingConstants.CENTER);
		
		rdbtnNewRadioButton = new JRadioButton("English");
		rdbtnNewRadioButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Locale.setDefault(new Locale("en"));
				System.out.println("Locale: "+Locale.getDefault());
				paintAgain();				}
		});
		buttonGroup.add(rdbtnNewRadioButton);
		
		rdbtnNewRadioButton_1 = new JRadioButton("Euskara");
		rdbtnNewRadioButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Locale.setDefault(new Locale("eus"));
				System.out.println("Locale: "+Locale.getDefault());
				paintAgain();				}
		});
		buttonGroup.add(rdbtnNewRadioButton_1);
		
		rdbtnNewRadioButton_2 = new JRadioButton("Castellano");
		rdbtnNewRadioButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Locale.setDefault(new Locale("es"));
				System.out.println("Locale: "+Locale.getDefault());
				paintAgain();
			}
		});
		buttonGroup.add(rdbtnNewRadioButton_2);
	
		panel = new JPanel();
		panel.add(rdbtnNewRadioButton_1);
		panel.add(rdbtnNewRadioButton_2);
		panel.add(rdbtnNewRadioButton);
		*/
		
		jContentPane = new JPanel();
		//jContentPane.add(panel);
		
		
		setContentPane(jContentPane);
		jContentPane.setLayout(new BorderLayout(0, 0));
		
		panel_1 = new JPanel();
		jContentPane.add(panel_1, BorderLayout.NORTH);
		
		lblDriver = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("DriverGUI.MainTitle")); //$NON-NLS-1$ //$NON-NLS-2$
		lblDriver.setFont(new Font("Tahoma", Font.PLAIN, 17));
		lblDriver.setHorizontalAlignment(SwingConstants.CENTER);
		panel_1.add(lblDriver);
		
		panel_2 = new JPanel();
		jContentPane.add(panel_2);
		
		jButtonQueryQueries = new JButton();
		panel_2.add(jButtonQueryQueries);
		jButtonQueryQueries.setText(ResourceBundle.getBundle("Etiquetas").getString("MainGUI.QueryRides"));
		jButtonQueryQueries.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				JFrame a = new FindRidesGUI();

				a.setVisible(true);
			}
		});
		
		kotxeaSortubtn = new JButton(ResourceBundle.getBundle("Etiquetas").getString("DriverGUI.createCar")); //$NON-NLS-1$ //$NON-NLS-2$
		kotxeaSortubtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame a= new CreateCarGUI(driver);
				a.setVisible(true);
			}
		});
		panel_2.add(kotxeaSortubtn);
		
		btnMyRides = new JButton(ResourceBundle.getBundle("Etiquetas").getString("MyRidesGUI.MyRides")); //$NON-NLS-1$ //$NON-NLS-2$
		panel_2.add(btnMyRides);
		btnMyRides.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame a= new MyRidesGUI(driver);
				a.setVisible(true);
			}
		});
		
		
		ErreserbakIkusiButton = new JButton(ResourceBundle.getBundle("Etiquetas").getString("DriverGUI.Reservations")); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-1$ //$NON-NLS-2$
		panel_2.add(ErreserbakIkusiButton);
		ErreserbakIkusiButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame a= new ErreserbakOnartuEtaDeuseztatu(driver);
				a.setVisible(true);
			}
		});
		
		
		
		btnDiruaAtera = new JButton(ResourceBundle.getBundle("Etiquetas").getString("DriverGUI.WithDraw"));
		panel_2.add(btnDiruaAtera);
		btnDiruaAtera.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame a= new DiruaAteraGUI(driver);
				a.setVisible(true);
			}
		});
		
		
		
		jButtonCreateQuery = new JButton();
		panel_2.add(jButtonCreateQuery);
		jButtonCreateQuery.setText(ResourceBundle.getBundle("Etiquetas").getString("MainGUI.CreateRide"));
		
		BackButton = new JButton(); //$NON-NLS-1$ //$NON-NLS-2$
		BackButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				setVisible(false);
				
			}
		});
		BackButton.setText(ResourceBundle.getBundle("Etiquetas").getString("DriverGUI.LogOut"));
		jContentPane.add(BackButton, BorderLayout.SOUTH);
		jButtonCreateQuery.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				JFrame a = new CreateRideGUI(driver);
				a.setVisible(true);
			}
		});
		setTitle(ResourceBundle.getBundle("Etiquetas").getString("DriverGUI.MainTitle") + " - driver :"+driver.getName());
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(1);
			}
		});
		
		JButton btnMezuak = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Mezuak.Mezuak"));
		panel_2.add(btnMezuak);
		btnMezuak.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame a= new MezuakGUI(driver);
				a.setVisible(true);
			}
		});
		JButton btnErreklamazioak = new JButton(ResourceBundle.getBundle("Etiquetas").getString("ErreklamazioakGUI.Erreklamazioak")); //$NON-NLS-1$ //$NON-NLS-2$
		panel_2.add(btnErreklamazioak);
		btnErreklamazioak.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame a= new ErreklamazioakGUI(driver);
				a.setVisible(true);
			}
		});
		
	}
	
	/*private void paintAgain() {
		jLabelSelectOption.setText(ResourceBundle.getBundle("Etiquetas").getString("MainGUI.SelectOption"));
		jButtonQueryQueries.setText(ResourceBundle.getBundle("Etiquetas").getString("MainGUI.QueryRides"));
		jButtonCreateQuery.setText(ResourceBundle.getBundle("Etiquetas").getString("MainGUI.CreateRide"));
		this.setTitle(ResourceBundle.getBundle("Etiquetas").getString("MainGUI.MainTitle")+ " - driver :"+driver.getName());
	}*/
	
} // @jve:decl-index=0:visual-constraint="0,0"

