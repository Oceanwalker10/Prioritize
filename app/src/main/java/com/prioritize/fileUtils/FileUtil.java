package com.prioritize.fileUtils;

import android.content.Context;

import com.prioritize.models.Task;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class FileUtil {
    private static final String FILENAME = "task.xml";
    private static File file;
    private static FileOutputStream fileOutputStream = null;

    public static void createFile(Context ctx) {
        try {
            fileOutputStream = ctx.openFileOutput(FILENAME, Context.MODE_APPEND);
            fileOutputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void writeTask(List<Task> items) {
        int index = 0;
        try {
            fileOutputStream = new FileOutputStream(file);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            while(items.size() > index) {
                objectOutputStream.writeObject(items.get(index));
                index++;
            }
            objectOutputStream.close();
            fileOutputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<Task> readFile(Context ctx) {
        List<Task> items = new ArrayList<>();
        file = ctx.getFileStreamPath(FILENAME);
        FileInputStream fileInputStream = null;
        ObjectInputStream objectInputStream = null;
        try {
            fileInputStream= new FileInputStream(file);
            objectInputStream = new ObjectInputStream(fileInputStream);
            while(true){
                items.add((Task) objectInputStream.readObject());
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return items;
    }
}
