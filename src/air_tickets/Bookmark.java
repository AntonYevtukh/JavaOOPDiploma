package air_tickets;

import air_tickets.globals.Users;
import air_tickets.tariffs.Tariff;
import air_tickets.globals.Schedule;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

/**
 * Created by Anton on 11.07.2017.
 */
public class Bookmark {

    private String flightRecordId;
    private SeatClass seatClass;
    private int seatCount;

    public Bookmark(FlightRecord flightRecord, SeatClass seatClass, int seatCount) {
        this.flightRecordId = flightRecord.getId();
        this.seatClass = seatClass;
        this.seatCount = seatCount;
    }

    public FlightRecord getFlightRecord() {
        return Schedule.getInstance().getFlightRecordById(flightRecordId);
    }

    public String getFlightRecordId() {
        return flightRecordId;
    }

    public SeatClass getSeatClass() {
        return seatClass;
    }

    public int getSeatCount() {
        return seatCount;
    }

    public void setSeatClass(SeatClass seatClass) {
        this.seatClass = seatClass;
    }

    public void setSeatCount(int seatCount) {
        this.seatCount = seatCount;
    }

    public long calculateBookingPrice(Tariff tariff) {
        return seatCount * tariff.calculateBookingPrice(getFlightRecord(),seatClass);
    }

    public long calculateFullPrice(Tariff tariff) {
        return seatCount * tariff.calculateFullPrice(getFlightRecord(),seatClass);
    }

    public List<Ticket> createTicketsFromBookmark(List<Passenger> passengers, boolean isBuyingDirectly)
            throws Exception {
        User currentUser = Users.getInstance().getCurrentUser();
        return currentUser.bookTickets(flightRecordId, seatClass, passengers, isBuyingDirectly);
    }

    public String toString() {
        User currentUser = Users.getInstance().getCurrentUser();
        StringJoiner joiner = new StringJoiner("\n", "Bookmark info:\n", "\n");
        joiner.add("Flight: \n");
        joiner.add(getFlightRecord().toString());
        joiner.add("Class: " + seatClass);
        joiner.add("Seat count: " + seatCount);
        joiner.add("Full price: " + calculateFullPrice(currentUser.getTariff()));
        joiner.add("Booking price: " + calculateBookingPrice(currentUser.getTariff()));
        joiner.add("\n--------------------------------------------------------------------------\n");
        return joiner.toString();
    }
}
