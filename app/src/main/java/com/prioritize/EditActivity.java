package com.prioritize;

import android.content.Intent;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.NumberPicker;

import androidx.appcompat.app.AppCompatActivity;

import com.prioritize.models.Task;

import org.jetbrains.annotations.NotNull;
import org.parceler.Parcels;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class EditActivity extends AppCompatActivity {

    private static final String TAG = "EditActivity";

    private EditText etTitle;
    private EditText etDescription;
    private NumberPicker npPriority;
    private CalendarView cvDueDate;
    private Button btnUpdate;
    private Task pendingTask;
    private Button btnAddToCalendar;
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
        btnAddToCalendar = findViewById(R.id.btnAddToCalendar);

        configureNumberPicker();
        setTaskDetail();

        btnUpdate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if(checkRequirement()) {
                    addTask();
                    Intent data = new Intent(EditActivity.this, MainActivity.class);
                    data.putExtra(MainActivity.KEY_ITEM_TEXT, Parcels.wrap(pendingTask));
                    data.putExtra(MainActivity.KEY_ITEM_POSITION, position);

                    setResult(RESULT_OK, data);
                    finish();
                }
            }
        });

        cvDueDate.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {

            @Override
            public void onSelectedDayChange(@NotNull CalendarView view, int year, int monthOfYear, int dayOfMonth) {
                GregorianCalendar date = new GregorianCalendar(year,monthOfYear,dayOfMonth);
                long timestamp = date.getTimeInMillis();
                cvDueDate.setDate(timestamp);
            }
        });

        btnAddToCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addTask();
                setCalendarEvent();
            }
        });
    }

    private void setCalendarEvent() {
        Intent calIntent = new Intent(Intent.ACTION_INSERT);
        calIntent.setType("vnd.android.cursor.item/event");
        calIntent.putExtra(CalendarContract.Events.TITLE, pendingTask.getTitle());
        calIntent.putExtra(CalendarContract.Events.DESCRIPTION, pendingTask.getDescription());
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(pendingTask.getDueDate());
        GregorianCalendar calDate = new GregorianCalendar(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        calIntent.putExtra(CalendarContract.EXTRA_EVENT_ALL_DAY, true);
        calIntent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME,
                calDate.getTimeInMillis());

        startActivity(calIntent);
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
        pendingTask = Parcels.unwrap(getIntent().getParcelableExtra(MainActivity.KEY_ITEM_TEXT));

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
        return !etTitle.getText().toString().trim().isEmpty();
    }

    private void addTask() {
        pendingTask.setTitle(etTitle.getText().toString().trim());
        pendingTask.setDescription(etDescription.getText().toString().trim());
        pendingTask.setPriority(npPriority.getValue());
        Date date = new Date(cvDueDate.getDate());
        pendingTask.setDueDate(date);
    }
}
