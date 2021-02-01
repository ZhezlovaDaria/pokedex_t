package com.example.pocedex.domain

import android.content.Context
import android.os.Handler
import android.util.Log
import com.example.pocedex.data.Pokemon
import com.example.pocedex.presentation.IUpdatePokemon
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import okhttp3.*
import org.json.JSONObject
import java.io.IOException
import java.util.*

internal class Network {

    private val pokemonsList = "https://pokeapi.co/api/v2/pokemon"
    private var link: String? = null
    private val client = OkHttpClient()
    private var iUpdatePokemon: IUpdatePokemon? = null
    private var handler: Handler = Handler()
    private var pokemons: List<Pokemon> = ArrayList()
    private var json: JSONObject? = null


    companion object {
        private var pokemonsListsNext = "https://pokeapi.co/api/v2/pokemon"
    }

    fun resetList() {
        pokemonsListsNext = pokemonsList
    }

    fun getPokemonsForList(context: Context, callactivity: IUpdatePokemon) {
        link = pokemonsListsNext
        iUpdatePokemon = callactivity
        handler = Handler(context.mainLooper)
        if (link == null || link == "null")
            return
        val request = Request.Builder()
                .url(link!!)
                .get()
                .build()
        client.newCall(request).enqueue(object : Callback {

            override fun onFailure(request: Call, e: IOException) {
                e.printStackTrace()
            }

            @Throws(IOException::class)
            override fun onResponse(call: Call, response: Response) {
                val answer = response.body!!.string()
                if (response.isSuccessful) {
                    try {
                        json = JSONObject(answer)
                        val s = json!!.toString()
                        val result = s.substring(s.indexOf('['), s.indexOf(']') + 1)
                        pokemonsListsNext = json!!.get("next").toString()
                        val builder = GsonBuilder()
                        val gson = builder.create()
                        val pokemonType = object : TypeToken<ArrayList<Pokemon>>() {

                        }.type
                        pokemons = gson.fromJson(result, pokemonType)

                    } catch (e: Exception) {
                        Log.d("Exe", e.message)
                    }

                    handler.post { iUpdatePokemon!!.refresh(pokemons) }
                }
            }
        })
    }

    fun getPokemon(context: Context, url: String, callactivity: IUpdatePokemon) {
        link = url
        handler = Handler(context.mainLooper)
        iUpdatePokemon = callactivity
        if (link == null || link == "null")
            return
        val request = Request.Builder()
                .url(link!!)
                .get()
                .build()
        client.newCall(request).enqueue(object : Callback {

            override fun onFailure(request: Call, e: IOException) {
                e.printStackTrace()
            }

            @Throws(IOException::class)
            override fun onResponse(call: Call, response: Response) {
                val answer = response.body!!.string()
                if (response.isSuccessful) {
                    try {
                        val builder = GsonBuilder()
                        val gson = builder.create()
                        val pokemon = gson.fromJson(answer, Pokemon::class.java)
                        pokemon.url=link
                        val pokemons = ArrayList<Pokemon>()
                        pokemons.add(pokemon)
                        handler.post { iUpdatePokemon!!.refresh(pokemons) }
                    } catch (e: Exception) {
                        Log.d("Exe", e.message)
                    }

                } else {
                    try {
                        handler.post { iUpdatePokemon!!.repeat() }
                    } catch (e: Exception) {
                        Log.d("Exe", e.message)
                    }

                }
            }
        })
    }

    fun getPokemonOfDay(context: Context, callactivity: IUpdatePokemon) {
        var linkRandom = ""
        val request = Request.Builder()
                .url(pokemonsList)
                .get()
                .build()
        client.newCall(request).enqueue(object : Callback {

            override fun onFailure(request: Call, e: IOException) {
                e.printStackTrace()
            }

            @Throws(IOException::class)
            override fun onResponse(call: Call, response: Response) {
                val answer = response.body!!.string()
                if (response.isSuccessful) {
                    try {
                        json = JSONObject(answer)
                        val count: Int = json!!.get("count").toString().toInt()

                        val randomNumber: Int = Utils.randomNumbers(count)
                        if (randomNumber != -1) {
                            linkRandom = "https://pokeapi.co/api/v2/pokemon/" + randomNumber + "/"
                            getPokemon(context, linkRandom, callactivity)
                        }
                    } catch (e: Exception) {
                        Log.d("Exe", e.message)
                    }
                }
            }
        })
    }
}