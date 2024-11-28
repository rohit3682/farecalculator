package com.littlepay.FareCalculator.util;

import com.littlepay.FareCalculator.dto.Trip;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CSVUtil {

    public Map<String, List<Trip>> readCsvData(String fileName) throws IOException {
        Map<String, List<Trip>> csvData = new HashMap<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        int i = 1;

        try (BufferedReader bufferedReader = new BufferedReader(
                new InputStreamReader(new ClassPathResource(fileName).getInputStream()))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {

                //Skip the header
                if(i == 1) {
                    i++;
                    continue;
                }

                String[] values = line.split(Constants.COMA_DELIMITER);
                String pan = values[Constants.FEILD_PAN].trim();

                Trip trip = new Trip();
                trip.setId(Integer.parseInt(values[Constants.FIELD_ID]));
                trip.setDateTimeUtc(LocalDateTime.parse(values[Constants.FIELD_DATETIME].trim(), formatter));
                trip.setTapType(values[Constants.FIELD_TAPTYPE].trim());
                trip.setStopId(values[Constants.FIELD_STOPID].trim());
                trip.setCompanyId(values[Constants.FIELD_COMPANYID].trim());
                trip.setBusId(values[Constants.FIELD_BUSID].trim());
                trip.setPan(pan);

                csvData.putIfAbsent(pan, new ArrayList<>());
                csvData.get(pan).add(trip);
            }

            return csvData;
        }
    }
}
