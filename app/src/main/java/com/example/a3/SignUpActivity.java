package com.example.a3;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.HashMap;

public class SignUpActivity extends AppCompatActivity {

    Toolbar mToolbar;
    MaterialEditText name,email,password;
    FirebaseAuth auth;
    DatabaseReference reference;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle("Sign Up");
        name=(MaterialEditText)findViewById(R.id.username);
        email=(MaterialEditText)findViewById((R.id.email));
        password=(MaterialEditText)findViewById(R.id.password);
        auth=FirebaseAuth.getInstance();
    }
    public void Register_App(View v)
    {
        String e=email.getText().toString();
        String n=name.getText().toString();
        String p=password.getText().toString();
        if (TextUtils.isEmpty(n) || TextUtils.isEmpty(e) || TextUtils.isEmpty(p)){
            Toast.makeText(SignUpActivity.this, "All fileds are required", Toast.LENGTH_SHORT).show();
        } else if (p.length() < 6 ){
            Toast.makeText(SignUpActivity.this, "password must be at least 6 characters", Toast.LENGTH_SHORT).show();
        } else {
            register(n,e,p);
        }

    }
    private void register(final String name, String email, String password)
    {
        auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful())
                {
                    FirebaseUser firebaseUser=auth.getCurrentUser();
                    assert firebaseUser != null;
                    String userid=firebaseUser.getUid();
                    reference= FirebaseDatabase.getInstance().getReference("Users").child(userid);
                    HashMap<String,String> hashMap=new HashMap<>();
                    hashMap.put("id",userid);
                    hashMap.put("username",name);

                    reference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful())
                            {
                                Intent intent=new Intent(SignUpActivity.this,TempActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        }
                    });
                }
                else
                {
                    Toast.makeText(SignUpActivity.this,"You cant register this email or password",Toast.LENGTH_LONG).show();
                }
            }
        });
    }

}