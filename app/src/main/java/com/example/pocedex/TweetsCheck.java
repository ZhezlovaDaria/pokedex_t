package com.example.pocedex;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

public class TweetsCheck extends Worker {

    static final String TAG = "tweet";
    private static final int NOTIFY_ID = 101;
    private static String CHANNEL_ID = "Pokedex";
    private static int nn = 0;

    public TweetsCheck(
            @NonNull Context context,
            @NonNull WorkerParameters params) {
        super(context, params);
    }

    @NonNull
    @Override
    public Result doWork() {
        if (!isStopped()) {
            Log.d(TAG, "check");
            String s = Utils.ExmpStr();
            GsonBuilder builder = new GsonBuilder();
            Gson gson = builder.create();
            PokeTweet[] p = gson.fromJson(s, PokeTweet[].class);
            for (int i = 0; i < p.length; i++) {
                //if(p[i].id!=pokeTweets.get(pokeTweets.size()-1).id)
                Utils.NewTweets.add(p[i]);
                //else {break;}
            }
            nn = Utils.NewTweets.size();
            Log.d("Create Response", "");
            if (nn > 0)
                SetNot();
        }
        return Result.success();
    }

    private void SetNot() {
        Intent resultIntent = new Intent(getApplicationContext(), UnreadNews.class);
        PendingIntent resultPendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, resultIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(getApplicationContext(), CHANNEL_ID)
                        .setSmallIcon(R.mipmap.pok)
                        .setContentTitle("Pocedex")
                        .setContentText("You have " + nn + " unread news")
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                        .setContentIntent(resultPendingIntent)
                        .setAutoCancel(true);

        NotificationManagerCompat notificationManager =
                NotificationManagerCompat.from(getApplicationContext());
        notificationManager.notify(NOTIFY_ID, builder.build());
    }
}