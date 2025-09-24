package gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import businessLogic.BLFacade;
import domain.Driver;
import domain.EgoeraRide;
import domain.EgoeraRideRequest;
import domain.Mezua;
import domain.Profile;
import domain.Ride;
import domain.RideContainer;
import domain.RideRequest;
import domain.Traveller;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JList;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;
import javax.swing.ScrollPaneConstants;
import javax.swing.JComboBox;
import java.awt.Color;

public class MyRidesTraveller extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private Traveller profile;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private DefaultComboBoxModel travellers = new DefaultComboBoxModel();
	private DefaultListModel laguntzaile = new DefaultListModel();
	private JComboBox comboBoxTraveller = new JComboBox();
	private JLabel Nondiklbl = new JLabel("");
	private JLabel Noralbl = new JLabel("");
	private JLabel Noizlbl = new JLabel("");
	private JLabel Gidarialbl = new JLabel("");
	private JLabel ibilbidea = new JLabel("");
	private JRadioButton rdbtnEzEginak = new JRadioButton();
	private JLabel errorlbl = new JLabel("");
	private JButton btnCancel = new JButton(ResourceBundle.getBundle("Etiquetas").getString("MyRidesGUI.Cancel"));
	private JRadioButton rdbtnEgindakoak = new JRadioButton();
	private List<RideRequest> requests;
	private RideRequest unekoRequest = null;

	/**
	 * Launch the application.
	 */

	/**
	 * Create the frame.
	 */
	public MyRidesTraveller(Traveller p) {
		this.profile = p;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 599, 481);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblNewLabel = new JLabel("ERRESERBAK");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblNewLabel.setBounds(172, 10, 146, 26);
		contentPane.add(lblNewLabel);
		lblNewLabel.setText(ResourceBundle.getBundle("Etiquetas").getString("TReservationsGUI.Reservations"));

		JLabel lblNewLabel_1 = new JLabel("NONDIK");
		lblNewLabel_1.setBounds(304, 59, 78, 13);
		contentPane.add(lblNewLabel_1);
		lblNewLabel_1.setText(ResourceBundle.getBundle("Etiquetas").getString("TReservationsGUI.From"));

		JLabel lblNewLabel_2 = new JLabel("NORA");
		lblNewLabel_2.setBounds(304, 94, 78, 13);
		contentPane.add(lblNewLabel_2);
		lblNewLabel_2.setText(ResourceBundle.getBundle("Etiquetas").getString("TReservationsGUI.To"));

		JLabel lblNewLabel_3 = new JLabel("NOIZ");
		lblNewLabel_3.setBounds(303, 131, 79, 13);
		contentPane.add(lblNewLabel_3);
		lblNewLabel_3.setText(ResourceBundle.getBundle("Etiquetas").getString("TReservationsGUI.When"));

		JLabel lblNewLabel_4 = new JLabel("GIDARIA");
		lblNewLabel_4.setBounds(303, 162, 79, 13);
		contentPane.add(lblNewLabel_4);
		lblNewLabel_4.setText(ResourceBundle.getBundle("Etiquetas").getString("FindRidesGUI.Driver"));

		Nondiklbl.setBounds(392, 58, 193, 13);
		contentPane.add(Nondiklbl);

		Noralbl.setBounds(392, 94, 193, 13);
		contentPane.add(Noralbl);

		Noizlbl.setBounds(392, 131, 193, 13);
		contentPane.add(Noizlbl);

		setTitle(ResourceBundle.getBundle("Etiquetas").getString("TReservationsGUI.MainTitle") + " - driver :"
				+ profile.getName());

		Gidarialbl.setBounds(363, 162, 194, 13);
		contentPane.add(Gidarialbl);

		comboBoxTraveller.setBounds(267, 299, 163, 22);
		contentPane.add(comboBoxTraveller);
		comboBoxTraveller.setModel(travellers);
			comboBoxTraveller.setVisible(false);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

		btnCancel.setVisible(true);

		JList TratatuGabekoList = new JList();
		scrollPane.setRowHeaderView(TratatuGabekoList);
		TratatuGabekoList.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				Ride ride = (Ride) TratatuGabekoList.getSelectedValue();
				if(ride!=null) {
				Driver driver = ride.getDriver();

				if (ride != null) {
					Nondiklbl.setText(ride.getFrom());
					Noralbl.setText(ride.getTo());
					Noizlbl.setText(ride.getDate().toString());
					Gidarialbl.setText(driver.getFullName());
					
						// int kop = ride.getTravellerSetKop((Traveller) profile);
						// seatKop.setText(Integer.toString(kop));
						Traveller t = (Traveller) profile;
						boolean aurkitua = false;
						int i = 0;
						while (!aurkitua) {
							RideRequest unekoa = requests.get(i);
							if (unekoa.getRide().equals(ride)) {
								aurkitua = true;
								unekoRequest = unekoa;
							}
							i++;
						}
					
					ibilbidea.setText(ride.getIbilbide());

				}
			}
			}
		});
		TratatuGabekoList.setModel(laguntzaile);

		scrollPane.setViewportView(TratatuGabekoList);
		TratatuGabekoList.setLayoutOrientation(JList.VERTICAL);
		scrollPane.setBounds(23, 60, 271, 219);
		contentPane.add(scrollPane);

		// $NON-NLS-1$ //$NON-NLS-2$

		btnCancel.setBounds(440, 295, 117, 31);
		contentPane.add(btnCancel);
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				Ride r = (Ride) TratatuGabekoList.getSelectedValue();
				if(r!=null) {
				BLFacade blf = MainGUI.getBusinessLogic();

				blf.kantzelatu(r);
				etiketakHustu();
				kargatuHurrengoak();
				}else {
					errorlbl.setText(ResourceBundle.getBundle("Etiquetas").getString("MyRidesGUI.AukeratuBatError"));
				}
			}
		});
		JButton btnBaloratu = new JButton(ResourceBundle.getBundle("Etiquetas").getString("BaloratuGUI.Baloratu")); //$NON-NLS-1$ //$NON-NLS-2$
		btnBaloratu.setBounds(440, 337, 117, 28);
		contentPane.add(btnBaloratu);
		btnBaloratu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Ride r = (Ride) TratatuGabekoList.getSelectedValue();
				if (r != null) {
					Profile nori = r.getDriver();
					RideRequest request = unekoRequest;
					boolean baloratu = true;
					
						BLFacade blf = MainGUI.getBusinessLogic();
						if (blf.isBaloratua(request, false))
							baloratu = false;
					
					if (baloratu) {
						JFrame a = new BaloratuGUI(profile, nori, request);
						a.setVisible(true);
					} else {
						errorlbl.setText(ResourceBundle.getBundle("Etiquetas").getString("MyRidesGUI.BaloratuError"));
						// Erakutsi baloratu duela jada
					}

				}else {
					errorlbl.setText(ResourceBundle.getBundle("Etiquetas").getString("MyRidesGUI.AukeratuBatError"));
				}
			}
		});
		btnBaloratu.setVisible(false);

		JButton btnErreklamatu = new JButton(
				ResourceBundle.getBundle("Etiquetas").getString("ErreklamatuGUI.Erreklamatu")); //$NON-NLS-1$ //$NON-NLS-2$
		btnErreklamatu.setBounds(444, 376, 113, 23);
		contentPane.add(btnErreklamatu);
		btnErreklamatu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Ride r = (Ride) TratatuGabekoList.getSelectedValue();
				if (r != null) {
					Profile nori = r.getDriver();
					RideRequest request = unekoRequest;
					boolean erreklamatu = true;
					BLFacade blf = MainGUI.getBusinessLogic();
					 
						if (blf.isErreklamatua(request, false))
							erreklamatu = false;
					
					if (erreklamatu) {
						JFrame a = new ErreklamatuGUI(profile, nori, request);
						a.setVisible(true);
					} else {
						errorlbl.setText(ResourceBundle.getBundle("Etiquetas").getString("MyRidesGUI.ErreklamatuError"));
						
					}

				}else {
					errorlbl.setText(ResourceBundle.getBundle("Etiquetas").getString("MyRidesGUI.AukeratuBatError"));
				}
			}
		});
		btnErreklamatu.setVisible(false);
		kargatuHurrengoak();

		JButton btnBACK = new JButton(ResourceBundle.getBundle("Etiquetas").getString("RegisterGUI.Back"));
		btnBACK.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
			}
		});
		btnBACK.setBounds(215, 397, 103, 21);
		contentPane.add(btnBACK);

		JLabel ibilbidelbl = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("MyRidesGUI.Ibilbidea")); //$NON-NLS-1$ //$NON-NLS-2$
		ibilbidelbl.setBounds(304, 207, 103, 13);
		contentPane.add(ibilbidelbl);

		// $NON-NLS-1$ //$NON-NLS-2$
		ibilbidea.setBounds(394, 207, 181, 13);
		contentPane.add(ibilbidea);

		
		buttonGroup.add(rdbtnEzEginak);
		rdbtnEzEginak.setText(ResourceBundle.getBundle("Etiquetas").getString("MyRidesGUI.ezEginak"));
		rdbtnEzEginak.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				etiketakHustu();
				kargatuEzEginak();
				btnCancel.setVisible(false);
				
					btnErreklamatu.setVisible(true);
					btnBaloratu.setVisible(true);
				
			}
		});

		rdbtnEzEginak.setBounds(23, 320, 103, 21);
		contentPane.add(rdbtnEzEginak);

		JRadioButton rdbtnKantzelatuak = new JRadioButton();
		buttonGroup.add(rdbtnKantzelatuak);
		rdbtnKantzelatuak.setText(ResourceBundle.getBundle("Etiquetas").getString("MyRidesGUI.Kantzelatuak"));
		rdbtnKantzelatuak.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				etiketakHustu();
				kargatuKantzelatuak();
				ezkutatuBox();
				
				
					btnErreklamatu.setVisible(true);
					btnBaloratu.setVisible(true);
				

				btnCancel.setVisible(false);
			}
		});
		rdbtnKantzelatuak.setBounds(128, 286, 133, 21);
		contentPane.add(rdbtnKantzelatuak);

		// HAUEK TXUKUNDU ETA ETIKETAK ONDO JARRI ETA HORI DENA
		
		buttonGroup.add(rdbtnEgindakoak);
		rdbtnEgindakoak.setText(ResourceBundle.getBundle("Etiquetas").getString("MyRidesGUI.Eginak"));
		rdbtnEgindakoak.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				travellers.removeAllElements();
				etiketakHustu();
				kargatuEgindakoak();
				//ezkutatuBox();
				btnErreklamatu.setVisible(true);
				btnBaloratu.setVisible(true);
				btnCancel.setVisible(false);
			}
		});
		rdbtnEgindakoak.setBounds(72, 344, 103, 21);
		contentPane.add(rdbtnEgindakoak);

		JRadioButton rdbtnEzKonfirmatuak = new JRadioButton();
		buttonGroup.add(rdbtnEzKonfirmatuak);
		rdbtnEzKonfirmatuak.setText(ResourceBundle.getBundle("Etiquetas").getString("MyRidesGUI.notConfirmated"));
		rdbtnEzKonfirmatuak.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				etiketakHustu();
				ezkutatuBox();
				kargatuTratatuGabeak();
				btnErreklamatu.setVisible(false);
				btnBaloratu.setVisible(false);
				btnCancel.setVisible(false);
			}
		});
		rdbtnEzKonfirmatuak.setBounds(23, 286, 103, 21);
		contentPane.add(rdbtnEzKonfirmatuak);

		JRadioButton rdbtnHurrengoak = new JRadioButton();
		buttonGroup.add(rdbtnHurrengoak);

		rdbtnHurrengoak.setText(ResourceBundle.getBundle("Etiquetas").getString("MyRidesGUI.DoingRightNow"));
		rdbtnHurrengoak.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				etiketakHustu();
				ezkutatuBox();
				kargatuHurrengoak();
				btnErreklamatu.setVisible(false);
				btnBaloratu.setVisible(false);
				
			}
		});
		rdbtnHurrengoak.setBounds(128, 320, 103, 21);
		contentPane.add(rdbtnHurrengoak);
		rdbtnHurrengoak.setSelected(true);
		
		
		errorlbl.setForeground(new Color(255, 0, 0));
		errorlbl.setBounds(23, 376, 407, 13);
		contentPane.add(errorlbl);

	}

	
	//Aldatu
	private void kargatuEzEginak() {
		laguntzaile.removeAllElements();
		BLFacade blf = MainGUI.getBusinessLogic();
	
			List<RideRequest> lagun = blf.getRidesRequestsOfTraveller((Traveller) profile);
			requests = new ArrayList<RideRequest>();
			List<Ride>rides=new LinkedList<Ride>();
			for (RideRequest r : lagun) {
				EgoeraRideRequest accepted = r.getState();
				if (accepted.equals(EgoeraRideRequest.NotDone)) {
					Ride o = r.getRide();
					this.requests.add(r);
					if (o.getEgoera().equals(EgoeraRide.PASATUA)) {
						if(!rides.contains(o))
							rides.add(o);
					}
					
				}
			}
			laguntzaile.addAll(rides);

		 

	}

	private void kargatuKantzelatuak() {
		laguntzaile.removeAllElements();
		BLFacade blf = MainGUI.getBusinessLogic();
		
			List<RideRequest> lagun = blf.getRidesRequestsOfTraveller((Traveller) profile);
			requests = new ArrayList<RideRequest>();
			List<Ride>rides=new LinkedList<Ride>();
			for (RideRequest r : lagun) {
				EgoeraRideRequest accepted = r.getState();
				if (accepted.equals(EgoeraRideRequest.Accepted) && r.getRide().getDate().before(new Date())) {
					Ride o = r.getRide();
					this.requests.add(r);
					if (o.getEgoera().equals(EgoeraRide.KANTZELATUA)) {
						if(!rides.contains(o))
							rides.add(o);
					}
					
				}
			}
			laguntzaile.addAll(rides);

		 
	}
