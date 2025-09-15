package gui;

/**
 * @author Software Engineering teachers
 */


import javax.swing.*;

import domain.Driver;
import domain.Ride;
import domain.RideRequest;
import businessLogic.BLFacade;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


public class MainGUI extends JFrame {
	
    
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
	private JButton jButtonLogin;
	private JButton RegisterButton;
	private final Action action = new SwingAction();
	
	/**
	 * This is the default constructor
	 */
	public MainGUI() {
		super();

		
		
		// this.setSize(271, 295);
		this.setSize(495, 290);
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
		
		
		
		jButtonQueryQueries = new JButton();
		jButtonQueryQueries.setText(ResourceBundle.getBundle("Etiquetas").getString("MainGUI.QueryRides"));
		jButtonQueryQueries.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				JFrame a = new FindRidesGUI();
				
				a.setVisible(true);
			}
		});
		RegisterButton = new JButton();
		//RegisterButton.setText("Register");
		RegisterButton.setText(ResourceBundle.getBundle("Etiquetas").getString("MainGUI.Register"));
		RegisterButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				JFrame a = new RegisterGUI();
				
				a.setVisible(true);
			}
		});
		jButtonLogin = new JButton();
		jButtonLogin.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				JFrame a = new LoginGUI();
				
				a.setVisible(true);
			}
		});
		//jButtonLogin.setText("Login");
		jButtonLogin.setText(ResourceBundle.getBundle("Etiquetas").getString("MainGUI.Login"));
		
		jContentPane = new JPanel();
		jContentPane.setLayout(new GridLayout(0, 1, 0, 0));
		jContentPane.add(jLabelSelectOption);

		jContentPane.add(jButtonQueryQueries);
		jContentPane.add(RegisterButton);
		
	 //$NON-NLS-1$ //$NON-NLS-2$
		jContentPane.add(jButtonLogin);
		jContentPane.add(panel);
		
		
		setContentPane(jContentPane);
		setTitle(ResourceBundle.getBundle("Etiquetas").getString("MainGUI.MainTitle") );
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(1);
			}
		});
	}
	
	private void paintAgain() {
		jLabelSelectOption.setText(ResourceBundle.getBundle("Etiquetas").getString("MainGUI.SelectOption"));
		jButtonQueryQueries.setText(ResourceBundle.getBundle("Etiquetas").getString("MainGUI.QueryRides"));
		jButtonLogin.setText(ResourceBundle.getBundle("Etiquetas").getString("MainGUI.Login"));
		RegisterButton.setText(ResourceBundle.getBundle("Etiquetas").getString("MainGUI.Register"));
		
		this.setTitle(ResourceBundle.getBundle("Etiquetas").getString("MainGUI.MainTitle"));
	}
	
	private class SwingAction extends AbstractAction {
		public SwingAction() {
			putValue(NAME, "SwingAction");
			putValue(SHORT_DESCRIPTION, "Some short description");
		}
		public void actionPerformed(ActionEvent e) {
		}
	}
	
	/*private void garbituRide() {
		BLFacade blf = getBusinessLogic();
		List<Ride> aurrekoak =blf.getBeforeTodayRides(new Date());
		Calendar egutegi = Calendar.getInstance();
		egutegi.add(Calendar.DAY_OF_MONTH, -3); 
        Date duela3egun = egutegi.getTime();
		for (Ride r: aurrekoak) {
			if(r.getDate().before(duela3egun)) {
				List<RideRequest> rq =r.getEskakizunak();
				for(RideRequest unekoa: rq) {
					unekoa.getTraveller().ezabatuRideRequest(unekoa);
					float diruSartu=unekoa.getSeats()*unekoa.getRide().getPrice();
					blf.gehituDirua(diruSartu, r.getDriver());
					//blf.gehituMezua(1,3,diruSartu,t);
					blf.ezabatuAllRideRequest(r);
					blf.ezabatuRideRequest(unekoa.getTraveller(), unekoa);
				}
				
			}
			
			
			
		}
		
		
		//List<Ride> gaurkoak =blf.getTodaysRides(new Date());
		
		
	}*/
	
} // @jve:decl-index=0:visual-constraint="0,0"

