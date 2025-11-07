package businessLogic;

import java.net.URL;

import javax.xml.ws.Service;
import javax.swing.UIManager;
import javax.xml.namespace.QName;

import configuration.ConfigXML;
import dataAccess.DataAccess;

public class BLFactory {

	
	
	public BLFacade getBussinessLogic(ConfigXML c) throws Exception{
		BLFacade appFacadeInterface;
		UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");

		//If locale
		if (c.isBusinessLogicLocal()) {
			DataAccess da= new DataAccess();
			appFacadeInterface = new BLFacadeImplementation(da);
		}else { //If remote
				
			String serviceName= "http://"+c.getBusinessLogicNode() +":"+ c.getBusinessLogicPort()+"/ws/"+c.getBusinessLogicName()+"?wsdl";
			URL url = new URL(serviceName);

	        //1st argument refers to wsdl document above
			//2nd argument is service name, refer to wsdl document above
	        QName qname = new QName("http://businessLogic/", "BLFacadeImplementationService");
	 
	        Service service = Service.create(url, qname);

	         appFacadeInterface = service.getPort(BLFacade.class);
		} 
		
		return appFacadeInterface;
	}
	
}
