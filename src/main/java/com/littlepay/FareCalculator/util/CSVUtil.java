package com.littlepay.FareCalculator.util;

import com.littlepay.FareCalculator.dto.Trip;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.HeaderColumnNameMappingStrategy;
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

//    public List<Trip> readCsvData(String fileName) throws IOException {
//        List<Trip> tripList;
//
//        try(BufferedReader bufferedReader = new BufferedReader(
//                new InputStreamReader(new ClassPathResource(fileName).getInputStream()))) {
//            HeaderColumnNameMappingStrategy<Trip> strategy = new HeaderColumnNameMappingStrategy<>();
//            strategy.setType(Trip.class);
//
//            CsvToBean<Trip> csvToBean = new CsvToBeanBuilder<Trip>(bufferedReader)
//                    .withMappingStrategy(strategy)
//                    .withIgnoreLeadingWhiteSpace(true)
//                    .build();
//
//            tripList = csvToBean.parse();
//        }
//
//        return tripList;
//    }

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

                String[] values = line.split(Constants.DELIMITER);
                String pan = values[Constants.FEILD_PAN];

                Trip trip = new Trip();
                trip.setId(Integer.parseInt(values[Constants.FIELD_ID]));
                trip.setDateTimeUtc(LocalDateTime.parse(values[Constants.FIELD_DATETIME].trim(), formatter));
                trip.setTapType(values[Constants.FIELD_TAPTYPE]);
                trip.setStopId(values[Constants.FIELD_STOPID]);
                trip.setCompanyId(values[Constants.FIELD_COMPANYID]);
                trip.setBusId(values[Constants.FIELD_BUSID]);
                trip.setPan(pan);

                csvData.putIfAbsent(pan, new ArrayList<>());
                csvData.get(pan).add(trip);
            }

            return csvData;
        }
    }
}
