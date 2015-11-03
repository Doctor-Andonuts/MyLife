package com.andonuts.mylife;

import junit.framework.TestCase;

import org.json.JSONObject;


/**
 * Created by jgowing on 10/30/2015.
 */
public class ChainTest extends TestCase {

    JSONObject testJsonOne;
    JSONObject testJsonTwo;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        testJsonOne = new JSONObject("{\"Title\": \"Exercise\",\"StartDate\": \"2015-10-01\",\"EndDate\": \"2015-11-01\",\"Type\": \"MinMax\",\"MinDays\": 2,\"MaxDays\": \"4\",\"PerWeekValue\": null,\"Dates\": {\"2015-10-01\": \"D\",\"2015-10-02\": \"D\",\"2015-10-05\": \"S\",\"2015-10-10\": \"D\"}}");
        testJsonTwo = new JSONObject("{\"Title\": \"Exercise\",\"StartDate\": \"2015-10-01\",\"EndDate\": \"2015-11-01\",\"Type\": \"PerWeek\",\"MinDays\": null,\"MaxDays\": null,\"PerWeekValue\": \"3\",\"Dates\": {\"2015-10-01\": \"D\",\"2015-10-02\": \"D\",\"2015-10-05\": \"S\",\"2015-10-10\": \"D\"}}");
    }

    // TODO: test the constructor has certain values

    public void testGetTitle() throws Exception {
        Chain chain = new Chain(testJsonOne);
        assertEquals("Exercise", chain.getTitle());
    }
    public void testGetStartDate() throws Exception {
        Chain chain = new Chain(testJsonOne);
        assertEquals("2015-10-01", chain.getStartDate());
    }
    public void testGetEndDate() throws Exception {
        Chain chain = new Chain(testJsonOne);
        assertEquals("2015-11-01", chain.getEndDate());
    }
    public void testGetType() throws Exception {
        Chain chain = new Chain(testJsonOne);
        assertEquals("MinMax", chain.getType());
    }
    public void testGetMinDays() throws Exception {
        Chain chain = new Chain(testJsonOne);
        assertEquals((Integer)2, chain.getMinDays());
    }
    public void testGetMinDaysPerWeek() throws Exception {
        Chain chain = new Chain(testJsonTwo);
        assertEquals(null, chain.getMinDays());
    }
    public void testGetMaxDays() throws Exception {
        Chain chain = new Chain(testJsonOne);
        assertEquals((Integer)4, chain.getMaxDays());
    }
    public void testGetMaxDaysPerWeek() throws Exception {
        Chain chain = new Chain(testJsonTwo);
        assertEquals(null, chain.getMaxDays());
    }
    public void testGetPerWeekValue() throws Exception {
        Chain chain = new Chain(testJsonTwo);
        assertEquals((Integer) 3, chain.getPerWeekValue());
    }
    public void testGetDateValue() throws Exception {
        Chain chain = new Chain(testJsonOne);
        assertEquals("D", chain.getDateValue("2015-10-01"));
        assertEquals("D", chain.getDateValue("2015-10-02"));
        assertEquals("", chain.getDateValue("2015-10-03"));
        assertEquals("", chain.getDateValue("2015-10-04"));
        assertEquals("S", chain.getDateValue("2015-10-05"));
        assertEquals("D", chain.getDateValue("2015-10-10"));
    }
    public void testSetTitle() throws Exception {
        Chain chain = new Chain(testJsonOne);
        chain.setTitle("Go Time");
        assertEquals("Go Time", chain.getTitle());
    }
    public void testSetTitleNull() throws Exception {
        Chain chain = new Chain(testJsonOne);
        chain.setTitle(null);
        assertEquals("Exercise", chain.getTitle());
    }
    public void testSetStartDate() throws Exception {
        Chain chain = new Chain(testJsonOne);
        chain.setStartDate("2015-09-20");
        assertEquals("2015-09-20", chain.getStartDate());
    }
    public void testSetStartDateNotDate() throws Exception {
        Chain chain = new Chain(testJsonOne);
        chain.setStartDate("Not a date");
        assertEquals("2015-10-01", chain.getStartDate());
    }
    public void testSetStartDateNull() throws Exception {
        Chain chain = new Chain(testJsonOne);
        chain.setStartDate(null);
        assertEquals("2015-10-01", chain.getStartDate());
    }
    public void testSetEndDate() throws Exception {
        Chain chain = new Chain(testJsonOne);
        chain.setEndDate("2015-11-01");
        assertEquals("2015-11-01", chain.getEndDate());
    }
    public void testSetEndDateNotDate() throws Exception {
        Chain chain = new Chain(testJsonOne);
        chain.setEndDate("Not A date");
        assertEquals("2015-11-01", chain.getEndDate());
    }
    public void testSetEndDateNull() throws Exception {
        Chain chain = new Chain(testJsonOne);
        chain.setEndDate(null);
        assertEquals(null, chain.getEndDate());
    }
    public void testSetStartDateOutOfScope_PLACEHOLDER() throws Exception {
        // TODO: Test that start date is before any data you have
        assertFalse(true);
    }
    public void testSetEndDateOutOfScope_PLACEHOLDER() throws Exception {
        // TODO: Test that end date is after any data you have
        assertFalse(true);
    }


    public void testSetMinDays() throws Exception {
        Chain chain = new Chain(testJsonOne);
        chain.setMinDays(3);
        assertEquals((Integer) 3, chain.getMinDays());
    }
    public void testSetMinDaysZero() throws Exception {
        Chain chain = new Chain(testJsonOne);
        chain.setMinDays(0);
        assertEquals((Integer) 2, chain.getMinDays());
    }
    public void testSetMinDaysMoreThanMax() throws Exception {
        Chain chain = new Chain(testJsonOne);
        chain.setMinDays(10);
        assertEquals((Integer) 2, chain.getMinDays());
    }
    public void testSetMinDaysOnPerWeek() throws Exception {
        Chain chain = new Chain(testJsonTwo);
        chain.setMinDays(3);
        assertEquals(null, chain.getMinDays());
    }
    public void testSetMaxDays() throws Exception {
        Chain chain = new Chain(testJsonOne);
        chain.setMaxDays(6);
        assertEquals((Integer)6, chain.getMaxDays());
    }
    public void testSetMaxDaysLessThanMin() throws Exception {
        Chain chain = new Chain(testJsonOne);
        chain.setMaxDays(1);
        assertEquals((Integer)4, chain.getMaxDays());
    }
    public void testSetMaxDaysOnPerWeek() throws Exception {
        Chain chain = new Chain(testJsonTwo);
        chain.setMaxDays(3);
        assertEquals(null, chain.getMaxDays());
    }

    public void testSetPerWeekValue() throws Exception {
        Chain chain = new Chain(testJsonTwo);
        chain.setPerWeekValue(2);
        assertEquals((Integer) 2, chain.getPerWeekValue());
    }
    public void testSetPerWeekValueZero() throws Exception {
        Chain chain = new Chain(testJsonTwo);
        chain.setPerWeekValue(0);
        assertEquals((Integer)3, chain.getPerWeekValue());
    }
    public void testSetPerWeekValueMoreThanSeven() throws Exception {
        Chain chain = new Chain(testJsonTwo);
        chain.setPerWeekValue(8);
        assertEquals((Integer)3, chain.getPerWeekValue());
    }
    public void testSetPerWeekValueOnMinMax() throws Exception {
        Chain chain = new Chain(testJsonOne);
        chain.setPerWeekValue(2);
        assertEquals(null, chain.getPerWeekValue());
    }
}