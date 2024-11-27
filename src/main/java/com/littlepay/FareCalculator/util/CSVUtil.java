package com.littlepay.FareCalculator.util;

import com.littlepay.FareCalculator.dto.Trip;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.HeaderColumnNameMappingStrategy;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class CSVUtil {

    @EventListener(ApplicationReadyEvent.class)
    public List<Trip> readCsvData(Path path) throws IOException {
        List<Trip> tripList = new ArrayList<>();

        try(BufferedReader bufferedReader = Files.newBufferedReader(path)) {
            HeaderColumnNameMappingStrategy strategy = new HeaderColumnNameMappingStrategy<>();
            strategy.setType(Trip.class);

            CsvToBean<Trip> csvToBean = new CsvToBeanBuilder<Trip>(bufferedReader)
                    .withMappingStrategy(strategy)
                    .withIgnoreLeadingWhiteSpace(true)
                    .build();

            tripList = csvToBean.parse();

            tripList.forEach(System.out::println);
        }

        return tripList;
    }

}
