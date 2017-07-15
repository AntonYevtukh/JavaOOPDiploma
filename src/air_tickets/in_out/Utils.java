package air_tickets.in_out;

import air_tickets.Gender;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
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
                System.out.print("Invalid input format, please try again:\n<-");
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
        return scanner.next();
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
}
