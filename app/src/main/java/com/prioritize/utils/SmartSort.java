package com.prioritize.utils;

import com.prioritize.models.Task;

import org.jetbrains.annotations.NotNull;

import java.util.Comparator;

public class SmartSort implements Comparator<Task> {
    /*
    Temporary algorithm for smart-sort because I don't remember what it was going to be.
    If the difference in the time of two activities is greater than the difference in their priorities,
    it returns the one due sooner. If not, it returns the one with a higher priority.
    */
    @Override
    public int compare(@NotNull Task o1, @NotNull Task o2) {
        long timeDiff = o1.getDueDate().getTime() - o2.getDueDate().getTime();
        int priorityDiff = o2.getPriority() - o1.getPriority();
        if (Math.abs(timeDiff) > Math.abs(priorityDiff)) {
            return (int)Math.signum(timeDiff);
        } else {
            return priorityDiff;
        }
    }
}
