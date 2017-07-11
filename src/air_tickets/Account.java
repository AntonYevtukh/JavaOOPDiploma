package air_tickets;

/**
 * Created by Anton on 06.07.2017.
 */
public class Account {

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
