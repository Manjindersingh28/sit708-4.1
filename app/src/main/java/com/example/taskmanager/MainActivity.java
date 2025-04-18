
package com.example.taskmanager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private TaskAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        FloatingActionButton fab = findViewById(R.id.fab);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new TaskAdapter(this);
        recyclerView.setAdapter(adapter);

        loadTasks();

        fab.setOnClickListener(view -> {
            startActivity(new Intent(MainActivity.this, AddEditTaskActivity.class));
        });
    }

    private void loadTasks() {
        TaskDatabase db = TaskDatabase.getInstance(this);
        List<Task> tasks = db.taskDao().getAllTasks();
        adapter.setTasks(tasks);
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadTasks();
    }
}
