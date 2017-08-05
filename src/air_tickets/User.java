package air_tickets;

import air_tickets.tariffs.Tariff;
import air_tickets.globals.Schedule;
import air_tickets.tariffs.Tariffs;

import java.io.Serializable;
import java.util.*;

/**
 * Created by Anton on 06.07.2017.
 */
public class User implements Serializable {

    private final String id = UUID.randomUUID().toString();
    private String login;
    private Account account;
    private long totallyPaid;
    public Tariffs tariffType;
    private Passenger passenger;
    private List<Bookmark> bookmarks = new ArrayList<>();
    private List<Ticket> tickets = new ArrayList<>();

    public User(String login) {
        this.login = login;
        this.account = new Account(0);
        this.tariffType = Tariffs.STANDARD;
    }

    public String getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    public Passenger getPassenger() {
        return passenger;
    }

    public List<Bookmark> getBookmarks() {
        return bookmarks;
    }

    public List<Ticket> getTickets() {
        return tickets;
    }

    public void setPassenger(Passenger passenger) {
        this.passenger = passenger;
    }

    public Tariff getTariff() {
        return tariffType;
    }

    //For test only
    public void debitAccount(long amount) {
        account.debit(amount);
    }

    public long getBalance() {
        return account.getBalance();
    }


    public List<Ticket> bookTickets(String flightRecordId, SeatClass seatClass,
                                    List<Passenger> passengers, boolean isBoughtDirectly)
                                        throws Exception {

        Tariff tariff = getTariff();
        FlightRecord flightRecord = Schedule.getInstance().getFlightRecordById(flightRecordId);

        if (flightRecord.isExpired())
            throw new Exception("Flight is expired!");

        List<Ticket> createdTickets = new ArrayList<>();

        long fullPrice = passengers.size() * tariff.calculateFullPrice(flightRecord, seatClass);

        if (account.getBalance() < fullPrice)
            throw new Exception("\nError: Not enough amount on the user balance!\n");
        if (!flightRecord.isEnoughSeats(seatClass, passengers.size()))
            throw new Exception("\nError: Not enough seats is available on flight!\n");

        for (Passenger passenger : passengers) {
            long price = fullPrice / passengers.size();

            createdTickets.add(new Ticket(flightRecord.getId(), passenger, seatClass, isBoughtDirectly, price));
            flightRecord.decrementAvailableSeats(seatClass);
        }

        account.withdraw(fullPrice);
        totallyPaid += fullPrice;
        tariffType = tariff.getNextTariff(totallyPaid);
        tickets.addAll(createdTickets);

        return createdTickets;
    }

    public List<Ticket> buyBookedTickets(List<Ticket> bookedTickets)
            throws Exception {
        for(Ticket ticket : bookedTickets)
            if (ticket.getBookingState() != State.BOOKED)
                throw new IllegalArgumentException("Can't buy not booked ticket");

        long fullPrice = 0;
        for (Ticket ticket : bookedTickets)
            fullPrice += getTariff().calculatePriceForBooked(ticket);

        if (getBalance() < fullPrice)
            throw new Exception("Not enough amount on the user balance!");

        for(Ticket ticket : bookedTickets)
            ticket.setBookingState(State.BOUGHT);

        account.withdraw(fullPrice);
        totallyPaid += fullPrice;
        tariffType = getTariff().getNextTariff(totallyPaid);
        return bookedTickets;
    }

    public List<Ticket> unBookTickets(List<Ticket> bookedTickets) {
        for(Ticket ticket : bookedTickets)
            if (ticket.getBookingState() != State.BOOKED)
                throw new IllegalArgumentException("Can't buy not booked ticket");

        long refundAmount = 0;
        for (Ticket ticket : bookedTickets)
             refundAmount += getTariff().calculateUnBookingRefund(ticket);

        for(Ticket ticket : bookedTickets) {
            ticket.setBookingState(State.FREE);
            ticket.getFlightRecord().incrementAvailableSeats(ticket.getSeatClass());
        }

        account.debit(refundAmount);
        return bookedTickets;
    }

    public String getAccountingInfo() {
        StringBuilder result = new StringBuilder("User's accounting info:");
        result.append("\n--------------------------------------------------------------------------\n");
        result.append("Current balance: \t\t\t$" + account.getBalance() + "\n");
        result.append("Current tariff: \t\t\t" + tariffType.toString() + "\n");
        result.append("Amount left to upgrade: \t$" + getTariff().requiredForUpgrade(totallyPaid));
        result.append("\n--------------------------------------------------------------------------\n");
        return result.toString();
    }

    public String toString() {
        return login;
    }
}
