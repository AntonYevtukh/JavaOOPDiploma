package air_tickets.globals;

import air_tickets.Bookmark;
import air_tickets.Ticket;
import air_tickets.User;
import air_tickets.exceptions.DataIntegrityException;

import java.io.Serializable;
import java.util.*;

/**
 * Created by Anton on 12.07.2017.
 */
public class Users implements Serializable {

    private static Users instance;
    private User defaultUser = new User("Unregistered user");
    private User currentUser = defaultUser;
    private final Map<String, User> accounts = new HashMap<>();

    private Users() {}

    public static Users getInstance() {
        if (instance == null)
            instance = new Users();
        return instance;
    }

    public boolean setAsInstance() {
        if (instance != null) {
            return false;
        }
        else {
            if (!checkFlightIDInUsers())
                throw new DataIntegrityException("Users info uses flight records, that not present in Schedule");
            instance = this;
            return true;
        }
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

    public boolean isLogged() {
        return currentUser != defaultUser;
    }

    public String allUserStrings() {
        StringJoiner userJoiner = new StringJoiner("\n----------------------------------------\n");
        for (String login : accounts.keySet())
            userJoiner.add(login);
        return userJoiner.toString();
    }

    private boolean checkFlightIDInUsers() {

        for(User user : getInstance().accounts.values()) {
            for (Ticket ticket : user.getTickets())
                if (ticket.getFlightRecord() == null)
                    return false;
            for (Bookmark bookmark : user.getBookmarks())
                if (bookmark.getFlightRecord() == null)
                    return false;
        }
        return true;
    }

    /*private Object readResolve() {
        if (setAsInstance()) {
            System.err.println("Users instance successfully loaded from the file.");
        }
        else {
            System.err.println("Users instance already exists, nothing to load.");
        }
        return instance;
    }*/
}
