package domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlIDREF;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlAccessorType(XmlAccessType.FIELD)
@Entity
public class Admin extends Profile implements Serializable  {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	public Admin(String email, String name, String surname, String user, String password, String telefono) {
		super(email, name, surname, user, password, telefono);
	}
	public Admin(String user, String password) {
		super( user, password);
	}
	public Admin() {
		super();
	}
	
	public String toString(){
		return(super.toString());
	}
	
	
	
	
	
	
}
