package air_tickets;

import air_tickets.globals.Schedule;
import air_tickets.globals.Users;
import air_tickets.tariffs.Tariff;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
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

    public Ticket(String flightRecordId, Passenger passenger, SeatClass seatClass,
                  boolean isBuyingDirectly, long price) {
        this.flightRecordId = flightRecordId;
        this.passenger = passenger;
        this.seatClass = seatClass;
        this.price = price;
        this.bookingState = isBuyingDirectly ? State.BOUGHT : State.BOOKED;
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

    public long getPrice() {
        return price;
    }

    public State getBookingState() {
        return bookingState;
    }

    public LocalDateTime getBookedAt() {
        return bookedAt;
    }

    public long getUnBookingRefund() {
        Tariff tariff = Users.getInstance().getCurrentUser().getTariff();
        return tariff.calculateUnBookingRefund(this);
    }

    public long getPriceForBooked() {
        Tariff tariff = Users.getInstance().getCurrentUser().getTariff();
        return tariff.calculatePriceForBooked(this);
    }

    void setBookingState(State bookingState) {
        this.bookingState = bookingState;
    }

    String getShortInfo() {
        return "Flight Number: " + getFlightRecord().getFlight().getFlightNumber() + "; date: " + getFlightRecord().getDate() +
                ";\nOrigin: " + getFlightRecord().getFlight().getOriginIata() +
                "Destination: " + getFlightRecord().getFlight().getDestinationIata() +
                ";\nPassenger: " + passenger.getFullName().toUpperCase();
    }

    public boolean isExpired() {
        return getFlightRecord().isExpired();
    }

    public String toString () {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
        StringBuilder result = new StringBuilder("Ticket info: ");
        result.append("\n**************************************************************************\n");
        result.append(getFlightRecord().getTicketInfo());
        result.append("--------------------------------------------------------------------------\n");
        result.append(passenger.toString());
        result.append("Booking info: ");
        result.append("\n--------------------------------------------------------------------------\n");
        result.append("Class: " + seatClass + "\n");
        result.append("Booked at: " + bookedAt.format(formatter) + ", Booking status: " + bookingState.toString() + "\n");
        result.append("Full price: $" + price + "\n");
        result.append("Full price if already booked: $" + getPriceForBooked() + "\n");
        result.append("Unbooking refund: $" + getUnBookingRefund() + "\n");
        result.append("Expired: " + (getFlightRecord().isExpired() ? "Yes" : "No"));
        result.append("\n**************************************************************************\n");
        return result.toString();
    }
}
