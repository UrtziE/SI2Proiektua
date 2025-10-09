package domain;

import java.io.Serializable;
import java.util.Date;

public class ErreserbaEskaera implements Serializable {

    private Date time;
    private Ride ride;
    private Traveller traveller;
    private int seats;
    private String requestFrom;
    private String requestTo;

    public ErreserbaEskaera(Date time, Ride ride, Traveller traveller, int seats, String requestFrom, String requestTo) {
        this.time = time;
        this.ride = ride;
        this.traveller = traveller;
        this.seats = seats;
        this.requestFrom = requestFrom;
        this.requestTo = requestTo;
    }

    // Getters
    public Date getTime() { return time; }
    public Ride getRide() { return ride; }
    public Traveller getTraveller() { return traveller; }
    public int getSeats() { return seats; }
    public String getRequestFrom() { return requestFrom; }
    public String getRequestTo() { return requestTo; }
}
