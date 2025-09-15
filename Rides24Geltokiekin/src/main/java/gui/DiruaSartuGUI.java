package gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import businessLogic.BLFacade;
import domain.Mezua;
import domain.Traveller;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.util.ResourceBundle;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;
import java.awt.Color;

public class DiruaSartuGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private BLFacade blf;
private Traveller traveller;
private JTextField DiruaSartuText;
private JLabel errorlbl = new JLabel(/*ResourceBundle.getBundle("Etiquetas").getString("DiruaSartuGUI.lblNewLabel_3.text")*/);
	/**
	 * Create the frame.
	 */
	public DiruaSartuGUI(Traveller t) {
		this.traveller=t;
		blf=MainGUI.getBusinessLogic();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		setTitle(ResourceBundle.getBundle("Etiquetas").getString("DepositeGUI.MainTitle") + " - traveller :"+traveller.getName());
		
		JLabel lblNewLabel = new JLabel("Zure dirua:");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblNewLabel.setBounds(63, 74, 87, 34);
		contentPane.add(lblNewLabel);
		lblNewLabel.setText(ResourceBundle.getBundle("Etiquetas").getString("DepositeGUI.YourMoney"));
		
		JLabel lblNewLabel_1 = new JLabel(Float.toString(blf.getMoney(traveller))+"€");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblNewLabel_1.setBounds(227, 87, 112, 21);
		contentPane.add(lblNewLabel_1);
		
		JButton btnDeposit = new JButton();
		btnDeposit.setText(ResourceBundle.getBundle("Etiquetas").getString("DepositeGUI.Deposite"));
		btnDeposit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			
				String diruaSartu=DiruaSartuText.getText();
				if(isNumericAndPositive(diruaSartu)) {
					float diruSartu=Float.parseFloat(diruaSartu);
					blf= MainGUI.getBusinessLogic();
					blf.sartuDirua(diruSartu, traveller);
					DiruaSartuText.setText("");
					
					
				}
				 lblNewLabel_1.setText(Float.toString(blf.getMoney(traveller))+"€"); 
				
			}
		});
		btnDeposit.setBounds(169, 195, 112, 34);
		contentPane.add(btnDeposit);
		
		JButton btnBack = new JButton("BACK");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
			}
		});
		btnBack.setBounds(24, 229, 85, 21);
		btnBack.setText(ResourceBundle.getBundle("Etiquetas").getString("RegisterGUI.Back"));
		contentPane.add(btnBack);
		
		DiruaSartuText = new JTextField();
		DiruaSartuText.setBounds(227, 130, 96, 19);
		contentPane.add(DiruaSartuText);
		DiruaSartuText.setColumns(10);
		
		JLabel lblNewLabel_2 = new JLabel("Zenbat sartu?");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblNewLabel_2.setBounds(63, 132, 98, 13);
		contentPane.add(lblNewLabel_2);
		lblNewLabel_2.setText(ResourceBundle.getBundle("Etiquetas").getString("DepositeGUI.Amount"));
		
		; //$NON-NLS-1$ //$NON-NLS-2$
		errorlbl.setForeground(new Color(255, 0, 0));
		errorlbl.setHorizontalAlignment(SwingConstants.CENTER);
		errorlbl.setBounds(121, 240, 202, 13);
		contentPane.add(errorlbl);
		
		JLabel lblDiruaSartu = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("DepositeGUI.MainTitle")); //$NON-NLS-1$ //$NON-NLS-2$
		lblDiruaSartu.setHorizontalAlignment(SwingConstants.CENTER);
		lblDiruaSartu.setFont(new Font("Tahoma", Font.PLAIN, 17));
		lblDiruaSartu.setBounds(10, 21, 414, 29);
		contentPane.add(lblDiruaSartu);
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

}
