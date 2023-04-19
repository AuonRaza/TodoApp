package com.example.todo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class TodoDatabase extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 2;
    private static final String DATABASE_NAME = "todoDB";
    private  static final String DATABASE_TABLE = "notestable";

    //column names for database table
    private static final String KEY_ID = "id";
    private static final String KEY_TITLE = "todo_title";
    private static final String KEY_TODO = "todo";
    private static final String KEY_DATE = "date";
    private static final String KEY_TIME = "time";

    TodoDatabase (Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // CREATE TABLE todo(id INT PRIMARY KEY, title TEXT, todo TEXT, date TEXT, time TEXT);
        String query = "CREATE TABLE " + DATABASE_TABLE + " ("
                + KEY_ID + " INTEGER PRIMARY KEY, "
                +KEY_TITLE+" TEXT, "
                +KEY_TODO+" TEXT, "
                +KEY_DATE+" TEXT, "
                +KEY_TIME+" TEXT);" ;
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion>=newVersion)
            return;
        db.execSQL("DROP TABLE IF EXISTS "+ DATABASE_TABLE);
        onCreate(db);
    }

    public int getLastID(){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT MAX("+KEY_ID+") "+"FROM "+DATABASE_TABLE;
        Cursor max = db.rawQuery(query,null);
        return Integer.valueOf(max.getString(0));
    }

    public boolean ifExist(long id){
        //SELECT * FROM TABLE WHERE ID = id
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM "+DATABASE_TABLE+" WHERE ID="+id;
        Cursor isEmpty = db.rawQuery(query,null);
        if (isEmpty == null)
            return false;
        else
            return true;

    }



    public long addTodo(Todo todo){
        long ifID = todo.getId();
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues c = new ContentValues();
        c.put(KEY_TITLE, todo.getTitle());
        c.put(KEY_TODO, todo.getContent());
        c.put(KEY_DATE, todo.getDate());
        c.put(KEY_TIME, todo.getTime());
        if (ifID==0L) {
            long ID = db.insert(DATABASE_TABLE, null, c);
            Log.d("Inserted", "from user; ID --> " + ID);
            return ID;
        }else{
            if (ifExist(ifID)){
                deleteTodo(ifID);
            }
                c.put(KEY_ID,ifID);
                db.insert(DATABASE_TABLE,null,c);
                Log.d("Inserted", "from PubApi; ID --> " + ifID);
                return ifID;
        }
    }

    public Todo getTodo(long id){
        // Select * from DATABASE_TABLE where id=1
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(DATABASE_TABLE,
                new String[]{KEY_ID,KEY_TITLE,KEY_TODO,KEY_DATE,KEY_TIME},KEY_ID+"=?",
                new String[]{String.valueOf(id)},null,null,null);
        if (cursor !=null)
            cursor.moveToFirst();

        return new Todo(cursor.getLong(0),
                cursor.getString(1),
                cursor.getString(2),
                cursor.getString(3),
                cursor.getString(4));
    };

    public List<Todo> getTodos(){
        SQLiteDatabase db = this.getReadableDatabase();
        List<Todo> allTodos = new ArrayList<>();

        String query = "SELECT * FROM "+DATABASE_TABLE;
        Cursor cursor = db.rawQuery(query,null);
        if(cursor.moveToFirst()){
            do{
                Todo todo = new Todo();
                todo.setId(cursor.getLong(0));
                todo.setTitle(cursor.getString(1));
                todo.setContent(cursor.getString(2));
                todo.setDate(cursor.getString(3));
                todo.setTime(cursor.getString(4));

                allTodos.add(todo);

            }while(cursor.moveToNext());
        }
        return allTodos;
    }

    public int editTodo(Todo todo){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues c = new ContentValues();
        Log.d("Edited", "Edited Title: -> "+ todo.getTitle() + "\n ID -> "+todo.getId());
        c.put(KEY_TITLE,todo.getTitle());
        c.put(KEY_TODO,todo.getContent());
        c.put(KEY_DATE,todo.getDate());
        c.put(KEY_TIME,todo.getTime());
        int x = db.update(DATABASE_TABLE,c,KEY_ID+"=?",new String[]{String.valueOf(todo.getId())});
        return x;
    }

    void deleteTodo(long id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(DATABASE_TABLE,KEY_ID+"=?",new String[]{String.valueOf(id)});
//        db.close();
    }
}
