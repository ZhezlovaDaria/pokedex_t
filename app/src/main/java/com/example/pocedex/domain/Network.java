package com.example.pocedex.domain;

import android.os.AsyncTask;
import android.util.Log;

import com.example.pocedex.data.Pokemon;
import com.example.pocedex.data.Tweet;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Network {

    private static String pokemonsList = "https://pokeapi.co/api/v2/pokemon";
    private static String pokemonsListsNext = "https://pokeapi.co/api/v2/pokemon";
    private static String twp = "https://api.twitter.com/1.1/statuses/user_timeline.json?screen_name=Pokemon&count=2";
    private static String link;
    private static OkHttpClient client = new OkHttpClient();
    List<Pokemon> pokemons = new ArrayList<>();
    private JSONObject json;

    public void resetList() {
        pokemonsListsNext = pokemonsList;
    }

    public List<Pokemon> getPokemonsForList() {
        link = pokemonsListsNext;
        AsyncTask<String, String, String> connetionTask = new Connection().executeOnExecutor(AsyncTask.SERIAL_EXECUTOR);
        try {
            String pokemonss = connetionTask.get();
            json = new JSONObject(pokemonss);
            String s = json.toString();
            String result = s.substring(s.indexOf('['), s.indexOf(']') + 1);
            pokemonsListsNext = json.get("next").toString();
            GsonBuilder builder = new GsonBuilder();
            Gson gson = builder.create();
            Type pokemonType = new TypeToken<ArrayList<Pokemon>>() {}.getType();
            pokemons = gson.fromJson(result, pokemonType);
            return pokemons;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (Exception e) {
            Log.d("Exe", e.getMessage());
        }
        return null;
    }

    public List<Tweet> getTweetsFeed() {
        List<Tweet> tweets;
        String tweetsString = Utils.exampleString();
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        Type tweetsType = new TypeToken<ArrayList<Tweet>>() {}.getType();
        tweets = gson.fromJson(tweetsString, tweetsType);
        return tweets;
    }

    public List<Tweet> getUnreadTweets() {
        List<Tweet> tweets;
        String s = Utils.exampleString();
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        Type tweetsType = new TypeToken<ArrayList<Tweet>>() {}.getType();
        tweets = gson.fromJson(s, tweetsType);
        return tweets;
    }

    public Pokemon getPokemon(String url) {
        link = url;
        AsyncTask<String, String, String> connetionTask = new Connection().executeOnExecutor(AsyncTask.SERIAL_EXECUTOR);
        try {
            String s = connetionTask.get();
            GsonBuilder builder = new GsonBuilder();
            Gson gson = builder.create();
            return gson.fromJson(s, Pokemon.class);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static class Connection extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        protected String doInBackground(String... args) {
            Request request = new Request.Builder()
                    .url(link)
                    .get()
                    .build();
            try {
                Response response = client.newCall(request).execute();

                return response.body().string();
            } catch (Exception e) {
                Log.d("Fail Image", e.getMessage());
            }
            return null;
        }

        protected void onPostExecute(String file_url) {
        }
    }
}
