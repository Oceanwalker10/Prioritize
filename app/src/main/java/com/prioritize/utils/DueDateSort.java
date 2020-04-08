package com.prioritize.utils;

import com.prioritize.models.Task;

import org.jetbrains.annotations.NotNull;

import java.util.Comparator;

public class DueDateSort implements Comparator<Task> {
    @Override
    public int compare(@NotNull Task o1, @NotNull Task o2) {
        return o1.getDueDate().compareTo(o2.getDueDate());
    }
}
