package com.example.lipa;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Register extends AppCompatActivity {

    TextView user;
    TextView password;
    TextView password_check;
    Button giris;
    TextView name;
    TextView surname;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);




        mAuth = FirebaseAuth.getInstance();
        user=findViewById(R.id.username_register_id);
        giris=findViewById(R.id.register);
        password=findViewById(R.id.password_register_id);
        password_check=findViewById(R.id.password_register_id_check);
        name=findViewById(R.id.name_register_id);
        surname=findViewById(R.id.surname_register_id);



        giris.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String kullanıcı_Adı=user.getText().toString();
                String sifre=password.getText().toString();
                String sifre_chek=password_check.getText().toString();

                String ord=name.getText().toString();
                String count=surname.getText().toString();

                if(sifre.equals(sifre_chek)){
                    saveDatabase yeni=new saveDatabase();
                    yeni.newUser(kullanıcı_Adı,ord,count);
                    registerTo(kullanıcı_Adı,sifre);
                }
                else{
                    Toast.makeText(Register.this, "Şifreler aynı diil",
                            Toast.LENGTH_SHORT).show();

                }
            }
        });
    }

    public void registerTo(String email,String password){

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information

                            Toast.makeText(Register.this, "Başarılı bir şekilde kaydoldu.",
                                    Toast.LENGTH_SHORT).show();
                            Intent i=new Intent(Register.this,Login.class);
                            startActivity(i);


                        } else {
                            // If sign in fails, display a message to the user.

                            Toast.makeText(Register.this, "Kaydolamadınız tekrar dene.",
                                    Toast.LENGTH_SHORT).show();

                        }

                        // ...
                    }
                });

    }
}
