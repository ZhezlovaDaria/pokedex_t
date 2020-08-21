package com.example.pocedex;

import android.util.Log;


import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.work.Worker;

public class TweetsCheck extends Worker {

    static final String TAG = "tweet";
    private static final int NOTIFY_ID = 101;
    private static String CHANNEL_ID = "Pokedex";
    private static int nn=0;

    @NonNull
    @Override
    public WorkerResult doWork() {


        Log.d(TAG, "check");
        SetNot();

        nn++;

        return WorkerResult.SUCCESS;
    }

    private void SetNot()
    {
        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(getApplicationContext(), CHANNEL_ID)
                        .setSmallIcon(R.drawable.ic_launcher_background)
                        .setContentTitle("Pocedex")
                        .setContentText("You have "+nn+" unread news")
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat notificationManager =
                NotificationManagerCompat.from(getApplicationContext());
        notificationManager.notify(NOTIFY_ID, builder.build());
    }
}