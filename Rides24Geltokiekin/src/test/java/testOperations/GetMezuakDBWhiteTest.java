package testOperations;

import static org.junit.Assert.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import dataAccess.DataAccess;
import domain.Driver;
import domain.Kotxe;
import domain.Mezua;
import domain.Ride;
import domain.RideRequest;
import domain.Traveller;
import exceptions.RideAlreadyExistException;
import exceptions.RideMustBeLaterThanTodayException;

public class GetMezuakDBWhiteTest {

	DataAccess db= new DataAccess();
	TestDataAccess testdb= new TestDataAccess();
	
	private String from= "Donostia";
	private String to= "Bilbo";
	
    SimpleDateFormat f = new SimpleDateFormat("dd/MM/yyyy");
    private String noiz= "25/10/2030";
	private int rideNum=-1;
	List<Float> prezioak = new ArrayList<Float>();
	int places = 4;

    
    private Date date;
    private Driver d;
	private String userT = "userTravellerTest";
	private String emailT = "emailTravellerTest";
	private String userD = "userDriverTest";
	private String emailD = "emailDriverTest";
	private String matrikula = "1234ABC";
	private Traveller t;
	private Kotxe k;
	private Ride r;
	private RideRequest rr;
	private List<String> ibilbide;
	private Mezua m;
	private Mezua m2;
	


    @Before
	public void initialize() {
        date = new Date(2025, 1, 1);
        ibilbide = Arrays.asList(from,to);
		prezioak = Arrays.asList(4.0f, 4.0f);

		testdb.open();
		t = testdb.createTraveller(userT, emailT);
		testdb.close();
		
		testdb.open();
		d = testdb.createDriver(userD, emailD);
		testdb.close();
		
		db.open();
		db.createCar("Toyota", "Yaris", matrikula, places, d);
		db.close();
		
		testdb.open();
		k = testdb.getCar(matrikula);
		testdb.close();
		
	}


	
    @Test
	public void testGetMezuak1() {
    	db.open();
        List<Mezua> mezuak = db.getMezuak(t);
        db.close();
        assertTrue(mezuak.isEmpty());
	}
    
    @Test
	public void testGetMezuak2() {
    	r = addRide(from, to, date, places, prezioak, d.getUser(), k, ibilbide);
    	
    	db.open();
    	rr = db.erreserbatu(date, r, t, 1, from, to);
    	db.close();
    	
    	db.open();
    	db.gehituErreklamazioa(t, d, "DeskripzioTest", 4.0f, rr);
    	db.close();
    	
    	db.open();
        List<Mezua> mezuak = db.getMezuak(t);
        db.close();
        
        for(Mezua mezu: mezuak) {
        	if(mezu.getType() == 0) {
                assertTrue(true);
        	}
        }
	}
	
    @Test
	public void testGetMezuak3() {
    	r = addRide(from, to, date, places, prezioak, d.getUser(), k, ibilbide);
    	db.open();
    	rr = db.erreserbatu(date, r, t, 1, from, to);
    	db.close();
    	db.open();
        List<Mezua> mezuak = db.getMezuak(t);
        db.close();
        assertFalse(mezuak.isEmpty());
	}
	
	
	@After
	public void bukatu() {
		try {
			testdb.open();
			if(rideNum>0) {
			db.open();
			db.Kantzelatu(r);
			db.close();
			testdb.removeRide(rideNum);
			}
			testdb.removeCar(matrikula);
			testdb.removeDriver(userD);
			testdb.removeTraveller(userT);
			testdb.close();
		} catch (Exception e) {
			fail("Imposible");
		}
	}
	
	private boolean addCar(String matrikula, int tokiKop,Driver driver) {
		db.open();
		Boolean createdCar = db.createCar("Seat", "ibiza", matrikula, tokiKop, driver);
		db.close();
		return createdCar;
	}
	private Driver addDriver(String user, String email) {
		testdb.open();
		Driver driver = testdb.existDriver(user);
		if (driver == null) {
			driver = testdb.createDriver(user, email);
		}else {
			driver=null;
			fail("Gidaria jadanik existitzen da, ezin dira guztiz zehatz jakin erantzunak");
		}
		testdb.close();
		return driver;
		
	}
	private Ride addRide(String from, String to, Date date, int nPlaces, /* float price */ List<Float> price,
			String driverUser, Kotxe kotxe, List<String> ibilbide) {
		db.open();
		Ride ride = null;
		try {
			ride = db.createRide(from, to, date, nPlaces, prezioak, driverUser, kotxe, ibilbide);
			rideNum=ride.getRideNumber();
		} catch (RideAlreadyExistException e) {
			fail("That Ride exists, you must change");
		} catch (RideMustBeLaterThanTodayException e) {
			fail("Ride Must Be Later Than Today");
		}
		db.close();
		return ride;
	}

}