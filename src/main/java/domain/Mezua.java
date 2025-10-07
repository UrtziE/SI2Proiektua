package domain;

import java.io.Serializable;
import java.util.Date;
import java.util.Locale;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlIDREF;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import java.util.Locale;
import java.util.ResourceBundle;

@Entity
@XmlAccessorType(XmlAccessType.FIELD)
public class Mezua implements Serializable, Comparable<Mezua> {
	@XmlID
	@XmlJavaTypeAdapter(IntegerAdapter.class)
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	private String info;

	private float kantitatea;

	@OneToOne(fetch = FetchType.EAGER)
	private RideRequest erreserba;

	@OneToOne(fetch = FetchType.EAGER)
	private Ride ride;

	@OneToOne(fetch = FetchType.EAGER)
	private Profile p;
	private int type = 1;
	private int mezutype;

	private String datamezua;
	private String diruMezu;
	private String typerenMezua;
	private Date when;
	private boolean irakurrita = false;
	@OneToOne(fetch = FetchType.EAGER)
	private Alerta alerta;
	@OneToOne(fetch = FetchType.EAGER)
	private Erreklamazioa erreklamazioa;
	
	private static final String DATADONE = "Mezuak.DataDone";  

	
	public int getType() {
		return type;
	}

	public RideRequest getErreserba() {
		return erreserba;
	}

	public void setErreserba(RideRequest erreserba) {
		this.erreserba = erreserba;
	}

	public Profile getP() {
		return p;
	}

	/*
	 * public Mezua( int i,RideRequest erreserba) { this.type=2;
	 * this.erreserba=erreserba; this.ride=erreserba.getRide(); this.zenbakia=i;
	 * when=new Date(); zeinRide(i);
	 * 
	 * }
	 */
	// Diru transakzioak ridetan
	public Mezua(int i, float kantitatea, RideRequest erreserba, Profile p) {
		when = new Date();
		this.kantitatea = kantitatea;
		this.erreserba = erreserba;
		this.ride = erreserba.getRide();
		this.mezutype = i;
		zeinTransaction(i);

	}

	// Dirua sartu eta atera
	public Mezua(int i, float kantitatea, Profile p) {
		when = new Date();
		this.kantitatea = kantitatea;
		// this.type=1;
		this.mezutype = i;
		this.p = p;
		zeinTransaction(i);

	}

	public Mezua(Profile p, Ride ride, Alerta alerta) {
		when = new Date();
		this.ride = ride;
		this.alerta = alerta;
		this.type = 2;
		this.p = p;

	}
	public Mezua(int i,Profile p, Ride ride, Erreklamazioa erreklamazioa) {
		when = new Date();
		this.ride = ride;
		this.erreklamazioa = erreklamazioa;
		this.type = 3;
		this.p = p;
		this.mezutype=i;
		zeinErreklamazio(i);

	}

	public Mezua() {

	}

	public void setInfo(String zerena, Date when, String mezua) {
		info = zerena + "__" + datamezua + "_ " + mezua;
	}

	public void setInfo(String zerena, Date when) {
		info = zerena + "__" + datamezua;
	}

	public void setIrakurritaTrue() {
		irakurrita = true;
	}

	public int getId() {
		return id;
	}

	public Date getWhen() {
		return this.when;
	}

	public Alerta getAlerta() {
		return alerta;
	}

	public String getInfo() {
		if (type == 1) {
			zeinTransaction(mezutype);
			setInfo(typerenMezua, when, diruMezu);
		}else if(type == 2){
			zeinAlerta();
			setInfo(typerenMezua, when);
		}else {
			zeinErreklamazio(mezutype);
			setInfo(typerenMezua, when);
		}
		return info;
	}

	public Ride getRide() {
		return ride;
	}

	public boolean isIrakurrita() {
		return irakurrita;
	}

	public String toString() {
		return getInfo();
	}

	public int compareTo(Mezua mezua) {

		return (this.when.compareTo(mezua.getWhen()));
	}

