package com.andonuts.mylife;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Chain {
    private String TAG = "TaskClass";
    private JSONObject chainJson;

    public Chain(JSONObject chainJson) {
        this.chainJson = chainJson;
    }


    public String getTitle() {
        try {
            return chainJson.getString("Title");
        } catch (Exception e) {
            Log.v(TAG, "Error getting value Name");
        }
        return null;
    }

    public String getStartDate() {
        try {
            return chainJson.getString("StartDate");
        } catch (Exception e) {
            Log.e(TAG, "Error getting value StartDate");
        }
        return null;
    }

    public String getEndDate() {
        try {
            return chainJson.getString("EndDate");
        } catch (Exception e) {
            Log.e(TAG, "Error getting value EndDate");
        }
        return null;
    }

    public String getType() {
        try {
            return chainJson.getString("Type");
        } catch (Exception e) {
            Log.e(TAG, "Error getting value Type");
        }
        return null;
    }

    public Integer getMinDays() {
        try {
            return chainJson.getInt("MinDays");
        } catch (Exception e) {
            Log.e(TAG, "Error getting value MinDays");
        }
        return null;
    }

    public Integer getMaxDays() {
        try {
            return chainJson.getInt("MaxDays");
        } catch (Exception e) {
            Log.e(TAG, "Error getting value MaxDays");
        }
        return null;
    }

    public Integer getPerWeekValue() {
        try {
            return chainJson.getInt("PerWeekValue");
        } catch (Exception e) {
            Log.e(TAG, "Error getting value PerWeekValue");
        }
        return null;
    }

    private JSONObject getDatesData() {
        try {
            return chainJson.getJSONObject("Dates");
        } catch (Exception e) {
            Log.e(TAG, "Error getting value DatesArray");
        }
        return null;
    }

    public String getDateValue(String dateToCheck) {
        JSONObject datesData = getDatesData();
        try {
            if (datesData.has(dateToCheck)) {
                return datesData.getString(dateToCheck);
            }
        } catch (Exception e) {
            Log.e(TAG, "getDateValue");
        }
        return "";
    }

    public void setTitle(String value) {
        if(value != null) {
            try {
                chainJson.put("Title", value);
            } catch (Exception e) {
                Log.e(TAG, "setTitle Error");
            }
        }
    }

    public void setStartDate(String value) {
        if(value != null && value.matches("\\d{4}-\\d{2}-\\d{2}")) {
            try {
                chainJson.put("StartDate", value);
            } catch (Exception e) {
                Log.e(TAG, "setStartDate Error");
            }
        }
    }

    public void setEndDate(String value) {
        if(value == null || value.matches("\\d{4}-\\d{2}-\\d{2}")) {
            try {
                chainJson.put("EndDate", value);
            } catch (Exception e) {
                Log.e(TAG, "setEndDate Error");
            }
        }
    }

    public void setMinDays(Integer value) {
        if(getType().equals("MinMax")) {
            if (value <= getMaxDays() && value > 0) {
                try {
                    chainJson.put("MinDays", value);
                } catch (Exception e) {
                    Log.e(TAG, "setMinDays Error");
                }
            }
        }
    }

    public void setMaxDays(Integer value) {
        if(getType().equals("MinMax")) {
            if (value >= getMinDays()) {
                try {
                    chainJson.put("MaxDays", value);
                } catch (Exception e) {
                    Log.e(TAG, "setMaxDays Error");
                }
            }
        }
    }
    public void setPerWeekValue(Integer value) {
        if(getType().equals("PerWeek")) {
            if (value >= 1 && value <= 7) {
                try {
                    chainJson.put("PerWeekValue", value);
                } catch (Exception e) {
                    Log.e(TAG, "setPerWeekValue Error");
                }
            }
        }
    }
}

//
//    public void setDone(String date, String doneType) {
//        String doneTypeAbbreviation;
//        switch (doneType) {
//            case "Sick":
//                doneTypeAbbreviation = "S";
//                break;
//            case "Vacation":
//                doneTypeAbbreviation = "V";
//                break;
//            case "Offday":
//                doneTypeAbbreviation = "O";
//                break;
//            case "Done":
//            default:
//                doneTypeAbbreviation = "D";
//                break;
//        }
//
//        JSONObject datesData = getDatesData();
//        try {
//            datesData.put(date, doneTypeAbbreviation);
//        } catch(Exception e) {
//            Log.e(TAG, "setDone");
//        }
//    }
//
//    public Integer getCurrentLength() {
//        // make recursive function for this.
//        /*
//        Have it look up to max days for a done mark, if it finds it
//         */
//        return null;
//    }
//
//    public String getDayStatus(String dateToCheckString) {
////        Integer maxDays = getMaxDays();
////        Integer minDays = getMinDays();
////        dateToCheckString += " 5";
////        SimpleDateFormat myDateInputFormat = new SimpleDateFormat("yyyy-MM-dd kk");
////        SimpleDateFormat myDateOutputFormat = new SimpleDateFormat("yyyy-MM-dd");
////        Calendar dateToCheck = Calendar.getInstance();
////
////        try {
////            dateToCheck.setTime(myDateInputFormat.parse(dateToCheckString));
////        } catch(Exception e) {
////            Log.e(TAG, "Date Parse Error");
////        }
////
////        for (int i = 0; i <= maxDays; i++) {
////            Calendar newDate = null;
////            try {
////                newDate = dateToCheck;
////            } catch(Exception e) {
////                Log.e(TAG, "Date Clone");
////            }
////            newDate.add(Calendar.DATE, 1);
////            String newDateString = myDateOutputFormat.format(newDate.getTime());
////
////            String dayValue = getDateValue(newDateString);
////            if(dayValue.equals("D")) {
////                if(i == 0) {
////                    return "Done";
////                } else if(i < minDays) {
////                    return "No need";
////                } else if(i < maxDays) {
////                    return "Should do";
////                } else {
////                    return "DO IT!";
////                }
////                // If it is a D day, find the diff between dateToCheck and this day and compare to min days to know what the status is
////            }
////        }
////
////        return "";
//        return null;
//    }
//}
