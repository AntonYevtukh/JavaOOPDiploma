package air_tickets;

import java.util.UUID;

/**
 * Created by Anton on 10.07.2017.
 */
public class Account {

    private final String id = UUID.randomUUID().toString();
    private long balance;

    public boolean isEnoughToPay(long amount) {
        return balance >= amount;
    }

    public long getBalance() {
        return balance;
    }

    public void withdraw(long amount) {
        if (balance >= amount)
            balance -= amount;
        else
            throw new IllegalArgumentException("Error! Available amount is not enough to pay");
    }
}
