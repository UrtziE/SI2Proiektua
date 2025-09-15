package gui;

import java.awt.EventQueue;
import java.util.ResourceBundle;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import businessLogic.BLFacade;
import domain.Driver;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class CreateCarGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
    private Driver driver;
    private JTextField matrikulaTxt;
    private JTextField tokiKopTxt;
    private JTextField modelotxt;
    private JTextField markaTxt;
    
    private JLabel Matrikulalbl = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("CreateCar.Matrikula"));
    private JLabel TokiKoplbl = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("CreateCar.TokiKop"));
    private JLabel Modelolbl = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("CreateCar.Modelo"));
    private JLabel Markalbl = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("CreateCar.Marka"));
    private JButton backbtn = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Close"));
    private JButton KotxeaSortubtn = new JButton(ResourceBundle.getBundle("Etiquetas").getString("CreateCar.Create")); 
    private JLabel errorlbl = new JLabel(""); //$NON-NLS-1$ //$NON-NLS-2$
	/**
	 * Launch the application.
	 */


	/**
	 * Create the frame.
	 */
	public CreateCarGUI(Driver d) {
		driver=d;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		
		Markalbl.setBounds(40, 60, 113, 19);
		contentPane.add(Markalbl);
		
		
		Modelolbl.setBounds(40, 89, 111, 19);
		contentPane.add(Modelolbl);
		
		
		TokiKoplbl.setBounds(40, 126, 113, 19);
		contentPane.add(TokiKoplbl);
		
		
		Matrikulalbl.setBounds(42, 162, 111, 19);
		contentPane.add(Matrikulalbl);
		KotxeaSortubtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				BLFacade blf= MainGUI.getBusinessLogic();
				errorlbl.setText("");
				int num=zeinKanpoHutsik();
				
				if(num==0&&isNumeric(tokiKopTxt.getText())) {
				boolean sortuta=blf.createCar(markaTxt.getText(), modelotxt.getText(), matrikulaTxt.getText(), Integer.parseInt(tokiKopTxt.getText()), driver);
				if(sortuta) {
					errorlbl.setForeground(java.awt.Color.black);
				errorlbl.setText(ResourceBundle.getBundle("Etiquetas").getString("CreateCar.WellCreated"));
				
				}else {
					errorlbl.setForeground(java.awt.Color.red);
					errorlbl.setText(ResourceBundle.getBundle("Etiquetas").getString("CreateCar.errorAlreadyExists"));
					
					
				}
				hustuEtiketak();
				}else {
					zeinErrore(num);
				}
			}
		});
		
		
		KotxeaSortubtn.setBounds(173, 224, 134, 37);
		contentPane.add(KotxeaSortubtn);
		backbtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
			}
		});
		
		
		backbtn.setBounds(40, 232, 85, 21);
		contentPane.add(backbtn);
		
		matrikulaTxt = new JTextField();
		matrikulaTxt.setBounds(185, 162, 96, 19);
		contentPane.add(matrikulaTxt);
		matrikulaTxt.setColumns(10);
		
		tokiKopTxt = new JTextField();
		tokiKopTxt.setBounds(185, 126, 96, 19);
		contentPane.add(tokiKopTxt);
		tokiKopTxt.setColumns(10);
		
		modelotxt = new JTextField();
		modelotxt.setBounds(185, 89, 96, 19);
		contentPane.add(modelotxt);
		modelotxt.setColumns(10);
		
		markaTxt = new JTextField();
		markaTxt.setBounds(185, 60, 96, 19);
		contentPane.add(markaTxt);
		markaTxt.setColumns(10);
		errorlbl.setBounds(42, 191, 303, 23);
		
		contentPane.add(errorlbl);
	}
	private int zeinKanpoHutsik() {
		if (markaTxt.getText().isBlank())
			return 1;
		else
		// AURRERAGO DATU BASEAREKIN BEGIRATU EA BESTE USER BERDINIK DAGOEN.
		if (modelotxt.getText().isBlank())
			return 2 ;
		else if (tokiKopTxt.getText().isBlank())
			return 4;
		else if (matrikulaTxt.getText().isBlank())
			return 3;
		else
			return 0;
	}
	private void zeinErrore(int num) {
		denakBeltzez();
		errorlbl.setForeground(java.awt.Color.red);
		switch (num) {
		
		case 1:
			errorlbl.setText(ResourceBundle.getBundle("Etiquetas").getString("CreateCar.errorMarka"));
			Markalbl.setForeground(java.awt.Color.red);
			//System.out.println();
			break;
		case 2:
			errorlbl.setText(ResourceBundle.getBundle("Etiquetas").getString("CreateCar.errorModelo"));
			Modelolbl.setForeground(java.awt.Color.red);
			//System.out.println();
			break;
		case 3:
			errorlbl.setText(ResourceBundle.getBundle("Etiquetas").getString("CreateCar.errorMatrikula"));
			Matrikulalbl.setForeground(java.awt.Color.red);
			//System.out.println();
			break;
		case 4:
			errorlbl.setText(ResourceBundle.getBundle("Etiquetas").getString("CreateCar.errorTokiKop"));
			TokiKoplbl.setForeground(java.awt.Color.red);
			//System.out.println();
			break;
		
			 
		}
	}
	private void denakBeltzez() {
		Markalbl.setForeground(java.awt.Color.black);
		Modelolbl.setForeground(java.awt.Color.black);
		Matrikulalbl.setForeground(java.awt.Color.black);
		TokiKoplbl.setForeground(java.awt.Color.black);
	}
	private void hustuEtiketak() {
		denakBeltzez();
		matrikulaTxt.setText("");
		tokiKopTxt.setText("");
		modelotxt.setText("");
		markaTxt.setText("");
	}
	private boolean isNumeric(String tokiKop) {

		try {
			
			int toki=Integer.parseInt(tokiKop);
			if(toki>9 ||toki<0) {
				errorlbl.setForeground(java.awt.Color.red);
				errorlbl.setText(ResourceBundle.getBundle("Etiquetas").getString("CreateCar.errorSeats"));
				TokiKoplbl.setForeground(java.awt.Color.red);
				return false;
			}
			return true;
		} catch (NumberFormatException e) {
			errorlbl.setForeground(java.awt.Color.red);
			errorlbl.setText(ResourceBundle.getBundle("Etiquetas").getString("CreateCar.errorLetters"));
			TokiKoplbl.setForeground(java.awt.Color.red);
			//System.out.println();
			return false;
		}
	}
}
