package com.example.pocedex;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.util.ArrayList;

public class PokeCard extends AppCompatActivity {

    private static String pok = "";
    JSONParser jsonParser = new JSONParser();
    CommAndFav p;
    Pokemon pokemon;
    Bitmap bitmap;
    ImageView ib;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poke_card);
        Bundle arguments = getIntent().getExtras();
        pok = arguments.get("link").toString();

        new Card().execute();
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

    public void saveComm(View view)
    {
        p.comment=((EditText) findViewById(R.id.UsCom)).getText().toString();
        showToast("You're comment save");
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
    public void saveFav(View view)
    {
        p.is_fav=!p.is_fav;
        if (p.is_fav)
        {
            ib.setImageResource(android.R.drawable.star_big_on);
            showToast("Save in Fav");
        }
        else {
            ib.setImageResource(android.R.drawable.star_big_off);
            showToast("Delete from Fav");
        }
        save();
    }

    private void save()
    {
        if (findOnId(pokemon.id, PokeWikia.CaFpoke)==null) {
            PokeWikia.CaFpoke.add(p);
        }
        FileOutputStream fos;
        try {
            fos = openFileOutput("commandfav.txt", Context.MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(PokeWikia.CaFpoke);
            oos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    private void showToast(String mes)
    {
        Toast toast = Toast.makeText(this, mes,Toast.LENGTH_LONG);
        toast.show();
    }




    class Card extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        protected String doInBackground(String... args) {
            JSONObject json = jsonParser.makeHttpRequest(pok);
            String s=json.toString();
            GsonBuilder builder = new GsonBuilder();
            Gson gson = builder.create();
            pokemon = gson.fromJson(s, Pokemon.class);
            pokemon.setLink(pok);
            try {

                InputStream is = new URL(pokemon.sprites.other.official_artwork.front_default).openStream();
                bitmap = BitmapFactory.decodeStream(is);
            }
            catch (Exception e)
            {
                Log.d("Fail Image", pokemon.sprites.other.official_artwork.front_default);
            }
            Log.d("Create Response", json.toString());

            return null;
        }

        protected void onPostExecute(String file_url) {
            TextView tv1 = (TextView) findViewById(R.id.pokname);
            TextView tv2 = (TextView) findViewById(R.id.ab);
            ImageView iv1 =(ImageView) findViewById(R.id.pokeim);
            ib =(ImageView) findViewById(R.id.favBtn);
            EditText et= (EditText)findViewById(R.id.UsCom);

            tv1.setText(pokemon.getName());
            iv1.setImageBitmap(bitmap);
            p=findOnId(pokemon.id, PokeWikia.CaFpoke);
            if (p==null)
            {
                p=new CommAndFav();
                p.id=pokemon.id;
                p.name=pokemon.getName();
                p.link=pok;
            }
            if (p.is_fav)
            {
                ib.setImageResource(android.R.drawable.star_big_on);
            }
            if (p.comment!=null)
            {
               et.setText(p.comment);
            }
        }

    }
}
