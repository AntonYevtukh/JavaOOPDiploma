package air_tickets.menus;

import air_tickets.*;
import air_tickets.globals.Users;
import air_tickets.globals.World;
import air_tickets.in_out.Utils;
import air_tickets.tariffs.Tariff;

import java.time.LocalDate;
import java.util.*;
import java.util.function.Predicate;

/**
 * Created by Anton on 21.07.2017.
 */
public class BookmarkMenu {

    private List<Bookmark> bookmarks;

    public BookmarkMenu() {
        bookmarks = new ArrayList<>(Users.getInstance().getCurrentUser().getBookmarks());
    }

    public void showItems() {
        showBookmarks();
        System.out.println("Please, select action:");
        if (!bookmarks.isEmpty()) {
            System.out.println("1. Buy tickets");
            System.out.println("2. Book tickets");
            System.out.println("3. Delete bookmark");
            System.out.println("4. Sort bookmarks");
            System.out.println("5. Filter bookmarks");
        }
        System.out.println("6. Drop filters and sorting");

        System.out.println("\n0. Back");
    }

    public void run() {
        int action = -1;
        while (action != 0) {
            showItems();
            action = Utils.readMenuItem(6);
            switch (action) {
                case 1:
                    if (!bookmarks.isEmpty())
                        createTicketsFromBookmark(true);
                    break;
                case 2:
                    if (!bookmarks.isEmpty())
                        createTicketsFromBookmark(false);
                    break;
                case 3:
                    if (!bookmarks.isEmpty())
                        deleteBookmarks();
                    break;
                case 4:
                    if (!bookmarks.isEmpty())
                        sortBookmarks();
                    break;
                case 5:
                    if (!bookmarks.isEmpty())
                        filterBookmarks();
                    break;
                case 6:
                    bookmarks = new ArrayList<>(Users.getInstance().getCurrentUser().getBookmarks());
                    break;
            }
        }
    }

    private void createTicketsFromBookmark(boolean isBuyingDirectly) {
        User currentUser = Users.getInstance().getCurrentUser();
        System.out.println("Please, select a source from list of bookmarks:");
        int answer = Utils.readInt(bookmarks.size());
        Bookmark source = bookmarks.get(answer - 1);
        List<Passenger> passengers = new ArrayList<>();
        List<Ticket> bookedTickets = new ArrayList<>();
        for (int i = 0; i < source.getSeatCount(); i++) {
            passengers.add(Utils.getNewPassenger());
        }
        try {
            bookedTickets = currentUser.bookTickets(source.getFlightRecordId(),
                    source.getSeatClass(), passengers, isBuyingDirectly);
        }
        catch (Exception exc) {
            System.out.println(exc.getMessage());
        }
        if (!bookedTickets.isEmpty()) {
            System.out.println("You have successfully booked tickets:\n");
            Utils.printList(bookedTickets);
        }
    }

    private void deleteBookmarks() {
        User currentUser = Users.getInstance().getCurrentUser();
        System.out.println("Choose bookmarks to delete: ");
        int[] bookmarksToDelete = Utils.readItemNumbers(bookmarks.size());
        for (int i: bookmarksToDelete) {
            currentUser.getBookmarks().remove(bookmarks.get(i));
            bookmarks = currentUser.getBookmarks();
        }
        System.out.println("Bookmarks successfully deleted");
    }

    private void showBookmarks() {
        if (!bookmarks.isEmpty()) {
            System.out.println("Your bookmarks: ");
            Utils.printList(bookmarks);
        }
        else
            System.out.println("No bookmarks found");
    }

    private void sortBookmarks() {
        int[] answers;
        System.out.println("Please, choose options (they will be applied in order, that you will type): ");
        System.out.println("1. Sort by fly date ascending");
        System.out.println("2. Sort by fly date descending");
        System.out.println("3. Sort by full price ascending");
        System.out.println("4. Sort by full price descending");
        System.out.println("5. Sort by booking price ascending");
        System.out.println("6. Sort by booking price descending");
        answers = Utils.readItemNumbers(6);
        bookmarks.sort(getBookmarkComparator(answers));
    }

