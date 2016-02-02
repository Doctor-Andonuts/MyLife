package com.doctor_andonuts.mylife;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Comparator;

/**
 * Created by jgowing on 11/13/2015.
 */
public class ChainComparator implements Comparator<Chain> {
    SimpleDateFormat myDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    final Calendar today = Calendar.getInstance();
    final String todayString = myDateFormat.format(today.getTime());

    @Override
    public int compare(Chain lhs, Chain rhs) {

        double[] lhsOnceOver = lhs.getOnceOverData(todayString);
        double[] rhsOnceOver = rhs.getOnceOverData(todayString);
        int lhsPercent = (int)(lhsOnceOver[0]*100 / lhsOnceOver[1]*100);
        int rhsPercent = (int)(rhsOnceOver[0]*100 / rhsOnceOver[1]*100);


        // return lhs.getTitle().compareTo(rhs.getTitle());
        return lhsPercent - rhsPercent;
    }
}
