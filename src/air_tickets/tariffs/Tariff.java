package air_tickets.tariffs;

import air_tickets.FlightRecord;
import air_tickets.SeatClass;
import air_tickets.Ticket;

/**
 * Created by Anton on 10.07.2017.
 */
public interface Tariff {

    long calculateBookingPrice(FlightRecord flightRecord, SeatClass seatClass);
    long calculateUnBookingRefund(Ticket ticket);
    long calculatePriceForBooked(Ticket ticket);
    long calculateFullPrice(FlightRecord flightRecord, SeatClass seatClass);
}
