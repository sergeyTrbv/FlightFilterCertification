package com.greednine.testing.filter;

import com.greednine.testing.model.Flight;

import com.greednine.testing.filter.Filter;
import com.greednine.testing.model.Segment;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * *Класс предназначен для применения фильтров на список перелетов
 */
public class FilterImpl implements Filter {
    LocalDateTime currentDateTime = LocalDateTime.now();

    /**
     * Метод возвращающий список перелетов без рейсов где вылет осуществляется до текущего времени
     */
    @Override
    public List<Flight> filterListWithoutFlightsUpToThePresentTime(List<Flight> flights) {
        if (flights == null) {
            throw new IllegalArgumentException("Список полётов не может быть Null");
        }
        List<Flight> filteredFlights = new ArrayList<>();
        for (Flight flight : flights) {
            List<Segment> segments = flight.getSegments();
            if (segments == null) {
                throw new IllegalArgumentException("Список сегментов не может быть Null");
            }
            boolean isSegmentAfterCurrentTime = false;
            for (Segment segment : segments) {
                LocalDateTime departureDate = segment.getDepartureDate();
                if (departureDate.isAfter(currentDateTime)) {
                    isSegmentAfterCurrentTime = true;
                    break;
                }
            }
            if (isSegmentAfterCurrentTime) {
                filteredFlights.add(flight);
            }
        }
        return filteredFlights;
    }

    /**
     * Метод возвращающий список перелетов без сегментов с датой прилёта раньше даты вылета
     */
    @Override
    public List<Flight> filterListOfFlightsWithoutArrivalDateBeforeDepartureDate(List<Flight> flights) {
        if (flights == null) {
            throw new IllegalArgumentException("Список полётов не может быть Null");
        }
        List<Flight> filteredFlights = new ArrayList<>();
        for (Flight flight : flights) {
            List<Segment> segments = flight.getSegments();
            if (segments == null) {
                throw new IllegalArgumentException("Список сегментов не может быть Null");
            }
            boolean isSegmentBeforeCurrentTime = false;
            for (Segment segment : segments) {
                LocalDateTime arrivalDate = segment.getArrivalDate();
                LocalDateTime departureDate = segment.getDepartureDate();
                if (departureDate.isBefore(arrivalDate)) {
                    isSegmentBeforeCurrentTime = true;
                    break;
                }
            }
            if (isSegmentBeforeCurrentTime) {
                filteredFlights.add(flight);
            }
        }
        return filteredFlights;
    }

    /**
     * Метод возвращающий список перелетов, где отсутствуют перелеты у которых
     * общее время проведенное на земле превышает 2 часа
     */
    @Override
    public List<Flight> filterListFlightsWhereTheTotalTravelTimeIsLessThanTwoHours(List<Flight> flights) {
        if (flights == null) {
            throw new IllegalArgumentException("Список полётов не может быть Null");
        }
        List<Flight> filteredFlights = new ArrayList<>();
        for (Flight flight : flights) {
            boolean addFlight = true;
            List<Segment> segments = flight.getSegments();
            if (segments == null) {
                throw new IllegalArgumentException("Список сегментов не может быть Null");
            }
            int totalTimeOnEarth = 0;

            for (int i = 0; i < segments.size() - 1; i++) {
                LocalDateTime currentSegmentArrival = segments.get(i).getArrivalDate();
                LocalDateTime nextSegmentDeparture = segments.get(i + 1).getDepartureDate();

                int timeOnEarth = (int) java.time.Duration.between(currentSegmentArrival, nextSegmentDeparture).toHours();
                totalTimeOnEarth += timeOnEarth;

                if (totalTimeOnEarth > 2) {
                    addFlight = false;
                    break;
                }
            }
            if (addFlight) {
                filteredFlights.add(flight);
            }
        }
        return filteredFlights;
    }
}