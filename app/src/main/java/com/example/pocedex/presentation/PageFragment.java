package com.example.pocedex.presentation;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.pocedex.R;
import com.example.pocedex.data.Pokemon;
import com.example.pocedex.domain.LocalSave;
import com.example.pocedex.domain.Network;
import com.example.pocedex.domain.Utils;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class PageFragment extends Fragment implements PokemonListAdapter.ItemClickListener {

    static final String ARGUMENT_PAGE_NUMBER = "arg_page_number";

    int pageNumber;
    boolean connetion = true;
    List<Pokemon> pokemons = new ArrayList<>();

    PokemonListAdapter adapter = new PokemonListAdapter(this.getActivity(), pokemons);

    static PageFragment newInstance(int page) {
        PageFragment pageFragment = new PageFragment();
        Bundle arguments = new Bundle();
        arguments.putInt(ARGUMENT_PAGE_NUMBER, page);
        pageFragment.setArguments(arguments);
        return pageFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pageNumber = getArguments().getInt(ARGUMENT_PAGE_NUMBER);
        if (pageNumber == 0) {
            UpdatePokemonList(new Network().getPokemonsForList());
        } else {
            UpdateFavList();
        }

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment, null);


        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.list);
        final LinearLayoutManager layoutManager
                = new LinearLayoutManager(this.getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        adapter.setClickListener(this);
        if (pageNumber == 0) {
            recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);
                    if (dy > 0) {
                        if ((layoutManager.getChildCount() + layoutManager.findFirstVisibleItemPosition()) >= layoutManager.getItemCount()) {
                            Log.d("TAG", "End of list");
                            if (Utils.isOnline(getActivity())) {
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
        }

        recyclerView.setAdapter(adapter);

        return view;
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

    private void UpdateFavList() {
        pokemons.clear();
        if (LocalSave.getCommentAndFavorites()==null)
            return;
        for (int i = 0; i < LocalSave.getCommentAndFavorites().size(); i++) {
            if (LocalSave.getCommentAndFavorites().get(i).getIsFav()) {
                Pokemon p = new Pokemon();
                p.setName(LocalSave.getCommentAndFavorites().get(i).getName());
                p.setUrl(LocalSave.getCommentAndFavorites().get(i).getUrl());
                p.setId(LocalSave.getCommentAndFavorites().get(i).getId() - 1);
                pokemons.add(p);
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

    private void showToast(String mes) {
        Toast toast = Toast.makeText(this.getActivity(), mes, Toast.LENGTH_LONG);
        toast.show();
    }
}