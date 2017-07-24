package air_tickets.proc_menu;

import air_tickets.*;
import air_tickets.globals.Schedule;
import air_tickets.globals.Users;
import air_tickets.globals.World;
import air_tickets.in_out.Utils;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Anton on 20.07.2017.
 */
public class SearchMenu {

    private boolean noResults = true;
    private SeatClass seatClass;
    private int seatCount;
    private List<FlightRecord> searchResults = null;

    public void showItems() {
        System.out.println("1. Search for tickets");
        if (!noResults) {
            System.out.println("2. Buy tickets");
            System.out.println("3. Book tickets");
            System.out.println("4. Add to my bookmarks");
        }
        System.out.println("\n0. Back");
    }

    public void run() {

        int action = -1;
        while (action != 0) {
            showItems();
            int itemCount = noResults ? 1 : 4;
            action = Utils.readMenuItem(4);
            switch (action) {
                case 1:
                    findTickets();
                    if (!searchResults.isEmpty() && Users.getInstance().isLogged())
                        noResults = false;
                    searchResults.forEach(System.out::println);
                    break;
                case 2:
                    buyTickets(true);
                    break;
                case 3:
                    buyTickets(false);
                    break;
                case 4:
                    addToBookmarks();
                    break;
            }
        }
    }

    public void findTickets() {
        List<String> origins;
        List<String> destinations;
        LocalDate date;

        System.out.println("Enter info for search:");
        System.out.println("Enter origin:");
        origins = World.getInstance().getAirportIatasByString(Utils.readString());
        System.out.println("Enter destination:");
        destinations = World.getInstance().getAirportIatasByString(Utils.readString());
        System.out.println("Enter date:");
        date = Utils.readDate();
        System.out.println("Choose a class:");
        System.out.println("1. Economy\n2. Business\n3. First");
        seatClass = SeatClass.values()[Utils.readInt(3) - 1];
        System.out.println("Enter count of seats");
        seatCount = Utils.readInt(10);
        searchResults =  Schedule.getInstance().searchForFlight(origins, destinations, date, seatClass, seatCount);
    }

    public void buyTickets(boolean buyDirectly) {
        User currentUser = Users.getInstance().getCurrentUser();
        List<Passenger> passengers = new ArrayList<>();
        List<Ticket> boughtTickets = new ArrayList<>();
        System.out.println("Please, choose a flight from the list above: ");
        int flightNumber = Utils.readInt(searchResults.size()) - 1;
        if (searchResults.get(flightNumber).isExpired()) {
            System.out.println("\nError: Unable to buy tickets, flight is expired!\n");
            return;
        }
        System.out.println("Do you prefer to use info about seats from search?");
        System.out.println("1. Yes\n2. No");
        int answer = Utils.readInt(2);
        if (answer == 2) {
            System.out.println("Please, choose a class: ");
            System.out.println("1. Economy\n2. Business\n3. First");
            seatClass = SeatClass.values()[Utils.readInt(3) - 1];
            System.out.println("Please, enter the count of seats: ");
            seatCount = Utils.readInt(10);
        }
        for (int i = 0; i < seatCount; i++) {
            if (currentUser.getPassenger() != null) {
                System.out.println("Do you prefer to use passenger info from profile?");
                System.out.println("1. Yes\n2. No");
                int answer2 = Utils.readInt(2);
                if (answer2 == 1)
                    passengers.add(currentUser.getPassenger());
                else
                    passengers.add(Passenger.readFromConsole());
            }
            else
                passengers.add(Passenger.readFromConsole());
        }
        try {
            boughtTickets = currentUser.bookTickets(searchResults.get(flightNumber).getId(),
                    seatClass, passengers, buyDirectly);
        }
        catch (Exception exc) {
            System.out.println(exc.getMessage());
        }
        if (!boughtTickets.isEmpty()) {
            System.out.println("You have successfully bought tickets:");
            boughtTickets.forEach(System.out::println);
        }
    }

    private void addToBookmarks() {
        User currentUser = Users.getInstance().getCurrentUser();
        System.out.println("Please, choose a flight from the list above: ");
        int flightNumber = Utils.readInt(searchResults.size()) - 1;
        currentUser.getBookmarks().add(new Bookmark(searchResults.get(flightNumber), seatClass, seatCount));
    }
}
