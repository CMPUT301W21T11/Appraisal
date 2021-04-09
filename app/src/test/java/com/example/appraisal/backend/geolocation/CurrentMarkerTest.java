package com.example.appraisal.backend.geolocation;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CurrentMarkerTest {
    private CurrentMarker test_marker;
    private CurrentMarker test_marker_2;
    private static final double DELTA = 1e-15;

    @Before
    public void init() {
        test_marker = new CurrentMarker(53.546123, -113.493822);
        test_marker_2 = new CurrentMarker(0.00,0.00);
    }

    @Test
    public void testConstruct() {
        assertEquals(53.546123, test_marker.getLatitude(), DELTA);
        assertEquals(-113.493822, test_marker.getLongitude(), DELTA);
        assertEquals(0.00, test_marker_2.getLatitude(), DELTA);
        assertEquals(0.00, test_marker_2.getLongitude(), DELTA);
    }

    @Test
    public void testLatitude(){
        assertEquals(53.546123, test_marker.getLatitude(), DELTA);
        assertEquals(0.00, test_marker_2.getLatitude(), DELTA);
    }

    @Test
    public void testLongitude(){
        assertEquals(-113.493822, test_marker.getLongitude(), DELTA);
        assertEquals(0.00, test_marker_2.getLongitude(), DELTA);
    }
}
