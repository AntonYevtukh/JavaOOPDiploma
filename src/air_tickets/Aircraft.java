package air_tickets;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Anton on 09.07.2017.
 */
public class Aircraft implements Serializable {

    private String type;
    private Map<SeatClass, Integer> seats = new HashMap<>();

    public Aircraft(String type, int... seatsCount) {
        if (seatsCount.length != SeatClass.values().length)
            throw new IllegalArgumentException("Mismatch of seats count description");

        this.type = type;
        for (int i = 0; i < seatsCount.length; i++) {
            seats.put(SeatClass.values()[i], seatsCount[i]);
        }
    }

    public Map<SeatClass, Integer> getSeats() {
        return seats;
    }

    public String toString() {
        return type;
    }
}
