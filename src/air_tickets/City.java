package air_tickets;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Anton on 09.07.2017.
 */
public class City {

    String name;
    String iata;
    Set<Airport> airports = new HashSet<>();

    public City(String name, String iata) {
        this.name = name;
        this.iata = iata;
    }

    public String getName() {
        return name;
    }

    public String getIata() {
        return iata;
    }

    public void addAirport(Airport airport) {
        airports.add(airport);
    }

    public Set<Airport> getAirports() {
        return airports;
    }
}
