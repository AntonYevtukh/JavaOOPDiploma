package air_tickets.trash;

import air_tickets.*;

import java.util.*;

/**
 * Created by Anton on 10.07.2017.
 */
/*public class BookingSystem {

    public static List<Ticket> bookTickets(User user, FlightRecord flightRecord,
                                           Map<Passenger, SeatClass> passengerSeats, boolean isBoughtDirectly) {

        List<Ticket> bookedTickets = new ArrayList<>();
        long fullPrice = 0;

        if (!isBoughtDirectly)
            for(SeatClass seatClass : passengerSeats.values())
                fullPrice += user.getTariff().calculateBookingPrice(flightRecord, seatClass);
        else
            for(SeatClass seatClass : passengerSeats.values())
                fullPrice += user.getTariff().calculateFullPrice(flightRecord, seatClass);

        if (user.getBalance() < fullPrice)
            throw new RuntimeException("Not enough amount on the user balance!");

        if (!checkSeatsForPassengers(flightRecord, passengerSeats))
            throw new RuntimeException("Not enough available seats is available on flight!");

        for (Passenger passenger : passengerSeats.keySet()) {
            long price = !isBoughtDirectly ?
                    user.getTariff().calculateBookingPrice(flightRecord, passengerSeats.get(passenger)) :
                    user.getTariff().calculateFullPrice(flightRecord, passengerSeats.get(passenger));
            bookedTickets.add(new Ticket(flightRecord.getId(), passenger, passengerSeats.get(passenger),
                    isBoughtDirectly, price));
        }

        user.getAccount().withdraw(fullPrice);
        return bookedTickets;
    }

    private static boolean checkSeatsForPassengers(FlightRecord flightRecord, Map<Passenger, SeatClass> passengerSeats) {
        Map<SeatClass, Integer> requiredSeats = new HashMap<>();
        for (SeatClass seatClass : passengerSeats.values()) {
            if (!requiredSeats.containsKey(seatClass))
                requiredSeats.put(seatClass, 1);
            else
                requiredSeats.put(seatClass, requiredSeats.get(seatClass) + 1);
        }
        return true;
    }
}*/
