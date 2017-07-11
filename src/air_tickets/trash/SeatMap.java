package air_tickets.trash;

import air_tickets.SeatClass;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Anton on 11.07.2017.
 */
public class SeatMap {

    private Map<SeatClass, Integer> map = new HashMap<>();

    public SeatMap(int... seatsCount) {
        int i = 0;
        for (SeatClass seatClass : SeatClass.values())
            if (i < seatsCount.length)
                map.put(seatClass, seatsCount[i++]);
            else
                break;
    }

    public void setCount(SeatClass seatClass, int seatCount) {
        map.put(seatClass, seatCount);
    }

    public int getCount(SeatClass seatClass) {
        return map.get(seatClass);
    }

    public Map<SeatClass, Integer> getMap() {
        return map;
    }

    public boolean isEnoughSeats(SeatMap requiredSeats) {
        for (SeatClass requiredClass : requiredSeats.getMap().keySet())
            if (map.keySet().contains(requiredClass)) {
                if (map.get(requiredClass) < requiredSeats.getMap().get(requiredClass))
                    return false;
            }
            else
                return false;
        return true;
    }
}
