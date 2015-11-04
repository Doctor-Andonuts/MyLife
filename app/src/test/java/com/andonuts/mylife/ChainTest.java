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
    // Title is string
    // StartDate is date
    // EndDate is null or a date
    // Type is either PerWeek or MinMax
    // MinDays is int if MinMax, null if PerWeek
    // MaxDays is int or null if MinMax, null if PerWeek
    // PerWeekDays is int if PerWeed, null if MinMax
    // Dates keys are all dates
    // Dates values are string exactly D, S, V, O
    // TODO: I am not sure

    public void testConstructor() throws Exception {

    }

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
    public void testGetMaxDays_PerWeek() throws Exception {
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
    public void testSetTitle_Null() throws Exception {
        Chain chain = new Chain(testJsonOne);
        chain.setTitle(null);
        assertEquals("Exercise", chain.getTitle());
    }
    public void testSetStartDate() throws Exception {
        Chain chain = new Chain(testJsonOne);
        chain.setStartDate("2015-09-20");
        assertEquals("2015-09-20", chain.getStartDate());
    }
    public void testSetStartDate_NotDate() throws Exception {
        Chain chain = new Chain(testJsonOne);
        chain.setStartDate("Not a date");
        assertEquals("2015-10-01", chain.getStartDate());
    }
    public void testSetStartDate_Null() throws Exception {
        Chain chain = new Chain(testJsonOne);
        chain.setStartDate(null);
        assertEquals("2015-10-01", chain.getStartDate());
    }
    public void testSetEndDate() throws Exception {
        Chain chain = new Chain(testJsonOne);
        chain.setEndDate("2015-11-01");
        assertEquals("2015-11-01", chain.getEndDate());
    }
    public void testSetEndDate_NotDate() throws Exception {
        Chain chain = new Chain(testJsonOne);
        chain.setEndDate("Not A date");
        assertEquals("2015-11-01", chain.getEndDate());
    }
    public void testSetEndDate_Null() throws Exception {
        Chain chain = new Chain(testJsonOne);
        chain.setEndDate(null);
        assertEquals(null, chain.getEndDate());
    }
    public void testSetStartDate_OutOfScope_PLACEHOLDER() throws Exception {
        // TODO: Test that start date is before any data you have
        assertFalse(true);
    }
    public void testSetEndDate_OutOfScope_PLACEHOLDER() throws Exception {
        // TODO: Test that end date is after any data you have
        assertFalse(true);
    }


    public void testSetMinDays() throws Exception {
        Chain chain = new Chain(testJsonOne);
        chain.setMinDays(3);
        assertEquals((Integer) 3, chain.getMinDays());
    }
    public void testSetMinDays_Zero() throws Exception {
        Chain chain = new Chain(testJsonOne);
        chain.setMinDays(0);
        assertEquals((Integer) 2, chain.getMinDays());
    }
    public void testSetMinDays_MoreThanMax() throws Exception {
        Chain chain = new Chain(testJsonOne);
        chain.setMinDays(10);
        assertEquals((Integer) 2, chain.getMinDays());
    }
    public void testSetMinDays_OnPerWeek() throws Exception {
        Chain chain = new Chain(testJsonTwo);
        chain.setMinDays(3);
        assertEquals(null, chain.getMinDays());
    }
    public void testSetMaxDays() throws Exception {
        Chain chain = new Chain(testJsonOne);
        chain.setMaxDays(6);
        assertEquals((Integer)6, chain.getMaxDays());
    }
    public void testSetMaxDays_LessThanMin() throws Exception {
        Chain chain = new Chain(testJsonOne);
        chain.setMaxDays(1);
        assertEquals((Integer)4, chain.getMaxDays());
    }
    public void testSetMaxDays_OnPerWeek() throws Exception {
        Chain chain = new Chain(testJsonTwo);
        chain.setMaxDays(3);
        assertEquals(null, chain.getMaxDays());
    }

    public void testSetPerWeekValue() throws Exception {
        Chain chain = new Chain(testJsonTwo);
        chain.setPerWeekValue(2);
        assertEquals((Integer) 2, chain.getPerWeekValue());
    }
    public void testSetPerWeekValue_Zero() throws Exception {
        Chain chain = new Chain(testJsonTwo);
        chain.setPerWeekValue(0);
        assertEquals((Integer)3, chain.getPerWeekValue());
    }
    public void testSetPerWeekValue_MoreThanSeven() throws Exception {
        Chain chain = new Chain(testJsonTwo);
        chain.setPerWeekValue(8);
        assertEquals((Integer)3, chain.getPerWeekValue());
    }
    public void testSetPerWeekValue_OnMinMax() throws Exception {
        Chain chain = new Chain(testJsonOne);
        chain.setPerWeekValue(2);
        assertEquals(null, chain.getPerWeekValue());
    }

    public void testSetDone_Done() throws Exception {
        Chain chain = new Chain(testJsonOne);
        chain.setDone("2015-10-16", "Done");
        assertEquals("D", chain.getDateValue("2015-10-16"));
    }
    public void testSetDone_DoneBeforeStart() throws Exception {
        Chain chain = new Chain(testJsonOne);
        chain.setDone("2015-09-16", "Done");
        assertEquals("", chain.getDateValue("2015-09-16"));
    }
    public void testSetDone_OffDay() throws Exception {
        Chain chain = new Chain(testJsonOne);
        chain.setDone("2015-10-17", "Offday");
        assertEquals("O", chain.getDateValue("2015-10-17"));
    }
    public void testSetDone_WrongType() throws Exception {
        Chain chain = new Chain(testJsonOne);
        chain.setDone("2015-10-18", "WARGS");
        assertEquals("", chain.getDateValue("2015-10-18"));
    }
    public void testSetDone_DoneAfterEndDate() throws Exception {
        Chain chain = new Chain(testJsonOne);
        chain.setDone("2015-11-19", "Done");
        assertEquals("", chain.getDateValue("2015-11-19"));
    }


    public void testGetDayStatus_Done() throws Exception {
        Chain chain = new Chain(testJsonOne);
        assertEquals("Done", chain.getDayStatus("2015-10-10"));
    }
    public void testGetDayStatus_NoNeed() throws Exception {
        Chain chain = new Chain(testJsonOne);
        assertEquals("No need", chain.getDayStatus("2015-10-11"));
    }
    public void testGetDayStatus_ShouldDo() throws Exception {
        Chain chain = new Chain(testJsonOne);
        assertEquals("Should do", chain.getDayStatus("2015-10-12"));
    }
    public void testGetDayStatus_DoIt() throws Exception {
        Chain chain = new Chain(testJsonOne);
        assertEquals("DO IT!", chain.getDayStatus("2015-10-15"));
    }
}