package com.prioritize;

import android.app.Application;

import androidx.room.Room;

public class PrioritizeApplication extends Application {

        TaskDatabase myDatabase;

        @Override
        public void onCreate() {
            super.onCreate();

            // when upgrading versions, kill the original tables by using fallbackToDestructiveMigration()
            myDatabase = Room.databaseBuilder(this, TaskDatabase.class, TaskDatabase.NAME).fallbackToDestructiveMigration().build();
        }

        public TaskDatabase getMyDatabase() {
            return myDatabase;
        }
}
