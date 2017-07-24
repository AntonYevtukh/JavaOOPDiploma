package air_tickets.proc_menu;

import air_tickets.Passenger;
import air_tickets.globals.Users;
import air_tickets.in_out.Utils;

/**
 * Created by Anton on 20.07.2017.
 */
public class PassengerMenu {

    public void showItems() {
        System.out.println("1. Change Passenger profile");
        System.out.println("\n0. Back");
    }

    public void run() {
        int action = -1;
        while (action != 0) {
            System.out.println(Users.getInstance().getCurrentUser().getPassenger());
            showItems();
            action = Utils.readMenuItem(1);
            if (action == 1)
                setNewPassenger();
        }
    }

    private void setNewPassenger() {
        System.out.println("Input data of new passenger profile");
        Passenger newPassenger = Passenger.readFromConsole();
        Users.getInstance().getCurrentUser().setPassenger(newPassenger);
        System.out.println("You have successfully changed new passenger data.");
    }
}
