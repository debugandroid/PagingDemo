package com.nplix.pagingdemo;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.support.annotation.NonNull;

/**
 * Created by PK on 1/31/2018.
 */
@Database(entities = Todo.class,version = 1)
public abstract class TodoDB extends RoomDatabase {
    public abstract TodoDao TodoDao();
 /*   private static TodoDB instance;

    static  synchronized TodoDB get(@NonNull Context context){
        if(instance!=null) {
            instance= Room.databaseBuilder(context.getApplicationContext(),TodoDB.class,"MyTodoDB")
                   .addCallback(new RoomDatabase.Callback() {
                       @Override
                       public void onCreate(@NonNull SupportSQLiteDatabase db) {
                           super.onCreate(db);
                       }
                   }).build();
        }
        return instance;
    }*/
}
