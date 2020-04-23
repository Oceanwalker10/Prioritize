package com.prioritize;

import android.content.Intent;
import android.os.AsyncTask;
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
import com.prioritize.models.TaskDao;
import com.prioritize.utils.DueDateSort;
import com.prioritize.utils.PrioritySort;
import com.prioritize.utils.SmartSort;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    public static final String KEY_ITEM_TEXT = "item_detail";
    public static final String KEY_ITEM_POSITION = "item_position";
    public static final int REQUEST_CODE_EDIT = 0;
    public static final int REQUEST_CODE_ADD = 4;


    private List<Task> items = new ArrayList<>();
    private RecyclerView rvItems;
    private Button btnAdd;
    private ItemsAdapter itemsAdapter;

    private Comparator<Task> sorter = new SmartSort();

    TaskDao taskDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        taskDao = ((PrioritizeApplication) getApplicationContext()).getMyDatabase().taskDao();

        rvItems = findViewById(R.id.rvItems);
        btnAdd = findViewById(R.id.btnAdd);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddActivity.class );
                startActivityForResult(intent, REQUEST_CODE_ADD);
            }
        });

        ItemsAdapter.OnLongClickListener onLongClickListener = new ItemsAdapter.OnLongClickListener() {
            @Override
            public void onItemLongClicked(final int position) {
                final Task deletedTask = items.get(position);
                AsyncTask.execute(new Runnable() {
                    @Override
                    public void run() {
                        taskDao.deleteTask(deletedTask);
                    }
                });
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
                intent.putExtra(KEY_ITEM_TEXT, Parcels.wrap(items.get(position)));
                intent.putExtra(KEY_ITEM_POSITION, position);
                startActivityForResult(intent, REQUEST_CODE_EDIT);
            }
        };

        itemsAdapter = new ItemsAdapter(items, onLongClickListener, onClickListener, getApplicationContext());
        rvItems.setAdapter(itemsAdapter);
        rvItems.setLayoutManager(new LinearLayoutManager(this));

        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                items.addAll(taskDao.getTasks());
                //If background thread tries to run this, Android says NOPE
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        reSort();
                    }
                });
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_CODE_EDIT:
                    final Task pendingTask = Parcels.unwrap(data.getParcelableExtra(KEY_ITEM_TEXT));
                    final int position = data.getExtras().getInt(KEY_ITEM_POSITION);
                    final Task editTask = items.get(position);

                    AsyncTask.execute(new Runnable() {
                        @Override
                        public void run() {
                            taskDao.deleteTask(editTask);
                            taskDao.insertTask(pendingTask);
                        }
                    });

                    items.set(position, pendingTask);
                    reSort();
                    itemsAdapter.notifyDataSetChanged();
                    displayMessage("Task updated successfully!");
                    break;
                case REQUEST_CODE_ADD:
                    final Task newTask = Parcels.unwrap(data.getParcelableExtra(KEY_ITEM_TEXT));
                    AsyncTask.execute(new Runnable() {
                        @Override
                        public void run() {
                            taskDao.insertTask(newTask);
                        }
                    });
                
                    addTask(newTask);
                    displayMessage("Item was added");
                default:
                    Log.e(TAG, "Invalid request code");
            }
        }
    }

    private void addTask(Task task) {
        int addPos = 0;
        for (int i = items.size() - 1; i >= 0; i--) {
            if (sorter.compare(task, items.get(i)) >= 0) {
                addPos = i + 1;
                break;
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
                sorter = new PrioritySort();
                reSort();
                break;
            case R.id.miDueDate:
                sorter = new DueDateSort();
                reSort();
                break;
            case R.id.miSmart:
                sorter = new SmartSort();
                reSort();
                break;
        }
        return true;
    }

    private void reSort() {
        Collections.sort(items, sorter);
        itemsAdapter.notifyDataSetChanged();
    }

    private void displayMessage(String message) { //convenience method for toasts
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
