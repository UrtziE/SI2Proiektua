package gui;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ResourceBundle;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import businessLogic.BLFacade;
import domain.Driver;
import domain.Mezua;

import java.awt.Color;
import javax.swing.SwingConstants;

public class DiruaAteraGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private Driver driver;
	private JTextField DiruaAteraText;
	private BLFacade blf;
	private JLabel errorlbl = new JLabel(/*ResourceBundle.getBundle("Etiquetas").getString("DiruaAteraGUI.lblNewLabel_1.text")*/);

	/**
	 * Launch the application.
	 */


	/**
	 * Create the frame.
	 */
	public DiruaAteraGUI(Driver d) {
		this.driver=d;
		blf=MainGUI.getBusinessLogic();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Zure dirua:");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblNewLabel.setBounds(63, 82, 87, 34);
		contentPane.add(lblNewLabel);
		lblNewLabel.setText(ResourceBundle.getBundle("Etiquetas").getString("DepositeGUI.YourMoney"));
		
		JLabel DiruaAteralbl = new JLabel(Float.toString(blf.getMoney(driver))+"€");
		DiruaAteralbl.setFont(new Font("Tahoma", Font.PLAIN, 13));
		DiruaAteralbl.setBounds(240, 89, 112, 21);
		contentPane.add(DiruaAteralbl);
		
		setTitle(ResourceBundle.getBundle("Etiquetas").getString("WithDrawMoneyGUI.MainTitle") + " - driver :"+driver.getName());
		
		JButton btnDeposit = new JButton("WITHDRAW");
		btnDeposit.setText(ResourceBundle.getBundle("Etiquetas").getString("WithDrawMoneyGUI.WithDraw"));
		btnDeposit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			
				String diruaAtera=DiruaAteraText.getText();
				if(isPosibleWithDraw(diruaAtera)) {
					float diruAtera=Float.parseFloat(diruaAtera);
					BLFacade blf= MainGUI.getBusinessLogic();
					blf.ateraDirua(diruAtera, driver);
					DiruaAteraText.removeAll();
					
					
				}
				DiruaAteralbl.setText(Float.toString(blf.getMoney(driver))+"€");
				
			}
		});
		btnDeposit.setBounds(165, 195, 112, 34);
		contentPane.add(btnDeposit);
		btnDeposit.setText(ResourceBundle.getBundle("Etiquetas").getString("WithdrawGUI.Withdraw"));
		
		JButton btnBack = new JButton("BACK");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
			}
		});
		btnBack.setBounds(10, 229, 85, 21);
		contentPane.add(btnBack);
		btnBack.setText(ResourceBundle.getBundle("Etiquetas").getString("RegisterGUI.Back"));
		
		
		JLabel lblNewLabel_2 = new JLabel("Zenbat atera?");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblNewLabel_2.setBounds(63, 127, 98, 13);
		contentPane.add(lblNewLabel_2);
		lblNewLabel_2.setText(ResourceBundle.getBundle("Etiquetas").getString("DepositeGUI.Amount"));
		
		DiruaAteraText = new JTextField();
		DiruaAteraText.setBounds(240, 125, 96, 19);
		contentPane.add(DiruaAteraText);
		DiruaAteraText.setColumns(10);
		
		 //$NON-NLS-1$ //$NON-NLS-2$
		errorlbl.setHorizontalAlignment(SwingConstants.CENTER);
		errorlbl.setForeground(new Color(255, 0, 0));
		errorlbl.setBounds(97, 240, 327, 23);
		contentPane.add(errorlbl);
		
		JLabel lblDiruaAtera = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("WithDrawMoneyGUI.MainTitle")); //$NON-NLS-1$ //$NON-NLS-2$
		lblDiruaAtera.setHorizontalAlignment(SwingConstants.CENTER);
		lblDiruaAtera.setFont(new Font("Tahoma", Font.PLAIN, 17));
		lblDiruaAtera.setBounds(10, 28, 414, 21);
		contentPane.add(lblDiruaAtera);
	}
	private boolean isPosibleWithDraw(String data) {

		try {
			float sartu=Float.parseFloat(data);
			if(sartu<0||sartu>driver.getWallet()) {
				errorlbl.setText(ResourceBundle.getBundle("Etiquetas").getString("WithDrawGUI.errorNotEnough"));
				//System.out.println("Ezin da negatiboa izan dirua");
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
