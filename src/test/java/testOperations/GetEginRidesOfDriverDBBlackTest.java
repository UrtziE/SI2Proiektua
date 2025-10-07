package testOperations;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.fail;

import java.text.ParseException;
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
import domain.EgoeraRide;
import domain.Kotxe;
import domain.Ride;
import domain.RideContainer;
import exceptions.AtriNullException;
import exceptions.DriverNotInDBException;
import exceptions.RideAlreadyExistException;
import exceptions.RideMustBeLaterThanTodayException;

/**
 * Klase honek getRidesOfDriver metodoaren kutxa beltzaren testing-a egingo du.
 * 
 * @author Ekaitz Pinedo Alvarez
 */
public class GetEginRidesOfDriverDBBlackTest {
	private DataAccess sut = new DataAccess();
	private TestDataAccess testdb = new TestDataAccess();

	private String user = "Antton";
	private String email = "antton@gmail.com";
	private Driver driver;
	private Kotxe kotxe = new Kotxe();
	private Ride ride;
	private String matrikula = "1234AAAA";
	private String from = "Bera";
	private String to = "Irun";
	SimpleDateFormat f = new SimpleDateFormat("dd/MM/yyyy");
	private String noiz = "1/5/2027";
	private Date date = null;
	int places = 4;
	private int rideNum = -1;
	private boolean createdCar = false;
	private boolean createdDriver = false;
	List<Float> prezioak = new ArrayList<Float>();
	List<String> ibilbide = new ArrayList<String>();

	@Before
	public void initialize() {
		System.out.println("Initialize and check...");
		try {
			date = f.parse(noiz);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		prezioak = Arrays.asList(4.0f, 4.0f, 4.0f);
		ibilbide = Arrays.asList("Bera", "Lesaka", "Irun");
		from = "Bera";
		to = "Irun";
		rideNum = -1;
		createdCar = false;
		createdDriver = false;
	}

	@After
	public void bukatu() {
		try {
			testdb.open();
			if (rideNum > 0) {
				testdb.removeRide(rideNum);
				testdb.open();
			}
			if (createdCar) {
				testdb.removeCar(matrikula);
			}
			if (createdDriver) {
				testdb.removeDriver(user);
			}
			testdb.close();
		} catch (Exception e) {
			fail("Imposible");
		}
	}

	/**
	 * Driver parametroan null aldagaia sartu
	 * 
	 * @author Ekaitz Pinedo Alvarez
	 */
	@Test
	public void testDriverNull() {

		System.out.println("1. Test: null driver -> error ");
		assertThrows(AtriNullException.class, () -> {
			sut.open();
			List<RideContainer> rides = sut.getEginRidesOfDriver(null);
			sut.close();
		});

	}

	/**
	 * Driver ez existitu datu basean
	 * 
	 * @author Ekaitz Pinedo Alvarez
	 */
	@Test
	public void testDriverEzDagoDBn() {

		System.out.println("2. Test: Driver-a ez dago datu basean");

		driver = new Driver();

		assertThrows(DriverNotInDBException.class, () -> {
			sut.open();
			List<RideContainer> rides = sut.getEginRidesOfDriver(driver);
			sut.close();
		});

	}

	/**
	 * Driver existitu eta ride bat Martxan edo Tokirik gabeko egoeran
	 * 
	 * @author Ekaitz Pinedo Alvarez
	 */
	@Test
	public void testRideAktibo() {

		System.out.println("3. Test: Driver-a badu aktibo dagoen bidai bat");

		driver = addDriver(user, email);
		addCar(matrikula, places, driver);
		ride = addRide(from, to, date, places, prezioak, user, kotxe, ibilbide);
		sut.open();
		List<RideContainer> rides = sut.getEginRidesOfDriver(driver);
		sut.close();
		Ride expected=ride;
		int emaitza = rides.size();
		assertEquals(1, emaitza);
		assertEquals(expected, rides.get(0).getRide());

	}

	/**
	 * Driver existitu eta ez dago ezta ride bat Martxan edo Tokirik gabeko egoeran
	 * 
	 * @author Ekaitz Pinedo Alvarez
	 */
	@Test
	public void testEzDaudeRideAktiborik() {

		System.out.println("4. Test: Driver-a ez ditu aktibo dauden bidairik");

		driver = addDriver(user, email);
		addCar(matrikula, places, driver);
		ride = addRide(from, to, date, places, prezioak, user, kotxe, ibilbide);
		sut.open();
		sut.kantzelatu(ride);
		List<RideContainer> rides = sut.getEginRidesOfDriver(driver);
		sut.close();
		int emaitza = rides.size();
		assertEquals(0, emaitza);

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

}
