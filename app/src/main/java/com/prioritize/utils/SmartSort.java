package com.prioritize.utils;

import com.prioritize.models.Task;

import org.jetbrains.annotations.NotNull;

import java.util.Comparator;

public class SmartSort implements Comparator<Task> {
    /*
    Turns out my algorithm matched Izzy's in functionality exactly :)
    A revisit is still possible.
    */
    @Override
    public int compare(@NotNull Task o1, @NotNull Task o2) {
        long timeDiff = o1.getDueDate().getTime() - o2.getDueDate().getTime(); //time in milliseconds
        timeDiff /= 1000 * 3600 * 24; //convert to days
        int priorityDiff = o2.getPriority() - o1.getPriority();
        if (Math.abs(timeDiff) > Math.abs(priorityDiff)) {
            return new DueDateSort().compare(o1, o2);
        } else {
            return new PrioritySort().compare(o1, o2);
        }
    }
}
