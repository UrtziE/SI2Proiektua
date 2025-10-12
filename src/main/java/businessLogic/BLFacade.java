package businessLogic;
import domain.Erreklamazioa;
import domain.Kotxe;
import domain.Mezua;
import domain.Profile;
import domain.Ride;
import domain.*;
import exceptions.RideMustBeLaterThanTodayException;
import exceptions.RideAlreadyExistException;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.persistence.TypedQuery;

/**
 * Interface that specifies the business logic.
 */
@WebService
public interface BLFacade {

	/**
	 * This method returns all the cities where rides depart
	 * 
	 * @return collection of cities
	 */
	@WebMethod
	public List<String> getDepartCities();

	/**
	 * This method returns all the arrival destinations, from all rides that depart
	 * from a given city
	 * 
	 * @param from the depart location of a ride
	 * @return all the arrival destinations
	 */
	@WebMethod
	public List<String> getDestinationCities(String from);

	/**
	 * This method creates a ride for a driver
	 * 
	 * @param from    the origin location of a ride
	 * @param to      the destination location of a ride
	 * @param date    the date of the ride
	 * @param nPlaces available seats
	 * @param driver  to which ride is added
	 * 
	 * @return the created ride, or null, or an exception
	 * @throws RideMustBeLaterThanTodayException if the ride date is before today
	 * @throws RideAlreadyExistException         if the same ride already exists for
	 *                                           the driver
	 */
	@WebMethod
	public Ride createRide(String from, String to, Date date, int nPlaces, /* float price */List<Float> price,
			String driverEmail, Kotxe kotxe, List<String> ibilbide)
			throws RideMustBeLaterThanTodayException, RideAlreadyExistException;

	/**
	 * Metodo honek bueltatzen ditu bi tokietatik pasatzen diren eta eselekuak libre   dituzten bidaiak
	 * 
	  * @param from bidaian lehendabiziko dagoen geltokia 
	  * @param to bidaian “from" eta gero dagoen geltokia 
	 * @param date the date of the ride
	 * @return collection of rides
	 */
	@WebMethod
	public List<Ride> getRides(String from, String to, Date date);

	/**
	 * This method retrieves from the database the dates a month for which there are
	 * events
	 * 
	 *  @param from bidaian lehendabiziko dagoen geltokia 

	 * @param to bidaian “from" eta gero dagoen geltokia 
	 * @param date of the month for which days with rides want to be retrieved
	 * @return collection of rides
	 */
	@WebMethod
	public List<Date> getThisMonthDatesWithRides(String from, String to, Date date);

	/**
	 * This method calls the data access to initialize the database with some events
	 * and questions. It is invoked only when the option "initialize" is declared in
	 * the tag dataBaseOpenMode of resources/config.xml file
	 */
	@WebMethod
	public void initializeBD();

	/**
	 * 
	 * Metodo honek profil berria erregistratzen du datu basean
	 * 
	 * @param email    erregistratutakoaren emaila
	 * @param name     erregistratutakoaren izena
	 * @param surname  erregistratutakoaren abizena
	 * @param user     erregistratutakoaren erabiltzailea
	 * @param password erregistratutakoaren pasahitza
	 * @param telf     erregistratutakoaren telefonoa
	 * @param type     bidaiaria edo gidaria den
	 * 
	 * @return profil berria
	 */
	@WebMethod
	public Profile register(Profile p, String type);
	/**
	 * Metodo honek erabiltzaile bat eta pasahitza bat sartuz, erabiltzaile eta
	 * pasahitza horiek dituen profila itzuliko du
	 * 
	 * @param user     bilatutako erabiltzailea
	 * @param password erabiltzaile horren pasahitza
	 * @return aurkitutako profila
	 */

	@WebMethod
	public Profile login(String user, String password);

	/**
	 * Metodo honek pasatutako erabiltzaileari pasatutako diru kantitatea gehituko
	 * dio bere kontura
	 * 
	 * @param dirua gehituko den diru kantitatea
	 * @param p     diru hori sartuko zaion erabiltzailearen profila
	 * 
	 */

	@WebMethod
	public void gehituDirua(float dirua, Profile p);

	/**
	 * Metodo honek pasatutako erabiltzaileari pasatutako diru kantitatea kenduko
	 * dio bere kontutik
	 * 
	 * @param dirua kenduko den diru kantitatea
	 * @param p     diru hori kenduko zaion erabiltzailearen profila
	 * 
	 */

	@WebMethod
	public void kenduDirua(float dirua, Profile p);

