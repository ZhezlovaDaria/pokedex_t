package com.example.pocedex.application;

import android.app.Application;
import android.util.Log;

import com.example.pocedex.domain.TweetsCheck;

import java.util.concurrent.TimeUnit;

import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

public class NotificationControl extends Application {

    PeriodicWorkRequest TweetcheckRequest = new PeriodicWorkRequest.Builder(TweetsCheck.class, 60, TimeUnit.MINUTES, 1, TimeUnit.MINUTES)
            .build();

    @Override
    public void onCreate() {
        super.onCreate();
        WorkManager.getInstance().cancelWorkById(TweetcheckRequest.getId());
        Log.d("States", "Application onCreate");
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        try {
            WorkManager.getInstance().enqueueUniquePeriodicWork("Notification", ExistingPeriodicWorkPolicy.REPLACE, TweetcheckRequest);
        } catch (Exception e) {
            Log.d("Set Notification error", e.getMessage());
        }
        Log.d("States", "Application onTerminate");

    }
}
