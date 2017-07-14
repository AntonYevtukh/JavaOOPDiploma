package air_tickets;

import air_tickets.tariffs.Tariff;
import air_tickets.tariffs.TariffType;
import air_tickets.tariffs.Tariffs;
import air_tickets.globals.Schedule;

import java.util.*;

/**
 * Created by Anton on 06.07.2017.
 */
public class User {

    private final String id = UUID.randomUUID().toString();
    private String login;
    private Account account;
    private long totallyPaid;
    public TariffType tariffType;
    private Passenger passenger;
    private List<Bookmark> bookmarks = new ArrayList<>();
    private List<Ticket> tickets = new ArrayList<>();

    public User(String login) {
        this.login = login;
        this.account = new Account(0);
        this.tariffType = TariffType.STANDARD;
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
        return Tariffs.getInstance().getTariffByType(tariffType);
    }

    public Account getAccount() {
        return account;
    }

    public long getBalance() {
        return account.getBalance();
    }


    public List<Ticket> bookTickets(String flightRecordId, SeatClass seatClass,
                                    List<Passenger> passengers, boolean isBoughtDirectly)
                                        throws Exception {

        Tariff tariff = getTariff();
        FlightRecord flightRecord = Schedule.getInstance().getFlightRecordById(flightRecordId);
        List<Ticket> createdTickets = new ArrayList<>();

        long fullPrice = passengers.size() * tariff.calculateFullPrice(flightRecord, seatClass);

        if (account.getBalance() < fullPrice)
            throw new Exception("Not enough amount on the user balance!");
        if (!flightRecord.isEnoughSeats(seatClass, passengers.size()))
            throw new Exception("Not enough seats is available on flight!");

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
        //result.append("Next tariff: \t\t\t\t" + getTariff().getNextTariff(1000000000) + "\n");
        result.append("Amount left to upgrade: \t$" + getTariff().requiredForUpgrade(totallyPaid));
        result.append("\n--------------------------------------------------------------------------\n");
        return result.toString();
    }

    public String toString() {
        return login;
    }
}
