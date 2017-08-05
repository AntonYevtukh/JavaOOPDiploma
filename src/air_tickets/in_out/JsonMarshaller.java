package air_tickets.in_out;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.StringJoiner;

/**
 * Created by Anton on 05.08.2017.
 */
public class JsonMarshaller implements Serializer {

    private static final Gson GSON;
    static {
        GsonBuilder gsonBuilder = new GsonBuilder().setPrettyPrinting();
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
        gsonBuilder.registerTypeAdapter(ZoneId.class, new ZoneIdSerializer());
        gsonBuilder.registerTypeAdapter(ZoneId.class, new ZoneIDDeserializer());
        gsonBuilder.registerTypeAdapter(LocalDate.class, new LocalDateSerializer(dateFormatter));
        gsonBuilder.registerTypeAdapter(LocalDate.class, new LocalDateDeserializer(dateFormatter));
        gsonBuilder.registerTypeAdapter(LocalTime.class, new LocalTimeSerializer(timeFormatter));
        gsonBuilder.registerTypeAdapter(LocalTime.class, new LocalTimeDeserializer(timeFormatter));
        GSON = gsonBuilder.create();
    }

    public <T extends Serializable> void serialize(T object, String fileName) throws IOException {

        String jsonString = GSON.toJson(object);
        try (PrintWriter printWriter = new PrintWriter(fileName)) {
            printWriter.println(jsonString);
        }
    }

    public <T extends Serializable> T deserialize(Class<T> classToken, String fileName) throws IOException, ClassNotFoundException {

        String jsonString;
        StringJoiner joiner = new StringJoiner("\n");
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName))) {
            while ((jsonString = bufferedReader.readLine()) != null)
                joiner.add(jsonString);
        }
        T object = GSON.fromJson(joiner.toString(), classToken);
        return object;
    }
}
