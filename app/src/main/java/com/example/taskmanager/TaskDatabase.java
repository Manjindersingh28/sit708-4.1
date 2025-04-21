package com.example.taskmanager;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Task.class}, version = 1)
public abstract class TaskDatabase extends RoomDatabase {
    private static TaskDatabase instance;
    public abstract TaskDao taskAccessObject();

    public static synchronized TaskDatabase getDbInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                            TaskDatabase.class, "tasks_storage")
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build();
        }
        return instance;
    }
}