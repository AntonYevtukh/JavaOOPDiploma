package air_tickets;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Anton on 10.07.2017.
 */
public enum Gender {

    NOT_KNOWN(0, "Not known"), MALE(1, "Male"), FEMALE(2, "Female"), NOT_APPLICABLE(3, "Not applicable");

    private int code;
    private String out;

    Gender(int code, String out) {
        this.code = code;
        this.out = out;
    }

    public static Gender getByCode(int code) {
        if (!getIndexes().contains(code))
            throw new IllegalArgumentException("Wrong gender code");
        else
            for (Gender gender : Gender.values())
                if (code == gender.code)
                    return gender;
        return Gender.NOT_KNOWN;
    }

    public String toString() {
        return this.out;
    }

    public static Set<Integer> getIndexes() {
        return new HashSet<>(Arrays.asList(0, 1, 2, 9));
    }
}
