package com.example.pocedex.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.pocedex.data.Pokemon
import com.example.pocedex.databinding.FavoriteGallaryBinding
import com.example.pocedex.databinding.PokemonItemBinding

internal class PokemonListAdapter(private val pokemons: List<Pokemon>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var clickListener: ItemClickListener? = null

    private val all = 0
    private val favorite = 1

    override fun getItemViewType(position: Int): Int {
        return if (getPokemon(position).getSprite(0)==null) all else favorite
    }

    override fun onCreateViewHolder(parent: ViewGroup, ViewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return if (ViewType == all) {
            val binding = PokemonItemBinding.inflate(inflater, parent, false)
            ViewHolderAll(binding.root)
        } else {
            val binding = FavoriteGallaryBinding.inflate(inflater, parent, false)
            ViewHolderFavorite(binding.root)
        }

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val pokemon = pokemons[position]
        if (holder.itemViewType == all) {
            val viewHolderAll: ViewHolderAll = holder as ViewHolderAll
            viewHolderAll.binding!!.pokemon = pokemon
        } else {
            val viewHolderFavorite: ViewHolderFavorite = holder as ViewHolderFavorite
            viewHolderFavorite.binding!!.pokemon = pokemon
        }
    }

    override fun getItemCount(): Int {
        return pokemons.size
    }

    inner class ViewHolderAll internal constructor(view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {
        var binding: PokemonItemBinding? = null

        init {
            binding = DataBindingUtil.bind(view)

            view.setOnClickListener(this)
        }

        override fun onClick(view: View) {
            if (clickListener != null) clickListener!!.onItemClick(view, adapterPosition)
        }
    }

    inner class ViewHolderFavorite internal constructor(view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {
        var binding: FavoriteGallaryBinding? = null

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

