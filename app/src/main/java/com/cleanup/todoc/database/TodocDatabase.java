package com.cleanup.todoc.database;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.ContentValues;
import android.content.Context;
import android.support.annotation.NonNull;

import com.cleanup.todoc.database.DAO.ProjectDao;
import com.cleanup.todoc.database.DAO.TaskDao;
import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;

import java.util.Arrays;
import java.util.List;

/**
 * A abstract class that extends {@link RoomDatabase}.
 *
 * Design Pattern: Singleton
 */
@Database(entities = {Project.class,
                      Task.class},
          version = 1,
          exportSchema = false)
public abstract class TodocDatabase extends RoomDatabase {

    //variable
    private static volatile TodocDatabase INSTANCE;
    private static final String DATABASE_NAME = "TodocDatabase.db";
    public static final List<Project> PROJECTS = Arrays.asList(new Project("Projet Tartampion", 0xFFEADAD1),
                                                               new Project("Projet Lucidia", 0xFFB4CDBA),
                                                               new Project("Projet Circus",0xFFA3CED2));

    // DAO
    public abstract ProjectDao mProjectDao();
    public abstract TaskDao mTaskDao();

    // METHODS

    // -- INSTANCE
    /**
     * Returns the instance of TodocDatabase
     * @param context a {@link Context}
     * @return the instance of TodocDatabase
     */
    public static com.cleanup.todoc.database.TodocDatabase getInstance(final Context context) {
        if (INSTANCE == null) {
            synchronized (com.cleanup.todoc.database.TodocDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                                    com.cleanup.todoc.database.TodocDatabase.class,
                                                    DATABASE_NAME)
                                   .addCallback(prepopulateDatabase())
                                   .build();
                }
            }
        }

        return INSTANCE;
    }

    // -- PREPOPULATE THE DATABASE
    /**
     * Returns a {@link Callback} that
     * allows to prepopulate the database
     * @return a {@link Callback}
     */
    private static Callback prepopulateDatabase() {
        return new Callback() {
            @Override
            public void onCreate(@NonNull SupportSQLiteDatabase db) {
                super.onCreate(db);

                for (Project project : PROJECTS) {
                    ContentValues contentValues = new ContentValues();
                    contentValues.put("name", project.getName());
                    contentValues.put("color", project.getColor());
                    db.insert("project", OnConflictStrategy.IGNORE, contentValues);
                }
            }
        };
    }
}
