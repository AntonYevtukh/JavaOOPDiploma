package air_tickets;

import air_tickets.trash.FlightCoupon;

import java.time.LocalDate;
import java.util.*;

/**
 * Created by Anton on 09.07.2017.
 */
public class FlightRecord {

    private final String id = UUID.randomUUID().toString();
    private Flight flight;
    private LocalDate date;
    private Map<SeatClass, Long> prices = new HashMap<>();
    private Map<SeatClass, Integer> availableSeats = new HashMap<>(); //чтоб не считать каждый раз

    public FlightRecord(Flight flight, LocalDate date, Map<SeatClass, Long> prices) {
        this.flight = flight;
        this.date = date;
        this.prices = prices;
        for (SeatClass seatClass : flight.getAircraft().getSeats().keySet())
            availableSeats.put(seatClass, flight.getAircraft().getSeats().get(seatClass));
    }

    public String getId() {
        return id;
    }

    public LocalDate getDate() {
        return date;
    }

    public Flight getFlight() {
        return flight;
    }

    public Map<SeatClass, Integer> getAvailableSeats() {
        return availableSeats;
    }

    public Map<SeatClass, Long> getPrices() {
        return prices;
    }

    public String getKey() {                                        //костыль, символизирующий уникальный составной индекс
        return flight.getFlightNumber() + " " + date.toString();    //номер рейса + дата
    }

    public boolean isEnoughSeats(SeatClass seatClass, int requiredCount) {
        if (!availableSeats.containsKey(seatClass))
            throw new IllegalArgumentException("Invalid seat class");
        else
            return availableSeats.get(seatClass) >= requiredCount;
    }

    public void incrementAvailableSeats(SeatClass seatClass) {
        availableSeats.put(seatClass, availableSeats.get(seatClass) + 1);
    }

    public void decrementAvailableSeats(SeatClass seatClass) {
        availableSeats.put(seatClass, availableSeats.get(seatClass) - 1);
    }

}
