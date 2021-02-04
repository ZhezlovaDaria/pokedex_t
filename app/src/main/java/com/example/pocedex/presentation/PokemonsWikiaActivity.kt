package com.example.pocedex.presentation

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.InsetDrawable
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pocedex.R
import com.example.pocedex.data.CommentAndFavorite
import com.example.pocedex.data.Pokemon
import com.example.pocedex.databinding.PokemonOfDayBinding
import com.example.pocedex.domain.LocalSave
import com.example.pocedex.domain.Network
import com.example.pocedex.domain.Utils
import java.util.*


internal class PokemonsWikiaActivity : AppCompatActivity(), IUpdatePokemon, INetworkChange {
    private var listvisible = false
    private var pokemon: Pokemon? = null
    private var pokemonOfDayDialog: Dialog? = null
    private var showPokemonOfDay = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pokemons_wikia)
        Utils.localSave = LocalSave(this)
        commAndFavList()
        val ft: FragmentTransaction = supportFragmentManager.beginTransaction()
        ft.add(R.id.llay_all_list, PageFragment.newInstance(0))
        ft.add(R.id.llay_favorite_list, PageFragment.newInstance(1))
        ft.commit()
        Utils.startNetworkCallback(this)
        if (!Utils.isConnected) {
            findViewById<LinearLayout>(R.id.inc_offline).visibility = View.VISIBLE
            return
        }
        Network().resetList()
        setOnline()
        listvisible = true

    }

    override fun onStop() {
        super.onStop()
        Utils.stopNetworkCallback(this)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = getMenuInflater()
        inflater.inflate(R.menu.menu_main, menu)
        menu.getItem(0).setChecked(showPokemonOfDay)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.getItemId() == R.id.pokemonofdaycheck) {
            if (item.isChecked()) {
                item.setChecked(false)
                showPokemonOfDay = false
            } else {
                item.setChecked(true)
                showPokemonOfDay = true
            }
            Utils.saveShowDialog(showPokemonOfDay)
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun setOnline() {
        if (!listvisible) {
            listvisible = true
            findViewById<LinearLayout>(R.id.inc_offline).visibility = View.GONE

            showPokemonOfDay = Utils.openShowDialog()
            if (showPokemonOfDay)
                repeat()
        } else {
            if (supportFragmentManager.fragments.size > 0)
                (supportFragmentManager.fragments[0] as PageFragment).updateConnection()
        }
    }

    override fun refresh(pokemons: List<Pokemon>) {
        setPokemonOfDay(pokemons.get(0))
    }

    override fun repeat() {
        Network().getPokemonOfDay(this, this)
    }

    fun setPokemonOfDay(newpokemon: Pokemon) {
        pokemon = newpokemon
        pokemonOfDayDialog = Dialog(this)
        val binding = PokemonOfDayBinding.inflate(LayoutInflater.from(this))
        binding.setPokemon(pokemon)
        pokemonOfDayDialog!!.setContentView(binding.getRoot())
        val back = ColorDrawable(Color.TRANSPARENT)
        val inset = InsetDrawable(back, 0)

        val recyclerView: RecyclerView = binding.rvPokemonsList
        val sprites: ArrayList<String> = ArrayList()
        for (i in 0..8) {
            if (pokemon!!.getSprite(i) != null)
                sprites.add(pokemon!!.getSprite(i)!!)
        }
        val adapter = SpriteAdapter(sprites)
        recyclerView.layoutManager = LinearLayoutManager(pokemonOfDayDialog!!.context, LinearLayoutManager.HORIZONTAL, false)
        recyclerView.adapter = adapter


        pokemonOfDayDialog!!.getWindow()!!.setBackgroundDrawable(inset)
        pokemonOfDayDialog!!.show()
    }

    fun saveToFavorite(view: View) {
        if (view.id != R.id.btn_save_favotire)
            return
        var commentAndFavorite = Utils.findOnId(pokemon!!.id, Utils.localSave!!.getCommentAndFavorites())
        if (commentAndFavorite == null) {
            commentAndFavorite = CommentAndFavorite(pokemon!!, true, "")
        }
        commentAndFavorite.is_favorite = true
        Utils.save(pokemon!!, commentAndFavorite)
        val toast = Toast.makeText(this, R.string.save_to_fav, Toast.LENGTH_LONG)
        toast.show()
        (supportFragmentManager.fragments[1] as PageFragment).updateFavList()
        pokemonOfDayDialog!!.dismiss()
    }

    override fun onStart() {
        super.onStart()
        Utils.startNetworkCallback(this)
        if (supportFragmentManager.fragments.size > 1)
            (supportFragmentManager.fragments[1] as PageFragment).updateFavList()
    }

    private fun commAndFavList() {
        Utils.localSave!!.open()
    }
}