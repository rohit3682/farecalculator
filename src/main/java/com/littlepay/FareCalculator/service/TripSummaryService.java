package com.littlepay.FareCalculator.service;

import com.littlepay.FareCalculator.dto.BusTripSummary;
import com.littlepay.FareCalculator.dto.Trip;
import com.littlepay.FareCalculator.dto.TripSummary;
import com.littlepay.FareCalculator.util.Constants;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class TripSummaryService {

    @Value("${fare.stop1.stop2}")
    private double fareStop1Stop2;

    @Value("${fare.stop2.stop3}")
    private double fareStop2Stop3;

    @Value("${fare.stop3.stop1}")
    private double fareStop3Stop1;

    public List<TripSummary> generateTripSummary(Map<String, List<Trip>> tripData) {
        List<TripSummary> tripSummaryList = new ArrayList<>();

        tripData.forEach((k, v)-> {
            boolean tapOn = false;
            TripSummary busTripSummary = new BusTripSummary();
            for(Trip trip : v) {
                if(trip.getTapType().equalsIgnoreCase(Constants.TAP_ON)) {
                    if(!tapOn) {
                        // It's a new trip, populate the trip start details
                        busTripSummary = new BusTripSummary();
                        busTripSummary.setStarted(trip.getDateTimeUtc());
                        busTripSummary.setFromStopId(trip.getStopId());
                        tapOn = true;
                    } else {
                        // We are seeing TAP ON followed by a TAP ON
                        // customer has forgotten to TAP OFF the previous trip.
                        // Write it to trip summary as INCOMPLETE trip &
                        // populate start details for new trip
                        busTripSummary.setFinished(null);
                        busTripSummary.setDurationSeconds(0);
                        busTripSummary.setToStopId(null);
//                        busTripSummary.setChargeAmt(calculateTripCost(busTripSummary.getFromStopId(), ));

                    }
                }
            }
        }
        );

        return tripSummaryList;
    }

    private double calculateTripCost(String fromStopId, String toStopId) {
        if(null == fromStopId || null == toStopId)
            // incomplete trip, get max fare
            return getMaxFare(null == fromStopId ? toStopId : fromStopId);

        if(fromStopId.equalsIgnoreCase(toStopId))
            // customer did not commute.
            return 0;

        if((fromStopId.equalsIgnoreCase(Constants.STOP_1) || fromStopId.equalsIgnoreCase(Constants.STOP_2)) &&
        toStopId.equalsIgnoreCase(Constants.STOP_1) || toStopId.equalsIgnoreCase(Constants.STOP_2))
            return fareStop1Stop2;

        if((fromStopId.equalsIgnoreCase(Constants.STOP_2) || fromStopId.equalsIgnoreCase(Constants.STOP_3)) &&
                toStopId.equalsIgnoreCase(Constants.STOP_2) || toStopId.equalsIgnoreCase(Constants.STOP_3))
            return fareStop2Stop3;

        if((fromStopId.equalsIgnoreCase(Constants.STOP_3) || fromStopId.equalsIgnoreCase(Constants.STOP_1)) &&
                toStopId.equalsIgnoreCase(Constants.STOP_3) || toStopId.equalsIgnoreCase(Constants.STOP_1))
            return fareStop3Stop1;

        return 0;
    }

    private double getMaxFare(String stopId) {
        switch (stopId) {
            case Constants.STOP_1 -> {
                return Math.max(fareStop1Stop2, fareStop3Stop1);
            }
            case Constants.STOP_2 -> {
                return Math.max(fareStop1Stop2, fareStop2Stop3);
            }
            case Constants.STOP_3 -> {
                return Math.max(fareStop1Stop2, fareStop2Stop3);
            }
            default -> {
                return 0;
            }
        }
    }
}
