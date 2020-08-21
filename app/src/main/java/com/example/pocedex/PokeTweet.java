package com.example.pocedex;


import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class PokeTweet {

    private String created_at;
    public long id;
    public String text;
    public  int retweet_count;
    public int favorite_count;
    public Media entities;
    public PokeTweet retweeted_status;

    public User user;

    public void setCreated_at(String created_at)
    {
       this.created_at=created_at;
    }
    public String getCreated_at()
    {
        //it must be in set, but gson ignor set...
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", Locale.getDefault());
        try {
            cal.setTime(sdf.parse(created_at));
            Date date = cal.getTime();
            sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
            return sdf.format(date);
        }
        catch (Exception e)
        {
            Log.d("Fail Date", "");
        }
        return created_at;
    }

    public class User
    {
        public long id;
        public String name;
        public String screen_name;
        public String profile_image_url_https;
    }

    public class Media
    {
        public Urls[] medias;

        public class Urls
        {
            public String media_url;
        }
    }
}
