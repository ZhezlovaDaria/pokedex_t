package com.example.pocedex.domain;

import android.content.Context;
import android.os.Handler;
import android.util.Log;

import com.example.pocedex.data.Pokemon;
import com.example.pocedex.presentation.IUpdatePokemon;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Network {

    private String pokemonsList = "https://pokeapi.co/api/v2/pokemon";
    private static String pokemonsListsNext = "https://pokeapi.co/api/v2/pokemon";
    private String link;
    private OkHttpClient client = new OkHttpClient();
    private IUpdatePokemon iUpdatePokemon;
    Handler handler;
    private List<Pokemon> pokemons = new ArrayList<>();
    private JSONObject json;

    public void resetList() {
        pokemonsListsNext = pokemonsList;
    }

    public void getPokemonsForList(Context context, IUpdatePokemon callactivity) {
        link = pokemonsListsNext;
        iUpdatePokemon=callactivity;
        handler = new Handler(context.getMainLooper());
        Request request = new Request.Builder()
                .url(link)
                .get()
                .build();
        client.newCall(request).enqueue(new Callback() {

            @Override
            public void onFailure(Call request, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call,Response response) throws IOException {
                final String answer = response.body().string();
                if (response.isSuccessful()) {
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

                    } catch (Exception e) {
                        Log.d("Exe", e.getMessage());
                    }
                    handler.post(new Runnable() {

                        @Override
                        public void run() {
                            iUpdatePokemon.refresh(pokemons);
                        }

                    });
                }
            }
        });
    }

    public void getPokemon(Context context, String url,  IUpdatePokemon callactivity) {
        link = url;
        handler = new Handler(context.getMainLooper());
        iUpdatePokemon=callactivity;
        Request request = new Request.Builder()
                .url(link)
                .get()
                .build();
        client.newCall(request).enqueue(new Callback() {

            @Override
            public void onFailure(Call request, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call,Response response) throws IOException {
                final String answer = response.body().string();
                if (response.isSuccessful()) {
                    try {
                        GsonBuilder builder = new GsonBuilder();
                        Gson gson = builder.create();
                        Pokemon pokemon = gson.fromJson(answer, Pokemon.class);
                        final List<Pokemon> pokemons=new ArrayList<Pokemon>();
                        pokemons.add(pokemon);
                        handler.post(new Runnable() {

                            @Override
                            public void run() {
                                iUpdatePokemon.refresh(pokemons);
                            }
                        });
                    } catch (Exception e) {
                        Log.d("Exe", e.getMessage());
                    }

                }
            }
        });
    }
}
