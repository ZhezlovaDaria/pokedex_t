package com.example.pocedex.domain;

import android.content.SharedPreferences;
import android.util.Log;

import com.example.pocedex.data.CommentAndFavorite;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class LocalSave {
    private static SharedPreferences SavePreferences;
    private static ArrayList<CommentAndFavorite> commentAndFavorites = new ArrayList<>();
    private static String path = "commandfav2";

    public static void open() {
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

    public static void save() {
        try {
            SharedPreferences.Editor prefsEditor = SavePreferences.edit();
            Gson gson = new Gson();
            String json = gson.toJson(LocalSave.commentAndFavorites);
            prefsEditor.putString(path, json);
            prefsEditor.commit();
        } catch (Exception e) {
            Log.d("comfavsave", e.getMessage());
        }
    }

    public static ArrayList<CommentAndFavorite> getCommentAndFavorites() {
        return commentAndFavorites;
    }

    public static void addToCommentAndFavorites(CommentAndFavorite newCommentAndFavorite) {
        commentAndFavorites.add(newCommentAndFavorite);
    }


    public static SharedPreferences getSavePreferences() {
        return SavePreferences;
    }

    public static void setSavePreferences(SharedPreferences savePreferences) {
        SavePreferences = savePreferences;
    }
}
