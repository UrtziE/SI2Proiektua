package gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import businessLogic.BLFacade;
import domain.Driver;
import domain.Profile;
import domain.Ride;
import domain.RideRequest;
import domain.Traveller;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.ResourceBundle;
import java.awt.event.ActionEvent;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import javax.swing.ButtonModel;

public class BaloratuGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private Profile nork;
	private Profile nori;
	private DefaultComboBoxModel balorazioLaguntzaile = new DefaultComboBoxModel();
	private final ButtonGroup buttonGroup = new ButtonGroup();
	
	public BaloratuGUI(Profile nork, Profile nori,RideRequest r) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 346, 212);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		this.nork=nork;
		this.nori=nori;
		
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JRadioButton rdbtn1 = new JRadioButton("1");
		buttonGroup.add(rdbtn1);
		rdbtn1.setBounds(69, 50, 38, 21);
		contentPane.add(rdbtn1);
		
		JRadioButton rdbtn2 = new JRadioButton("2");
		buttonGroup.add(rdbtn2);
		rdbtn2.setBounds(109, 50, 38, 21);
		contentPane.add(rdbtn2);
		
		JRadioButton rdbtn3 = new JRadioButton("3");
		rdbtn3.setSelected(true);
		buttonGroup.add(rdbtn3);
		rdbtn3.setBounds(149, 50, 38, 21);
		contentPane.add(rdbtn3);
		
		JRadioButton rdbtn4 = new JRadioButton("4");
		buttonGroup.add(rdbtn4);
		rdbtn4.setBounds(189, 50, 38, 21);
		contentPane.add(rdbtn4);
		
		JRadioButton rdbtn5 = new JRadioButton("5");
		buttonGroup.add(rdbtn5);
		rdbtn5.setBounds(229, 50, 38, 21);
		contentPane.add(rdbtn5);
		kargatuBalorazioak();
		
		JButton btnBaloratu = new JButton(ResourceBundle.getBundle("Etiquetas").getString("BaloratuGUI.Baloratu"));
		btnBaloratu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				int balo=-1;
				if(rdbtn1.isSelected()) {
					balo=1;
				}else if(rdbtn2.isSelected()) {
					balo=2;
				}else if(rdbtn3.isSelected()) {
					balo=3;
				}else if(rdbtn4.isSelected()) {
					balo=4;
				}else if(rdbtn5.isSelected()) {
					balo=5;
				}
				
				if(balo!=-1){
					BLFacade blf=MainGUI.getBusinessLogic();
					blf.baloratu(balo,nori,r);
					
					boolean erreklamatua= false;
					if(nori instanceof Traveller) {
						erreklamatua= r.isErreklamatuaTraveller();
					}else {
						erreklamatua= r.isErreklamatuaDriver();
					}
					
					if(!erreklamatua) {
						ErreklamatuGUI erre = new ErreklamatuGUI(nork,nori,r);
						erre.setVisible(true);
					}
					
					
					setVisible(false);
					
				}
				
				
			}
		});
		btnBaloratu.setBounds(118, 91, 109, 21);
		contentPane.add(btnBaloratu);
		
		JButton btnBack = new JButton(ResourceBundle.getBundle("Etiquetas").getString("BaloratuGUI.EzBaloratu"));
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				BLFacade blf=MainGUI.getBusinessLogic();;
				ErreklamatuGUI erre = new ErreklamatuGUI(nork,nori,r);
				erre.setVisible(true);
				
				setVisible(false);
			}
		});
		btnBack.setBounds(32, 144, 123, 21);
		contentPane.add(btnBack);
	
	}
	private void kargatuBalorazioak() {
		balorazioLaguntzaile.removeAllElements();
		balorazioLaguntzaile.addElement("");
		for(int i=1; i<=5;i++) {
			balorazioLaguntzaile.addElement(i);
		}
	}
}
