package air_tickets.trash;

import air_tickets.SeatClass;
import air_tickets.State;

import java.util.UUID;

/**
 * Created by Anton on 10.07.2017.
 */
public class FlightCoupon {

    private final String id = UUID.randomUUID().toString();
    private final SeatClass seatClass;
    private State state;

    public FlightCoupon(SeatClass seatClass) {
        this.seatClass = seatClass;
    }

    public String getId() {
        return id;
    }

    public SeatClass getSeatClass() {
        return seatClass;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }
}
