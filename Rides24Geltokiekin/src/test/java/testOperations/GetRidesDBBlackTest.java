package testOperations;

import static org.junit.Assert.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
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
import exceptions.*;
public class GetRidesDBBlackTest {

	DataAccess db = new DataAccess();
	TestDataAccess testdb = new TestDataAccess();
	private String from = "Bera";
	private String to = "Irun";
	private String user = "tester00";
	private String email = "tester00@gmail.com";
	SimpleDateFormat f = new SimpleDateFormat("dd/MM/yyyy");
	private String noiz = "25/10/2230";
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
	public void testGetRidesDenakOndoSeatekin() {
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
	@Test
	public void testGetRidesDenakOndoSeatekinDateToday() {
		date= new Date();
		String dateString= f.format(date);
		try {
			date=f.parse(dateString);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Driver driver = addDriver(user,email);
		addCar(matrikula,places,driver);
		db.open();
		addRideDataGabe(from, to, date, places, prezioak, user, kotxe, ibilbide);
		db.open();
	
		List<Ride> rides = db.getRides(from, to, date);
		db.close();
		List<Ride> expectedRide = new ArrayList<Ride>();
		assertEquals(expectedRide, rides);
	}
	@Test
	public void testGetRidesDenakOndoSeatekinBeforeToday() {
		try {
			date=f.parse("22/10/2014");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Driver driver = addDriver(user,email);
		addCar(matrikula,places,driver);
		db.open();
		addRideDataGabe(from, to, date, places, prezioak, user, kotxe, ibilbide);
		db.open();
		List<Ride> rides = db.getRides(from, to, date);
		db.close();
		List<Ride> expectedRide = new ArrayList<Ride>();
		assertEquals(expectedRide, rides);
	}
	
	@Test
	public void testGetRidesSeat0() {

		Driver driver = addDriver(user,email);
		addCar(matrikula,places,driver);
		
		addRide(from, to, date, places, prezioak, user, kotxe, ibilbide);
		testdb.open();
		testdb.ezabatuRideSeats(from, rideNum);
		testdb.close();
		db.open();
		List<Ride> rides = db.getRides(from, to, date);
		db.close();
		List<Ride> expectedRide = new ArrayList<Ride>();
		assertEquals(expectedRide, rides);
	}
	
	@Test
	public void testGetRidesFromNull() {

		Driver driver = addDriver(user,email);
		addCar(matrikula,places,driver);
		addRide(from, to, date, places, prezioak, user, kotxe, ibilbide);
		db.open();
		assertThrows(AtriNullException.class,()->{db.getRides(null, to, date);});
		db.close();
		


	}
@Test
	public void testGetRidesToNull() {
	Driver driver = addDriver(user,email);
	addCar(matrikula,places,driver);
	addRide(from, to, date, places, prezioak, user, kotxe, ibilbide);
	db.open();
	assertThrows(AtriNullException.class,()->{db.getRides(from, null, date);});
	db.close();

	}

	@Test
	public void testGetRidesDateNull() {
		prezioak = Arrays.asList(4.0f, 4.0f);
	Driver driver = addDriver(user,email);
	addCar(matrikula,places,driver);
	addRide(from, to, date, places, prezioak, user, kotxe, ibilbide);
	
	db.open();
	assertThrows(AtriNullException.class,()->{db.getRides(from, to, null);});
	db.close();
	
	}
	@Test
	public void testGetRidesFromNotExistsDB() {
	Driver driver = addDriver(user,email);
	addCar(matrikula,places,driver);
	addRide(from, to, date, places, prezioak, user, kotxe, ibilbide);
	
	from="1234452";
	db.open();
	List<Ride> rides = db.getRides(from, to,date);
	db.close();
	
	List<Ride>expectedRides= new ArrayList<Ride>();
	assertEquals(expectedRides,rides);
	}
	@Test
	public void testGetRidesToNotExistsDB() {
		
	Driver driver = addDriver(user,email);
	addCar(matrikula,places,driver);
	addRide(from, to, date, places, prezioak, user, kotxe, ibilbide);
	
	to="1234452";
	db.open();
	List<Ride> rides = db.getRides(from, to,date);
	db.close();
	
	List<Ride>expectedRides= new ArrayList<Ride>();
	assertEquals(expectedRides,rides);
	}
	@Test
	public void testGetRidesDateNotExistsDB() {

	Driver driver = addDriver(user,email);
	addCar(matrikula,places,driver);
	addRide(from, to, date, places, prezioak, user, kotxe, ibilbide);
	
	try {
		date=f.parse("11/11/2499");
	} catch (ParseException e) {
		e.printStackTrace();
	}
	db.open();
	List<Ride> rides = db.getRides(from, to,date);
	db.close();
	
	List<Ride>expectedRides= new ArrayList<Ride>();
	assertEquals(expectedRides,rides);
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
	private Ride addRideDataGabe(String from, String to, Date date, int nPlaces, /* float price */ List<Float> price,
			String driverUser, Kotxe kotxe, List<String> ibilbide) {
		testdb.open();
		Ride ride = null;
		try {
			ride = testdb.createRideDataGabe(from, to, date, nPlaces, prezioak, user, kotxe, ibilbide);
			rideNum=ride.getRideNumber();
		} catch (RideAlreadyExistException e) {
			fail("That Ride exists, you must change");
		} 
		testdb.close();
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
