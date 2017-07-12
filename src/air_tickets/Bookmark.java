package air_tickets;

import air_tickets.tariffs.Tariff;
import air_tickets.globals.Schedule;

/**
 * Created by Anton on 11.07.2017.
 */
public class Bookmark {

    private String flightRecordId;
    private SeatClass seatClass;
    private int seatCount;

    public Bookmark(String flightRecordId, SeatClass seatClass, int seatCount) {
        this.flightRecordId = flightRecordId;
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

    public long calculateFullPrice(State bookingState, Tariff tariff) {
        return seatCount * tariff.calculateFullPrice(getFlightRecord(),seatClass);
    }
}
