package com.example.pocedex;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    final String TAG = "States";
    private static String twp = "https://api.twitter.com/1.1/statuses/user_timeline.json?screen_name=Pokemon&count=2";
    JSONParser jsonParser = new JSONParser();
    PeriodicWorkRequest TweetcheckRequest = new PeriodicWorkRequest.Builder(TweetsCheck.class, 60, TimeUnit.MINUTES, 1, TimeUnit.MINUTES)
            .build();
    ArrayList<PokeTweet> pokeTweets = new ArrayList<>();
    TweetsAdapter adapter = new TweetsAdapter(this, pokeTweets);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!Utils.isOnline(this)) {
            setContentView(R.layout.offline);
            return;
        }

        setContentView(R.layout.activity_main);

        if (Utils.isOnline(this))

        new TweetLenta().execute();

        WorkManager.getInstance().cancelWorkById(TweetcheckRequest.getId());

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.twwfeed);
        final LinearLayoutManager layoutManager
                = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0) {
                    if ((layoutManager.getChildCount() + layoutManager.findFirstVisibleItemPosition()) >= layoutManager.getItemCount()) {
                        Log.d("TAG", "End of list");
                        new TweetLenta().execute();
                    }
                }
            }
        });
        recyclerView.setAdapter(adapter);
    }


    public void onClick(View view) {
        Intent intent = new Intent(this, PokeWikia.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onPause() {
        super.onPause();
        try {
            WorkManager.getInstance().enqueueUniquePeriodicWork(TAG, ExistingPeriodicWorkPolicy.REPLACE, TweetcheckRequest);
        } catch (Exception e) {
            Log.d(TAG, e.getMessage());
        }
        Log.d(TAG, "MainActivity: onPause()");
    }

    @Override
    protected void onStop() {
        super.onStop();
        try {
            WorkManager.getInstance().enqueueUniquePeriodicWork(TAG, ExistingPeriodicWorkPolicy.REPLACE, TweetcheckRequest);
        } catch (Exception e) {
            Log.d(TAG, e.getMessage());
        }
        Log.d(TAG, "MainActivity: onStop()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            WorkManager.getInstance().enqueueUniquePeriodicWork(TAG, ExistingPeriodicWorkPolicy.REPLACE, TweetcheckRequest);
        } catch (Exception e) {
            Log.d(TAG, e.getMessage());
        }
        Log.d(TAG, "MainActivity: onDestroy()");
    }

    class TweetLenta extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        protected String doInBackground(String... args) {
            // JSONObject json = jsonParser.makeHttpRequest(twp);
            String s = Utils.ExmpStr();
            GsonBuilder builder = new GsonBuilder();
            Gson gson = builder.create();
            PokeTweet[] p = gson.fromJson(s, PokeTweet[].class);
            for (int i = 0; i < p.length; i++) {
                pokeTweets.add(p[i]);
            }

            Log.d("Create Response", "");

            return null;
        }

        protected void onPostExecute(String file_url) {
            adapter.notifyDataSetChanged();
        }


    }
}
