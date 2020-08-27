package com.example.pocedex.data;

import android.os.AsyncTask;
import android.util.Log;

import com.example.pocedex.domain.Utils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Network {

    private static String pw = "https://pokeapi.co/api/v2/pokemon";
    private static String pwnext = "https://pokeapi.co/api/v2/pokemon";
    private static String twp = "https://api.twitter.com/1.1/statuses/user_timeline.json?screen_name=Pokemon&count=2";
    private static String link;
    List<Pokemon> pokemons = new ArrayList<>();
    private static int imid = 0;
    private JSONObject json;

    public void resetList() {
        pwnext = pw;
        imid = 0;
    }

    public List<Pokemon> getPokemonsForList() {
        link = pwnext;
        AsyncTask fpl = new Connection().executeOnExecutor(AsyncTask.SERIAL_EXECUTOR);
        try {
            String pokemonss = fpl.get().toString();
            json = new JSONObject(pokemonss);
            String s = json.toString();
            String result = s.substring(s.indexOf('['), s.indexOf(']') + 1);

            pwnext = json.get("next").toString();
            JSONArray jsonArray = new JSONArray(result);
            for (int i = 0; i < jsonArray.length(); i++) {
                pokemons.add(decodeJSON(jsonArray.getJSONObject(i)));
                pokemons.get(i).setId(imid);
                imid++;
            }
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
        List<Tweet> tweets = new ArrayList<Tweet>();
        JSONObject json;
        String s = Utils.exampleString();
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        Tweet[] p = gson.fromJson(s, Tweet[].class);
        if (p.length != 0) {
            for (int i = 0; i < p.length; i++) {
                tweets.add(p[i]);
            }
            return tweets;
        }
        return null;
    }

    public List<Tweet> getUnreadTweets() {
        List<Tweet> tweets = new ArrayList<Tweet>();
        String s = Utils.exampleString();
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        Tweet[] p = gson.fromJson(s, Tweet[].class);
        if (p.length != 0) {
            for (int i = 0; i < p.length; i++) {
                //if(p[i].id!=tweets.get(tweets.size()-1).id)
                tweets.add(p[i]);
                //else {break;}
            }
            return tweets;
        }
        return null;
    }

    public Pokemon getPokemon(String url) {
        link = url;
        AsyncTask fpl = new Connection().executeOnExecutor(AsyncTask.SERIAL_EXECUTOR);
        try {
            String s = fpl.get().toString();
            GsonBuilder builder = new GsonBuilder();
            Gson gson = builder.create();
            Pokemon pokemon = gson.fromJson(s, Pokemon.class);
            return pokemon;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Pokemon decodeJSON(JSONObject json) {
        try {
            String name = json.get("name").toString();
            String link = json.get("url").toString();
            Pokemon p = new Pokemon();
            p.setName(name);
            p.setUrl(link);
            return p;

        } catch (Exception e) {
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
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(link)
                    .get()
                    .build();
            try {
                Response response = client.newCall(request).execute();

                return response.body().string();
            } catch (Exception e) {
                Log.d("Fail Image", "png");
            }
            return null;
        }

        protected void onPostExecute(String file_url) {
        }
    }
}
