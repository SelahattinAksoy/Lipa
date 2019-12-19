package com.example.lipa;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class History extends AppCompatActivity {

    String data[] = new String[Integer.parseInt(Anasayfa.event_count)];
    String ce[]={"E"};
    ListView ls;
    String a;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        ls=findViewById(R.id.history_list);
        final List<String> gecmis = new LinkedList<>();




        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference(Anasayfa.username);

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                for (int i=0;i<data.length;i++){
                    String a=dataSnapshot.child("history").child(String.valueOf(i)).getValue().toString();
                    data[i]=a;
                    Log.e("SİK   "," DERYAC ----" +dataSnapshot.child("history").child(String.valueOf(i)).getValue().toString());

                }


                ArrayAdapter<String> adp=new ArrayAdapter<String>(History.this,R.layout.support_simple_spinner_dropdown_item,data){@Override
                public View getView(int position, View convertView, ViewGroup parent){
                    // Get the Item from ListView
                    View view = super.getView(position, convertView, parent);

                    // Initialize a TextView for ListView each Item
                    TextView tv = (TextView) view.findViewById(android.R.id.text1);

                    // Set the text color of TextView (ListView Item)
                    tv.setTextColor(Color.BLUE);

                    // Generate ListView Item using TextView
                    return view;
                }
                };

                ls.setAdapter(adp);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value

            }
        });


        ls.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position,
                                    long id) {

                getValu_for_listview_Clik(position);


            }
        });





    }

    public void getValu_for_listview_Clik(final int pos){
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference(Anasayfa.username);
        final ArrayList<String> arrlist = new ArrayList<String>(Integer.parseInt(Anasayfa.event_count));

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String a=dataSnapshot.child("history").child(String.valueOf(pos)).getValue().toString();
                AlertDialog.Builder alert = new AlertDialog.Builder(History.this);
                alert.setMessage(a).setCancelable(false).setPositiveButton("TAMAM", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
                AlertDialog al = alert.create();
                al.setTitle("DETAYLI BİLGİ");
                al.show();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

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
            Intent x=new Intent(History.this,MainActivity.class);
            startActivity(x);
        }
        if(item.getItemId()==R.id.anasayfa_item){
            Intent y=new Intent(History.this,Anasayfa.class);
            startActivity(y);
        }
        if(item.getItemId()==R.id.money_transfer){
            Intent y=new Intent(History.this,Money_Transfer.class);
            startActivity(y);
        }
        if(item.getItemId()==R.id.hesap_bilgi){
            Intent y=new Intent(History.this,Hesap.class);
            startActivity(y);
        }



        return super.onOptionsItemSelected(item);
    }
}
