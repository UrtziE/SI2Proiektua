package adapter;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;

import domain.Driver;

public class DriverTable extends JFrame {
	
	private Driver driver;
	private JTable	tabla;
	
	public DriverTable(Driver driver){
		super(driver.getUser()+"’s	rides ");
		this.setBounds(100,	100,	700,	200);
		this.driver =	driver;
		DriverAdapter	adapt	=	new DriverAdapter(driver);
		tabla =	new JTable(adapt);
		tabla.setPreferredScrollableViewportSize(new Dimension(500,	70));
		JScrollPane	scrollPane = new JScrollPane(tabla);
		getContentPane().add(scrollPane,	BorderLayout.CENTER);
	}
		

}
