
package com.example.taskmanager;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class AddEditTaskActivity extends AppCompatActivity {
    private EditText titleInput, descriptionInput, dueDateInput;
    private Task existingTask;
    private int taskId = -1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_task);

        titleInput = findViewById(R.id.editTextTitle);
        descriptionInput = findViewById(R.id.editTextDescription);
        dueDateInput = findViewById(R.id.editTextDueDate);

        findViewById(R.id.buttonSave).setOnClickListener(view -> saveTask());
        taskId = getIntent().getIntExtra("task_id", -1);
        if (taskId != -1) {
            // Editing
            existingTask = TaskDatabase.getInstance(this).taskDao().getTaskById(taskId);
            if (existingTask != null) {
                titleInput.setText(existingTask.title);
                descriptionInput.setText(existingTask.description);
                dueDateInput.setText(existingTask.dueDate);
            }
        }
        // Check if editing existing task
        if (getIntent().hasExtra("task_id")) {
            taskId = getIntent().getIntExtra("task_id", -1);
            Task task = TaskDatabase.getInstance(this).taskDao().getTaskById(taskId);
            if (task != null) {
                titleInput.setText(task.title);
                descriptionInput.setText(task.description);
                dueDateInput.setText(task.dueDate);
            }
        }


        Button deleteButton = findViewById(R.id.buttonDelete);
        if (taskId != -1) {
            deleteButton.setVisibility(View.VISIBLE);
            deleteButton.setOnClickListener(v -> {
                if (existingTask != null) {
                    TaskDatabase.getInstance(this).taskDao().delete(existingTask);
                    Toast.makeText(this, "Task Deleted", Toast.LENGTH_SHORT).show();
                    finish();
                }
            });
        }


    }

    private void saveTask() {
        String title = titleInput.getText().toString().trim();
        String desc = descriptionInput.getText().toString().trim();
        String dueDate = dueDateInput.getText().toString().trim();

        if (title.isEmpty()) {
            titleInput.setError("Title required");
            return;
        }

        TaskDatabase db = TaskDatabase.getInstance(this);

        if (taskId == -1) {
            // New task
            Task task = new Task(title, desc, dueDate);
            db.taskDao().insert(task);
            Toast.makeText(this, "Task Saved", Toast.LENGTH_SHORT).show();
        } else {
            // Existing task - update
            Task task = new Task(title, desc, dueDate);
            task.id = taskId;
            db.taskDao().update(task);
            Toast.makeText(this, "Task Updated", Toast.LENGTH_SHORT).show();
        }

        finish();
    }
}