	public void zeinTransaction(int i) {
		switch (i) {
		// Traveller
		case 0:

			typerenMezua = ResourceBundle.getBundle("Etiquetas").getString("Mezuak.Requested") + ":  "
					+ erreserba.mezua() + "__" + ride;
			datamezua = ResourceBundle.getBundle("Etiquetas").getString("Mezuak.DataRequest") + " " + when;
			diruMezu = "-" + kantitatea + "€";

			break;
		case 1:

			typerenMezua = ResourceBundle.getBundle("Etiquetas").getString("Mezuak.Rejected") + ":  "
					+ erreserba.mezua() + "__" + ride.mezua();
			datamezua = ResourceBundle.getBundle("Etiquetas").getString("Mezuak.DataRejected") + " " + when;
			diruMezu = "+" + kantitatea + "€";

			break;
		case 2:

			typerenMezua = ResourceBundle.getBundle("Etiquetas").getString("Mezuak.Canceled") + ":  " + ride.mezua();
			datamezua = ResourceBundle.getBundle("Etiquetas").getString("Mezuak.DataCanceled") + " " + when;
			diruMezu = "+" + kantitatea + "€";

			break;
		case 3:
			typerenMezua = ResourceBundle.getBundle("Etiquetas").getString("Mezuak.Deposite") + ": ";
			datamezua = ResourceBundle.getBundle("Etiquetas").getString("Mezuak.DataDeposite") + " " + when;
			diruMezu = "+" + kantitatea + "€";
			break;
		// Driver
		case 4:
			typerenMezua = ResourceBundle.getBundle("Etiquetas").getString("Mezuak.NotDone") + ":    " + ride.mezua();
			datamezua = ResourceBundle.getBundle("Etiquetas").getString("Mezuak.DataNotDone") + " " + when;
			diruMezu = "+" + kantitatea + "€";

			break;
		case 5:
			typerenMezua = ResourceBundle.getBundle("Etiquetas").getString("Mezuak.Canceled") + ":   " + ride.mezua();
			datamezua = ResourceBundle.getBundle("Etiquetas").getString("Mezuak.DataCanceled") + " " + when;
			diruMezu = "+" + kantitatea + "€";
			break;
		case 6:
			typerenMezua = ResourceBundle.getBundle("Etiquetas").getString("Mezuak.Withdraw") + ":";
			datamezua = ResourceBundle.getBundle("Etiquetas").getString("Mezuak.DataWithdraw") + " " + when;
			diruMezu = "-" + kantitatea + "€";
			break;
		case 7:
			typerenMezua = ResourceBundle.getBundle("Etiquetas").getString("Mezuak.Done") + ":   " + ride.mezua();
			datamezua = ResourceBundle.getBundle("Etiquetas").getString(DATADONE) + " " + when;
			diruMezu = "+" + kantitatea + "€";
			break;
		case 8:
			typerenMezua = ResourceBundle.getBundle("Etiquetas").getString("Mezuak.NewErreklamazioa") + ":   "
					+ ride.mezua();
			datamezua = ResourceBundle.getBundle("Etiquetas").getString(DATADONE) + " " + when;
			diruMezu = "+" + kantitatea + "€";
			break;
		case 9:
			typerenMezua = ResourceBundle.getBundle("Etiquetas").getString("Mezuak.ErreklamazioaAccepted") + ":   "
					+ ride.mezua();
			datamezua = ResourceBundle.getBundle("Etiquetas").getString(DATADONE) + " " + when;
			diruMezu = "+" + kantitatea + "€";
			break;
		case 10:
			typerenMezua = ResourceBundle.getBundle("Etiquetas").getString("Mezuak.ErreklamazioaRejected") + ":   "
					+ ride.mezua();
			datamezua = ResourceBundle.getBundle("Etiquetas").getString(DATADONE) + " " + when;
			break;
		case 11:
			typerenMezua = ResourceBundle.getBundle("Etiquetas").getString("Mezuak.Erreklamatuta") + ":   "
					+ ride.mezua();
			datamezua = ResourceBundle.getBundle("Etiquetas").getString(DATADONE) + " " + when;
			diruMezu = "-" + kantitatea + "€";
			break;
		}

	}

