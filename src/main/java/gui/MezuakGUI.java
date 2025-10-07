package gui;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import businessLogic.BLFacade;
import domain.Mezua;
import domain.Profile;


import javax.swing.JLabel;

import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JRadioButton;
import javax.swing.ScrollPaneConstants;
import javax.swing.ButtonGroup;
import javax.swing.SwingConstants;

public class MezuakGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private DefaultListModel laguntzaileT= new DefaultListModel();
	private DefaultListModel laguntzaileR= new DefaultListModel();
	private Profile id;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private final ButtonGroup buttonGroup_1 = new ButtonGroup();
	public MezuakGUI(Profile id) {
		this.id=id;
		kargatuTransactions();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 636, 402);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblTransaction = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Mezuak.Transactions"));
		lblTransaction.setHorizontalAlignment(SwingConstants.CENTER);
		lblTransaction.setBounds(236, 21, 120, 14);
		contentPane.add(lblTransaction);
		
		
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
		
		JRadioButton rdbtnTransakzioak = new JRadioButton(ResourceBundle.getBundle("Etiquetas").getString("Mezuak.Transactions")); //$NON-NLS-1$ //$NON-NLS-2$
		buttonGroup_1.add(rdbtnTransakzioak);
		rdbtnTransakzioak.setBounds(176, 307, 109, 23);
		contentPane.add(rdbtnTransakzioak);
		rdbtnTransakzioak.setSelected(true);
		rdbtnTransakzioak.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				kargatuTransactions();
				lblTransaction.setText(ResourceBundle.getBundle("Etiquetas").getString("Mezuak.Transactions"));
				
			}
		});
		
		JRadioButton rdbtnErreklamazioak = new JRadioButton(ResourceBundle.getBundle("Etiquetas").getString("ErreklamazioakGUI.Erreklamazioak")); //$NON-NLS-1$ //$NON-NLS-2$
		buttonGroup_1.add(rdbtnErreklamazioak);
		rdbtnErreklamazioak.setBounds(314, 307, 109, 23);
		contentPane.add(rdbtnErreklamazioak);
		rdbtnErreklamazioak.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				kargatuErreklamazioak();
				lblTransaction.setText(ResourceBundle.getBundle("Etiquetas").getString("ErreklamazioakGUI.Erreklamazioak"));
				
			}
		});
		
		
	}
	private void kargatuErreklamazioak() {
		laguntzaileR.removeAllElements();
		BLFacade blf= MainGUI.getBusinessLogic();
		List<Mezua> mezuak=blf.getErreklamazioMezuak(id);
		Collections.sort(mezuak,Collections.reverseOrder());
		for(Mezua m: mezuak) {
			 laguntzaileR.addElement(m.getInfo());
			
		}
	}
	private void kargatuTransactions() {
		laguntzaileR.removeAllElements();
		BLFacade blf= MainGUI.getBusinessLogic();
		List<Mezua> mezuak=blf.getMezuak(id);
		Collections.sort(mezuak,Collections.reverseOrder());
		for(Mezua m: mezuak) {
			 laguntzaileR.addElement(m.getInfo());
			
		}
	}

	
}
