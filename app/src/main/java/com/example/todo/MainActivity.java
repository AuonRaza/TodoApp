 package com.example.todo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

 public class MainActivity extends AppCompatActivity {
    Toolbar toolbar;
    RecyclerView rvTodoList;
    Adapter adapter;
    List<Todo> todos;
    Button getJson;

    //API endpoint
    String myUrl = "https://www.jsonkeeper.com/b/UOYR";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // get data from API and store in database before showing data on screen
        getJsonData(myUrl);

        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(getResources().getColor(R.color.colorYellow));
        setSupportActionBar(toolbar);

//        getJson = (Button) findViewById(R.id.xbutton);

        TodoDatabase db = new TodoDatabase(this);
        todos = db.getTodos();
        rvTodoList = findViewById(R.id.todosList);
        rvTodoList.setLayoutManager(new LinearLayoutManager(this));
        adapter = new Adapter(this,todos);
        rvTodoList.setAdapter(adapter);


    }

    public void getJsonData(String url){
        HttpsTrustManager.allowAllSSL();
        RequestQueue requestQueue;
        requestQueue = Volley.newRequestQueue(MainActivity.this);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("Todo Data", response.toString());
                Todo todo = null;
                try {
                    todo = new Todo(Long.parseLong(response.getString("id")), //id
                            response.getString("todo_title"), //title
                            response.getString("todo"), //details
                            response.getString("date"), //date
                            response.getString("time"));
                    Log.d("VolleyOnResp", "onResponse: "+response);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                TodoDatabase db = new TodoDatabase(MainActivity.this);
                db.addTodo(todo);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Todo Data", "Something went wrong");
            }
        });

        requestQueue.add(jsonObjectRequest);
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

//     public class MyAsyncTasks extends AsyncTask<String,String,String> {
//         @Override
//         protected void onPreExecute() {
//             super.onPreExecute();
//             progressDialog = new ProgressDialog(MainActivity.this);
//             progressDialog.setMessage("processing results..");
//             progressDialog.setCancelable(false);
//             progressDialog.show();
//         }
//
//         @Override
//         protected String doInBackground(String... strings) {
//             String result = "";
//             try{
//                 URL url;
//                 HttpsURLConnection urlConnection=null;
//                 try{
//                     url = new URL(myUrl);
//                     //open a url connection
//                     urlConnection = (HttpsURLConnection) url.openConnection();
//
//                     InputStream isw = urlConnection.getInputStream();
//
//                     int data = isw.read();
//
//                     while (data != -1){
//                         result += (char) data;
//                         data = isw.read();
//                     }
//                     //return data to onPostExecute method
//                     return result;
//                 } catch (Exception e ){
//                    e.printStackTrace();
//                 }finally {
//                    if (urlConnection != null){
//                        urlConnection.disconnect();
//                    }
//                 }
//             } catch (Exception e){
//                 e.printStackTrace();
//                 return "Exception: "+e.getMessage();
//             }
//             return result;
//         }
//
//         @Override
//         protected void onPostExecute(String s) {
//             progressDialog.dismiss();
//             try{
//                 JSONObject jsonObject = new JSONObject(s);
//
//                 JSONArray jsonArray1 = jsonObject.getJSONArray("todo");
//
//                 JSONObject jsonObject1 = jsonArray1.getJSONObject(1);
//                 int id = jsonObject1.getInt("id");
//                 String todo_title = jsonObject1.getString("todo_title");
//                 String todo = jsonObject1.getString("todo");
//                 String date = jsonObject1.getString("date");
//                 String time = jsonObject1.getString("time");
//
//                 Log.d("Todo Data",
//                         "ID: | "+id
//                             +" Title: | "+todo_title
//                             +" Todo: | "+todo
//                             +" Date: | "+date
//                             +"Time: | "+time);
//             }catch (JSONException e){
//                e.printStackTrace();
//             }
//             super.onPostExecute(s);
//         }
//     }

 }
