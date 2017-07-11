package air_tickets.system;

import air_tickets.*;

import java.util.*;

/**
 * Created by Anton on 10.07.2017.
 */
public class BookingSystem {

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
            throw new RuntimeException("Not enough available seats");

        for (Passenger passenger : passengerSeats.keySet())
            bookedTickets.add(new Ticket(flightRecord, passenger, passengerSeats.get(passenger),
                    isBoughtDirectly ? State.BOUGHT : State.BOOKED));

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
        return flightRecord.isEnouhgSeats(requiredSeats);
    }
}
