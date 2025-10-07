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
	 * Existitzen ez den bidaia bat lortzen saiatzen da.
	 * Konprobatu egiten da lista hutsa itzultzen duela getRides metodoak
	 * @author Urtzi Etxegarai Taberna
	 */
	@Test
	
	public void testDBEzDagoBidaiHori() {

	sut.open();
		List<Ride> rides = sut.getRides(from, to, date);
		sut.close();
		List<Ride> rideExpected = new ArrayList<Ride>();
		assertEquals(rideExpected, rides);


	}
	/**
	 * "To" atributua duen bidaia bat lortzen saiatzen da, hau, ez da exisitzen
	 * Ibilbide barruan to ez dagoenez konprobatu egiten da lista hutsa itzultzen duela getRides metodoak
	 * @author Urtzi Etxegarai Taberna
	 */
	@Test
	public void toNotDB() {

		prezioak = Arrays.asList(4.0f, 4.0f);
		ibilbide = Arrays.asList("Bera", "Irun");
		
		Driver driver = addDriver(user,email);
		addCar(matrikula,places,driver);
		addRide(from, to, date, places, prezioak, user, kotxe, ibilbide);
		to="Lesaka";
		sut.open();
		List<Ride> rides = sut.getRides(from, to, date);
		sut.close();
		List<Ride> expectedRide = new ArrayList<Ride>();
		assertEquals(expectedRide, rides);

	}
	/**
	 * Testeatu egiten du eskatu egiten badiogu from bat ez dagoena DB ezta bidaia baten ibilbidean
	 * Ibilbide barruan from ez dagoenez konprobatu egiten da lista hutsa itzultzen duela getRides metodoak
	 * @author Urtzi Etxegarai Taberna
	 */
	@Test
	public void fromNotDB() {
		prezioak = Arrays.asList(4.0f, 4.0f);
		ibilbide = Arrays.asList("Lesaka", "Irun");
		Driver driver = addDriver(user,email);
		addCar(matrikula,places,driver);
		sut.open();
		addRide(from, to, date, places, prezioak, user, kotxe, ibilbide);
		sut.open();
		List<Ride> rides = sut.getRides(from, to, date);
		sut.close();
		List<Ride> expectedRide = new ArrayList<Ride>();
		assertEquals(expectedRide, rides);
	}
	/**
	 * Test honek bidaia bat sortu, DB-an sartu eta bidaia honen bilaketa egiten du
	 * Konprobatu egiten du guk DB-an sortutako bidaia lista batean itzultzen duela getRides metodoak
	 * @author Urtzi Etxegarai Taberna
	 */
	@Test
	public void getRideRidekin() {
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
	 * Metodo laguntzaile bat kotxe bat sortu eta DB-an gordetzeko
	 * @author Urtzi Etxegarai Taberna
	 */
	private boolean addCar(String matrikula, int tokiKop,Driver driver) {
		sut.open();
		createdCar = sut.createCar("Seat", "ibiza", matrikula, tokiKop, driver);
		sut.close();
		testdb.open();
		kotxe = testdb.getCar(matrikula);
		testdb.close();
		return createdCar;
	}
	/**
	 * Metodo laguntzaile bat gidari bat sortu eta DB-an gordetzeko
	 * @author Urtzi Etxegarai Taberna
	 */
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
	/**
	 * Metodo laguntzaile bat bidai bat sortu eta DB-an gordetzeko
	 * @author Urtzi Etxegarai Taberna
	 */
	private Ride addRide(String from, String to, Date date, int nPlaces, /* float price */ List<Float> price,
			String driverUser, Kotxe kotxe, List<String> ibilbide) {
		sut.open();
		Ride ride = null;
		try {
			ride = sut.createRide(from, to, date, nPlaces,  price, driverUser, kotxe, ibilbide);
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
