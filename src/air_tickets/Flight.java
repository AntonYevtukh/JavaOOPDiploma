package air_tickets;

import air_tickets.globals.World;

import java.time.LocalTime;

/**
 * Created by Anton on 09.07.2017.
 */
public class Flight {

    private String flightNumber;
    private String originIata;
    private String destinationIata;
    private LocalTime departure;
    private LocalTime arrival;
    private Aircraft aircraft;
    private String airline;

    public Flight(String flightNumber, String originIata, String destinationIata,
                  LocalTime departure, LocalTime arrival, Aircraft aircraft, String airline) {

        String errorMessage = checkWaypoints(originIata, destinationIata);
        if (!errorMessage.equals(""))
            throw new IllegalArgumentException(errorMessage);

        this.flightNumber = flightNumber;
        this.originIata = originIata;
        this.destinationIata = destinationIata;
        this.departure = departure;
        this.arrival = arrival;
        this.aircraft = aircraft;
        this.airline = airline;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public String getOriginIata() {
        return originIata;
    }

    public String getDestinationIata() {
        return destinationIata;
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

    private String checkWaypoints(String originIata, String destinationIata) {
        String errorMessage = "";
        if (World.getInstance().getAirportByIata(originIata) == null)
            errorMessage += "IATA code of origin " + originIata + " is not found in DB";
        if (World.getInstance().getAirportByIata(destinationIata) == null)
            errorMessage += "IATA code of destination " + destinationIata + " is not found in DB";
        return errorMessage;
    }
}
