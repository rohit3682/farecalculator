package com.littlepay.FareCalculator.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;


public class FareServiceTests {

    private static final FareService fareService = new FareService();

    @BeforeAll
    public static void setUp() throws IOException {
        fareService.loadFareData();
    }

    @Test
    public void testFareCount() {
        Assertions.assertEquals(10, fareService.getFareCount());
    }

    @Test
    public void testMaxFare1() {
        Assertions.assertEquals(9.78, fareService.getMaxFare(1));
    }

    @Test
    public void testMaxFare2() {
        Assertions.assertEquals(9.45, fareService.getMaxFare(6));
    }

    @Test
    public void testMaxFare3() {
        Assertions.assertEquals(9.51, fareService.getMaxFare(9));
    }

    @Test
    public void testGetFare1() {
        Assertions.assertEquals(7.89, fareService.getFare(5, 7));
    }

    // Switching from & to stops should return same charge.
    @Test
    public void testGetFare2() {
        Assertions.assertEquals(7.89, fareService.getFare(7, 5));
    }

    @Test
    public void testGetFare3() {
        Assertions.assertEquals(6.16, fareService.getFare(3, 4));
    }

    // Switching from & to stops should return same charge.
    @Test
    public void testGetFare4() {
        Assertions.assertEquals(6.16, fareService.getFare(4, 3));
    }

    // TAP ON & TAP OFF should return 0 fare.
    @Test
    public void testGetFare5() {
        Assertions.assertEquals(0, fareService.getFare(8, 8));
    }

}
