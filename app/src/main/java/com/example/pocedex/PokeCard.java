package com.example.pocedex;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class PokeCard extends AppCompatActivity {

    private static String pok = "";
    JSONParser jsonParser = new JSONParser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poke_card);
        Bundle arguments = getIntent().getExtras();
        pok = arguments.get("link").toString();

        new Card().execute();
    }


    class Card extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        protected String doInBackground(String... args) {
            JSONObject json = jsonParser.makeHttpRequest(pok);

            Log.d("Create Response", json.toString());

            return null;
        }

        protected void onPostExecute(String file_url) {
        }


        public  Pokemon decodeJSON(JSONObject json)
        {
            try {
                String name = json.get("name").toString();
                String link = json.get("url").toString();
                Pokemon p=new Pokemon();
                p.setName(name);
                p.setLink(link);
                return p;

            }catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}
