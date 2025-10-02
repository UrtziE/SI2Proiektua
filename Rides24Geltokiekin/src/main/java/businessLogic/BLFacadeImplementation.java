package businessLogic;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Logger;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.persistence.TypedQuery;

import configuration.ConfigXML;
import dataAccess.DataAccess;
import domain.Ride;
import domain.RideContainer;
import domain.RideRequest;
import domain.Traveller;
import domain.Admin;
import domain.Alerta;
import domain.Driver;
import domain.Erreklamazioa;
import domain.Kotxe;
import domain.Mezua;
import domain.Profile;
import exceptions.RideMustBeLaterThanTodayException;
import exceptions.RideAlreadyExistException;

/**
 * It implements the business logic as a web service.
 */
@WebService(endpointInterface = "businessLogic.BLFacade")
public class BLFacadeImplementation  implements BLFacade {
	DataAccess dbManager;
	Logger logger = Logger.getLogger(getClass().getName());
	public BLFacadeImplementation()  {		
		logger.info("Creating BLFacadeImplementation instance");
		
		    dbManager=new DataAccess();
		    
		
	}
	
    public BLFacadeImplementation(DataAccess da)  {
		
		System.out.println("Creating BLFacadeImplementation instance with DataAccess parameter");
		
		dbManager=da;		
	}
    
    
    /**
     * {@inheritDoc}
     */
    @WebMethod public List<String> getDepartCities(){
    	dbManager.open();	
		
		 List<String> departLocations=dbManager.getDepartCities();		

		dbManager.close();
		
		return departLocations;
    	
    }
    /**
     * {@inheritDoc}
     */
	@WebMethod public List<String> getDestinationCities(String from){
		dbManager.open();	
		
		 List<String> targetCities=dbManager.getArrivalCities(from);		

		dbManager.close();
		
		return targetCities;
	}

	/**
	 * {@inheritDoc}
	 */
   @WebMethod
   public Ride createRide( String from, String to, Date date, int nPlaces,/* float price*/List<Float>price, String driverUser ,Kotxe kotxe ,List<String> ibilbide) throws RideMustBeLaterThanTodayException, RideAlreadyExistException{
	   
		dbManager.open();
		Ride ride=dbManager.createRide(from, to, date, nPlaces, price, driverUser,kotxe,ibilbide);		
		dbManager.close();
		return ride;
   };
   @WebMethod
   public Profile register (String email, String name, String surname, String user, String password, String telf, String type) {
	   dbManager.open();
	  Profile p=dbManager.register(email,name,surname,user,password,telf,type);
	   dbManager.close();
	   return p;
   }
   @WebMethod
   public Profile login(String user, String password) {
	   dbManager.open();
	 Profile p= dbManager.login(user,password);
	   dbManager.close();
	   return p;
   }
   
 
  
  
  
   @WebMethod
   public void sartuDirua(float dirua, Traveller traveller) {
	   
	   dbManager.open();
	    dbManager.sartuDirua( dirua, traveller);
	   dbManager.close();
	   
   }
   @WebMethod
   public void ateraDirua(float dirua, Driver driver) {
	   
	   
	   dbManager.open();
	    dbManager.kenduDirua( dirua, driver);
	   dbManager.close();
	  
	   
   }
   @WebMethod
	public void gehituDirua(float dirua, Profile p) {
		dbManager.open();
	    dbManager.sartuDirua( dirua, p);
	   dbManager.close();
	}
   @WebMethod
	public void kenduDirua(float dirua, Profile p) {
		dbManager.open();
	    dbManager.kenduDirua( dirua, p);
	   dbManager.close();
	}
   @WebMethod
   public RideRequest erreserbatu(Date time, Ride ride, Traveller traveller,int seats,String from, String to) {
	   
	
		   dbManager.open();
		 RideRequest request=dbManager.erreserbatu(time,ride,traveller,seats,from,to);
		   dbManager.close();
		   return request;
	
   }
   @WebMethod
   public Ride getRideFromRequest(RideRequest erreserba) {
	   dbManager.open();
	  Ride ride= dbManager.getRideFromRequest(erreserba) ;
	   dbManager.close();
	   return ride;
	
   }
   @WebMethod
   public void onartuEdoDeuseztatu(RideRequest request, boolean onartuta) {
	   dbManager.open();
	   dbManager.onartuEdoDeuseztatu(request, onartuta);
	   dbManager.close();
   }
   /**
    * {@inheritDoc}
    */
	@WebMethod 
	public List<Ride> getRides(String from, String to, Date date){
		dbManager.open();
		List<Ride>  rides=dbManager.getRides(from, to, date);
		dbManager.close();
		return rides;
	}
	   @WebMethod
	public List<RideContainer> getRidesOfDriver(Driver driver){
		dbManager.open();
		List<RideContainer> rides=dbManager.getRidesOfDriver(driver);
		dbManager.close();
		return rides;
	}
	   @WebMethod
	public List<RideContainer> getAllRidesOfDriver(Driver driver){
		dbManager.open();
		List<RideContainer> rides=dbManager.getAllRidesOfDriver(driver);
		dbManager.close();
		return rides;
	}
	   @WebMethod
	public List<RideRequest> getRidesRequestsOfRide(Ride ride) {
		dbManager.open();
		List<RideRequest> request=dbManager.getRidesRequestsOfRide(ride);
		dbManager.close();
		return request;
	
	}
	   @WebMethod
	public List<RideRequest> getRidesRequestsOfTraveller(Traveller traveller) {
		dbManager.open();
		List<RideRequest> request=dbManager.getRidesRequestsOfTraveller(traveller);
		dbManager.close();
		return request;
	}
	   @WebMethod
	public float getMoney(Profile p) {
		dbManager.open();
		float money=dbManager.getMoney(p);
		dbManager.close();
		return money;
	
	}

    
	/**
	 * {@inheritDoc}
	 */
	@WebMethod 
	public List<Date> getThisMonthDatesWithRides(String from, String to, Date date){
		dbManager.open();
		List<Date>  dates=dbManager.getThisMonthDatesWithRides(from, to, date);
		dbManager.close();
		return dates;
	}
	
