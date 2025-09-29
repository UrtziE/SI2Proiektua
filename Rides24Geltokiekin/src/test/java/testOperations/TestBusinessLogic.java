package testOperations;
//
import java.util.Date;
import java.util.List;

import configuration.ConfigXML;
import domain.Driver;
import domain.Kotxe;
import domain.Ride;

public class TestBusinessLogic {
	TestDataAccess dbManagerTest;
 	
    
	   public TestBusinessLogic()  {
			
			System.out.println("Creating TestBusinessLogic instance");
			ConfigXML c=ConfigXML.getInstance();
			dbManagerTest=new TestDataAccess(); 
			dbManagerTest.close();
		}
		
		 
		public boolean removeDriver(String driverEmail) {
			dbManagerTest.open();
			boolean b=dbManagerTest.removeDriver(driverEmail);
			dbManagerTest.close();
			return b;

		}
		
		public Driver createDriver(String email, String name) {
			dbManagerTest.open();
			Driver driver=dbManagerTest.createDriver(email, name);
			dbManagerTest.close();
			return driver;

		}
		
		public boolean existDriver(String email) {
			dbManagerTest.open();
			boolean existDriver=dbManagerTest.existDriver(email);
			dbManagerTest.close();
			return existDriver;

		}
		
		public Driver addDriverWithRide(String user, String email,String from, String to, Date date, int nPlaces, /*float price*/ List<Float>price,Kotxe kotxe,List<String>ibilbide) {
			dbManagerTest.open();
			Driver driver=dbManagerTest.addDriverWithRide(user, email,from,  to, date, nPlaces, /*float price*/ price, kotxe,ibilbide);
			dbManagerTest.close();
			return driver;

		}
		/*public boolean existRide(String email, String from, String to, Date date) {
			dbManagerTest.open();
			boolean b=dbManagerTest.existRide(email, from, to, date);
			dbManagerTest.close();
			return b;
		}*/
		public Ride removeRide(int num,String user) {
			dbManagerTest.open();
			Ride r=dbManagerTest.removeRide( num, user);
			dbManagerTest.close();
			return r;
		}
		


}
