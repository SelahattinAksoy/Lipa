package com.example.lipa;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class Login extends AppCompatActivity{

    Button bt;
    TextView bt_kyıt;
    TextView user;
    TextView pass;
    private ProgressDialog prgrs;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();
        prgrs=new ProgressDialog(this);

        bt=findViewById(R.id.login);
        user=findViewById(R.id.hspno);
        pass=findViewById(R.id.pass);
        bt_kyıt=findViewById(R.id.kaydol);

        //for register
        bt_kyıt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i=new Intent(Login.this,Register.class);
                startActivity(i);
                bt.setText("DCDS");
            }
        });

        //for login
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name=user.getText().toString().trim();
                String passW=pass.getText().toString();

                if(name.matches("") || passW.matches("")){
                    Toast.makeText(Login.this, "Şifreler name girrr",
                            Toast.LENGTH_SHORT).show();

                }
                else {
                    signTo(name,passW);
                }


            }
        });




    }

    public void signTo(final String email, String password){
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            // Sign in success, update UI with the signed-in user's information
                            Intent i=new Intent(Login.this,Anasayfa.class);
                            i.putExtra("mail", email);
                            startActivity(i);
                        } else {
                            // If sign in fails, display a message to the user.

                            Toast.makeText(Login.this, "Giriş yapamadınız",
                                    Toast.LENGTH_SHORT).show();

                        }

                        // ...
                    }
                });
    }



}
