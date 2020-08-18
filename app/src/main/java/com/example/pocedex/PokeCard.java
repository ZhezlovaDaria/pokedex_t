package com.example.pocedex;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.URL;

public class PokeCard extends AppCompatActivity {

    private static String pok = "";
    JSONParser jsonParser = new JSONParser();
    Pokemon pokemon;
    Bitmap bitmap;
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
            String s=json.toString();
            GsonBuilder builder = new GsonBuilder();
            Gson gson = builder.create();
            pokemon = gson.fromJson(s, Pokemon.class);
            try {

                InputStream is = new URL(pokemon.sprites.other.official_artwork.front_default).openStream();
                bitmap = BitmapFactory.decodeStream(is);
            }
            catch (Exception e)
            {
                Log.d("Fail Image", pokemon.sprites.other.official_artwork.front_default);
            }
            Log.d("Create Response", json.toString());

            return null;
        }

        protected void onPostExecute(String file_url) {
            TextView tv1 = (TextView) findViewById(R.id.pokname);
            TextView tv2 = (TextView) findViewById(R.id.ab);
            ImageView iv1 =(ImageView) findViewById(R.id.pokeim);
            tv1.setText(pokemon.getName());
            iv1.setImageBitmap(bitmap);
        }

    }
}
