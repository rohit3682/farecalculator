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
    private static List<BusTripSummary> completedTrips;
    private static List<BusTripSummary> cancelledTrips;
    private static List<BusTripSummary> incompleteTrips;

    @BeforeAll
    public static void setUp() throws IOException {
        FareService fareService = new FareService();
        TripSummaryService tripSummaryService = new TripSummaryService(fareService);
        CSVUtil csvUtil = new CSVUtil();
        fareService.loadFareData();
        Map<String, List<Trip>> tripData = csvUtil.readTapsData("taps.csv");
        busTripSummaryList = tripSummaryService.generateTripSummary(tripData);
        completedTrips = busTripSummaryList.stream()
                .filter(trip -> Constants.TRIP_COMPLETE.equalsIgnoreCase(trip.getStatus()))
                .toList();
        cancelledTrips = busTripSummaryList.stream()
                .filter(trip -> Constants.TRIP_CANCELLED.equalsIgnoreCase(trip.getStatus()))
                .toList();
        incompleteTrips = busTripSummaryList.stream()
                .filter(trip -> Constants.TRIP_INCOMPLETE.equalsIgnoreCase(trip.getStatus()))
                .toList();
    }

    @Test
    public void testTripSummaryCount() {
        Assertions.assertEquals(15, busTripSummaryList.size());
    }

    @Test
    public void testCompleteTripsCount() {
        Assertions.assertEquals(10, completedTrips.size());
    }

    @Test
    public void testCancelledTripsCount() {
        Assertions.assertEquals(2, cancelledTrips.size());
    }

    @Test
    public void testIncompleteTripsCount() {
        Assertions.assertEquals(3, incompleteTrips.size());
    }

    @Test
    public void testIncompleteTrips() {
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

    @Test
    public void testCompletedTripStopIds() {
        List<BusTripSummary> tempList = completedTrips.stream()
                .filter(trip -> "5500005555555559".equalsIgnoreCase(trip.getPan()))
                .toList();

        Assertions.assertEquals("Stop1", tempList.get(0).getFromStopId());
        Assertions.assertEquals("Stop2", tempList.get(0).getToStopId());

        Assertions.assertEquals("Stop2", tempList.get(1).getFromStopId());
        Assertions.assertEquals("Stop3", tempList.get(1).getToStopId());
    }

    @Test
    public void testCompletedTripBusIds() {
        List<BusTripSummary> tempList = completedTrips.stream()
                .filter(trip -> "1200001333333336".equalsIgnoreCase(trip.getPan()))
                .toList();

        Assertions.assertEquals("Bus44", tempList.get(0).getBusId());
    }

    @Test
    public void testCompletedTripsFare() {
        List<BusTripSummary> tempList = completedTrips.stream()
                .filter(trip -> "9000009999999993".equalsIgnoreCase(trip.getPan()))
                .toList();

        Assertions.assertEquals(1.93, tempList.get(0).getChargeAmt());
    }

    @Test
    public void testIncompletedTripStopIds() {
        List<BusTripSummary> tempList = incompleteTrips.stream()
                .filter(trip -> "4111111111111111".equalsIgnoreCase(trip.getPan()))
                .toList();

        Assertions.assertNull(tempList.get(0).getToStopId());
        Assertions.assertEquals("Stop3", tempList.get(0).getFromStopId());
    }

    @Test
    public void testIncompletedTripBusIds() {
        List<BusTripSummary> tempList = incompleteTrips.stream()
                .filter(trip -> "7000007777777771".equalsIgnoreCase(trip.getPan()))
                .toList();

        Assertions.assertEquals("Bus39", tempList.get(0).getBusId());
    }

    @Test
    public void testIncompletedTripsFare() {
        List<BusTripSummary> tempList = incompleteTrips.stream()
                .filter(trip -> "1400001555555558".equalsIgnoreCase(trip.getPan()))
                .toList();

        Assertions.assertEquals(9.54, tempList.get(0).getChargeAmt());
    }

    @Test
    public void testCancelledTripStopIds() {
        List<BusTripSummary> tempList = cancelledTrips.stream()
                .filter(trip -> "6000006666666660".equalsIgnoreCase(trip.getPan()))
                .toList();

        Assertions.assertEquals("Stop7", tempList.get(0).getFromStopId());
        Assertions.assertEquals("Stop7", tempList.get(0).getToStopId());
    }

    @Test
    public void testCancelledTripBusIds() {
        List<BusTripSummary> tempList = cancelledTrips.stream()
                .filter(trip -> "6000006666666660".equalsIgnoreCase(trip.getPan()))
                .toList();

        Assertions.assertEquals("Bus38", tempList.get(0).getBusId());
    }

    @Test
    public void testCancelledTripsFare() {
        List<BusTripSummary> tempList = cancelledTrips.stream()
                .filter(trip -> "4111111111111111".equalsIgnoreCase(trip.getPan()))
                .toList();

        Assertions.assertEquals(0, tempList.get(0).getChargeAmt());
    }

}
