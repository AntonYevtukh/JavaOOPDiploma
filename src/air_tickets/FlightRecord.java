package air_tickets;

import air_tickets.globals.Users;
import air_tickets.trash.FlightCoupon;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
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

    public boolean isExpired() {
        LocalDateTime startDateTime = LocalDateTime.of(date, flight.getDeparture());
        return startDateTime.isBefore(LocalDateTime.now());
    }

    public String toString() {
        User user = Users.getInstance().getCurrentUser();
        StringBuilder result = new StringBuilder(this.getTicketInfo());
        result.append("Available seats: \tCount \tBooking price \tFullPrice\n");
        for (SeatClass seatClass : availableSeats.keySet()) {
            result.append(seatClass + ":\t\t\t" + availableSeats.get(seatClass) + "\t\t$");
            result.append(user.getTariff().calculateBookingPrice(this, seatClass) + "\t\t\t\t$");
            result.append(user.getTariff().calculateFullPrice(this, seatClass) + "\n");
        }
        result.append("--------------------------------------------------------------------------\n");
        return result.toString();
    }

    public String getTicketInfo() {
        StringBuilder result = new StringBuilder("Flight info:");
        result.append("\n--------------------------------------------------------------------------\n");
        result.append("Flight number: \t\t" + flight.getFlightNumber() + "\n");
        result.append("Flight date: \t\t" + date + "\n");
        result.append("Origin: \t\t\t" + flight.getOriginIata() + ", Departure at: \t" + flight.getDeparture() + "\n");
        result.append("Destination: \t\t" + flight.getDestinationIata() + ", Arrival at: \t" + flight.getArrival() + "\n");
        result.append("Airline: \t\t\t" + flight.getAirline() + "\n");
        result.append("Aircraft: \t\t\t" + flight.getAircraft() + "\n");
        result.append("Expired: \t\t\t" + (isExpired() ? "Yes" : "No") + "\n");
        return result.toString();
    }
}
