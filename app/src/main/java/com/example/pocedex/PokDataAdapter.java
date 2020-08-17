package com.example.pocedex;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

class PokDataAdapter extends RecyclerView.Adapter<PokDataAdapter.ViewHolder> {

    private List<Pokemon> pokemons;

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
    }

    @Override
    public int getItemCount() {
        return pokemons.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        final TextView nameView;
        ViewHolder(View view){
            super(view);
            nameView = (TextView) view.findViewById(R.id.name);
        }
    }
}