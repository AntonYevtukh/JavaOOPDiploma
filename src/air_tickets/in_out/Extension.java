package air_tickets.in_out;

/**
 * Created by Anton on 05.08.2017.
 */
public enum Extension {
    DAT(new BinFileSerializer()), JSON(new JsonMarshaller());

    private Serializer serializer;

    Extension(Serializer serializer) {
        this.serializer = serializer;
    }

    public Serializer getSerializer() {
        return serializer;
    }

    public String toString() {
        return super.toString().toLowerCase();
    }
}
