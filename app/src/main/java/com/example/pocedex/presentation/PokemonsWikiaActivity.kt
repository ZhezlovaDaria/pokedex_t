package com.example.pocedex.presentation

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.InsetDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.example.pocedex.R
import com.example.pocedex.data.CommentAndFavorite
import com.example.pocedex.data.Pokemon
import com.example.pocedex.databinding.PokemonOfDayBinding
import com.example.pocedex.domain.LocalSave
import com.example.pocedex.domain.Network
import com.example.pocedex.domain.Utils
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import java.util.*

internal class PokemonsWikiaActivity : AppCompatActivity(), IUpdatePokemon {
    private var allFragments: List<Fragment>? = null
    private var listvisible = false
    private var viewPager: ViewPager2? = null
    private var pagerAdapter: FragmentStateAdapter? = null
    private var pokemon: Pokemon? = null
    private var linkRandom: String? = null
    private var pokemonOfDayDialog: Dialog? = null
    private var showPokemonOfDay = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (!Utils.isOnline(this)) {
            setContentView(R.layout.offline)
            return
        }
        Network().resetList()
        setOnline()
        listvisible = true

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

    fun checkOnline(view: View) {
        if (Utils.isOnline(this)) {
            if (!listvisible)
                setOnline()
            else {
                findViewById<View>(R.id.tryreconnect).setVisibility(View.INVISIBLE)
                if (allFragments != null && allFragments!!.size > 0)
                    (allFragments!!.get(0) as PageFragment).updateConnection()
            }
        }
    }

    fun setOffline() {
        findViewById<View>(R.id.tryreconnect).setVisibility(View.VISIBLE)
    }

    private fun setOnline() {
        listvisible = true
        setContentView(R.layout.activity_poce_wikia)

        findViewById<View>(R.id.tryreconnect).setVisibility(View.INVISIBLE)
        Utils.localSave = LocalSave(this)
        commAndFavList()

        viewPager = findViewById(R.id.pager)
        pagerAdapter = ScreenSlidePagerAdapter(this)
        viewPager!!.setAdapter(pagerAdapter)

        val tabLayout = findViewById<TabLayout>(R.id.tab_layout)
        TabLayoutMediator(tabLayout, viewPager!!
        ) { tab, position ->
            if (position == 0)
                tab.setText("All")
            else
                tab.setText("Favorite")
        }.attach()
        showPokemonOfDay = Utils.openShowDialog()
        if (showPokemonOfDay)
            repeat()
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
        val inset = InsetDrawable(back, 40)
        pokemonOfDayDialog!!.getWindow()!!.setBackgroundDrawable(inset)
        pokemonOfDayDialog!!.show()
    }

    fun saveToFavorite(view: View) {
        var commentAndFavorite = Utils.findOnId(pokemon!!.id, Utils.localSave!!.getCommentAndFavorites())
        if (commentAndFavorite == null) {
            commentAndFavorite = CommentAndFavorite(pokemon!!.name, pokemon!!.id,
                    pokemon!!.url, true, null)
        }
        commentAndFavorite!!.is_favorite=true
        Utils.save(pokemon!!, commentAndFavorite!!)
        val toast = Toast.makeText(this, "Save in Fav", Toast.LENGTH_LONG)
        toast.show()
        pokemonOfDayDialog!!.dismiss()
    }

    override fun onStart() {
        super.onStart()
        allFragments = getSupportFragmentManager().getFragments()
        if (allFragments!!.size > 1)
            (allFragments!!.get(1) as PageFragment).updateFavList()
    }

    private fun commAndFavList() {
        Utils.localSave!!.open()
    }

    private inner class ScreenSlidePagerAdapter(fa: FragmentActivity) : FragmentStateAdapter(fa) {

        override fun createFragment(position: Int): Fragment {
            val fragment = PageFragment()
            val args = Bundle()
            args.putInt("arg_page_number", position)
            fragment.setArguments(args)
            return fragment
        }

        override fun getItemCount(): Int {
            return PAGE_COUNT
        }
    }

    companion object {

        val PAGE_COUNT = 2
    }
}