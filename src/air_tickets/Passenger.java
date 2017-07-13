package air_tickets;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Anton on 10.07.2017.
 */
public class Passenger {

    private String name;
    private String surname;
    private Gender gender;
    private LocalDate birthday;
    private String passportNumber;

    public Passenger(String name, String surname, Gender gender, LocalDate birthday, String passportNumber) {
        this.name = name;
        this.surname = surname;
        this.gender = gender;
        this.birthday = birthday;
        this.passportNumber = passportNumber;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public String getPassportNumber() {
        return passportNumber;
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
