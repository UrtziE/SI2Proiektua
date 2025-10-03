package mockTests;

import static org.junit.Assert.*;

<<<<<<< HEAD
import java.text.ParseException;
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
import domain.EgoeraRide;
import domain.Geltoki;
import domain.Kotxe;
import domain.Ride;
import exceptions.AtriNullException;
import testOperations.TestDataAccess;

//UrtziMokito
public class GetRidesMockBlackTest {
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
	public void testGetRidesDenakOndoSeatekin() {
		
		List<Ride>expected= new ArrayList<Ride>();
		List<Ride>emaitza= new ArrayList<Ride>();
		Kotxe kotxe=new Kotxe();
		Driver driver= new Driver();
		Ride ride= new Ride(1,from, to, date, places, prezioak, driver, kotxe, ibilbide);
		
		emaitza.add(ride);
		Mockito.when(db.createQuery("SELECT r FROM Ride r", Ride.class))
        .thenReturn(queryKonprobatuEgunak);
		Mockito.when(queryKonprobatuEgunak.getResultList()).thenReturn(expected);
		Mockito.when(db.createQuery("SELECT r FROM Ride r WHERE r.date=?3 AND r.egoera=?4", Ride.class))
        .thenReturn(typedQueryRide);
		Mockito.when(typedQueryRide.getResultList()).thenReturn(emaitza);
		sut.open();
		emaitza = sut.getRides(from, to, date);
		sut.close();
		expected.add(ride);
		assertEquals(expected, emaitza);
	}
	@Test
	public void testGetRidesDenakOndoSeatekinDateToday() {
		date= new Date();
		List<Ride>expected= new ArrayList<Ride>();
		List<Ride>emaitza= new ArrayList<Ride>();
		Kotxe kotxe=new Kotxe();
		Driver driver= new Driver();
		Ride ride= new Ride(1,from, to, date, places, prezioak, driver, kotxe, ibilbide);
		
		emaitza.add(ride);
		Mockito.when(db.createQuery("SELECT r FROM Ride r", Ride.class))
        .thenReturn(queryKonprobatuEgunak);
		Mockito.when(queryKonprobatuEgunak.getResultList()).thenReturn(emaitza);
		Mockito.when(db.find(Ride.class, ride.getRideNumber())).thenReturn(ride);
		
		Mockito.when(db.createQuery("SELECT r FROM Ride r WHERE r.date=?3 AND r.egoera=?4", Ride.class))
        .thenReturn(typedQueryRide);
		Mockito.when(typedQueryRide.getResultList()).thenReturn(expected);
		sut.open();
		if(!ride.getEgoera().equals(EgoeraRide.PASATUA)) {
			sut.close();
			fail("Ez da ondo aktualizatu bidaiaren egoera");
			
		}
		emaitza = sut.getRides(from, to, date);
		sut.close();
		assertEquals(expected, emaitza);
	}
	@Test
	public void testGetRidesDenakOndoSeatekinBeforeToday(){
		try {
			date=f.parse("22/10/2014");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		List<Ride>expected= new ArrayList<Ride>();
		List<Ride>emaitza= new ArrayList<Ride>();
		Kotxe kotxe=new Kotxe();
		Driver driver= new Driver();
		Ride ride= new Ride(1,from, to, date, places, prezioak, driver, kotxe, ibilbide);
		ride.setEgoera(EgoeraRide.PASATUA);
		emaitza.add(ride);
		Mockito.when(db.createQuery("SELECT r FROM Ride r", Ride.class))
        .thenReturn(queryKonprobatuEgunak);
		Mockito.when(queryKonprobatuEgunak.getResultList()).thenReturn(expected);
		Mockito.when(db.createQuery("SELECT r FROM Ride r WHERE r.date=?3 AND r.egoera=?4", Ride.class))
        .thenReturn(typedQueryRide);
		Mockito.when(typedQueryRide.getResultList()).thenReturn(expected);
		sut.open();
		emaitza = sut.getRides(from, to, date);
		sut.close();
		assertEquals(expected, emaitza);
	}
	@Test
	public void testGetRidesSeat0(){	
		List<Ride>expected= new ArrayList<Ride>();
		List<Ride>emaitza= new ArrayList<Ride>();
		Kotxe kotxe=new Kotxe();
		Driver driver= new Driver();
		Ride ride= new Ride(1,from, to, date, places, prezioak, driver, kotxe, ibilbide);
		ride=ezabatuEserlekuak("Lesaka",ride);
		emaitza.add(ride);
		Mockito.when(db.createQuery("SELECT r FROM Ride r", Ride.class))
        .thenReturn(queryKonprobatuEgunak);
		Mockito.when(queryKonprobatuEgunak.getResultList()).thenReturn(expected);
		Mockito.when(db.createQuery("SELECT r FROM Ride r WHERE r.date=?3 AND r.egoera=?4", Ride.class))
        .thenReturn(typedQueryRide);
		Mockito.when(typedQueryRide.getResultList()).thenReturn(emaitza);
		sut.open();
		emaitza = sut.getRides(from, to, date);
		sut.close();
		assertEquals(expected, emaitza);
	}
	@Test
	public void testGetRidesFromNull() {	
		List<Ride>expected= new ArrayList<Ride>();
		List<Ride>emaitza= new ArrayList<Ride>();
		Kotxe kotxe=new Kotxe();
		Driver driver= new Driver();
		Ride ride= new Ride(1,from, to, date, places, prezioak, driver, kotxe, ibilbide);
		emaitza.add(ride);
		Mockito.when(db.createQuery("SELECT r FROM Ride r", Ride.class))
        .thenReturn(queryKonprobatuEgunak);
		Mockito.when(queryKonprobatuEgunak.getResultList()).thenReturn(expected);
		Mockito.when(db.createQuery("SELECT r FROM Ride r WHERE r.date=?3 AND r.egoera=?4", Ride.class))
        .thenReturn(typedQueryRide);
		Mockito.when(typedQueryRide.getResultList()).thenReturn(emaitza);
		sut.open();
		assertThrows(AtriNullException.class,()->{sut.getRides(null, to, date);});
		sut.close();
	}
	@Test
	public void testGetRidesToNull() {	
		List<Ride>expected= new ArrayList<Ride>();
		List<Ride>emaitza= new ArrayList<Ride>();
		Kotxe kotxe=new Kotxe();
		Driver driver= new Driver();
		Ride ride= new Ride(1,from, to, date, places, prezioak, driver, kotxe, ibilbide);
		emaitza.add(ride);
		Mockito.when(db.createQuery("SELECT r FROM Ride r", Ride.class))
        .thenReturn(queryKonprobatuEgunak);
		Mockito.when(queryKonprobatuEgunak.getResultList()).thenReturn(expected);
		Mockito.when(db.createQuery("SELECT r FROM Ride r WHERE r.date=?3 AND r.egoera=?4", Ride.class))
        .thenReturn(typedQueryRide);
		Mockito.when(typedQueryRide.getResultList()).thenReturn(emaitza);
		sut.open();
		assertThrows(AtriNullException.class,()->{sut.getRides(from, null, date);});
		sut.close();
	}
	@Test
	public void testGetRidesDateNull() {	
		List<Ride>expected= new ArrayList<Ride>();
		List<Ride>emaitza= new ArrayList<Ride>();
		Kotxe kotxe=new Kotxe();
		Driver driver= new Driver();
		Ride ride= new Ride(1,from, to, date, places, prezioak, driver, kotxe, ibilbide);
		emaitza.add(ride);
		Mockito.when(db.createQuery("SELECT r FROM Ride r", Ride.class))
        .thenReturn(queryKonprobatuEgunak);
		Mockito.when(queryKonprobatuEgunak.getResultList()).thenReturn(expected);
		Mockito.when(db.createQuery("SELECT r FROM Ride r WHERE r.date=?3 AND r.egoera=?4", Ride.class))
        .thenReturn(typedQueryRide);
		Mockito.when(typedQueryRide.getResultList()).thenReturn(emaitza);
		sut.open();
		assertThrows(AtriNullException.class,()->{sut.getRides(from, to, null);});
		sut.close();
	}
	@Test
	public void testGetRidesFromNotExistsDB() {	
		List<Ride>expected= new ArrayList<Ride>();
		List<Ride>emaitza= new ArrayList<Ride>();
		Kotxe kotxe=new Kotxe();
		Driver driver= new Driver();
		Ride ride= new Ride(1,from, to, date, places, prezioak, driver, kotxe, ibilbide);
		emaitza.add(ride);
		Mockito.when(db.createQuery("SELECT r FROM Ride r", Ride.class))
        .thenReturn(queryKonprobatuEgunak);
		Mockito.when(queryKonprobatuEgunak.getResultList()).thenReturn(expected);
		Mockito.when(db.createQuery("SELECT r FROM Ride r WHERE r.date=?3 AND r.egoera=?4", Ride.class))
        .thenReturn(typedQueryRide);
		Mockito.when(typedQueryRide.getResultList()).thenReturn(emaitza);
		from="134435";
		sut.open();
		emaitza = sut.getRides(from, to, date);
		sut.close();
		assertEquals(expected, emaitza);
	}
	@Test
	public void testGetRidesToNotExistsDB() {	
		List<Ride>expected= new ArrayList<Ride>();
		List<Ride>emaitza= new ArrayList<Ride>();
		Kotxe kotxe=new Kotxe();
		Driver driver= new Driver();
		Ride ride= new Ride(1,from, to, date, places, prezioak, driver, kotxe, ibilbide);
		emaitza.add(ride);
		Mockito.when(db.createQuery("SELECT r FROM Ride r", Ride.class))
        .thenReturn(queryKonprobatuEgunak);
		Mockito.when(queryKonprobatuEgunak.getResultList()).thenReturn(expected);
		Mockito.when(db.createQuery("SELECT r FROM Ride r WHERE r.date=?3 AND r.egoera=?4", Ride.class))
        .thenReturn(typedQueryRide);
		Mockito.when(typedQueryRide.getResultList()).thenReturn(emaitza);
		to="134435";
		sut.open();
		emaitza = sut.getRides(from, to, date);
		sut.close();
		assertEquals(expected, emaitza);
	}
	@Test
	public void testGetRidesDateNotExistsDB() {	
		List<Ride>expected= new ArrayList<Ride>();
		List<Ride>emaitza= new ArrayList<Ride>();
		Mockito.when(db.createQuery("SELECT r FROM Ride r", Ride.class))
        .thenReturn(queryKonprobatuEgunak);
		Mockito.when(queryKonprobatuEgunak.getResultList()).thenReturn(expected);
		Mockito.when(db.createQuery("SELECT r FROM Ride r WHERE r.date=?3 AND r.egoera=?4", Ride.class))
        .thenReturn(typedQueryRide);
		Mockito.when(typedQueryRide.getResultList()).thenReturn(expected);
		sut.open();
		emaitza = sut.getRides(from, to, date);
		sut.close();
		assertEquals(expected, emaitza);
	}
		
	@After
	public void tearDown() {
	persistenceMock.close();
	 }
	private Ride ezabatuEserlekuak(String nongoa,Ride ride) {
		List<Geltoki>geltokiList=ride.getGeltokiList();
		int i=0;
		boolean eginda=false;
		while(i<geltokiList.size()&&!eginda) {
			Geltoki geltoki= geltokiList.get(i);
			if(geltoki.getTokiIzen().equals(nongoa)) {
				geltoki.kenduSeatKop(geltoki.getEserleku());
				eginda=true;
			}
			i++;
		}
		return ride;
=======
import org.junit.Test;

public class GetRidesMockBlackTest {

	@Test
	public void testGetRides() {
		fail("Not yet implemented");
>>>>>>> branch 'main' of https://github.com/UrtziE/SI2Proiektua
	}

}
