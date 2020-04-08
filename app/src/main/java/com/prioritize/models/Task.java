package com.prioritize.models;

import org.parceler.Parcel;

import java.util.Date;

@Parcel
public class Task {

    //Fields can't be private
    String title;
    String description;
    int priority;
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

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

}
