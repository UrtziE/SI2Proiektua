package gui;

import java.text.DateFormat;
import java.util.*;
import java.util.List;

import javax.swing.*;

import com.toedter.calendar.JCalendar;

import java.awt.*;
import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import businessLogic.BLFacade;
import configuration.UtilDate;
import domain.Driver;
import domain.Kotxe;
import domain.Ride;
import exceptions.RideAlreadyExistException;
import exceptions.RideMustBeLaterThanTodayException;

public class CreateRideGUI extends JFrame {
	private static final long serialVersionUID = 1L;

	
	private Driver driver;
	private JTextField fieldOrigin=new JTextField();
	private JTextField fieldDestination=new JTextField();
	
	private JLabel jLabelOrigin = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("CreateRideGUI.LeavingFrom"));
	private JLabel jLabelDestination = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("CreateRideGUI.GoingTo")); 
	private JLabel jLabelSeats = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("CreateRideGUI.NumberOfSeats"));
	private JLabel jLabRideDate = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("CreateRideGUI.RideDate"));
	private JLabel jLabelPrice = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("CreateRideGUI.Price"));
	private JLabel lblKotxe = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("CreateRideGUI.Kotxe")); 
	private JTextField jTextFieldPrice = new JTextField();
	private DefaultComboBoxModel kotxeLagun= new DefaultComboBoxModel();
	private DefaultComboBoxModel eserlekuLagun= new DefaultComboBoxModel();
    private JComboBox<Kotxe> comboBoxKotxe = new JComboBox<Kotxe>();
    private JComboBox<Integer> comboBoxSeats = new JComboBox<Integer>();
    private JButton btnGordeHiriak = new JButton(ResourceBundle.getBundle("Etiquetas").getString("CreateRideGUI.GordeHiriak")); 
    private JLabel formatulbl = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("CreateRideGUI.FormatuHonetan")); 
    private JTextField ibilbideText = new JTextField();
    
	private JCalendar jCalendar = new JCalendar();
	private Calendar calendarAct = null;
	private Calendar calendarAnt = null;

	private JScrollPane scrollPaneEvents = new JScrollPane();

	private JButton jButtonCreate = new JButton(ResourceBundle.getBundle("Etiquetas").getString("CreateRideGUI.CreateRide"));
	private JButton jButtonClose = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Close"));
	private JLabel jLabelMsg = new JLabel();
	private JLabel jLabelError = new JLabel();
	private JLabel hiriGehigarriak = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("CreateRideGUI.HiriGehigarriak"));
	
	private List<Date> datesWithEventsCurrentMonth;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private JLabel hiriGehiagolbl = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("CreateRideGUI.GeltokiGehiago"));
	private JLabel prezioPartziallbl = new JLabel(""); 
	private JRadioButton rdbtnEz = new JRadioButton(ResourceBundle.getBundle("Etiquetas").getString("TReservationsGUI.No")); //$NON-NLS-1$ //$NON-NLS-2$
	private JButton btnEdit = new JButton(ResourceBundle.getBundle("Etiquetas").getString("CreateRideGUI.Edit")); //$NON-NLS-1$ //$NON-NLS-2$
	private JLabel prezioakEsleitzekolbl = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("CreateRideGUI.GordePrezioa")); //$NON-NLS-1$ //$NON-NLS-2$
	private  JButton gordePreziobtn = new JButton(ResourceBundle.getBundle("Etiquetas").getString("CreateRideGUI.JarriPrezioa")); //$NON-NLS-1$ //$NON-NLS-2$
	private JLabel bidaiaAnitzarenPreziototala = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("CreateRideGUI.Prezioa")); 
	private LinkedList<String>ibilbideaEtiketarako;
	private LinkedList<Float>prezioak=new LinkedList<Float>();
	public CreateRideGUI(Driver driver) {

		this.driver=driver;
		this.getContentPane().setLayout(null);
		this.setSize(new Dimension(628, 449));
		this.setTitle(ResourceBundle.getBundle("Etiquetas").getString("CreateRideGUI.CreateRide"));
		
		comboBoxKotxe.setModel(kotxeLagun);
		comboBoxSeats.setModel(eserlekuLagun);
		
		kargatuKotxeak();
		if(kotxeLagun.getSize()>0) {
			kargatuEserlekuKopurua((Kotxe)comboBoxKotxe.getSelectedItem());
		}

		jLabelOrigin.setBounds(new Rectangle(6, 56, 92, 20));
		jLabelSeats.setBounds(new Rectangle(6, 310, 173, 20));
		
		jLabelPrice.setBounds(new Rectangle(6, 226, 173, 20));
		jTextFieldPrice.setBounds(new Rectangle(192, 227, 60, 20));

		jCalendar.setBounds(new Rectangle(350, 50, 225, 150));
		scrollPaneEvents.setBounds(new Rectangle(25, 44, 346, 116));

		jButtonCreate.setBounds(new Rectangle(100, 372, 130, 30));

		jButtonCreate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jButtonCreate_actionPerformed(e);
			}
		});
		jButtonClose.setBounds(new Rectangle(278, 372, 130, 30));
		jButtonClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jButtonClose_actionPerformed(e);
			}
		});

		jLabelMsg.setBounds(new Rectangle(299, 247, 305, 20));
		jLabelMsg.setForeground(Color.red);

		jLabelError.setBounds(new Rectangle(260, 330, 320, 20));
		jLabelError.setForeground(Color.red);

		this.getContentPane().add(jLabelMsg, null);
		this.getContentPane().add(jLabelError, null);

		this.getContentPane().add(jButtonClose, null);
		this.getContentPane().add(jButtonCreate, null);

		this.getContentPane().add(jLabelSeats, null);
		this.getContentPane().add(jLabelOrigin, null);
		

		

		this.getContentPane().add(jCalendar, null);
		
		this.getContentPane().add(jLabelPrice, null);
		this.getContentPane().add(jTextFieldPrice, null);

		
		
		
		BLFacade facade = MainGUI.getBusinessLogic();
		datesWithEventsCurrentMonth=facade.getThisMonthDatesWithRides("a","b",jCalendar.getDate());		
		
		jLabRideDate.setBounds(new Rectangle(40, 15, 140, 25));
		jLabRideDate.setBounds(343, 15, 140, 25);
		getContentPane().add(jLabRideDate);
		
		jLabelDestination.setBounds(6, 81, 61, 16);
		getContentPane().add(jLabelDestination);
		
		
		fieldOrigin.setBounds(100, 53, 130, 26);
		getContentPane().add(fieldOrigin);
		fieldOrigin.setColumns(10);
		
		
		fieldDestination.setBounds(104, 81, 123, 26);
		getContentPane().add(fieldDestination);
		fieldDestination.setColumns(10);
		
		 //$NON-NLS-1$ //$NON-NLS-2$
		lblKotxe.setBounds(6, 280, 140, 20);
		getContentPane().add(lblKotxe);
		comboBoxKotxe.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
					kargatuEserlekuKopurua((Kotxe)comboBoxKotxe.getSelectedItem());
				
			}
		});
		
		
		comboBoxKotxe.setBounds(140, 279, 123, 21);
		getContentPane().add(comboBoxKotxe);
		
		
		comboBoxSeats.setBounds(170, 310, 60, 21);
		getContentPane().add(comboBoxSeats);
		
		JRadioButton rdbtnBai = new JRadioButton(ResourceBundle.getBundle("Etiquetas").getString("TReservationsGUI.Yes")); //$NON-NLS-1$ //$NON-NLS-2$
		rdbtnBai.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ezkutatuHiriakEzikDena();
			}
		});
		buttonGroup.add(rdbtnBai);
		rdbtnBai.setBounds(349, 296, 103, 21);
		getContentPane().add(rdbtnBai);
		
		
		buttonGroup.add(rdbtnEz);
		rdbtnEz.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				erakutsiDena();
			}
		});
		rdbtnEz.setBounds(476, 296, 103, 21);
		getContentPane().add(rdbtnEz);
		rdbtnEz.setSelected(true);
	
		 //$NON-NLS-1$ //$NON-NLS-2$
		hiriGehiagolbl.setBounds(320, 277, 257, 13);
		getContentPane().add(hiriGehiagolbl);
		
		 //$NON-NLS-1$ //$NON-NLS-2$
		ibilbideText.setBounds(125, 133, 85, 19);
		getContentPane().add(ibilbideText);
		ibilbideText.setColumns(10);
		
		//$NON-NLS-1$ //$NON-NLS-2$
		prezioPartziallbl.setBounds(53, 157, 199, 13);
		getContentPane().add(prezioPartziallbl);
		
		hiriGehigarriak.setVisible(false); //$NON-NLS-1$ //$NON-NLS-2$
		hiriGehigarriak.setBounds(10, 136, 136, 13);
		getContentPane().add(		hiriGehigarriak);
		 btnGordeHiriak.addActionListener(new ActionListener() {
		 	public void actionPerformed(ActionEvent e) {
		 		ibilbideaEtiketarako=getIbilbidea(ibilbideText.getText());
		 		if(ibilbideaEtiketarako==null) {
		 			
		 		}else {
		 		if(ibilbideText.getText().isBlank()) {
		 			jLabelError .setText(ResourceBundle.getBundle("Etiquetas").getString("CreateRideGUI.ErrorRoute"));
		 		}else {
		 		erakutsiIbilbideGordeEtaGero() ;
		 		kargatuPrezioenEtiketa();
		 		}
		 		}
		 	}
		 });
		
		 btnGordeHiriak.setVisible(false);
		btnGordeHiriak.setBounds(116, 180, 112, 20);
		getContentPane().add(btnGordeHiriak);
		btnEdit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				erakutsiEditatuEtaGero();
			}
		});
		btnEdit.setVisible(false);
		btnEdit.setBounds(220, 136, 71, 16);
		
		getContentPane().add(btnEdit);
		
		//$NON-NLS-1$ //$NON-NLS-2$
		formatulbl.setVisible(false);
		formatulbl.setBounds(22, 117, 230, 13);
		getContentPane().add(formatulbl);
		prezioakEsleitzekolbl.setBounds(22, 203, 123, 13);
		gordePreziobtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				float prezioa=ondoPrezioa(jTextFieldPrice.getText());
				if(prezioa!=-1) {
					prezioak.add(prezioa);
					
				if(ibilbideaEtiketarako.size()>=1) {
				kargatuPrezioenEtiketa();
				}
			}
			}
		});
		
		gordePreziobtn.setVisible(false);
		getContentPane().add(prezioakEsleitzekolbl);
		gordePreziobtn.setBounds(170, 256, 120, 13);
		
		getContentPane().add(gordePreziobtn);
		
		bidaiaAnitzarenPreziototala.setVisible(false);
		bidaiaAnitzarenPreziototala.setBounds(170, 204, 292, 13);
		getContentPane().add(bidaiaAnitzarenPreziototala);
		 prezioakEsleitzekolbl .setVisible(false);
		ibilbideText.setVisible(false);
		
		 //Code for JCalendar
		this.jCalendar.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent propertychangeevent) {
//			
				if (propertychangeevent.getPropertyName().equals("locale")) {
					jCalendar.setLocale((Locale) propertychangeevent.getNewValue());
				} else if (propertychangeevent.getPropertyName().equals("calendar")) {
					calendarAnt = (Calendar) propertychangeevent.getOldValue();
					calendarAct = (Calendar) propertychangeevent.getNewValue();
					DateFormat dateformat1 = DateFormat.getDateInstance(1, jCalendar.getLocale());
					
					int monthAnt = calendarAnt.get(Calendar.MONTH);
					int monthAct = calendarAct.get(Calendar.MONTH);
					if (monthAct!=monthAnt) {
						if (monthAct==monthAnt+2) { 
							// Si en JCalendar está 30 de enero y se avanza al mes siguiente, devolverá 2 de marzo (se toma como equivalente a 30 de febrero)
							// Con este código se dejará como 1 de febrero en el JCalendar
							calendarAct.set(Calendar.MONTH, monthAnt+1);
							calendarAct.set(Calendar.DAY_OF_MONTH, 1);
						}
						
						jCalendar.setCalendar(calendarAct);						
	
					}
					jCalendar.setCalendar(calendarAct);
					int offset = jCalendar.getCalendar().get(Calendar.DAY_OF_WEEK);
					
						if (Locale.getDefault().equals(new Locale("es")))
							offset += 4;
						else
							offset += 5;
				Component o = (Component) jCalendar.getDayChooser().getDayPanel().getComponent(jCalendar.getCalendar().get(Calendar.DAY_OF_MONTH) + offset);
				}}});
		
	}	 
	private void jButtonCreate_actionPerformed(ActionEvent e) {
		jLabelMsg.setText("");
		if(rdbtnEz.isSelected()) {
			ibilbideText.removeAll();
			prezioak=new LinkedList<Float>();
			float price= ondoPrezioa(jTextFieldPrice.getText());
			if(price!=-1)
			prezioak.add(price);
			
		}
		String error=field_Errors();
		
		if (error!=null) 
			jLabelMsg.setText(error);
		else
			try {
				
				BLFacade facade = MainGUI.getBusinessLogic();
				int inputSeats = (Integer)comboBoxSeats.getSelectedItem();
				LinkedList<String>ibilbidea= this.getIbilbidea(ibilbideText.getText());
				//LinkedList<Float> prezioLista=this.getPrezioa(jTextFieldPrice.getText());
				//float price = Float.parseFloat(jTextFieldPrice.getText());
				Ride r=facade.createRide(fieldOrigin.getText(), fieldDestination.getText(), UtilDate.trim(jCalendar.getDate()), inputSeats, prezioak, driver.getUser(),(Kotxe)comboBoxKotxe.getSelectedItem(),ibilbidea);
				jLabelMsg.setText(ResourceBundle.getBundle("Etiquetas").getString("CreateRideGUI.RideCreated"));
			} catch (RideMustBeLaterThanTodayException e1) {
				// TODO Auto-generated catch block
				jLabelMsg.setText(e1.getMessage());
			} catch (RideAlreadyExistException e1) {
				// TODO Auto-generated catch block
			
				jLabelMsg.setText(e1.getMessage());
			}

		}
	

	private void jButtonClose_actionPerformed(ActionEvent e) {
		this.setVisible(false);
	}
	
	private String field_Errors() {
		
		try {
			if ((fieldOrigin.getText().length()==0) || (fieldDestination.getText().length()==0)/*(jTextFieldSeats.getText().length()==0) */|| (jTextFieldPrice.getText().length()==0))
				return ResourceBundle.getBundle("Etiquetas").getString("CreateRideGUI.ErrorQuery");
			else {

				// trigger an exception if the introduced string is not a number
				int inputSeats = 1;

				if (kotxeLagun.getSize()<=0 ) {
					return ResourceBundle.getBundle("Etiquetas").getString("CreateRideGUI.Don'tHaveACar");
				}
				else {
					float price = Float.parseFloat(jTextFieldPrice.getText());
					if (price <= 0) {
						return ResourceBundle.getBundle("Etiquetas").getString("CreateRideGUI.PriceMustBeGreaterThan0");
					
					}else {
						LinkedList<String> ibil= this.getIbilbidea(this.ibilbideText.getText());
						if(ibil==null) {
							String er= "";
							return er;
						}
						if(prezioak.size()!=ibil.size()-1) {
							String error=ResourceBundle.getBundle("Etiquetas").getString("CreateRideGUI.ErrorPrezioEtiketa");
							
							return error;
						}else {
							return null;
						}
						
					}
						
					
				}
			}
		} catch (java.lang.NumberFormatException e1) {

			return  ResourceBundle.getBundle("Etiquetas").getString("CreateRideGUI.ErrorNumber");		
		} catch (Exception e1) {

			e1.printStackTrace();
			return null;

		}
	}
	
	private void kargatuKotxeak() {
		BLFacade blf= MainGUI.getBusinessLogic();
		List<Kotxe> kotxeList= blf.getKotxeGuztiak(driver);
		kotxeLagun.removeAllElements();
		if(kotxeList!=null) {
		for(Kotxe kotxea: kotxeList) {
			kotxeLagun.addElement(kotxea);
		}
		if(kotxeList.size()>0)
		comboBoxKotxe.setSelectedIndex(0);
		}
	}
	private void kargatuEserlekuKopurua(Kotxe kotxe) {
		eserlekuLagun.removeAllElements();
		for(int i=1; i<=kotxe.getTokiKopurua();i++) {
			eserlekuLagun.addElement(i);
		}
		
		comboBoxSeats.setSelectedIndex(0);
	}
	private LinkedList<String> getIbilbidea(String textu){
		if(fieldOrigin.getText().isBlank()||fieldDestination.getText().isBlank()) {
			 jLabelError .setText(ResourceBundle.getBundle("Etiquetas").getString("CreateRideGUI.ErrorHelmuga"));
			return null;
		}else {
		 String[] zatiak = textu.split("/");
		 	
	        LinkedList<String> lista = new LinkedList<>(Arrays.asList(zatiak));
	        if(textu.isBlank()) {
	        	LinkedList<String>listaberri=new LinkedList<String>();
	        	lista=listaberri;
	        }
	        if(!lista.contains(fieldOrigin.getText())){
	        	 lista.addFirst(fieldOrigin.getText());
	        }else {
	        	jLabelError .setText(ResourceBundle.getBundle("Etiquetas").getString("CreateRideGUI.ErrorTokiBerdin"));
	        	return null;
	        }
	        if(!lista.contains(fieldDestination.getText())) {
	        	 lista.addLast(fieldDestination.getText());
	        }else {
	        	jLabelError .setText(ResourceBundle.getBundle("Etiquetas").getString("CreateRideGUI.ErrorTokiBerdin"));
	        	return null;
	        }
	       
	       
	        return lista;
		}

	}
	private LinkedList<Float> getPrezioa(String textu){
		 String[] zatiak = textu.split("/");
		 	
	        LinkedList<String> lista = new LinkedList<>(Arrays.asList(zatiak));
	        LinkedList<Float> zkiLista=new LinkedList<Float>();
	        
	        for(String zki: lista) {
	        	float prezio= Float.parseFloat(zki);
	        	zkiLista.add(prezio);
	        }
	        return zkiLista;

	}
	private void ezkutatuHiriakEzikDena() {
		formatulbl.setVisible(true);
		hiriGehiagolbl.setVisible(true);
		btnGordeHiriak.setEnabled(true);
		ibilbideText.setEnabled(true);
		 jLabelPrice.setVisible(false);
		lblKotxe .setVisible(false);
		 jTextFieldPrice.setVisible(false);
		 comboBoxKotxe.setVisible(false);
	     comboBoxSeats .setVisible(false);
	     ibilbideText.setVisible(true);
	     btnGordeHiriak.setVisible(true);
	     hiriGehigarriak.setVisible(true);
	     jButtonCreate.setEnabled(false);
	     jLabelSeats.setVisible(false);
	     bidaiaAnitzarenPreziototala.setVisible(false);
	     
	}
    private void erakutsiDena() {
    	formatulbl.setVisible(false);
    	ibilbideText.setVisible(false);
		ibilbideText.setText("");
		hiriGehiagolbl.setVisible(false);
		 jLabelPrice.setVisible(true);
		lblKotxe .setVisible(true);
		 jTextFieldPrice.setVisible(true);
		 comboBoxKotxe.setVisible(true);
	     comboBoxSeats .setVisible(true);
	     btnGordeHiriak.setVisible(false);
	     hiriGehigarriak.setVisible(false);
	     jLabelSeats.setVisible(true);
	     btnEdit.setVisible(false);
	     prezioakEsleitzekolbl.setVisible(false);
	     gordePreziobtn.setVisible(false);
	     gordePreziobtn.setEnabled(false);
	     jButtonCreate.setEnabled(true);
	     bidaiaAnitzarenPreziototala.setVisible(false);
	     prezioak=new LinkedList<Float>();
	}
    private void erakutsiIbilbideGordeEtaGero() {
    	ibilbideText.setEnabled(false);
    	btnEdit.setVisible(true);
		 jLabelPrice.setVisible(true);
		lblKotxe .setVisible(true);
		 jTextFieldPrice.setVisible(true);
		 comboBoxKotxe.setVisible(true);
	     comboBoxSeats .setVisible(true);
	     btnGordeHiriak.setEnabled(false);
	     jLabelSeats.setVisible(true);
	     prezioakEsleitzekolbl.setVisible(true);
	     gordePreziobtn.setVisible(true);
	     gordePreziobtn.setEnabled(true);
	     jButtonCreate.setEnabled(true);
	     bidaiaAnitzarenPreziototala.setVisible(true);
	     prezioak=new LinkedList<Float>();
    }
    private void erakutsiEditatuEtaGero() {
    	btnEdit.setVisible(false);
    	ibilbideText.setEnabled(true);
    	 gordePreziobtn.setVisible(false);
    	 btnGordeHiriak.setEnabled(true);
    	 prezioakEsleitzekolbl .setVisible(false);
    	 bidaiaAnitzarenPreziototala.setVisible(false);
    	ezkutatuHiriakEzikDena();
    }
    private void kargatuPrezioenEtiketa() {
    	if(ibilbideaEtiketarako.size()>1) {
    		
    	prezioakEsleitzekolbl.setText(ibilbideaEtiketarako.getFirst()+"-->"+ ibilbideaEtiketarako.get(1));
    	ibilbideaEtiketarako.removeFirst();
    	}else { 	
    		gordePreziobtn.setEnabled(false);
    	}
    	String prezioa="";
		for(Float num: prezioak) {
			if(prezioa.isBlank()) {
				prezioa=prezioa+num;
			}else {
				prezioa=prezioa+"/"+num;
			}
		}
	bidaiaAnitzarenPreziototala.setText( ResourceBundle.getBundle("Etiquetas").getString("CreateRideGUI.SartutakoPrezioak")+prezioa);
    	
    }
    private float ondoPrezioa(String prezioa) {
    	try {
    		if(prezioa.isBlank()) {
    			
    							jLabelError .setText(ResourceBundle.getBundle("Etiquetas").getString("CreateRideGUI.ErrorPrice"));
    			return -1;
    		}else {
    			float prezio= Float.parseFloat(prezioa);
    			if(prezio<=0) {
    				jLabelError .setText(ResourceBundle.getBundle("Etiquetas").getString("CreateRideGUI.ErrorPrice0"));
    				
    				return -1;
    			}
    		return prezio;
    		}	
    	}catch(NumberFormatException e) {
    		jLabelError .setText(ResourceBundle.getBundle("Etiquetas").getString("CreateRideGUI.ErrorPrice1"));
    	
    		return -1;
    	}
    }
}
