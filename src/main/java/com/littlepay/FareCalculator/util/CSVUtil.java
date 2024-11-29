package com.littlepay.FareCalculator.util;

import com.littlepay.FareCalculator.dto.BusTripSummary;
import com.littlepay.FareCalculator.dto.Trip;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CSVUtil {

    @Value("${trips.csv.path}")
    private String tripsCsvPath;

    public Map<String, List<Trip>> readTapsData(String fileName) throws IOException {
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

    public void writeTripSummary(List<BusTripSummary> busTripSummaryList) throws IOException{
        String fileName = "trips.csv";
        String[] headers = {"Started", "Finished", "DurationSecs", "FromStopId", "ToStopId", "ChargeAmount", "CompanyId", "BusID", "PAN", "Status"};

        String resourcePath = Paths.get(tripsCsvPath, fileName).toString();

        writeCsv(resourcePath, headers, busTripSummaryList);
        System.out.println("Done");
    }

    private void writeCsv(String filePath, String[] headers, List<BusTripSummary> busTripSummaryList) throws IOException {
        File file = new File(filePath);

        try(BufferedWriter bufferedWriter = new BufferedWriter((new FileWriter(file)))) {
            bufferedWriter.write(String.join(Constants.COMA_DELIMITER, headers));
            bufferedWriter.newLine();

            for(BusTripSummary busTripSummary : busTripSummaryList) {
                bufferedWriter.write(busTripSummary.getStarted()==null?"":
                        busTripSummary.getStarted()+"");
                bufferedWriter.write(Constants.COMA_DELIMITER);

                bufferedWriter.write(busTripSummary.getFinished()==null? "" :
                        busTripSummary.getFinished()+"");
                bufferedWriter.write(Constants.COMA_DELIMITER);

                bufferedWriter.write(busTripSummary.getDurationSeconds()+"");
                bufferedWriter.write(Constants.COMA_DELIMITER);

                bufferedWriter.write(busTripSummary.getFromStopId()==null?"":busTripSummary.getFromStopId());
                bufferedWriter.write(Constants.COMA_DELIMITER);

                bufferedWriter.write(busTripSummary.getToStopId()==null?"":busTripSummary.getToStopId());
                bufferedWriter.write(Constants.COMA_DELIMITER);

                bufferedWriter.write(busTripSummary.getChargeAmt()+"");
                bufferedWriter.write(Constants.COMA_DELIMITER);

                bufferedWriter.write(busTripSummary.getCompanyId());
                bufferedWriter.write(Constants.COMA_DELIMITER);

                bufferedWriter.write(busTripSummary.getBusId());
                bufferedWriter.write(Constants.COMA_DELIMITER);

                bufferedWriter.write(busTripSummary.getPan());
                bufferedWriter.write(Constants.COMA_DELIMITER);

                bufferedWriter.write(busTripSummary.getStatus());
                bufferedWriter.newLine();
            }
        }
    }
}
