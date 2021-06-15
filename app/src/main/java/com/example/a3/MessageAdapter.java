package com.example.a3;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ProgrammingViewHolder> {
    private Context context;
    private List<Chat> chatList;
    FirebaseUser firebaseUser;
    public MessageAdapter(List<Chat> chatList,Context context)
    {
        this.chatList=chatList;
        this.context=context;
    }

    @Override
    public int getItemViewType(int position)
    {
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if (chatList.get(position).getSender().equals(firebaseUser.getUid()))
        {
            return R.layout.my_side;
        }
        else {
            return R.layout.opposite;
        }
    }

    @Override
    public MessageAdapter.ProgrammingViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(viewType, parent, false);
            return new MessageAdapter.ProgrammingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MessageAdapter.ProgrammingViewHolder holder, int position) {
        if(chatList.get(position).message!=null&&chatList.get(position).getMessage().length()>0) {
            holder.textView.setText(chatList.get(position).getMessage());
            holder.textView1.setText(chatList.get(position).getDate());
        }
    }

    @Override
    public int getItemCount() {
        return chatList.size();
    }

    class ProgrammingViewHolder extends RecyclerView.ViewHolder
    {
        TextView textView;
        TextView textView1;
        public ProgrammingViewHolder(View itemView) {
            super(itemView);
            textView=(TextView)itemView.findViewById(R.id.message);
            textView1=(TextView)itemView.findViewById(R.id.Opposite_Timing);
        }
    }

}
