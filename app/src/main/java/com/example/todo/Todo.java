package com.example.todo;

public class Todo {
    long id;
    String title;
    String content;
    String date;
    String time;

    Todo(String title,String content,String date, String time){
        this.title = title;
        this.content = content;
        this.date = date;
        this.time = time;
    }

    Todo(long id, String title, String content, String date, String time){
        this.id = id;
        this.title = title;
        this.content = content;
        this.date = date;
        this.time = time;
    }

    Todo(){
        //Empty constructor -- used in retrieving all data getTodos()
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
