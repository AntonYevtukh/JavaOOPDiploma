package air_tickets.client;

import air_tickets.*;
import air_tickets.globals.Schedule;
import air_tickets.globals.Users;
import air_tickets.globals.World;
import air_tickets.in_out.Initializer;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Anton on 03.08.2017.
 */
public class CodeInitializer implements Initializer {

    public void init() {
        try {
            initWorld();
            initSchedule();
            initUsers();
        } catch (Exception exc) {
            System.err.println("Error during initialization from code: " + exc.getMessage());
            System.out.println("Program will be terminated.");
            System.exit(1);
        }
        System.err.println("Initialization from code successfully completed.\n");
        try {
            Thread.sleep(100);
        } catch (InterruptedException exc) {

        }
    }

    private void initWorld() {

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
    }

    private void initSchedule() {

        Schedule schedule = Schedule.getInstance();
        Aircraft boeing = new Aircraft("B-737-300", 100, 50, 0);

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

        schedule.addFlight(flightOne, LocalDate.of(2017, 8, 24),
                flightOnePrices);

        schedule.addFlight(flightOne, LocalDate.of(2017, 6, 22),
                flightOnePrices);

        schedule.addFlight(flightTwo, LocalDate.of(2017, 8, 24),
                flightOnePrices);

        schedule.addFlight(flightThree, LocalDate.of(2017, 8, 24),
                flightOnePrices);
    }

    private void initUsers() {

        Passenger anton = new Passenger("Anton", "Yevtukh", Gender.MALE,
                LocalDate.of(1991,2,25), "ME 814732");

        Schedule schedule = Schedule.getInstance();
        World world = World.getInstance();
        Users users = Users.getInstance();
        User myUser = users.signUp("anton172");

        myUser.debitAccount(100500L);
        myUser.setPassenger(anton);

        List<FlightRecord> searchResults = new ArrayList<>();

        searchResults.addAll(schedule.searchForFlight(world.getAirportIatasByString("Kiev"),
                world.getAirportIatasByString("PAR"), LocalDate.of(2017, 8, 24),
                SeatClass.ECONOMY, 15));

        User user = users.signIn("anton172");

        List<Passenger> passengers = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            passengers.add(user.getPassenger());
        }

        try {
            user.bookTickets(searchResults.get(0).getId(), SeatClass.ECONOMY, passengers, false);
        }
        catch (Exception exc) {
            System.out.println(exc.getMessage());
        }

        user.getBookmarks().add(new Bookmark(searchResults.get(0), SeatClass.ECONOMY, 2));
        user.getBookmarks().add(new Bookmark(searchResults.get(1), SeatClass.BUSINESS, 1));
        user.getBookmarks().add(new Bookmark(searchResults.get(2), SeatClass.ECONOMY, 1));

        try {
            user.buyBookedTickets(user.getTickets());
        }
        catch (Exception exc) {
            System.out.println(exc.getMessage());
        }

        Users.getInstance().signOut();
    }
}
