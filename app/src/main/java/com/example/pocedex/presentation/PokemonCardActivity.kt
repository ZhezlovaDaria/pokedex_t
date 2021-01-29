package com.example.pocedex.presentation

import android.app.Activity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.pocedex.R
import com.example.pocedex.data.CommentAndFavorite
import com.example.pocedex.data.Pokemon
import com.example.pocedex.databinding.ActivityPokeCardBinding
import com.example.pocedex.domain.Network
import com.example.pocedex.domain.Utils

internal class PokemonCardActivity : AppCompatActivity(), IUpdatePokemon {

    var pokemonlink = ""
    var cardCommentAndFavorite: CommentAndFavorite? = null
    internal var pokemon: Pokemon = Pokemon()
    lateinit var binding: ActivityPokeCardBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (Utils.isOnline(this)) {
            setOnline()
        } else {
            setOffline()
        }

    }

    fun checkOnline(view: View) {
        if (Utils.isOnline(this))
            setOnline()

    }

    private fun setOffline() {
        setContentView(R.layout.offline)
    }

    private fun setOnline() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_poke_card)
        val arguments = intent.extras
        pokemonlink = arguments!!.get("link")!!.toString()
        Network().getPokemon(this, pokemonlink, this)
    }


    override fun refresh(pokemons: List<Pokemon>) {
        setPokemon(pokemons[0])
    }

    fun setPokemon(newPokemon: Pokemon) {
        pokemon = newPokemon

        binding.pokemon = pokemon
        cardCommentAndFavorite = Utils.findOnId(pokemon.getId(), Utils.localSave!!.getCommentAndFavorites())
        if (cardCommentAndFavorite == null) {
            cardCommentAndFavorite = CommentAndFavorite()
            cardCommentAndFavorite!!.setId(pokemon.getId())
            cardCommentAndFavorite!!.setName(pokemon.getName()!!)
            cardCommentAndFavorite!!.setUrl(pokemonlink)
        }
        if (cardCommentAndFavorite!!.getIsFav()) {
            binding.favBtn.setImageResource(android.R.drawable.star_big_on)
        }
        if (cardCommentAndFavorite!!.getComment() != null) {
            binding.UsCom.setText(cardCommentAndFavorite!!.getComment())
        }
        if (pokemon.getSprite(5) == null) {
            binding.sp5.visibility = View.GONE
            binding.sp6.visibility = View.GONE
            binding.sp7.visibility = View.GONE
            binding.sp8.visibility = View.GONE
        }
    }

    override fun repeat() {}

    fun saveComm(view: View) {
        cardCommentAndFavorite!!.setComment(binding.UsCom.text.toString())
        showToast("Your comment save")
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
        cardCommentAndFavorite!!.setIsFav(!cardCommentAndFavorite!!.getIsFav())
        if (cardCommentAndFavorite!!.getIsFav()) {
            binding.favBtn.setImageResource(android.R.drawable.star_big_on)
            showToast("Save in Fav")
        } else {
            binding.favBtn.setImageResource(android.R.drawable.star_big_off)
            showToast("Delete from Fav")
        }
        Utils.save(pokemon, cardCommentAndFavorite!!)
    }

    private fun showToast(mes: String) {
        val toast = Toast.makeText(this, mes, Toast.LENGTH_LONG)
        toast.show()
    }
}