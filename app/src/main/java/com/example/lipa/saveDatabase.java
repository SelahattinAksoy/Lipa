package com.example.lipa;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;

public class saveDatabase {


    public saveDatabase(){


    }
    public  void selo(){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("message");

        myRef.setValue("allah");
    }

    public void newUser(String mail,String name,String surname){

        String[] arrOfStr = mail.split(".com", 2);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference(arrOfStr[0]);

        SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
        Date date = new Date(System.currentTimeMillis());


        myRef.child("Name").setValue(name);
        myRef.child("Surname").setValue(surname);
        myRef.child("USD").setValue("1000");
        myRef.child("EUR").setValue("1000");
        myRef.child("YEN").setValue("1000");
        myRef.child("TL").setValue("1000");
        myRef.child("event").setValue("0");
        myRef.child("time").setValue(formatter.format(date));



        /*
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("selocanlar");
        String ord="mert";
        String count="huhu";
        myRef.child(ord).setValue(count);*/
    }
    public void mert(){

    }

}
