package air_tickets.tariffs;

import air_tickets.FlightRecord;
import air_tickets.SeatClass;
import air_tickets.Ticket;

/**
 * Created by Anton on 14.07.2017.
 */
public class GoldTariff implements Tariff{

    public long calculateBookingPrice(FlightRecord flightRecord, SeatClass seatClass) {
        return (long)(flightRecord.getPrices().get(seatClass) * 0.15);
    }

    public long calculateFullPrice(FlightRecord flightRecord, SeatClass seatClass) {
        return (long) (flightRecord.getPrices().get(seatClass) * 0.95);
    }

    public long calculateUnBookingRefund(Ticket ticket) {
        if (ticket.getFlightRecord().isExpired())
            return 0;
        else
            return (long) (ticket.getPrice() * 0.05);
    }

    public long calculatePriceForBooked(Ticket ticket) {
        return (long) (ticket.getPrice() * 0.8);
    }

    public TariffType getNextTariff(long totallyPaid) {
        if (totallyPaid >= 100000)
            return TariffType.PLATINUM;
        else
            return TariffType.GOLD;
    }

    public long requiredForUpgrade(long totallyPaid) {
        return 100000 - totallyPaid;
    }
}
