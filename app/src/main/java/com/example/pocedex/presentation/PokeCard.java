package com.example.pocedex.presentation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.pocedex.R;
import com.example.pocedex.data.CommAndFav;
import com.example.pocedex.domain.Network;
import com.example.pocedex.data.Pokemon;
import com.example.pocedex.databinding.ActivityPokeCardBinding;
import com.example.pocedex.domain.Utils;
import com.google.gson.Gson;

import java.util.ArrayList;

public class PokeCard extends AppCompatActivity {

    private static String pok = "";
    SharedPreferences mPrefs;
    CommAndFav p;
    Pokemon pokemon;
    ImageView ib;
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
        pok = arguments.get("link").toString();
        mPrefs = getSharedPreferences(Utils.getPreferenses(), MODE_PRIVATE);
        pokemon = new Network().getPokemon(pok);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_poke_card);

        binding.setPokemon(pokemon);
        p = findOnId(pokemon.getId(), PokeWikia.CaFpoke);
        if (p == null) {
            p = new CommAndFav();
            p.setId(pokemon.getId());
            p.setName(pokemon.getName());
            p.setUrl(pok);
        }
        if (p.getIsFav()) {
            binding.favBtn.setImageResource(android.R.drawable.star_big_on);
        }
        if (p.getComment() != null) {
            binding.UsCom.setText(p.getComment());
        }
        if (pokemon.getSprite(5) == null) {
            binding.sp5.setVisibility(View.GONE);
            binding.sp6.setVisibility(View.GONE);
            binding.sp7.setVisibility(View.GONE);
            binding.sp8.setVisibility(View.GONE);
        }

    }

    public CommAndFav findOnId(
            int id, ArrayList<CommAndFav> caf) {

        for (CommAndFav c : caf) {
            if (c.equals(id)) {
                return c;
            }
        }
        return null;
    }

    public void saveComm(View view) {
        p.setComment(binding.UsCom.getText().toString());
        //p.setComment(((EditText) findViewById(R.id.UsCom)).getText().toString());
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
        p.setIsFav(!p.getIsFav());
        if (p.getIsFav()) {
            binding.favBtn.setImageResource(android.R.drawable.star_big_on);
            showToast("Save in Fav");
        } else {
            binding.favBtn.setImageResource(android.R.drawable.star_big_off);
            showToast("Delete from Fav");
        }
        save();
    }

    private void save() {
        if (findOnId(pokemon.getId(), PokeWikia.CaFpoke) == null) {
            PokeWikia.CaFpoke.add(p);
        }
        try {
            SharedPreferences.Editor prefsEditor = mPrefs.edit();
            Gson gson = new Gson();
            String json = gson.toJson(PokeWikia.CaFpoke);
            prefsEditor.putString("commandfav2", json);
            prefsEditor.commit();
        } catch (Exception e) {
            Log.d("comfavsave", e.getMessage());
        }
    }

    private void showToast(String mes) {
        Toast toast = Toast.makeText(this, mes, Toast.LENGTH_LONG);
        toast.show();
    }
}
