package air_tickets.globals;

import air_tickets.Airport;
import air_tickets.City;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Anton on 10.07.2017.
 */
public class World {

    private static final World instance = new World();
    private final Map<String, Airport> airports = new HashMap<>();
    private final Map<String, City> cities = new HashMap<>();

    private World() {}

    public static World getInstance() {
        return instance;
    }

    public void addAirport(Airport airport)
            throws IllegalArgumentException {
        if (!airports.containsKey(airport.getIata()))
            airports.put(airport.getIata(), airport);
        else
            throw new IllegalArgumentException("This airport already exists!");
    }

    public void addCity(City city)
            throws IllegalArgumentException {
        String errorMessage = "";
        if (!cities.containsKey(city)) {
            cities.put(city.getIata(), city);
            for (Airport airport : city.getAirports())
                try {
                    airports.put(airport.getIata(), airport);
                }
                catch (IllegalArgumentException exc) {
                    errorMessage += exc.getMessage() + " ";
                }
            if (!errorMessage.equals(""))
                throw new IllegalArgumentException(errorMessage);
        }
        else throw new IllegalArgumentException("This city already exists");
    }

    public Airport getAirportByIata(String iata) {
        return airports.get(iata);
    }

    public Airport getAirportByName(String name) {
        List<Airport> airports = new ArrayList<>(this.airports.values());
        for (Airport airport : airports)
            if (airport.getName().equals(name))
                return airport;
        return null;
    }

    public List<Airport> getAirportsByCityIata(String iata) {
        if (cities.containsKey(iata))
            return new ArrayList<>(cities.get(iata).getAirports());
        else
            return null;
    }

    public List<Airport> getAirportsByCityName(String name) {
        List<City> cities = new ArrayList<>(this.cities.values());
        for (City city : cities)
            if (city.getName().equals(name))
                return new ArrayList<>(city.getAirports());
        return null;
    }
}
