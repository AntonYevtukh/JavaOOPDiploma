package air_tickets.trash;

import air_tickets.Aircraft;
import air_tickets.Airport;
import air_tickets.City;
import air_tickets.Flight;
import air_tickets.globals.World;
import air_tickets.in_out.JsonMarshaller;
import com.google.gson.JsonSyntaxException;

import java.io.IOException;
import java.time.LocalTime;
import java.time.ZoneId;

/**
 * Created by Anton on 05.08.2017.
 */
public class AircraftJsonTester {

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





        JsonMarshaller serializer = new JsonMarshaller();
        Aircraft boeing = new Aircraft("B-737-300", 100, 50, 0);
        Flight flightOne = new Flight("KBP-LBG-1142", "KBP", "LBG",
                LocalTime.of(7,0), LocalTime.of(9, 0), boeing, "МАУ");
        Flight flightOneCopy = null;
        Aircraft boeing2 = null;
        try{
            serializer.serialize(flightOne, "D:\\boeing.json");
        }
        catch (IOException | JsonSyntaxException exc) {
            System.out.println(exc);
        }
        try {
            flightOneCopy = serializer.deserialize(Flight.class, "D:\\boeing.json");
        }
        catch (IOException | ClassNotFoundException | JsonSyntaxException exc) {
            System.out.println(exc);
        }
        try{
            serializer.serialize(flightOneCopy, "D:\\boeing2.json");
        }
        catch (IOException | JsonSyntaxException exc) {
            System.out.println(exc);
        }
    }
}
