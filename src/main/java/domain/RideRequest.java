package domain;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.ResourceBundle;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@Entity
@XmlAccessorType(XmlAccessType.FIELD)
public class RideRequest implements Serializable,Comparable<RideRequest>{
	
	@XmlID
	@XmlJavaTypeAdapter(IntegerAdapter.class)
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer requestId;
	private Date whenRequested;
	private EgoeraRideRequest state;
	private Date whenDecided;
	private int seats;
	private String fromRequested;
	private String toRequested;
	private boolean baloratuaDriver;
	private boolean erreklamatuaDriver;
	private boolean baloratuaTraveller;
	private boolean erreklamatuaTraveller;
	private boolean bidaiaEsandaZer=false;

	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
	private Traveller traveller;

	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
	private Ride ride;

	public RideRequest(Date whenRequested, Ride ride, Traveller traveller, int seats,String from, String to) {
		this.seats = seats;
		this.whenRequested = whenRequested;
		this.ride = ride;
		this.traveller = traveller;
		this.state = EgoeraRideRequest.TRATATU_GABE;
		this.fromRequested=from;
		this.toRequested=to;
		this.baloratuaDriver =false;
		this.erreklamatuaDriver=false;
		this.baloratuaTraveller=false;
		this.erreklamatuaTraveller=false;
	}
	public RideRequest() {
		
	}
  public String getToRequested() {
	  return toRequested;
  }
  public String getFromRequested() {
	  return fromRequested;
  }
  public boolean isBidaiaEsandaZer() {
	  return bidaiaEsandaZer;
  }
  public void setBidaiaEsandaTrue() {
	  bidaiaEsandaZer=true;
  }
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RideRequest other = (RideRequest) obj;
		if (this.getId() != other.getId())
			return false;
		return true;
	}
	
     public int compareTo(RideRequest request) {
		
		if(this.state.equals(request.getState())) {
			if(!this.state.equals(EgoeraRideRequest.TRATATU_GABE)) {
				return(this.whenDecided.compareTo(request.getWhenDecided()));
			}else {
				float GureaRating= this.getTraveller().kalkulatuBalorazioMedia();
				float BesteRating= request.getTraveller().kalkulatuBalorazioMedia();
				if(GureaRating>BesteRating) {
					return 1;
				}else {
					if(GureaRating<BesteRating) {
						return -1;
					}else {
						int GureaRatingKop= this.getTraveller().getBalorazioKop();
						int BesteaRatingKop= request.getTraveller().getBalorazioKop();
						if(GureaRatingKop>BesteaRatingKop) {
							return 1;
						}else {
							if(GureaRatingKop<BesteaRatingKop) {
								return -1;
							}else {
								return 0;
							}
						}
						}
				}
				
			}
		}
		return(this.whenRequested.compareTo(request.getWhenRequested()));
		
	}

	public int getId() {
		return requestId;
	}

	public String toString() {
		return ResourceBundle.getBundle("Etiquetas").getString("RegisterGUI.User")+" "  + this.traveller.getUser()+" " 
		+ ResourceBundle.getBundle("Etiquetas").getString("RideRequest")+" " + this.getId()+" " 
		+ResourceBundle.getBundle("Etiquetas").getString("FindRidesGUI.NPlaces") + this.getSeats();
	}
	
	
	

	public Traveller getTraveller() {
		return traveller;
	}

	public Date getWhenRequested() {
		return whenRequested;
	}

	public Ride getRide() {
		return ride;
	}
	
	public boolean isBaloratuaDriver() {
		return baloratuaDriver;
	}
	public void setBaloratuaDriver(boolean baloratuaDriver) {
		this.baloratuaDriver = baloratuaDriver;
	}
	public boolean isErreklamatuaDriver() {
		return erreklamatuaDriver;
	}
	public void setErreklamatuaDriver(boolean erreklamatuaDriver) {
		this.erreklamatuaDriver = erreklamatuaDriver;
	}
	public boolean isBaloratuaTraveller() {
		return baloratuaTraveller;
	}
	public void setBaloratuaTraveller(boolean baloratuaTraveller) {
		this.baloratuaTraveller = baloratuaTraveller;
	}
	public boolean isErreklamatuaTraveller() {
		return erreklamatuaTraveller;
	}
	public void setErreklamatuaTraveller(boolean erreklamatuaTraveller) {
		this.erreklamatuaTraveller = erreklamatuaTraveller;
	}
	public void setWhenDecided(Date whenAccepted) {
		this.whenDecided = whenAccepted;
	}

	public Date getWhenDecided() {
		return whenDecided;
	}

	public int getSeats() {
		return this.seats;
	}

	public EgoeraRideRequest getState() {
		return state;
	}

	public void setState(EgoeraRideRequest state) {
		this.state = state;
	}
	
	public String requestInfo() {
		return " request:" + this.getId() + this.ride.toString() + " seats: " + this.getSeats();
	}
	public float getPrezioa() {
		float prezio=ride.lortuBidaiarenPrezioa(fromRequested, toRequested);
		return seats*prezio;
	}
	public String mezua()
	{
		return( ResourceBundle.getBundle("Etiquetas").getString("RideRequest")+":ID :"+ requestId+" "+
				ResourceBundle.getBundle("Etiquetas").getString("CreateRideGUI.NumberOfSeats")+" "+seats);
				//Aldatu
	}
	
	public void setId(Integer id) {
		this.requestId = id;
	}
}
