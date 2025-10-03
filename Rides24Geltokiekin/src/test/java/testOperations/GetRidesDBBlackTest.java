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
import domain.Geltoki;
import domain.Kotxe;
import domain.Ride;
import exceptions.RideAlreadyExistException;
import exceptions.RideMustBeLaterThanTodayException;
import exceptions.*;
public class GetRidesDBBlackTest {

	DataAccess sut = new DataAccess();
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
	/**
	 * Test-en aurretik parametroak hasieratzeko metodoa
	 * @author Urtzi Etxegarai Taberna
	 */
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
	/**
	 * DB-an sartutako gauza berriak ezabatzeko metodoa
	 * @author Urtzi Etxegarai Taberna
	 */
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

	/**
	 * Test honek baliozko bidaia bat DB-an gorde eta hau bilatu egiten du
	 * Konprobatu egiten da itzultzen duen bidaia DB-an gorde dugunaren berdina dela
	 * @author Urtzi Etxegarai Taberna
	 */
	@Test
	public void testGetRidesDenakOndoSeatekin() {
		Driver driver = addDriver(user,email);
		addCar(matrikula,places,driver);
		sut.open();
		Ride ride = addRide(from, to, date, places, prezioak, user, kotxe, ibilbide);
		sut.open();
		List<Ride> rides = sut.getRides(from, to, date);
		sut.close();
		List<Ride> expectedRide = new ArrayList<Ride>();
		expectedRide.add(ride);
		assertEquals(expectedRide, rides);
	}
	/**
	 * Test honek "gaur"-ko datarekin bidaia bat sortu eta DB-an gorde egiten du, getRides-ek ride hau bilatu egiten du
	 * Konprobatu egiten da getRides-ek ez duela ezer itzuli, "gaur"-ko eguneko bidaiak "PASATUTA" egoeran baitaude, 
	 * ez "MARTXAN" egoeran
	 * @author Urtzi Etxegarai Taberna
	 */
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
		sut.open();
		addRideDataGabe(from, to, date, places, prezioak, user, kotxe, ibilbide);
		sut.open();
	
