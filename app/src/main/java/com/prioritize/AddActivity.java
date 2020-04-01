package com.prioritize;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;

public class AddActivity extends AppCompatActivity {

    EditText etTitle;
    EditText etDescription;
    RadioGroup rgPriority;
    EditText etDueDate;
    Button btnAddTask;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        etTitle = findViewById(R.id.etTitle);
        etDescription=findViewById(R.id.etDescription);
        rgPriority=findViewById(R.id.rgPriority);
        etDueDate=findViewById(R.id.etDueDate);
        btnAddTask=findViewById(R.id.btnAddTask);

    }
}