package com.nplix.pagingdemo;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by PK on 1/31/2018.
 */

class TodoViewHolder extends RecyclerView.ViewHolder{

    private TextView todoName;
    public  Todo todo;

    public TodoViewHolder(View itemView) {
        super(itemView);
        todoName= itemView.findViewById(R.id.name);
    }

    void bindTo(Todo todo){
        this.todo=todo;
        todoName.setText(todo.getTodo());
    }
}
