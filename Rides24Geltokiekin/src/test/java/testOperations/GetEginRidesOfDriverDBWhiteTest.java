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
/**
 * Klase honek getRidesOfDriver metodoaren kutxa txuriaren testing-a egingo du.
 * 
 * @author Ekaitz Pinedo Alvarez
 */
public class GetEginRidesOfDriverDBWhiteTest {

    private DataAccess sut = new DataAccess();
    private TestDataAccess testdb = new TestDataAccess();

    private String from = "Bera";
    private String to = "Irun";
    private String user = "Antton";
    private String email = "antton@gmail.com";

    private SimpleDateFormat f = new SimpleDateFormat("dd/MM/yyyy");
    private String noiz = "7/1/2027";
    private Date date = null;
    private String matrikula = "1234AAAA";
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
            System.out.println("Ezabaketak eginda");
        } catch (Exception e) {
            e.printStackTrace();
            fail("Ezabaketetan arazoa");
        } finally {
            testdb.close();
        }
    }
    
    /**
	 * Driver-ak ez ditu bidairik
	 * @author Ekaitz Pinedo Alvarez
	 */
    @Test
    public void testBidairikEz() {
        
        System.out.println("1. Test: Driver bidairik gabe");

        sut.open();
        List<RideContainer> rides = sut.getEginRidesOfDriver(driver);
        sut.close();

        assertEquals(new ArrayList<RideContainer>(), rides);
    }
    /**
	 * Driver-ak badu bidai bat martxan
	 * @author Ekaitz Pinedo Alvarez
	 */
    @Test
    public void testBidaiBatAktibo() {
       
        System.out.println("2. Test: Driver martxan dagoen bidai batekin");

        Ride ride = addRide(from, to, date, places,kotxe, ibilbide);

        testdb.open();
        driver = testdb.existDriver(user);
        testdb.close();

<<<<<<< HEAD
        db.open();
        List<RideContainer> rides = db.getEginRidesOfDriver(driver);
        db.close();
=======
        sut.open();
        List<RideContainer> rides = sut.getEginRidesOfDriver(driver);
        sut.close();
        
        System.out.println(rides.toString());
>>>>>>> branch 'main' of https://github.com/UrtziE/SI2Proiektua


        List<RideContainer>expected= new ArrayList<RideContainer>();
        expected.add(new RideContainer(ride));
        assertEquals(expected, rides);
    }
    
    /**
	 * Driver-ak ez du martxan edo tokirik gabeko bidairik
	 * @author Ekaitz Pinedo Alvarez
	 */
    @Test
    public void testEzDagoBidaiAktiborik() {
        
        System.out.println("3. Test: Driver martxan ez dagoen bidai batekin");

        Ride ride = addRide(from, to, date, places, kotxe, ibilbide);

        sut.open();
        sut.kantzelatu(ride);
        sut.close();

        testdb.open();
        driver = testdb.existDriver(user);
        testdb.close();

        sut.open();
        List<RideContainer> rides = sut.getEginRidesOfDriver(driver);
        sut.close();

        assertEquals(new ArrayList<RideContainer>(), rides);
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
	private Ride addRide(String from, String to, Date date, int nPlaces,  Kotxe kotxe, List<String> ibilbide) {
		sut.open();
		Ride ride = null;
		try {
			ride = sut.createRide(from, to, date, nPlaces, prezioak, user, kotxe, ibilbide);
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
