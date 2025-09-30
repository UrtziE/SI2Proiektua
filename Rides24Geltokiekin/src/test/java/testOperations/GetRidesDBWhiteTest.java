package testOperations;

import static org.junit.Assert.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import dataAccess.DataAccess;
import domain.Driver;
import domain.Kotxe;
import domain.Ride;
import exceptions.RideAlreadyExistException;
import exceptions.RideMustBeLaterThanTodayException;

public class GetRidesDBWhiteTest {
	DataAccess db= new DataAccess();
	TestDataAccess testdb= new TestDataAccess();
	
	private String from= "Bera";
	private String to= "Irun";
	private String user= "tester00";
	private String email= "tester00@gmail.com";
    SimpleDateFormat f = new SimpleDateFormat("dd/MM/yyyy");
    private String noiz= "25/10/2030";
    private Date date=null;
    int places=4;
    List<Float> prezioak= new ArrayList<Float>();
    
    Kotxe kotxe= new Kotxe();
    List<String>ibilbide= new ArrayList<String>();
	@Test
	public void testGetRides1() {
	
		try {
			date = f.parse(noiz);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		prezioak= Arrays.asList(4.0f,4.0f,4.0f);
        ibilbide= Arrays.asList("Bera","Lesaka","Irun");
       db.open();
		List<Ride>rides= db.getRides(from, to, date);
		db.close();
		List<Ride>rideExpected= new ArrayList<Ride>();
		System.out.println(rides);
		assertEquals(rideExpected, rides);
	
	}
	@Test
	public void testGetRides2() {
		
		try {
			date = f.parse(noiz);
		} catch (ParseException e) {
			e.printStackTrace();
		}
        prezioak= Arrays.asList(4.0f,4.0f,4.0f);
        ibilbide= Arrays.asList("Bera","Lesaka","Irun");
        testdb.open();
        Driver driver=testdb.existDriver(user);
        testdb.close();
        if(driver==null) {
		testdb.open();
		driver=testdb.createDriver(user, email);
		testdb.close();
        }
        
		db.open();
		db.createCar("Seat", "ibiza", "3453CRN", 4, driver);
		db.close();
		db.open();
		kotxe=db.getKotxeGuztiak(driver).getFirst();
		db.close();
        db.open();
		Ride ride=null;
		try {
			ride = db.createRide(from, to, date, places, prezioak, user, kotxe, ibilbide);
		} catch (RideAlreadyExistException e) {
			fail("That Ride exists");
		} catch (RideMustBeLaterThanTodayException e) {
			fail("Ride Must Be Later Than TOday");
		}
		db.close();
		db.open();
		List<Ride>rides= db.getRides(from, to, date);
		db.close();
		List<Ride>expectedRide= new ArrayList<Ride>();
		expectedRide.add(ride);
		assertEquals(expectedRide,rides);
		try {
			testdb.open();
			testdb.removeRide(ride.getRideNumber());
			testdb.close();
			testdb.open();
			testdb.removeCar("3453CRN");
			testdb.close();
			testdb.open();
			testdb.removeDriver(user);
			testdb.close();
			assertTrue(true);
		}
		catch(Exception e){
			fail("Imposible");
		}
		
		
	}
	public void testGetRides3() {
		fail("Not yet implemented");
	}
	public void testGetRides4() {
		fail("Not yet implemented");
	}

}
