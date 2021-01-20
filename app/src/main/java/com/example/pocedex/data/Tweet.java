package com.example.pocedex.data;


import android.util.Log;

import com.example.pocedex.domain.Utils;

import java.util.ArrayList;
import java.util.List;

public class Tweet {

    private String created_at;
    private long id;
    private String text;
    private String shorttxt;
    private int retweet_count;
    private int favorite_count;
    private Media entities = new Media();
    private Tweet retweeted_status;

    private User user;

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setShortTxt(String text) {
        this.shorttxt = text;
    }

    public String getShortTxt() {
        if (shorttxt != null)
            return shorttxt;
        else return text;
    }

    public void setRTcount(int retweet_count) {
        this.retweet_count = retweet_count;
    }

    public int getRTcount() {
        return retweet_count;
    }

    public void setFavCount(int favorite_count) {
        this.favorite_count = favorite_count;
    }

    public int getFavCount() {
        return favorite_count;
    }

    public void setEntities(String url) {
        entities.addMediaUrl(url);
    }

    public String getEntities(int n) {
        String medias = null;
        try {
            medias = entities.medias.get(n).media_url;
        } catch (Exception e) {
            Log.d("Media", e.getMessage());
        }
        return medias;
    }

    public void setReTweet(Tweet retweeted_status) {
        this.retweeted_status = retweeted_status;
    }

    public Tweet getRetweeted_status() {
        return retweeted_status;
    }

    public void setUserId(long id) {
        user.id = id;
    }

    public long getUserId() {
        return id;
    }

    public void setUserName(String name) {
        user.name = name;
    }

    public String getUserName() {
        return user.name;
    }

    public void setUserSName(String name) {
        user.screen_name = name;
    }

    public String getUserSName() {
        return user.screen_name;
    }

    public void setUserPfp(String pfp) {
        user.profile_image_url_https = pfp;
    }

    public String getUserPfp() {
        return user.profile_image_url_https;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getCreated_at() {
        return Utils.dateFormat(created_at);
    }

    private class User {
        public long id;
        public String name;
        public String screen_name;
        public String profile_image_url_https;
    }

    private class Media {
        public List<Urls> medias = new ArrayList<Urls>();

        public void addMediaUrl(String url) {
            Urls urls = new Urls();
            urls.media_url = url;
            medias.add(urls);
        }

        public class Urls {
            public String media_url;
        }
    }
}
