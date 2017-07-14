package air_tickets.globals;

import air_tickets.Airport;
import air_tickets.Flight;
import air_tickets.FlightRecord;
import air_tickets.SeatClass;

import java.time.LocalDate;
import java.util.*;

/**
 * Created by Anton on 09.07.2017.
 */
public class Schedule {

    private final static Schedule instance = new Schedule();
    private final Map<String, FlightRecord> records = new HashMap<>();

    private Schedule() {}

    public static Schedule getInstance() {
        return instance;
    }

    public void addFlight(Flight flight, LocalDate date, Map<SeatClass, Long> prices)
            throws IllegalArgumentException {
        FlightRecord tempRecord = new FlightRecord(flight, date, prices);
        if (!records.containsKey(tempRecord.getKey()))
            records.put(tempRecord.getKey(), tempRecord);
        else
            throw new IllegalArgumentException("This flight has been already appointed on this date!");
    }

    public List<FlightRecord> getFlightRecords() {
        return new ArrayList<>(records.values());
    }

    public FlightRecord getFlightRecordById(String flightRecordId) {
        for (FlightRecord flightRecord : records.values())
            if (flightRecord.getId().equals(flightRecordId))
                return flightRecord;
        return null;
    }

    public List<FlightRecord> searchForFlight(List<String> originIatas, List<String > destinationIatas,
                                              LocalDate date, SeatClass seatClass, int seatCount) {
        List<FlightRecord> result = new ArrayList<>();

        for (FlightRecord flightRecord : getFlightRecords())
            if (flightRecord.getDate().equals(date) && originIatas.contains(flightRecord.getFlight().getOriginIata()) &&
                    destinationIatas.contains(flightRecord.getFlight().getDestinationIata()) &&
                    flightRecord.isEnoughSeats(seatClass, seatCount))
                result.add(flightRecord);
        return result;
    }

    public String allFlightString() {
        String separator = "--------------------------------------------------------------------------\n";
        StringJoiner joiner = new StringJoiner("", "All flights in schedule:\n" + separator, "");
        for (FlightRecord record : records.values())
            joiner.add(record.toString());
        return joiner.toString();
    }
}
