package com.prioritize.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import org.parceler.Parcel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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

}
