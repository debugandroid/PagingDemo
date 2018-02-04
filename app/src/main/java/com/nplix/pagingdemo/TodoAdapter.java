package com.nplix.pagingdemo;

import android.arch.paging.PagedListAdapter;
import android.support.annotation.NonNull;
import android.support.v7.recyclerview.extensions.DiffCallback;
import android.view.LayoutInflater;
import android.view.ViewGroup;

/**
 * Created by PK on 1/31/2018.
 */

public class TodoAdapter extends PagedListAdapter<Todo,TodoViewHolder> {


    protected TodoAdapter(@NonNull DiffCallback diffCallback) {
        super(diffCallback);
    }


    @Override
    public TodoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new TodoViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.todo_item, parent, false));
    }

    @Override
    public void onBindViewHolder(TodoViewHolder holder, int position) {
            holder.bindTo(getItem(position));
    }

}
