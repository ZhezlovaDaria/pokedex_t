package com.example.pocedex;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class PokeWikia extends AppCompatActivity implements PokDataAdapter.ItemClickListener {


    private static String pw = "https://pokeapi.co/api/v2/pokemon";
    List<Pokemon> pokemons = new ArrayList<>();
    JSONParser jsonParser = new JSONParser();
    PokDataAdapter adapter = new PokDataAdapter(this, pokemons);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poce_wikia);

        new FullPokeList().execute();

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
                        new FullPokeList().execute();
                    }
                }
            }
        });
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onItemClick(View view, int position) {
        try{
            String link=(adapter.getPok(position)).getLink();
            Intent intent = new Intent(this, PokeCard.class);
            intent.putExtra("link", link);
            startActivity(intent);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    class FullPokeList extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        protected String doInBackground(String... args) {
            JSONObject json = jsonParser.makeHttpRequest(pw);

            Log.d("Create Response", json.toString());
            String s=json.toString();
            String result = s.substring(s.indexOf('['), s.indexOf(']')+1);
            try {
                JSONArray jsonArray = new JSONArray(result);
                for (int i=0; i<jsonArray.length();i++)
                {
                    pokemons.add(decodeJSON1(jsonArray.getJSONObject(i)));
                }
        }
            catch (JSONException e) {

            Log.e("JSON Parser", "Error parsing data " + e.toString());


        }
            return null;
        }

        protected void onPostExecute(String file_url) {
            adapter.notifyDataSetChanged();
        }


        public  Pokemon decodeJSON1(JSONObject json)
        {
            try {
                String name = json.get("name").toString();
                String link = json.get("url").toString();
                Pokemon p=new Pokemon();
                p.setName(name);
                p.setLink(link);
                return p;

            }catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}