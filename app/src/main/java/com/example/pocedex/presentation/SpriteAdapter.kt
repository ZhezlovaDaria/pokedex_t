package com.example.pocedex.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.pocedex.databinding.ShowSpriteBinding

internal class SpriteAdapter(private val sprites: List<String>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var clickListener: ItemClickListener? = null


    override fun onCreateViewHolder(parent: ViewGroup, ViewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ShowSpriteBinding.inflate(inflater, parent, false)
        return ViewHolder(binding.root)

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val sprite = sprites[position]
        val viewHolder: ViewHolder = holder as ViewHolder
        viewHolder.binding!!.sprite = sprite
    }

    override fun getItemCount(): Int {
        return sprites.size
    }

    inner class ViewHolder internal constructor(view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {
        var binding: ShowSpriteBinding? = null

        init {
            binding = DataBindingUtil.bind(view)

            view.setOnClickListener(this)
        }

        override fun onClick(view: View) {
            if (clickListener != null) clickListener!!.onItemClick(view, adapterPosition)
        }
    }

    fun getSprite(id: Int): String {
        return sprites[id]
    }

    fun setClickListener(itemClickListener: ItemClickListener) {
        this.clickListener = itemClickListener
    }

    interface ItemClickListener {
        fun onItemClick(view: View, position: Int)
    }
}