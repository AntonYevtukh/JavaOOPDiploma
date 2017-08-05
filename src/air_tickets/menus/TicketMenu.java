package air_tickets.menus;

import air_tickets.*;
import air_tickets.globals.Users;
import air_tickets.globals.World;
import air_tickets.in_out.Utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;

/**
 * Created by Anton on 20.07.2017.
 */
public class TicketMenu {

    private List<Ticket> tickets;

    public TicketMenu() {
        tickets = new ArrayList<>(Users.getInstance().getCurrentUser().getTickets());
    }

    public void showItems() {
        showTickets();
        if (!tickets.isEmpty()) {
            if (containsBookedOnly()) {
                System.out.println("1. Buy tickets");
                System.out.println("2. Unbook tickets");
            }
            System.out.println("3. Sort tickets");
            System.out.println("4. Filter tickets");
        }
        System.out.println("5. Drop filters and sorting");

        System.out.println("\n0. Back");
    }

    public void run() {
        int action = -1;
        while (action != 0) {
            showItems();
            action = Utils.readMenuItem(6);
            switch (action) {
                case 1:
                    if (!tickets.isEmpty() && containsBookedOnly())
                        try {
                            buyTickets();
                        }
                        catch (Exception exc) {
                            System.out.println(exc.getMessage());
                        }
                    break;
                case 2:
                    if (!tickets.isEmpty() && containsBookedOnly())
                        unBookTickets();
                    break;
                case 3:
                    if (!tickets.isEmpty())
                        sortTickets();
                    break;
                case 4:
                    if (!tickets.isEmpty())
                        filterTickets();
                    break;
                case 5:
                    tickets = new ArrayList<>(Users.getInstance().getCurrentUser().getTickets());
                    break;
            }
        }
    }

    private void showTickets() {
        if (!tickets.isEmpty()) {
            System.out.println("Your tickets: ");
            Utils.printList(tickets);
        }
        else
            System.out.println("No tickets found");
    }

    private void buyTickets()
            throws Exception {
        boolean containExpired = false;
        User currentUser = Users.getInstance().getCurrentUser();
        List<Ticket> ticketsToBuy = new ArrayList<>();
        System.out.println("Please, select tickets from the list above: ");
        int[] answers = Utils.readItemNumbers(tickets.size());
        long totalPrice = 0;
        for (int i : answers) {
            Ticket ticket = tickets.get(i);
            if (!ticket.isExpired()) {
                ticketsToBuy.add(ticket);
                totalPrice += ticket.getPriceForBooked();
            }
            else {
                containExpired = true;
                System.out.println("Unable to buy ticket #" + i + ", it is expired");
            }
        }
        if (containExpired) {
            System.out.println("One or more tickets are expired. Do you want to proceed and buy all other?");
            System.out.println("1. Yes\n2. No");
            int answer = Utils.readInt(2);
            if (answer == 2)
                return;
        }
        System.out.println("It will cost $" + totalPrice);
        if (currentUser.getBalance() < totalPrice)
            throw new Exception("Not enough money on the balance");
        currentUser.buyBookedTickets(ticketsToBuy);
        if (!ticketsToBuy.isEmpty()) {
            System.out.println("Tickets successfully bought: ");
            Utils.printList(ticketsToBuy);
        }
        else
            System.out.println("You have bought no tickets!");
    }

    private void unBookTickets() {
        User currentUser = Users.getInstance().getCurrentUser();
        List<Ticket> ticketsToUnBook = new ArrayList<>();
        System.out.println("Please, select tickets from the list above: ");
        int[] answers = Utils.readItemNumbers(tickets.size());
        long totalRefund = 0;
        for (int i : answers) {
            Ticket ticket = tickets.get(i);
            if (!ticket.isExpired()) {
                ticketsToUnBook.add(tickets.get(i));
                totalRefund += tickets.get(i).getUnBookingRefund();
            }
            else
                System.out.println("Ticked #" + i + " is expired. You will get no refund for it.");
        }
        System.out.println("You will get back $" + totalRefund);
        currentUser.unBookTickets(ticketsToUnBook);
        if (!ticketsToUnBook.isEmpty()) {
            System.out.println("Tickets successfully bought: ");
            Utils.printList(ticketsToUnBook);
        }
        else
            System.out.println("You have unBooked no tickets!");
    }

    private void sortTickets() {
        int[] answers;
        System.out.println("Please, choose options (they will be applied in order, that you will type):");
        System.out.println("1. Sort by fly date ascending");
        System.out.println("2. Sort by fly date descending");
        System.out.println("3. Sort by booking date ascending");
        System.out.println("4. Sort by booking date descending");
        System.out.println("5. Sort by full price ascending");
        System.out.println("6. Sort by full price descending");
        System.out.println("7. Sort by unbooking refund ascending");
        System.out.println("8. Sort by unbooking refund descending");
        answers = Utils.readItemNumbers(6);
        tickets.sort(getTicketComparator(answers));
    }

