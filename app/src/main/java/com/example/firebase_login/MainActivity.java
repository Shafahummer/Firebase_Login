package com.example.firebase_login;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    EditText email,password;
    Button button;
    private FirebaseAuth mAuth;

    @Override
    public void onStart() {
        super.onStart();
        Toast.makeText(this,"Already Logged in",Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        email=findViewById(R.id.email);
        password=findViewById(R.id.password);
        button=findViewById(R.id.button);



        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (email.getText().toString().isEmpty())
                    email.setError("Enter your email!");
                else if (password.getText().toString().isEmpty())
                    password.setError("Enter your password!");
                else
                    onRegister(v);
            }
        });

        mAuth = FirebaseAuth.getInstance();

    }
    public void onRegister(View view){

        String myEmail=email.getText().toString();
        String myPassword=password.getText().toString();

        mAuth.createUserWithEmailAndPassword(myEmail, myPassword)
                .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(MainActivity.this,"SignUp Success",Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(MainActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
    public void onLogin(View view){

        String myEmail=email.getText().toString();
        String myPassword=password.getText().toString();

        mAuth.signInWithEmailAndPassword(myEmail, myPassword)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.i("TAG", "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            String userID=user.getUid();
                          Toast.makeText(MainActivity.this,"Login Success",Toast.LENGTH_SHORT).show();
                          Log.i("USER","USER: "+user.toString());
                          Log.i("USER","USER: "+userID);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.i("TAG", "signInWithEmail:failure", task.getException());
                         Toast.makeText(MainActivity.this,"Login failed",Toast.LENGTH_SHORT).show();

                        }

                        // ...
                    }
                });
    }
    public void logOut(View view){
        FirebaseAuth.getInstance().signOut();
        Toast.makeText(MainActivity.this,"Signed out successfully",Toast.LENGTH_SHORT).show();
    }
}
