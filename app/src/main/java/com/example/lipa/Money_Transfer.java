package com.example.lipa;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TabHost;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.net.Inet4Address;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Money_Transfer extends AppCompatActivity {

    Button transfer;
    TextView hesapNo;
    TextView miktar;
    String usd,usd_sender;
    int neww,neww_sender;
    int usda;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_money__transfer);

        transfer=findViewById(R.id.transfer_send);
        hesapNo=findViewById(R.id.transfer_hesap_no);
        miktar=findViewById(R.id.transfer_miktar);
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();


        transfer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                  rootRef.addListenerForSingleValueEvent(new ValueEventListener() {
                      @Override
                      public void onDataChange(DataSnapshot snapshot) {
                          if (!snapshot.hasChild(hesapNo.getText().toString())) {
                              // The child doesn't exist


                              AlertDialog.Builder alert = new AlertDialog.Builder(Money_Transfer.this);
                              alert.setMessage(" Böyle biri yoh aq. ").setCancelable(false).setPositiveButton("TAMAM", new DialogInterface.OnClickListener() {
                                  @Override
                                  public void onClick(DialogInterface dialogInterface, int i) {
                                      dialogInterface.cancel();
                                  }
                              });
                              AlertDialog al = alert.create();
                              al.setTitle("----------UYARI---------");
                              al.show();
                              transfer.setText("TAMAMDIR");


                          }
                          if (snapshot.hasChild(hesapNo.getText().toString())) {

                              //if childe does exist

                              usda = Integer.parseInt(miktar.getText().toString());

                              DatabaseReference myRef = database.getReference(hesapNo.getText().toString());
                              final DatabaseReference myRef_sender = database.getReference(Anasayfa.username);

                              myRef_sender.addListenerForSingleValueEvent(new ValueEventListener() {
                                  @Override
                                  public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                      usd_sender = dataSnapshot.child("TL").getValue().toString();
                                      neww_sender = (int) Double.parseDouble(usd_sender);

                                  }

                                  @Override
                                  public void onCancelled(@NonNull DatabaseError databaseError) {

                                  }
                              });
                              myRef.addValueEventListener(new ValueEventListener() {
                                  @Override
                                  public void onDataChange(DataSnapshot dataSnapshot) {

                                      usd = dataSnapshot.child("TL").getValue().toString();
                                      neww = Integer.parseInt(usd);


                                  }

                                  @Override
                                  public void onCancelled(DatabaseError error) {
                                      // Failed to read value

                                  }
                              });


                              int top = usda + neww;
                              if (top > 0 & neww != 0) {


                                  int a = usda;
                                  int topa = neww_sender - a;
                                  myRef_sender.child("TL").setValue(String.valueOf(topa));
                                  myRef.child("TL").setValue(String.valueOf(top));


                                  Intent i = new Intent(Money_Transfer.this, Transfer_was_done.class);
                                  DateFormat df = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
                                  Date dateobj = new Date();
                                  i.putExtra("miktar", String.valueOf(usda));
                                  i.putExtra("reciver", hesapNo.getText().toString());
                                  i.putExtra("send_time", df.format(dateobj));
                                  i.putExtra("sender", Anasayfa.username);

                                  startActivity(i);


                              } else {
                                  AlertDialog.Builder alert = new AlertDialog.Builder(Money_Transfer.this);
                                  alert.setMessage(" para yollican emin misin  alındı. ").setCancelable(false).setPositiveButton("TAMAM", new DialogInterface.OnClickListener() {
                                      @Override
                                      public void onClick(DialogInterface dialogInterface, int i) {
                                          dialogInterface.cancel();
                                      }
                                  });
                                  AlertDialog al = alert.create();
                                  al.setTitle("----------UYARI---------");
                                  al.show();
                                  transfer.setText("ONAYLA");
                              }
                          }
                      }

                      @Override
                      public void onCancelled(@NonNull DatabaseError databaseError) {

                      }
                  });
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
            Intent x=new Intent(Money_Transfer.this,MainActivity.class);
            startActivity(x);
        }
        if(item.getItemId()==R.id.past_item){
            Intent y=new Intent(Money_Transfer.this,History.class);
            startActivity(y);
        }
        if(item.getItemId()==R.id.anasayfa_item){
            Intent y=new Intent(Money_Transfer.this,Anasayfa.class);
            startActivity(y);
        }
        if(item.getItemId()==R.id.hesap_bilgi){
            Intent y=new Intent(Money_Transfer.this,Hesap.class);
            startActivity(y);
        }



        return super.onOptionsItemSelected(item);
    }

}
