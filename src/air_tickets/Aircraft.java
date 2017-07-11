package air_tickets;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Anton on 09.07.2017.
 */
public class Aircraft {

    private String type;
    private Map<SeatClass, Integer> seats = new HashMap<>();

    Aircraft(String type, int economySeats, int businessSeats, int firstSeats) {
        this.type = type;
        seats.put(SeatClass.ECONOMY, economySeats);
        seats.put(SeatClass.BUSINESS, businessSeats);
        seats.put(SeatClass.FIRST, firstSeats);
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
