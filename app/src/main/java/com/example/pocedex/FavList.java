package com.example.pocedex;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class FavList extends AppCompatActivity implements PokDataAdapter.ItemClickListener {

    List<Pokemon> pokemons = new ArrayList<>();
    PokDataAdapter adapter = new PokDataAdapter(this, pokemons);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fav_list);

        for (int i=0; i<PokeWikia.CaFpoke.size();i++)
        {
            if (PokeWikia.CaFpoke.get(i).is_fav) {
                Pokemon p = new Pokemon();
                p.setName(PokeWikia.CaFpoke.get(i).name);
                p.setUrl(PokeWikia.CaFpoke.get(i).link);
                p.setId(PokeWikia.CaFpoke.get(i).id-1);
                pokemons.add(p);
            }
        }

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.flist);
        final LinearLayoutManager layoutManager
                = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        adapter.setClickListener(this);

        recyclerView.setAdapter(adapter);
    }

    public void ToNews(View view)
    {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onItemClick(View view, int position) {
        try{
            String link=(adapter.getPok(position)).getUrl();
            Intent intent = new Intent(this, PokeCard.class);
            intent.putExtra("link", link);
            startActivity(intent);
            finish();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void backToAll(View view)
    {
        finish();
    }
}
