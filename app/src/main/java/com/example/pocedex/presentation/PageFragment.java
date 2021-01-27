package com.example.pocedex.presentation;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.pocedex.R;
import com.example.pocedex.data.CommentAndFavorite;
import com.example.pocedex.data.Pokemon;
import com.example.pocedex.domain.Network;
import com.example.pocedex.domain.Utils;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class PageFragment extends Fragment implements PokemonListAdapter.ItemClickListener, IUpdatePokemon {

    private static final String ARGUMENT_PAGE_NUMBER = "arg_page_number";

    private int pageNumber;
    private boolean connetion = true;
    private boolean isLoading = false;
    private List<Pokemon> pokemons = new ArrayList<>();

    PokemonListAdapter adapter = new PokemonListAdapter(this.getActivity(), this.pokemons);

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pageNumber = getArguments().getInt(ARGUMENT_PAGE_NUMBER);
        if (pageNumber == 0) {
            new Network().getPokemonsForList(this.getActivity(), this);
        } else {
            updateFavList();
        }

    }

    @Override
    public void refresh(List<Pokemon> pokemons) {
        updatePokemonList(pokemons);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment, container, false);


        RecyclerView recyclerView = view.findViewById(R.id.list);
        final LinearLayoutManager layoutManager
                = new LinearLayoutManager(this.getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        adapter.setClickListener(this);
        if (pageNumber == 0) {
            recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);
                    if (!isLoading) {
                        isLoading = true;
                        if (Utils.isOnline(getActivity())) {
                            new Network().getPokemonsForList(getActivity(), PageFragment.this);
                        } else {
                            if (connetion) {
                                showToast("No internet connection");
                                connetion = false;
                                ((PokemonsWikiaActivity) getActivity()).setOffline();
                            }
                        }
                    }
                }
            });
        }

        recyclerView.setAdapter(adapter);

        return view;
    }

    public void updateConnection() {
        isLoading = false;
        connetion = true;
    }

    @Override
    public void onItemClick(View view, int position) {
        try {
            String link;
            link = (adapter.getPokemon(position)).getUrl();
            Intent intent = new Intent(this.getActivity(), PokemonCardActivity.class);
            intent.putExtra("link", link);
            startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    void updateFavList() {
        pokemons.clear();
        ArrayList<CommentAndFavorite> commentAndFavorite = Utils.getLocalSave().getCommentAndFavorites();
        if (commentAndFavorite == null)
            return;
        int count = commentAndFavorite.size();
        for (int i = 0; i < count; i++) {
            if (commentAndFavorite.get(i).getIsFav()) {
                Pokemon p = new Pokemon();
                p.setName(commentAndFavorite.get(i).getName());
                p.setUrl(commentAndFavorite.get(i).getUrl());
                p.setId(commentAndFavorite.get(i).getId() - 1);
                pokemons.add(p);
            }
        }
        adapter.notifyDataSetChanged();
    }

    void updatePokemonList(List<Pokemon> p) {
        if (!p.isEmpty()) {
            pokemons.addAll(p);
            adapter.notifyDataSetChanged();
            isLoading = false;
        }
    }

    private void showToast(String mes) {
        Toast toast = Toast.makeText(this.getActivity(), mes, Toast.LENGTH_LONG);
        toast.show();
    }
}