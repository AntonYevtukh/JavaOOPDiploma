package air_tickets.tariffs;

import air_tickets.FlightRecord;
import air_tickets.Passenger;
import air_tickets.SeatClass;
import air_tickets.Ticket;

import java.util.List;
import java.util.Map;

/**
 * Created by Anton on 10.07.2017.
 */
public interface Tariff {

    long calculateBookingPrice(FlightRecord flightRecord, SeatClass seatClass);
    long calculateFullPrice(FlightRecord flightRecord, SeatClass seatClass);
    long calculateUnBookingRefund(Ticket ticket);
    long calculatePriceForBooked(Ticket ticket);
    long requiredForUpgrade(long totallyPaid);
    Tariffs getNextTariff(long totallyPaid);
}
