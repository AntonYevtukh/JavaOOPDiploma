package air_tickets.tariffs;

import java.util.Map;

/**
 * Created by Anton on 12.07.2017.
 */
public class Tariffs {

    private Map<TariffType, Tariff> tarrifs;
    private static Tariffs instance = new Tariffs();

    private Tariffs() {

    }

    public static Tariffs getInstance() {
        return instance;
    }

    public Tariff getTariffByType(TariffType type) {
        if (tarrifs.containsKey(type))
            return tarrifs.get(type);
        else
            throw new IllegalArgumentException("Invalid tariff type");
    }
}
