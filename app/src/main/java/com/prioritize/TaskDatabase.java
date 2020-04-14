package com.prioritize;


import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.prioritize.models.Task;
import com.prioritize.models.TaskDao;
import com.prioritize.utils.DateConverter;

@Database(entities={Task.class}, version = 1)
@TypeConverters(DateConverter.class)
public abstract class TaskDatabase extends RoomDatabase {
    public abstract TaskDao taskDao();

    public static final String NAME = "TaskDatabase";
}
