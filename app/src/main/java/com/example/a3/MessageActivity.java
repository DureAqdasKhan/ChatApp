package com.example.a3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.renderscript.Sampler;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class MessageActivity extends AppCompatActivity {
TextView name;
FirebaseUser firebaseUser;
DatabaseReference reference;
Intent intent;
ImageButton send;
EditText box;
MessageAdapter messageAdapter;
RecyclerView recyclerView;
List<Chat> chatList;
String temp_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        Toolbar toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView=findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        name=findViewById(R.id.name);
        send=findViewById(R.id.send_button);
        box=findViewById(R.id.chatbox);

        intent=getIntent();
        final String id=intent.getStringExtra("userId");
        temp_id=id;
        firebaseUser= FirebaseAuth.getInstance().getCurrentUser();

        send.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                String msg = box.getText().toString();
                if (!msg.equals("")){
                    DateFormat dateFormat = new SimpleDateFormat("hh.mm aa");
                    String dateString = ((SimpleDateFormat) dateFormat).format(new Date()).toString();
                    sendMessage(firebaseUser.getUid(), id, msg,dateString);
                } else {
                    Toast.makeText(MessageActivity.this, "You can't send empty message", Toast.LENGTH_SHORT).show();
                }
                box.setText("");
            }
        });

        reference= FirebaseDatabase.getInstance().getReference("Users").child(id);
        reference.keepSynced(true);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user=snapshot.getValue(User.class);
                name.setText(user.getName());
                readMesagges(firebaseUser.getUid(),id);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });
    }
    private void sendMessage(String sender,String receiver,String message,String date)
    {
        DatabaseReference reference=FirebaseDatabase.getInstance().getReference();
        HashMap<String,String> hashMap=new HashMap<>();
        hashMap.put("sender",sender);
        hashMap.put("receiver",receiver);
        hashMap.put("message",message);
        hashMap.put("date",date);
        reference.child("Chats").push().setValue(hashMap);
    }
    private void readMesagges(final String myid, final String userid){
        chatList = new ArrayList<>();

        reference = FirebaseDatabase.getInstance().getReference("Chats");
        reference.addValueEventListener(new ValueEventListener() {
            boolean flag=true;
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                chatList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Chat chat = snapshot.getValue(Chat.class);
                    if (chat.getReceiver().equals(myid) && chat.getSender().equals(userid) ||
                            chat.getReceiver().equals(userid) && chat.getSender().equals(myid))
                    {
                        if(chat.draft_content!=null)
                        {
                            flag = false;
                            System.out.println(chat.draft_content);
                            box.setText(chat.draft_content);
                            chat.draft_content=null;
                        }
                        else {
                            chatList.add(chat);
                        }
                    }

                    messageAdapter = new MessageAdapter(chatList,MessageActivity.this);
                    recyclerView.setAdapter(messageAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    public void onBackPressed()
    {
        super.onBackPressed();
        if(box.getText().toString().length()>0) {
            DatabaseReference reference=FirebaseDatabase.getInstance().getReference();
            HashMap<String,String> hashMap=new HashMap<>();
            hashMap.put("draft_content",box.getText().toString());
            hashMap.put("sender",firebaseUser.getUid());
            hashMap.put("receiver",temp_id);
            reference.child("Chats").push().setValue(hashMap);
            Toast toast = Toast.makeText(getApplicationContext(), "Message Drafted", Toast.LENGTH_SHORT);
            toast.show();
        }
    }
}