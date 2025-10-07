package gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import businessLogic.BLFacade;
import domain.Mezua;
import domain.Traveller;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;
import java.awt.event.ActionEvent;
import java.awt.FlowLayout;
import javax.swing.JLabel;
import java.awt.GridLayout;
import javax.swing.JToolBar;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.BorderLayout;
import javax.swing.SwingConstants;
import javax.swing.BoxLayout;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import java.awt.Font;

public class TravellerGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
    private Traveller traveller;
    private JButton btnAlertakIkusi = new JButton(ResourceBundle.getBundle("Etiquetas").getString("AlertakIkusiGUI.Alertak")); 
	public TravellerGUI(Traveller t) {
		this.traveller=t;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 423, 215);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		
		setTitle(ResourceBundle.getBundle("Etiquetas").getString("TravellerGUI.MainTitle") + " - traveller :"+traveller.getName());
				
				JPanel panel_1 = new JPanel();
				
				JPanel panel = new JPanel();
				panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
				
				JLabel lblTraveller = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("TravellerGUI.Traveller")); //$NON-NLS-1$ //$NON-NLS-2$
				lblTraveller.setFont(new Font("Tahoma", Font.PLAIN, 17));
				lblTraveller.setHorizontalAlignment(SwingConstants.CENTER);
				panel.add(lblTraveller);
				
				JPanel panel_2 = new JPanel();
				contentPane.setLayout(new BorderLayout(0, 0));
				contentPane.add(panel_1, BorderLayout.SOUTH);
					panel_1.setLayout(new GridLayout(0, 1, 0, 0));
					konprobatuEaAlertaIrakurriGabe(traveller); 
					JButton btnHome = new JButton(ResourceBundle.getBundle("Etiquetas").getString("RegisterGUI.Back"));
					panel_1.add(btnHome);
					btnHome.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							
							setVisible(false);
					
						}
					});
					btnHome.setText(ResourceBundle.getBundle("Etiquetas").getString("DriverGUI.LogOut"));
				contentPane.add(panel, BorderLayout.NORTH);
				contentPane.add(panel_2);
				
				JButton btnErreserba = new JButton("Erreserbak egin");
				panel_2.add(btnErreserba);
				btnErreserba.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						konprobatuEaAlertaIrakurriGabe(traveller); 
						JFrame a=new BidaiakErreserbatuGUI(traveller);
						a.setVisible(true);
					}
				});
				btnErreserba.setText(ResourceBundle.getBundle("Etiquetas").getString("TravellerGUI.Reservate"));
				
				JButton btnIkusiErreserbak = new JButton("Ikusi Erreserbak");
				panel_2.add(btnIkusiErreserbak);
				btnIkusiErreserbak.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						konprobatuEaAlertaIrakurriGabe(traveller); 
						JFrame a= new IkusiTravellerErreserbakGUI(traveller);
						a.setVisible(true);
					}
				});
				btnIkusiErreserbak.setText(ResourceBundle.getBundle("Etiquetas").getString("TravellerGUI.ShowReservations"));
				
				JButton btnDiruaSartu = new JButton("Dirua Sartu");
				panel_2.add(btnDiruaSartu);
				btnDiruaSartu.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						konprobatuEaAlertaIrakurriGabe(traveller); 
						JFrame a= new DiruaSartuGUI(traveller);
						a.setVisible(true);
					}
				});
				btnDiruaSartu.setText(ResourceBundle.getBundle("Etiquetas").getString("TravellerGUI.Deposite"));
	
				JButton btnMezuak = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Mezuak.Mezuak"));
				panel_2.add(btnMezuak);
				
				btnMezuak.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						konprobatuEaAlertaIrakurriGabe(traveller); 
						JFrame a= new MezuakGUI(traveller);
						a.setVisible(true);
					}
				});
				
	

				JButton btnErreklamazioak = new JButton(ResourceBundle.getBundle("Etiquetas").getString("ErreklamazioakGUI.Erreklamazioak")); //$NON-NLS-1$ //$NON-NLS-2$
				panel_2.add(btnErreklamazioak);
				btnErreklamazioak.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						konprobatuEaAlertaIrakurriGabe(traveller); 
						JFrame a= new ErreklamazioakGUI(traveller);
						a.setVisible(true);
					}
				});
				btnAlertakIkusi.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						konprobatuEaAlertaIrakurriGabe(traveller);
						JFrame a= new AlertakIkusiGUI(traveller);
						a.setVisible(true);
					}
				});
				panel_2.add(btnAlertakIkusi);
				
				
				JButton btnMyRides = new JButton(ResourceBundle.getBundle("Etiquetas").getString("MyRidesGUI.MyRides")); //$NON-NLS-1$ //$NON-NLS-2$
				panel_2.add(btnMyRides);
				btnMyRides.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						konprobatuEaAlertaIrakurriGabe(traveller); 
						JFrame a= new MyRidesTraveller(traveller);
						a.setVisible(true);
					}
				});
				
	
	}
	public void konprobatuEaAlertaIrakurriGabe(Traveller traveller) {
		BLFacade blf= MainGUI.getBusinessLogic();
		List<Mezua> irakurriGabe= blf.getIkusiGabeAlerta(traveller);
		List<Mezua> alertak= new LinkedList<Mezua>();
		for(Mezua mezu: irakurriGabe) {
			if(mezu.getRide().lortuEserlekuKopMin(mezu.getAlerta().getFrom(),mezu.getAlerta().getTo())>0) {
				alertak.add(mezu);
			}
		}
		if(alertak.size()>0) {
			btnAlertakIkusi.setForeground(new Color(255, 0, 0));
		}else {
			btnAlertakIkusi.setForeground(new Color(0, 0, 0));
		}
	}
}
