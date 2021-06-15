package com.example.a3;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;


import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class TempActivity extends AppCompatActivity {
    FirebaseUser firebaseUser;
    DatabaseReference reference;
    TextView username;
    Toolbar mToolbar;
    RecyclerView recyclerView;
    UserAdapter userAdapter;
    private List<User> userList;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temp);
        mToolbar = (androidx.appcompat.widget.Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle("");
       // mToolbar.setTitle("Message App");

        setSupportActionBar(mToolbar);
        mToolbar.showOverflowMenu();
        username = findViewById(R.id.name);
        firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
        reference= FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid());
        reference.keepSynced(true);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user=snapshot.getValue(User.class);
                assert user != null;
                username.setText(user.getName());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        recyclerView = findViewById(R.id.recycler_user);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        userList=new ArrayList<>();

        //Load_From_Firebase();

        readUsers();


        /*TabLayout tabLayout=findViewById(R.id.tabs);
        ViewPager viewPager=findViewById(R.id.viewpager);
        ViewPagerAdapter viewPagerAdapter=new ViewPagerAdapter(getSupportFragmentManager());
        //viewPagerAdapter.addFragment(new ChatFragment(),"Messages");
        viewPagerAdapter.addFragment(new UsersFragment(),"Users");

        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);*/
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
                userAdapter = new UserAdapter(userList,getApplicationContext());
                recyclerView.setAdapter(userAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){

            case  R.id.logout:
                FirebaseAuth.getInstance().signOut();
                Intent intent=new Intent(this,MainActivity.class);
                startActivity(intent);
                finish();
                return true;
        }

        return false;
    }

    /*class ViewPagerAdapter extends FragmentPagerAdapter
    {
        private ArrayList<Fragment> fragments;
        private ArrayList<String>titles;
        ViewPagerAdapter(FragmentManager fm)
        {
            super(fm);
            this.fragments=new ArrayList<>();
            this.titles=new ArrayList<>();

        }
        @NonNull
        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }
        public void addFragment(Fragment fragment,String title)
        {
            this.fragments.add(fragment);
            this.titles.add(title);
        }
        public CharSequence getPageTitle(int position)
        {
            return titles.get(position);
        }
    }*/

}
