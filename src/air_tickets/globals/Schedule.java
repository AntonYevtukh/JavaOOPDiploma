package air_tickets.globals;

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
}
