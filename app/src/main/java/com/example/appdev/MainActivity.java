package com.example.appdev;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    FirebaseAuth mAuth;

    EditText SignEmail, SignPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();


        Button goReg = findViewById(R.id.goRegister);//(Button)
        goReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signUpIntent = new Intent(getApplicationContext(),SignUpActivity.class);
                        startActivity(signUpIntent);
            }
        });

        Button signIn = findViewById(R.id.signIn);
        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signInUser();
            }

        });
    }

        private void signInUser() {
        String email = SignEmail.getText().toString().trim();
        String password = SignPassword.getText().toString().trim();


            if(email.isEmpty()){
                SignEmail.setError("Email is required");
                SignEmail.requestFocus();
                return;
            }

            if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                SignEmail.setError("Email a valid Email");
                SignEmail.requestFocus();
                return;
            }

            if(password.isEmpty()){
                SignPassword.setError("Password is required");
                SignPassword.requestFocus();
                return;
            }

            if(password.length()<6){
                SignPassword.setError("Password length should be greater than 6");
                SignPassword.requestFocus();
                return;
            }



            mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        Intent signIntent = new Intent(getApplicationContext(), ProfileActivity.class);
                        signIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(signIntent);
                    } else {
                        Toast.makeText(MainActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }