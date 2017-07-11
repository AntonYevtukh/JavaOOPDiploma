package air_tickets;

import java.time.ZoneOffset;

/**
 * Created by Anton on 09.07.2017.
 */
public class Airport {

    private String name;
    private String iata;
    private ZoneOffset timezone;

    public Airport(String name, String iata, ZoneOffset timezone) {
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

    public ZoneOffset getTimezone() {
        return timezone;
    }
}