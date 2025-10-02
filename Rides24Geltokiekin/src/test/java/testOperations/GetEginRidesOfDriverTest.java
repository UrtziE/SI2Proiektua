package testOperations;

import static org.junit.Assert.assertEquals;
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
import domain.Kotxe;
import domain.Ride;
import domain.RideContainer;
import exceptions.RideAlreadyExistException;
import exceptions.RideMustBeLaterThanTodayException;

public class GetEginRidesOfDriverTest {

    private DataAccess db = new DataAccess();
    private TestDataAccess testdb = new TestDataAccess();

    private String from = "Bera";
    private String to = "Irun";
    private String user = "tester00";
    private String email = "tester00@gmail.com";

    private SimpleDateFormat f = new SimpleDateFormat("dd/MM/yyyy");
    private String noiz = "25/10/2030";
    private Date date = null;
    private String matrikula = "9321CRNN";
    private int places = 4;

    private int rideNum = -1;
    private boolean createdCar;
    private boolean createdDriver;

    private List<Float> prezioak;
    private Kotxe kotxe;
    private List<String> ibilbide;
    private Driver driver;

    @Before
    public void initialize() {
        System.out.println("Initialize and check...");
        try {
            date = f.parse(noiz);
        } catch (ParseException e) {
            e.printStackTrace();
            fail("Date parsing failed");
        }

        prezioak = Arrays.asList(4.0f, 4.0f, 4.0f);
        ibilbide = Arrays.asList("Bera", "Lesaka", "Irun");

        rideNum = -1;
        createdCar = false;
        createdDriver = false;

        driver = addDriver(user, email);
        addCar(matrikula, places, driver);
    }

    @After
    public void bukatu() {
        try {
            testdb.open();
            if (rideNum != -1) {
                testdb.removeRide(rideNum);
            }
            if (createdCar) {
                testdb.removeCar(matrikula);
            }
            if (createdDriver) {
                testdb.removeDriver(user);
            }
            System.out.println("Cleanup done");
        } catch (Exception e) {
            e.printStackTrace();
            fail("Cleanup failed");
        } finally {
            testdb.close();
        }
    }

    @Test
    public void test1() {
        
        System.out.println("1. Test: Driver sin rides");

        db.open();
        List<RideContainer> rides = db.getEginRidesOfDriver(driver);
        db.close();

        assertEquals(new ArrayList<RideContainer>(), rides);
    }

    @Test
    public void test2() {
       
        System.out.println("2. Test: Driver con ride activo");

        Ride ride = addRide(from, to, date, places, prezioak, user, kotxe, ibilbide);

        testdb.open();
        driver = testdb.existDriver(user);
        testdb.close();

        db.open();
        List<RideContainer> rides = db.getEginRidesOfDriver(driver);
        db.close();
        
        System.out.println(rides.toString());

        Ride expected = ride;
        assertEquals(1,rides.size());
        assertEquals(expected, rides.get(0).getRide());
    }

    @Test
    public void test3() {
        
        System.out.println("3. Test: Driver con ride cancelado");

        Ride ride = addRide(from, to, date, places, prezioak, user, kotxe, ibilbide);

        db.open();
        db.kantzelatu(ride);
        db.close();

        testdb.open();
        driver = testdb.existDriver(user);
        testdb.close();

        db.open();
        List<RideContainer> rides = db.getEginRidesOfDriver(driver);
        db.close();

        assertEquals(new ArrayList<RideContainer>(), rides);
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
		driver = testdb.existDriver(user);
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
}
