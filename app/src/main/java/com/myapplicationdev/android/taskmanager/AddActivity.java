package com.myapplicationdev.android.taskmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Calendar;

public class AddActivity extends AppCompatActivity {

    Button btnAddTask, btnCancel;
    EditText etName, etDescription, etTimer;
    int reqCode = 12345;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        btnAddTask = (Button) findViewById(R.id.btnAddTask);
        btnCancel = (Button) findViewById(R.id.btnCancel);
        etName = (EditText) findViewById(R.id.etName);
        etDescription = (EditText) findViewById(R.id.etDescription);
        etTimer = (EditText) findViewById(R.id.etTimer);

        btnAddTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etName.getText().toString().length() == 0 ||
                        etDescription.getText().toString().length() == 0 || etTimer.getText().toString().length() == 0) {

                    Toast.makeText(AddActivity.this, "Incomplete data", Toast.LENGTH_SHORT).show();
                }else {
                    DBHelper dbh = new DBHelper(AddActivity.this);

                    String name = etName.getText().toString();
                    String description = etDescription.getText().toString();
                    int time = Integer.parseInt(etTimer.getText().toString());

                    long inserted_id = dbh.insertTask(name, description);
                    dbh.close();
                    if (inserted_id != -1) {
                        Toast.makeText(AddActivity.this, "Insert successful",
                                Toast.LENGTH_SHORT).show();
                        Calendar cal = Calendar.getInstance();
                        cal.add(Calendar.SECOND, time);

                        Intent intent = new Intent(AddActivity.this, ScheduledNotificationReceiver.class);
                        intent.putExtra("name", name);
                        intent.putExtra("description", description);

                        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                                AddActivity.this, reqCode, intent, PendingIntent.FLAG_CANCEL_CURRENT);

                        AlarmManager am = (AlarmManager) getSystemService(Activity.ALARM_SERVICE);
                        am.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), pendingIntent);

                        finish();
                    }
                }
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AddActivity.this, MainActivity.class);
                startActivity(i);
            }
        });
    }
}
