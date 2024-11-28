package com.littlepay.FareCalculator;

import com.littlepay.FareCalculator.dto.Trip;
import com.littlepay.FareCalculator.util.CSVUtil;
import com.littlepay.FareCalculator.util.Constants;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;
import java.util.Map;

@SpringBootApplication
public class FareCalculatorApplication {

	public static void main(String[] args) {
		SpringApplication.run(FareCalculatorApplication.class, args);
	}

	@Bean
	public CommandLineRunner run(CSVUtil csvUtil) {
		return args -> {
			Map<String, List<Trip>> tripData = csvUtil.readCsvData("taps.csv");



		};
	}
}
