package com.myapplicationdev.android.taskmanager;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class TaskAdapter extends ArrayAdapter {
    Context context;
    ArrayList<Task> tasks;
    int resource;
    TextView tvName, tvDescription;

    public TaskAdapter(Context context, int resource, ArrayList<Task> tasks) {
        super(context, resource, tasks);
        this.context = context;
        this.tasks = tasks;
        this.resource = resource;
}

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(R.layout.row, parent, false);

        //Match the UI components with Java variables
        tvName = (TextView) rowView.findViewById(R.id.tvName);
        tvDescription = (TextView)rowView.findViewById(R.id.tvDescription);

        Task task = tasks.get(position);

        tvName.setText(task.getId() + " " + task.getTaskName());
        tvDescription.setText(task.getDescription());

        return rowView;
    }

}