//Aldatu
	private void kargatuEgindakoak() {
		laguntzaile.removeAllElements();
		BLFacade blf = MainGUI.getBusinessLogic();
		
			List<RideRequest> lagun = blf.getRidesRequestsOfTraveller((Traveller) profile);
			requests = new ArrayList<RideRequest>();
			List<Ride>rides=new LinkedList<Ride>();
			for (RideRequest r : lagun) {
				EgoeraRideRequest accepted = r.getState();
				if (accepted.equals(EgoeraRideRequest.Done)) {
					Ride o = r.getRide();
					this.requests.add(r);
					if (o.getEgoera().equals(EgoeraRide.PASATUA)) {
						if(!rides.contains(o))
							rides.add(o);
					}
					
				}
			}
			laguntzaile.addAll(rides);

		
	}

	private void kargatuTratatuGabeak() {
		laguntzaile.removeAllElements();
		BLFacade blf = MainGUI.getBusinessLogic();
		
			List<RideRequest> lagun = blf.getRidesRequestsOfTraveller((Traveller) profile);
			requests = new ArrayList<RideRequest>();
			List<Ride>rides=new LinkedList<Ride>();
			for (RideRequest r : lagun) {
				EgoeraRideRequest accepted = r.getState();
				if (accepted.equals(EgoeraRideRequest.Accepted) && r.getRide().getDate().before(new Date())) {
					Ride o = r.getRide();
					this.requests.add(r);
					if (o.getEgoera().equals(EgoeraRide.PASATUA)) {
						if(!rides.contains(o))
							rides.add(o);
						
					}
					
				}
			}
			laguntzaile.addAll(rides);

		 
	}

	private void kargatuHurrengoak() {
		laguntzaile.removeAllElements();
		BLFacade blf = MainGUI.getBusinessLogic();
		
			List<RideRequest> lagun = blf.getRidesRequestsOfTraveller((Traveller) profile);
			requests = new ArrayList<RideRequest>();
			List<Ride>rides=new LinkedList<Ride>();
			for (RideRequest r : lagun) {
				EgoeraRideRequest accepted = r.getState();
				if (accepted.equals(EgoeraRideRequest.Accepted)) {
					this.requests.add(r);
					Ride o = r.getRide();
					if (o.getEgoera().equals(EgoeraRide.MARTXAN)) {
						if(!rides.contains(o))
						rides.add(o);
						
					}
					
				}
			}

			laguntzaile.addAll(rides);

		}
	
private void ezkutatuBox() {
	 comboBoxTraveller.setVisible(false);
	 travellers.removeAllElements();
}
	private void etiketakHustu() {
		errorlbl.setText("");
		Nondiklbl.setText("");
		Noralbl.setText("");
		Noizlbl.setText("");
		Gidarialbl.setText("");
		ibilbidea.setText("");
	}
}
