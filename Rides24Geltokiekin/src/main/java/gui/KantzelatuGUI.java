package gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import businessLogic.BLFacade;
import domain.Driver;
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
import javax.swing.DefaultListModel;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import javax.swing.ScrollPaneConstants;

public class KantzelatuGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private Driver driver;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private DefaultListModel laguntzaile= new DefaultListModel();
	private JLabel Nondiklbl = new JLabel("");
	private JLabel Noralbl = new JLabel("");
	private JLabel Noizlbl = new JLabel("");
	private JLabel Gidarialbl = new JLabel("");
	private JLabel requestNum = new JLabel("");
	private JLabel seatKop = new JLabel("");
	private JLabel ibilbidea = new JLabel("");
	
	private JButton btnCancel = new JButton(ResourceBundle.getBundle("Etiquetas").getString("MyRidesGUI.Cancel")); 
	
	
	/**
	 * Launch the application.
	 */


	/**
	 * Create the frame.
	 */
	public KantzelatuGUI(Driver d) {
		this.driver=d;
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
		lblNewLabel_4.setBounds(303, 162, 67, 13);
		contentPane.add(lblNewLabel_4);
		lblNewLabel_4.setText(ResourceBundle.getBundle("Etiquetas").getString("FindRidesGUI.Driver"));
		
		
		Nondiklbl.setBounds(392, 58, 193, 13);
		contentPane.add(Nondiklbl);
		
		
		Noralbl.setBounds(392, 94, 193, 13);
		contentPane.add(Noralbl);
		
		
		Noizlbl.setBounds(392, 131, 193, 13);
		contentPane.add(Noizlbl);
		
		setTitle(ResourceBundle.getBundle("Etiquetas").getString("TReservationsGUI.MainTitle") + " - driver :"+driver.getName());
		
		Gidarialbl.setBounds(363, 162, 194, 13);
		contentPane.add(Gidarialbl);
		
		JLabel lblNewLabel_5 = new JLabel();
		lblNewLabel_5.setBounds(303, 202, 113, 13);
		contentPane.add(lblNewLabel_5);
		lblNewLabel_5.setText(ResourceBundle.getBundle("Etiquetas").getString("MyRidesGUI.RequestNum"));
		
		requestNum.setBounds(419, 202, 194, 13);
		contentPane.add(requestNum);
		
		JLabel seatlbl = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("MyRidesGUI.Seats")); //$NON-NLS-1$ //$NON-NLS-2$
		seatlbl.setBounds(304, 236, 96, 13);
		contentPane.add(seatlbl);
		
		 //$NON-NLS-1$ //$NON-NLS-2$
		seatKop.setBounds(409, 236, 45, 13);
		contentPane.add(seatKop);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		
		btnCancel.setVisible(false);
		
		JList TratatuGabekoList = new JList();
		scrollPane.setRowHeaderView(TratatuGabekoList);
		TratatuGabekoList.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Ride ride=(Ride)TratatuGabekoList.getSelectedValue();
				btnCancel.setVisible(true);
				if(ride!=null) {
				Nondiklbl.setText(ride.getFrom());
				Noralbl.setText(ride.getTo());
				requestNum.setText(Integer.toString(ride.getEskakizunak().size()));
				Noizlbl.setText(ride.getDate().toString());
				Gidarialbl.setText(driver.getIzenaAbizenaUser());
				seatKop.setText(Integer.toString(ride.getnPlaces()));
				ibilbidea.setText(ride.getIbilbide());
			}
			}
		});
		TratatuGabekoList.setModel(laguntzaile);
		
		scrollPane.setViewportView( TratatuGabekoList);
		 TratatuGabekoList.setLayoutOrientation(JList.VERTICAL);
		scrollPane.setBounds(23, 60, 271, 219);
		contentPane.add(scrollPane);
		
		//$NON-NLS-1$ //$NON-NLS-2$
		btnCancel.setBounds(97, 325, 113, 31);
		contentPane.add(btnCancel);
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				

				Ride r=(Ride)TratatuGabekoList.getSelectedValue();
				
				BLFacade blf= MainGUI.getBusinessLogic();
				
				blf.Kantzelatu(r);
				etiketakHustu();
				kargatuHurrengoak();
				
			}
		});
		
		kargatuHurrengoak();
		
		JButton btnBACK = new JButton(ResourceBundle.getBundle("Etiquetas").getString("RegisterGUI.Back"));
		btnBACK.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
			}
		});
		btnBACK.setBounds(233, 397, 85, 21);
		contentPane.add(btnBACK);
		
		JLabel ibilbidelbl = new JLabel("ibilbidea"); //$NON-NLS-1$ //$NON-NLS-2$
		ibilbidelbl.setBounds(304, 271, 65, 13);
		contentPane.add(ibilbidelbl);
		
		 //$NON-NLS-1$ //$NON-NLS-2$
		ibilbidea.setBounds(392, 271, 183, 13);
		contentPane.add(ibilbidea);
		/*
		JRadioButton rdbtnEzEginak = new JRadioButton();
		buttonGroup.add(rdbtnEzEginak);
		rdbtnEzEginak.setText(ResourceBundle.getBundle("Etiquetas").getString("MyRidesGUI.ezEginak"));
		rdbtnEzEginak.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				etiketakHustu();
				kargatuEzEginak();
				btnCancel.setVisible(false);
			}
		});
		
		rdbtnEzEginak.setBounds(23, 320, 103, 21);
		contentPane.add(rdbtnEzEginak);
		*/
		/*
		JRadioButton rdbtnKantzelatuak = new JRadioButton();
		buttonGroup.add(rdbtnKantzelatuak);
		rdbtnKantzelatuak.setText(ResourceBundle.getBundle("Etiquetas").getString("MyRidesGUI.Kantzelatuak"));
		rdbtnKantzelatuak.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				etiketakHustu();
				kargatuKantzelatuak();
				btnCancel.setVisible(false);
			}
		});
		rdbtnKantzelatuak.setBounds(128, 286, 103, 21);
		contentPane.add(rdbtnKantzelatuak);
		*/
		/*//HAUEK TXUKUNDU ETA ETIKETAK ONDO JARRI ETA HORI DENA
		JRadioButton rdbtnEgindakoak = new JRadioButton();
		buttonGroup.add(rdbtnEgindakoak);
		rdbtnEgindakoak.setText(ResourceBundle.getBundle("Etiquetas").getString("MyRidesGUI.Eginak"));
		rdbtnEgindakoak.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				etiketakHustu();
				kargatuEgindakoak();
				btnCancel.setVisible(false);
			}
		});
		rdbtnEgindakoak.setBounds(23, 286, 103, 21);
		contentPane.add(rdbtnEgindakoak);
		*/
	/*	JRadioButton rdbtnEzKonfirmatuak = new JRadioButton();
		buttonGroup.add(rdbtnEzKonfirmatuak);
		rdbtnEzKonfirmatuak.setText(ResourceBundle.getBundle("Etiquetas").getString("MyRidesGUI.notConfirmated"));
		rdbtnEzKonfirmatuak.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				etiketakHustu();
				kargatuTratatuGabeak();
				
				btnCancel.setVisible(false);
			}
		});
		rdbtnEzKonfirmatuak.setBounds(23, 360, 103, 21);
		contentPane.add(rdbtnEzKonfirmatuak);
		*/
		/*
		JRadioButton rdbtnHurrengoak = new JRadioButton();
		buttonGroup.add(rdbtnHurrengoak);
	
		rdbtnHurrengoak.setText(ResourceBundle.getBundle("Etiquetas").getString("MyRidesGUI.DoingRightNow"));
		rdbtnHurrengoak.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				etiketakHustu();
				kargatuHurrengoak();
				
				btnCancel.setVisible(true);
			}
		});
		rdbtnHurrengoak.setBounds(128, 320, 103, 21);
		contentPane.add(rdbtnHurrengoak);
		
		*/
		
	}
	/*
	private void kargatuEzEginak() {
		laguntzaile.removeAllElements();
		BLFacade blf= MainGUI.getBusinessLogic();
		for(Ride r: blf.getAllRidesOfDriver(driver)) {
			if(r.getEgoera().equals("EzEgina")) {
				laguntzaile.addElement(r);
			}
		}
	}
	private void kargatuKantzelatuak() {
		laguntzaile.removeAllElements();
		BLFacade blf= MainGUI.getBusinessLogic();
		for(Ride r: blf.getAllRidesOfDriver(driver)) {
			if(r.getEgoera().equals("Kantzelatua")) {
				laguntzaile.addElement(r);
			}
		}
	}
	private void kargatuEgindakoak() {
		laguntzaile.removeAllElements();
		BLFacade blf= MainGUI.getBusinessLogic();
		for(Ride r: blf.getAllRidesOfDriver(driver)) {
			if(r.getEgoera().equals("Eginda")) {
				laguntzaile.addElement(r);
			}
		}
	}
	*/
	/*
	private void kargatuTratatuGabeak() {
		laguntzaile.removeAllElements();
		BLFacade blf= MainGUI.getBusinessLogic();
		for(Ride r: blf.getAllRidesOfDriver(driver)) {
			if(r.getEgoera().equals("Pasatuta")) {
				laguntzaile.addElement(r);
			}
		}
	}
	*/
	private void kargatuHurrengoak() {
		laguntzaile.removeAllElements();
		BLFacade blf= MainGUI.getBusinessLogic();
		List<RideContainer>containerList=blf.getEginRidesOfDriver(driver);
		List<Ride>rideList=new ArrayList<Ride>();
		for(RideContainer r:containerList) {
			rideList.add(r.getRide());
		}
		laguntzaile.addAll(rideList);
	}
	private void etiketakHustu() {

		Nondiklbl.setText("");
		Noralbl.setText("");
		Noizlbl.setText("");
		Gidarialbl.setText("");
	    requestNum.setText("");
		seatKop.setText("");
		ibilbidea.setText("");
	}
}
