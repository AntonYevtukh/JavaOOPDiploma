package air_tickets.client;

import air_tickets.*;
import air_tickets.globals.Schedule;
import air_tickets.globals.World;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Anton on 13.07.2017.
 */
public class Runner {

    public static void main(String[] args) {

        WorldInitializer.initWorld();
    }
}

class WorldInitializer {

    static void initWorld() {

        World world = World.getInstance();
        Schedule schedule = Schedule.getInstance();

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

        System.out.println(world.getAirportsString());
        System.out.println(world.getCitiesString());

        for (Airport airport : world.getAirportsByCityIata("PAR"))
            System.out.println(airport);

        Aircraft boeing = new Aircraft("B-737-300", 100, 50, 0);

        System.out.println(boeing.getFullInfo());

        Flight flightOne = new Flight("KBP-LBG-1142", "KBP", "LBG",
                LocalTime.of(7,0), LocalTime.of(9, 0), boeing, "МАУ");

        Map<SeatClass, Long> flightOnePrices = new HashMap<>();
        flightOnePrices.put(SeatClass.ECONOMY, 200L);
        flightOnePrices.put(SeatClass.BUSINESS, 300L);
        flightOnePrices.put(SeatClass.FIRST, 0L);

        schedule.addFlight(flightOne, LocalDate.of(2017, 7, 24),
                flightOnePrices);

        schedule.addFlight(flightOne, LocalDate.of(2017, 7, 28),
                flightOnePrices);

        System.out.println(schedule.allFlightString());
    }
}
