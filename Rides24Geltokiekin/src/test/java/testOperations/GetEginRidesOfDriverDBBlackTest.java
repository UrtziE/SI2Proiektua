package testOperations;

import static org.junit.Assert.assertEquals;
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
import exceptions.RideAlreadyExistException;
import exceptions.RideMustBeLaterThanTodayException;

/**
 * Klase honek getRidesOfDriver metodoaren kutxa beltzaren testing-a egingo du.
 * 
 * @author Ekaitz Pinedo Alvarez
 */
public class GetEginRidesOfDriverDBBlackTest {
	private DataAccess db = new DataAccess();
	private TestDataAccess testdb = new TestDataAccess();

	private String user = "Antton";
	private String email = "antton@gmail.com";
	private Driver driver;
	private Kotxe kotxe = new Kotxe();
	private Ride ride;

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

	}

	@After
	public void bukatu() {
		try {
			testdb.open();
			if (createdDriver) {
				testdb.removeDriver(user);
			}
			if (rideNum != -1) {
				testdb.removeRide(rideNum);
			}

			System.out.println("Ezabaketak eginda");
		} catch (Exception e) {
			e.printStackTrace();
			fail("Ezabaketetan arazoa");
		} finally {
			testdb.close();
		}
	}

	/**
	 * Driver parametroan null aldagaia sartu
	 * 
	 * @author Ekaitz Pinedo Alvarez
	 */
	@Test
	public void test1() {

		System.out.println("1. Test: null driver -> error ");
		assertThrows(AtriNullException.class, () -> {
			db.open();
			List<RideContainer> rides = db.getEginRidesOfDriver(null);
			db.close();
		});

	}

	/**
	 * Driver ez existitu datu basean
	 * 
	 * @author Ekaitz Pinedo Alvarez
	 */
	@Test
	public void test2() {

		System.out.println("2. Test: Driver-a ez dago datu basean");

		driver = new Driver();
		assertThrows(AtriNullException.class, () -> {
			db.open();
			List<RideContainer> rides = db.getEginRidesOfDriver(driver);
			db.close();
		});

	}

	/**
	 * Driver existitu eta ride bat Martxan edo Tokirik gabeko egoeran
	 * 
	 * @author Ekaitz Pinedo Alvarez
	 */
	@Test
	public void test3() {

		System.out.println("3. Test: Driver-a badu aktibo dagoen bidai bat");

		driver = addDriver(user, email);
		ride = new Ride(4, from, to, date, places, prezioak, driver, kotxe, ibilbide);
		ride.setEgoera(EgoeraRide.TOKIRIK_GABE);
		db.open();
		List<RideContainer> rides = db.getEginRidesOfDriver(driver);
		db.close();
		int emaitza = rides.size();
		assertEquals(0, emaitza);

	}

	/**
	 * Driver existitu eta ez dago ezta ride bat Martxan edo Tokirik gabeko egoeran
	 * 
	 * @author Ekaitz Pinedo Alvarez
	 */
	@Test
	public void test4() {

		System.out.println("4. Test: Driver-a ez ditu aktibo dauden bidairik");

		driver = addDriver(user, email);
		ride = new Ride(4, from, to, date, places, prezioak, driver, kotxe, ibilbide);
		ride.setEgoera(EgoeraRide.KANTZELATUA);
		driver.getRides().add(ride);
		db.open();
		List<RideContainer> rides = db.getEginRidesOfDriver(driver);
		db.close();
		int emaitza = rides.size();
		assertEquals(0, emaitza);

	}

	private Driver addDriver(String user, String email) {
		testdb.open();
		driver = testdb.existDriver(user);
		if (driver == null) {
			driver = testdb.createDriver(user, email);
			createdDriver = true;
		} else {
			driver = null;
			createdDriver = false;
			fail("Gidaria jadanik existitzen da, ezin dira guztiz zehatz jakin erantzunak");
		}
		testdb.close();
		return driver;

	}

}
