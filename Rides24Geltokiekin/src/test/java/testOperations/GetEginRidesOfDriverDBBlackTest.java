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


public class GetEginRidesOfDriverDBBlackTest {
    private DataAccess db = new DataAccess();
    private TestDataAccess testdb = new TestDataAccess();

    private String user = "Antton";
    private String email = "antton@gmail.com";
    private Driver driver;
    private boolean createdDriver = false;

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
            System.out.println("Ezabaketak eginda");
        } catch (Exception e) {
            e.printStackTrace();
            fail("Ezabaketetan arazoa");
        } finally {
            testdb.close();
        }
    }

    @Test
    public void test1() {
        
        System.out.println("1. Test: null driver -> error ");
        
        db.open();
        try {
        	List<RideContainer> rides = db.getEginRidesOfDriver(null);
        	fail("Ez du errorerik eman");
        }catch(Exception e) {
        	System.out.println("Errorea eman du");
        }
        db.close();

        
    }

    @Test
    public void test2() {
       
        System.out.println("2. Test: Driver con ride activo");

        driver = addDriver(user, email);

        db.open();
        List<RideContainer> rides = db.getEginRidesOfDriver(driver);
        db.close();
        
        assertEquals(rides.size(),0);
        
        
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
	
}





