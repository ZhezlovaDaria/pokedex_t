package com.example.pocedex;


import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class PokeTweet {

    private String created_at;
    private long id;
    private String text;
    private String shorttxt;
    private  int retweet_count;
    private int favorite_count;
    private Media entities=new Media();
    private PokeTweet retweeted_status;

    private User user;

    public void setId(long id){this.id=id;}
    public long getId() {return id;}

    public void setText(String text){this.text=text;}
    public String getText() {return text;}
    public void setShortTxt(String text){this.shorttxt=text;}
    public String getShortTxt() {
        if (shorttxt!=null)
            return shorttxt;
        else return text; }

    public void setRTcount(int retweet_count){this.retweet_count=retweet_count;}
    public int getRTcount() {return retweet_count;}

    public void setFavCount(int favorite_count){this.favorite_count=favorite_count;}
    public int getFavCount() {return favorite_count;}

    public void setEntities(String url, int n)
    {
        entities.medias[n].media_url=url;
    }
    public String getEntities(int n)
    {
        String m=null;
        try {
            m=entities.medias[n].media_url;
        }
        catch (Exception e)
        {
            Log.d("Media", e.getMessage());
        }
        return m;
    }
    public void setReTweet(PokeTweet retweeted_status) { this.retweeted_status=retweeted_status;}
    public PokeTweet getRetweeted_status() {return  retweeted_status;}

    public void setUserId(long id){user.id=id;}
    public long getUserId() {return id;}

    public void setUserName(String name){user.name=name;}
    public String getUserName() {return user.name;}

    public void setUserSName(String name){user.screen_name=name;}
    public String getUserSName() {return user.screen_name;}

    public void setUserPfp(String pfp){user.profile_image_url_https=pfp;}
    public String getUserPfp() {return user.profile_image_url_https;}

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

    private class User
    {
        public long id;
        public String name;
        public String screen_name;
        public String profile_image_url_https;
    }

    private class Media
    {
        public Urls[] medias=new Urls[4];

        public class Urls
        {
            public String media_url;
        }
    }
}
