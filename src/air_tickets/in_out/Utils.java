package air_tickets.in_out;

import air_tickets.Gender;
import air_tickets.Passenger;
import air_tickets.User;
import air_tickets.globals.Users;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

/**
 * Created by Anton on 14.07.2017.
 */
public class Utils {

    private static Scanner scanner = new Scanner(System.in).useDelimiter("\n");

    public static int readInt(int lowerBound, int upperBound, boolean showMessage) {
        clearStream();
        int i;
        System.out.print(showMessage ? "Enter integer in range <" + lowerBound + ", " + upperBound + ">\n" : "");
        do {
            while (!scanner.hasNextInt()) {
                System.out.print("Invalid input format, please try again:\n");
                scanner.next();
            }
            i = scanner.nextInt();
            if (i < lowerBound || i > upperBound)
                System.out.println("Invalid value, should be in range <" + lowerBound + ", " + upperBound +
                        ">, please, try again");
        } while (i < lowerBound || i > upperBound);
        return i;
    }

    public static int readInt(int upperBound) {
        return readInt(1, upperBound,false);
    }

    public static int readMenuItem(int menuSize) {
        return readInt(0, menuSize,false);
    }

    public static String readString() {
        clearStream();
        String result;
        do {
            result = scanner.next();
            if (result.isEmpty())
                System.out.println("Please, enter non-empty string: ");
        } while (result.isEmpty());
        return result;
    }

    public static int[] readItemNumbers(int bound) {
        clearStream();
        String itemString;
        String[] inputArray;
        Set<Integer> itemSet = new HashSet<>();
        boolean correct;
        System.out.println("Enter values in range <1, " + bound + ">, separated by space");
        do {
            correct = true;
            itemSet.clear();
            itemString = scanner.nextLine();
            inputArray = itemString.split("[ ]+");
            for (int i = 0; i < inputArray.length; i++) {
                try {
                    int temp = Integer.parseInt(inputArray[i]);
                    if (temp >= 1 && temp <= bound)
                        itemSet.add(temp);
                    else
                        correct = false;
                }
                catch (NumberFormatException exc){
                    correct = false;
                }
            }
            if (!correct)
                System.out.println("Invalid input format or value is not in range <1, " + bound + ">, please, try again");
        } while (!correct);

        int[] result = itemSet.stream().mapToInt(Integer::intValue).toArray();
        for (int i = 0; i < result.length; i++)
            result[i]--;
        return result;
    }

    public static <E> void printList(List<E> list) {
        int i = 1;
        for (E elem : list) {
            System.out.println(i++ + ".");
            System.out.println(elem);
        }
    }

    public static LocalDate readDate() {
        clearStream();
        boolean correct;
        String temp;
        LocalDate result = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        do {
            correct = true;
            temp = scanner.next();
            temp.trim();
            try {
                result = LocalDate.parse(temp, formatter);
            }
            catch (Exception exc) {
                System.out.println("Wrong format, it should match dd.mm.yy (" + LocalDate.now().format(formatter) + ")");
                correct = false;
            }
        } while (!correct);
        return result;
    }

    public static Passenger readPassengerFromConsole() {
        System.out.println("Enter passenger's data: ");
        System.out.println("Enter name: ");
        String name = Utils.readString();
        System.out.println("Enter surname: ");
        String surname = Utils.readString();
        System.out.println("Enter date of birth in format dd.mm.yyyy: ");
        LocalDate birthday = Utils.readDate();
        Gender gender = Utils.readGender();
        System.out.println("Enter passport number: ");
        String passport = Utils.readString();
        return new Passenger(name, surname, gender, birthday, passport);
    }

    public static Passenger getNewPassenger() {
        User currentUser = Users.getInstance().getCurrentUser();
        if (currentUser.getPassenger() != null) {
            System.out.println("Do you prefer to use passenger info from profile?");
            System.out.println("1. Yes\n2. No");
            int answer = Utils.readInt(2);
            if (answer == 1)
                return currentUser.getPassenger();
            else
                return Utils.readPassengerFromConsole();
        }
        else {
            System.out.println("There is no passenger associated with your profile. It will be read from console.");
            return Utils.readPassengerFromConsole();
        }
    }

    public static Gender readGender() {
        clearStream();
        boolean correct;
        int code;
        System.out.println("Enter gender code: 0 - Not known, 1 - Male, 2 - Female, 9 - Not applicable");
        do {
            correct = true;
            while (!scanner.hasNextInt()) {
                System.out.println("Invalid input, should be: 0 - Not known, 1 - Male, 2 - Female, 9 - Not applicable");
                scanner.next();
            }
            code = scanner.nextInt();
            if (!Gender.getIndexes().contains(code)) {
                System.out.println("Invalid input, should be: 0 - Not known, 1 - Male, 2 - Female, 9 - Not applicable");
                correct = false;
            }
        } while (!correct);
        return Gender.getByCode(code);
    }

    private static void clearStream() {
        try {
            System.in.skip(System.in.available());
        }
        catch (IOException exc) {

        }
    }

    public static DateTimeFormatter getDateTimeFormatter() {
        return DateTimeFormatter.ofPattern("HH:mm O");
    }
}