	   @WebMethod
	public void close() {
		DataAccess dB4oManager=new DataAccess();

		dB4oManager.close();

	}

	/**
	 * {@inheritDoc}
	 */
    @WebMethod	
	 public void initializeBD(){
    	dbManager.open();
		dbManager.initializeDB();
		dbManager.close();
	}
    @WebMethod
	@Override
	public List<Kotxe> getKotxeGuztiak(Driver driver) {
		dbManager.open();
		List<Kotxe> kotxeList=dbManager.getKotxeGuztiak(driver);
				dbManager.close();
		return kotxeList;
	}
    @WebMethod
	public List<RideContainer> getEginRidesOfDriver(Driver d){
		dbManager.open();
		List<RideContainer> rideList=dbManager.getEginRidesOfDriver(d);
				dbManager.close();
		return rideList;
	}
    @WebMethod
	@Override
	public boolean createCar(String marka, String modelo, String matrikula, int tokiKop, Driver driver) {
		dbManager.open();
		boolean ondo=dbManager.createCar(marka,modelo,matrikula,tokiKop,driver);
				dbManager.close();
		return ondo;
	}
	/*
	public void gehituMezuaRide( int i,RideRequest erreserba, int norentzat) {
		dbManager.open();
		dbManager.gehituMezuaRide( i, erreserba,norentzat);
		dbManager.close();
	}
	public void gehituMezua( int i,float kantitatea ,RideRequest erreserba,Profile profile ) {
		dbManager.open();
		dbManager.gehituMezua( i,kantitatea, erreserba, profile);
		dbManager.close();
	}
	
	public void gehituMezua( int i,float kantitatea,Profile p) {
		dbManager.open();
		dbManager.gehituMezua( i, kantitatea, p);
		dbManager.close();
	}
	*/
    @WebMethod
	public List<Mezua> getMezuak(Profile p){
		dbManager.open();
		List<Mezua> mList= dbManager.getMezuak(p);
		dbManager.close();
		return mList;
	}
    @WebMethod
   	public List<Mezua> getErreklamazioMezuak(Profile p){
   		dbManager.open();
   		List<Mezua> mList= dbManager.getErreklamazioMezuak(p);
   		dbManager.close();
   		return mList;
   	}
    @WebMethod
	public void kantzelatu(Ride r) {
		dbManager.open();
		dbManager.kantzelatu(r);
		dbManager.close();
	}
    @WebMethod
	public void egindaEdoEzEgina(RideRequest request, boolean onartuta) {
		dbManager.open();
		dbManager.egindaEdoEzEgina(request, onartuta);
		dbManager.close();
	}
    @WebMethod
    public void baloratu(int balorazioa, Profile nori, RideRequest r) {
		dbManager.open();
		dbManager.baloratu(balorazioa,nori,r);
		dbManager.close();
	}
    @WebMethod
    public float getBalorazioMedia(Driver driver) {
		dbManager.open();
		float media=dbManager.getBalorazioMedia(driver);
		dbManager.close();
		return media;
		
	}
    @WebMethod
    public void sortuAlerta(Traveller t, String from, String to, Date when) {
    	dbManager.open();
    	dbManager.sortuAlerta(t,from,to,when);
    	dbManager.close();
    }
    @WebMethod
    public Alerta getAlerta(Traveller traveller,String from,String to, Date when) {
    	dbManager.open();
    	Alerta alerta=dbManager.getAlerta(traveller,from,to,when);
    	dbManager.close();
    	return alerta;
    }
    
    
    @WebMethod
	public List<Erreklamazioa> getErreklamazioak(Profile p) {
    	dbManager.open();
    	List<Erreklamazioa> erreklamazioak=dbManager.getErreklamazioak(p);
    	dbManager.close();
    	return erreklamazioak;
	}
    @WebMethod
	public void gehituErreklamazioa(Profile p, Profile nori, String deskripzioa,float prezioa, RideRequest r) {
    	dbManager.open();
    	dbManager.gehituErreklamazioa(p,nori,deskripzioa,prezioa,r);
    	dbManager.close();

	}
    @WebMethod
	public Erreklamazioa takeNewErreklamazioa(Admin a) {
    	dbManager.open();
    	Erreklamazioa erreklamazioa=dbManager.takeNewErreklamazioa(a);
    	dbManager.close();
    	return erreklamazioa;
	}
    @WebMethod
    public List<Traveller> getTravellersOfRideDone(Ride ride){
		dbManager.open();
		List<Traveller> r= dbManager.getTravellersOfRideDone(ride);
    	dbManager.close();
		return r;
	}
    @WebMethod
    public List<Traveller> getTravellersOfRideNotDone(Ride ride){
		dbManager.open();
		List<Traveller> r= dbManager.getTravellersOfRideNotDone(ride);
    	dbManager.close();
		return r;
	}
    @WebMethod public void erreklamazioaOnartuEdoDeuseztatu(Erreklamazioa erreklamazioa,float kantitatea, boolean onartuta) {
    	dbManager.open();
    	dbManager.erreklamazioaOnartuEdoDeuseztatu(erreklamazioa, kantitatea, onartuta);
    	dbManager.close();
    }
    @WebMethod
    public void mezuaIrakurrita(Mezua alerta) {
    	dbManager.open();
    	dbManager.mezuaIrakurrita(alerta);
    	dbManager.close();
    }
    @WebMethod
    public List<Mezua>ikusitakoAlerta(Traveller traveller){
    	dbManager.open();
    	List<Mezua> mezuak=dbManager.ikusitakoAlerta( traveller);
    	dbManager.close();
    	return mezuak;
    }
    @WebMethod
    public List<Mezua> getIkusiGabeAlerta(Traveller traveller){
    	dbManager.open();
    	List<Mezua> mezuak=dbManager.getIkusiGabeAlerta( traveller);
    	dbManager.close();
    	return mezuak;
    }
    @WebMethod public List<Alerta>kargatuTravellerAlertak(Traveller traveller){
    	dbManager.open();
    	List<Alerta> alertak=dbManager.kargatuTravellerAlertak(traveller);
    	dbManager.close();
    	return alertak;
    }
    @WebMethod
    public void  deuseztatuAlerta(Alerta alerta) {
    	dbManager.open();
    	dbManager.deuseztatuAlerta(alerta);
    	dbManager.close();
    }
    @WebMethod 
    public boolean isBaloratua(RideRequest request, boolean gidari) {
    	dbManager.open();
    	boolean baloratuta=dbManager.isBaloratua(request, gidari);
    	dbManager.close();
    	return baloratuta;
    }
    @WebMethod
    public boolean isErreklamatua(RideRequest request, boolean gidari) {
    	dbManager.open();
    	boolean baloratuta=dbManager.isErreklamatua(request, gidari);
    	dbManager.close();
    	return baloratuta;
    }
    @WebMethod
    public RideRequest lortuLehenRequestBidaiakoa(Ride r,Traveller t) {
    	dbManager.open();
    	RideRequest baloratuta=dbManager. lortuLehenRequestBidaiakoa(r,t);
    	dbManager.close();
    	return baloratuta;
    }
    @WebMethod public int lortuZenbatEserlekuGeratu(Ride ride, RideRequest request) {
    	dbManager.open();
    	int seats=dbManager.lortuZenbatEserlekuGeratu(ride,request);
    	dbManager.close();
    	return seats;
    }
    @WebMethod public List<Erreklamazioa> lortuErreklamazioakProzesuan(Profile a){
    	dbManager.open();
    	List<Erreklamazioa> erreklam=dbManager.lortuErreklamazioakProzesuan(a);
    	dbManager.close();
    	return erreklam;
    }
}

