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

public class GetRidesDBBlackTest {

	DataAccess db = new DataAccess();
	TestDataAccess testdb = new TestDataAccess();
	private String from = "Bera";
	private String to = "Irun";
	private String user = "tester00";
	private String email = "tester00@gmail.com";
	SimpleDateFormat f = new SimpleDateFormat("dd/MM/yyyy");
	private String noiz = "25/10/2030";
	private Date date = null;
	private String matrikula = "9321CRNN";
	int places = 4;


	private int rideNum=-1;
	private boolean createdCar;
	private boolean createdDriver;
	List<Float> prezioak = new ArrayList<Float>();

	Kotxe kotxe = new Kotxe();
	List<String> ibilbide = new ArrayList<String>();

	@Before
	public void initialize() {
		System.out.println("Initialize	and	check	...");
		try {
			date = f.parse(noiz);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		prezioak = Arrays.asList(4.0f, 4.0f, 4.0f);
		ibilbide = Arrays.asList("Bera", "Lesaka", "Irun");
		from = "Bera";
		to = "Irun";
		rideNum=-1;
		createdCar=false;
		createdDriver=false;
	}


	@Test
	public void testGetRides1() {

		db.open();
		List<Ride> rides = db.getRides(from, to, date);
		db.close();
		List<Ride> rideExpected = new ArrayList<Ride>();
		assertEquals(rideExpected, rides);


	}

	@Test
	public void testGetRides2() {

		prezioak = Arrays.asList(4.0f, 4.0f);
		ibilbide = Arrays.asList("Bera", "Irun");
		to="Lesaka";
		Driver driver = addDriver(user,email);
		addCar(matrikula,places,driver);
		addRide(from, to, date, places, prezioak, user, kotxe, ibilbide);
		db.open();
		List<Ride> rides = db.getRides(from, to, date);
		db.close();
		List<Ride> expectedRide = new ArrayList<Ride>();
		assertEquals(expectedRide, rides);

	}

	@Test
	public void testGetRides3() {
		prezioak = Arrays.asList(4.0f, 4.0f);
		ibilbide = Arrays.asList("Lesaka", "Irun");
		Driver driver = addDriver(user,email);
		addCar(matrikula,places,driver);
		db.open();
		addRide(from, to, date, places, prezioak, user, kotxe, ibilbide);
		db.open();
		List<Ride> rides = db.getRides(from, to, date);
		db.close();
		List<Ride> expectedRide = new ArrayList<Ride>();
		assertEquals(expectedRide, rides);
	}

	@Test
	public void testGetRides4() {
		Driver driver = addDriver(user,email);
		addCar(matrikula,places,driver);
		db.open();
		Ride ride = addRide(from, to, date, places, prezioak, user, kotxe, ibilbide);
		db.open();
		List<Ride> rides = db.getRides(from, to, date);
		db.close();
		List<Ride> expectedRide = new ArrayList<Ride>();
		expectedRide.add(ride);
		assertEquals(expectedRide, rides);
	}
	private boolean addCar(String matrikula, int tokiKop,Driver driver) {
		db.open();
		createdCar = db.createCar("Seat", "ibiza", matrikula, tokiKop, driver);
		db.close();
		testdb.open();
		kotxe = testdb.getCar(matrikula);
		testdb.close();
		return createdCar;
	}
	private Driver addDriver(String user, String email) {
		testdb.open();
		Driver driver = testdb.existDriver(user);
		if (driver == null) {
			driver = testdb.createDriver(user, email);
			createdDriver=true;
		}else {
			driver=null;
			createdDriver=false;
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
			ride = db.createRide(from, to, date, nPlaces, prezioak, user, kotxe, ibilbide);
			rideNum=ride.getRideNumber();
		} catch (RideAlreadyExistException e) {
			fail("That Ride exists, you must change");
		} catch (RideMustBeLaterThanTodayException e) {
			fail("Ride Must Be Later Than Today");
		}
		db.close();
		return ride;
		
	}
	@After
	public void bukatu() {
		try {
			testdb.open();
			if(rideNum>0) {
			testdb.removeRide(rideNum);
			testdb.open();
			}
			if (createdCar) {
				testdb.removeCar(matrikula);
			}
			if(createdDriver) {
				testdb.removeDriver(user);
			}
			testdb.close();
		} catch (Exception e) {
			fail("Imposible");
		}
	}

}
