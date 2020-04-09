package com.prioritize;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.NumberPicker;

import androidx.appcompat.app.AppCompatActivity;

import com.prioritize.models.Task;

import java.util.Date;
import java.util.GregorianCalendar;

public class EditActivity extends AppCompatActivity {

    private static final String KEY_ITEM_TEXT = "item_detail";
    private static final String KEY_ITEM_POSITION = "item_position";
    private static final String TAG = "EditActivity";

    private EditText etTitle;
    private EditText etDescription;
    private NumberPicker npPriority;
    private CalendarView cvDueDate;
    private Button btnUpdate;
    private Task pendingTask;
    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        etTitle = findViewById(R.id.etTitle);
        etDescription = findViewById(R.id.etDescription);
        npPriority = findViewById(R.id.npPriority);
        btnUpdate = findViewById(R.id.btnUpdate);
        cvDueDate = findViewById(R.id.cvDueDate);

        configureNumberPicker();
        setTaskDetail();

        btnUpdate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if(checkRequirement()) {
                    addTask();
                    Intent intent = new Intent(EditActivity.this, MainActivity.class);
                    intent.putExtra(MainActivity.KEY_ITEM_TEXT, pendingTask);
                    intent.putExtra(MainActivity.KEY_ITEM_POSITION, position);

                    setResult(RESULT_OK, intent);
                    finish();
                }
            }
        });

        cvDueDate.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {

            @Override
            public void onSelectedDayChange(CalendarView view, int year, int monthOfYear, int dayOfMonth) {
                GregorianCalendar date = new GregorianCalendar(year,monthOfYear,dayOfMonth);
                long timestamp = date.getTimeInMillis();
                cvDueDate.setDate(timestamp);
            }
        });
    }

    NumberPicker.OnValueChangeListener onValueChangeListener =
            new NumberPicker.OnValueChangeListener(){
                @Override
                public void onValueChange(NumberPicker numberPicker, int i, int i1) {
                    npPriority = numberPicker;
                }
            };

    private void configureNumberPicker() {
        npPriority.setMinValue(1);
        npPriority.setMaxValue(5);
        npPriority.setOnValueChangedListener(onValueChangeListener);
    }

    private void setTaskDetail() {
        pendingTask = (Task) getIntent().getSerializableExtra(KEY_ITEM_TEXT);

        getPositionOfTask();

        long date = pendingTask.getDueDate().getTime();
        cvDueDate.setDate(date);

        etTitle.setText(pendingTask.getTitle());
        etDescription.setText(pendingTask.getDescription());
        npPriority.setValue(pendingTask.getPriority());

    }

    private void getPositionOfTask() {
        position = getIntent().getExtras().getInt(MainActivity.KEY_ITEM_POSITION);
    }

    private boolean checkRequirement() {
        if (etTitle.getText().toString().trim().isEmpty()) {
            return false;
        } else {
            return true;
        }
    }

    private void addTask() {
        pendingTask.setTitle(etTitle.getText().toString().trim());
        pendingTask.setDescription(etDescription.getText().toString().trim());
        pendingTask.setPriority(npPriority.getValue());
        Date date = new Date(cvDueDate.getDate());
        pendingTask.setDueDate(date);
    }
}
