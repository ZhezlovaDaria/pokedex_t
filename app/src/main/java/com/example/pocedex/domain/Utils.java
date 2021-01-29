package com.example.pocedex.domain;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.example.pocedex.data.CommentAndFavorite;
import com.example.pocedex.data.Pokemon;

import java.util.ArrayList;
import java.util.List;


public class Utils {

    private static final String APP_PREFERENCES = "commandfav";

    public static String getPreferenses() {
        return APP_PREFERENCES;
    }

    private static LocalSave localSave;

    private static List<Integer> notRandom;

    public static boolean isOnline(Context context) {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();

        return activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
    }

    public static List<Integer> getNotRandomNumbers() {
        ArrayList<CommentAndFavorite> commentAndFavorite = localSave.getCommentAndFavorites();
        notRandom = new ArrayList<Integer>();
        for (int i = 0; i < commentAndFavorite.size(); i++) {
            if (commentAndFavorite.get(i).getIsFav())
                notRandom.add(commentAndFavorite.get(i).getId());
        }
        return notRandom;
    }

    public static void setLocalSave(LocalSave newLocalSave) {
        localSave = newLocalSave;
    }

    public static LocalSave getLocalSave() {
        return localSave;
    }

    public static CommentAndFavorite findOnId(
            int id, ArrayList<CommentAndFavorite> caf) {
        try {
            for (CommentAndFavorite c : caf) {
                if (c.equals(id)) {
                    return c;
                }
            }
        } catch (Exception e) {
            Log.d("", e.getMessage());
        }
        return null;
    }

    public static void save(Pokemon pokemon, CommentAndFavorite commentAndFavorite) {
        CommentAndFavorite commentAndFavorite1Check = findOnId(pokemon.getId(), localSave.getCommentAndFavorites());
        if (commentAndFavorite1Check == null) {
            localSave.addToCommentAndFavorites(commentAndFavorite);
        }
        try {
            localSave.save();
        } catch (Exception e) {
            Log.d("comfavsave", e.getMessage());
        }
    }

    public static void saveShowDialog(boolean show) {
        localSave.saveMenu(show);
    }

    public static boolean openShowDialog() {
        localSave.openMenu();
        return localSave.getShowDialod();
    }
}
