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

    @Test
    public void testIncompleteTrips() {
        List<BusTripSummary> incompleteTrips = busTripSummaryList.stream()
                .filter(trip -> Constants.TRIP_INCOMPLETE.equalsIgnoreCase(trip.getStatus()))
                .toList();

        List<BusTripSummary> tempList = incompleteTrips.stream()
                .filter(trip -> "1400001555555558".equalsIgnoreCase(trip.getPan()))
                .toList();

        Assertions.assertEquals(1, tempList.size());

        tempList = incompleteTrips.stream()
                .filter(trip -> "4111111111111111".equalsIgnoreCase(trip.getPan()))
                .toList();

        Assertions.assertEquals(1, tempList.size());

        tempList = incompleteTrips.stream()
                .filter(trip -> "7000007777777771".equalsIgnoreCase(trip.getPan()))
                .toList();

        Assertions.assertEquals(1, tempList.size());
    }

    @Test
    public void testCancelledTrips() {
        List<BusTripSummary> cancelledTrips = busTripSummaryList.stream()
                .filter(trip -> Constants.TRIP_CANCELLED.equalsIgnoreCase(trip.getStatus()))
                .toList();

        List<BusTripSummary> tempList = cancelledTrips.stream()
                .filter(trip -> "4111111111111111".equalsIgnoreCase(trip.getPan()))
                .toList();

        Assertions.assertEquals(1, tempList.size());

        tempList = cancelledTrips.stream()
                .filter(trip -> "6000006666666660".equalsIgnoreCase(trip.getPan()))
                .toList();

        Assertions.assertEquals(1, tempList.size());
    }

    @Test
    public void testCompleteTrips() {
        List<BusTripSummary> completedTrips = busTripSummaryList.stream()
                .filter(trip -> Constants.TRIP_COMPLETE.equalsIgnoreCase(trip.getStatus()))
                .toList();

        List<BusTripSummary> tempList = completedTrips.stream()
                .filter(trip -> "5500005555555559".equalsIgnoreCase(trip.getPan()))
                .toList();

        Assertions.assertEquals(2, tempList.size());

        tempList = completedTrips.stream()
                .filter(trip -> "6000006666666660".equalsIgnoreCase(trip.getPan()))
                .toList();

        Assertions.assertEquals(1, tempList.size());

        tempList = completedTrips.stream()
                .filter(trip -> "1200001333333336".equalsIgnoreCase(trip.getPan()))
                .toList();

        Assertions.assertEquals(1, tempList.size());

        tempList = completedTrips.stream()
                .filter(trip -> "1400001555555558".equalsIgnoreCase(trip.getPan()))
                .toList();

        Assertions.assertEquals(1, tempList.size());

        tempList = completedTrips.stream()
                .filter(trip -> "1000001111111114".equalsIgnoreCase(trip.getPan()))
                .toList();

        Assertions.assertEquals(1, tempList.size());

        tempList = completedTrips.stream()
                .filter(trip -> "8000008888888882".equalsIgnoreCase(trip.getPan()))
                .toList();

        Assertions.assertEquals(1, tempList.size());

        tempList = completedTrips.stream()
                .filter(trip -> "9000009999999993".equalsIgnoreCase(trip.getPan()))
                .toList();

        Assertions.assertEquals(1, tempList.size());

        tempList = completedTrips.stream()
                .filter(trip -> "1100001222222225".equalsIgnoreCase(trip.getPan()))
                .toList();

        Assertions.assertEquals(1, tempList.size());

        tempList = completedTrips.stream()
                .filter(trip -> "1300001444444447".equalsIgnoreCase(trip.getPan()))
                .toList();

        Assertions.assertEquals(1, tempList.size());




    }

}
