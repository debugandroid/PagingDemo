package com.nplix.pagingdemo;

import android.arch.paging.DataSource;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;


/**
 * Created by PK on 1/31/2018.
 */
@Dao
public interface TodoDao {
    @Query("SELECT * FROM Todo ORDER BY todo COLLATE NOCASE ASC")
    public abstract DataSource.Factory<Integer, Todo> allTodoByName();

    @Insert
    public void insert(List<Todo> todos);

    @Insert
    public void insert(Todo todo);

    @Delete
    public void delete(Todo todo);
}
