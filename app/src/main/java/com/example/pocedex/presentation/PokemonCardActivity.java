package com.example.pocedex.presentation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.example.pocedex.R;
import com.example.pocedex.data.CommentAndFavorite;
import com.example.pocedex.domain.LocalSave;
import com.example.pocedex.domain.Network;
import com.example.pocedex.data.Pokemon;
import com.example.pocedex.databinding.ActivityPokeCardBinding;
import com.example.pocedex.domain.Utils;

import java.util.ArrayList;
import java.util.List;

public class PokemonCardActivity extends AppCompatActivity implements IUpdatePokemon {

    private static String pokemonlink = "";
    CommentAndFavorite cardCommentAndFavorite;
    Pokemon pokemon;
    ActivityPokeCardBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!Utils.isOnline(this)) {
            setContentView(R.layout.offline);
            return;
        }
        setContentView(R.layout.activity_poke_card);
        Bundle arguments = getIntent().getExtras();
        pokemonlink = arguments.get("link").toString();
        new Network().getPokemon(this, pokemonlink, this);
    }

    @Override
    public void refresh(List<Pokemon> pokemons)
    {
        setPokemon(pokemons.get(0));
    }

    public void setPokemon(Pokemon newPokemon)
    {
        pokemon=newPokemon;
        binding = DataBindingUtil.setContentView(this, R.layout.activity_poke_card);

        binding.setPokemon(pokemon);
        cardCommentAndFavorite = findOnId(pokemon.getId(), LocalSave.getCommentAndFavorites());
        if (cardCommentAndFavorite == null) {
            cardCommentAndFavorite = new CommentAndFavorite();
            cardCommentAndFavorite.setId(pokemon.getId());
            cardCommentAndFavorite.setName(pokemon.getName());
            cardCommentAndFavorite.setUrl(pokemonlink);
        }
        if (cardCommentAndFavorite.getIsFav()) {
            binding.favBtn.setImageResource(android.R.drawable.star_big_on);
        }
        if (cardCommentAndFavorite.getComment() != null) {
            binding.UsCom.setText(cardCommentAndFavorite.getComment());
        }
        if (pokemon.getSprite(5) == null) {
            binding.sp5.setVisibility(View.GONE);
            binding.sp6.setVisibility(View.GONE);
            binding.sp7.setVisibility(View.GONE);
            binding.sp8.setVisibility(View.GONE);
        }
    }

    public CommentAndFavorite findOnId(
            int id, ArrayList<CommentAndFavorite> caf) {
        try {
            for (CommentAndFavorite c : caf) {
                if (c.equals(id)) {
                    return c;
                }
            }
        } catch (Exception e) {
            Log.d("", e.getMessage());
        }
        return null;
    }

    public void saveComm(View view) {
        cardCommentAndFavorite.setComment(binding.UsCom.getText().toString());
        //cardCommentAndFavorite.setComment(((EditText) findViewById(R.id.UsCom)).getText().toString());
        showToast("Your comment save");
        save();
        hideKeyboard(this);
    }

    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        View view = activity.getCurrentFocus();
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public void saveFav(View view) {
        cardCommentAndFavorite.setIsFav(!cardCommentAndFavorite.getIsFav());
        if (cardCommentAndFavorite.getIsFav()) {
            binding.favBtn.setImageResource(android.R.drawable.star_big_on);
            showToast("Save in Fav");
        } else {
            binding.favBtn.setImageResource(android.R.drawable.star_big_off);
            showToast("Delete from Fav");
        }
        save();
    }

    private void save() {
        if (findOnId(pokemon.getId(), LocalSave.getCommentAndFavorites()) == null) {
            LocalSave.addToCommentAndFavorites(cardCommentAndFavorite);
        }
        try {
            LocalSave.save();
        } catch (Exception e) {
            Log.d("comfavsave", e.getMessage());
        }
    }

    private void showToast(String mes) {
        Toast toast = Toast.makeText(this, mes, Toast.LENGTH_LONG);
        toast.show();
    }
}
