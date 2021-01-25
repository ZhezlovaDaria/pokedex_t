package com.example.pocedex.domain;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.pocedex.data.CommentAndFavorite;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class LocalSave {
    private SharedPreferences SavePreferences;
    private ArrayList<CommentAndFavorite> commentAndFavorites = new ArrayList<>();
    private String path = "commandfav2";

    public LocalSave(Context context) {
        SavePreferences = context.getSharedPreferences(Utils.getPreferenses(), Context.MODE_PRIVATE);
    }

    public void open() {
        commentAndFavorites.clear();
        try {
            Gson gson = new Gson();
            String json = SavePreferences.getString(path, "");
            if (!json.equals("")) {
                Type cafType = new TypeToken<ArrayList<CommentAndFavorite>>() {
                }.getType();
                commentAndFavorites = gson.fromJson(json, cafType);
            }
        } catch (Exception e) {
            Log.d("prefs", e.getMessage());
        }
    }

    public void save() {
        try {
            SharedPreferences.Editor prefsEditor = SavePreferences.edit();
            Gson gson = new Gson();
            String json = gson.toJson(commentAndFavorites);
            prefsEditor.putString(path, json);
            prefsEditor.apply();
        } catch (Exception e) {
            Log.d("comfavsave", e.getMessage());
        }
    }

    public ArrayList<CommentAndFavorite> getCommentAndFavorites() {
        return commentAndFavorites;
    }

    public void addToCommentAndFavorites(CommentAndFavorite newCommentAndFavorite) {
        commentAndFavorites.add(newCommentAndFavorite);
    }
}
