package com.example.todo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Calendar;
import java.util.List;

public class AddTodo extends AppCompatActivity {

    Toolbar toolbar;
    EditText todoTitle,todoDetails;
    Calendar c;
    String currentDate;
    String currentTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_todo);

        Intent i = getIntent();
        long id = i.getLongExtra("ID",0);
        boolean isEdit = i.getBooleanExtra("EditFlag",false);

        if(id == 0 || !isEdit){

            toolbar = findViewById(R.id.toolbar);
            toolbar.setTitleTextColor(getResources().getColor(R.color.colorYellow));
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("New Note");

            todoTitle = findViewById(R.id.todoTitle);
            todoDetails = findViewById(R.id.todoDetails);

            todoTitle.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    if(charSequence.length() !=0){
                        getSupportActionBar().setTitle(charSequence);
                    }
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });

            //set current date and time
            c = Calendar.getInstance();
            currentDate = c.get(Calendar.YEAR)+"/"+pad(c.get(Calendar.MONTH)+1)+"/"+pad(c.get(Calendar.DAY_OF_MONTH));
            Log.d("DATE","Date: "+currentDate);
            currentTime = pad(c.get(Calendar.HOUR))+":"+pad(c.get(Calendar.MINUTE));
            Log.d("TIME","Time: "+currentTime);
        }else{

        }
    }

    private String pad(int time) {
        if (time < 10)
            return "0"+time;
        return String.valueOf(time);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.save_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId()==R.id.delete){
            Toast.makeText(this, "Note Not Saved", Toast.LENGTH_SHORT).show();
        }else if (item.getItemId()==R.id.save){
            Todo todo = new Todo(todoTitle.getText().toString(), //title
                    todoDetails.getText().toString(), //details
                    currentDate, //date
                    currentTime); //time
            TodoDatabase db = new TodoDatabase(this);
            db.addTodo(todo);
            db.close();
            Toast.makeText(this, "Save btn clicked", Toast.LENGTH_SHORT).show();
            goToMain();
        }
        return super.onOptionsItemSelected(item);
    }

    private void goToMain() {
        Intent i = new Intent(this,MainActivity.class);
        startActivity(i);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}