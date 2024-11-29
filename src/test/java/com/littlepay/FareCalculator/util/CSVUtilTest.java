package com.littlepay.FareCalculator.util;

import com.littlepay.FareCalculator.dto.Trip;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class CSVUtilTest {

    private static Map<String, List<Trip>> tapsData;

    @BeforeAll
    public static void setUp() throws IOException {
        CSVUtil csvUtil = new CSVUtil();
        tapsData = csvUtil.readTapsData("taps.csv");
    }

    @Test
    public void testUniquePanCount() {
        Assertions.assertEquals(11, tapsData.size());
    }

    @Test
    public void testTapCountForPan() {
        Assertions.assertEquals(2, tapsData.get("1000001111111114").size());

        Assertions.assertEquals(3, tapsData.get("4111111111111111").size());

        Assertions.assertEquals(4, tapsData.get("5500005555555559").size());
    }

    @Test
    public void testCompanyId() {
        Assertions.assertEquals("Company3", tapsData.get("8000008888888882").get(0).getCompanyId());

        Assertions.assertEquals("Company6", tapsData.get("1400001555555558").get(0).getCompanyId());
    }

    @Test
    public void testTapType() {
        Assertions.assertEquals(Constants.TAP_ON, tapsData.get("1100001222222225").get(0).getTapType());

        Assertions.assertEquals(Constants.TAP_OFF, tapsData.get("1100001222222225").get(1).getTapType());
    }

    @Test
    public void testBusId() {
        Assertions.assertEquals("Bus39", tapsData.get("7000007777777771").get(0).getBusId());

        Assertions.assertEquals("Bus41", tapsData.get("9000009999999993").get(1).getBusId());
    }

}
