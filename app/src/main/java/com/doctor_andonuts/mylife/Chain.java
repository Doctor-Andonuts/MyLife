package com.doctor_andonuts.mylife;

import android.util.Log;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Chain {
    private String TAG = "ChainClass";
    private JSONObject chainJson;

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

    public void setDone(String date, String doneType) {
        String startDateString = getStartDate();
        String endDateString = getEndDate();
        SimpleDateFormat myDateFormat = new SimpleDateFormat("yyyy-MM-dd");

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
                    if(doneTypeAbbreviation.equals("")) {
                        datesData.remove(date);
                    } else {
                        datesData.put(date, doneTypeAbbreviation);
                    }
                } catch (Exception e) {
                    Log.e(TAG, "setDone");
                }
            }
        }
    }


    public String getDayStatus(String dateToCheckString) {
        // TODO: Don't return things if the date to check if before start or after end date
        if(getType().equals("MinMax")) {
            Integer maxDays = getMaxDays();
            Integer minDays = getMinDays();

            if (maxDays != null) {

                SimpleDateFormat myDateFormat = new SimpleDateFormat("yyyy-MM-dd");
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
                            return "Done";
                        } else if (i < minDays) {
                            return "No need";
                        } else if (i < maxDays) {
                            return "Should do";
                        }
                    }
                }
                return "DO IT!";
            }
        } else {
            String dayValue = getDateValue(dateToCheckString);
            if (dayValue.equals("D")) {
                return "Done";
            } else {
                return "DO IT!";
            }
        }
        return "";
    }


    public String getOnceOver(String dateToCheckString) {
        SimpleDateFormat myDateFormat = new SimpleDateFormat("yyyy-MM-dd");

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
                        return "Done";
                    } else if (i < minDays) {
                        return "0 in " + maxDays;
                    } else if (i < maxDays) {
                        return "1 in " + (i - 1 - maxDays);
                    }
                }
            }
            return "1 in 1";
        } else {
            Calendar todayDate = Calendar.getInstance();
            int todayDayOfWeek = todayDate.get(Calendar.DAY_OF_WEEK);
            // Sunday == 1, Saturday == 7
            // I want Monday to be one and Sunday to be 7
            todayDayOfWeek -= 1;
            if(todayDayOfWeek == 0) { todayDayOfWeek = 7; }
            int daysLeftInWeek = 8 - todayDayOfWeek;

            int timesDoneThisWeek = 0;
            for (int i = 0; i <= todayDayOfWeek; i++) {
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
                    daysLeftInWeek--;
                    timesDoneThisWeek++;
                }
            }

            Integer perWeekValue = getPerWeekValue();
            int stillNeedToDoThisWeek = perWeekValue - timesDoneThisWeek;

            return stillNeedToDoThisWeek + " in " + daysLeftInWeek;
        }
    }
}
