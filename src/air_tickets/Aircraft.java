package air_tickets;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Anton on 09.07.2017.
 */
public class Aircraft {

    private String type;
    private Map<SeatClass, Integer> seats = new HashMap<>();

    Aircraft(String type, int... seatsCount) {
        this.type = type;
        if (seatsCount.length != SeatClass.values().length)
            throw new IllegalArgumentException("Mismatch of seats count description");
        else
            for (int i = 0; i < seatsCount.length; i++) {
                seats.put(SeatClass.values()[i], seatsCount[i]);
            }
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Map<SeatClass, Integer> getSeats() {
        return seats;
    }
}
