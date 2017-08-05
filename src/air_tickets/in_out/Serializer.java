package air_tickets.in_out;

import java.io.IOException;
import java.io.Serializable;

/**
 * Created by Anton on 05.08.2017.
 */
public interface Serializer {

    <T extends Serializable> void serialize(T object, String fileame) throws IOException;
    <T extends Serializable> T deserialize(Class<T> classToken, String fileame) throws IOException, ClassNotFoundException;
}