    private Comparator<Bookmark> getBookmarkComparator(int[] answers) {
        Tariff tariff = Users.getInstance().getCurrentUser().getTariff();
        List<Comparator<Bookmark>> comparators = new ArrayList<>();
        comparators.add(Comparator.comparing((Bookmark b) -> b.getFlightRecord().getDate()));
        comparators.add(Comparator.comparing((Bookmark b) -> b.getFlightRecord().getDate()).reversed());
        comparators.add(Comparator.comparing((Bookmark b) -> b.calculateFullPrice(tariff)));
        comparators.add(Comparator.comparing((Bookmark b) -> b.calculateFullPrice(tariff)).reversed());
        comparators.add(Comparator.comparing((Bookmark b) -> b.calculateBookingPrice(tariff)));
        comparators.add(Comparator.comparing((Bookmark b) -> b.calculateBookingPrice(tariff)).reversed());

        Comparator<Bookmark> resultComparator = (Bookmark b1, Bookmark b2) -> 0;
        for (int i : answers)
            resultComparator = resultComparator.thenComparing(comparators.get(i));
        return resultComparator;
    }

    private void filterBookmarks() {
        int[] answers;
        System.out.println("Choose options: ");
        System.out.println("1. Filter by flight date");
        System.out.println("2. Filter by origin and destination");
        System.out.println("3. Filter by full price");
        System.out.println("4. Filter by booking price");
        System.out.println("5. Filter by class");
        answers = Utils.readItemNumbers(5);
        bookmarks.removeIf(getBookmarkPredicate(answers));
    }

    private Predicate<Bookmark> getBookmarkPredicate(int[] answers) {
        Tariff tariff = Users.getInstance().getCurrentUser().getTariff();
        Predicate<Bookmark> resultPredicate = (Bookmark b) -> false;
        for (int i : answers)
            switch (i) {
                case 0:
                    System.out.println("Please, enter the start date of period");
                    LocalDate start = Utils.readDate();
                    System.out.println("Please, enter the end date of period");
                    LocalDate end = Utils.readDate();
                    resultPredicate = resultPredicate.or((Bookmark b) -> b.getFlightRecord().getDate().isBefore(start)
                            || b.getFlightRecord().getDate().isAfter(end));
                    break;
                case 1:
                    System.out.println("Please, enter origin: ");
                    String origin = Utils.readString();
                    System.out.println("Please, enter destination: ");
                    String destination = Utils.readString();
                    List<String> origins = World.getInstance().getAirportIatasByString(origin);
                    List<String> destinations = World.getInstance().getAirportIatasByString(destination);
                    resultPredicate = resultPredicate.or((Bookmark b) ->
                            !origins.contains(b.getFlightRecord().getFlight().getOriginIata()) ||
                                    !destinations.contains(b.getFlightRecord().getFlight().getDestinationIata()));
                    break;
                case 2:
                    System.out.println("Please, enter the min full price");
                    long minFullPrice = Utils.readInt(100500);
                    System.out.println("Please, enter the max full price");
                    long maxFullPrice = Utils.readInt(100500);
                    resultPredicate = resultPredicate.or((Bookmark b) -> b.calculateFullPrice(tariff) < minFullPrice
                            || b.calculateFullPrice(tariff) > maxFullPrice);
                    break;
                case 3:
                    System.out.println("Please, enter the min booking price");
                    long minBookingPrice = Utils.readInt(100500);
                    System.out.println("Please, enter the max booking price");
                    long maxBookingPrice = Utils.readInt(100500);
                    resultPredicate = resultPredicate.or((Bookmark b) -> b.calculateFullPrice(tariff) < minBookingPrice
                            || b.calculateFullPrice(tariff) > maxBookingPrice);
                    break;
                case 4:
                    System.out.println("Please, select a class");
                    System.out.println("1. Economy\n2. Business\n3. First");
                    SeatClass seatClass = SeatClass.values()[Utils.readInt(3) - 1];
                    resultPredicate = resultPredicate.or((Bookmark b) -> b.getSeatClass() != seatClass);
                    break;
                default:
                    throw new RuntimeException("Invalid answer in bookmark filtering options");
            }
        return resultPredicate;
    }
}
