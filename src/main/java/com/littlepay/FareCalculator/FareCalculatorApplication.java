package com.littlepay.FareCalculator;

import com.littlepay.FareCalculator.util.CSVUtil;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class FareCalculatorApplication {

	public static void main(String[] args) {
		SpringApplication.run(FareCalculatorApplication.class, args);
	}

	@Bean
	public CommandLineRunner run(CSVUtil csvUtil) {
		return args -> {
			csvUtil.readCsvData("taps.csv");

		};
	}
}