	public void zeinErreklamazio(int i) {
		switch (i) {
		case 0:
			typerenMezua = ResourceBundle.getBundle("Etiquetas").getString("Mezuak.ErreklamazioaAccepted") + ":   "
					+ ride.mezua();
			datamezua = ResourceBundle.getBundle("Etiquetas").getString(DATADONE) + " " + when;
			diruMezu = "+" + kantitatea + "€";
			break;
		case 1:
			typerenMezua = ResourceBundle.getBundle("Etiquetas").getString("Mezuak.ErreklamazioaRejected") + ":   "
					+ ride.mezua();
			datamezua = ResourceBundle.getBundle("Etiquetas").getString(DATADONE) + " " + when;
			break;
		case 2:
			typerenMezua = ResourceBundle.getBundle("Etiquetas").getString("Mezuak.ErreklamazioBukatuta") + ":   "
					+ ride.mezua()+"  Accepted";
			datamezua = ResourceBundle.getBundle("Etiquetas").getString(DATADONE) + " " + when;
			break;
		case 3:
			typerenMezua = ResourceBundle.getBundle("Etiquetas").getString("Mezuak.ErreklamazioBukatuta") + ":   "
					+ ride.mezua() +"  Rejected";
			datamezua = ResourceBundle.getBundle("Etiquetas").getString(DATADONE) + " " + when;
			break;
		}
	}

	public void zeinAlerta() {
		typerenMezua = ResourceBundle.getBundle("Etiquetas").getString("Mezuak.EskatutakoAlerta") + ":   " + alerta.toString();
		datamezua = ResourceBundle.getBundle("Etiquetas").getString(DATADONE) + " " + when;
	}

	/*
	 * public void zeinTransaction(int i) { switch(i) { //Traveller case 0:
	 * 
	 * zerena= "Ride requested: ride id: "+ erreserba.getRide().getRideNumber();
	 * mezua= "-" + kantitatea + "€"; p=erreserba.getTraveller(); break; case 1:
	 * 
	 * zerena= "Ride request rejected: ride id: "+
	 * erreserba.getRide().getRideNumber(); mezua= "+" + kantitatea + "€";
	 * p=erreserba.getTraveller(); break; case 2:
	 * 
	 * zerena= "Canceled ride: ride id: " +erreserba.getRide().getRideNumber();
	 * mezua= "+" + kantitatea + "€"; p=erreserba.getTraveller(); break; case 3:
	 * zerena= "Deposited money"; mezua= "+" + kantitatea + "€"; break; //Driver
	 * case 4: zerena= "Ride finished: ride id: "
	 * +erreserba.getRide().getRideNumber(); mezua= "+" + kantitatea + "€";
	 * p=erreserba.getRide().getDriver(); break; case 5: zerena=
	 * "Cancelled Ride: ride id: " +erreserba.getRide().getRideNumber(); mezua= "-"
	 * + kantitatea + "€"; break; case 6: zerena= "Withdraw money"; mezua= "-" +
	 * kantitatea + "€"; break; }
	 * 
	 * }
	 * 
	 * public void zeinRide(int i) { switch(i) { //Transactions
	 * 
	 * //Traveller case 0:
	 * 
	 * zerena= "Ride requested: "; mezua= erreserba.requestInfo();
	 * p=erreserba.getTraveller(); break; case 1:
	 * 
	 * zerena= "Ride request rejected: "; mezua= erreserba.requestInfo();
	 * p=erreserba.getTraveller(); break; case 2:
	 * 
	 * zerena= "Canceled ride: "; mezua= erreserba.requestInfo();
	 * p=erreserba.getTraveller(); break; case 3:
	 * 
	 * zerena= "Accepted request: "; mezua= erreserba.requestInfo();
	 * p=erreserba.getTraveller(); break;
	 * 
	 * case 4:
	 * 
	 * zerena= "Ride done: "; mezua= erreserba.requestInfo();
	 * p=erreserba.getTraveller(); break; //Driver case 5:
	 * 
	 * zerena= "New Ride request: " + erreserba.getId(); mezua=
	 * erreserba.toString(); p=erreserba.getRide().getDriver(); break; case 6:
	 * 
	 * zerena= "Ride not done: " + erreserba.getId();
	 * p=erreserba.getRide().getDriver();
	 * 
	 * } }
	 */

}
