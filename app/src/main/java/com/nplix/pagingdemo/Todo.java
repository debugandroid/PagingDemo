package com.nplix.pagingdemo;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by PK on 1/31/2018.
 */
@Entity
public class Todo {
    @PrimaryKey(autoGenerate = true)
    int id;

    public String getTodo() {
        return todo;
    }

    public void setTodo(String todo) {
        this.todo = todo;
    }

    private String todo;
}
