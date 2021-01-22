package com.example.pocedex.domain;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.example.pocedex.data.Pokemon;
import com.example.pocedex.data.Tweet;
import com.example.pocedex.presentation.PageFragment;
import com.example.pocedex.presentation.PokemonCardActivity;
import com.example.pocedex.presentation.PokemonsWikiaActivity;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import androidx.fragment.app.FragmentManager;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Network {

    private static String pokemonsList = "https://pokeapi.co/api/v2/pokemon";
    private static String pokemonsListsNext = "https://pokeapi.co/api/v2/pokemon";
    private static String twp = "https://api.twitter.com/1.1/statuses/user_timeline.json?screen_name=Pokemon&count=2";
    private static String link;
    private static OkHttpClient client = new OkHttpClient();
    private static int mode = 0;
    private static Context activityContext;
    private static List<Pokemon> pokemons = new ArrayList<>();
    private static JSONObject json;

    public void resetList() {
        pokemonsListsNext = pokemonsList;
    }

    public void getPokemonsForList(Context context) {
        mode = 1;
        link = pokemonsListsNext;
        activityContext = context;
        new Connection().executeOnExecutor(AsyncTask.SERIAL_EXECUTOR);
    }

    public static void refreshPokemonsForList(String answer) {
        try {
            json = new JSONObject(answer);
            String s = json.toString();
            String result = s.substring(s.indexOf('['), s.indexOf(']') + 1);
            pokemonsListsNext = json.get("next").toString();
            GsonBuilder builder = new GsonBuilder();
            Gson gson = builder.create();
            Type pokemonType = new TypeToken<ArrayList<Pokemon>>() {
            }.getType();
            pokemons = gson.fromJson(result, pokemonType);
            ((PokemonsWikiaActivity) activityContext).updatePokemonList(pokemons);

        } catch (Exception e) {
            Log.d("Exe", e.getMessage());
        }
    }

    public List<Tweet> getTweetsFeed() {
        List<Tweet> tweets;
        String tweetsString = Utils.exampleString();
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        Type tweetsType = new TypeToken<ArrayList<Tweet>>() {
        }.getType();
        tweets = gson.fromJson(tweetsString, tweetsType);
        return tweets;
    }

    public List<Tweet> getUnreadTweets() {
        List<Tweet> tweets;
        String s = Utils.exampleString();
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        Type tweetsType = new TypeToken<ArrayList<Tweet>>() {
        }.getType();
        tweets = gson.fromJson(s, tweetsType);
        return tweets;
    }

    public void getPokemon(Context context, String url) {
        mode = 2;
        activityContext = context;
        link = url;
        new Connection().executeOnExecutor(AsyncTask.SERIAL_EXECUTOR);
    }

    public static void refreshGetPokemon(String answer) {
        try {
            GsonBuilder builder = new GsonBuilder();
            Gson gson = builder.create();
            Pokemon pokemon = gson.fromJson(answer, Pokemon.class);
            ((PokemonCardActivity) activityContext).setPokemon(pokemon);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static class Connection extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        protected String doInBackground(String... args) {
            if (link==null||link.equals("null"))
                return null;
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

        protected void onPostExecute(String answer) {

            switch (mode) {
                case 1:
                    refreshPokemonsForList(answer);

                    break;
                case 2:
                    refreshGetPokemon(answer);
                    break;
                default:
                    mode = 0;
                    break;
            }
        }
    }
}
