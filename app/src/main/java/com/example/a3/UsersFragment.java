package com.example.a3;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UsersFragment#} factory method to
 * create an instance of this fragment.
 */
public class UsersFragment extends Fragment {
    RecyclerView recyclerView;
    UserAdapter userAdapter;
    private List<User> userList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_users, container, false);
        recyclerView = view.findViewById(R.id.recycler_user);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        userList=new ArrayList<>();

        //Load_From_Firebase();

        readUsers();
        /*User user=new User("dure,","1");
        userList.add(user);
        User user1=new User("ali","2");
        userList.add(user1);
        userAdapter=new UserAdapter(userList,getContext());
        recyclerView.setAdapter(userAdapter);*/
        return view;
    }

    private void readUsers() {
        final FirebaseUser firebaseUser=FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference reference=FirebaseDatabase.getInstance().getReference("Users");
        reference.keepSynced(true);
        reference.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                userList.clear();
                for(DataSnapshot dataSnapshot:snapshot.getChildren())
                {
                    User user=dataSnapshot.getValue(User.class);
                    System.out.println("NAMEEE"+user.getName());
                    //assert user!=null;
                    //assert firebaseUser!=null;
                    if(user!=null&&firebaseUser!=null) {
                        if (!user.getId().equals(firebaseUser.getUid())) {
                            System.out.println("INSIDE FIREBASE NAMEEE"+user.getName());
                            userList.add(user);
                        }
                    }
                }
                userAdapter = new UserAdapter(userList,getContext());
                recyclerView.setAdapter(userAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}