package com.greednine.testing;

import com.greednine.testing.builder.FlightBuilder;
import com.greednine.testing.filter.Filter;
import com.greednine.testing.filter.FilterImpl;
import com.greednine.testing.model.Flight;

import java.time.LocalDateTime;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        List<Flight> listFlight = FlightBuilder.createFlights();
        Filter filter = new FilterImpl();

        System.out.println("Время сейчас: " + LocalDateTime.now());

        System.out.println("Список всех перелетов:");
        showTConsoleAListOfFlights(listFlight);

        System.out.println("Список перелетов без вылетов до текущего момента времени:");
        showTConsoleAListOfFlights(filter.filterListWithoutFlightsUpToThePresentTime(listFlight));

        System.out.println("Список всех перелетов без сегментов с датой прилета раньше даты вылета");
        showTConsoleAListOfFlights(filter.filterListOfFlightsWithoutArrivalDateBeforeDepartureDate(listFlight));

        System.out.println("Список всех перелетов где общее время, проведенное на земле не превышает 2-х часов");
        showTConsoleAListOfFlights(filter.filterListFlightsWhereTheTotalTravelTimeIsLessThanTwoHours(listFlight));

        System.out.println("Список всех перелетов где общее время, проведенное на земле не превышает 2-х часов и без вылетов до текущего момента времени");
        showTConsoleAListOfFlights(filter.filterListWithoutFlightsUpToThePresentTime(
                filter.filterListFlightsWhereTheTotalTravelTimeIsLessThanTwoHours(listFlight)));
    }


    public static void showTConsoleAListOfFlights(List<Flight> flights) {
        if (flights == null) {
            throw new IllegalArgumentException("Список полетов пуст");
        }
        for (Flight flight : flights) {
            System.out.println(flight.getSegments());
        }
    }
}