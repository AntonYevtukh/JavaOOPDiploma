package air_tickets.trash;

import air_tickets.Airport;

import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Anton on 04.08.2017.
 */
public class JsonTester {

    public static void main(String[] args) {
        Airport kbp = new Airport("Borispol", "KBP", ZoneId.of("Europe/Kiev"));
        Airport iev = new Airport("Zhuliany", "IEV", ZoneId.of("Europe/Kiev"));
        Airport cdg = new Airport("Charles de Gaulle", "CDG", ZoneId.of("Europe/Paris"));
        Airport lbg = new Airport("Le Bourget", "LBG", ZoneId.of("Europe/Paris"));
        //System.out.println(new jsonParser<Airport>().toOutputString(kbp));
        List<Airport> kievAirports = new ArrayList<>();
        kievAirports.addAll(Arrays.asList(kbp, iev));
        List<Airport> parisAirports = new ArrayList<>();
        parisAirports.addAll(Arrays.asList(cdg, iev));
        List<List<Airport>> airports = new ArrayList<>();
        airports.add(kievAirports);
        airports.add(parisAirports);
        System.out.println(new jsonParser<List<List<Airport>>>().toOutputString(airports));
    }
}
