package com.example.pocedex.presentation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TabHost;

import com.example.pocedex.R;
import com.example.pocedex.domain.LocalSave;
import com.example.pocedex.domain.Network;
import com.example.pocedex.data.Pokemon;
import com.example.pocedex.domain.Utils;

import java.util.ArrayList;
import java.util.List;

public class PokemonsWikiaActivity extends AppCompatActivity implements PokemonListAdapter.ItemClickListener {


    List<Pokemon> pokemons = new ArrayList<>();
    List<Pokemon> favpokemons = new ArrayList<>();
    PokemonListAdapter adapter = new PokemonListAdapter(this, pokemons);
    PokemonListAdapter favadapter = new PokemonListAdapter(this, favpokemons);
    TabHost tabs;
    ViewPager pager;
    PagerAdapter pagerAdapter;
    static final int PAGE_COUNT = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!Utils.isOnline(this)) {
            setContentView(R.layout.offline);
            return;
        }
        new Network().resetList();
        setContentView(R.layout.activity_poce_wikia);
        LocalSave.setSavePreferences( getSharedPreferences(Utils.getPreferenses(), Context.MODE_PRIVATE));
        commAndFavList();

        pager = (ViewPager) findViewById(R.id.pager);
        pagerAdapter = new WikiaFragmentPagerAdapter(getSupportFragmentManager());
        pager.setAdapter(pagerAdapter);


        pager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                Log.d("", "position = " + position);
            }

            @Override
            public void onPageScrolled(int position, float positionOffset,
                                       int positionOffsetPixels) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

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
            Intent intent = new Intent(this, PokemonCardActivity.class);
            intent.putExtra("link", link);
            startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void commAndFavList() {
        LocalSave.open();
    }

    private void UpdateFavList() {
        favpokemons.clear();
        if (LocalSave.getCommentAndFavorites()==null)
            return;
        for (int i = 0; i < LocalSave.getCommentAndFavorites().size(); i++) {
            if (LocalSave.getCommentAndFavorites().get(i).getIsFav()) {
                Pokemon p = new Pokemon();
                p.setName(LocalSave.getCommentAndFavorites().get(i).getName());
                p.setUrl(LocalSave.getCommentAndFavorites().get(i).getUrl());
                p.setId(LocalSave.getCommentAndFavorites().get(i).getId() - 1);
                favpokemons.add(p);
            }
        }
    }

    private class WikiaFragmentPagerAdapter extends FragmentPagerAdapter {

        public WikiaFragmentPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return PageFragment.newInstance(position);
        }

        @Override
        public int getCount() {
            return PAGE_COUNT;
        }

        public CharSequence getPageTitle(int position) {
            if (position == 0)
                return "All";
            else return "Favorite";
        }

    }
}