package air_tickets.globals;

import air_tickets.Flight;
import air_tickets.FlightRecord;
import air_tickets.SeatClass;
import air_tickets.exceptions.DataIntegrityException;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.*;

/**
 * Created by Anton on 09.07.2017.
 */
public class Schedule implements Serializable {

    private static Schedule instance;
    private final Map<String, FlightRecord> records = new HashMap<>();

    private Schedule() {}

    public static Schedule getInstance() {
        if (instance == null)
            instance = new Schedule();
        return instance;
    }

    public boolean setAsInstance() {
        if (instance != null) {
            return false;
        }
        else {
            if (!checkIatasInRecords())
                throw new DataIntegrityException("Scheduled flights use some airports, that not present in World");
            instance = this;
            return true;
        }
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
        List<FlightRecord> list = new ArrayList<>();
        list.addAll(records.values());
        return list;
    }

    public FlightRecord getFlightRecordById(String flightRecordId) {
        for (FlightRecord flightRecord : records.values())
            if (flightRecord.getId().equals(flightRecordId))
                return flightRecord;
        return null;
    }

    public List<FlightRecord> searchForFlight(List<String> originIatas, List<String> destinationIatas,
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

    /*private Object readResolve() {
        if (setAsInstance())
            System.err.println("Schedule successfully loaded from the file");
        else
            System.err.println("Schedule instance already exists, nothing to load");
        return instance;
    }*/

    private boolean checkIatasInRecords() {
        World world = World.getInstance();
        for(FlightRecord flightRecord : getFlightRecords())
            if (world.getAirportByIata(flightRecord.getFlight().getOriginIata()) == null ||
                    world.getAirportByIata(flightRecord.getFlight().getDestinationIata()) == null)
                return false;
        return true;
    }
}
