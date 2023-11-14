package filterTest;

import com.greednine.testing.filter.Filter;
import com.greednine.testing.filter.FilterImpl;
import com.greednine.testing.model.Flight;
import com.greednine.testing.model.Segment;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class FilterTest {

    Filter filter = new FilterImpl();

    // Тест проверяет метод, возвращающий список без рейсов до настоящего времени
    // (В тесте нет рейсов где осуществляются вылеты после текущего времени.
    // Ожидаемый результат - тест возвращает пустой список)
    @Test
    public void testFilterListWithoutFlightsUpToThePresentTimeNoFlightsAfterCurrentTimeReturnsEmptyList() {
        Flight flight1 = new Flight(Arrays.asList(
                new Segment(LocalDateTime.parse("2022-01-01T10:00"), LocalDateTime.parse("2022-01-01T12:00"))
        ));
        Flight flight2 = new Flight(Arrays.asList(
                new Segment(LocalDateTime.parse("2022-01-01T14:00"), LocalDateTime.parse("2022-01-01T16:00"))
        ));
        List<Flight> flights = Arrays.asList(flight1, flight2);

        List<Flight> result = filter.filterListWithoutFlightsUpToThePresentTime(flights);

        Assertions.assertEquals(0, result.size());
    }

    // Тест проверяет метод, возвращающий список без рейсов до настоящего времени
    // (В тесте, в список добавлены рейсы где осуществляются вылеты после текущего времени.
    // Ожидаемый результат - список, идентичный входному)
    @Test
    public void testFilterListWithoutFlightsUpToThePresentTimeAllFlightsAfterCurrentTimeReturnsSameList() {
        Flight flight1 = new Flight(Arrays.asList(
                new Segment(LocalDateTime.parse("2023-11-16T10:00"), LocalDateTime.parse("2023-11-16T12:00"))
        ));
        Flight flight2 = new Flight(Arrays.asList(
                new Segment(LocalDateTime.parse("2023-11-16T14:00"), LocalDateTime.parse("2023-11-16T16:00"))
        ));
        List<Flight> flights = Arrays.asList(flight1, flight2);

        List<Flight> result = filter.filterListWithoutFlightsUpToThePresentTime(flights);

        Assertions.assertEquals(flights, result);
    }

    // Тест проверяет метод, возвращающий список без рейсов до настоящего времени
    // (В тесте, в список добавлены рейсы где осуществляются вылеты после и до текущего времени.
    // Ожидаемый результат - список, содержащий только полеты с сегментами после текущего времени.)
    @Test
    public void testFilterListWithoutFlightsUpToThePresentTimeSomeFlightsAfterCurrentTimeReturnsFilteredList() {
        Flight flight1 = new Flight(Arrays.asList(
                new Segment(LocalDateTime.parse("2022-01-01T10:00"), LocalDateTime.parse("2022-01-01T12:00"))
        ));
        Flight flight2 = new Flight(Arrays.asList(
                new Segment(LocalDateTime.parse("2022-11-16T14:00"), LocalDateTime.parse("2023-11-16T16:00")),
                new Segment(LocalDateTime.parse("2023-11-16T18:00"), LocalDateTime.parse("2023-11-16T20:00"))
        ));
        Flight flight3 = new Flight(Arrays.asList(
                new Segment(LocalDateTime.parse("2022-03-01T10:00"), LocalDateTime.parse("2022-03-01T12:00")),
                new Segment(LocalDateTime.parse("2022-03-01T14:00"), LocalDateTime.parse("2022-03-01T16:00"))
        ));
        List<Flight> flights = Arrays.asList(flight1, flight2, flight3);
        List<Flight> expected = Arrays.asList(flight2);

        List<Flight> result = filter.filterListWithoutFlightsUpToThePresentTime(flights);

        Assertions.assertEquals(expected, result);
    }

    // Тест проверяет метод, возвращающий список перелетов без без сегментов с датой прилёта раньше даты вылета
    // (В тесте, в список добавлены рейсы где у сегментов дата прибытия праньше даты вылета
    // Ожидаемый результат - тест возвращает пустой список)
    @Test
    public void testFilterListOfFlightsWithoutArrivalDateBeforeDepartureDateNoSegmentsWithArrivalBeforeDepartureReturnsEmptyList() {
        Flight flight1 = new Flight(Arrays.asList(
                new Segment(LocalDateTime.parse("2022-11-10T10:00"), LocalDateTime.parse("2022-11-10T09:00")),
                new Segment(LocalDateTime.parse("2022-11-10T14:00"), LocalDateTime.parse("2022-11-10T13:00"))
        ));
        Flight flight2 = new Flight(Arrays.asList(
                new Segment(LocalDateTime.parse("2022-11-10T18:00"), LocalDateTime.parse("2022-11-10T16:00"))
        ));
        List<Flight> flights = Arrays.asList(flight1, flight2);

        List<Flight> result = filter.filterListOfFlightsWithoutArrivalDateBeforeDepartureDate(flights);

        Assertions.assertEquals(0, result.size());
    }

    // Тест проверяет метод, возвращающий список перелетов без без сегментов с датой прилёта раньше даты вылета
    // (В тесте, в список добавлены рейсы где у сегментов дата прибытия позже даты вылета
    // Ожидаемый результат - список, идентичный входному.)
    @Test
    public void testFilterListOfFlightsWithoutArrivalDateBeforeDepartureDateAllSegmentsWithArrivalAfterDepartureReturnsSameList() {
        Flight flight1 = new Flight(Arrays.asList(
                new Segment(LocalDateTime.parse("2022-11-16T10:00"), LocalDateTime.parse("2022-11-16T12:00")),
                new Segment(LocalDateTime.parse("2022-11-16T14:00"), LocalDateTime.parse("2022-11-16T16:00"))
        ));
        Flight flight2 = new Flight(Arrays.asList(
                new Segment(LocalDateTime.parse("2022-11-16T18:00"), LocalDateTime.parse("2022-11-16T20:00")),
                new Segment(LocalDateTime.parse("2022-11-16T22:00"), LocalDateTime.parse("2022-11-16T23:00"))
        ));
        List<Flight> flights = Arrays.asList(flight1, flight2);

        List<Flight> result = filter.filterListOfFlightsWithoutArrivalDateBeforeDepartureDate(flights);

        Assertions.assertEquals(flights, result);
    }

    // Тест проверяет метод, возвращающий список перелетов без без сегментов с датой прилёта раньше даты вылета
    // (В тесте, в список добавлены рейсы где у сегментов дата прибытия позже даты вылета
    // Ожидаемый результат - список, содержащий только полеты без сегментов с датой прилёта раньше даты вылета)
    @Test
    public void testFilterListOfFlightsWithoutArrivalDateBeforeDepartureDateSomeSegmentsWithArrivalBeforeDepartureReturnsFilteredList() {
        Flight flight1 = new Flight(Arrays.asList(
                new Segment(LocalDateTime.parse("2022-01-01T10:00"), LocalDateTime.parse("2022-01-01T12:00")),
                new Segment(LocalDateTime.parse("2022-01-01T14:00"), LocalDateTime.parse("2022-01-01T16:00"))
        ));
        Flight flight2 = new Flight(Arrays.asList(
                new Segment(LocalDateTime.parse("2022-01-01T08:00"), LocalDateTime.parse("2022-01-01T07:00"))
        ));
        List<Flight> flights = Arrays.asList(flight1, flight2);

        List<Flight> result = filter.filterListOfFlightsWithoutArrivalDateBeforeDepartureDate(flights);

        Assertions.assertEquals(1, result.size());
        Assertions.assertEquals(flight1, result.get(0));
    }

    // Тест проверяет метод, возвращающий список перелетов где отсутствуют перелеты у которых общее время проведенное на земле превышает 2 часа
    // (В тесте, в список добавлены рейсы где разница во времени между датой прибытия и даты вылета не превышает 2 часа
    // Ожидаемый результат - список, содржащий рейсы где общее время проведенное на земле не превышает 2 часа)
    @Test
    public void testFilterListFlightsWhereTheTotalTravelTimeIsLessThanTwoHoursFlightsWithTotalTimeLessThanTwoHoursReturnsFilteredList() {
        Flight flight1 = new Flight(Arrays.asList(
                new Segment(LocalDateTime.parse("2022-01-01T10:00"), LocalDateTime.parse("2022-01-01T12:00")),
                new Segment(LocalDateTime.parse("2022-01-01T14:00"), LocalDateTime.parse("2022-01-01T16:00"))
        ));
        Flight flight2 = new Flight(Arrays.asList(
                new Segment(LocalDateTime.parse("2022-01-01T18:00"), LocalDateTime.parse("2022-01-01T19:00")),
                new Segment(LocalDateTime.parse("2022-01-01T20:00"), LocalDateTime.parse("2022-01-01T21:00")),
                new Segment(LocalDateTime.parse("2022-01-01T22:00"), LocalDateTime.parse("2022-01-01T23:00"))
        ));
        List<Flight> flights = Arrays.asList(flight1, flight2);

        List<Flight> result = filter.filterListFlightsWhereTheTotalTravelTimeIsLessThanTwoHours(flights);

        Assertions.assertEquals(2, result.size());
        Assertions.assertTrue(result.contains(flight1));
        Assertions.assertTrue(result.contains(flight2));
    }

    // Тест проверяет метод, возвращающий список перелетов где отсутствуют перелеты у которых общее время проведенное на земле превышает 2 часа
    // (В тесте, в список добавлены рейсы где разница во времени между датой прибытия и даты вылета превышает и не превышает 2 часа
    // Ожидаемый результат - список, содржащий рейсы где общее время проведенное на земле не превышает 2 часа)
    @Test
    public void testFilterListFlightsWhereTheTotalTravelTimeIsLessThanTwoHoursFlightsWithTotalTimeGreaterThanTwoHoursReturnsFilteredListWithoutFlight() {
        Flight flight1 = new Flight(Arrays.asList(
                new Segment(LocalDateTime.parse("2022-01-01T10:00"), LocalDateTime.parse("2022-01-01T12:00")),
                new Segment(LocalDateTime.parse("2022-01-01T14:00"), LocalDateTime.parse("2022-01-01T16:00"))
        ));
        Flight flight2 = new Flight(Arrays.asList(
                new Segment(LocalDateTime.parse("2022-01-01T18:00"), LocalDateTime.parse("2022-01-01T20:00")),
                new Segment(LocalDateTime.parse("2022-01-01T22:00"), LocalDateTime.parse("2022-01-01T23:00")),
                new Segment(LocalDateTime.parse("2022-01-02T01:00"), LocalDateTime.parse("2022-01-02T02:00"))
        ));
        List<Flight> flights = Arrays.asList(flight1, flight2);

        List<Flight> result = filter.filterListFlightsWhereTheTotalTravelTimeIsLessThanTwoHours(flights);

        Assertions.assertEquals(1, result.size());
        Assertions.assertTrue(result.contains(flight1));
        Assertions.assertFalse(result.contains(flight2));
    }
}