package domain;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlIDREF;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@Entity
@XmlAccessorType(XmlAccessType.FIELD)
public class Alerta {
	@Id 
	@XmlID
	
	@XmlJavaTypeAdapter(IntegerAdapter.class)
	@GeneratedValue
	private Integer id;
	@OneToOne(fetch=FetchType.EAGER, cascade=CascadeType.PERSIST)
	private Traveller traveller;
	private String from;
	private String to;
	private Date when;
	private boolean ezabatuta;
	
	public Alerta() {}
	
	public Alerta(Traveller t, String from, String to, Date when) {
		this.traveller=t;
		this.from=from;
		this.to=to;
		this.when=when;
		ezabatuta=false;
		
	}
	public void setEzabatuta(boolean aurkitu) {
		ezabatuta=aurkitu;
	}
	public Date getWhen() {
		return when;
	}
	public int getId() {
		return id;
	}
	public boolean isEzabatuta() {
		return ezabatuta;
	}
	public String toString() {
		return(ResourceBundle.getBundle("Etiquetas").getString("TReservationsGUI.From")+" "+from+" "+ResourceBundle.getBundle("Etiquetas").getString("TReservationsGUI.To")+" "+ to + " "+ResourceBundle.getBundle("Etiquetas").getString("TReservationsGUI.When")+ when);
	}
	/*public boolean berdinaDa(Ride ride) {
		if(ride.badaBide(from, to)&&ride.getDate().equals(when)) {
			return true;
		}else {
			return false;
		}
	}*/
	public Traveller getTraveller() {
		return traveller;
	}
	public String getFrom() {
		return from;
	}
	public String getTo() {
		return to;
	}
}
