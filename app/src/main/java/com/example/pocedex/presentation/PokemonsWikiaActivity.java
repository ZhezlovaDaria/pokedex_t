package com.example.pocedex.presentation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.InsetDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.pocedex.R;
import com.example.pocedex.data.CommentAndFavorite;
import com.example.pocedex.data.Pokemon;
import com.example.pocedex.databinding.PokemonOfDayBinding;
import com.example.pocedex.domain.LocalSave;
import com.example.pocedex.domain.Network;
import com.example.pocedex.domain.Utils;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.List;
import java.util.Random;

public class PokemonsWikiaActivity extends AppCompatActivity implements IUpdatePokemon {

    static final int PAGE_COUNT = 2;
    List<Fragment> allFragments;
    boolean listvisible = false;
    private ViewPager2 viewPager;
    private FragmentStateAdapter pagerAdapter;
    final Random random = new Random();
    private Pokemon pokemon;
    private String linkRandom;
    private Dialog pokemonOfDayDialog;
    private int fullCount = 1118; //это число есть в списке покемонов, но оно не успевает подгрузиться перед вызовом рандомайзера...
    private boolean showPokemonOfDay = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!Utils.isOnline(this)) {
            setContentView(R.layout.offline);
            return;
        }
        new Network().resetList();
        setOnline();
        listvisible = true;

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        menu.getItem(0).setChecked(showPokemonOfDay);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.pokemonofdaycheck) {
            if (item.isChecked()) {
                item.setChecked(false);
                showPokemonOfDay = false;
            } else {
                item.setChecked(true);
                showPokemonOfDay = true;
            }
            Utils.saveShowDialog(showPokemonOfDay);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void checkOnline(View view) {
        if (Utils.isOnline(this)) {
            if (!listvisible)
                setOnline();
            else {
                findViewById(R.id.tryreconnect).setVisibility(View.INVISIBLE);
                if (allFragments != null && allFragments.size() > 0)
                    ((PageFragment) allFragments.get(0)).updateConnection();
            }
        }
    }

    public void setOffline() {
        findViewById(R.id.tryreconnect).setVisibility(View.VISIBLE);
    }

    private void setOnline() {
        listvisible = true;
        setContentView(R.layout.activity_poce_wikia);

        findViewById(R.id.tryreconnect).setVisibility(View.INVISIBLE);
        Utils.setLocalSave(new LocalSave(this));
        commAndFavList();

        viewPager = findViewById(R.id.pager);
        pagerAdapter = new ScreenSlidePagerAdapter(this);
        viewPager.setAdapter(pagerAdapter);

        TabLayout tabLayout = findViewById(R.id.tab_layout);
        new TabLayoutMediator(tabLayout, viewPager,
                (tab, position) -> {
                    if (position == 0)
                        tab.setText("All");
                    else tab.setText("Favorite");
                }
        ).attach();
        showPokemonOfDay = Utils.openShowDialog();
        if (showPokemonOfDay)
            repeat();
    }

    @Override
    public void refresh(List<Pokemon> pokemons) {
        setPokemonOfDay(pokemons.get(0));
    }

    @Override
    public void repeat() {
        List<Integer> notRandom = Utils.getNotRandomNumbers();
        int randomNumber = random.nextInt(fullCount) + 1;
        while (notRandom.contains(randomNumber))
            randomNumber = random.nextInt(fullCount) + 1;
        linkRandom = "https://pokeapi.co/api/v2/pokemon/" + randomNumber + "/";
        Log.d("link", linkRandom);
        new Network().getPokemon(this, linkRandom, this);
    }

    public void setPokemonOfDay(Pokemon newpokemon) {
        pokemon = newpokemon;
        pokemonOfDayDialog = new Dialog(this);
        PokemonOfDayBinding binding = PokemonOfDayBinding.inflate(LayoutInflater.from(this));
        binding.setPokemon(pokemon);
        pokemonOfDayDialog.setContentView(binding.getRoot());
        ColorDrawable back = new ColorDrawable(Color.TRANSPARENT);
        InsetDrawable inset = new InsetDrawable(back, 40);
        pokemonOfDayDialog.getWindow().setBackgroundDrawable(inset);
        pokemonOfDayDialog.show();
    }

    public void saveToFavorite(View view) {
        CommentAndFavorite commentAndFavorite = Utils.findOnId(pokemon.getId(), Utils.getLocalSave().getCommentAndFavorites());
        if (commentAndFavorite == null) {
            commentAndFavorite = new CommentAndFavorite(pokemon.getName(), pokemon.getId(),
                    linkRandom, true, null);
        }
        commentAndFavorite.setIsFav(true);
        Utils.save(pokemon, commentAndFavorite);
        Toast toast = Toast.makeText(this, "Save in Fav", Toast.LENGTH_LONG);
        toast.show();
        pokemonOfDayDialog.dismiss();
    }

    @Override
    protected void onStart() {
        super.onStart();
        allFragments = getSupportFragmentManager().getFragments();
        if (allFragments.size() > 1)
            ((PageFragment) allFragments.get(1)).updateFavList();
    }

    private void commAndFavList() {
        Utils.getLocalSave().open();
    }

    private class ScreenSlidePagerAdapter extends FragmentStateAdapter {
        public ScreenSlidePagerAdapter(FragmentActivity fa) {
            super(fa);
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            Fragment fragment = new PageFragment();
            Bundle args = new Bundle();
            args.putInt("arg_page_number", position);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public int getItemCount() {
            return PAGE_COUNT;
        }
    }
}