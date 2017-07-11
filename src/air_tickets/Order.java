package air_tickets;

import air_tickets.tariffs.Tariff;

import java.util.Map;

/**
 * Created by Anton on 11.07.2017.
 */
public class Order {

    private FlightRecord flightRecord;
    private Map<SeatClass, Integer> seats;

    public Order(FlightRecord flightRecord, Map<SeatClass, Integer> seats) {
        this.flightRecord = flightRecord;
        this.seats = seats;
    }

    public FlightRecord getFlightRecord() {
        return flightRecord;
    }

    public Map<SeatClass, Integer> getSeats() {
        return seats;
    }

    public void setSeats(SeatClass seatClass, int count) {
        seats.put(seatClass, count);
    }

    public long calculateBookingPrice(State bookingState, Tariff tariff) {
        long totalPrice = 0;
        for (SeatClass seatClass : seats.keySet())
            totalPrice += seats.get(seatClass) * tariff.calculateBookingPrice(flightRecord, seatClass);
        return totalPrice;
    }

    public long calculateBuyingPrice(State bookingState, Tariff tariff) {
        long totalPrice = 0;
        for (SeatClass seatClass : seats.keySet())
            totalPrice += seats.get(seatClass) * tariff.calculateFullPrice(flightRecord, seatClass);
        return totalPrice;
    }
}
