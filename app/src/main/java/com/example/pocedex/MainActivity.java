package com.example.pocedex;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {


    private static String twp="https://api.twitter.com/1.1/statuses/user_timeline.json?screen_name=Pokemon&count=2";
    JSONParser jsonParser = new JSONParser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //new TweetLenta().execute();
    }

    public void onClick(View view) {
        Intent intent = new Intent(this, PokeWikia.class);
        startActivity(intent);
        finish();
    }

    class TweetLenta extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        protected String doInBackground(String... args) {
            JSONObject json = jsonParser.makeHttpRequest(twp);
            Log.d("Create Response", json.toString());

            return null;
        }

        protected void onPostExecute(String file_url) {

        }
    }
}
