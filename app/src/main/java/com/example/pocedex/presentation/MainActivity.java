package com.example.pocedex.presentation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.pocedex.R;
import com.example.pocedex.data.Network;
import com.example.pocedex.data.PokeTweet;
import com.example.pocedex.domain.TweetsCheck;
import com.example.pocedex.domain.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    final String TAG = "States";
    PeriodicWorkRequest TweetcheckRequest = new PeriodicWorkRequest.Builder(TweetsCheck.class, 15, TimeUnit.MINUTES, 1, TimeUnit.MINUTES)
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

        UpdateTweetsFeed(new Network().GetTweetsFeed());

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
                        UpdateTweetsFeed(new Network().GetTweetsFeed());
                    }
                }
            }
        });
        recyclerView.setAdapter(adapter);
    }

    private boolean UpdateTweetsFeed(List<PokeTweet> p) {
        if (p.isEmpty())
            return false;
        else {
            for (int i = 0; i < p.size(); i++) {
                pokeTweets.add(p.get(i));
            }
            adapter.notifyDataSetChanged();
            return true;
        }
    }

    public void onClick(View view) {
        Intent intent = new Intent(this, PokeWikia.class);
        startActivity(intent);
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
}
