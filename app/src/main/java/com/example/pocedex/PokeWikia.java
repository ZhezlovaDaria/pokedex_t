package com.example.pocedex;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TabHost;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PokeWikia extends AppCompatActivity implements PokDataAdapter.ItemClickListener {


    private static String pw = "https://pokeapi.co/api/v2/pokemon";
    private static String pwnext = "";

    SharedPreferences mPrefs;
    List<Pokemon> pokemons = new ArrayList<>();
    List<Pokemon> favpokemons = new ArrayList<>();
    public static ArrayList<CommAndFav> CaFpoke = new ArrayList<>();
    PokDataAdapter adapter = new PokDataAdapter(this, pokemons);
    PokDataAdapter favadapter = new PokDataAdapter(this, favpokemons);
    boolean connetion = true;
    TabHost tabs;
    int imid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!Utils.isOnline(this)) {
            setContentView(R.layout.offline);
            return;
        }
        setContentView(R.layout.activity_poce_wikia);
        pwnext = pw;
        imid = 0;
        mPrefs = getSharedPreferences(Utils.APP_PREFERENCES, Context.MODE_PRIVATE);
        new FullPokeList().execute();
        CommandfavList();
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
                            if (pwnext != "null") {
                                new FullPokeList().execute();
                            } else {
                                showToast("End of list");
                            }
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

    public void ToNews(View view) {
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

    private void CommandfavList() {
        try {
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


    class FullPokeList extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        protected String doInBackground(String... args) {
            JSONObject json;
            String serverAnswer;

            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(pwnext)
                    .get()
                    .build();
            try {
                Response response = client.newCall(request).execute();

                serverAnswer = response.body().string();
                json = new JSONObject(serverAnswer);
                String s = json.toString();
                String result = s.substring(s.indexOf('['), s.indexOf(']') + 1);

                pwnext = json.get("next").toString();
                JSONArray jsonArray = new JSONArray(result);
                for (int i = 0; i < jsonArray.length(); i++) {
                    pokemons.add(decodeJSON1(jsonArray.getJSONObject(i)));
                    pokemons.get(imid).setId(imid);
                    imid++;
                }
            } catch (JSONException e) {
                Log.e("JSON Parser", "Error parsing data " + e.toString());
            } catch (Exception e) {
                Log.d("Fail Image", "png");
            }
            return null;
        }

        protected void onPostExecute(String file_url) {
            adapter.notifyDataSetChanged();
        }


        public Pokemon decodeJSON1(JSONObject json) {
            try {
                String name = json.get("name").toString();
                String link = json.get("url").toString();
                Pokemon p = new Pokemon();
                p.setName(name);
                p.setUrl(link);
                return p;

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}