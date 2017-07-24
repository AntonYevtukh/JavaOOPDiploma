package air_tickets.proc_menu;

import air_tickets.User;
import air_tickets.globals.Users;
import air_tickets.in_out.Utils;

/**
 * Created by Anton on 14.07.2017.
 */
public class UserMenu {

    public void showItems() {
        System.out.println("1. View accounting info");
        System.out.println("2. View passenger profile");
        System.out.println("3. View tickets");
        System.out.println("4. View bookmarks");
        System.out.println("5. Find tickets");
        System.out.println("\n0. SignOut");
    }

    public void run() {
        int action = -1;
        while (action != 0) {
            showItems();
            action = Utils.readMenuItem(5);
            switch (action) {
                case 1:
                    showAccountingInfo();
                    break;
                case 2:
                    showPassengerInfo();
                    break;
                case 3:
                    showTickets();
                    break;
                case 4:
                    showBookmarks();
                    break;
                case 5:
                    findTickets();
                    break;
                case 0:
                    Users.getInstance().signOut();
            }
        }
    }

    private void showAccountingInfo() {
        new AccountingMenu().run();
    }

    private void showPassengerInfo() {
        new PassengerMenu().run();
    }

    private void showTickets() {
        new TicketMenu().run();
    }

    private void findTickets() {
        new SearchMenu().run();
    }

    private void showBookmarks() {
        new BookmarkMenu().run();
    }
}
