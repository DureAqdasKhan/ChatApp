package com.example.a3;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.rengwuxian.materialedittext.MaterialEditText;

public class SignInActivity extends AppCompatActivity {
Toolbar mToolbar;
MaterialEditText email,password;
    FirebaseAuth auth;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle("Sign In");
        email=findViewById(R.id.email);
        password=findViewById(R.id.password);
        auth=FirebaseAuth.getInstance();
    }
    public void SignIn(View v)
    {
        String e=email.getText().toString();
        String p=password.getText().toString();
        auth.signInWithEmailAndPassword(e,p).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful())
                {
                    Intent intent=new Intent(SignInActivity.this,TempActivity.class);
                    startActivity(intent);
                    finish();
                }
                else
                {
                    Toast.makeText(SignInActivity.this,"You cant login with this email or password",Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}