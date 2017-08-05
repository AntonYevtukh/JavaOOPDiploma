package air_tickets;

import air_tickets.globals.Users;
import air_tickets.globals.World;
import air_tickets.in_out.Utils;
import air_tickets.trash.FlightCoupon;

import java.io.Serializable;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * Created by Anton on 09.07.2017.
 */
public class FlightRecord implements Serializable {

    private final String id = UUID.randomUUID().toString();
    private final Flight flight;
    private final LocalDate date;
    private final Map<SeatClass, Long> prices;
    private final Map<SeatClass, Integer> availableSeats = new TreeMap<>(); //чтоб не считать каждый раз

    public FlightRecord(Flight flight, LocalDate date, Map<SeatClass, Long> prices) {
        this.flight = flight;
        this.date = date;
        this.prices = new TreeMap<>(prices);
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
        result.append("Origin: \t\t\t" + flight.getOriginIata() + ", Departure at: \t" + flight.getDeparture() + " UTC \\ ");
        result.append(getAirportZonedTime(flight.getOriginIata(), flight.getDeparture()) + "\n");
        result.append("Destination: \t\t" + flight.getDestinationIata() + ", Arrival at: \t" + flight.getArrival() + " UTC \\ ");
        result.append(getAirportZonedTime(flight.getDestinationIata(), flight.getArrival()) + "\n");
        result.append("Airline: \t\t\t" + flight.getAirline() + "\n");
        result.append("Aircraft: \t\t\t" + flight.getAircraft() + "\n");
        result.append("Expired: \t\t\t" + (isExpired() ? "Yes" : "No") + "\n");
        return result.toString();
    }

    private String getAirportZonedTime(String iata, LocalTime time) {
        Airport airport = World.getInstance().getAirportByIata(iata);
        LocalDateTime dateTime = LocalDateTime.of(date, time);
        ZonedDateTime utcDateTime = ZonedDateTime.of(dateTime, ZoneOffset.UTC);
        Instant instant = Instant.from(utcDateTime);
        ZonedDateTime airportDateTime = ZonedDateTime.ofInstant(instant, airport.getTimezone());
        return airportDateTime.format(Utils.getDateTimeFormatter());
    }
}
