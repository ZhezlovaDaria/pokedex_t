package com.example.pocedex.presentation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TabHost;
import android.widget.Toast;

import com.example.pocedex.R;
import com.example.pocedex.data.CommAndFav;
import com.example.pocedex.data.Network;
import com.example.pocedex.data.Pokemon;
import com.example.pocedex.domain.Utils;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class PokeWikia extends AppCompatActivity implements PokemonListAdapter.ItemClickListener {


    SharedPreferences mPrefs;
    List<Pokemon> pokemons = new ArrayList<>();
    List<Pokemon> favpokemons = new ArrayList<>();
    public static ArrayList<CommAndFav> CaFpoke = new ArrayList<>();
    PokemonListAdapter adapter = new PokemonListAdapter(this, pokemons);
    PokemonListAdapter favadapter = new PokemonListAdapter(this, favpokemons);
    boolean connetion = true;
    TabHost tabs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!Utils.isOnline(this)) {
            setContentView(R.layout.offline);
            return;
        }
        new Network().resetList();
        setContentView(R.layout.activity_poce_wikia);
        mPrefs = getSharedPreferences(Utils.APP_PREFERENCES, Context.MODE_PRIVATE);
        UpdatePokemonList(new Network().getPokemonsForList());
        commAndFavList();
        UpdateFavList();

        tabs = (TabHost) this.findViewById(R.id.tabhost);
        tabs.setup();

        tabs.addTab(tabs.newTabSpec("All").setContent(R.id.list).setIndicator("All pokemons"));
        tabs.addTab(tabs.newTabSpec("Fav").setContent(R.id.list2).setIndicator("Favorite"));

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.list);
        final LinearLayoutManager layoutManager
                = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        adapter.setClickListener(this);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0) {
                    if ((layoutManager.getChildCount() + layoutManager.findFirstVisibleItemPosition()) >= layoutManager.getItemCount()) {
                        Log.d("TAG", "End of list");
                        if (Utils.isOnline(PokeWikia.this)) {
                            if (!UpdatePokemonList(new Network().getPokemonsForList()))
                                showToast("End of list");
                            connetion = true;
                        } else {
                            if (connetion) {
                                showToast("No internet connection");
                                connetion = false;
                            }
                        }
                    }
                }
            }
        });
        recyclerView.setAdapter(adapter);

        RecyclerView recyclerView1 = (RecyclerView) findViewById(R.id.list2);
        final LinearLayoutManager layoutManager1
                = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView1.setLayoutManager(layoutManager1);
        favadapter.setClickListener(this);

        recyclerView1.setAdapter(favadapter);

    }

    private void showToast(String mes) {
        Toast toast = Toast.makeText(this, mes, Toast.LENGTH_LONG);
        toast.show();
    }

    @Override
    protected void onStart() {
        super.onStart();
        UpdateFavList();
        favadapter.notifyDataSetChanged();
    }

    public void toNews(View view) {
        finish();
    }

    @Override
    public void onItemClick(View view, int position) {
        try {
            String link;
            if (tabs.getCurrentTab() == 0)
                link = (adapter.getPokemon(position)).getUrl();
            else link = (favadapter.getPokemon(position)).getUrl();
            Intent intent = new Intent(this, PokeCard.class);
            intent.putExtra("link", link);
            startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void commAndFavList() {
        try {
            CaFpoke.clear();
            Gson gson = new Gson();
            String json = mPrefs.getString("commandfav2", "");
            CommAndFav[] caf;
            caf = gson.fromJson(json, CommAndFav[].class);
            for (int i = 0; i < caf.length; i++) {
                CaFpoke.add(caf[i]);
            }
        } catch (Exception e) {
            Log.d("prefs", e.getMessage());
        }
    }

    private void UpdateFavList() {
        favpokemons.clear();
        for (int i = 0; i < CaFpoke.size(); i++) {
            if (CaFpoke.get(i).getIsFav()) {
                Pokemon p = new Pokemon();
                p.setName(CaFpoke.get(i).getName());
                p.setUrl(CaFpoke.get(i).getUrl());
                p.setId(CaFpoke.get(i).getId() - 1);
                favpokemons.add(p);
            }
        }
    }

    private boolean UpdatePokemonList(List<Pokemon> p) {
        if (p.isEmpty())
            return false;
        else {
            for (int i = 0; i < p.size(); i++) {
                pokemons.add(p.get(i));
            }
            adapter.notifyDataSetChanged();
            return true;
        }
    }
}