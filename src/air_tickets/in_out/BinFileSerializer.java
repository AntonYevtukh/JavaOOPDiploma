package air_tickets.in_out;

import java.io.*;

/**
 * Created by Anton on 03.08.2017.
 */
public class BinFileSerializer implements Serializer {

    public <T extends Serializable> void serialize(T object, String fileame)
            throws IOException {
        try(ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(fileame))) {
            objectOutputStream.writeObject(object);
        }
    }

    public <T extends Serializable> T deserialize(Class<T> classToken, String fileame)
            throws IOException, ClassNotFoundException {
        try(ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(fileame))) {
            return (T)objectInputStream.readObject();
        }
    }
}