	/**
	 * Metodo honek pasatutako bidaiariari pasatutako diru kantitatea gehituko dio
	 * bere kontura
	 * 
	 * @param dirua gehituko den diru kantitatea
	 * @param p     diru hori sartuko zaion bidaiaria
	 */
	@WebMethod
	public void sartuDirua(float dirua, Traveller traveller);

	/**
	 * Metodo honek pasatutako gidariari pasatutako diru kantitatea aterako dio bere
	 * kontutik
	 * 
	 * @param dirua aterako den diru kantitatea
	 * @param p     diru hori aterako zaion gidaria
	 */
	@WebMethod
	public void ateraDirua(float dirua, Driver driver);

	/**
	 * Metodo honek bidai baten erreserba eskakizuna sortuko du
	 * 
	 * @param r eskatu den erreserbaren eskaera
	 * 
	 * @return erreserba eskakizuna
	 */
	@WebMethod
	public RideRequest erreserbatu(RideRequest r);

	/**
	 * Metodo honek erreserba eskakizun bat onartzen edo deuseztatzen du, behar den   pertsonari dirua itzuliz(beharrezkoa bada)
	 * 
	 * @param request  onartzen edo deuseztatzen den erreserba eskakizuna
	 * @param onartuta eskakizuna onartu baden gordetzen du
	 * 
	 */
	@WebMethod
	public void onartuEdoDeuseztatu(RideRequest request, boolean onartuta);

	/**
	 * Metodo honek gidari baten bidaiak, martxan daudenak eta leku libreak
	 * dituztenak, lista batean bueltatzen ditu RideContainer itzultzen dugu web
	 * zerbitzua dela eta
	 * 
	 * @param d gidaria
	 * @return gidariaren bidaien lista
	 */
	@WebMethod
	public List<RideContainer> getRidesOfDriver(Driver d);

	/**
	 * Metodo honek bidai baten erreserba eskakizun guztiak lista batean bueltatzen
	 * ditu
	 * 
	 * @param ride bidaia
	 * @return bidaiaren erreserba eskakizun guztien lista
	 */
	@WebMethod
	public List<RideRequest> getRidesRequestsOfRide(Ride ride);

	/**
	 * Metodo honek erabiltzaile batek duen diru kantitatea itzultzen du
	 * 
	 * @param p erabiltzaile horren profila
	 * @return bere kontuan duen diru kantitatea
	 */
	@WebMethod
	public float getMoney(Profile p);

	/**
	 * Metodo honek bidaiari baten erreserba eskakizun guztiak lista batean
	 * bueltatzen ditu
	 * 
	 * @param traveller bidaiaria
	 * @return bidaiariaren erreserba eskakizun guztien lista
	 */
	@WebMethod
	public List<RideRequest> getRidesRequestsOfTraveller(Traveller traveller);

	/**
	 * Metodo honek gidari baten bidaia guztiak (Container-ak)lista batean bueltatzen ditu
	 * 
	 * @param d gidaria
	 * @return gidariaren bidaien lista(Container-ak)
	 */
	@WebMethod
	public List<RideContainer> getAllRidesOfDriver(Driver driver);

	/**
	 * Metodo honek erreserba eskakizun baten bidaia itzultzen du
	 * 
	 * @param erreserba erreserba eskakizuna
	 * @return erreserba eskakizunaren bidaia
	 */
	@WebMethod
	public Ride getRideFromRequest(RideRequest erreserba);

	/**
	 * Metodo honek gidari baten kotxe guztiak lista batean bueltatzen ditu
	 * 
	 * @param driver gidaria
	 * @return gidariaren kotxeen lista
	 */
	@WebMethod
	public List<Kotxe> getKotxeGuztiak(Driver driver);

	/**
	 * Metodo honek gidari bati kotxe berri bat gehitzen dio eta ondo gehitu den edo
	 * ez itzultzen du
	 * 
	 * @param marka     kotxearen marka
	 * @param modelo    kotxearen modeloa
	 * @param matrikula kotxearen matrikula
	 * @param tokiKop   kotxearen eserleku kopurua
	 * @param driver    gidaria
	 * @return ondo gehitu den edo ez
	 */
	@WebMethod
	public boolean createCar(String marka, String modelo, String matrikula, int tokiKop, Driver driver);

	/**
	 * Metodo honek erabiltzaile batek dituen mezuen lista itzultzen du
	 * 
	 * @param p erabiltzaile horren profila
	 * @return mezuen lista
	 */
	@WebMethod
	public List<Mezua> getMezuak(Profile p);

