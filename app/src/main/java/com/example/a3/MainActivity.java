package com.example.a3;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Toolbar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    Toolbar mToolbar;
    FirebaseUser firebaseUser;
    protected void onStart()
    {
        super.onStart();
        firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
        if(firebaseUser!=null)
        {
            Intent intent=new Intent(this,TempActivity.class);
            startActivity(intent);
            finish();
        }
    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle("Message App");

        //Intent intent=new Intent(this,TempActivity.class);
        //startActivity(intent);
    }
    public void Sign_In(View v)
    {
        Intent intent=new Intent(this,SignInActivity.class);
        startActivity(intent);
    }
    public void Sign_Up(View v)
    {
        Intent intent=new Intent(this,SignUpActivity.class);
        startActivity(intent);
    }
}