package air_tickets.globals;

import air_tickets.FlightRecord;
import air_tickets.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Anton on 12.07.2017.
 */
public class Users {

    private final static Users instance = new Users();
    private User currentUser;
    private User defaultUser = new User("Unregistered user");
    private final Map<String, User> accounts = new HashMap<>();

    private Users() {}

    public static Users getInstance() {
        return instance;
    }

    public User signIn (String login)
            throws IllegalArgumentException {
        if (!accounts.containsKey(login))
            throw new IllegalArgumentException("User not found");
        else {
            currentUser = accounts.get(login);
            return currentUser;
        }
    }

    public User signUp (String login)
            throws IllegalArgumentException {
        if (accounts.containsKey(login))
            throw new IllegalArgumentException("Login already exists");
        else {
            User newUser = new User(login);
            accounts.put(newUser.getLogin(), newUser);
            currentUser = newUser;
            return newUser;
        }
    }

    public void signOut() {
        currentUser = defaultUser;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public Map<String, User> getAccounts() {
        return accounts;
    }

    public boolean isLogged() {
        return currentUser != defaultUser;
    }
}
