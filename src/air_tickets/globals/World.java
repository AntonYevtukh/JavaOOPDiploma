package air_tickets.globals;

import air_tickets.Airport;
import air_tickets.City;

import java.io.Serializable;
import java.util.*;

/**
 * Created by Anton on 10.07.2017.
 */
public class World implements Serializable {

    private static World instance;
    private final Map<String, Airport> airports = new HashMap<>();
    private final Map<String, City> cities = new HashMap<>();

    private World() {}

    public static World getInstance() {
        if (instance == null)
            instance = new World();
        return instance;
    }

    public boolean setAsInstance() {
        if (instance != null) {
            return false;
        }
        else {
            instance = this;
            return true;
        }
    }

    public void addAirport(Airport airport)
            throws IllegalArgumentException {
        if (!airports.containsKey(airport.getIata()))
            airports.put(airport.getIata(), airport);
        else
            throw new IllegalArgumentException("This airport already exists");
    }

    public void addAirports(Airport... airports)
            throws IllegalArgumentException {
        for (Airport airport : airports)
            addAirport(airport);
    }

    public void addCities(City... cities) {
        for (City city : cities)
            addCity(city);
    }

    public void addCity(City city)
            throws IllegalArgumentException {
        String errorMessage = "";
        if (!cities.containsKey(city.getIata())) {
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
            if (airport.getName().toLowerCase().equals(name.toLowerCase()))
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
            if (city.getName().toLowerCase().equals(name.toLowerCase()))
                return new ArrayList<>(city.getAirports());
        return null;
    }

    public List<String> getAirportIatasByString(String target) {
        List<Airport> foundAirports = new ArrayList<>();
        List<String> result = new ArrayList<>();
        if (isIataCode(target)) {
            String iata = target;
            if (cities.containsKey(iata))
                foundAirports.addAll(getAirportsByCityIata(iata));
            else if (airports.containsKey(iata))
                foundAirports.add(getAirportByIata(iata));
        }
        else {
            String name = target;
            if (getAirportsByCityName(name) != null)
                foundAirports.addAll(getAirportsByCityName(name));
            else if (getAirportByName(name) != null)
                foundAirports.add(getAirportByName(name));
        }
        for (Airport airport : foundAirports)
            result.add(airport.getIata());
        return result;
    }

    private boolean isIataCode(String target) {
        return target.length() == 3 && target.equals(target.toUpperCase());
    }

    public String getAirportsString() {
        String separator = "\n------------------------------------------------------------------------------\n";
        StringJoiner joiner = new StringJoiner("\n", "World's airports" + separator, separator);
        for (Airport airport : airports.values())
            joiner.add(airport.toString());
        return joiner.toString();
    }

    public String getCitiesString() {
        String separator = "\n------------------------------------------------------------------------------\n";
        StringJoiner joiner = new StringJoiner("\n", "World's cities" + separator, separator);
        for (City city : cities.values())
            joiner.add(city.toString());
        return joiner.toString();
    }

    /*private Object readResolve() {
        if (setAsInstance()) {
            System.err.println("World instance successfully loaded from the file.");
        }
        else {
            System.err.println("World instance already exists, nothing to load.");
        }
        return instance;
    }*/
}
