package com.example.pocedex.domain;

import android.content.SharedPreferences;
import android.util.Log;

import com.example.pocedex.data.CommAndFav;
import com.google.gson.Gson;

import java.util.ArrayList;

public class LocalSave {
    public static SharedPreferences SavePreferences;
    public static ArrayList<CommAndFav> CaFpoke = new ArrayList<>();
    private static String path="commandfav2";

    public static void open() {
        CaFpoke.clear();
        try {
            Gson gson = new Gson();
            String json = SavePreferences.getString(path, "");
            CommAndFav[] caf;
            caf = gson.fromJson(json, CommAndFav[].class);
            for (int i = 0; i < caf.length; i++) {
                CaFpoke.add(caf[i]);
            }
        } catch (Exception e) {
            Log.d("prefs", e.getMessage());
        }
    }

    public static void save() {
        try {
            SharedPreferences.Editor prefsEditor = SavePreferences.edit();
            Gson gson = new Gson();
            String json = gson.toJson(LocalSave.CaFpoke);
            prefsEditor.putString(path, json);
            prefsEditor.commit();
        } catch (Exception e) {
            Log.d("comfavsave", e.getMessage());
        }
    }

}
