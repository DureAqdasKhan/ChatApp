package com.example.a3;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import org.w3c.dom.Text;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeoutException;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ProgrammingViewHolder> {
    List<User> userList;
    Context context;
    public UserAdapter(List<User> messageList,Context context)
    {
        this.userList=messageList;
        this.context=context;
    }

    @Override
    public int getItemViewType(int position) {
        return R.layout.user_item;
    }

    @Override
    public ProgrammingViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater= LayoutInflater.from(parent.getContext());
        View view=inflater.inflate(viewType,parent,false);
        return new ProgrammingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ProgrammingViewHolder holder, int position) {
        final User user=userList.get(position);
        holder.textView.setText(userList.get(position).getName());
        holder.textView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context,MessageActivity.class);
                intent.putExtra("userId",user.getId());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    class ProgrammingViewHolder extends RecyclerView.ViewHolder
    {
        TextView textView;
        public ProgrammingViewHolder(View itemView) {
            super(itemView);
            textView=(TextView)itemView.findViewById(R.id.user_holder);
        }
    }
}