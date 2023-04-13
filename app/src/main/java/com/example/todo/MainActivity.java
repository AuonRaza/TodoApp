 package com.example.todo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.List;

 public class MainActivity extends AppCompatActivity {
    Toolbar toolbar;
    RecyclerView rvTodoList;
    Adapter adapter;
    List<Todo> todos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(getResources().getColor(R.color.colorYellow));
        setSupportActionBar(toolbar);
        TodoDatabase db = new TodoDatabase(this);
        todos = db.getTodos();
        rvTodoList = findViewById(R.id.todosList);
        rvTodoList.setLayoutManager(new LinearLayoutManager(this));
        adapter = new Adapter(this,todos);
        rvTodoList.setAdapter(adapter);
    }

     @Override
     public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.add_menu,menu);
        return true;
     }

     @Override
     public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId()==R.id.add){
            Toast.makeText(this, "Add Button is Clicked", Toast.LENGTH_SHORT).show();
            Intent i = new Intent(this,AddTodo.class);
            startActivity(i);
        }
         return super.onOptionsItemSelected(item);
     }
 }