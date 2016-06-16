package com.doctor_andonuts.mylife.Chain;

import android.util.Log;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class Chain {
    private final String TAG = "ChainClass";
    private final JSONObject chainJson;

    public Chain(JSONObject chainJson) {
        this.chainJson = chainJson;
    }
    public String getJsonString() {
        return chainJson.toString();
    }
    public String getTitle() {
        try {
            return chainJson.getString("Title");
        } catch (Exception e) {
            Log.v(TAG, "Error getting value Name");
        }
        return null;
    }
    public String getUUID() {
        try {
            return chainJson.getString("UUID");
        } catch (Exception e) {
            Log.v(TAG, "Error getting value UUID");
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
    // Gets JSONObject of all date data
    private JSONObject getDatesData() {
        try {
            return chainJson.getJSONObject("Dates");
        } catch (Exception e) {
            Log.e(TAG, "Error getting value DatesArray");
        }
        return null;
    }
    // Gets the actual value for a date
    public String getDateValue(String dateToCheck) {
        JSONObject datesData = getDatesData();
        try {
            if(datesData == null) {
                return "";
            } else if (datesData.has(dateToCheck)) {
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
        if(value == null || value.equals("null") || value.matches("\\d{4}-\\d{2}-\\d{2}")) {
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
    public void setDone(String date, String doneType) {
        String startDateString = getStartDate();
        String endDateString = getEndDate();
        SimpleDateFormat myDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);

        Calendar startDate = Calendar.getInstance();
        Calendar endDate = Calendar.getInstance();
        Calendar dateDone = Calendar.getInstance();

        try {
            startDate.setTime(myDateFormat.parse(startDateString));
            if(endDateString != null && !endDateString.equals("null")) { endDate.setTime(myDateFormat.parse(endDateString)); }
            dateDone.setTime(myDateFormat.parse(date));
        } catch (Exception e) {
            Log.e(TAG, "Parse Error");
        }

        if(dateDone.after(startDate) || dateDone.equals(startDate)) {
            if(endDateString == null || dateDone.before(endDate) || dateDone.equals(endDate)) {
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
                        doneTypeAbbreviation = "D";
                        break;
                    default:
                        doneTypeAbbreviation = "";
                        break;
                }

                JSONObject datesData = getDatesData();
                try {
                    if(datesData != null) {
                        if (doneTypeAbbreviation.equals("")) {
                            datesData.remove(date);
                        } else {
                            datesData.put(date, doneTypeAbbreviation);
                        }
                    }
                } catch (Exception e) {
                    Log.e(TAG, "setDone");
                }
            }
        }
    }

    // Checks to see if I need to do a chain on a particular date
    public String getDayStatus(String dateToCheckString) {
        // TODO: Don't return things if the date to check if before start or after end date
        if(getType().equals("MinMax")) {
            Integer maxDays = getMaxDays();
            Integer minDays = getMinDays();

            if (maxDays != null) {

                SimpleDateFormat myDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
                Calendar dateToCheck = Calendar.getInstance();

                try {
                    dateToCheck.setTime(myDateFormat.parse(dateToCheckString));
                } catch (Exception e) {
                    Log.e(TAG, "Date Parse Error");
                }

                for (int i = 0; i <= maxDays; i++) {
                    Calendar newDate = Calendar.getInstance();

                    try {
                        newDate.setTime(myDateFormat.parse(dateToCheckString));
                    } catch (Exception e) {
                        Log.e(TAG, "Date Clone");
                    }
                    newDate.add(Calendar.DATE, -i);
                    String newDateString = myDateFormat.format(newDate.getTime());

                    String dayValue = getDateValue(newDateString);
                    if (i == 0) {
                        if (dayValue.equals("D")) {
                            return "Done";
                        } else if (dayValue.equals("V") || dayValue.equals("S") || dayValue.equals("O")) {
                            return "Offday";
                        }
                    }
                    if (dayValue.equals("V")) {
                        maxDays++;
                    }
                    if (dayValue.equals("D")) {
                        if (i < minDays) {
                            return "No need";
                        } else if (i < maxDays) {
                            return "Should do";
                        }
                    }
                }
                return "DO IT!";
            }
        } else {
/*
            Red means I have to do it today.
            1 in 1, 2 in 2, 3 in 3, etc
            return "DO IT!";

            Yellow means I should do it (50% or more)
            1 in 2, 3 in 4, 4 in 5, etc
            return "Should do";

            Dark green is don't have to (less then 50%)
            1 in 3, 3 in 7, 2 in 5, etc
            return "No need";
*/

            double[] onceOverData = getOnceOverData(dateToCheckString);
            if (onceOverData[0] == -1 && onceOverData[1] == -1) {
                return "Done";
            } else if (onceOverData[0] >= onceOverData[1]) {
                return "DO IT!";
            } else if (onceOverData[0] / onceOverData[1] >= 0.5) {
                return "Should do";
            } else {
                return "No need";
            }

//            String dayValue = getDateValue(dateToCheckString);
//            if (dayValue.equals("D")) {
//                return "Done";
//            } else {
//                return "DO IT!";
//            }
        }
        return "";
    }

    public int getCurrentLength(String lastDoneDate) {
        SimpleDateFormat myDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        int counter = 0;
        int chainLength = 0;
        if(getType().equals("MinMax")) {
            int maxDays = getMaxDays();
            boolean chainContinues = true;

            if(getDateValue(lastDoneDate).equals("D")) {
                chainLength++;
            }

            while(chainContinues) {
                counter++;
                if(counter == 1000) { // Catch for no looping forever
                    break;
                }
                boolean foundDate = false;

                for (int i = 1; i <= maxDays; i++) {
                    Calendar newDateToCheck = Calendar.getInstance();
                    try {
                        newDateToCheck.setTime(myDateFormat.parse(lastDoneDate));
                    } catch(Exception e) {
                        Log.e(TAG, "Could not set time correctly");
                    }
                    newDateToCheck.add(Calendar.DATE, -i);
                    String newDateToCheckString = myDateFormat.format(newDateToCheck.getTime());
                    if(getDateValue(newDateToCheckString).equals("V") ||  getDateValue(newDateToCheckString).equals("S") || getDateValue(newDateToCheckString).equals("O")) {
                        maxDays++;
                    } else if(getDateValue(newDateToCheckString).equals("D")) {
                        foundDate = true;
                        lastDoneDate = newDateToCheckString;
                        chainLength++;
                        break;
                    }

                }
                if(!foundDate) {
                    chainContinues = false;
                }
            }
        } else {
            Calendar today = Calendar.getInstance();
            int todayDayOfWeek = today.get(Calendar.DAY_OF_WEEK); // Sunday == 1, Saturday == 7

            int dayCounterOffset = 0;
            if(todayDayOfWeek == 1) { // Sunday
                dayCounterOffset = 0;
            } else if(todayDayOfWeek >= 2) { // Monday
                dayCounterOffset = 8 - todayDayOfWeek;
            }

            // TODO: This happens twice on start? (the whole loop that is)
            boolean chainContinues = true;
            while(chainContinues) {
                int weeklyDoneCounter = 0;

                for (int i = 0; i < 7; i++) {
                    // This goes back 7 days and counts, so this needs to start on a Sunday.
                    // Maybe I start on the next (or current) sunday in the future to count the current week?
                    // Then loop 1 week at a time for that until I don't continue
                    Calendar newDateToCheck = Calendar.getInstance();
                    try {
                        newDateToCheck.setTime(myDateFormat.parse(lastDoneDate));
                    } catch (Exception e) {
                        Log.e(TAG, "Could not set time correctly");
                    }
                    newDateToCheck.add(Calendar.DATE, -i + dayCounterOffset);
                    String newDateToCheckString = myDateFormat.format(newDateToCheck.getTime());
                    if (getDateValue(newDateToCheckString).equals("D")) {
                        weeklyDoneCounter++;
                    }
                }

                if(dayCounterOffset >= 0) { // if it is the current week
                    chainLength += weeklyDoneCounter;
                } else if(weeklyDoneCounter >= getPerWeekValue()) {
                    chainLength += weeklyDoneCounter;
                }

                if(dayCounterOffset < 0 && weeklyDoneCounter < getPerWeekValue()) {
                    chainContinues = false;
                }

                dayCounterOffset = dayCounterOffset - 7;
            }
        }

        return chainLength;
    }

    // Get string containing a quick once over
    public double[] getOnceOverData(String dateToCheckString) {
        SimpleDateFormat myDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        double[] returnValue = new double[2];

        if(getType().equals("MinMax")) {

            Integer maxDays = getMaxDays();
            Integer minDays = getMinDays();

            Calendar dateToCheck = Calendar.getInstance();

            try {
                dateToCheck.setTime(myDateFormat.parse(dateToCheckString));
            } catch (Exception e) {
                Log.e(TAG, "Date Parse Error");
            }

            for (int i = 0; i <= maxDays; i++) {
                Calendar newDate = Calendar.getInstance();

                try {
                    newDate.setTime(myDateFormat.parse(dateToCheckString));
                } catch (Exception e) {
                    Log.e(TAG, "Date Clone");
                }
                newDate.add(Calendar.DATE, -i);
                String newDateString = myDateFormat.format(newDate.getTime());

                String dayValue = getDateValue(newDateString);
                if (dayValue.equals("D")) {
                    if (i == 0) {
                        returnValue[0] = -1;
                        returnValue[1] = -1;
                        return returnValue;
                    } else if (i < minDays) {
                        returnValue[0] = 0;
                        returnValue[1] = (maxDays - i + 1);
                        return returnValue;
                    } else if (i < maxDays) {
                        returnValue[0] = 1;
                        returnValue[1] = (maxDays - i + 1);
                        return returnValue;
                    }
                }
            }
            returnValue[0] = 1;
            returnValue[1] = 1;
            return returnValue;
        } else {
            Calendar dateToCheck = Calendar.getInstance();
            try {
                dateToCheck.setTime(myDateFormat.parse(dateToCheckString));
            } catch (Exception e) {
                Log.e(TAG, "Date Clone");
            }

            int todayDayOfWeek = dateToCheck.get(Calendar.DAY_OF_WEEK);
            // Sunday == 1, Saturday == 7
            // I want Monday to be one and Sunday to be 7
            todayDayOfWeek -= 1;
            if(todayDayOfWeek == 0) { todayDayOfWeek = 7; } // Monday == 1, Sunday == 7
            int daysLeftInWeek = 8 - todayDayOfWeek;

            int timesDoneThisWeek = 0;
            for (int i = 0; i < todayDayOfWeek; i++) {
                Calendar newDate = Calendar.getInstance();

                try {
                    newDate.setTime(myDateFormat.parse(dateToCheckString));
                } catch (Exception e) {
                    Log.e(TAG, "Date Clone");
                }
                newDate.add(Calendar.DATE, -i);
                String newDateString = myDateFormat.format(newDate.getTime());

                String dayValue = getDateValue(newDateString);
                if (dayValue.equals("D")) {
                    if(i==0) {
                        returnValue[0] = -1;
                        returnValue[1] = -1;
                        return returnValue;
                    }
                    timesDoneThisWeek++;
                }
            }

            Integer perWeekValue = getPerWeekValue();
            int stillNeedToDoThisWeek = perWeekValue - timesDoneThisWeek;
            if (stillNeedToDoThisWeek < 0) {
                stillNeedToDoThisWeek = 0;
            }

            returnValue[0] = stillNeedToDoThisWeek;
            returnValue[1] = daysLeftInWeek;
            return returnValue;
        }
    }

    public String getOnceOverString(String dateToCheckString) {
        double[] data;
        data = getOnceOverData(dateToCheckString);
        if(data[0] == -1 && data[1] == -1 ) {
            return "Done";
        }
        return (int)data[0] + " in " + (int)data[1];
    }
}
