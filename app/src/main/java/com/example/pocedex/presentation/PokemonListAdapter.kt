package com.example.pocedex.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.pocedex.data.Pokemon
import com.example.pocedex.databinding.PokemonItemBinding

internal class PokemonListAdapter(private val pokemons: List<Pokemon>) : RecyclerView.Adapter<PokemonListAdapter.ViewHolder>() {
    private var clickListener: ItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, ViewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = PokemonItemBinding.inflate(inflater, parent, false)
        return ViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val pokemon = pokemons[position]
        holder.binding!!.pokemon = pokemon
    }

    override fun getItemCount(): Int {
        return pokemons.size
    }

    inner class ViewHolder internal constructor(view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {
        internal var binding: PokemonItemBinding? = null

        init {
            binding = DataBindingUtil.bind(view)

            view.setOnClickListener(this)
        }

        override fun onClick(view: View) {
            if (clickListener != null) clickListener!!.onItemClick(view, adapterPosition)
        }
    }

    fun getPokemon(id: Int): Pokemon {
        return pokemons[id]
    }

    fun setClickListener(itemClickListener: ItemClickListener) {
        this.clickListener = itemClickListener
    }

    interface ItemClickListener {
        fun onItemClick(view: View, position: Int)
    }
}

