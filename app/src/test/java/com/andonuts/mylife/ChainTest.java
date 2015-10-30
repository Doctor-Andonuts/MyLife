package com.andonuts.mylife;

import junit.framework.TestCase;

import org.json.JSONObject;

/**
 * Created by jgowing on 10/30/2015.
 */
public class ChainTest extends TestCase {

    public void testGetTitle() throws Exception {
        JSONObject testJson = new JSONObject("{\"Title\": \"Exercise\",\"StartDate\": \"2015-10-01\",\"EndDate\": null,\"Type\": \"MinMax\",\"MinDays\": 2,\"MaxDays\": \"4\",\"PerWeekValue\": null,\"Date\": {\"2015-10-01\": \"D\",\"2015-10-02\": \"D\",\"2015-10-05\": \"D\",\"2015-10-10\": \"D\"}}");
        Chain chain = new Chain(testJson);
        assertEquals("GetTitle", "Exercise", chain.getTitle());
    }
}