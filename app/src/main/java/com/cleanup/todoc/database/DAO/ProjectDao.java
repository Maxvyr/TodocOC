package com.cleanup.todoc.database.DAO;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.cleanup.todoc.model.Project;

import java.util.List;

/*
 * Design Pattern: Data Access Object
 */
@Dao
public interface ProjectDao {

    // METHODS

    //CREATE
    @Insert
    long insertProject(final Project project);

    // READ
    @Query("SELECT * FROM project")
    LiveData<List<Project>> getProjects();

    @Query("SELECT * FROM project WHERE id = :projectId")
    LiveData<Project> getProjectById(final long projectId);

    // UPDATE
    @Update
    int updateProject(final Project project);

    //DELETE
    @Query("DELETE FROM project WHERE id = :projectId")
    int deleteProjectById(final long projectId);
}
