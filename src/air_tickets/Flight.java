package air_tickets;

import java.time.LocalTime;

/**
 * Created by Anton on 09.07.2017.
 */
public class Flight {

    private String flightNumber;
    private Airport origin;
    private Airport destination;
    private LocalTime departure;
    private LocalTime arrival;
    private Aircraft aircraft;
    private String airline;

    public Flight(String flightNumber, Airport origin, Airport destination,
                  LocalTime departure, LocalTime arrival, Aircraft aircraft, String airline) {
        this.flightNumber = flightNumber;
        this.origin = origin;
        this.destination = destination;
        this.departure = departure;
        this.arrival = arrival;
        this.aircraft = aircraft;
        this.airline = airline;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public Airport getOrigin() {
        return origin;
    }

    public Airport getDestination() {
        return destination;
    }

    public LocalTime getDeparture() {
        return departure;
    }

    public LocalTime getArrival() {
        return arrival;
    }

    public Aircraft getAircraft() {
        return aircraft;
    }

    public String getAirline() {
        return airline;
    }
}
