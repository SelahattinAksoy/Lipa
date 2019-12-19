package com.example.lipa;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Transfer_was_done extends AppCompatActivity {

    Button bt;
    int neww;
    int a;
    int top;
    String usd;

    TextView miktar_textview,alıcı_textview,time_textview,sender_textview;
    String miktar,alıcı,time,sender;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transfer_was_done);

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference(Anasayfa.username);

        bt=findViewById(R.id.transfer_was_done_btn);
        miktar_textview=findViewById(R.id.transfer_was_done_amount);
        alıcı_textview=findViewById(R.id.transfer_was_done_reciver);
        time_textview=findViewById(R.id.transfer_was_done_date);
        sender_textview=findViewById(R.id.transfer_was_done_sender);



         miktar = getIntent().getStringExtra("miktar");
         alıcı = getIntent().getStringExtra("reciver");
         time = getIntent().getStringExtra("send_time");
         sender = getIntent().getStringExtra("sender");

        miktar_textview.setText(miktar+" TL");
        alıcı_textview.setText(alıcı);
        time_textview.setText(time);
        sender_textview.setText(sender);




        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int event=Integer.parseInt(Anasayfa.event_count);
                myRef.child("history").child(String.valueOf(event)).setValue(time+" :: "+"::"+miktar+" TL "+alıcı+"ya gönderildi.");
                event=event+1;
                myRef.child("event").setValue(String.valueOf(event));



                Intent i=new Intent(Transfer_was_done.this,Money_Transfer.class);
                startActivity(i);

            }
        });


        /*
        final FirebaseDatabase database = FirebaseDatabase.getInstance();

        mik = getIntent().getStringExtra("miktar");


        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final DatabaseReference myRef = database.getReference(Anasayfa.username);

                myRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        usd = dataSnapshot.child("TL").getValue().toString();
                        neww=(int)Double.parseDouble(usd);



                    }

                    @Override
                    public void onCancelled(DatabaseError error) {
                        // Failed to read value

                    }
                });


                if(neww>0 ){

                    a=Integer.parseInt(mik);
                    top=neww-a;
                    myRef.child("TL").setValue(String.valueOf(top));
                    Intent i=new Intent(Transfer_was_done.this,Money_Transfer.class);
                    startActivity(i);
                }


            }
        });
        */


    }
}
