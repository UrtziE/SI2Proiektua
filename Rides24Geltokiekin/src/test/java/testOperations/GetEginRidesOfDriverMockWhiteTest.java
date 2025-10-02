
package testOperations;

import static org.junit.Assert.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import dataAccess.DataAccess;
import domain.Driver;
import domain.Kotxe;
import domain.Ride;
import domain.RideContainer;

//UrtziMokito
public class GetEginRidesOfDriverMockWhiteTest {
	static DataAccess sut;
	protected MockedStatic<Persistence> persistenceMock;
	@Mock
	protected EntityManagerFactory entityManagerFactory;
	@Mock
	protected EntityManager db;
	@Mock
	protected EntityTransaction et;
	@Mock
	 protected TypedQuery<Ride> typedQueryRide;
	@Mock
	protected TypedQuery<Ride> queryKonprobatuEgunak;
	TestDataAccess testdb ;
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
	List<String> ibilbide = new ArrayList<String>();
	@Before
	 public void init() {
	MockitoAnnotations.openMocks(this);
	persistenceMock = Mockito.mockStatic(Persistence.class);
	persistenceMock.when(() ->
	Persistence.createEntityManagerFactory(Mockito.any())).thenReturn(entityManagerFactory);
	Mockito.doReturn(db).when(entityManagerFactory).createEntityManager();
	Mockito.doReturn(et).when(db).getTransaction();
	sut=new DataAccess(db);
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
	public void test1() {
		System.out.println("1. Test: Driver bidairik gabe");
		
		Driver driver = new Driver(user,email);
        List<Ride> expected = new ArrayList<>();
        Mockito.when(db.createQuery("SELECT r FROM Ride r", Ride.class))
        .thenReturn(queryKonprobatuEgunak);
        Mockito.when(db.find(Driver.class, user)).thenReturn(driver);

       
        sut.open();
        List<RideContainer> rides = sut.getEginRidesOfDriver(driver);
        sut.close();

        assertEquals(expected, rides);
	}
	
	@Test
	public void test2() {
		
		System.out.println("2. Test: Driver martxan dagoen bidai batekin");

		Driver driver = new Driver(user,email);
		Kotxe kotxe=new Kotxe();
        Mockito.when(db.createQuery("SELECT r FROM Ride r", Ride.class))
        .thenReturn(queryKonprobatuEgunak);
        Mockito.when(db.find(Driver.class, user)).thenReturn(driver);
        Ride ride= new Ride(4,from, to, date, places, prezioak, driver, kotxe, ibilbide);
        
        driver.addRide(from, to, date, places, prezioak, kotxe, ibilbide);
        sut.open();
        List<RideContainer> rides = sut.getEginRidesOfDriver(driver);
        sut.close();
        System.out.println(rides.get(0).getRide().getRideNumber());//Galdetu urtziri 4 ride number moduan utzi edo bilatu beste era bat

        Ride expected = ride;
        assertEquals(1,rides.size());
        assertEquals(expected, rides.get(0).getRide());
	}
	

    @Test
    public void test3() {
        
        System.out.println("3. Test: Driver martxan ez dagoen bidai batekin");

		Driver driver = new Driver(user,email);
		Kotxe kotxe=new Kotxe();
		Ride ride= new Ride(4,from, to, date, places, prezioak, driver, kotxe, ibilbide);
		driver.addRide(from, to, date, places, prezioak, kotxe, ibilbide);
		
        Mockito.when(db.createQuery("SELECT r FROM Ride r", Ride.class))
        .thenReturn(queryKonprobatuEgunak);
        Mockito.when(db.find(Driver.class, user)).thenReturn(driver);
        Mockito.when(db.find(Ride.class, ride.getRideNumber())).thenReturn(ride);
        
        
        
        
        sut.open();
        sut.kantzelatu(ride);
        
        List<RideContainer> rides = sut.getEginRidesOfDriver(driver);
        sut.close();
        //System.out.println(rides.getRide());
        assertEquals(new ArrayList<RideContainer>(), rides);
    }
	
	@After
	public void tearDown() {
	persistenceMock.close();
	 }

}

