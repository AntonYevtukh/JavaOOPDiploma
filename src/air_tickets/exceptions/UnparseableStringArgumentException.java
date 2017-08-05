package air_tickets.exceptions;

/**
 * Created by Anton on 04.08.2017.
 */
public class UnparseableStringArgumentException extends IllegalArgumentException {

    public UnparseableStringArgumentException(String message) {
        super(message);
    }
}
