package air_tickets.trash;

import air_tickets.Aircraft;
import air_tickets.Airport;
import air_tickets.City;
import air_tickets.Flight;
import air_tickets.globals.World;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;

import java.io.*;
import java.time.LocalTime;
import java.time.ZoneId;

/**
 * Created by Anton on 04.08.2017.
 */
class JsonFlightParser {

    private static final String PATH = "D:\\boeing.json";

    public String toOutputString(Flight flight) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String jsonString = gson.toJson(flight);
        try (PrintWriter printWriter = new PrintWriter(PATH)) {
            printWriter.println(jsonString);
        } catch (IOException exc) {
            System.out.println(exc.getMessage());
        }
        return gson.toJson(flight);
    }

    public Flight parse(String fileName) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        Flight flight = null;
        try {
            flight = gson.fromJson(new FileReader(fileName), Flight.class);
        } catch (JsonSyntaxException | JsonIOException | FileNotFoundException exc) {
            System.out.println(exc.getMessage());
        }
        return flight;
    }

    public static void main(String[] args) {

        World world = World.getInstance();

        Airport kbp = new Airport("Borispol", "KBP", ZoneId.of("Europe/Kiev"));
        Airport iev = new Airport("Zhuliany", "IEV", ZoneId.of("Europe/Kiev"));
        Airport cdg = new Airport("Charles de Gaulle", "CDG", ZoneId.of("Europe/Paris"));
        Airport lbg = new Airport("Le Bourget", "LBG", ZoneId.of("Europe/Paris"));

        City kiev = new City("Kiev", "IEV");
        City paris = new City("Paris", "PAR");

        kiev.addAirport(kbp);
        kiev.addAirport(iev);
        paris.addAirport(cdg);
        paris.addAirport(lbg);

        world.addAirports(kbp, iev, cdg, lbg);
        world.addCities(kiev, paris);
        JsonFlightParser parser = new JsonFlightParser();

        Aircraft boeing = new Aircraft("B-737-300", 100, 50, 0);

        Flight flightOne = new Flight("KBP-LBG-1142", "KBP", "LBG",
                LocalTime.of(7,0), LocalTime.of(9, 0), boeing, "МАУ");

        //System.out.println(parser.toOutputString(flightOne));

        Flight flightOneCopy = parser.parse(PATH);

        System.out.println(parser.toOutputString(flightOneCopy));
    }
}
