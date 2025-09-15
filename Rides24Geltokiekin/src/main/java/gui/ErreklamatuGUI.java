package gui;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ResourceBundle;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import businessLogic.BLFacade;
import domain.Erreklamazioa;
import domain.Profile;
import domain.RideRequest;

import javax.swing.JLabel;
import javax.swing.JEditorPane;
import javax.swing.JButton;
import java.awt.Color;

public class ErreklamatuGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JEditorPane editorPane;

	/**
	 * Create the frame.
	 */
	public ErreklamatuGUI(Profile nork,Profile nori, RideRequest r ) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblErreklamatu = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("ErreklamatuGUI.Erreklamatu"));
		lblErreklamatu.setBounds(193, 20, 55, 13);
		contentPane.add(lblErreklamatu);
		
		JLabel lblArazoa = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("ErreklamatuGUI.Arazoa"));
		lblArazoa.setBounds(34, 57, 85, 13);
		contentPane.add(lblArazoa);
		
		JEditorPane editorPane = new JEditorPane();
		editorPane.setBounds(34, 80, 369, 110);
		contentPane.add(editorPane);
		
		JButton btnBack = new JButton("Back");
		btnBack.setBounds(34, 214, 85, 21);
		contentPane.add(btnBack);
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				setVisible(false);
		
			}
		});
		btnBack.setText(ResourceBundle.getBundle("Etiquetas").getString("RegisterGUI.Back"));
		
		JButton btnErreklamatu = new JButton(ResourceBundle.getBundle("Etiquetas").getString("ErreklamatuGUI.Erreklamatu"));
		btnErreklamatu.setBounds(177, 214, 95, 21);
		contentPane.add(btnErreklamatu);
		
		JLabel errorlbl = new JLabel(""); //$NON-NLS-1$ //$NON-NLS-2$
		errorlbl.setForeground(new Color(255, 0, 0));
		errorlbl.setBounds(44, 200, 359, 13);
		contentPane.add(errorlbl);
		btnErreklamatu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!editorPane.getText().isBlank()) {
					BLFacade facade = MainGUI.getBusinessLogic();
					facade.gehituErreklamazioa(nork, nori,editorPane.getText(),r.getPrezioa(),  r);
					setVisible(false);
					//bidali mezua
				}else {
					errorlbl.setText(ResourceBundle.getBundle("Etiquetas").getString("ErreklamatuGUI.IdatziError"));
				}
				
				
		
			}
		});
		
		
	}
}
