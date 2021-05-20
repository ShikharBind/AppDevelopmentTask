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
import com.google.firebase.auth.FirebaseAuthUserCollisionException;

public class SignUpActivity extends AppCompatActivity {

    EditText RegisterEmail, RegisterPassword;

    private FirebaseAuth mAuth;



    @Override
    public <T extends View> T findViewById(int id) {
        return super.findViewById(id);
    }

    private void registerUser(){
        String email = RegisterEmail.getText().toString().trim();
        String password = RegisterPassword.getText().toString().trim();

        if(email.isEmpty()){
            RegisterEmail.setError("Email is required");
            RegisterEmail.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            RegisterEmail.setError("Email a valid Email");
            RegisterEmail.requestFocus();
            return;
        }

        if(password.isEmpty()){
            RegisterPassword.setError("Password is required");
            RegisterPassword.requestFocus();
            return;
        }

        if(password.length()<6){
            RegisterPassword.setError("Password length should be greater than 6");
            RegisterPassword.requestFocus();
            return;
        }



        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull  Task<AuthResult> task) {//@org.jetbrains.annotations.NotNull
                if(task.isSuccessful()){

                    Intent signIntent = new Intent(getApplicationContext(), ProfileActivity.class);
                    signIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(signIntent);
                } else {
                    if(task.getException() instanceof FirebaseAuthUserCollisionException){
                        Toast.makeText(SignUpActivity.this, "Already registered", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(SignUpActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });





    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mAuth = FirebaseAuth.getInstance();

        RegisterEmail = findViewById(R.id.RegisterEmail);
        RegisterPassword = findViewById(R.id.RegisterPassWord);

        Button goSign = findViewById(R.id.goSignIn);//(Button)
        goSign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goLogInIntent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(goLogInIntent);
            }
        });

        Button Register = findViewById(R.id.Register);
        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });


    }
}