	/**
	 * Metodo honek erabiltzaile batek dituen erreklamazio mezuen lista itzultzen du
	 * 
	 * @param p erabiltzaile horren profila
	 * @return erreklamazio mezuen lista
	 */
	@WebMethod
	public List<Mezua> getErreklamazioMezuak(Profile p);

	/**
	 * Metodo honek bidai bat kantzelatzen du
	 * 
	 * @param r kantzelatuko den bidaia
	 * 
	 */
	@WebMethod
	public void kantzelatu(Ride r);

	/**
	 * Metodo honek erreserbatutako bidai bat gauzatu den jarriko du, horretarako, 
	 *  bidaia bereko profile bereko request guztiak eginda edo ez eginda bezala jarriko ditu.Aldaketa egiten den
	 *  erreklamazio bakoitzeko dirua gehituko dio driverra-ri edo traveller-ari, segun bidaia egin dela esan den ala ez
	 * 
	 * @param request  bidaiaren erreserba
	 * @param onartuta bidaia gauzatu bada edo ez
	 * 
	 */
	@WebMethod
	public void egindaEdoEzEgina(RideRequest request, boolean onartuta);

	/**
	 * Metodo honek gidari batek martxan dituen bidaiak itzultzen ditu nahiz eta
	 * tokirik gabe izatea. RideContainer itzultzen dugu web zerbitzuagatik
	 * 
	 * @param d gidaria
	 * @return gidariak martxan dituen bidaien lista
	 */
	@WebMethod
	public List<RideContainer> getEginRidesOfDriver(Driver d);

	/**
	 * Metodo honek erabiltzaile bat baloratzen du bidai bategatik
	 * 
	 * @param balorazioa eman zaion baloraketa
	 * @param nori       baloratuko den erabiltzailea
	 * @param r          baloratu den bidaiaren erreserba
	 * 
	 */
	@WebMethod
	public void baloratu(int balorazioa, Profile nori, RideRequest r);

	/**
	 * Metodo honek gidari baten balorazioen media bueltatzen du
	 * 
	 * @param driver gidaria
	 * @return float balorazioa
	 */
	@WebMethod
	public float getBalorazioMedia(Driver driver);

	/**
	 * Metodo honek alerta bat sortzen du bidai batez abisatzeko
	 * 
	 * @param from irteera hiria
	 * @param to   helmuga hiria
	 * @param when noiz nahi duzun bidaia egitea
	 * @param t    alerta sortu duen bidaiaria
	 * 
	 */
	@WebMethod
	public void sortuAlerta(Traveller t, String from, String to, Date when);

	/**
	 * Metodo honek pasatutako parametroak dituen alerta itzultzen du
	 * 
	 * @param from irteera hiria
	 * @param to   helmuga hiria
	 * @param when bidaiaren data
	 * @return aurkitu den alerta
	 */
	@WebMethod
	public Alerta getAlerta(Traveller traveller, String from, String to, Date when);

	/**
	 * Metodo honek itzultzen ditu erabiltzaile batek egin dituen erreklamazio
	 * guztiak
	 * 
	 * @param p erabiltzailea
	 * @return erreklamazioen lista
	 */
	@WebMethod
	public List<Erreklamazioa> getErreklamazioak(Profile p);

	/**
	 * Metodo honek erreklamazio berri bat sortzen du eta esleitu gabe moduan
	 * gordetzen da datu basean
	 * 
	 * @param p            erreklamatzailea
	 * @param nori         erreklamatua
	 * @param deskripizioa erreklamazioaren arrazoia
	 * @param prezioa      erreklamatzen ari den bidai erreserbaren prezioa
	 * @param r            bidai erreserba
	 * 
	 */
	@WebMethod
	public void gehituErreklamazioa(Profile p, Profile nori, String deskripzioa, float prezioa, RideRequest r);

	/**
	 * Metodo honek administratzaile bateri esleitu gabeko erreklamazio bat
	 * esleitzen dio
	 * 
	 * @param a erreklamazio berria eskatu duen administratzailea
	 * @return esleitu zaion erreklamazioa
	 */
	@WebMethod
	public Erreklamazioa takeNewErreklamazioa(Admin a);

	/**
	 *  Metodo honek itzultzen ditu bidaia hori eginda izan dela esan duten bidaiariak
	 * 
	 * @param ride begiratu nahi den bidai
	 * @return bidaiarien lista
	 */
	@WebMethod
	public List<Traveller> getTravellersOfRideDone(Ride ride);

