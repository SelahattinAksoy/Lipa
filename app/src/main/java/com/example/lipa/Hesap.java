package com.example.lipa;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Hesap extends AppCompatActivity {

    TextView kullanıcı,hesap_no,kyt_tarih;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hesap);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference(Anasayfa.username);

        kullanıcı=findViewById(R.id.kullanıcı_hesap);
        hesap_no=findViewById(R.id.hesap_no_hesap);
        kyt_tarih=findViewById(R.id.kayıt_tarih_hesap);

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                String  name = dataSnapshot.child("Name").getValue().toString();
                String surname = dataSnapshot.child("Surname").getValue().toString();
                String time = dataSnapshot.child("time").getValue().toString();


                kullanıcı.setText(name + " "+surname);
                hesap_no.setText(Anasayfa.email);
                kyt_tarih.setText(time);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value

            }
        });
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.drwa_menu,menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId()==R.id.kur_item){
            Intent x=new Intent(Hesap.this,MainActivity.class);
            startActivity(x);
        }
        if(item.getItemId()==R.id.past_item){
            Intent y=new Intent(Hesap.this,History.class);
            startActivity(y);
        }
        if(item.getItemId()==R.id.anasayfa_item){
            Intent y=new Intent(Hesap.this,Anasayfa.class);
            startActivity(y);
        }
        if(item.getItemId()==R.id.money_transfer){
            Intent y=new Intent(Hesap.this,Money_Transfer.class);
            startActivity(y);
        }
        if(item.getItemId()==R.id.logout){
            Intent y=new Intent(Hesap.this,Login.class);
            startActivity(y);
        }



        return super.onOptionsItemSelected(item);
    }
}
