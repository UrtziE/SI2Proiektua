package domain;

import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@Embeddable
@XmlAccessorType(XmlAccessType.FIELD)
public class Geltoki {
	
	
	
	private String tokiIzen;
	private float prezioa;
	private int seatKop;
	public Geltoki(String tokiIzen, float prezioa, int seatkop) {
		this.tokiIzen=tokiIzen;
		this.prezioa=prezioa;
		this.seatKop=seatkop;
	}
	public Geltoki()
	{}
	public void kenduSeatKop(int seat) {
		seatKop=seatKop-seat;
	}
	public void gehituSeatKop(int seat) {
		seatKop=seatKop+seat;
	}
	public String getTokiIzen() {
		return tokiIzen;
	}
	public float getPrezioa() {
		return prezioa;
	}
	public int getEserleku() {
		return seatKop;
	}
	
	
}
