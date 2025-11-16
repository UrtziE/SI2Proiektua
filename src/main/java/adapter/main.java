package adapter;

import businessLogic.BLFacade;
import businessLogic.BLFactory;
import configuration.ConfigXML;
import domain.Driver;

public class main {

	public static void main(String[]args)	{
		ConfigXML config=ConfigXML.getInstance();
		BLFacade blFacade;
		try {
			blFacade = new BLFactory().getBusinessLogicFactory(config);
			Driver	d= blFacade.getDriver("Urtzi");
			DriverTable	dt=new	DriverTable(d);
			dt.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}

