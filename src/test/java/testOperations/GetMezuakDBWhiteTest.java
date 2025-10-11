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
import domain.Erreklamazioa;

import domain.Kotxe;
import domain.Mezua;
import domain.Ride;
import domain.RideRequest;
import domain.Traveller;
import exceptions.RideAlreadyExistException;
import exceptions.RideMustBeLaterThanTodayException;

public class GetMezuakDBWhiteTest {

	DataAccess sut= new DataAccess();
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
		
		sut.open();
		sut.createCar("Toyota", "Yaris", matrikula, places, d);
		sut.close();
		
		testdb.open();
		k = testdb.getCar(matrikula);
		testdb.close();
		
	}

	@After
	public void bukatu() {
		try {
			testdb.open();
			if(rideNum>0) {
			sut.open();
			sut.kantzelatu(r);
			sut.close();
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
	/**
	 * Erreserbarik ez dituen bidaiari baten mezuak lortzen ditu.
	 * Berifikatzen du bidaiari horrek ez dituela mezurik.
	 * @author Beñat Ercibengoa Calvo
	 */
    @Test
	public void testGetMezuak1() {
    	sut.open();
        List<Mezua> mezuak = sut.getMezuak(t);
        sut.close();
        assertTrue(mezuak.isEmpty());
	}
    
	/**
	 * Test honek Ride bat sortu, erreserba bat egin eta ondoren erreklamazio bat
	 * sortzen du. Azkenik, bidaiariaren mezuak lortzen ditu.
	 * Berifikatzen du itzulitako mezuak soilik 1 motako mezuak direla.
	 * @author Beñat Ercibengoa Calvo
	 */
    @Test
	public void testGetMezuak2() {
    	r = addRide(from, to, date, places, prezioak, d.getUser(), k, ibilbide);
    	
    	sut.open();
    	rr = sut.erreserbatu(new RideRequest(date, r, t, 1, from, to));
    	sut.close();
    	
    	sut.open();
    	Erreklamazioa erreklamazioa= new Erreklamazioa(t, d, "DeskripzioTest", 4.0f, rr);
    	sut.gehituErreklamazioa(erreklamazioa);
    	sut.close();
    	
    	sut.open();
        List<Mezua> mezuak = sut.getMezuak(t);
        sut.close();
        
        for(Mezua mezu: mezuak) {
        	if(mezu.getType() != 1) {
                fail();
        	}
        }
        assertTrue(true);
	}
	
	 /**
	  * Test honek bidaiari batentzat erreserba bat sortzen du eta ondoren
	  * bidaiariaren mezuak lortzen ditu.
	  * Berifikatzen du bidaiariaren mezu lista ez dagoela hutsa.
	  * @author Beñat Ercibengoa Calvo
	  */
    @Test
	public void testGetMezuak3() {
    	r = addRide(from, to, date, places, prezioak, d.getUser(), k, ibilbide);
    	sut.open();
    	rr = sut.erreserbatu(new RideRequest(date, r, t, 1, from, to));
    	sut.close();
    	sut.open();
        List<Mezua> mezuak = sut.getMezuak(t);
        sut.close();
        assertFalse(mezuak.isEmpty());
	}
	
	

	
	private boolean addCar(String matrikula, int tokiKop,Driver driver) {
		sut.open();
		Boolean createdCar = sut.createCar("Seat", "ibiza", matrikula, tokiKop, driver);
		sut.close();
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
		sut.open();
		Ride ride = null;
		try {
			ride = sut.createRide(from, to, date, nPlaces, price, driverUser, kotxe, ibilbide);
			rideNum=ride.getRideNumber();
		} catch (RideAlreadyExistException e) {
			fail("That Ride exists, you must change");
		} catch (RideMustBeLaterThanTodayException e) {
			fail("Ride Must Be Later Than Today");
		}
		sut.close();
		return ride;
	}

}