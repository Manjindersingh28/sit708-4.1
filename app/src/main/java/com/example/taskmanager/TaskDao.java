package com.example.taskmanager;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface TaskDao {
    @Insert
    void add(Task task);

    @Update
    void modify(Task task);

    @Delete
    void remove(Task task);

    @Query("SELECT * FROM task_entries ORDER BY dueDate ASC")
    List<Task> fetchAll();

    @Query("SELECT * FROM task_entries WHERE id = :id LIMIT 1")
    Task fetchById(int id);
}