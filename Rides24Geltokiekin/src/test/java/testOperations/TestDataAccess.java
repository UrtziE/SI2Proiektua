package testOperations;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import configuration.ConfigXML;
import domain.Driver;
import domain.Kotxe;
import domain.Ride;
import domain.Traveller;


public class TestDataAccess {
	protected  EntityManager  db;
	protected  EntityManagerFactory emf;

	ConfigXML  c=ConfigXML.getInstance();


	public TestDataAccess()  {
		
		System.out.println("TestDataAccess created");

		//open();
		
	}

	
	public void open(){
		

		String fileName=c.getDbFilename();
		
		if (c.isDatabaseLocal()) {
			  emf = Persistence.createEntityManagerFactory("objectdb:"+fileName);
			  db = emf.createEntityManager();
		} else {
			Map<String, String> properties = new HashMap<String, String>();
			  properties.put("javax.persistence.jdbc.user", c.getUser());
			  properties.put("javax.persistence.jdbc.password", c.getPassword());

			  emf = Persistence.createEntityManagerFactory("objectdb://"+c.getDatabaseNode()+":"+c.getDatabasePort()+"/"+fileName, properties);

			  db = emf.createEntityManager();
    	   }
		System.out.println("TestDataAccess opened");

		
	}
	public void close(){
		db.close();
		System.out.println("TestDataAccess closed");
	}
	
	
	public Traveller createTraveller(String user, String email) {
		System.out.println(">> TestDataAccess: addDriver");
		Traveller traveller=null;
			db.getTransaction().begin();
			try {
			    traveller= new Traveller(user,email);
				db.persist(traveller);
				db.getTransaction().commit();
			}
			catch (Exception e){
				e.printStackTrace();
			}
			return traveller;
    }
	
	public boolean removeTraveller(String user) {
		System.out.println(">> TestDataAccess: removeTraveller");
		Traveller t = db.find(Traveller.class, user);
		if (t!=null) {
			db.getTransaction().begin();
			db.remove(t);
			db.getTransaction().commit();
			return true;
		} else 
		return false;
    }

	

	public boolean removeDriver(String user) {
		System.out.println(">> TestDataAccess: removeDriver");
		Driver d = db.find(Driver.class, user);
		if (d!=null) {
			db.getTransaction().begin();
			db.remove(d);
			db.getTransaction().commit();
			return true;
		} else 
		return false;
    }
	public Driver createDriver(String user, String email) {
		System.out.println(">> TestDataAccess: addDriver");
		Driver driver=null;
			db.getTransaction().begin();
			try {
			    driver=new Driver(user,email);
				db.persist(driver);
				db.getTransaction().commit();
			}
			catch (Exception e){
				e.printStackTrace();
			}
			return driver;
    }
	public Driver existDriver(String user) {
		 return  db.find(Driver.class, user);
		 

	}
		
		public Driver addDriverWithRide(String user, String email,String from, String to, Date date, int nPlaces, /*float price*/ List<Float>price,Kotxe kotxe,List<String>ibilbide) {
			System.out.println(">> TestDataAccess: addDriverWithRide");
				Driver driver=null;
				db.getTransaction().begin();
				try {
					 driver = db.find(Driver.class, user);
					if (driver==null)
						driver=new Driver(user,email);
				   driver.addRide(from,  to,  date, nPlaces, /*float price*/ price, kotxe,ibilbide);
					db.getTransaction().commit();
					return driver;
					
				}
				catch (Exception e){
					e.printStackTrace();
				}
				return null;
	    }
		
		
		/*public boolean existRide(String email, String from, String to, Date date) {
			System.out.println(">> TestDataAccess: existRide");
			Driver d = db.find(Driver.class, email);
			if (d!=null) {
				return true;//d.doesRideExists(from, to, date);
			} else 
			return false;
		}*/
		public boolean removeRide(int num) {
			System.out.println(">> TestDataAccess: removeRide");
			Ride r = db.find(Ride.class, num);
			if (r!=null) {
				db.getTransaction().begin();
				db.remove(r);
				db.getTransaction().commit();
				return true;

			} else 
			return false;


		}
		public boolean removeCar(String matrikula) {
			System.out.println(">> TestDataAccess: removeCar");
		
			Kotxe k= db.find(Kotxe.class, matrikula);
			if (k!=null) {
				db.getTransaction().begin();
				db.remove(k);
				db.getTransaction().commit();
				return true;

			} else 
			return false;

		}
		public Kotxe getCar(String matrikula) {
			return db.find(Kotxe.class, matrikula);
		}


		
}


