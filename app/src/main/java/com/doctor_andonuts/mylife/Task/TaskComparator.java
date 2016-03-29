package com.doctor_andonuts.mylife.Task;

import java.util.Comparator;

public class TaskComparator implements Comparator<Task> {

    @Override
    public int compare(Task lhs, Task rhs) {
        return rhs.getUrgency().compareTo(lhs.getUrgency());
    }
}