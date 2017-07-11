package air_tickets;

import air_tickets.trash.FlightCoupon;

/**
 * Created by Anton on 10.07.2017.
 */
public class Ticket {

    private FlightRecord flightRecord;
    private Passenger passenger;
    private SeatClass seatClass;
    private State bookingState;

    public Ticket(FlightRecord flightRecord, Passenger passenger, SeatClass seatClass, State bookingState) {
        this.flightRecord = flightRecord;
        this.passenger = passenger;
        this.seatClass = seatClass;
    }

    public FlightRecord getFlightRecord() {
        return flightRecord;
    }

    public Passenger getPassenger() {
        return passenger;
    }

    public SeatClass getSeatClass() {
        return seatClass;
    }

    String getShortInfo() {
        return "Flight Number: " + flightRecord.getFlight().getFlightNumber() + "; date: " + flightRecord.getDate() +
                ";\nOrigin: " + flightRecord.getFlight().getOrigin().getIata() +
                "Destination: " + flightRecord.getFlight().getDestination().getIata() +
                ";\nPassenger: " + passenger.getFullName().toUpperCase();
    }
}