    private Comparator<Ticket> getTicketComparator(int[] answers) {
        List<Comparator<Ticket>> comparators = new ArrayList<>();
        comparators.add(Comparator.comparing((Ticket t) -> t.getFlightRecord().getDate()));
        comparators.add(Comparator.comparing((Ticket t) -> t.getFlightRecord().getDate()).reversed());
        comparators.add(Comparator.comparing((Ticket t) -> t.getBookedAt()));
        comparators.add(Comparator.comparing((Ticket t) -> t.getBookedAt()).reversed());
        comparators.add(Comparator.comparing((Ticket t) -> t.getPrice()));
        comparators.add(Comparator.comparing((Ticket t) -> t.getPrice()).reversed());
        comparators.add(Comparator.comparing((Ticket t) -> t.getUnBookingRefund()));
        comparators.add(Comparator.comparing((Ticket t) -> t.getUnBookingRefund()).reversed());

        Comparator<Ticket> resultComparator = (Ticket t1, Ticket t2) -> 0;
        for (int i : answers)
            resultComparator = resultComparator.thenComparing(comparators.get(i));
        return resultComparator;
    }

    private void filterTickets() {
        int[] answers;
        System.out.println("Choose options: ");
        System.out.println("1. Show booked tickets");
        System.out.println("2. Show bought tickets");
        System.out.println("3. Show unbooked tickets");
        System.out.println("4. Filter by flight date");
        System.out.println("5. Filter by booking date");
        System.out.println("6. Filter by origin and destination");
        System.out.println("7. Filter by price");
        System.out.println("8. Filter by airline name");
        System.out.println("9. Filter by passenger name");
        System.out.println("10. Filter by class");
        answers = Utils.readItemNumbers(10);
        tickets.removeIf(getTicketPredicate(answers));
    }

    private Predicate<Ticket> getTicketPredicate(int[] answers) {
        Predicate<Ticket> resultPredicate = (Ticket t) -> false;
        for (int i : answers)
            switch (i) {
                case 0:
                    resultPredicate = resultPredicate.or((Ticket t) -> t.getBookingState() != State.BOOKED);
                    break;
                case 1:
                    resultPredicate = resultPredicate.or((Ticket t) -> t.getBookingState() != State.BOUGHT);
                    break;
                case 2:
                    resultPredicate = resultPredicate.or((Ticket t) -> t.getBookingState() != State.FREE);
                    break;
                case 3:
                    System.out.println("Filtering by the flight date:");
                    System.out.println("Please, select a start date of period:");
                    LocalDate start = Utils.readDate();
                    System.out.println("Please, select a end date of period:");
                    LocalDate end = Utils.readDate();
                    resultPredicate = resultPredicate.or((Ticket t) -> t.getFlightRecord().getDate().isBefore(start)
                            || t.getFlightRecord().getDate().isAfter(end));
                    break;
                case 4:
                    System.out.println("Filtering by the booking date:");
                    System.out.println("Please, select a start date of period:");
                    LocalDateTime startBooking = LocalDateTime.of(Utils.readDate(), LocalTime.MIN);
                    System.out.println("Please, select a end date of period:");
                    LocalDateTime endBooking = LocalDateTime.of(Utils.readDate(), LocalTime.MAX);
                    resultPredicate = resultPredicate.or((Ticket t) -> t.getBookedAt().isBefore(startBooking)
                            || t.getBookedAt().isAfter(endBooking));
                    break;
                case 5:
                    System.out.println("Please, enter origin: ");
                    String origin = Utils.readString();
                    System.out.println("Please, enter destination: ");
                    String destination = Utils.readString();
                    List<String> origins = World.getInstance().getAirportIatasByString(origin);
                    List<String> destinations = World.getInstance().getAirportIatasByString(destination);
                    resultPredicate = resultPredicate.or((Ticket t) ->
                            !origins.contains(t.getFlightRecord().getFlight().getOriginIata()) ||
                                    !destinations.contains(t.getFlightRecord().getFlight().getDestinationIata()));
                    break;
                case 6:
                    System.out.println("Please, select the min price:");
                    long minPrice = Utils.readInt(100500);
                    System.out.println("Please, select the min price:");
                    long maxPrice = Utils.readInt(100500);
                    resultPredicate = resultPredicate.or((Ticket t) -> t.getPrice() < minPrice || t.getPrice() > maxPrice);
                    break;
                case 7:
                    System.out.println("Please, specify the airline name");
                    String airlineName = Utils.readString();
                    resultPredicate = resultPredicate.or((Ticket t) ->
                            !t.getFlightRecord().getFlight().getAirline().toUpperCase().contains(airlineName.toUpperCase()));
                    break;
                case 8:
                    System.out.println("Please, specify the passenger name");
                    String passengerName = Utils.readString();
                    resultPredicate = resultPredicate.or((Ticket t) ->
                            !t.getPassenger().getFullName().toUpperCase().contains(passengerName.toUpperCase()));
                    break;
                case 9:
                    System.out.println("Please, select a class");
                    System.out.println("1. Economy\n2. Business\n3. First");
                    SeatClass seatClass = SeatClass.values()[Utils.readInt(3) - 1];
                    resultPredicate = resultPredicate.or((Ticket t) -> t.getSeatClass() != seatClass);
                    break;
                default:
                    throw new RuntimeException("Invalid answer in bookmark filtering options");
            }
        return resultPredicate;
    }

    private boolean containsBookedOnly() {
        for (Ticket ticket : tickets)
            if (ticket.getBookingState() != State.BOOKED)
                return false;
        return true;
    }
}
