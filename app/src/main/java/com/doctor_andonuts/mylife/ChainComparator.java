package com.doctor_andonuts.mylife;

import java.util.Comparator;

/**
 * Created by jgowing on 11/13/2015.
 */
public class ChainComparator implements Comparator<Chain> {

    @Override
    public int compare(Chain lhs, Chain rhs) {
        return rhs.getTitle().compareTo(lhs.getTitle());
    }
}
