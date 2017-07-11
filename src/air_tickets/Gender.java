package air_tickets;

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

    @Override
    public String toString() {
        return this.out;
    }
}
