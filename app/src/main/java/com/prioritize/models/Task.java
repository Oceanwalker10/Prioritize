package com.prioritize.models;

import android.util.Log;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.parceler.Parcel;

import static com.prioritize.adapters.ItemsAdapter.TAG;


@Parcel
@Entity
public class Task {

    //Fields can't be private
    @ColumnInfo
    @PrimaryKey(autoGenerate = true)
    long id;
    @ColumnInfo
    String title;
    @ColumnInfo
    String description;
    @ColumnInfo
    int priority;
    @ColumnInfo
    Date dueDate;

    public Task() {} //Parceler needs this

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public Date getDueDate() { return dueDate; }

    public void setDueDate(Date date) { this.dueDate = date; }

    public int daysToDue() {
        Log.d(TAG, "OverDue has been called");
        Calendar calendar = Calendar.getInstance();
        Date currentDate = calendar.getTime();
        return (int) (dueDate.getTime() / (1000 * 3600 * 24) - currentDate.getTime() / (1000 * 3600 * 24));
    }
}
