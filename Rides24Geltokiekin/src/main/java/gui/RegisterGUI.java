package gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import businessLogic.BLFacade;
import businessLogic.BLFacadeImplementation;
import domain.Driver;
import domain.Profile;
import domain.Traveller;

import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.ButtonGroup;
import java.awt.event.ActionListener;
import java.util.ResourceBundle;
import java.awt.event.ActionEvent;
import java.awt.Color;
import javax.swing.SwingConstants;
import java.awt.Font;

public class RegisterGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField userText;
	private JTextField nameText;
	private JTextField surnameText;
	private JTextField tlfText;
	private JTextField emailText;
	private JPasswordField password;
	private JPasswordField repeatPassword;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private boolean onartuta;
	private String type;
	private JLabel errorlbl = new JLabel();
	private JLabel lbluser = new JLabel("user:");
	private JLabel lblname = new JLabel("name:");
	private JLabel lblsurname = new JLabel("surname:");
	private JLabel lbltlf = new JLabel("tlf:");
	private JLabel lblemail = new JLabel("email:");
	private JLabel lblpassword = new JLabel("password");
	private JLabel lblRpassword = new JLabel("repeat password");
	/**
	 * Launch the application.
	 */


	/**
	 * Create the frame.
	 */
	

	public RegisterGUI() {
		denakBeltzez();
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 560, 413);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		setTitle(ResourceBundle.getBundle("Etiquetas").getString("RegisterGUI.MainTitle"));
		lbluser.setBounds(38, 70, 58, 13);
		contentPane.add(lbluser);
		lbluser.setText(ResourceBundle.getBundle("Etiquetas").getString("RegisterGUI.User"));
		

		
		lblname.setBounds(38, 202, 58, 13);
		contentPane.add(lblname);
		lblname.setText(ResourceBundle.getBundle("Etiquetas").getString("RegisterGUI.Name"));

		
		lblsurname.setBounds(272, 202, 62, 13);
		contentPane.add(lblsurname);
		lblsurname.setText(ResourceBundle.getBundle("Etiquetas").getString("RegisterGUI.Surname"));

		
		lbltlf.setBounds(38, 248, 26, 13);
		contentPane.add(lbltlf);

		
		lblemail.setBounds(272, 248, 45, 13);
		contentPane.add(lblemail);

		JRadioButton rdbtnDriver = new JRadioButton("Driver");
		buttonGroup.add(rdbtnDriver);
		rdbtnDriver.setBounds(388, 140, 103, 21);
		contentPane.add(rdbtnDriver);
		rdbtnDriver.setText(ResourceBundle.getBundle("Etiquetas").getString("RegisterGUI.Driver"));

		JRadioButton rdbtnTraveller = new JRadioButton("Traveller");
		buttonGroup.add(rdbtnTraveller);
		rdbtnTraveller.setSelected(true);
		rdbtnTraveller.setBounds(388, 102, 103, 21);
		contentPane.add(rdbtnTraveller);
		rdbtnTraveller.setText(ResourceBundle.getBundle("Etiquetas").getString("RegisterGUI.Traveller"));

		
		lblpassword.setBounds(38, 110, 96, 13);
		contentPane.add(lblpassword);
		lblpassword.setText(ResourceBundle.getBundle("Etiquetas").getString("RegisterGUI.Password"));

		
		lblRpassword.setBounds(38, 146, 126, 16);
		contentPane.add(lblRpassword);
		lblRpassword.setText(ResourceBundle.getBundle("Etiquetas").getString("RegisterGUI.RPassword"));

		JButton btnHome = new JButton("BACK");
		btnHome.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				setVisible(false);
		
			}
		});
		btnHome.setBounds(26, 335, 85, 21);
		contentPane.add(btnHome);
		btnHome.setText(ResourceBundle.getBundle("Etiquetas").getString("RegisterGUI.Back"));
		
		
		JButton btnRegister = new JButton("REGISTER");
		btnRegister.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				int zki = zeinKanpoHutsik();
				if (zki == 0) {
					String pwd = new String(password.getPassword());

					if (rdbtnTraveller.isSelected()) {
						type = "Traveller";

					} else {

						type = "Driver";
					}
					BLFacade facade = MainGUI.getBusinessLogic();
					Profile user = facade.register(emailText.getText(), nameText.getText(), surnameText.getText(),
							userText.getText(), pwd, tlfText.getText(), type);
					if (user!=null) {
						if(user instanceof Traveller) {
				
							JFrame a= new TravellerGUI((Traveller)user);
							a.setVisible(true);
			
						}else if(user instanceof Driver) {
							
							JFrame a= new DriverGUI((Driver)user);
							a.setVisible(true);
						}
						setVisible(false);
					} else {
						errorlbl.setText(ResourceBundle.getBundle("Etiquetas").getString("RegisterGUI.errorUserAlreadyExist"));
						lbluser.setForeground(java.awt.Color.red);
						
					}
				} else {
					zeinErrore(zki);
					
				}

			}
		});
		btnRegister.setBounds(231, 308, 103, 43);
		contentPane.add(btnRegister);
		btnRegister.setText(ResourceBundle.getBundle("Etiquetas").getString("MainGUI.Register"));

		userText = new JTextField();
		userText.setBounds(164, 66, 120, 19);
		contentPane.add(userText);
		userText.setColumns(10);

		nameText = new JTextField();
		nameText.setBounds(110, 202, 139, 19);
		contentPane.add(nameText);
		nameText.setColumns(10);

		surnameText = new JTextField();
		surnameText.setBounds(340, 202, 175, 19);
		contentPane.add(surnameText);
		surnameText.setColumns(10);

		tlfText = new JTextField();
		tlfText.setBounds(110, 244, 139, 19);
		contentPane.add(tlfText);
		tlfText.setColumns(10);

		emailText = new JTextField();
		emailText.setBounds(340, 244, 175, 19);
		contentPane.add(emailText);
		emailText.setColumns(10);

		password = new JPasswordField();
		password.setBounds(164, 106, 175, 19);
		contentPane.add(password);

		repeatPassword = new JPasswordField();
		repeatPassword.setBounds(164, 144, 175, 19);
		contentPane.add(repeatPassword);
		
		errorlbl.setText("");
		errorlbl.setHorizontalAlignment(SwingConstants.CENTER);
		errorlbl.setForeground(new Color(255, 0, 0));
		errorlbl.setBounds(149, 274, 257, 23);
		contentPane.add(errorlbl);
		
		JLabel lblRegister = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("RegisterGUI.MainTitle")); //$NON-NLS-1$ //$NON-NLS-2$
		lblRegister.setHorizontalAlignment(SwingConstants.CENTER);
		lblRegister.setFont(new Font("Tahoma", Font.PLAIN, 17));
		lblRegister.setBounds(10, 11, 524, 31);
		contentPane.add(lblRegister);
	}

	private int zeinKanpoHutsik() {
		if(emailText.getText().isBlank()||emailText.getText().isEmpty()) {
			emailText.setText(null);
		}
		if (userText.getText().isBlank())
			return 1;
		else
		// AURRERAGO DATU BASEAREKIN BEGIRATU EA BESTE USER BERDINIK DAGOEN.
		if (nameText.getText().isBlank())
			return 2;
		else if (surnameText.getText().isBlank())
			return 3;
		else if (tlfText.getText().isBlank())
			return 4;
		else if (!isNumeric(tlfText.getText()))
			return 8;
		else if (new String(password.getPassword()).isBlank() || new String(repeatPassword.getPassword()).isBlank())
			return 5;
		else if (!new String(password.getPassword()).equals(new String(repeatPassword.getPassword())))
			return 7;
		else
			return 0;
	}
    //Idatzi GUIAN GORRIZ.
	
	private void denakBeltzez() {
		lbluser.setForeground(java.awt.Color.black);
		lblname.setForeground(java.awt.Color.black);
		lblsurname.setForeground(java.awt.Color.black);
		lbltlf.setForeground(java.awt.Color.black);
		lblpassword.setForeground(java.awt.Color.black);
		lblRpassword.setForeground(java.awt.Color.black);
	}
	
	private void zeinErrore(int num) {
		denakBeltzez();
		switch (num) {
		case 1:
			errorlbl.setText(ResourceBundle.getBundle("Etiquetas").getString("RegisterGUI.errorUser"));
			lbluser.setForeground(java.awt.Color.red);
			//System.out.println();
			break;
		case 2:
			errorlbl.setText(ResourceBundle.getBundle("Etiquetas").getString("RegisterGUI.errorName"));
			lblname.setForeground(java.awt.Color.red);
			//System.out.println();
			break;
		case 3:
			errorlbl.setText(ResourceBundle.getBundle("Etiquetas").getString("RegisterGUI.errorSurname"));
			lblsurname.setForeground(java.awt.Color.red);
			//System.out.println();
			break;
		case 4:
			errorlbl.setText(ResourceBundle.getBundle("Etiquetas").getString("RegisterGUI.errorTlf"));
			lbltlf.setForeground(java.awt.Color.red);
			//System.out.println();
			break;
		case 5:
			errorlbl.setText(ResourceBundle.getBundle("Etiquetas").getString("RegisterGUI.errorPwd"));
			lblpassword.setForeground(java.awt.Color.red);
			//System.out.println();
			break;
		case 7:
			errorlbl.setText(ResourceBundle.getBundle("Etiquetas").getString("RegisterGUI.errorPwdIncorrect"));
			lblRpassword.setForeground(java.awt.Color.red);
			//System.out.println();
			break;
		case 8:
			lbltlf.setForeground(java.awt.Color.red);
			//System.out.println();
			break;
		}
	}

	private boolean isNumeric(String data) {

		try {
			data = data.replaceAll("[^0-9]", "");
			Integer.parseInt(data);
			if (data.length() != 9) {
				errorlbl.setText(ResourceBundle.getBundle("Etiquetas").getString("RegisterGUI.errorTlfshort"));
				return false;
			} else {
				return true;
			}
		} catch (NumberFormatException e) {
			errorlbl.setText(ResourceBundle.getBundle("Etiquetas").getString("RegisterGUI.errorLettersTlf"));
			//System.out.println();
			return false;
		}
	}

	public void Desagertarazi() {
		this.setVisible(false);
	}
}
