package air_tickets;

import air_tickets.search.Routes;
import air_tickets.tariffs.Tariff;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by Anton on 06.07.2017.
 */
public class User {

    private final String id = UUID.randomUUID().toString();
    private String login;
    private Account account;
    private Tariff tariff;
    private Passenger passenger;
    private List<Order> orders = new ArrayList<>();
    private List<Ticket> bookedTickets = new ArrayList<>();
    private List<Ticket> boughtTickets = new ArrayList<>();

    public User(String login) {
        this.login = login;
        this.account = new Account(0);
    }

    public Tariff getTariff() {
        return tariff;
    }

    public long getBalance() {
        return account.getBalance();
    }
}
