package gui;

import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import domain.Ride;
import domain.Traveller;
import com.toedter.calendar.JCalendar;

import businessLogic.BLFacade;
import configuration.UtilDate;

import java.awt.Rectangle;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Vector;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class AlertaGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private Traveller traveller;
	private JCalendar jCalendar1 = new JCalendar();
	private Calendar calendarAnt = null;
	private Calendar calendarAct = null;
	private JTextField textFrom=new JTextField();
	private JTextField textTo=new JTextField();
	private JLabel errorlbl = new JLabel("");
	/**
	 * Launch the application.
	 */
	

	/**
	 * Create the frame.
	 */
	public AlertaGUI(Traveller t) {
		traveller=t;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		
		jCalendar1.setBounds(new Rectangle(225, 10, 199, 152));
		contentPane.add(jCalendar1);
		
		
		textFrom.setBounds(78, 26, 96, 19);
		contentPane.add(textFrom);
		textFrom.setColumns(10);
		
		
		textTo.setBounds(78, 85, 96, 19);
		contentPane.add(textTo);
		textTo.setColumns(10);
		
		JButton backbtn = new JButton(ResourceBundle.getBundle("Etiquetas").getString("RegisterGUI.Back"));
		backbtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
			}
		});
		backbtn.setBounds(33, 232, 85, 21);
		contentPane.add(backbtn);
		
		JButton sortuAlertabtn = new JButton(ResourceBundle.getBundle("Etiquetas").getString("AlertaGUI.Create"));
		sortuAlertabtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String from=textFrom.getText();
				String to=textTo.getText();
				Date when=UtilDate.trim(jCalendar1.getDate());
				if(!to.isBlank()&&!from.isBlank()&&when!=null) {
					
					BLFacade blf= MainGUI.getBusinessLogic();
					
					List<Ride> rideList= blf.getRides(from, to, when);
					if(rideList.size()==0) {
						if(blf.getAlerta(traveller,from, to, when)==null) {
							errorlbl.setText("");
							if(when.before(new Date())) {
								errorlbl.setText(ResourceBundle.getBundle("Etiquetas").getString("AlertaGUI.AlertaAtzo"));
							}else {
							blf.sortuAlerta(traveller, from, to, when);
							}
						}else {
							
							errorlbl.setText(ResourceBundle.getBundle("Etiquetas").getString("AlertaGUI.AlertaExists"));
							
						}
					}else {
						errorlbl.setText(ResourceBundle.getBundle("Etiquetas").getString("AlertaGUI.BidaiaExists"));
						
					}
					}else {
						errorlbl.setText(ResourceBundle.getBundle("Etiquetas").getString("AlertaGUI.BeteGabe"));
				
				}
			}
		});
		sortuAlertabtn.setBounds(166, 232, 121, 21);
		contentPane.add(sortuAlertabtn);
		
		JLabel lblNewLabel = new JLabel("From");
		lblNewLabel.setBounds(10, 29, 45, 13);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("To:");
		lblNewLabel_1.setBounds(10, 88, 45, 13);
		contentPane.add(lblNewLabel_1);
		errorlbl.setForeground(new Color(255, 0, 0));
		
		 //$NON-NLS-1$ //$NON-NLS-2$
		errorlbl.setBounds(59, 186, 319, 13);
		contentPane.add(errorlbl);
		jCalendar1.addPropertyChangeListener(new PropertyChangeListener()
		{
		public void propertyChange(PropertyChangeEvent propertychangeevent)
		{
			if (propertychangeevent.getPropertyName().equals("locale"))
			{
				jCalendar1.setLocale((Locale) propertychangeevent.getNewValue());
			}
			else if (propertychangeevent.getPropertyName().equals("calendar"))
			{
				calendarAnt = (Calendar) propertychangeevent.getOldValue();
				calendarAct = (Calendar) propertychangeevent.getNewValue();
				

				
				DateFormat dateformat1 = DateFormat.getDateInstance(1, jCalendar1.getLocale());

				int monthAnt = calendarAnt.get(Calendar.MONTH);
				int monthAct = calendarAct.get(Calendar.MONTH);

				if (monthAct!=monthAnt) {
					if (monthAct==monthAnt+2) {
						// Si en JCalendar está 30 de enero y se avanza al mes siguiente, devolvería 2 de marzo (se toma como equivalente a 30 de febrero)
						// Con este código se dejará como 1 de febrero en el JCalendar
						calendarAct.set(Calendar.MONTH, monthAnt+1);
						calendarAct.set(Calendar.DAY_OF_MONTH, 1);
					}						

					jCalendar1.setCalendar(calendarAct);

				}
		 

			}
		} 
		
	});
	}
	}


