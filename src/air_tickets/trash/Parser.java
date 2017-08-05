package air_tickets.trash;

/**
 * Created by Anton on 04.08.2017.
 */
public interface Parser<T> {
    //T parse(String lineToParse);
    String toOutputString(T object);
}
