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
    private final Map<String, User> accounts = new HashMap<>();

    private Users() {}

    public static Users getInstance() {
        return instance;
    }

    public User SignIn (String login)
            throws IllegalArgumentException {
        if (!accounts.containsKey(login))
            throw new IllegalArgumentException("User not found");
        else return accounts.get(login);
    }

    public User SignUp (User newUser)
            throws IllegalArgumentException {
        if (!accounts.containsKey(newUser.getLogin()))
            throw new IllegalArgumentException("Login already exists");
        else
            accounts.put(newUser.getLogin(), newUser);
        return newUser;
    }
}
