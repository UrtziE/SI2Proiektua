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
import domain.Driver;
import domain.ErreserbaEskaera;
import domain.Kotxe;
import domain.Mezua;
import domain.Profile;
import domain.Ride;
import domain.RideRequest;
import domain.Traveller;
import testOperations.TestDataAccess;

public class GetMezuakMockWhiteTest {

	
	static DataAccess sut;
	protected MockedStatic<Persistence> persistenceMock;
	@Mock
	protected EntityManagerFactory entityManagerFactory;
	@Mock
	protected EntityManager db;
	@Mock
	protected EntityTransaction et;
	TestDataAccess testdb;
			
	private String from= "Donostia";
	private String to= "Bilbo";
	
    SimpleDateFormat f = new SimpleDateFormat("dd/MM/yyyy");
	List<Float> prezioak = new ArrayList<Float>();
	int places = 4;

	private Date date;
    private Driver d;
	private String userT = "userTravellerTest";
	private String emailT = "emailTravellerTest";

	private Traveller t;
	private Kotxe k;
	private Ride r;
	private RideRequest rr;
	private List<String> ibilbide;
	
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

		
        date = new Date(2025, 1, 1);
        ibilbide = Arrays.asList(from,to);
		prezioak = Arrays.asList(4.0f, 4.0f);
		
		t = new Traveller(userT, emailT);
		
		Mockito.when(db.createQuery("SELECT r FROM Ride r", Ride.class))
        .thenReturn(queryKonprobatuEgunak);
		List<Ride>expected= new ArrayList<Ride>();
		Mockito.when(queryKonprobatuEgunak.getResultList()).thenReturn(expected);
	 }
	
	/**
	 * Erreserbarik ez dituen bidaiari baten mezuak lortzen ditu.
	 * Berifikatzen du bidaiari horrek ez dituela mezurik.
	 * Datu basea Mockeatzen da
	 * @author Beñat Ercibengoa Calvo
	 */
	@Test
	public void testGetMezuak1() {
		Mockito.when(db.find(Profile.class, userT)).thenReturn(t);
		sut.open();
        List<Mezua> mezuak = sut.getMezuak(t);
        sut.close();
        assertTrue(mezuak.isEmpty());
	}
	
	/**
	 * Test honek Ride bat sortu, erreserba bat egin eta ondoren erreklamazio bat
	 * sortzen du. Azkenik, bidaiariaren mezuak lortzen ditu.
	 * Berifikatzen du itzulitako mezuak soilik 1 motako mezuak direla.
	 * Datu basea Mockeatzen da
	 * @author Beñat Ercibengoa Calvo
	 */
	 @Test
	public void testGetMezuak2() {
    	r = new Ride(1, from, to, date, places, prezioak, d, k, ibilbide);

		Mockito.when(db.find(Profile.class, userT)).thenReturn(t);
		Mockito.when(db.find(Traveller.class, t.getUser())).thenReturn(t);
		Mockito.when(db.find(Ride.class, 1)).thenReturn(r);

		//Mezua sortu
    	sut.open();
   		ErreserbaEskaera ee = new ErreserbaEskaera(date, r, t, 1, from, to);
    	rr = sut.erreserbatu(ee);
    	rr.setId(1);
    	sut.close();
    	
		Mockito.when(db.find(RideRequest.class, 1)).thenReturn(rr);
		
    	sut.open();
    	sut.gehituErreklamazioa(t, d, "DeskripzioTest", 4.0f, rr);
    	sut.close();
    	
    	sut.open();
        List<Mezua> mezuak = sut.getMezuak(t);
        sut.close();
        
        for(Mezua mezu: mezuak) {
        	if(mezu.getType() != 1) {
                fail();
        	}
        }
        assertTrue(true);
	}

	 /**
	  * Test honek bidaiari batentzat erreserba bat sortzen du eta ondoren
	  * bidaiariaren mezuak lortzen ditu.
	  * Berifikatzen du bidaiariaren mezu lista ez dagoela hutsa.
	  * Datu basea Mockeatzen da
	  * @author Beñat Ercibengoa Calvo
	  */
    @Test
	public void testGetMezuak3() {
    	r = new Ride(1, from, to, date, places, prezioak, d, k, ibilbide);
    	
		Mockito.when(db.find(Profile.class, userT)).thenReturn(t);
		Mockito.when(db.find(Traveller.class, t.getUser())).thenReturn(t);
		Mockito.when(db.find(Ride.class, 1)).thenReturn(r);
    	
    	sut.open();
   		ErreserbaEskaera ee = new ErreserbaEskaera(date, r, t, 1, from, to);
    	rr = sut.erreserbatu(ee);
    	rr.setId(1);
    	sut.close();
    	
    	sut.open();
        List<Mezua> mezuak = sut.getMezuak(t);
        sut.close();
        assertFalse(mezuak.isEmpty());
	}
	
	@After
	public void tearDown() {
		persistenceMock.close();
	}
	

}
