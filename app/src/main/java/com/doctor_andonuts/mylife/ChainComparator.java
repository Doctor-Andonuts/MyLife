package com.doctor_andonuts.mylife;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Locale;

/**
 * Created by jgowing on 11/13/2015.
 *
 * Sorting comparator, currently sorts by using once over text
 */
class ChainComparator implements Comparator<Chain> {
    private final SimpleDateFormat myDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
    private final Calendar today = Calendar.getInstance();
    private final String todayString = myDateFormat.format(today.getTime());

    @Override
    public int compare(Chain lhs, Chain rhs) {

        double[] lhsOnceOver = lhs.getOnceOverData(todayString);
        double[] rhsOnceOver = rhs.getOnceOverData(todayString);

        int lhsPercent = -1;
        if(lhsOnceOver[0] != -1 || lhsOnceOver[1] != -1) {
            lhsPercent = (int) ((lhsOnceOver[0] / lhsOnceOver[1]) * 100);
        }
        int rhsPercent = -1;
        if(rhsOnceOver[0] != -1 || rhsOnceOver[1] != -1) {
            rhsPercent = (int) ((rhsOnceOver[0] / rhsOnceOver[1]) * 100);
        }

        return rhsPercent - lhsPercent;
    }
}
