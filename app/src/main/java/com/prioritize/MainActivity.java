package com.prioritize;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.prioritize.adapters.ItemsAdapter;
import com.prioritize.models.Task;
import com.prioritize.utils.DueDateSort;
import com.prioritize.utils.PrioritySort;
import com.prioritize.utils.SmartSort;

import org.parceler.Parcels;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    public static final String KEY_ITEM_TEXT = "item_detail";
    public static final String KEY_ITEM_POSITION = "item_position";
    public static final int EDIT_TEXT_CODE = 0;
    public static final int REQUEST_CODE_ADD = 4;


    private List<Task> items = new ArrayList<>();
    private RecyclerView rvItems;
    private Button btnAdd;
    private ItemsAdapter itemsAdapter;
    private final String FILENAME = "task";
    private File file;
    private FileOutputStream fileOutputStream = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rvItems = findViewById(R.id.rvItems);
        btnAdd = findViewById(R.id.btnAdd);

        createFile();
        readFile();

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddActivity.class );
                startActivityForResult(intent, REQUEST_CODE_ADD);
            }
        });

        ItemsAdapter.OnLongClickListener onLongClickListener = new ItemsAdapter.OnLongClickListener() {
            @Override
            public void onItemLongClicked(int position) {
                // Delete the item from the model
                items.remove(position);
                // Notify the adapter
                itemsAdapter.notifyItemRemoved(position);
                displayMessage("Item was removed");
            }
        };

        ItemsAdapter.OnClickListener onClickListener = new ItemsAdapter.OnClickListener() {
            @Override
            public void onItemClicked(int position) {
                // create the new activity
                Intent intent = new Intent(MainActivity.this, EditActivity.class);
                // display the activity
                intent.putExtra(KEY_ITEM_TEXT, items.get(position));
                intent.putExtra(KEY_ITEM_POSITION, position);
                startActivityForResult(intent, EDIT_TEXT_CODE);
            }
        };

        itemsAdapter = new ItemsAdapter(items, onLongClickListener, onClickListener);
        rvItems.setAdapter(itemsAdapter);
        rvItems.setLayoutManager(new LinearLayoutManager(this));

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == EDIT_TEXT_CODE) {
            Task pendingTask = (Task) data.getSerializableExtra(KEY_ITEM_TEXT);
            int position = data.getExtras().getInt(KEY_ITEM_POSITION);

            items.set(position, pendingTask);
            itemsAdapter.notifyItemChanged(position);
            writeTask();
            displayMessage("Task updated successfully!");
        } else if(resultCode == RESULT_OK && requestCode == REQUEST_CODE_ADD){
            Task pendTask = (Task)Parcels.unwrap(data.getParcelableExtra("task"));
            addTask(pendTask);
            writeTask();
            displayMessage("Item was added");
        }
    }

    private void addTask(Task task) {
        int addPos = 0;
        for (int i = 0; i <= items.size() - 1; i++) {
            if (task.getPriority() >= items.get(i).getPriority()) {
                addPos = i + 1;
            }
        }

        items.add(addPos, task);
        itemsAdapter.notifyItemInserted(addPos);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.miPriority:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    items.sort(new PrioritySort());
                    itemsAdapter.notifyDataSetChanged();
                } else {
                    Log.e(TAG, "Insufficient API level");
                }
                break;
            case R.id.miDueDate:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    items.sort(new DueDateSort());
                    itemsAdapter.notifyDataSetChanged();
                } else {
                    Log.e(TAG, "Insufficient API level");
                }
                break;
            case R.id.miSmart:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    items.sort(new SmartSort());
                    itemsAdapter.notifyDataSetChanged();
                } else {
                    Log.e(TAG, "Insufficient API level");
                }
                break;
        }
        return true;
    }

    private void displayMessage(String message) { //convenience method for toasts
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private void createFile() {
        try {
            fileOutputStream = openFileOutput(FILENAME, Context.MODE_APPEND);
            fileOutputStream.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void writeTask() {
        int index = 0;
        try {
            fileOutputStream = new FileOutputStream(file);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            while(items.size() > index) {
                objectOutputStream.writeObject(items.get(index));
                Log.d(TAG, items.get(index).toString());
                index ++;
            }
            objectOutputStream.close();
            fileOutputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void readFile() {
        file = getFileStreamPath(FILENAME);
        FileInputStream fileInputStream = null;
        ObjectInputStream objectInputStream = null;
        Task task = null;
        try {
            fileInputStream= new FileInputStream(file);
            objectInputStream = new ObjectInputStream(fileInputStream);
            while((task = (Task) objectInputStream.readObject()) != null){
                Log.d(TAG, objectInputStream.readObject().toString());
                items.add(task);
            }
            fileInputStream.close();
            objectInputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

}
