package air_tickets.proc_menu;

import air_tickets.globals.Users;
import air_tickets.in_out.Utils;

/**
 * Created by Anton on 20.07.2017.
 */
public class AccountingMenu {

    public void showItems() {
        System.out.println("1. Debit account");
        System.out.println("\n0. Back");
    }

    public void run() {
        int action = -1;
        while (action != 0) {
            System.out.println(Users.getInstance().getCurrentUser().getAccountingInfo());
            showItems();
            action = Utils.readMenuItem(1);
            if (action == 1)
                debitAccount();
        }
    }

    private void debitAccount() {
        System.out.println("Input amount you want to debit:");
        int amount = Utils.readInt(10000);
        Users.getInstance().getCurrentUser().debitAccount(amount);
        System.out.println("You have successfully debit account on $" + amount);
    }
}
