package com.example.pocedex;


public class PokeTweet {

    public String created_at;
    public long id;
    public String text;
    public  int retweet_count;
    public int favorite_count;
    public Media entities;

    public User user;

    private class User
    {
        public long id;
        public String name;
        public String screen_name;
        public String profile_image_url_https;
    }

    private class Media
    {
        public Urls[] medias;

        private class Urls
        {
            public String media_url;
        }
    }
}
