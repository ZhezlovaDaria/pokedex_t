package com.example.pocedex;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;

import com.example.pocedex.databinding.ListItemBinding;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

class PokDataAdapter extends RecyclerView.Adapter<PokDataAdapter.ViewHolder> {

    private List<Pokemon> pokemons;
    private ItemClickListener mClickListener;


    PokDataAdapter(Context context, List<Pokemon> pokemons) {
        this.pokemons = pokemons;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int ViewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ListItemBinding binding = ListItemBinding.inflate(inflater, parent, false);
        return new ViewHolder(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(PokDataAdapter.ViewHolder holder, int position) {
        Pokemon pokemon = pokemons.get(position);
        holder.binding.setPokemon(pokemon);
    }

    @Override
    public int getItemCount() {
        return pokemons.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ListItemBinding binding;

        ViewHolder(View view) {
            super(view);
            binding = DataBindingUtil.bind(view);

            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    public Pokemon getPokemon(int id) {
        return pokemons.get(id);
    }

    public void setClickListener(PokDataAdapter.ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}

