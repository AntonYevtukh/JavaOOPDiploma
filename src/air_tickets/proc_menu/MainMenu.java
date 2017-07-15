package air_tickets.proc_menu;

import air_tickets.User;
import air_tickets.globals.Users;
import air_tickets.in_out.Utils;

import java.util.function.Consumer;
import java.util.function.Function;

/**
 * Created by Anton on 14.07.2017.
 */
public class MainMenu {

    public void showItems() {
        System.out.println("Please, select action:");
        System.out.println("1. Sign in");
        System.out.println("2. Sign up");
        System.out.println("3. Find tickets");
        System.out.println("\n0. Exit");
    }

    public void run() {
        int action = -1;
        while (action != 0) {
            showItems();
            action = Utils.readMenuItem(2);
            switch (action) {
                case 1:
                    sign(Users.getInstance()::signIn, "your");
                    break;
                case 2:
                    sign(Users.getInstance()::signUp, "new");
                    break;
                case 3:
                    break;
            }
        }
    }

    private void sign(Function<String, User> function, String whose) {
        System.out.println("Enter " + whose + " login or type \"0\" to exit:");
        String login;
        boolean success = false;
        while (!success) {
            success = true;
            login = Utils.readString();
            if (login.trim().equals("0"))
                return;
            try {
                function.apply(login);
            }
            catch (IllegalArgumentException exc) {
                System.out.println(exc.getMessage() + ", please, try again or type \"0\" to exit:");
                success = false;
            }
        }
        new UserMenu().run();
    }
}
