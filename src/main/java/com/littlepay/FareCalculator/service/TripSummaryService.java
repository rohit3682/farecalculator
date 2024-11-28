package com.littlepay.FareCalculator.service;

import com.littlepay.FareCalculator.dto.BusTripSummary;
import com.littlepay.FareCalculator.dto.Trip;
import com.littlepay.FareCalculator.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class TripSummaryService {

    @Autowired
    private FareService fareService;

    public List<BusTripSummary> generateTripSummary(Map<String, List<Trip>> tripData) {
        List<BusTripSummary> tripSummaryList = new ArrayList<>();

        tripData.forEach((k, v)-> {
            boolean tapOn = false;
            BusTripSummary busTripSummary = new BusTripSummary();
            for(Trip trip : v) {
                if(trip.getTapType().equalsIgnoreCase(Constants.TAP_ON)) {
                    if(!tapOn) {
                        // It's a new trip, populate the trip start details
                        busTripSummary = createBusTripSummary(trip);
                        tapOn = true;
                    } else {
                        // We are seeing TAP ON followed by a TAP ON
                        // customer has forgotten to TAP OFF the previous trip.
                        // Write it to trip summary as INCOMPLETE trip
                        busTripSummary.setFinished(null);
                        busTripSummary.setDurationSeconds(0);
                        busTripSummary.setToStopId(null);
                        busTripSummary.setChargeAmt(calculateTripCost(busTripSummary.getFromStopId(), null));
                        busTripSummary.setStatus(Constants.TRIP_INCOMPLETE);
                        tripSummaryList.add(busTripSummary);

                        // populate start details for new trip
                        busTripSummary = createBusTripSummary(trip);
                    }
                } else if (trip.getTapType().equalsIgnoreCase(Constants.TAP_OFF)) {
                    if(tapOn) {
                        // Ideal scenario, TAP ON followed by TAP OFF
                        String fromStopId = busTripSummary.getFromStopId();
                        String toStopId = trip.getStopId();
                        busTripSummary.setFinished(trip.getDateTimeUtc());
                        busTripSummary.setDurationSeconds(getTimeDiffInSeconds(busTripSummary.getStarted(), trip.getDateTimeUtc()));
                        busTripSummary.setToStopId(toStopId);
                        busTripSummary.setChargeAmt(calculateTripCost(fromStopId, toStopId));
                        busTripSummary.setStatus(fromStopId.equalsIgnoreCase(toStopId) ?
                                Constants.TRIP_CANCELLED : Constants.TRIP_COMPLETE);
                        tripSummaryList.add(busTripSummary);
                        tapOn = false;
                    } else {
                        // We are seeing a TAP OFF without a TAP ON
                        // mark this as an incomplete trip
                        busTripSummary.setFinished(trip.getDateTimeUtc());
                        busTripSummary.setDurationSeconds(0);
                        busTripSummary.setToStopId(trip.getStopId());
                        busTripSummary.setChargeAmt(calculateTripCost(null, trip.getStopId()));
                        busTripSummary.setStatus(Constants.TRIP_INCOMPLETE);
                        tripSummaryList.add(busTripSummary);
                    }
                }
            }
        }
        );

        return tripSummaryList;
    }

    private long getTimeDiffInSeconds(LocalDateTime date1, LocalDateTime date2) {
        long seconds = 0;

        Duration duration = Duration.between(date1, date2);
        seconds = duration.getSeconds();

        return seconds;
    }

    private BusTripSummary createBusTripSummary(Trip trip) {
        BusTripSummary busTripSummary = new BusTripSummary();

        busTripSummary.setStarted(trip.getDateTimeUtc());
        busTripSummary.setFromStopId(trip.getStopId());
        busTripSummary.setCompanyId(trip.getCompanyId());
        busTripSummary.setBusId(trip.getBusId());
        busTripSummary.setPan(trip.getPan());

        return busTripSummary;
    }

    private double calculateTripCost(String fromStopId, String toStopId) {
        if(null == fromStopId || null == toStopId)
            // incomplete trip, get max fare
            return fareService.getMaxFare(
                    retrieveStopNumber(null == fromStopId ? toStopId : fromStopId));

        return fareService.getFare(retrieveStopNumber(fromStopId),
                retrieveStopNumber(toStopId));
    }

    private int retrieveStopNumber(String stopId) {
        int stopNumber = 0;

        stopNumber = Integer.parseInt(stopId.substring(stopId.indexOf('p')+1));

        return stopNumber;
    }


}
