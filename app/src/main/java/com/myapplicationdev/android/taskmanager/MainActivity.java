package com.myapplicationdev.android.taskmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Button btnAddTask;
    ListView lv;
    ArrayAdapter aa;
    ArrayList<Task> al;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnAddTask = (Button) findViewById(R.id.btnAddTask);
        lv = (ListView) findViewById(R.id.lv);

        DBHelper db = new DBHelper(MainActivity.this);
        al = db.getAllTasks();
        db.close();

        aa = new TaskAdapter(this, R.layout.row, al);
        lv.setAdapter(aa);

        btnAddTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, AddActivity.class);
                startActivity(i);
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        DBHelper db = new DBHelper(MainActivity.this);
        al.clear();
        al = db.getAllTasks();
        db.close();

        aa = new TaskAdapter(this, R.layout.row, al);
        lv.setAdapter(aa);
    }
}
