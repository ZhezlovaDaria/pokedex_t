package com.example.pocedex;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

class PokDataAdapter extends RecyclerView.Adapter<PokDataAdapter.ViewHolder> {

    private List<Pokemon> pokemons;
    private ItemClickListener mClickListener;


    PokDataAdapter(Context context, List<Pokemon> pokemons) {
        this.pokemons = pokemons;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int ViewType)
    {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item,parent,false);
        return  new ViewHolder(v);
    }
    @Override
    public void onBindViewHolder(PokDataAdapter.ViewHolder holder, int position) {
        Pokemon pokemon = pokemons.get(position);
        holder.nameView.setText(pokemon.getName());
        Picasso.get().load("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/"+(pokemon.id+1)+".png").into(holder.imageView);
        //holder.imageView.setImageBitmap(pokemon.perview);
    }

    @Override
    public int getItemCount() {
        return pokemons.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        final TextView nameView;
        final ImageView imageView;
        ViewHolder(View view){
            super(view);
            nameView = (TextView) view.findViewById(R.id.name);
            imageView = (ImageView) view.findViewById(R.id.imageView);

            view.setOnClickListener(this);
        }
        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    public Pokemon getPok(int id) {
        return pokemons.get(id);
    }

    public void setClickListener(PokDataAdapter.ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}

