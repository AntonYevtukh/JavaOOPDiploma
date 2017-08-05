package air_tickets;

import java.io.Serializable;

/**
 * Created by Anton on 06.07.2017.
 */
public class Account implements Serializable {

    private long balance;

    public Account(long initBalance) {
        this.balance = initBalance;
    }

    public boolean withdraw(long amount) {
        if (balance < amount)
            return false;
        else
            balance -= amount;
        return true;
    }

    public void debit(long amount) {
        balance += amount;
    }

    public long getBalance() {
        return balance;
    }
}
