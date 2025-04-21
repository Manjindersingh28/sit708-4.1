package com.example.taskmanager;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class TaskRecyclerAdapter extends RecyclerView.Adapter<TaskRecyclerAdapter.TaskHolder> {
    private List<Task> taskItems = new ArrayList<>();
    private Context context;

    public TaskRecyclerAdapter(Context context) {
        this.context = context;
    }

    @Override
    public TaskHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_task, parent, false);
        return new TaskHolder(view);
    }

    @Override
    public void onBindViewHolder(TaskHolder holder, int position) {
        Task current = taskItems.get(position);
        holder.titleText.setText(current.title);
        holder.dueDateText.setText(current.dueDate);
    }

    @Override
    public int getItemCount() {
        return taskItems.size();
    }

    public void updateTaskList(List<Task> tasks) {
        this.taskItems = tasks;
        notifyDataSetChanged();
    }

    class TaskHolder extends RecyclerView.ViewHolder {
        TextView titleText, dueDateText;

        public TaskHolder(View itemView) {
            super(itemView);
            titleText = itemView.findViewById(R.id.textViewTitle);
            dueDateText = itemView.findViewById(R.id.textViewDueDate);

            itemView.setOnClickListener(v -> {
                int pos = getAdapterPosition();
                if (pos != RecyclerView.NO_POSITION) {
                    Task selectedTask = taskItems.get(pos);
                    Intent intent = new Intent(context, TaskEditorActivity.class);
                    intent.putExtra("task_id", selectedTask.id);
                    context.startActivity(intent);
                }
            });
        }
    }
}
