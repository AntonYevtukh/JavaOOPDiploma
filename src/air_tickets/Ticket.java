package air_tickets;

import air_tickets.globals.Schedule;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.UUID;

/**
 * Created by Anton on 10.07.2017.
 */
public class Ticket {

    private String id = UUID.randomUUID().toString();
    private String flightRecordId;
    private Passenger passenger;
    private SeatClass seatClass;
    private State bookingState;
    private long price;
    private LocalDateTime bookedAt;

    public Ticket(String flightRecordId, Passenger passenger, SeatClass seatClass, State bookingState, long price) {
        this.flightRecordId = flightRecordId;
        this.passenger = passenger;
        this.seatClass = seatClass;
        this.price = price;
        this.bookedAt = LocalDateTime.now(ZoneOffset.UTC);
    }

    public FlightRecord getFlightRecord() {
        return Schedule.getInstance().getFlightRecordById(flightRecordId);
    }

    public Passenger getPassenger() {
        return passenger;
    }

    public SeatClass getSeatClass() {
        return seatClass;
    }

    public State getBookingState() {
        return bookingState;
    }

    public void setBookingState(State bookingState) {
        this.bookingState = bookingState;
    }

    String getShortInfo() {
        return "Flight Number: " + getFlightRecord().getFlight().getFlightNumber() + "; date: " + getFlightRecord().getDate() +
                ";\nOrigin: " + getFlightRecord().getFlight().getOriginIata() +
                "Destination: " + getFlightRecord().getFlight().getDestinationIata() +
                ";\nPassenger: " + passenger.getFullName().toUpperCase();
    }

    String getFullInfo() {
        StringBuilder result = new StringBuilder("Ticket info: ");
        result.append("\n**************************************************************************\n");
        result.append(getFlightRecord().toString()).append(passenger.toString());
        result.append("Booking info: ");
        result.append("\n--------------------------------------------------------------------------\n");
        result.append("Booked at: " + bookedAt.toString() + ", Full price: $" + price + ", Booking status: " + bookingState);
        result.append("\n**************************************************************************\n");
        return result.toString();
    }
}
