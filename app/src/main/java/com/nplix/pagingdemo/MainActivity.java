package com.nplix.pagingdemo;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProviders;
import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.db.SupportSQLiteOpenHelper;
import android.arch.persistence.room.DatabaseConfiguration;
import android.arch.persistence.room.InvalidationTracker;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.recyclerview.extensions.DiffCallback;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {
    private DiffCallback diffCallback = new  DiffCallback<Todo>() {

        @Override
        public boolean areItemsTheSame(@NonNull Todo oldItem, @NonNull Todo newItem) {

            return  oldItem.id == newItem.id;
        }

        @Override
        public boolean areContentsTheSame(@NonNull Todo oldItem, @NonNull Todo newItem) {
            return  oldItem == newItem;
        }
    };

    TodoViewModel viewModel;
    RecyclerView todoList;
    EditText inputText;
    Button addBtn;
    TodoDB todoDB;
    TodoDao todoDao;
    private ExecutorService IO_EXECUTOR = Executors.newSingleThreadExecutor();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        todoDB= Room.databaseBuilder(getApplicationContext(),TodoDB.class,"MyTodoDB")
                .addCallback(new RoomDatabase.Callback() {
                    @Override
                    public void onCreate(@NonNull SupportSQLiteDatabase db) {
                        super.onCreate(db);
                    }
                }).build();
        todoDao=todoDB.TodoDao();
        viewModel = ViewModelProviders.of(this).get(TodoViewModel.class);
        viewModel.init(todoDao);

        inputText=findViewById(R.id.inputText);
        addBtn=findViewById(R.id.addButton);
        todoList=findViewById(R.id.todoList);
      //  todoDB=TodoDB.get(this);
        TodoAdapter todoAdapter=new TodoAdapter(diffCallback);
        viewModel.todoList.observe(this, todoAdapter::setList);

        todoList.setAdapter(todoAdapter);

        initAddButtonListener();
        initSwipeToDelete();

    }
    public void initSwipeToDelete(){
        new ItemTouchHelper(new ItemTouchHelper.Callback() {

            @Override
            public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
                return makeMovementFlags(0,ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT);
            }

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                remove(((TodoViewHolder) viewHolder).todo);
            }
        }).attachToRecyclerView(todoList    );
    }

    private void addTodo() {
        String newTodo = inputText.getText().toString().trim();
        if (newTodo!=null) {
            insert(newTodo);
            inputText.setText("");
        }
    }

    private void initAddButtonListener(){
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addTodo();
            }
        });

        inputText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId== EditorInfo.IME_ACTION_DONE){
                    addTodo();
                }
                return true;
            }
        });

        inputText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if(event.getAction()== KeyEvent.ACTION_DOWN && keyCode== KeyEvent.KEYCODE_ENTER){
                    addTodo();
                }
                return true;
            }
        });


    }


    public void  insert(CharSequence text){
        final Todo todo=new Todo();
        todo.setTodo(text.toString());
        IO_EXECUTOR.execute(()->{
            todoDB.TodoDao().insert(todo);
        });
    }

    public void remove(Todo todo){
        IO_EXECUTOR.execute(()->{
            todoDB.TodoDao().delete(todo);
        });

    }
}
