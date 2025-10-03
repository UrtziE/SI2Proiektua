
package mockTests;

import static org.junit.Assert.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
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
import domain.Alerta;
import domain.Driver;
import domain.Kotxe;
import domain.Mezua;
import domain.Profile;
import domain.Ride;
import domain.RideContainer;
import domain.RideRequest;
import domain.Traveller;
import exceptions.AtriNullException;
import testOperations.TestDataAccess;

public class GetEginRidesOfDriverMockBlackTest {
	static DataAccess sut;
	protected MockedStatic<Persistence> persistenceMock;
	@Mock
	protected EntityManagerFactory entityManagerFactory;
	@Mock
	protected EntityManager db;
	@Mock
	protected EntityTransaction et;
			
	private String from = "Bera";
	private String to = "Irun";
	private String user = "Antton";
	private String email = "antton@gmail.coantton@gmail.co";
	SimpleDateFormat f = new SimpleDateFormat("dd/MM/yyyy");
	private String noiz = "1/5/2027";
	private Date date = null;
	private String matrikula = "1234AAAA";
	int places = 4;
	
	private int rideNum=-1;
	private boolean createdCar;
	private boolean createdDriver;
	List<Float> prezioak = new ArrayList<Float>();
	List<String> ibilbide = new ArrayList<String>();
	
	@Mock
	 protected TypedQuery<Ride> typedQueryRide;
	@Mock
	protected TypedQuery<Ride> queryKonprobatuEgunak;



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

		
        
		
		Mockito.when(db.createQuery("SELECT r FROM Ride r", Ride.class))
        .thenReturn(queryKonprobatuEgunak);
		List<Ride>expected= new ArrayList<Ride>();
		Mockito.when(queryKonprobatuEgunak.getResultList()).thenReturn(expected);
	 }
	
	 
    @Test
	public void test1() {
    	System.out.println("1. Test: null driver -> error ");
    	
       	assertThrows(AtriNullException.class, ()->{
            sut.open();
    		sut.getEginRidesOfDriver(null);
            sut.close();
    		});	
    }
	
    @Test
	public void test2() {
    	System.out.println("2. Test: Driver ez null eta bidaiak dituenak");
    	
    	Driver driver = new Driver(user,email);
		Kotxe kotxe=new Kotxe();
        Mockito.when(db.createQuery("SELECT r FROM Ride r", Ride.class))
        .thenReturn(queryKonprobatuEgunak);
        Mockito.when(db.find(Driver.class, user)).thenReturn(driver);
        
        driver.addRide(from, to, date, places, prezioak, kotxe, ibilbide);
		
		
		
		sut.open();
		List<RideContainer> rides = sut.getEginRidesOfDriver(driver);
    	sut.close();
    	
    	
        assertFalse(rides.isEmpty());
	}
	
   
	
	
	
	@After
	public void tearDown() {
		persistenceMock.close();
	}
	

}
