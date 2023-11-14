package com.greednine.testing.filter;


import com.greednine.testing.model.Flight;

import java.util.List;

/**
 * Интерфейс, содержащий методы фильтрующие перелёты
 */
public interface Filter {
    List<Flight> filterListWithoutFlightsUpToThePresentTime(List<Flight> flights);

    List<Flight> filterListOfFlightsWithoutArrivalDateBeforeDepartureDate(List<Flight> flights);

    List<Flight> filterListFlightsWhereTheTotalTravelTimeIsLessThanTwoHours(List<Flight> flights);
}
