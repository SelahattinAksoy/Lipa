package com.example.lipa;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Currency;
import java.util.Date;

public class MainActivity extends AppCompatActivity {




    ListView lv;
    String[] urlList={"http://www.floatrates.com/daily/usd.json",
            "http://www.floatrates.com/daily/eur.json",
            "http://www.floatrates.com/daily/jpy.json",
            "http://www.floatrates.com/daily/azn.json"};

    public static String usd="$";
    public static String euro="£";
    public static String yen="Y";
    public static String manat="M";
    static  int increment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lv=findViewById(R.id.list_id);


        String a[]={usd,euro,yen,manat};
        String[] name={"USD","EUR","YEN","MNT"};


        SatınAl selo=new SatınAl(this,R.layout.row,a,name);
        lv.setAdapter(selo);
        int x =1;
        for(String i:urlList){
            fetchData f=new fetchData(i,x);
            f.execute();
            x++;
        }


    }

    class  SatınAl extends ArrayAdapter<String >{

        Context context;
        String[] a;
        int resource;
        String[] name;
        public SatınAl(@NonNull Context context, int resource,String [] a, String[] name) {
            super(context, resource,a);
            this.a= a;
            this.context=context;
            this.resource=resource;
            this.name=name;
        }

        @NonNull
        @Override
        public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {

            LayoutInflater layoutInflater=LayoutInflater.from(context);
            View v=layoutInflater.inflate(resource,null);

            final TextView money=v.findViewById(R.id.currency_id);
            final Button bt_buy=v.findViewById(R.id.buy_button_id);
            final Button bt_convert=v.findViewById(R.id.conver_button_id);
            final TextView amount=v.findViewById(R.id.currency_amaout_id);
            final TextView price=v.findViewById(R.id.currency_price_id);
            final Switch sw=v.findViewById(R.id.switch1);


            sw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                    if(b){
                        a= new String[]{MainActivity.usd, MainActivity.euro,MainActivity.yen,MainActivity.manat};
                        money.setText(a[position]);
                    }



                }
            });



            bt_buy.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {




                    AlertDialog.Builder alert=new AlertDialog.Builder(MainActivity.this);
                    alert.setMessage(price.getText().toString()+" ye "+amount.getText().toString()+" "+sw.getText().toString()+" alındı. ").setCancelable(false).setPositiveButton("TAMAM", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.cancel();
                        }
                    });
                    AlertDialog al=alert.create();
                    al.setTitle("----------BAŞARIYLA---------");
                    al.show();

                    selo(price.getText().toString(),amount.getText().toString(),sw.getText().toString());



                }
            });
            bt_convert.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    String deger=money.getText().toString();
                    String mik=amount.getText().toString();

                    double sex=(Double.parseDouble(deger)*Integer.parseInt(mik));
                    price.setText(Double.toString(sex));

                }
            });
            money.setText(a[position]);
            sw.setText(name[position]);
            return v;

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.drwa_menu,menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId()==R.id.anasayfa_item){
            Intent i=new Intent(MainActivity.this,Anasayfa.class);
            startActivity(i);
        }
        if(item.getItemId()==R.id.past_item){
            Intent y=new Intent(MainActivity.this,History.class);
            startActivity(y);
        }
        if(item.getItemId()==R.id.money_transfer){
            Intent y=new Intent(MainActivity.this,Money_Transfer.class);
            startActivity(y);
        }
        if(item.getItemId()==R.id.hesap_bilgi){
            Intent y=new Intent(MainActivity.this,Hesap.class);
            startActivity(y);
        }



        return super.onOptionsItemSelected(item);
    }


    public static  void selo(String tl,String usd,String tip){

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference(Anasayfa.username);
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //usd = dataSnapshot.child("USD").getValue().toString();
               // neww=Integer.parseInt(usd);

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value

            }
        });






        if(tip.equals("USD")){
                double val2=Double.parseDouble(usd);
                double val1=Double.parseDouble(tl);
                val1=Double.parseDouble(Anasayfa.db_tl)-val1;
                val2=Double.parseDouble(Anasayfa.db_usd)+val2;
                myRef.child("USD").setValue(String.valueOf(val2));
                myRef.child("TL").setValue(String.valueOf(val1));

                DateFormat df = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
                Date dateobj = new Date();
                int event=Integer.parseInt(Anasayfa.event_count);
                myRef.child("history").child(String.valueOf(event)).setValue(df.format(dateobj)+" :: "+" USD "+"::"+usd+" satın alındı");
                event=event+1;
                myRef.child("event").setValue(String.valueOf(event));

            }

        if(tip.equals("EUR")){
            double val2=Double.parseDouble(usd);
            double val1=Double.parseDouble(tl);
            val1=Double.parseDouble(Anasayfa.db_tl)-val1;
            val2=Double.parseDouble(Anasayfa.db_eur)+val2;
            myRef.child("EUR").setValue(String.valueOf(val2));
            myRef.child("TL").setValue(String.valueOf(val1));
            DateFormat df = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
            Date dateobj = new Date();
            int event=Integer.parseInt(Anasayfa.event_count);
            myRef.child("history").child(String.valueOf(event)).setValue(df.format(dateobj)+" :: "+" EUR "+"::"+usd+" satın alındı");
            event=event+1;
            myRef.child("event").setValue(String.valueOf(event));

        }

        if(tip.equals("YEN")){
            double val2=Double.parseDouble(usd);
            double val1=Double.parseDouble(tl);
            val1=Double.parseDouble(Anasayfa.db_tl)-val1;
            val2=Double.parseDouble(Anasayfa.db_yen)+val2;
            myRef.child("YEN").setValue(String.valueOf(val2));
            myRef.child("TL").setValue(String.valueOf(val1));
            int event=Integer.parseInt(Anasayfa.event_count);


            DateFormat df = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
            Date dateobj = new Date();
            myRef.child("history").child(String.valueOf(event)).setValue(df.format(dateobj)+" :: "+" YEN "+"::"+usd+" satın alındı");
            event=event+1;
            myRef.child("event").setValue(String.valueOf(event));

        }

        }
    }

