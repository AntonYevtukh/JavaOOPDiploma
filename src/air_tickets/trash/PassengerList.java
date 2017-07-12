package air_tickets.trash;

import air_tickets.Passenger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Anton on 12.07.2017.
 */
public class PassengerList {

    private List<Passenger> passengers = new ArrayList<>();

    public void addPassenger(Passenger passenger) {
        if (passengers.contains(passenger))
            throw new IllegalArgumentException("Passenger is already present in the list");
        else
            passengers.add(passenger);
    }

    public List<Passenger> getPassengers() {
        return passengers;
    }

    public int getPassengersCount() {
        return passengers.size();
    }
}
