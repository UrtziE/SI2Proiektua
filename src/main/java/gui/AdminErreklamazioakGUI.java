package gui;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.ResourceBundle;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import businessLogic.BLFacade;
import domain.Admin;
import domain.Erreklamazioa;
import domain.Profile;
import domain.Ride;


import javax.swing.JComboBox;
import javax.swing.JLabel;

import javax.swing.JButton;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JTextField;
import java.awt.Color;

public class AdminErreklamazioakGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private JTextField textKantitatea;

	private DefaultComboBoxModel erreklamazioak = new DefaultComboBoxModel();
	private JRadioButton rdbtnOnartu = new JRadioButton();
	private JRadioButton rdbtnDeuseztatu= new JRadioButton();
	private JLabel Egilealbl = new JLabel("");
	private JLabel Norilbl = new JLabel("");
	private JLabel Noizlbl = new JLabel("");
	private JLabel Nondiklbl = new JLabel(""); 
	private JLabel Noralbl = new JLabel(""); 
	private JLabel Arazoalbl = new JLabel(""); 
	private JLabel pricelbl = new JLabel(""); 
	private JLabel errorlbl;
	private Admin admin;
	
	
	
	public AdminErreklamazioakGUI(Admin a) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		admin=a;
		setBounds(100, 100, 528, 383);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setModel(erreklamazioak);
		comboBox.setBounds(38, 51, 183, 22);
		contentPane.add(comboBox);
		BLFacade blf= MainGUI.getBusinessLogic();
		erreklamazioak.addAll(blf.lortuErreklamazioakProzesuan(admin));
		if(erreklamazioak.getSize()>0) {
			comboBox.setSelectedIndex(0);
			kargatu((Erreklamazioa) comboBox.getItemAt(0));
		}
		
		comboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				clearEtiketak();
				Erreklamazioa erreklamazioa = (Erreklamazioa) comboBox.getSelectedItem();
				if (erreklamazioa != null) {
					kargatu(erreklamazioa);
					

				} 
				
			}
		});
		
		
		
		
		
		JLabel lblEgilea = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("AdminErreklamazioakGUI.Egilea"));
		lblEgilea.setBounds(256, 31, 69, 14);
		contentPane.add(lblEgilea);
		
		JLabel lblNori = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("AdminErreklamazioakGUI.Norentzat"));
		lblNori.setBounds(256, 55, 69, 14);
		contentPane.add(lblNori);
		
		JLabel lblNewLabel = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("TReservationsGUI.From"));
		lblNewLabel.setBounds(256, 79, 69, 14);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("TReservationsGUI.When"));
		lblNewLabel_1.setBounds(256, 126, 69, 14);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("ErreklamatuGUI.Arazoa"));
		lblNewLabel_2.setBounds(38, 96, 70, 14);
		contentPane.add(lblNewLabel_2);
		

		JLabel lblNewLabel_4 = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("TReservationsGUI.To"));
		lblNewLabel_4.setBounds(256, 103, 69, 13);
		contentPane.add(lblNewLabel_4);
		
		
		rdbtnOnartu = new JRadioButton(ResourceBundle.getBundle("Etiquetas").getString("O/DGUI.Accept"));
		buttonGroup.add(rdbtnOnartu);
		rdbtnOnartu.setBounds(291, 254, 69, 21);
		contentPane.add(rdbtnOnartu);
		
		rdbtnDeuseztatu = new JRadioButton(ResourceBundle.getBundle("Etiquetas").getString("O/DGUI.Reject"));
		buttonGroup.add(rdbtnDeuseztatu);
		rdbtnDeuseztatu.setBounds(370, 254, 89, 21);
		contentPane.add(rdbtnDeuseztatu);
		
		JLabel lblNewLabel_3 = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("DepositeGUI.Amount"));
		lblNewLabel_3.setBounds(268, 237, 69, 13);
		contentPane.add(lblNewLabel_3);
		
		textKantitatea = new JTextField();
		textKantitatea.setBounds(363, 234, 96, 19);
		contentPane.add(textKantitatea);
		textKantitatea.setColumns(10);
		
		Nondiklbl = new JLabel("");
		Nondiklbl.setBounds(311, 79, 193, 13);
		contentPane.add(Nondiklbl);
		
		Noralbl = new JLabel("");
		Noralbl.setBounds(311, 103, 193, 13);
		contentPane.add(Noralbl);
		
		Noizlbl = new JLabel("");
		Noizlbl.setBounds(311, 127, 193, 13);
		contentPane.add(Noizlbl);
		
		Egilealbl = new JLabel("");
		Egilealbl.setBounds(320, 32, 194, 13);
		contentPane.add(Egilealbl);
		
		
		Norilbl = new JLabel("");
		Norilbl.setBounds(335, 60, 172, 13);
		contentPane.add(Norilbl);
		
		Arazoalbl = new JLabel("");
		Arazoalbl.setBounds(38, 126, 208, 163);
		contentPane.add(Arazoalbl);
		
		JButton btnBack = new JButton("BACK");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				setVisible(false);
		
			}
		});
		btnBack.setBounds(23, 299, 85, 21);
		contentPane.add(btnBack);
		btnBack.setText(ResourceBundle.getBundle("Etiquetas").getString("RegisterGUI.Back"));
		
		JButton btnTakeNew = new JButton(ResourceBundle.getBundle("Etiquetas").getString("AdminErreklamazioakGUI.TakeNew"));
		btnTakeNew.setBounds(123, 299, 98, 21);
		contentPane.add(btnTakeNew);
		btnTakeNew.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				BLFacade blf = MainGUI.getBusinessLogic();
				Erreklamazioa erreklamazioa=blf.takeNewErreklamazioa(a);
				if(erreklamazioa == null) errorlbl.setText(ResourceBundle.getBundle("Etiquetas").getString("AdminErreklamazioakGUI.TakeNewError")); 
				else {
					System.out.println(erreklamazioa);
					erreklamazioak.addElement(erreklamazioa);
					kargatu(erreklamazioa);
					comboBox.setSelectedItem(erreklamazioa);
				}
				
				//bidali Mezua
			}
		});
		
		
		errorlbl = new JLabel(); //$NON-NLS-1$ //$NON-NLS-2$
		errorlbl.setForeground(new Color(255, 0, 0));
		errorlbl.setBounds(256, 191, 231, 14);
		contentPane.add(errorlbl);
		
		JButton btnBukatu = new JButton(ResourceBundle.getBundle("Etiquetas").getString("AdminErreklamazioakGUI.Bukatu"));
		btnBukatu.setBounds(341, 281, 89, 23);
		contentPane.add(btnBukatu);
		
		JLabel price = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("CreateRideGUI.Price")); //$NON-NLS-1$ //$NON-NLS-2$
		price.setBounds(256, 157, 55, 13);
		contentPane.add(price);
		
		//$NON-NLS-1$ //$NON-NLS-2$
		pricelbl.setBounds(335, 157, 169, 13);
		contentPane.add(pricelbl);
		btnBukatu.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				BLFacade blf = MainGUI.getBusinessLogic();
				Erreklamazioa erreklamazioa = (Erreklamazioa) comboBox.getSelectedItem();
				if(erreklamazioa!=null) {
				Profile nork = erreklamazioa.getNork();
				Profile nori = erreklamazioa.getNori();
				float dirua= 5;
				boolean onartuta=false;
				
				if(rdbtnOnartu.isSelected()) {
					String data = textKantitatea.getText();
					onartuta=true;
					if( isNumericAndPositive(data)) {
						dirua  = Float.parseFloat(data) ;
					}
					
				}
				blf.erreklamazioaOnartuEdoDeuseztatu(erreklamazioa, dirua, onartuta);
				clearEtiketak();
				erreklamazioak.removeElement(erreklamazioa);
				
			}else {
				errorlbl.setText(ResourceBundle.getBundle("Etiquetas").getString("AdminErreklamazioakGUI.TakeOneError"));
			}
			}
		});
		
	}
	private boolean isNumericAndPositive(String data) {

		try {
			float sartu=Float.parseFloat(data);
			if(sartu<0) {
				//GUIAN GORRIZ IDATZI
				errorlbl.setText(ResourceBundle.getBundle("Etiquetas").getString("DepositeGUI.errorNegative"));
				
				return false;
			}else {
			return true;
			}
		} catch (NumberFormatException e) {
			//GUIAN GORRIZ IDATZI
			errorlbl.setText(ResourceBundle.getBundle("Etiquetas").getString("DepositeGUI.errorLetters"));
			//System.out.println("Do not put letters on the money");
			return false;
		}
	}
	private void clearEtiketak() {
		rdbtnOnartu.setEnabled(false);
		rdbtnDeuseztatu.setEnabled(false);
		Egilealbl.setText("");
		Norilbl.setText("");
		Noizlbl.setText("");
		Nondiklbl.setText("");
		Noralbl.setText("");
		Arazoalbl.setText("");
		pricelbl.setText("");
	}
	
	private void kargatu(Erreklamazioa e) {
		rdbtnOnartu.setEnabled(true);
		rdbtnDeuseztatu.setEnabled(true);
		Egilealbl.setText(e.getNork().getFullName());
		Norilbl.setText(e.getNori().getFullName());
		
		
		Ride r = e.getRide();
		Nondiklbl.setText(r.getFrom().toString());
		Noralbl.setText(r.getTo().toString());
		Noizlbl.setText(r.getDate().toString());
		pricelbl.setText(String.valueOf(e.getPrezioa())+"€");
		Arazoalbl.setText(e.getDeskripzioa());
	}
}
