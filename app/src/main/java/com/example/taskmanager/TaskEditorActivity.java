package com.example.taskmanager;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;


public class TaskEditorActivity extends AppCompatActivity {
    private EditText titleBox, descBox, dateBox;
    private int currentTaskId = -1;

    @Override
    protected void onCreate(Bundle savedState) {
        super.onCreate(savedState);
        setContentView(R.layout.screen_task_editor);

        titleBox = findViewById(R.id.inputTitle);
        descBox = findViewById(R.id.inputDescription);
        dateBox = findViewById(R.id.inputDate);

        if (getIntent().hasExtra("task_id")) {
            currentTaskId = getIntent().getIntExtra("task_id", -1);
            populateFields(currentTaskId);
        }

        findViewById(R.id.saveBtn).setOnClickListener(view -> handleSave());
        findViewById(R.id.deleteBtn).setOnClickListener(view -> handleDelete());
    }

    private void populateFields(int taskId) {
        Task task = TaskDatabase.getDbInstance(this).taskAccessObject().fetchById(taskId);
        if (task != null) {
            titleBox.setText(task.title);
            descBox.setText(task.description);
            dateBox.setText(task.dueDate);

            // ✅ Show delete button if editing
            View deleteBtn = findViewById(R.id.deleteBtn);
            deleteBtn.setVisibility(View.VISIBLE);
        }
    }

    private void handleSave() {
        String title = titleBox.getText().toString().trim();
        String description = descBox.getText().toString().trim();
        String dueDate = dateBox.getText().toString().trim();

        if (title.isEmpty()) {
            titleBox.setError("Title can't be empty");
            return;
        }

        TaskDatabase db = TaskDatabase.getDbInstance(this);
        Task task = new Task(title, description, dueDate);

        if (currentTaskId == -1) {
            db.taskAccessObject().add(task);
            Toast.makeText(this, "Task Created", Toast.LENGTH_SHORT).show();
        } else {
            task.id = currentTaskId;
            db.taskAccessObject().modify(task);
            Toast.makeText(this, "Task Updated", Toast.LENGTH_SHORT).show();
        }

        finish();
    }

    private void handleDelete() {
        if (currentTaskId != -1) {
            TaskDatabase db = TaskDatabase.getDbInstance(this);
            Task task = db.taskAccessObject().fetchById(currentTaskId);
            db.taskAccessObject().remove(task);
            Toast.makeText(this, "Task Deleted", Toast.LENGTH_SHORT).show();
        }
        finish();
    }
}