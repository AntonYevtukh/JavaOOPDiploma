package air_tickets;

import air_tickets.trash.FlightCoupon;

/**
 * Created by Anton on 10.07.2017.
 */
public class Ticket {

    private FlightRecord flightRecord;
    private Passenger passenger;
    private FlightCoupon flightCoupon;

    public Ticket(FlightRecord flightRecord, Passenger passenger) {
        this.flightRecord = flightRecord;
        this.passenger = passenger;
    }

    public FlightRecord getFlightRecord() {
        return flightRecord;
    }

    public Passenger getPassenger() {
        return passenger;
    }

    String getShortInfo() {
        return "Flight Number: " + flightRecord.getFlight().getFlightNumber() + "; date: " + flightRecord.getDate() +
                ";\nOrigin: " + flightRecord.getFlight().getOrigin().getIata() +
                "Destination: " + flightRecord.getFlight().getDestination().getIata() +
                ";\nPassenger: " + passenger.getFullName().toUpperCase();
    }
}
