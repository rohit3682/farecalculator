package com.littlepay.FareCalculator.service;

import com.littlepay.FareCalculator.dto.BusTripSummary;
import com.littlepay.FareCalculator.dto.Trip;
import com.littlepay.FareCalculator.util.CSVUtil;
import com.littlepay.FareCalculator.util.Constants;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class TripSummaryServiceTest {

    private static List<BusTripSummary> busTripSummaryList;

    @BeforeAll
    public static void setUp() throws IOException {
        FareService fareService = new FareService();
        TripSummaryService tripSummaryService = new TripSummaryService(fareService);
        CSVUtil csvUtil = new CSVUtil();
        fareService.loadFareData();
        Map<String, List<Trip>> tripData = csvUtil.readTripsData("taps.csv");
        busTripSummaryList = tripSummaryService.generateTripSummary(tripData);
    }

    @Test
    public void testTripSummaryCount() {
        Assertions.assertEquals(15, busTripSummaryList.size());
    }

    @Test
    public void testCompleteTripsCount() {
        List<BusTripSummary> completedTrips = busTripSummaryList.stream()
                .filter(trip -> Constants.TRIP_COMPLETE.equalsIgnoreCase(trip.getStatus()))
                .toList();

        Assertions.assertEquals(10, completedTrips.size());
    }

    @Test
    public void testCancelledTripsCount() {
        List<BusTripSummary> cancelledTrips = busTripSummaryList.stream()
                .filter(trip -> Constants.TRIP_CANCELLED.equalsIgnoreCase(trip.getStatus()))
                .toList();

        Assertions.assertEquals(2, cancelledTrips.size());
    }

    @Test
    public void testIncompleteTripsCount() {
        List<BusTripSummary> incompleteTrips = busTripSummaryList.stream()
                .filter(trip -> Constants.TRIP_INCOMPLETE.equalsIgnoreCase(trip.getStatus()))
                .toList();

        Assertions.assertEquals(3, incompleteTrips.size());
    }

}
