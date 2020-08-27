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
import com.example.pocedex.data.Tweet;
import com.example.pocedex.domain.NotificationControl;
import com.example.pocedex.domain.TweetsCheck;
import com.example.pocedex.domain.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    //PeriodicWorkRequest TweetcheckRequest = new PeriodicWorkRequest.Builder(TweetsCheck.class, 60, TimeUnit.MINUTES, 1, TimeUnit.MINUTES).build();
    ArrayList<Tweet> tweets = new ArrayList<>();
    TweetsAdapter adapter = new TweetsAdapter(this, tweets);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!Utils.isOnline(this)) {
            setContentView(R.layout.offline);
            return;
        }

        setContentView(R.layout.activity_main);

        updateTweetsFeed(new Network().getTweetsFeed());

        //WorkManager.getInstance().cancelWorkById(TweetcheckRequest.getId());

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
                        updateTweetsFeed(new Network().getTweetsFeed());
                    }
                }
            }
        });
        recyclerView.setAdapter(adapter);
    }

    private boolean updateTweetsFeed(List<Tweet> p) {
        if (p.isEmpty())
            return false;
        else {
            for (int i = 0; i < p.size(); i++) {
                tweets.add(p.get(i));
            }
            adapter.notifyDataSetChanged();
            return true;
        }
    }

    public void onClick(View view) {
        Intent intent = new Intent(this, PokeWikia.class);
        startActivity(intent);
    }

//    @Override
//    protected void onPause() {
//        super.onPause();
//        try {
//            WorkManager.getInstance().enqueueUniquePeriodicWork(TAG, ExistingPeriodicWorkPolicy.REPLACE, TweetcheckRequest);
//        } catch (Exception e) {
//            Log.d(TAG, e.getMessage());
//        }
//        Log.d(TAG, "MainActivity: onPause()");
//    }
}
