package com.example.pocedex.presentation

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pocedex.R
import com.example.pocedex.data.Pokemon
import com.example.pocedex.domain.Network
import com.example.pocedex.domain.Utils
import com.google.gson.Gson
import java.util.*


internal class PageFragment : Fragment(), PokemonListAdapter.ItemClickListener, IUpdatePokemon {

    private var pageNumber: Int = 0
    private var connetion = true
    private var isLoading = false
    private val pokemons = ArrayList<Pokemon>()
    private var adapter: PokemonListAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        pageNumber = arguments!!.getInt(ARGUMENT_PAGE_NUMBER)
        if (pageNumber == 1) {
            updateFavList()
        }
        adapter = PokemonListAdapter(this.pokemons)

    }

    companion object {
        private const val ARGUMENT_PAGE_NUMBER = "arg_page_number"

        fun newInstance(page: Int): PageFragment {
            val pageFragment = PageFragment()
            val arguments = Bundle()
            arguments.putInt(ARGUMENT_PAGE_NUMBER, page)
            pageFragment.arguments = arguments
            return pageFragment
        }
    }

    override fun refresh(pokemons: List<Pokemon>) {
        updatePokemonList(pokemons)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.pokemon_list, container, false)


        val recyclerView = view.findViewById<RecyclerView>(R.id.rv_pokemons_list)

        val layoutManager: LinearLayoutManager = if (pageNumber == 0) {
            LinearLayoutManager(this.activity, LinearLayoutManager.VERTICAL, false)
        } else {
            LinearLayoutManager(this.activity, LinearLayoutManager.HORIZONTAL, false)
        }

        recyclerView.layoutManager = layoutManager
        adapter?.setClickListener(this)
        if (pageNumber == 0) {
            recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    if (!isLoading) {
                        isLoading = true
                        if (Utils.isConnected) {
                            Network().getPokemonsForList(activity!!, this@PageFragment)
                        } else {
                            if (connetion) {
                                showToast()
                                connetion = false
                                isLoading = false
                            }
                        }
                    }
                }
            })
        }
        recyclerView.adapter = adapter

        return view
    }

    fun updateConnection() {
        Network().getPokemonsForList(this.activity!!, this)
        isLoading = false
        connetion = true
    }

    override fun onItemClick(view: View, position: Int) {
        try {
            val pokemon: Pokemon = adapter?.getPokemon(position)!!
            val gson = Gson()
            val jsonString = gson.toJson(pokemon)
            val intent = Intent(this.activity, PokemonCardActivity::class.java)
            intent.putExtra("pokemon", jsonString)
            startActivity(intent)
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }


    fun updateFavList() {
        pokemons.clear()
        val commentAndFavorite = Utils.localSave!!.getCommentAndFavorites()
        if (commentAndFavorite.isEmpty())
            return
        val count = commentAndFavorite.size
        for (i in 0 until count) {
            if (commentAndFavorite[i].is_favorite) {
                val p = commentAndFavorite[i].pokemon
                pokemons.add(p)
            }
        }
        adapter?.notifyDataSetChanged()
    }

    private fun updatePokemonList(p: List<Pokemon>) {
        if (p.isNotEmpty()) {
            pokemons.addAll(p)
            adapter?.notifyDataSetChanged()
            isLoading = false
        }
    }

    private fun showToast() {
        val toast = Toast.makeText(this.activity, R.string.noincon, Toast.LENGTH_LONG)
        toast.show()
    }

    override fun repeat() {}
}