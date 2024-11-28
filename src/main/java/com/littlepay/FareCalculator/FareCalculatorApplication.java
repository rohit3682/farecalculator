package com.littlepay.FareCalculator;

import com.littlepay.FareCalculator.dto.BusTripSummary;
import com.littlepay.FareCalculator.dto.Trip;
import com.littlepay.FareCalculator.service.FareService;
import com.littlepay.FareCalculator.service.TripSummaryService;
import com.littlepay.FareCalculator.util.CSVUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;
import java.util.Map;

@SpringBootApplication
public class FareCalculatorApplication {

	@Autowired
	private CSVUtil csvUtil;

	@Autowired
	private FareService fareService;

	@Autowired
	private TripSummaryService tripSummaryService;

	public static void main(String[] args) {
		SpringApplication.run(FareCalculatorApplication.class, args);
	}

	@Bean
	public CommandLineRunner run() {
		return args -> {
			fareService.loadFareData();
			Map<String, List<Trip>> tripData = csvUtil.readCsvData("taps.csv");
			List<BusTripSummary> busTripSummaryList = tripSummaryService.generateTripSummary(tripData);
		};
	}
}
