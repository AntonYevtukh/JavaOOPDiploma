package air_tickets.system;

import air_tickets.FlightRecord;
import air_tickets.Passenger;
import air_tickets.SeatClass;
import air_tickets.Ticket;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Anton on 10.07.2017.
 */
public class BookingSystem {

    public static List<Ticket> bookTickets(FlightRecord flightRecord, Map<SeatClass, Integer> seats, List<Passenger> passengers) {

        List<Ticket> bookedTickets = new ArrayList<>();

        //TODO check tickets availability
        int totalSeats = 0;

        for (SeatClass seatClass : seats.keySet())
            for (int i = 0; i < seats.get(seatClass); i++) {
                totalSeats++;
            }

        if (totalSeats != passengers.size())
            throw new IllegalArgumentException("Mismatch of seats and passengers count");

        int passengerCounter = 0;

        for(SeatClass seatClass : seats.keySet())
            for (int i = 0; i < seats.get(seatClass); i++) {
                bookedTickets.add(new Ticket(flightRecord, passengers.get(passengerCounter)));
            }

        for (SeatClass seatClass : seats.keySet())
            flightRecord.getAvailableSeats()

        return bookedTickets;
    }
}
