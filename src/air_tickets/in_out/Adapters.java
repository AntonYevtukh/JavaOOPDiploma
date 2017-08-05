package air_tickets.in_out;
import air_tickets.globals.World;
import com.google.gson.*;

import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

class ZoneIdSerializer implements JsonSerializer<ZoneId> {
    public JsonElement serialize(ZoneId zoneId, Type type, JsonSerializationContext jsc) {
        JsonPrimitive jo = new JsonPrimitive(zoneId.toString());
        return jo;
    }
}

class ZoneIDDeserializer implements JsonDeserializer<ZoneId> {
    public ZoneId deserialize(JsonElement je, Type type, JsonDeserializationContext jdc)
            throws JsonParseException {
        ZoneId zoneId = ZoneId.of(je.toString().replace("\"",""));
        return zoneId;
    }
}

class LocalDateSerializer implements JsonSerializer<LocalDate> {

    private DateTimeFormatter formatter;

    public LocalDateSerializer(DateTimeFormatter formatter) {
        this.formatter = formatter;
    }

    public JsonElement serialize(LocalDate localDate, Type type, JsonSerializationContext jsc) {
        JsonPrimitive jo = new JsonPrimitive(localDate.format(formatter));
        return jo;
    }
}

class LocalDateDeserializer implements JsonDeserializer<LocalDate> {

    private DateTimeFormatter formatter;

    public LocalDateDeserializer(DateTimeFormatter formatter) {
        this.formatter = formatter;
    }

    public LocalDate deserialize(JsonElement je, Type type, JsonDeserializationContext jdc)
            throws JsonParseException {

        String localDateString = je.toString().replace("\"","");
        return LocalDate.parse(localDateString, formatter);
    }
}

class LocalTimeSerializer implements JsonSerializer<LocalTime> {

    private DateTimeFormatter formatter;

    public LocalTimeSerializer(DateTimeFormatter formatter) {
        this.formatter = formatter;
    }

    public JsonElement serialize(LocalTime localTime, Type type, JsonSerializationContext jsc) {
        JsonPrimitive jo = new JsonPrimitive(localTime.format(formatter));
        return jo;
    }
}

class LocalTimeDeserializer implements JsonDeserializer<LocalTime> {

    private DateTimeFormatter formatter;

    public LocalTimeDeserializer(DateTimeFormatter formatter) {
        this.formatter = formatter;
    }

    public LocalTime deserialize(JsonElement je, Type type, JsonDeserializationContext jdc)
            throws JsonParseException {

        String localTimeString = je.toString().replace("\"","");
        return LocalTime.parse(localTimeString, formatter);
    }
}