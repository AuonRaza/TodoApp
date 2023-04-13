package com.example.todo;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {
    LayoutInflater inflater;
    List<Todo> todos;

    Adapter(Context context, List<Todo> todos){
        this.inflater = LayoutInflater.from(context);
        this.todos = todos;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.custom_list_view,parent,false );
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter.ViewHolder holder, int position) {
        String title = todos.get(position).getTitle();
        String date = todos.get(position).getDate();
        String time = todos.get(position).getTime();
        long id = todos.get(position).getId();


        holder.nTitle.setText(title);
        holder.nDate.setText(date);
        holder.nTime.setText(time);
        holder.nID.setText(String.valueOf(id));
    }

    @Override
    public int getItemCount() {
        if (todos!=null)
            return todos.size();
        else
            return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView nTitle, nDate, nTime,nID;
        public ViewHolder(@NonNull final View itemView) {
            super(itemView);
            nTitle = itemView.findViewById(R.id.dpTodoTitle);
            nDate = itemView.findViewById(R.id.dpTodoDate);
            nTime = itemView.findViewById(R.id.dpTodoTime);
            nID = itemView.findViewById(R.id.dpTodoID);
            
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    Toast.makeText(view.getContext(), "item clicked", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(view.getContext(),EditTodo.class);
                    i.putExtra("ID",todos.get(getAdapterPosition()).getId());
                    view.getContext().startActivity(i);
                }
            });
        }
    }
}
