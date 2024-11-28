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
import java.util.List;

@Service
public class CSVUtil {

    public List<Trip> readCsvData(String fileName) throws IOException {
        List<Trip> tripList;

        try(BufferedReader bufferedReader = new BufferedReader(
                new InputStreamReader(new ClassPathResource(fileName).getInputStream()))) {
            HeaderColumnNameMappingStrategy<Trip> strategy = new HeaderColumnNameMappingStrategy<>();
            strategy.setType(Trip.class);

            CsvToBean<Trip> csvToBean = new CsvToBeanBuilder<Trip>(bufferedReader)
                    .withMappingStrategy(strategy)
                    .withIgnoreLeadingWhiteSpace(true)
                    .build();

            tripList = csvToBean.parse();
        }

        return tripList;
    }

}
