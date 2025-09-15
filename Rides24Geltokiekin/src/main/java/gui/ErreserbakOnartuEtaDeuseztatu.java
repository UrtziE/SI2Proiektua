package gui;

import java.awt.EventQueue;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import businessLogic.BLFacade;
import domain.Driver;
import domain.EgoeraRideRequest;
import domain.Mezua;
import domain.Ride;
import domain.RideContainer;
import domain.RideRequest;
import domain.Traveller;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.ResourceBundle;
import java.util.SortedSet;
import java.util.TreeSet;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;
import java.util.List;

public class ErreserbakOnartuEtaDeuseztatu extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private Driver driver;
	private DefaultComboBoxModel bidaiak = new DefaultComboBoxModel();
	private DefaultComboBoxModel erreserbak = new DefaultComboBoxModel();
	private JComboBox comboBoxErreserbak = new JComboBox();
	private JComboBox comboBoxBidaiak = new JComboBox();
	private JButton btnOnartu = new JButton("Onartu");
	private JLabel erreserbaKop = new JLabel(""); //$NON-NLS-1$ //$NON-NLS-2$
	private JLabel libreSeatKop = new JLabel(""); //$NON-NLS-1$ //$NON-NLS-2$
	private JLabel eskatutakoSeatKop = new JLabel(""); //$NON-NLS-1$ //$NON-NLS-2$

	private JButton btnBack = new JButton("BACK");

	private JButton btnDeuseztatu = new JButton("Deuseztatu");

	private JLabel lblNewLabel_1 = new JLabel("Erreserba eskakizunak");

	private JLabel lblNewLabel = new JLabel("Zure Bidaiak");

	private final JLabel lbl = new JLabel("Nork Eskatuta:");

	private final JLabel lblNewLabel_2 = new JLabel("Noiz Eskatuta:");

	// GEHIAGO ERE GEHITU, BIDAIAREN XEHETASUNAK ERAKUSTEKO.
	private final JLabel Norklbl = new JLabel("");
	private final JLabel Noizlbl = new JLabel("");
	private final JLabel lblNewLabel_4 = new JLabel(
			ResourceBundle.getBundle("Etiquetas").getString("O/DGUI.From")); //$NON-NLS-1$ //$NON-NLS-2$
	private final JLabel fromlbl = new JLabel(""); //$NON-NLS-1$ //$NON-NLS-2$
	private final JLabel lblNewLabel_6 = new JLabel(
			ResourceBundle.getBundle("Etiquetas").getString("O/DGUI.To")); //$NON-NLS-1$ //$NON-NLS-2$
	private final JLabel tolbl = new JLabel(""); //$NON-NLS-1$ //$NON-NLS-2$
	private final JLabel lblNewLabel_5 = new JLabel(
			ResourceBundle.getBundle("Etiquetas").getString("TReservationsGUI.When")); //$NON-NLS-1$ //$NON-NLS-2$
	private final JLabel whenlbl = new JLabel(""); //$NON-NLS-1$ //$NON-NLS-2$
	private JLabel lblIInteRating = new JLabel();
	private  JLabel lblNewLabel_7 = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("O/DGUI.RatingKop")); //$NON-NLS-1$ //$NON-NLS-2$
	private JLabel ratingKoplbl = new JLabel(""); //$NON-NLS-1$ //$NON-NLS-2$
	private  JLabel price = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("O/DGUI.Price")); //$NON-NLS-1$ //$NON-NLS-2$
	private  JLabel pricelbl = new JLabel(""); //$NON-NLS-1$ //$NON-NLS-2$
	
	
	/**
	 * Launch the application.
	 */

	/**
	 * Create the frame.
	 */
	public ErreserbakOnartuEtaDeuseztatu(Driver d) {
		this.driver = d;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 618, 334);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		comboBoxBidaiak.setModel(bidaiak);
		comboBoxErreserbak.setModel(erreserbak);
		
		setContentPane(contentPane);
		contentPane.setLayout(null);

		kargatuBidaiak();

		hasieratuBoxak();

		comboBoxBidaiak.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				clearEtiketak();
				Ride ride = (Ride) comboBoxBidaiak.getSelectedItem();
				kargatuErreserbak(ride);
				erreserbaKop.setText(Integer.toString(erreserbak.getSize()));
			}
		});
		comboBoxBidaiak.setBounds(165, 38, 310, 21);
		contentPane.add(comboBoxBidaiak);

		kargatuErreserbak((Ride) comboBoxBidaiak.getSelectedItem());

		comboBoxErreserbak.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnOnartu.setEnabled(true);
				btnDeuseztatu.setEnabled(true);

				RideRequest r = (RideRequest) comboBoxErreserbak.getSelectedItem();

				if (r != null) {
					etiketakKargatu(r);

				} else {
					btnOnartu.setEnabled(false);
					btnDeuseztatu.setEnabled(false);
				}
			}
		});

		comboBoxErreserbak.setBounds(165, 86, 310, 21);
		contentPane.add(comboBoxErreserbak);

		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNewLabel.setBounds(34, 39, 70, 17);
		contentPane.add(lblNewLabel);
		lblNewLabel.setText(ResourceBundle.getBundle("Etiquetas").getString("O/DGUI.Travels"));

		setTitle(
				ResourceBundle.getBundle("Etiquetas").getString("O/DGUI.MainTitle") + " - driver :" + driver.getName());

		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNewLabel_1.setBounds(34, 87, 70, 17);
		contentPane.add(lblNewLabel_1);
		btnDeuseztatu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				RideRequest erreserba = (RideRequest) comboBoxErreserbak.getSelectedItem();
				BLFacade blf = MainGUI.getBusinessLogic();
				blf.onartuEdoDeuseztatu(erreserba, false);
				kargatuErreserbak(erreserba.getRide());
				erreserbaKop.setText(Integer.toString(erreserbak.getSize()));
	
			}
		});
		lblNewLabel_1.setText(ResourceBundle.getBundle("Etiquetas").getString("O/DGUI.Requests"));

		btnDeuseztatu.setBounds(357, 255, 100, 35);
		btnDeuseztatu.setText(ResourceBundle.getBundle("Etiquetas").getString("O/DGUI.Reject"));
		contentPane.add(btnDeuseztatu);

		btnOnartu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				RideRequest erreserba = (RideRequest) comboBoxErreserbak.getSelectedItem();
				BLFacade blf = MainGUI.getBusinessLogic();
				blf.onartuEdoDeuseztatu(erreserba, true);
				boolean tokiLibreak = erreserba.getRide().eserlekuakAmaituta();
				
				if (tokiLibreak) {
					kargatuBidaiak();
					if (bidaiak.getSize() == 0) {
						erreserbak.removeAllElements();
					} else {
						comboBoxBidaiak.setSelectedIndex(0);
						Ride ride = (Ride) comboBoxBidaiak.getSelectedItem();
						kargatuErreserbak(ride);

					}
				} else {
					Ride ride = blf.getRideFromRequest(erreserba);
					clearEtiketak();
					kargatuErreserbak(ride);
				}
				erreserbaKop.setText(Integer.toString(erreserbak.getSize()));


			}
		});

		btnOnartu.setBounds(169, 255, 100, 35);
		btnOnartu.setText(ResourceBundle.getBundle("Etiquetas").getString("O/DGUI.Accept"));
		contentPane.add(btnOnartu);
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);

			}

		});

		btnBack.setBounds(10, 262, 85, 21);
		contentPane.add(btnBack);
		btnBack.setText(ResourceBundle.getBundle("Etiquetas").getString("RegisterGUI.Back"));
		lbl.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lbl.setBounds(34, 135, 121, 13);
		lbl.setText(ResourceBundle.getBundle("Etiquetas").getString("O/DGUI.Who"));
		contentPane.add(lbl);
		lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblNewLabel_2.setBounds(34, 175, 121, 13);

		contentPane.add(lblNewLabel_2);
		lblNewLabel_2.setText(ResourceBundle.getBundle("Etiquetas").getString("O/DGUI.When"));
		Norklbl.setFont(new Font("Tahoma", Font.PLAIN, 12));
		Norklbl.setBounds(169, 135, 100, 13);

		contentPane.add(Norklbl);
		Noizlbl.setFont(new Font("Tahoma", Font.PLAIN, 12));
		Noizlbl.setBounds(165, 175, 231, 13);

		contentPane.add(Noizlbl);

		JLabel lblNewLabel_3 = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("O/DGUI.ErreserbaKop")); //$NON-NLS-1$ //$NON-NLS-2$
		lblNewLabel_3.setBounds(390, 175, 85, 13);
		contentPane.add(lblNewLabel_3);

		erreserbaKop.setHorizontalAlignment(SwingConstants.CENTER);
		erreserbaKop.setBounds(501, 175, 45, 13);
		contentPane.add(erreserbaKop);
		lblNewLabel_4.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_4.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNewLabel_4.setBounds(10, 239, 118, 13);

		contentPane.add(lblNewLabel_4);
		fromlbl.setHorizontalAlignment(SwingConstants.LEFT);
		fromlbl.setBounds(136, 239, 37, 13);

		contentPane.add(fromlbl);
		lblNewLabel_6.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNewLabel_6.setBounds(175, 239, 103, 13);

		contentPane.add(lblNewLabel_6);
		tolbl.setHorizontalAlignment(SwingConstants.LEFT);
		tolbl.setBounds(288, 239, 59, 13);

		contentPane.add(tolbl);
		lblNewLabel_5.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNewLabel_5.setBounds(343, 239, 85, 13);

		contentPane.add(lblNewLabel_5);
		whenlbl.setBounds(414, 239, 180, 13);

		contentPane.add(whenlbl);

		JLabel eskatutakoSeats = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("O/DGUI.eskatutakoSeats")); //$NON-NLS-1$ //$NON-NLS-2$
		eskatutakoSeats.setBounds(390, 193, 115, 13);
		contentPane.add(eskatutakoSeats);

		JLabel libreSeats = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("O/DGUI.libreSeats")); //$NON-NLS-1$ //$NON-NLS-2$
		libreSeats.setBounds(390, 216, 115, 13);
		contentPane.add(libreSeats);

		eskatutakoSeatKop.setBounds(515, 193, 55, 13);
		contentPane.add(eskatutakoSeatKop);

		libreSeatKop.setBounds(515, 216, 55, 13);
		contentPane.add(libreSeatKop);
		
		JLabel lblRating = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("TReservationsGUI.Rating")); //$NON-NLS-1$ //$NON-NLS-2$
		lblRating.setBounds(277, 135, 70, 14);
		contentPane.add(lblRating);
		
		 //$NON-NLS-1$ //$NON-NLS-2$
		lblIInteRating.setBounds(369, 135, 59, 14);
		contentPane.add(lblIInteRating);
		lblNewLabel_7.setBounds(461, 136, 59, 13);
		
		contentPane.add(lblNewLabel_7);
		ratingKoplbl.setBounds(530, 136, 45, 13);
		
		contentPane.add(ratingKoplbl);
		price.setBounds(34, 198, 139, 17);
		
		contentPane.add(price);
		pricelbl.setBounds(175, 198, 85, 17);
		
		contentPane.add(pricelbl);
		
	}

	private void kargatuBidaiak() {
		bidaiak.removeAllElements();
		Norklbl.setText("");
		Noizlbl.setText("");
		BLFacade blf = MainGUI.getBusinessLogic();
		List<RideContainer> rideContainerList = blf.getRidesOfDriver(driver);
		List<Ride>rideList=new ArrayList<Ride>();
		
		for (RideContainer ride : rideContainerList) {
				rideList.add(ride.getRide());
		}
		Collections.sort(rideList);
		bidaiak.addAll(rideList);
	}

	private void kargatuErreserbak(Ride ride) {
		erreserbak.removeAllElements();
		if (ride != null) {

			BLFacade blf = MainGUI.getBusinessLogic();
			List<RideRequest> requests= new LinkedList<RideRequest>();
			for (RideRequest request : blf.getRidesRequestsOfRide(ride)) {
				if (request.getState().equals(EgoeraRideRequest.TratatuGabe))
					requests.add(request);
			}
			Collections.sort(requests,Collections.reverseOrder());
			erreserbak.addAll(requests);
			if(erreserbak.getSize()>0) {
				comboBoxErreserbak.setSelectedIndex(0);
				etiketakKargatu((RideRequest)comboBoxErreserbak.getSelectedItem());
			}
			
			
		}

	}

	private void clearEtiketak() {
		btnOnartu.setEnabled(false);
		btnDeuseztatu.setEnabled(false);
		Norklbl.setText("");
		Noizlbl.setText("");
		fromlbl.setText("");
		tolbl.setText("");
		whenlbl.setText("");
		libreSeatKop.setText("");
		eskatutakoSeatKop.setText("");
		ratingKoplbl.setText("");
		 pricelbl.setText("");
	}

	private void etiketakKargatu(RideRequest request) {
		if (request != null) {
			Traveller t = request.getTraveller();
			lblIInteRating.setText(String.format("%.2f", t.getRating()));
			Norklbl.setText(t.getFullName());
			Noizlbl.setText(request.getWhenRequested().toString());
			fromlbl.setText(request.getFromRequested());
			tolbl.setText(request.getToRequested());
			whenlbl.setText(request.getRide().getDate().toString());
			BLFacade blf=MainGUI.getBusinessLogic();
			Ride ride=(Ride) comboBoxBidaiak.getSelectedItem();
			int eserleku =blf.lortuZenbatEserlekuGeratu(ride,request);
			libreSeatKop.setText(Integer.toString(eserleku));
			eskatutakoSeatKop.setText(Integer.toString(request.getSeats()));
			ratingKoplbl.setText(Integer.toString(t.getBalorazioKop()));
			 pricelbl.setText(Float.toString(request.getPrezioa()));
		}
	}

	private void hasieratuBoxak() {
		if (bidaiak.getSize() > 0) {
			comboBoxBidaiak.setSelectedIndex(0);
			Ride ride = (Ride) comboBoxBidaiak.getSelectedItem();
			libreSeatKop.setText(Integer.toString(ride.getnPlaces()));
			kargatuErreserbak(ride);

			RideRequest request = (RideRequest) comboBoxErreserbak.getSelectedItem();
			etiketakKargatu(request);
			erreserbaKop.setText(Integer.toString(erreserbak.getSize()));

		}

	}
}
