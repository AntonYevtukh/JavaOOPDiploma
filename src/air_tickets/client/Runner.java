package air_tickets.client;

import air_tickets.*;
import air_tickets.globals.Schedule;
import air_tickets.globals.Users;
import air_tickets.globals.World;
import air_tickets.in_out.Utils;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.*;

import static air_tickets.in_out.Utils.readDate;
import static air_tickets.in_out.Utils.readItemNumbers;

/**
 * Created by Anton on 13.07.2017.
 */
public class Runner {

    public static void main(String[] args) {

        MaualRunner.run();
        /*for (int i = 0; i < 3; i++) {
            System.out.println("Select few numbers from {1, 2, 3, 4, 5}:");
            int[] input = Utils.readItemNumbers(5);
            for (int j: input)
                System.out.print(j + ",");
        }*/
        //System.out.println(Utils.readInt(5));
        //System.out.println(Utils.readInt(5));
        //System.out.println(readDate());
        //System.out.println(Utils.readGender());

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

        //System.out.println(world.getAirportsString());
        //System.out.println(world.getCitiesString());

        for (Airport airport : world.getAirportsByCityIata("PAR"))
            System.out.println(airport);

        Aircraft boeing = new Aircraft("B-737-300", 100, 50, 0);

        //System.out.println(boeing.getFullInfo());

        Flight flightOne = new Flight("KBP-LBG-1142", "KBP", "LBG",
                LocalTime.of(7,0), LocalTime.of(9, 0), boeing, "МАУ");
        Flight flightTwo = new Flight("IEV-CDG-1235", "IEV", "CDG",
                LocalTime.of(10,0), LocalTime.of(12, 0), boeing, "МАУ");
        Flight flightThree = new Flight("KBP-CDG-1248", "KBP", "CDG",
                LocalTime.of(5,30), LocalTime.of(7, 20), boeing, "МАУ");

        Map<SeatClass, Long> flightOnePrices = new HashMap<>();
        flightOnePrices.put(SeatClass.ECONOMY, 200L);
        flightOnePrices.put(SeatClass.BUSINESS, 300L);
        flightOnePrices.put(SeatClass.FIRST, 0L);

        schedule.addFlight(flightOne, LocalDate.of(2017, 7, 24),
                flightOnePrices);

        schedule.addFlight(flightOne, LocalDate.of(2017, 7, 28),
                flightOnePrices);

        schedule.addFlight(flightTwo, LocalDate.of(2017, 7, 24),
                flightOnePrices);

        schedule.addFlight(flightThree, LocalDate.of(2017, 7, 24),
                flightOnePrices);

        //System.out.println(schedule.allFlightString());

        Passenger anton = new Passenger("Anton", "Yevtukh", Gender.MALE,
                LocalDate.of(1991,2,25), "ME 814732");

        User myUser = new User("anton172");
        Users users = Users.getInstance();
        users.signUp(myUser);

        //System.out.println("Users" + users.getAccounts().values());

        myUser.getAccount().debit(100500L);
        myUser.setPassenger(anton);
        myUser.setPassenger(Passenger.readFromConsole());
    }
}

class MaualRunner {

    static void run() {

        WorldInitializer.initWorld();

        List<FlightRecord> searchResults = new ArrayList<>();
        Schedule schedule = Schedule.getInstance();
        World world = World.getInstance();
        Users users = Users.getInstance();

        System.out.println("Searching for flight: ");
        System.out.println("\n****************************************************************\n");

        searchResults.addAll(schedule.searchForFlight(world.getAirportIatasByString("Kiev"),
                world.getAirportIatasByString("PAR"), LocalDate.of(2017, 7, 24),
                SeatClass.ECONOMY, 15));

        User user = users.signIn("anton172");
        List<Passenger> passengers = new ArrayList<>();
        for (int i = 0; i < 51; i++) {
            passengers.add(user.getPassenger());
        }

        try {
            user.bookTickets(searchResults.get(0).getId(), SeatClass.ECONOMY, passengers, false);
        }
        catch (Exception exc) {
            System.out.println(exc.getMessage());
        }

        user.getTickets().forEach(System.out::println);
        System.out.println(user.getAccountingInfo());

        try {
            user.buyBookedTickets(user.getTickets());
        }
        catch (Exception exc) {
            System.out.println(exc.getMessage());
        }

        user.getTickets().forEach(System.out::println);
        System.out.println(user.getAccountingInfo());

        searchResults.forEach(System.out::println);

        //System.out.println(schedule.allFlightString());
        //System.out.println(searchResults);
    }
}
