package gui;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;

import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import businessLogic.BLFacade;
import domain.Erreklamazioa;
import domain.Mezua;
import domain.Profile;

public class ErreklamazioakGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	private DefaultListModel laguntzaileT= new DefaultListModel();
	private DefaultListModel laguntzaileR= new DefaultListModel();
	private Profile id;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	public ErreklamazioakGUI(Profile id) {
		this.id=id;
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 636, 402);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblErreklamazioak = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("ErreklamazioakGUI.Erreklamazioak"));
		lblErreklamazioak.setHorizontalAlignment(SwingConstants.CENTER);
		lblErreklamazioak.setBounds(236, 21, 120, 14);
		contentPane.add(lblErreklamazioak);
		
		
		JButton btnBACK = new JButton(ResourceBundle.getBundle("Etiquetas").getString("RegisterGUI.Back"));
		btnBACK.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
			}
		});
		btnBACK.setBounds(57, 334, 85, 21);
		contentPane.add(btnBACK);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setBounds(57, 45, 492, 259);
		contentPane.add(scrollPane);
		JList Rides = new JList();
		scrollPane.setViewportView(Rides);
		Rides.setModel(laguntzaileR);
		
		kargatuErrekamazioak();
		
		
	}
	
	private void kargatuErrekamazioak() {
		laguntzaileR.removeAllElements();
		BLFacade blf= MainGUI.getBusinessLogic();
		List<Erreklamazioa> erreklamazioak=blf.getErreklamazioak(id);
		System.out.print(erreklamazioak.toString());
		for(Erreklamazioa m: erreklamazioak) {
			 laguntzaileR.addElement(m.toString());
				
		}
		
		
	}


}
