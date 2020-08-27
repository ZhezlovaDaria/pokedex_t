package com.example.pocedex.presentation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.pocedex.R;
import com.example.pocedex.data.Tweet;
import com.example.pocedex.domain.Utils;

import java.util.ArrayList;
import java.util.List;

public class UnreadNews extends AppCompatActivity {


    List<Tweet> tweets = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unread_news);

        tweets = Utils.getNewTweets();
        TweetsAdapter adapter = new TweetsAdapter(this, tweets);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.urt);
        final LinearLayoutManager layoutManager
                = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    public void ToAll(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
