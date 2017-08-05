package air_tickets.exceptions;

/**
 * Created by Anton on 04.08.2017.
 */
public class DataIntegrityException extends RuntimeException {

    public DataIntegrityException(String message) {
        super(message);
    }
}
