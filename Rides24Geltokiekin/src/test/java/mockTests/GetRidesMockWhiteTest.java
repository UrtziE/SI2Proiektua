package mockTests;

import static org.junit.Assert.*;

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
import domain.Kotxe;
import domain.Ride;
import testOperations.TestDataAccess;

//UrtziMokito
public class GetRidesMockWhiteTest {
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
	/**
	 * Existitzen ez den bidaia bat lortzen saiatzen da.
	 * Konprobatu egiten da lista hutsa itzultzen duela getRides metodoak
	 * DB-a mockeatu egiten da(Entity manager), eta getRides-ek eta "konprobatuBidaienEgunak" metodoak createQuery egiten dutenean zer itzuli esaten da
	 * @author Urtzi Etxegarai Taberna
	 */
	@Test
	public void testDBEzDagoBidaiHori() {
		
		List<Ride>expected= new ArrayList<Ride>();
		Mockito.when(db.createQuery("SELECT r FROM Ride r", Ride.class))
        .thenReturn(queryKonprobatuEgunak);
		Mockito.when(queryKonprobatuEgunak.getResultList()).thenReturn(expected);
		Mockito.when(db.createQuery("SELECT r FROM Ride r WHERE r.date=?3 AND r.egoera=?4", Ride.class))
        .thenReturn(typedQueryRide);
		Mockito.when(typedQueryRide.getResultList()).thenReturn(expected);
		sut.open();
		List<Ride> emaitza = sut.getRides(from, to, date);
		sut.close();
		System.out.println(expected);
		assertEquals(expected, emaitza);
	}
	/**
	 * "To" atributua duen bidaia bat lortzen saiatzen da, hau, ez da exisitzen
	 * Ibilbide barruan to ez dagoenez konprobatu egiten da lista hutsa itzultzen duela getRides metodoak
	 * DB-a mockeatu egiten da(Entity manager), eta getRides-ek eta "konprobatuBidaienEgunak" metodoak createQuery egiten dutenean zer itzuli esaten da
	 * @author Urtzi Etxegarai Taberna
	 */
	@Test
	public void toNotDB()  {
		prezioak = Arrays.asList(4.0f, 4.0f);
		ibilbide = Arrays.asList("Bera", "Irun");
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
		to="Lesaka";
		sut.open();
		emaitza = sut.getRides(from, to, date);
		sut.close();
		System.out.println(expected);
		System.out.println(emaitza);
		assertEquals(expected, emaitza);
	}
	/**
	 * Testeatu egiten du eskatu egiten badiogu from bat ez dagoena DB ezta bidaia baten ibilbidean
	 * Ibilbide barruan from ez dagoenez konprobatu egiten da lista hutsa itzultzen duela getRides metodoak
	 * DB-a mockeatu egiten da(Entity manager), eta getRides-ek eta "konprobatuBidaienEgunak" metodoak createQuery egiten dutenean zer itzuli esaten da
	 * @author Urtzi Etxegarai Taberna
	 */
	@Test
	public void fromNotDB() {
		prezioak = Arrays.asList(4.0f, 4.0f);
		ibilbide = Arrays.asList("Lesaka", "Irun");
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
		System.out.println(expected);
		System.out.println(emaitza);
		assertEquals(expected, emaitza);
		
	}
	/**
	 * Test honek bidaia bat sortueta bidaia honen bilaketa egiten du
	 * Konprobatu egiten du guk DB-an sortutako bidaia lista batean itzultzen duela getRides metodoak
	 * DB-a mockeatu egiten da(Entity manager), eta getRides-ek eta "konprobatuBidaienEgunak" metodoak createQuery egiten dutenean zer itzuli esaten da
	 * @author Urtzi Etxegarai Taberna
	 */ 
	@Test
	public void getRideRidekin() {
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
		System.out.println(expected);
		System.out.println(emaitza);
		assertEquals(expected, emaitza);
	}
	
	@After
	public void tearDown() {
	persistenceMock.close();
	 }

}
