package com.littlepay.FareCalculator.service;

import com.littlepay.FareCalculator.util.Constants;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

@Service
public class FareService {

    private final Map<Integer, Map<Integer, Double>> faresData = new HashMap<>();
    private final Map<Integer, Double> maxFareData = new HashMap<>();

    public void loadFareData() throws IOException {
        try (BufferedReader bufferedReader = new BufferedReader(
                new InputStreamReader(new ClassPathResource("fares").getInputStream()))) {
            String line;
            int fromStopId = 1;

            while((line = bufferedReader.readLine())!= null) {
                int toStopId = 1;

                faresData.putIfAbsent(fromStopId, new HashMap<>());

                String[] fares = line.split(Constants.COMA_DELIMITER);

                for(String fare : fares) {
                    double dFare = Double.parseDouble(fare);
                    Double maxFare = maxFareData.get(fromStopId);
                    if(maxFare == null)
                        maxFareData.put(fromStopId, dFare);
                    else
                        maxFareData.put(fromStopId, Math.max(maxFareData.get(fromStopId), dFare));
                    faresData.get(fromStopId).put(toStopId++, Double.parseDouble(fare));
                }

                fromStopId++;
            }
        }
    }

    public int getFareCount(){
        return faresData.size();
    }

    public double getMaxFare(Integer stopId) {
        return maxFareData.get(stopId);
    }

    public double getFare(Integer fromStopId, Integer toStopId) {
        double fare;

        fare = faresData.get(fromStopId).get(toStopId);

        return fare;
    }
}
