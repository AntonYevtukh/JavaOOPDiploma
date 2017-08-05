package air_tickets;

import air_tickets.globals.Users;
import air_tickets.in_out.Utils;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Anton on 10.07.2017.
 */
public class Passenger  implements Serializable {

    private final String name;
    private final String surname;
    private final Gender gender;
    private final LocalDate birthday;
    private final String passportNumber;

    public Passenger(String name, String surname, Gender gender, LocalDate birthday, String passportNumber) {
        this.name = name;
        this.surname = surname;
        this.gender = gender;
        this.birthday = birthday;
        this.passportNumber = passportNumber;
    }

    public String getFullName() {
        return name + " " + surname;
    }

    public String toString() {
        String result = "Passenger info:";
        result += "\n--------------------------------------------------------------------------\n";
        result += "Name: " + name + ", Surname: " + surname + "\n";
        result += "Date of birth: " + birthday.toString() + ", Gender: " + gender.toString() + "\n";
        result += "Passport number: " + passportNumber;
        result += "\n--------------------------------------------------------------------------\n";
        return result;
    }
}
