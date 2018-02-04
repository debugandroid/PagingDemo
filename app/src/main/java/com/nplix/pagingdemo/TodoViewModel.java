package com.nplix.pagingdemo;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.paging.LivePagedListBuilder;
import android.arch.paging.PagedList;
import android.support.annotation.NonNull;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by PK on 1/31/2018.
 */

public class TodoViewModel extends ViewModel {

     LiveData<PagedList<Todo>> todoList;
    public TodoViewModel() {
    }

    public void init(TodoDao todoDao){
        PagedList.Config pagedListConfig=(new PagedList.Config.Builder()).setEnablePlaceholders(true)
                .setPrefetchDistance(10)
                .setPageSize(20).build();

        todoList = new LivePagedListBuilder<>(
                todoDao.allTodoByName(), pagedListConfig).build();
    }
}
