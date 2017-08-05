package air_tickets.menus;

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
        System.out.println("Please, select action:");
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
            action = Utils.readMenuItem(4);
            switch (action) {
                case 1:
                    findTickets();
                    if (!searchResults.isEmpty() && Users.getInstance().isLogged())
                        noResults = false;
                    Utils.printList(searchResults);
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
        System.out.println("Enter origin (3-letter upper case string will be recognized as IATA code):");
        origins = World.getInstance().getAirportIatasByString(Utils.readString());
        System.out.println("Enter destination (3-letter upper case string will be recognized as IATA code):");
        destinations = World.getInstance().getAirportIatasByString(Utils.readString());
        System.out.println("Enter date:");
        date = Utils.readDate();
        System.out.println("Choose a class:");
        System.out.println("1. Economy\n2. Business\n3. First");
        seatClass = SeatClass.values()[Utils.readInt(3) - 1];
        System.out.println("Enter count of seats (not more than 10)");
        seatCount = Utils.readInt(10);
        searchResults =  Schedule.getInstance().searchForFlight(origins, destinations, date, seatClass, seatCount);
        if (!searchResults.isEmpty())
            System.out.println("Tickets found:\n");
        else
            System.out.println("No tickets were found by your query.");
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
            passengers.add(Utils.getNewPassenger());
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
