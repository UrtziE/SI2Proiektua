package domain;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
@XmlAccessorType(XmlAccessType.FIELD) 
public class RideContainer {
private Ride ride;
private List<RideRequest> rideRequestList;
private Driver driver;
public RideContainer(Ride r) {
	this.ride=r;
	this.driver=r.getDriver();
	this.rideRequestList=r.getEskakizunak();
}
public RideContainer() {
	this.ride=null;
	this.driver=null;
	this.rideRequestList=null;
}
public Ride getRide() {
	return ride;
}
public List<RideRequest> getRideRequestList() {
	return rideRequestList;
}
public Driver getDriver() {
	return driver;
}
public String toString() {
	return ride+"/"+rideRequestList+"/"+driver;
}
}
