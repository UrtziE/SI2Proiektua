package gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import businessLogic.BLFacade;
import domain.Admin;
import domain.Driver;
import domain.Profile;
import domain.Traveller;

import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JButton;
import javax.swing.ButtonGroup;
import java.awt.event.ActionListener;
import java.util.ResourceBundle;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;
import java.awt.Color;

public class LoginGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textuser;
	private JPasswordField passwordField;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private static JLabel errorlbl = new JLabel(/*ResourceBundle.getBundle("Etiquetas").getString("LoginGUI.lblNewLabel_1.text")*/);
	private static final String LETRA_MOTA = "Tahoma";

	/**
	 * Launch the application.
	 */

	/**
	 * Create the frame.
	 */
	public LoginGUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 448, 332);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lbluser = new JLabel("user:");
		lbluser.setFont(new Font(LETRA_MOTA, Font.PLAIN, 13));
		lbluser.setBounds(35, 71, 112, 18);
		contentPane.add(lbluser);
		lbluser.setText(ResourceBundle.getBundle("Etiquetas").getString("RegisterGUI.User"));

		JLabel lblpassword = new JLabel("Password: ");
		lblpassword.setFont(new Font(LETRA_MOTA, Font.PLAIN, 13));
		lblpassword.setBounds(35, 115, 112, 29);
		contentPane.add(lblpassword);
		lblpassword.setText(ResourceBundle.getBundle("Etiquetas").getString("RegisterGUI.Password"));

		textuser = new JTextField();
		textuser.setBounds(174, 71, 183, 19);
		contentPane.add(textuser);
		textuser.setColumns(10);

		JButton btnRegister = new JButton("Register");
		btnRegister.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame a = new RegisterGUI();
				a.setVisible(true);
			}
		});
		btnRegister.setBounds(228, 166, 112, 18);
		contentPane.add(btnRegister);
		btnRegister.setText(ResourceBundle.getBundle("Etiquetas").getString("MainGUI.Register"));

		JLabel lblNewLabel = new JLabel("Ez duzu konturik?");
		lblNewLabel.setFont(new Font(LETRA_MOTA, Font.PLAIN, 13));
		lblNewLabel.setBounds(35, 166, 167, 29);
		contentPane.add(lblNewLabel);
		lblNewLabel.setText(ResourceBundle.getBundle("Etiquetas").getString("LoginGUI.Question"));
		
		JButton btnHome = new JButton("HOME");
		btnHome.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				setVisible(false);
				
			}
		});
		btnHome.setBounds(21, 261, 85, 21);
		contentPane.add(btnHome);
		btnHome.setText(ResourceBundle.getBundle("Etiquetas").getString("LoginGUI.Home"));

		JButton btnLogin = new JButton("LOGIN");
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				BLFacade blf = MainGUI.getBusinessLogic();
				if (zeinKanpoHutsik() == 0) {
					Profile p=blf.login(textuser.getText(),new String( passwordField.getPassword()));
					if (p!=null) {
						if(p instanceof Traveller) {
							Traveller usert= (Traveller)p;
							JFrame a= new TravellerGUI(usert);
							a.setVisible(true);
							
						}else if(p instanceof Driver) {
							Driver userd=(Driver)p;
							JFrame a= new DriverGUI(userd);
							a.setVisible(true);
						}
						else if(p instanceof Admin) {
							Admin userd=(Admin)p;
							JFrame a= new AdminGUI(userd);
							a.setVisible(true);
						}
						setVisible(false);
						
					} else {
						LoginGUI.setErrorText(ResourceBundle.getBundle("Etiquetas").getString("LoginGUI.errorUserNotExist"));
					}
				} else {
					zeinErrore(zeinKanpoHutsik());
				}
			}
		});
		btnLogin.setBounds(164, 216, 178, 40);
		contentPane.add(btnLogin);
		btnLogin.setText(ResourceBundle.getBundle("Etiquetas").getString("MainGUI.Login"));

		passwordField = new JPasswordField();
		passwordField.setBounds(174, 120, 183, 19);
		contentPane.add(passwordField);
		errorlbl.setForeground(new Color(255, 0, 0));
		
		 //$NON-NLS-1$ //$NON-NLS-2$
		errorlbl.setText("");
		errorlbl.setFont(new Font(LETRA_MOTA, Font.PLAIN, 14));
		errorlbl.setHorizontalAlignment(SwingConstants.CENTER);
		errorlbl.setBounds(144, 267, 183, 23);
		contentPane.add(errorlbl);
		
		JLabel lblLogin = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("LoginGUI.MainTitle")); //$NON-NLS-1$ //$NON-NLS-2$
		lblLogin.setHorizontalAlignment(SwingConstants.CENTER);
		lblLogin.setFont(new Font(LETRA_MOTA, Font.PLAIN, 17));
		lblLogin.setBounds(10, 11, 412, 30);
		contentPane.add(lblLogin);
		setTitle(ResourceBundle.getBundle("Etiquetas").getString("LoginGUI.MainTitle"));
	}
   //IDAZTEA GUIAN GORRIZ.
	private int zeinKanpoHutsik() {

		if (textuser.getText().isBlank())
			return 1;
		else if (new String(passwordField.getPassword()).isBlank())
			return 2;

		else
			return 0;
	}

	private void zeinErrore(int num) {
		switch (num) {
		
				
		case 1:
			errorlbl.setText(ResourceBundle.getBundle("Etiquetas").getString("LoginGUI.errorUser"));
			//System.out.println("Bete usuarioa");
			break;
		case 2:
			errorlbl.setText(ResourceBundle.getBundle("Etiquetas").getString("LoginGUI.errorPwd"));
			//System.out.println();
			break;
		}
	}
	public static void setErrorText(String error) {
		errorlbl.setText(error);
	}
}