		List<Ride> rides = sut.getRides(from, to, date);
		sut.close();
		List<Ride> expectedRide = new ArrayList<Ride>();
		assertEquals(expectedRide, rides);
	}
	/**
	 * Test honek iraganeko data batekin bidaia bat sortu eta DB-an gorde egiten du, getRides-ek ride hau bilatu egiten du
	 * Konprobatu egiten da getRides-ek ez duela ezer itzuli, iraganeko bidaiak "PASATUTA" egoeran baitaude, 
	 * ez "MARTXAN" egoeran
	 * @author Urtzi Etxegarai Taberna
	 */
	@Test
	public void testGetRidesDenakOndoSeatekinBeforeToday() {
		try {
			date=f.parse("22/10/2014");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Driver driver = addDriver(user,email);
		addCar(matrikula,places,driver);
		sut.open();
		addRideDataGabe(from, to, date, places, prezioak, user, kotxe, ibilbide);
		sut.open();
		List<Ride> rides = sut.getRides(from, to, date);
		sut.close();
		List<Ride> expectedRide = new ArrayList<Ride>();
		assertEquals(expectedRide, rides);
	}
	/**
	 * Test honek  bidaia bat sortu egiten du non ez duen geltokirik ibilbide posibleetako batean eta DB-an gorde egiten du,
	 * getRides-ek eserlekurik ez duen ibilbidea bilatuko du
	 * Konprobatu egiten da getRides-ek ez duela ezer itzuli, ez badago tokirik eskatutako "from"-etik "to"-ra
	 * ez baitu balio bidaiak
	 * @author Urtzi Etxegarai Taberna
	 */
	@Test
	public void testGetRidesSeat0() {

		Driver driver = addDriver(user,email);
		addCar(matrikula,places,driver);
		
		addRide(from, to, date, places, prezioak, user, kotxe, ibilbide);
		testdb.open();
		testdb.ezabatuRideSeats(from, rideNum);
		testdb.close();
		sut.open();
		List<Ride> rides = sut.getRides(from, to, date);
		sut.close();
		List<Ride> expectedRide = new ArrayList<Ride>();
		assertEquals(expectedRide, rides);
	}
	/**
	 * Test honek baliozko bidaia bat gorde egiten du DB-an eta getRides metodoa from atributua null-ekin deitzen du
	 * Konprobatu egiten da ea getRides-ek AtriNullException altxatzen duen
	 * @author Urtzi Etxegarai Taberna
	 */
	@Test
	public void testGetRidesFromNull() {

		Driver driver = addDriver(user,email);
		addCar(matrikula,places,driver);
		addRide(from, to, date, places, prezioak, user, kotxe, ibilbide);
		sut.open();
		assertThrows(AtriNullException.class,()->{sut.getRides(null, to, date);});
		sut.close();
		


	}
	/**
	 * Test honek baliozko bidaia bat gorde egiten du DB-an eta getRides metodoa "to" atributua null-ekin deitzen du
	 * Konprobatu egiten da ea getRides-ek AtriNullException altxatzen duen
	 * @author Urtzi Etxegarai Taberna
	 */
@Test
	public void testGetRidesToNull() {
	Driver driver = addDriver(user,email);
	addCar(matrikula,places,driver);
	addRide(from, to, date, places, prezioak, user, kotxe, ibilbide);
	sut.open();
	assertThrows(AtriNullException.class,()->{sut.getRides(from, null, date);});
	sut.close();

	}
/**
 * Test honek baliozko bidaia bat gorde egiten du DB-an eta getRides metodoa "date" atributua null-ekin deitzen du
 * Konprobatu egiten da ea getRides-ek AtriNullException altxatzen duen
 * @author Urtzi Etxegarai Taberna
 */
	@Test
	public void testGetRidesDateNull() {
		prezioak = Arrays.asList(4.0f, 4.0f);
	Driver driver = addDriver(user,email);
	addCar(matrikula,places,driver);
	addRide(from, to, date, places, prezioak, user, kotxe, ibilbide);
	
	sut.open();
	assertThrows(AtriNullException.class,()->{sut.getRides(from, to, null);});
	sut.close();
	
	}
	/**
	 * Test honek baliozko bidaia bat gorde egiten du DB-an, getRides-en "from" atributua ez dago DB-ko bidaia
	 * baten ibilbidean
	 * Konprobatu egiten da ea getRides-ek lista huts bat itzultzen duen, ez baitago eskatutakoa betetzen duen bidairik
	 * @author Urtzi Etxegarai Taberna
	 */
	@Test
	public void testGetRidesFromNotExistsDB() {
	Driver driver = addDriver(user,email);
	addCar(matrikula,places,driver);
	addRide(from, to, date, places, prezioak, user, kotxe, ibilbide);
	
	from="1234452";
	sut.open();
	List<Ride> rides = sut.getRides(from, to,date);
	sut.close();
	
	List<Ride>expectedRides= new ArrayList<Ride>();
	assertEquals(expectedRides,rides);
	}
	/**
	 * Test honek baliozko bidaia bat gorde egiten du DB-an, getRides-en "to" atributua ez dago DB-ko bidaia
	 * baten ibilbidean
	 * Konprobatu egiten da ea getRides-ek lista huts bat itzultzen duen, ez baitago eskatutakoa betetzen duen bidairik
	 * @author Urtzi Etxegarai Taberna
	 */
	@Test
	public void testGetRidesToNotExistsDB() {
		
	Driver driver = addDriver(user,email);
	addCar(matrikula,places,driver);
	addRide(from, to, date, places, prezioak, user, kotxe, ibilbide);
	
	to="1234452";
	sut.open();
	List<Ride> rides = sut.getRides(from, to,date);
	sut.close();
	
	List<Ride>expectedRides= new ArrayList<Ride>();
	assertEquals(expectedRides,rides);
	}
	/**
	 * Test honek baliozko bidaia bat gorde egiten du DB-an, getRides-en "date" atributua ez dago DB-ko bidaia
	 * baten ibilbidean
	 * Konprobatu egiten da ea getRides-ek lista huts bat itzultzen duen, ez baitago eskatutakoa betetzen duen bidairik
	 * @author Urtzi Etxegarai Taberna
	 */
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
	sut.open();
	List<Ride> rides = sut.getRides(from, to,date);
	sut.close();
	
	List<Ride>expectedRides= new ArrayList<Ride>();
	assertEquals(expectedRides,rides);
	}
	
	private boolean addCar(String matrikula, int tokiKop,Driver driver) {
		sut.open();
		createdCar = sut.createCar("Seat", "ibiza", matrikula, tokiKop, driver);
		sut.close();
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
	private Ride addRideDataGabe(String from, String to, Date date, int nPlaces, /* float price */ List<Float> price,
			String driverUser, Kotxe kotxe, List<String> ibilbide) {
		testdb.open();
		Ride ride = null;
		try {
			ride = testdb.createRideDataGabe(from, to, date, nPlaces, price, driverUser, kotxe, ibilbide);
			rideNum=ride.getRideNumber();
		} catch (RideAlreadyExistException e) {
			fail("That Ride exists, you must change");
		} 
		testdb.close();
		return ride;
		
	}



}
