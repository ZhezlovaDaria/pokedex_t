package com.example.pocedex.presentation

import android.app.Activity
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.InsetDrawable
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pocedex.R
import com.example.pocedex.data.CommentAndFavorite
import com.example.pocedex.data.Pokemon
import com.example.pocedex.databinding.ActivityPokemonCardBinding
import com.example.pocedex.domain.Network
import com.example.pocedex.domain.Utils
import com.google.gson.Gson
import java.util.ArrayList

internal class PokemonCardActivity : AppCompatActivity(), IUpdatePokemon {

    private var pokemonlink = ""
    private var cardCommentAndFavorite: CommentAndFavorite? = null
    private var pokemon: Pokemon = Pokemon()
    private lateinit var binding: ActivityPokemonCardBinding
    private var edit: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_pokemon_card)
        val arguments = intent.extras
        val gson = Gson()
        pokemon = gson.fromJson(arguments!!.get("pokemon")!!.toString(), Pokemon::class.java)
        pokemonlink = pokemon.url.toString()
        if (pokemon.getSprite(0) != null)
            setPokemon(pokemon)
        else {
            if (Utils.isOnline(this)) {
                setOnline()
            } else {
                setOffline()
            }
        }

    }

    fun checkOnline(view: View) {
        if (view.id != R.id.tryreconnect)
            return
        if (Utils.isOnline(this))
            setOnline()

    }

    private fun setOffline() {
        setContentView(R.layout.offline)
    }

    private fun setOnline() {
        Network().getPokemon(this, pokemonlink, this)
    }


    override fun refresh(pokemons: List<Pokemon>) {
        setPokemon(pokemons[0])
    }

    private fun setPokemon(newPokemon: Pokemon) {
        pokemon = newPokemon

        binding.pokemon = pokemon
        cardCommentAndFavorite = Utils.findOnId(pokemon.safeId, Utils.localSave!!.getCommentAndFavorites())
        if (cardCommentAndFavorite == null) {
            cardCommentAndFavorite = CommentAndFavorite()
            cardCommentAndFavorite!!.pokemon = pokemon
            cardCommentAndFavorite!!.pokemon.url = pokemonlink
        }
        if (cardCommentAndFavorite!!.is_favorite) {
            binding.favBtn.setImageResource(android.R.drawable.star_big_on)
        }
        if (!cardCommentAndFavorite!!.comment?.isEmpty()!!) {
            binding.UsCom.visibility = View.VISIBLE
            binding.UsCom.setText(cardCommentAndFavorite!!.comment)
        }
    }

    fun showSprite(view: View) {
        val spriteDialog = Dialog(this)
        spriteDialog.setContentView(R.layout.sprite_list)
        val back = ColorDrawable(Color.TRANSPARENT)
        val inset = InsetDrawable(back, 40)
        val recyclerView = spriteDialog.findViewById<RecyclerView>(R.id.list)
        val sprites: ArrayList<String> = ArrayList()
        for (i in 1..8) {
            if (pokemon.getSprite(i) != null)
                sprites.add(pokemon.getSprite(i)!!)
        }
        val adapter = SpriteAdapter(sprites)
        recyclerView.layoutManager = LinearLayoutManager(spriteDialog.context, LinearLayoutManager.HORIZONTAL, false)
        recyclerView.adapter = adapter

        spriteDialog.window!!.setBackgroundDrawable(inset)
        spriteDialog.show()
    }

    fun editComment(view: View) {
        edit = !edit
        if (edit) {
            binding.UsCom.visibility = View.VISIBLE
            binding.UsCom.isEnabled = true
            (view as ImageButton).setImageResource(android.R.drawable.ic_menu_save)
        } else {
            (view as ImageButton).setImageResource(android.R.drawable.ic_menu_edit)
            binding.UsCom.isEnabled = false
            if (binding.UsCom.text.isEmpty())
                binding.UsCom.visibility = View.GONE
            else
                saveComm()
        }
    }

    override fun repeat() {}

    fun saveComm() {
        cardCommentAndFavorite!!.comment = binding.UsCom.text.toString()
        showToast(R.string.save_comment)
        Utils.save(pokemon, cardCommentAndFavorite!!)
        hideKeyboard(this)
    }

    private fun hideKeyboard(activity: Activity) {
        val imm = activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        var view = activity.currentFocus
        if (view == null) {
            view = View(activity)
        }
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    fun saveFav(view: View) {
        if (view.id != R.id.favBtn)
            return
        cardCommentAndFavorite!!.is_favorite = (!cardCommentAndFavorite!!.is_favorite)
        if (cardCommentAndFavorite!!.is_favorite) {
            binding.favBtn.setImageResource(android.R.drawable.star_big_on)
            showToast(R.string.save_to_fav)
        } else {
            binding.favBtn.setImageResource(android.R.drawable.star_big_off)
            showToast(R.string.delete_from_fav)
        }
        Utils.save(pokemon, cardCommentAndFavorite!!)
    }

    private fun showToast(mes: Int) {
        val toast = Toast.makeText(this, mes, Toast.LENGTH_LONG)
        toast.show()
    }
}