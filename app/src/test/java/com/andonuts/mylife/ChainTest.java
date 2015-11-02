package com.andonuts.mylife;

import junit.framework.TestCase;

import org.json.JSONObject;


/**
 * Created by jgowing on 10/30/2015.
 */
public class ChainTest extends TestCase {

    JSONObject testJson;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        testJson = new JSONObject("{\"Title\": \"Exercise\",\"StartDate\": \"2015-10-01\",\"EndDate\": null,\"Type\": \"MinMax\",\"MinDays\": 2,\"MaxDays\": \"4\",\"PerWeekValue\": null,\"Dates\": {\"2015-10-01\": \"D\",\"2015-10-02\": \"D\",\"2015-10-05\": \"S\",\"2015-10-10\": \"D\"}}");
    }

    public void testGetTitle() throws Exception {
        Chain chain = new Chain(testJson);
        assertEquals("getTitle", "Exercise", chain.getTitle());
    }
    public void testGetStartDate() throws Exception {
        Chain chain = new Chain(testJson);
        assertEquals("getStartDate", "2015-10-01", chain.getStartDate());
    }
    public void testGetEndDate() throws Exception {
        Chain chain = new Chain(testJson);
        assertEquals("getEndDate", null, chain.getEndDate());
    }
    public void testGetType() throws Exception {
        Chain chain = new Chain(testJson);
        assertEquals("getType", "MinMax", chain.getType());
    }
    public void testGetMinDays() throws Exception {
        Chain chain = new Chain(testJson);
        assertEquals("getMinDays", (Integer)2, chain.getMinDays());
    }
    public void testGetMaxDays() throws Exception {
        Chain chain = new Chain(testJson);
        assertEquals("getMaxDays", (Integer)4, chain.getMaxDays());
    }
    public void testGetPerWeekValue() throws Exception {
        Chain chain = new Chain(testJson);
        assertEquals("getPerWeekValue", null, chain.getPerWeekValue());
    }
    public void testGetDateValue() throws Exception {
        Chain chain = new Chain(testJson);
        assertEquals("getDateValue1", "D", chain.getDateValue("2015-10-01"));
        assertEquals("getDateValue2", "D", chain.getDateValue("2015-10-02"));
        assertEquals("getDateValue3", "", chain.getDateValue("2015-10-03"));
        assertEquals("getDateValue4", "", chain.getDateValue("2015-10-04"));
        assertEquals("getDateValue5", "S", chain.getDateValue("2015-10-05"));
        assertEquals("getDateValue6", "D", chain.getDateValue("2015-10-10"));
    }
    public void testSetTitle() throws Exception {
        Chain chain = new Chain(testJson);
        chain.setTitle("Go Time");
        assertEquals("setTitle", "Go Time", chain.getTitle());
    }
    public void testSetStartDate() throws Exception {
        Chain chain = new Chain(testJson);
        chain.setStartDate("2015-09-20");
        assertEquals("setStartDate", "2015-09-20", chain.getStartDate());
    }
    public void testSetEndDate() throws Exception {
        Chain chain = new Chain(testJson);
        chain.setEndDate("2015-11-01");
        assertEquals("setEndDate1", "2015-11-01", chain.getEndDate());
        chain.setEndDate(null);
        assertEquals("setEndDate2", null, chain.getEndDate());
    }
    public void testSetType() throws Exception {
        Chain chain = new Chain(testJson);
        chain.setType("PerWeek");
        assertEquals("setType1", "PerWeek", chain.getType());

        chain = new Chain(testJson);
        chain.setType("Nothing");
        assertEquals("setType2", "MinMax", chain.getType());

        chain = new Chain(testJson);
        chain.setType(null);
        assertEquals("setType2", "MinMax", chain.getType());
    }
    public void testSetMinDays() throws Exception {
        Chain chain = new Chain(testJson);
        chain.setMinDays(3);
        assertEquals("setMinDays", (Integer)3, chain.getMinDays());
    }
    public void testSetMaxDays() throws Exception {
        Chain chain = new Chain(testJson);
        chain.setMaxDays(6);
        assertEquals("setMaxDays", (Integer)6, chain.getMaxDays());
    }
}