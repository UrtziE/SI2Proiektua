package gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import businessLogic.BLFacade;
import domain.EgoeraRide;
import domain.EgoeraRideRequest;
import domain.Mezua;
import domain.Ride;
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
import javax.swing.ButtonModel;
import javax.swing.DefaultListModel;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import javax.swing.ScrollPaneConstants;
import java.awt.Color;

public class IkusiTravellerErreserbakGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private Traveller traveller;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private DefaultListModel laguntzaile = new DefaultListModel();
	private JLabel Nondiklbl = new JLabel("");
	private JLabel Noralbl = new JLabel("");
	private JLabel Noizlbl = new JLabel("");
	private JLabel Gidarialbl = new JLabel("");
	private JLabel NoizEskatua = new JLabel("");
	private JLabel Erantzunalbl = new JLabel("");
	private JLabel seatKop = new JLabel("");
	private JLabel pricelbl = new JLabel("");
	private	JLabel errorlbl = new JLabel("");
	private int modua;

	/**
	 * Launch the application.
	 */

	/**
	 * Create the frame.
	 */
	public IkusiTravellerErreserbakGUI(Traveller t) {
		this.traveller = t;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 599, 388);
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
		lblNewLabel_1.setBounds(304, 59, 67, 13);
		contentPane.add(lblNewLabel_1);
		lblNewLabel_1.setText(ResourceBundle.getBundle("Etiquetas").getString("TReservationsGUI.From"));

		JLabel lblNewLabel_2 = new JLabel("NORA");
		lblNewLabel_2.setBounds(304, 94, 67, 13);
		contentPane.add(lblNewLabel_2);
		lblNewLabel_2.setText(ResourceBundle.getBundle("Etiquetas").getString("TReservationsGUI.To"));

		JLabel lblNewLabel_3 = new JLabel("NOIZ");
		lblNewLabel_3.setBounds(303, 131, 66, 13);
		contentPane.add(lblNewLabel_3);
		lblNewLabel_3.setText(ResourceBundle.getBundle("Etiquetas").getString("TReservationsGUI.When"));

		JLabel lblNewLabel_4 = new JLabel("GIDARIA");
		lblNewLabel_4.setBounds(304, 179, 67, 13);
		contentPane.add(lblNewLabel_4);
		lblNewLabel_4.setText(ResourceBundle.getBundle("Etiquetas").getString("FindRidesGUI.Driver"));

		Nondiklbl.setBounds(392, 58, 193, 13);
		contentPane.add(Nondiklbl);

		Noralbl.setBounds(392, 94, 193, 13);
		contentPane.add(Noralbl);

		Noizlbl.setBounds(392, 131, 193, 13);
		contentPane.add(Noizlbl);

		setTitle(ResourceBundle.getBundle("Etiquetas").getString("TReservationsGUI.MainTitle") + " - traveller :"
				+ traveller.getName());

		Gidarialbl.setBounds(391, 179, 194, 13);
		contentPane.add(Gidarialbl);

		JLabel lblNewLabel_5 = new JLabel();
		lblNewLabel_5.setBounds(303, 202, 78, 13);
		contentPane.add(lblNewLabel_5);
		lblNewLabel_5.setText(ResourceBundle.getBundle("Etiquetas").getString("TReservationsGUI.WhenAsked"));

		NoizEskatua.setBounds(381, 202, 194, 13);
		contentPane.add(NoizEskatua);

		JLabel lblNewLabel_6 = new JLabel("Erantzuna");
		lblNewLabel_6.setBounds(304, 233, 103, 13);
		contentPane.add(lblNewLabel_6);
		lblNewLabel_6.setText(ResourceBundle.getBundle("Etiquetas").getString("TReservationsGUI.Answer"));

		Erantzunalbl.setBounds(401, 233, 192, 13);
		contentPane.add(Erantzunalbl);

		JLabel seatlbl = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("TReservationsGUI.Seats")); //$NON-NLS-1$ //$NON-NLS-2$
		seatlbl.setBounds(305, 266, 111, 13);
		contentPane.add(seatlbl);

		// $NON-NLS-1$ //$NON-NLS-2$
		seatKop.setBounds(426, 266, 45, 13);
		contentPane.add(seatKop);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

		JList TratatuGabekoList = new JList();
		scrollPane.setRowHeaderView(TratatuGabekoList);
		TratatuGabekoList.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				RideRequest request = (RideRequest) TratatuGabekoList.getSelectedValue();
				if (request != null) {
					Ride ride = request.getRide();

					Nondiklbl.setText(ride.getFrom());
					Noralbl.setText(ride.getTo());
					Noizlbl.setText(ride.getDate().toString());
					Gidarialbl.setText(ride.getDriver().getIzenaAbizenaUser());
					NoizEskatua.setText(request.getWhenRequested().toString());
					seatKop.setText(Integer.toString(request.getSeats()));
					pricelbl.setText(Float.toString(request.getPrezioa()));
					if (!request.getState().equals(EgoeraRideRequest.TRATATU_GABE))
						Erantzunalbl.setText(request.getWhenDecided().toString());
					else
						Erantzunalbl.setText("");

				}
			}
		});
		TratatuGabekoList.setModel(laguntzaile);

		scrollPane.setViewportView(TratatuGabekoList);
		TratatuGabekoList.setLayoutOrientation(JList.VERTICAL);
		scrollPane.setBounds(23, 60, 271, 219);
		contentPane.add(scrollPane);

		JButton btnBai = new JButton(ResourceBundle.getBundle("Etiquetas").getString("TReservationsGUI.Yes")); //$NON-NLS-1$ //$NON-NLS-2$
		btnBai.setBounds(486, 283, 89, 23);
		contentPane.add(btnBai);
		btnBai.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				RideRequest r = (RideRequest) TratatuGabekoList.getSelectedValue();
				if (r != null) {
					BLFacade blf = MainGUI.getBusinessLogic();
					blf.egindaEdoEzEgina(r, true);
					etiketakHustu();
					kargatuEgindakoak();
					JFrame a = new BaloratuGUI(t, r.getRide().getDriver(), r);
					a.setVisible(true);
					
				}else {
					errorlbl.setText(ResourceBundle.getBundle("Etiquetas").getString("MyRidesGUI.AukeratuBatError"));
				}

			}
		});
		btnBai.setVisible(false);

		JButton btnEz = new JButton(ResourceBundle.getBundle("Etiquetas").getString("TReservationsGUI.No")); //$NON-NLS-1$ //$NON-NLS-2$
		btnEz.setBounds(486, 316, 89, 23);
		contentPane.add(btnEz);
		btnEz.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				RideRequest r = (RideRequest) TratatuGabekoList.getSelectedValue();
				if (r != null) {
					BLFacade blf = MainGUI.getBusinessLogic();
					blf.egindaEdoEzEgina(r, false);
					etiketakHustu();
					kargatuEgindakoak();
					JFrame a = new BaloratuGUI(t, r.getRide().getDriver(), r);
					a.setVisible(true);
					
				}else {
					errorlbl.setText(ResourceBundle.getBundle("Etiquetas").getString("MyRidesGUI.AukeratuBatError"));
				}

			}
		});
		btnEz.setVisible(false);

		JButton btnBACK = new JButton(ResourceBundle.getBundle("Etiquetas").getString("RegisterGUI.Back"));
		btnBACK.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
			}
		});
		btnBACK.setBounds(142, 330, 85, 21);
		contentPane.add(btnBACK);

		JRadioButton rdbtnEgindakoak = new JRadioButton(
				ResourceBundle.getBundle("Etiquetas").getString("MyRidesGUI.notConfirmated"));
		buttonGroup.add(rdbtnEgindakoak);
		rdbtnEgindakoak.setBounds(373, 284, 83, 21);
		contentPane.add(rdbtnEgindakoak);
		rdbtnEgindakoak.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				etiketakHustu();
				kargatuEgindakoak();
				btnBai.setVisible(true);
				btnEz.setVisible(true);
			}
		});

		JRadioButton rdbtnOnartuak = new JRadioButton();
		buttonGroup.add(rdbtnOnartuak);
		rdbtnOnartuak.setText(ResourceBundle.getBundle("Etiquetas").getString("TReservationsGUI.Accepted"));
		rdbtnOnartuak.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				etiketakHustu();
				kargatuOnartuak();
				btnBai.setVisible(false);
				btnEz.setVisible(false);
			}
		});
		rdbtnOnartuak.setBounds(268, 285, 103, 21);
		contentPane.add(rdbtnOnartuak);

		JRadioButton rdbtnDeuseztatuak = new JRadioButton();
		buttonGroup.add(rdbtnDeuseztatuak);
		rdbtnDeuseztatuak.setText(ResourceBundle.getBundle("Etiquetas").getString("TReservationsGUI.Rejected"));
		rdbtnDeuseztatuak.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				etiketakHustu();
				kargatuDeuseztatuak();
				btnBai.setVisible(false);
				btnEz.setVisible(false);
			}
		});
		rdbtnDeuseztatuak.setBounds(26, 285, 103, 21);
		contentPane.add(rdbtnDeuseztatuak);

		// HAUEK TXUKUNDU ETA ETIKETAK ONDO JARRI ETA HORI DENA
		JRadioButton rdbtnTratatuGabeak = new JRadioButton();
		buttonGroup.add(rdbtnTratatuGabeak);
		rdbtnTratatuGabeak.setText(ResourceBundle.getBundle("Etiquetas").getString("TReservationsGUI.Unhandled"));
		rdbtnTratatuGabeak.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				etiketakHustu();
				kargatuTratatuGabeak();
				btnBai.setVisible(false);
				btnEz.setVisible(false);
			}
		});
		rdbtnTratatuGabeak.setBounds(142, 285, 103, 21);
		contentPane.add(rdbtnTratatuGabeak);
		
		JLabel price = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("CreateRideGUI.Price")); //$NON-NLS-1$ //$NON-NLS-2$
		price.setBounds(304, 156, 54, 13);
		contentPane.add(price);
		
		 //$NON-NLS-1$ //$NON-NLS-2$
		pricelbl.setBounds(373, 156, 193, 13);
		contentPane.add(pricelbl);
		
	 //$NON-NLS-1$ //$NON-NLS-2$
		errorlbl.setForeground(new Color(255, 0, 0));
		errorlbl.setBounds(23, 316, 415, 13);
		contentPane.add(errorlbl);

	}

	private void kargatuDeuseztatuak() {
		laguntzaile.removeAllElements();
		BLFacade blf = MainGUI.getBusinessLogic();
		List<RideRequest> lagun = blf.getRidesRequestsOfTraveller(traveller);
		List<RideRequest> deusez = new ArrayList<RideRequest>();
		for (RideRequest r : lagun) {
			if (r.getState().equals(EgoeraRideRequest.REJECTED)&&r.getRide().getDate().after(new Date())) {
				deusez.add(r);

			}
		}
		Collections.sort(deusez, Collections.reverseOrder());
		laguntzaile.addAll(deusez);
	}

	private void kargatuOnartuak() {
		laguntzaile.removeAllElements();
		BLFacade blf = MainGUI.getBusinessLogic();
		List<RideRequest> lagun = blf.getRidesRequestsOfTraveller(traveller);
		List<RideRequest> onartu = new ArrayList<RideRequest>();
		for (RideRequest r : lagun) {
			if (r.getState().equals(EgoeraRideRequest.ACCEPTED) && !r.getRide().getDate().before(new Date())) {
				onartu.add(r);
			}
		}
		Collections.sort(onartu, Collections.reverseOrder());
		laguntzaile.addAll(onartu);
	}

	private void kargatuEgindakoak() {
		laguntzaile.removeAllElements();
		BLFacade blf = MainGUI.getBusinessLogic();
		List<RideRequest> lagun = blf.getRidesRequestsOfTraveller(traveller);
		List<RideRequest> egindakoak = new ArrayList<RideRequest>();
		for (RideRequest r : lagun) {
			if (r.getState().equals(EgoeraRideRequest.ACCEPTED) &&r.getRide().getEgoera().equals(EgoeraRide.PASATUA)&& !r.isBidaiaEsandaZer()) { //.getEgoera().equals(EgoeraRide.PASATUA)
				egindakoak.add(r);
			}
		}
		Collections.sort(egindakoak, Collections.reverseOrder());
		laguntzaile.addAll(egindakoak);
	}

	private void kargatuTratatuGabeak() {
		laguntzaile.removeAllElements();
		BLFacade blf = MainGUI.getBusinessLogic();
		List<RideRequest> lagun = blf.getRidesRequestsOfTraveller(traveller);
		List<RideRequest> tratatuGabe = new ArrayList<RideRequest>();
		for (RideRequest r : lagun) {
			EgoeraRideRequest tratatuGabeak = r.getState();
			if (tratatuGabeak.equals(EgoeraRideRequest.TRATATU_GABE)&&!r.getRide().getDate().before(new Date())) {
				tratatuGabe.add(r);
			}
		}
		Collections.sort(tratatuGabe, Collections.reverseOrder());
		laguntzaile.addAll(tratatuGabe);
	}

	private void etiketakHustu() {
		pricelbl.setText("");
		Nondiklbl.setText("");
		Noralbl.setText("");
		Noizlbl.setText("");
		Gidarialbl.setText("");
		NoizEskatua.setText("");
		Erantzunalbl.setText("");
		seatKop.setText("");
		errorlbl.setText("");

	}
}
