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
    private TariffType tariffType;
    private Passenger passenger;
    private List<Bookmark> bookmarks = new ArrayList<>();
    private List<Ticket> tickets = new ArrayList<>();

    public User(String login) {
        this.login = login;
        this.account = new Account(0);
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

        long fullPrice = isBoughtDirectly ?
                passengers.size() * tariff.calculateFullPrice(flightRecord, seatClass) :
                passengers.size() * tariff.calculateBookingPrice(flightRecord, seatClass);

        if (account.getBalance() < fullPrice)
            throw new Exception("Not enough amount on the user balance!");
        if (!flightRecord.isEnoughSeats(seatClass, passengers.size()))
            throw new Exception("Not enough available seats is available on flight!");

        for (Passenger passenger : passengers) {
            long price = fullPrice / passengers.size();
            createdTickets.add(new Ticket(flightRecord.getId(), passenger, seatClass,
                    isBoughtDirectly ? State.BOUGHT : State.BOOKED, price));
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

        long fullPrice = getTariff().calculatePriceForBooked(bookedTickets);

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

        long refundAmount = getTariff().calculateUnBookingRefund(bookedTickets);

        for(Ticket ticket : bookedTickets) {
            ticket.setBookingState(State.FREE);
            ticket.getFlightRecord().incrementAvailableSeats(ticket.getSeatClass());
        }

        account.debit(refundAmount);
        return bookedTickets;
    }
}
