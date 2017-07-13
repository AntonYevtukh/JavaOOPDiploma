package air_tickets;

import java.time.ZoneId;
import java.time.ZoneOffset;

/**
 * Created by Anton on 09.07.2017.
 */
public class Airport {

    private String name;
    private String iata;
    private ZoneId timezone;

    public Airport(String name, String iata, ZoneId timezone) {
        this.name = name;
        this.iata = iata;
        this.timezone = timezone;
    }

    public String getName() {
        return name;
    }

    public String getIata() {
        return iata;
    }

    public ZoneId getTimezone() {
        return timezone;
    }

    public String toString() {
        return "Name: " + name + ", IATA: " + iata + ", Time Zone: " + timezone.toString() + ".";
    }
}
