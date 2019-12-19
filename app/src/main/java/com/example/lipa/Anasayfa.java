package com.example.lipa;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Anasayfa extends AppCompatActivity {

    Button usd;
    Button eur;
    Button mnt;
    Button yen;
    TextView user_name_anasayfa;
    static boolean oguz=true;
    static  String username;
    public static String db_usd,db_eur,db_yen,db_tl,event_count;
    DatabaseReference myRef;
    static String email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anasayfa);
        FirebaseDatabase database = FirebaseDatabase.getInstance();



        user_name_anasayfa=findViewById(R.id.kullanıcı_hesap);

        if(oguz) {
            email = getIntent().getStringExtra("mail");
            String[] arrOfStr = email.split(".com", 2);
            myRef = database.getReference(arrOfStr[0]);
            username=arrOfStr[0];
            oguz=false;
        }


        if(myRef!=null){


        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                db_usd = dataSnapshot.child("USD").getValue().toString();
                db_eur = dataSnapshot.child("EUR").getValue().toString();
                db_yen = dataSnapshot.child("YEN").getValue().toString();
                db_tl = dataSnapshot.child("TL").getValue().toString();
                event_count=dataSnapshot.child("event").getValue().toString();
                user_name_anasayfa.setText(dataSnapshot.child("Name").getValue().toString()+" "+
                        dataSnapshot.child("Surname").getValue().toString());


            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value

            }
        });
        }


        usd=findViewById(R.id.show_amount_fragment_usd_id);
        usd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveDatabase a=new saveDatabase();
                a.selo();
                getSupportFragmentManager().beginTransaction().replace(R.id.show_amount_fragment_id,new show_amount_fragment_usd()).commit();
            }
        });


        eur=findViewById(R.id.show_amount_fragment_eur_id);
        eur.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getSupportFragmentManager().beginTransaction().replace(R.id.show_amount_fragment_id,new show_amount_fragment_eur_fragment()).commit();
            }
        });



        yen=findViewById(R.id.show_amount_fragment_yen_id);
        yen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getSupportFragmentManager().beginTransaction().replace(R.id.show_amount_fragment_id,new show_amount_fragment_yen()).commit();
            }
        });

        mnt=findViewById(R.id.show_amount_fragment_mnt_id);
        mnt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getSupportFragmentManager().beginTransaction().replace(R.id.show_amount_fragment_id,new show_amount_fragment_manat()).commit();
                FirebaseDatabase database = FirebaseDatabase.getInstance();

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
            Intent x=new Intent(Anasayfa.this,MainActivity.class);
            startActivity(x);
        }
        if(item.getItemId()==R.id.past_item){
            Intent y=new Intent(Anasayfa.this,History.class);
            startActivity(y);
        }
        if(item.getItemId()==R.id.money_transfer){
            Intent y=new Intent(Anasayfa.this,Money_Transfer.class);
            startActivity(y);
        }
        if(item.getItemId()==R.id.hesap_bilgi){
            Intent y=new Intent(Anasayfa.this,Hesap.class);
            startActivity(y);
        }





        return super.onOptionsItemSelected(item);
    }
}
