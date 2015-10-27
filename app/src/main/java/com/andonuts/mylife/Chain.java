package com.andonuts.mylife;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

public class Chain {
    private String TAG = "TaskClass";
    private JSONObject chainJson;

    public Chain(JSONObject chainJson) {
        this.chainJson = chainJson;
    }


    public String getTitle(){
        try {
            return chainJson.getString("Title");
        } catch (Exception e) {
            Log.e(TAG, "Error getting value Name");
        }
        return null;
    }
    public String getStartDate(){
        try {
            return chainJson.getString("StartDate");
        } catch (Exception e) {
            Log.e(TAG, "Error getting value StartDate");
        }
        return null;
    }
    public String getEndDate(){
        try {
            return chainJson.getString("EndDate");
        } catch (Exception e) {
            Log.e(TAG, "Error getting value EndDate");
        }
        return null;
    }
    public String getType(){
        try {
            return chainJson.getString("Type");
        } catch (Exception e) {
            Log.e(TAG, "Error getting value Type");
        }
        return null;
    }
    public Integer getMinDays(){
        try {
            return chainJson.getInt("MinDays");
        } catch (Exception e) {
            Log.e(TAG, "Error getting value MinDays");
        }
        return null;
    }
    public Integer getMaxDays(){
        try {
            return chainJson.getInt("MaxDays");
        } catch (Exception e) {
            Log.e(TAG, "Error getting value MaxDays");
        }
        return null;
    }
    public Integer getPerWeekValue(){
        try {
            return chainJson.getInt("PerWeekValue");
        } catch (Exception e) {
            Log.e(TAG, "Error getting value PerWeekValue");
        }
        return null;
    }
    public JSONObject getDatesArray() {
        try {
            return chainJson.getJSONObject("Dates");
        } catch (Exception e) {
            Log.e(TAG, "Error getting value DatesArray");
        }
        return null;
    }


    public void setTitle(String value) {
        try {
            chainJson.put("Title", value);
        } catch (Exception e) {
            Log.e(TAG, "setTitle Error");
        }
    }
    public void setStartDate(String value) {
        try {
            chainJson.put("StartDate", value);
        } catch (Exception e) {
            Log.e(TAG, "setStartDate Error");
        }
    }
    public void setEndDate(String value) {
        try {
            chainJson.put("EndDate", value);
        } catch (Exception e) {
            Log.e(TAG, "setEndDate Error");
        }
    }
    public void setType(String value) {
        try {
            chainJson.put("Type", value);
        } catch (Exception e) {
            Log.e(TAG, "setType Error");
        }
    }
    public void setMinDays(Integer value) {
        try {
            chainJson.put("MinDays", value);
        } catch (Exception e) {
            Log.e(TAG, "setMinDays Error");
        }
    }
    public void setMaxDays(Integer value) {
        try {
            chainJson.put("MaxDays", value);
        } catch (Exception e) {
            Log.e(TAG, "setMaxDays Error");
        }
    }
    public void setPerWeekValue(Integer value) {
        try {
            chainJson.put("PerWeekValue", value);
        } catch (Exception e) {
            Log.e(TAG, "setPerWeekValue Error");
        }
    }

    public void setDone(String date, String doneType) {
        String doneTypeAbbreviation;
        switch (doneType) {
            case "Sick":
                doneTypeAbbreviation = "S";
                break;
            case "Vacation":
                doneTypeAbbreviation = "V";
                break;
            case "Offday":
                doneTypeAbbreviation = "O";
                break;
            case "Done":
            default:
                doneTypeAbbreviation = "D";
                break;
        }

        JSONObject datesArray = getDatesArray();
        try {
            datesArray.put(date, doneTypeAbbreviation);
        } catch(Exception e) {
            Log.e(TAG, "setDone");
        }
    }
}
