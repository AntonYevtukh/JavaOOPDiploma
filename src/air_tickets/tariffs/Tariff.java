package air_tickets.tariffs;

import air_tickets.FlightRecord;

/**
 * Created by Anton on 10.07.2017.
 */
public interface Tariff {

    long getBookingPrice(FlightRecord flightRecord);
    long getUnBookingRefund(FlightRecord flightRecord);
    long getFullPrice(FlightRecord flightRecord);
}
