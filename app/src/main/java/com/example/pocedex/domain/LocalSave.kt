package com.example.pocedex.domain

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.example.pocedex.data.CommentAndFavorite
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.ArrayList

internal class LocalSave(context: Context) {
    private val SavePreferences: SharedPreferences
    private var commentAndFavorites = ArrayList<CommentAndFavorite>()
    private val path = "commandfav2"
    private val pathMenu = "menu"
    private var showDialog = true

    init {
        SavePreferences = context.getSharedPreferences(Utils.preferenses, Context.MODE_PRIVATE)
    }

    fun open() {
        commentAndFavorites.clear()
        try {
            val gson = Gson()
            val json = SavePreferences.getString(path, "")
            if (json != "") {
                val cafType = object : TypeToken<ArrayList<CommentAndFavorite?>>() {
                }.type
                commentAndFavorites = gson.fromJson(json, cafType)
            }
        } catch (e: Exception) {
            Log.d("prefs", e.message)
        }

    }

    fun save() {
        try {
            val prefsEditor = SavePreferences.edit()
            val gson = Gson()
            val json = gson.toJson(commentAndFavorites)
            prefsEditor.putString(path, json)
            prefsEditor.apply()
        } catch (e: Exception) {
            Log.d("comfavsave", e.message)
        }

    }

    fun openMenu() {
        try {
            val json = SavePreferences.getString(pathMenu, "")
            if (json != "") {
                showDialog = java.lang.Boolean.valueOf(json)
            }
        } catch (e: Exception) {
            Log.d("prefs", e.message)
        }

    }

    fun saveMenu(show: Boolean) {
        try {
            showDialog = show
            val prefsEditor = SavePreferences.edit()
            val gson = Gson()
            val json = gson.toJson(showDialog)
            prefsEditor.putString(pathMenu, json)
            prefsEditor.apply()
        } catch (e: Exception) {
            Log.d("menusave", e.message)
        }

    }

    fun getShowDialod(): Boolean {
        return showDialog
    }

    fun getCommentAndFavorites(): ArrayList<CommentAndFavorite> {
        return commentAndFavorites
    }

    fun addToCommentAndFavorites(newCommentAndFavorite: CommentAndFavorite) {
        commentAndFavorites.add(newCommentAndFavorite)
    }
}