	/**
	 * Metodo honek itzultzen ditu bidaia hori eginda ez dela izan esan duten bidaiariak
	 *
	 * 
	 * @param ride begiratu nahi den bidai
	 * @return bidaiarien lista
	 */
	@WebMethod
	public List<Traveller> getTravellersOfRideNotDone(Ride ride);

	/**
	 * Metodo honek erreklamazio bat onartu edo deuseztatzen den gordetzen du.
	 * Onartu bada, erreklamatuari dirua kendu eta erreklamatzaileari diru sartuko
	 * dio eta erreklamazio emaitzaren mezuak bidaliko dira Deuseztatu bada,
	 * bakarrik mezuak bidaliko dira
	 * 
	 * @param erreklamazioa aukeratu den erreklamazioa
	 * @param kantitatea    onartu baden erreklamatu duen erabiltzaileari itzuliko
	 *                      zaion diru kantitatea
	 * @param onartuta      onartu edo deuseztatu baden erreklamazioa
	 */
	@WebMethod
	public void erreklamazioaProzesatu(Erreklamazioa erreklamazioa, float kantitatea, boolean onartuta);

	/**
	 * Metodo honek egiten duena da, Pasatutako mezua irakurrita= true moduan jarri
	 * egiten du.
	 * 
	 * @param alerta Alerta mezua
	 * 
	 */
	@WebMethod
	public void mezuaIrakurrita(Mezua alerta);

	/**
	 * Metodo honek itzultzen ditu bidaiari baten ikusitako alertak lista batean
	 * 
	 * @param traveller bidaiaria
	 * @return bidaiariaren ikusitako alerten lista
	 */
	@WebMethod
	public List<Mezua> ikusitakoAlerta(Traveller traveller);

	/**
	 * Metodo honek itzultzen ditu bidaiari baten ikusi gabeko alertak lista batean
	 * 
	 * @param traveller bidaiaria
	 * @return bidaiariaren ikusi gabeko alerten lista
	 */
	@WebMethod
	public List<Mezua> getIkusiGabeAlerta(Traveller traveller);

	/**
	 * Metodo honek itzultzen ditu bidaiari baten alertak lista batean
	 * 
	 * @param traveller bidaiaria
	 * @return bidaiariaren alerten lista
	 */
	@WebMethod
	public List<Alerta> kargatuTravellerAlertak(Traveller traveller);

	/**
	 * Metodo honek alerta bat deuseztatzen du
	 * 
	 * @param alerta deuseztatuko den alerta
	 */
	@WebMethod
	public void deuseztatuAlerta(Alerta alerta);

	/**
	 * Metodo honek erreserba eskaera baten bidaiaria edo gidaria baloratu baden
	 * bueltatzen du
	 * 
	 * @param request erreserba eskaera
	 * @param gidari  gidaria edo bidaiaria begiratu behar baden jakiteko
	 * @return erreklamatuta badagoen
	 */
	@WebMethod
	public boolean isBaloratua(RideRequest request, boolean gidari);

	/**
	 * Metodo honek erreserba eskaera baten bidaiaria edo gidaria erreklamatu baden
	 * bueltatzen du
	 * 
	 * @param request erreserba eskaera
	 * @param gidari  gidaria edo bidaiaria begiratu behar baden jakiteko
	 * @return erreklamatuta badagoen
	 */
	@WebMethod
	public boolean isErreklamatua(RideRequest request, boolean gidari);

	/**
	 * Metodo honek pasatutako bidaiaren pasatutako bidaiariaren lehenengo eskaera
	 * lortzen du
	 * 
	 * @param r bilatu nahi den bidaia
	 * @param t bilatu nahi den bidaiaria
	 * @return bidaiariaren lehenengo erreserba eskaera
	 */
	@WebMethod
	public RideRequest lortuLehenRequestBidaiakoa(Ride r, Traveller t);

	/**
	 * Metodo honek itzuli egiten duena da, Bidaia batean, request-aren ibilbidea
	 * egitean, zenbat eserleku dauden. Hau da RideRequest-aren from-etik to-ra
	 * zenbat eserleku dauden libre
	 * 
	 * @param ride    Bidaia
	 * @param request Bidaia Eskaera
	 * @return eserleku kopurua(Integer)
	 */
	@WebMethod
	public int lortuZenbatEserlekuGeratu(Ride ride, RideRequest request);

	/**
	 * Metodo honek administratzaile batek prozesuan dituen erreklamazioak itzultzen
	 * ditu
	 * 
	 * @param a administratzailea
	 * @return erreklamazioen lista
	 */
	@WebMethod
	public List<Erreklamazioa> lortuErreklamazioakProzesuan(Profile a);
}