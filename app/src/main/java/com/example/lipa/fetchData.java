package com.example.lipa;
import android.os.AsyncTask;
import android.util.Log;

import com.example.lipa.MainActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;

public class fetchData extends AsyncTask<Void,Void,Void> {
    String data;
    String dataParsed="";
    String singleParsed="";
    String a;
    int b;

    fetchData(String a,int b){
        this.a=a;
        this.b=b;
    }
    @Override
    protected Void doInBackground(Void... voids) {

        try {
            URL url = new URL(a);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

            String line = "";

            line = bufferedReader.readLine();
            data = data + line;

            String[] arrOfStr = data.split("try", 2);
            String[] aaa = arrOfStr[1].split("rate", 2);

            dataParsed =dataParsed+ aaa[1].substring(2, 7);


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;


    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);


        if(b==1){
            MainActivity.usd=dataParsed;
        }    if(b==2){
            MainActivity.euro=dataParsed;
        }
        if(b==3){
            MainActivity.yen=dataParsed;
        }
        else {
            MainActivity.manat=dataParsed;
        }

    }
}
