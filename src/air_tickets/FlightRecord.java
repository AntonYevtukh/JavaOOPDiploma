package air_tickets;

import air_tickets.trash.FlightCoupon;

import java.time.LocalDate;
import java.util.*;

/**
 * Created by Anton on 09.07.2017.
 */
public class FlightRecord {

    private final String id = UUID.randomUUID().toString();
    private Flight flight;
    private LocalDate date;
    private Map<SeatClass, Long> prices = new HashMap<>();
    private Map<SeatClass, Integer> availableSeats = new HashMap<>(); //чтоб не считать каждый раз

    public FlightRecord(Flight flight, LocalDate date, Map<SeatClass, Long> prices) {
        this.flight = flight;
        this.date = date;
        this.prices = prices;
        for (SeatClass seatClass : flight.getAircraft().getSeats().keySet())
            availableSeats.put(seatClass, flight.getAircraft().getSeats().get(seatClass));
    }

    public String getId() {
        return id;
    }

    public LocalDate getDate() {
        return date;
    }

    public Flight getFlight() {
        return flight;
    }

    public Map<SeatClass, Integer> getAvailableSeats() {
        return availableSeats;
    }

    public Map<SeatClass, Long> getPrices() {
        return prices;
    }

    public String getKey() {                                        //костыль, символизирующий уникальный составной индекс
        return flight.getFlightNumber() + " " + date.toString();    //номер рейса + дата
    }

    public boolean isEnouhgSeats(Map<SeatClass, Integer> requiredSeats) {
        for (SeatClass seatClass : requiredSeats.keySet())
            if (!availableSeats.keySet().contains(seatClass) || availableSeats.get(seatClass) < requiredSeats.get(seatClass))
                return false;
        return true;
    }

    public void incrementAvailableSeats(SeatClass seatClass) {
        availableSeats.put(seatClass, availableSeats.get(seatClass) + 1);
    }

    public void decrementAvailableSeats(SeatClass seatClass) {
        availableSeats.put(seatClass, availableSeats.get(seatClass) - 1);
    }

}
    /*public List<String> bookTickets(Map<SeatClass, Integer> seatCount) {    //Забронировать билеты
        List<String> bookingIdS = new ArrayList<>();
        for (SeatClass seatClass : seatCount.keySet())                      //для каждого класса мест из заказа
            for (int i = 0; i < seatCount.get(seatClass); i++)              //повторить по количеству заказываемых мест класса
                for (String bookingId : bookingStates.keySet())             //пройтись по всем id мест и найти свободные
                    if (bookingStates.get(bookingId) == State.FREE) {       //если оно свободное,
                        bookingStates.put(bookingId, State.BOOKED);         //Забронировать
                        bookingIdS.add(bookingId);                          //Добавить в список, возвращаемый пользователю
                        break;                                              //Выйти из внутреннего цикла - свободное место найдено
                    }
        return bookingIdS;
    }

    public List<String> buyTickets(Map<SeatClass, Integer> seatCount) {     //Купить билеты сразу
        List<String> bookingIdS = new ArrayList<>();
        for (SeatClass seatClass : seatCount.keySet())                      //для каждого класса мест из заказа
            for (int i = 0; i < seatCount.get(seatClass); i++)              //повторить по количеству заказываемых мест класса
                for (String bookingId : bookingStates.keySet())             //пройтись по всем id мест и найти свободные
                    if (bookingStates.get(bookingId) == State.FREE) {       //если оно свободное,
                        bookingStates.put(bookingId, State.BOUGHT);         //купить окончательно
                        bookingIdS.add(bookingId);                          //Добавить ид посадочного купона в список, возвращаемый пользователю
                        break;                                              //Выйти из внутреннего цикла - свободное место найдено
                    }
        return bookingIdS;
    }

    public int buyBookedTickets(List<String> bookingIds) {                  //Купить забронированные билеты
        int i = 0;
        for (String userBookingId : bookingIds)                             //Для всех покупаемых билетов
            for (String flightBookingId : bookingStates.keySet())           //Пройтись по списку посадочных купонов
                if (userBookingId.equals(flightBookingId)) {                //Если ид найден в списке
                    bookingStates.put(userBookingId, State.BOUGHT);         //Обновить статус на купленный
                    i++;                                                    //увеличить счетчик подтвержденных
                    break;
                }
        if (bookingIds.size() != i)
            throw new RuntimeException("Not all booking are confirmed");
        return i;
    }

    public int unBookTickets (List<String> bookingIds) {                    //Снять бронь
        int i = 0;
        for (String userBookingId : bookingIds)                             //Для всех отменяемых бронирований
            for (String flightBookingId : bookingStates.keySet())           //Пройтись по списку посадочных купонов
                if (userBookingId.equals(flightBookingId)) {                //Если ид найден в списке
                    bookingStates.remove(flightBookingId);                  //Удалить его из списка
                    bookingStates.put(UUID.randomUUID().toString(), State.FREE); //Сгенерировать новый ид купона и добавить
                    i++;                                                    //увеличить счетчик подтвержденных
                    break;
                }
        if (bookingIds.size() != i)
            throw new RuntimeException("Not all booking are canceled");
        return i;
    }
}*/
