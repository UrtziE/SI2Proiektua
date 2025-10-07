package gui;

import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import businessLogic.BLFacade;
import domain.Alerta;
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
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;
import javax.swing.ScrollPaneConstants;

public class AlertakIkusiGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private Traveller traveller;
	
	private DefaultListModel laguntzaile = new DefaultListModel();
	private JLabel Nondiklbl = new JLabel("");
	private JLabel Noralbl = new JLabel("");
	private JLabel Noizlbl = new JLabel("");
	private JLabel Gidarialbl = new JLabel("");
	private JLabel NoizEskatua = new JLabel("");
	private JLabel seatKop = new JLabel("");
	private JRadioButton rdbtnEsleituak = new JRadioButton();
	private JRadioButton rdbtnIkusiGabeak = new JRadioButton(ResourceBundle.getBundle("Etiquetas").getString("AlertakIkusiGUI.IkusiGabe"));
	private JRadioButton rdbtnIkusitakoak = new JRadioButton();
	private JRadioButton btnSortutakoAlertak = new JRadioButton();
	private int modua;
	private JButton btnKanzelatu = new JButton(ResourceBundle.getBundle("Etiquetas").getString("AlertakIkusiGUI.Kanzelatu"));
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private final ButtonGroup buttonGroup_1 = new ButtonGroup();
 
	/**
	 * Launch the application.
	 */

	/**
	 * Create the frame.
	 */
	public AlertakIkusiGUI(Traveller t) {
		this.traveller = t;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 599, 388);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		konprobatuEaAlertaIrakurriGabe(traveller) ;
		JLabel lblNewLabel = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("AlertakIkusiGUI.Alertak"));
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblNewLabel.setBounds(172, 10, 146, 26);
		contentPane.add(lblNewLabel);
		

		JLabel lblNewLabel_1 = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("TReservationsGUI.From"));
		lblNewLabel_1.setBounds(304, 59, 67, 13);
		contentPane.add(lblNewLabel_1);
		

		JLabel lblNewLabel_2 = new JLabel("NORA");
		lblNewLabel_2.setBounds(304, 94, 78, 13);
		contentPane.add(lblNewLabel_2);
		lblNewLabel_2.setText(ResourceBundle.getBundle("Etiquetas").getString("TReservationsGUI.To"));

		JLabel lblNewLabel_3 = new JLabel("NOIZ");
		lblNewLabel_3.setBounds(303, 131, 68, 13);
		contentPane.add(lblNewLabel_3);
		lblNewLabel_3.setText(ResourceBundle.getBundle("Etiquetas").getString("TReservationsGUI.When"));

		JLabel lblNewLabel_4 = new JLabel("GIDARIA");
		lblNewLabel_4.setBounds(303, 162, 68, 13);
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

		Gidarialbl.setBounds(391, 162, 194, 13);
		contentPane.add(Gidarialbl);

		JLabel lblNewLabel_5 = new JLabel();
		lblNewLabel_5.setBounds(303, 202, 132, 13);
		contentPane.add(lblNewLabel_5);
		lblNewLabel_5.setText(ResourceBundle.getBundle("Etiquetas").getString("AlertakIkusiGUI.NoizSortua"));

		NoizEskatua.setBounds(391, 225, 194, 13);
		contentPane.add(NoizEskatua);

		JLabel seatlbl = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("AlertakIkusiGUI.GeraEserleku")); //$NON-NLS-1$ //$NON-NLS-2$
		seatlbl.setBounds(305, 266, 173, 13);
		contentPane.add(seatlbl);

		// $NON-NLS-1$ //$NON-NLS-2$
		seatKop.setBounds(477, 266, 45, 13);
		contentPane.add(seatKop);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

		JList TratatuGabekoList = new JList();
		scrollPane.setRowHeaderView(TratatuGabekoList);
		TratatuGabekoList.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(rdbtnEsleituak.isSelected()) {
				Mezua alerta = (Mezua) TratatuGabekoList.getSelectedValue();
				if (alerta != null) {
					Ride ride= alerta.getRide();
					
					Nondiklbl.setText(ride.getIbilbide());
					Noralbl.setText("");
					Noizlbl.setText(ride.getDate().toString());
					Gidarialbl.setText(ride.getDriver().getIzenaAbizenaUser());
					NoizEskatua.setText(ride.getDate().toString());
					seatKop.setText(Integer.toString(ride.lortuEserlekuKopMin(alerta.getAlerta().getFrom(), alerta.getAlerta().getTo())));
					
					if(!alerta.isIrakurrita()) {
						BLFacade blf= MainGUI.getBusinessLogic();
						blf.mezuaIrakurrita(alerta);
						konprobatuEaAlertaIrakurriGabe(traveller);
						
					}

				}
			}else {
				Alerta alerta = (Alerta) TratatuGabekoList.getSelectedValue();
				if (alerta != null) {
				
					btnKanzelatu.setEnabled(true);
					Nondiklbl.setText(alerta.getFrom());
					Noralbl.setText(alerta.getTo());
					Noizlbl.setText(alerta.getWhen().toString());
					
				}
			}
			}
		});
		TratatuGabekoList.setModel(laguntzaile);

		scrollPane.setViewportView(TratatuGabekoList);
		TratatuGabekoList.setLayoutOrientation(JList.VERTICAL);
		scrollPane.setBounds(23, 60, 271, 219);
		contentPane.add(scrollPane);

		JButton btnBACK = new JButton(ResourceBundle.getBundle("Etiquetas").getString("RegisterGUI.Back"));
		btnBACK.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
			}
		});
		btnBACK.setBounds(286, 320, 85, 21);
		contentPane.add(btnBACK);
		btnBACK.setText(ResourceBundle.getBundle("Etiquetas").getString("RegisterGUI.Back"));
		
		
		buttonGroup_1.add(rdbtnIkusiGabeak);

		
		rdbtnIkusiGabeak.setVisible(false);
		rdbtnIkusiGabeak.setBounds(138, 320, 103, 21);
		contentPane.add(rdbtnIkusiGabeak);
		rdbtnIkusiGabeak.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				etiketakHustu();
				kargatuIkusiGabeak();
			
			}
		});
		buttonGroup_1.add(rdbtnIkusitakoak);

		
		rdbtnIkusitakoak.setVisible(false);
		rdbtnIkusitakoak.setText(ResourceBundle.getBundle("Etiquetas").getString("AlertakIkusiGUI.Ikusitakoak"));
		rdbtnIkusitakoak.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				etiketakHustu();
				
				kargatuIkusitakoak();
				
			}
		});
		rdbtnIkusitakoak.setBounds(23, 320, 103, 21);
		contentPane.add(rdbtnIkusitakoak);
		buttonGroup.add(rdbtnEsleituak);

		
		
		rdbtnEsleituak.setText(ResourceBundle.getBundle("Etiquetas").getString("AlertakIkusiGUI.Esleituak"));
		rdbtnEsleituak.setSelected(true);
		rdbtnIkusitakoak.setVisible(true);
		rdbtnIkusiGabeak.setVisible(true);
		rdbtnIkusiGabeak.setSelected(true);
		kargatuIkusiGabeak();
		rdbtnEsleituak.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				etiketakHustu();
				rdbtnIkusitakoak.setVisible(true);
				rdbtnIkusiGabeak.setVisible(true);
				rdbtnIkusiGabeak.setSelected(true);
				lblNewLabel_1.setText(ResourceBundle.getBundle("Etiquetas").getString("MyRidesGUI.Ibilbidea"));
				lblNewLabel_2.setVisible(false);
				lblNewLabel_4 .setVisible(true);
				lblNewLabel_5.setVisible(true);
				//lblNewLabel_6 .setVisible(false);
				btnKanzelatu.setEnabled(false);
				btnKanzelatu.setVisible(false);;
				seatlbl.setVisible(true);
				kargatuIkusiGabeak();
				
			}
		});
		rdbtnEsleituak.setBounds(26, 285, 103, 21);
		contentPane.add(rdbtnEsleituak);
		buttonGroup.add(btnSortutakoAlertak);

		// HAUEK TXUKUNDU ETA ETIKETAK ONDO JARRI ETA HORI DENA
		
		
		btnSortutakoAlertak.setText(ResourceBundle.getBundle("Etiquetas").getString("AlertakIkusiGUI.SortutakoAlertak"));
		btnSortutakoAlertak.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				etiketakHustu();
				rdbtnIkusitakoak.setVisible(false);
				rdbtnIkusiGabeak.setVisible(false);
				buttonGroup_1.clearSelection();
				btnKanzelatu.setVisible(true);
				btnKanzelatu.setEnabled(false);
				Gidarialbl.setText("");
				NoizEskatua.setText("");
				seatKop.setText("");
				lblNewLabel_1.setText(ResourceBundle.getBundle("Etiquetas").getString("TReservationsGUI.From"));
				lblNewLabel_2.setVisible(true);	
				
				lblNewLabel_4 .setVisible(false);
				lblNewLabel_5.setVisible(false);
				//lblNewLabel_6 .setVisible(false);
				seatlbl.setVisible(false);
				kargatuSortutakoAlertak();
				
			}
		});
		btnSortutakoAlertak.setBounds(138, 285, 156, 21);
		contentPane.add(btnSortutakoAlertak);
		
		 //$NON-NLS-1$ //$NON-NLS-2$
		btnKanzelatu.setVisible(false);
		btnKanzelatu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Alerta alerta = (Alerta) TratatuGabekoList.getSelectedValue();
				BLFacade blf= MainGUI.getBusinessLogic();
				blf.deuseztatuAlerta(alerta);
				
				 kargatuSortutakoAlertak();
			}
		});
		btnKanzelatu.setBounds(337, 285, 85, 21);
		contentPane.add(btnKanzelatu);
		

	}

	

	private void kargatuIkusitakoak() {
		laguntzaile.removeAllElements();
		BLFacade blf = MainGUI.getBusinessLogic();
		List<Mezua> lagun=blf.ikusitakoAlerta(traveller);
		List<Mezua> ikusitakoak=new ArrayList<Mezua>();
		for (Mezua r : lagun) {
			if(r.getRide().lortuEserlekuKopMin(r.getAlerta().getFrom(), r.getAlerta().getTo())>0) {
			ikusitakoak.add(r);
			}
		}
		Collections.sort(ikusitakoak,Collections.reverseOrder());
		laguntzaile.addAll(ikusitakoak);
	}

	private void kargatuIkusiGabeak() {
		laguntzaile.removeAllElements();
		BLFacade blf = MainGUI.getBusinessLogic();
		List<Mezua> lagun=blf.getIkusiGabeAlerta(traveller);
		List<Mezua> ikusiGabe=new ArrayList<Mezua>();
		for (Mezua r : lagun) {
			
			if(r.getRide().lortuEserlekuKopMin(r.getAlerta().getFrom(), r.getAlerta().getTo())>0) {
			ikusiGabe.add(r);
			}
			
		}
		
		Collections.sort(ikusiGabe,Collections.reverseOrder());
		laguntzaile.addAll(ikusiGabe);
	}

	private void kargatuSortutakoAlertak() {
		laguntzaile.removeAllElements();
		BLFacade blf = MainGUI.getBusinessLogic();
		List<Alerta> lagun=blf.kargatuTravellerAlertak(traveller);
		List<Alerta> sortutakoAlerta=new ArrayList<Alerta>();
		for (Alerta r : lagun) {
			
			 sortutakoAlerta.add(r);
			
		}
		//Collections.sort( sortutakoAlerta,Collections.reverseOrder());
		laguntzaile.addAll( sortutakoAlerta);
	}

	private void etiketakHustu() {

		Nondiklbl.setText("");
		Noralbl.setText("");
		Noizlbl.setText("");
		Gidarialbl.setText("");
		NoizEskatua.setText("");
		//Erantzunalbl.setText("");
		seatKop.setText("");
		
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
			rdbtnIkusiGabeak.setForeground(new Color(255, 0, 0));
		}else {
			rdbtnIkusiGabeak.setForeground(new Color(0, 0, 0));
		}
	}
}
