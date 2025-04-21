package com.example.taskmanager;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.List;

public class MainScreenActivity extends AppCompatActivity {
    private TaskRecyclerAdapter taskAdapter;

    @Override
    protected void onCreate(Bundle savedState) {
        super.onCreate(savedState);
        setContentView(R.layout.screen_main);

        RecyclerView taskListView = findViewById(R.id.taskListView);
        FloatingActionButton addTaskBtn = findViewById(R.id.btnAddTask);

        taskListView.setLayoutManager(new LinearLayoutManager(this));
        taskAdapter = new TaskRecyclerAdapter(this);
        taskListView.setAdapter(taskAdapter);

        fetchTasksFromDB();

        addTaskBtn.setOnClickListener(v ->
                startActivity(new Intent(this, TaskEditorActivity.class))
        );
    }

    @Override
    protected void onResume() {
        super.onResume();
        fetchTasksFromDB();
    }

    private void fetchTasksFromDB() {
        TaskDatabase database = TaskDatabase.getDbInstance(this);
        List<Task> taskList = database.taskAccessObject().fetchAll();
        taskAdapter.updateTaskList(taskList);
    }
}