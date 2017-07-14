package air_tickets.tariffs;

import air_tickets.FlightRecord;
import air_tickets.SeatClass;
import air_tickets.Ticket;

/**
 * Created by Anton on 13.07.2017.
 */
public class StandardTariff implements Tariff {

    public long calculateBookingPrice(FlightRecord flightRecord, SeatClass seatClass) {
        return flightRecord.getPrices().get(seatClass) / 5;
    }

    public long calculateFullPrice(FlightRecord flightRecord, SeatClass seatClass) {
        return flightRecord.getPrices().get(seatClass);
    }

    public long calculateUnBookingRefund(Ticket ticket) {
        return 0;
    }

    public long calculatePriceForBooked(Ticket ticket) {
        return ticket.getPrice() * 9 / 10;
    }

    public TariffType getNextTariff(long totallyPaid) {
        if (totallyPaid >= 100)
            return TariffType.GOLD;
        else
            return TariffType.STANDARD;
    }

    public long requiredForUpgrade(long totallyPaid) {
        return 10000 - totallyPaid;
    }
}
