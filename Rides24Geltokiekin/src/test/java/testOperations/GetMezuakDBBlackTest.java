package testOperations;

import static org.junit.Assert.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
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
import exceptions.AtriNullException;
import exceptions.RideAlreadyExistException;
import exceptions.RideMustBeLaterThanTodayException;

public class GetMezuakDBBlackTest {

	DataAccess sut = new DataAccess();
	TestDataAccess testdb = new TestDataAccess();
	
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
    
	/**
	 * Test nagusia konprobatzen duena getMezuak zerrenda ez huts bat
	 * itzultzen duela erreserba bat egin ondoren.
	 * Profile, Traveller eta Ride mockeatu dira.
	 * @author Beñat Ercibengoa Calvo
	 */
    @Test
	public void testMezuaItzuli() {
    	r = addRide(from, to, date, places, prezioak, d.getUser(), k, ibilbide);
    	sut.open();
    	rr = sut.erreserbatu(date, r, t, 1, from, to);
    	sut.close();
    	sut.open();
        List<Mezua> mezuak = sut.getMezuak(t);
        sut.close();
        assertFalse(mezuak.isEmpty());
	}
    
	/**
	 * getMezuak 1 motako mezuak itzultzen ez dituela
	 * konprobatzen dituen testa
	 * @author Beñat Ercibengoa Calvo
	 */
    @Test
	public void testMezuEzMotaBat() {
    	r = addRide(from, to, date, places, prezioak, d.getUser(), k, ibilbide);
    	sut.open();
    	rr = sut.erreserbatu(date, r, t, 1, from, to);
    	sut.close();
    	
    	sut.open();
    	sut.gehituErreklamazioa(t, d, "DeskripzioTest", 4.0f, rr);
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
     * getMezuak AtriNullException salbuespena altxatzen duela
     * profil nulu bat sartuz gero berifikatzen duen testa
     * @author Beñat Ercibengoa Calvo
     */
    @Test
	public void testNull() {
       	assertThrows("Ez du AtriNullException salbuespena altxatzen", AtriNullException.class, ()->{
            sut.open();
    		sut.getMezuak(null);
    		sut.close();
    		});	
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
	private Ride addRide(String from, String to, Date date, int nPlaces, /* float price */ List<Float> price,
			String driverUser, Kotxe kotxe, List<String> ibilbide) {
		sut.open();
		Ride ride = null;
		try {
			ride = sut.createRide(from, to, date, nPlaces, prezioak, driverUser, kotxe, ibilbide);
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
