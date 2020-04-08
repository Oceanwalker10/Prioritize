package com.prioritize;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.FileUtils;
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

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    public static final int REQUEST_CODE_ADD = 4;

    List<Task> items = new ArrayList<>();
    RecyclerView rvItems;
    Button btnAdd;
    ItemsAdapter itemsAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rvItems = findViewById(R.id.rvItems);
        btnAdd = findViewById(R.id.btnAdd);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddActivity.class );
                startActivityForResult(intent, REQUEST_CODE_ADD);
            }
        });

        itemsAdapter = new ItemsAdapter(items);
        rvItems.setAdapter(itemsAdapter);
        rvItems.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_CODE_ADD:
                addTask((Task)Parcels.unwrap(data.getParcelableExtra("task")));
                displayMessage("Item was added");
                break;
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
}
