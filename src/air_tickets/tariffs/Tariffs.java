package air_tickets.tariffs;

import air_tickets.FlightRecord;
import air_tickets.SeatClass;
import air_tickets.Ticket;

import java.io.Serializable;

/**
 * Created by Anton on 25.07.2017.
 */
public enum Tariffs implements Tariff, Serializable {

    STANDARD {

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

        public Tariffs getNextTariff(long totallyPaid) {
            if (totallyPaid >= 100)
                return Tariffs.GOLD;
            else
                return Tariffs.STANDARD;
        }

        public long requiredForUpgrade(long totallyPaid) {
            return 10000 - totallyPaid;
        }
    },

    GOLD {

        public long calculateBookingPrice(FlightRecord flightRecord, SeatClass seatClass) {
            return (long)(flightRecord.getPrices().get(seatClass) * 0.15);
        }

        public long calculateFullPrice(FlightRecord flightRecord, SeatClass seatClass) {
            return (long) (flightRecord.getPrices().get(seatClass) * 0.95);
        }

        public long calculateUnBookingRefund(Ticket ticket) {
            if (ticket.isExpired())
                return 0;
            else
                return (long) (ticket.getPrice() * 0.05);
        }

        public long calculatePriceForBooked(Ticket ticket) {
            return (long) (ticket.getPrice() * 0.8);
        }

        public Tariffs getNextTariff(long totallyPaid) {
            if (totallyPaid >= 100000)
                return Tariffs.PLATINUM;
            else
                return Tariffs.GOLD;
        }

        public long requiredForUpgrade(long totallyPaid) {
            return 100000 - totallyPaid;
        }
    },

    PLATINUM {

        public long calculateBookingPrice(FlightRecord flightRecord, SeatClass seatClass) {
            return (long)(flightRecord.getPrices().get(seatClass) * 0.1);
        }

        public long calculateFullPrice(FlightRecord flightRecord, SeatClass seatClass) {
            return (long) (flightRecord.getPrices().get(seatClass) * 0.9);
        }

        public long calculateUnBookingRefund(Ticket ticket) {
            if (ticket.isExpired())
                return 0;
            else
                return (long) (ticket.getPrice() * 0.1);
        }

        public long calculatePriceForBooked(Ticket ticket) {
            return (long) (ticket.getPrice() * 0.8);
        }

        public Tariffs getNextTariff(long totallyPaid) {
            return Tariffs.PLATINUM;
        }

        public long requiredForUpgrade(long totallyPaid) {
            return 1000000 - totallyPaid;
        }
    }
}
