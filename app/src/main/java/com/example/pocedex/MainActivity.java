package com.example.pocedex;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    final String TAG = "States";
    private static String twp="https://api.twitter.com/1.1/statuses/user_timeline.json?screen_name=Pokemon&count=2";
    JSONParser jsonParser = new JSONParser();
    PeriodicWorkRequest TweetcheckRequest= new PeriodicWorkRequest.Builder(TweetsCheck.class, 60, TimeUnit.MINUTES, 1, TimeUnit.MINUTES)
            .build();
    ArrayList<PokeTweet> pokeTweets=new ArrayList<>();
    TweetsAdapter adapter = new TweetsAdapter(this, pokeTweets);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new TweetLenta().execute();

       WorkManager.getInstance().cancelWorkById(TweetcheckRequest.getId());

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.twwfeed);
        final LinearLayoutManager layoutManager
                = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0) {
                    if ((layoutManager.getChildCount() + layoutManager.findFirstVisibleItemPosition()) >= layoutManager.getItemCount()) {
                        Log.d("TAG", "End of list");
                        new TweetLenta().execute();
                    }
                }
            }
        });
        recyclerView.setAdapter(adapter);
    }

    public void onClick(View view) {
        Intent intent = new Intent(this, PokeWikia.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onPause() {
        super.onPause();
        try {
            WorkManager.getInstance().enqueueUniquePeriodicWork(TAG, ExistingPeriodicWorkPolicy.REPLACE, TweetcheckRequest);
        }
        catch (Exception e)
        {
            Log.d(TAG, e.getMessage());
        }
        Log.d(TAG, "MainActivity: onPause()");
    }

    @Override
    protected void onStop() {
        super.onStop();
        try {
            WorkManager.getInstance().enqueueUniquePeriodicWork(TAG, ExistingPeriodicWorkPolicy.REPLACE, TweetcheckRequest);
        }
        catch (Exception e)
        {
            Log.d(TAG, e.getMessage());
        }
        Log.d(TAG, "MainActivity: onStop()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            WorkManager.getInstance().enqueueUniquePeriodicWork(TAG, ExistingPeriodicWorkPolicy.REPLACE, TweetcheckRequest);
        }
        catch (Exception e)
        {
            Log.d(TAG, e.getMessage());
        }
        Log.d(TAG, "MainActivity: onDestroy()");
    }

    class TweetLenta extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        protected String doInBackground(String... args) {
           // JSONObject json = jsonParser.makeHttpRequest(twp);
            String s=ExmpStr();
            GsonBuilder builder = new GsonBuilder();
            Gson gson = builder.create();
            PokeTweet[] p = gson.fromJson(s, PokeTweet[].class);
            for (int i=0; i<p.length;i++)
            { pokeTweets.add(p[i]);}

            Log.d("Create Response", "");

            return null;
        }
        protected void onPostExecute(String file_url) {
            adapter.notifyDataSetChanged();
        }

        private String ExmpStr()
        {
            //region JSON example
            String s="[\n" +
                    "  {\n" +
                    "    \"created_at\": \"Thu Apr 06 15:28:43 +0000 2017\",\n" +
                    "    \"id\": 850007368138018817,\n" +
                    "    \"id_str\": \"850007368138018817\",\n" +
                    "    \"text\": \"RT @TwitterDev: 1/ Today we’re sharing our vision for the future of the Twitter API platform!nhttps://t.co/XweGngmxlP\",\n" +
                    "    \"truncated\": false,\n" +
                    "    \"entities\": {\n" +
                    "      \"hashtags\": [],\n" +
                    "      \"symbols\": [],\n" +
                    "      \"user_mentions\": [\n" +
                    "        {\n" +
                    "          \"screen_name\": \"TwitterDev\",\n" +
                    "          \"name\": \"TwitterDev\",\n" +
                    "          \"id\": 2244994945,\n" +
                    "          \"id_str\": \"2244994945\",\n" +
                    "          \"indices\": [\n" +
                    "            3,\n" +
                    "            14\n" +
                    "          ]\n" +
                    "        }\n" +
                    "      ],\n" +
                    "      \"urls\": [\n" +
                    "        {\n" +
                    "          \"url\": \"https://t.co/XweGngmxlP\",\n" +
                    "          \"expanded_url\": \"https://cards.twitter.com/cards/18ce53wgo4h/3xo1c\",\n" +
                    "          \"display_url\": \"cards.twitter.com/cards/18ce53wg…\",\n" +
                    "          \"indices\": [\n" +
                    "            94,\n" +
                    "            117\n" +
                    "          ]\n" +
                    "        }\n" +
                    "      ]\n" +
                    "    },\n" +
                    "    \"in_reply_to_status_id\": null,\n" +
                    "    \"in_reply_to_status_id_str\": null,\n" +
                    "    \"in_reply_to_user_id\": null,\n" +
                    "    \"in_reply_to_user_id_str\": null,\n" +
                    "    \"in_reply_to_screen_name\": null,\n" +
                    "    \"user\": {\n" +
                    "      \"id\": 6253282,\n" +
                    "      \"id_str\": \"6253282\",\n" +
                    "      \"name\": \"Twitter API\",\n" +
                    "      \"screen_name\": \"twitterapi\",\n" +
                    "      \"location\": \"San Francisco, CA\",\n" +
                    "      \"description\": \"The Real Twitter API. I tweet about API changes, service issues and happily answer questions about Twitter and our API. Don't get an answer? It's on my website.\",\n" +
                    "      \"url\": \"http://t.co/78pYTvWfJd\",\n" +
                    "      \"entities\": {\n" +
                    "        \"url\": {\n" +
                    "          \"urls\": [\n" +
                    "            {\n" +
                    "              \"url\": \"http://t.co/78pYTvWfJd\",\n" +
                    "              \"expanded_url\": \"https://dev.twitter.com\",\n" +
                    "              \"display_url\": \"dev.twitter.com\",\n" +
                    "              \"indices\": [\n" +
                    "                0,\n" +
                    "                22\n" +
                    "              ]\n" +
                    "            }\n" +
                    "          ]\n" +
                    "        },\n" +
                    "        \"description\": {\n" +
                    "          \"urls\": []\n" +
                    "        }\n" +
                    "      },\n" +
                    "      \"protected\": false,\n" +
                    "      \"followers_count\": 6172353,\n" +
                    "      \"friends_count\": 46,\n" +
                    "      \"listed_count\": 13091,\n" +
                    "      \"created_at\": \"Wed May 23 06:01:13 +0000 2007\",\n" +
                    "      \"favourites_count\": 26,\n" +
                    "      \"utc_offset\": -25200,\n" +
                    "      \"time_zone\": \"Pacific Time (US & Canada)\",\n" +
                    "      \"geo_enabled\": true,\n" +
                    "      \"verified\": true,\n" +
                    "      \"statuses_count\": 3583,\n" +
                    "      \"lang\": \"en\",\n" +
                    "      \"contributors_enabled\": false,\n" +
                    "      \"is_translator\": false,\n" +
                    "      \"is_translation_enabled\": false,\n" +
                    "      \"profile_background_color\": \"C0DEED\",\n" +
                    "      \"profile_background_image_url\": \"http://pbs.twimg.com/profile_background_images/656927849/miyt9dpjz77sc0w3d4vj.png\",\n" +
                    "      \"profile_background_image_url_https\": \"https://pbs.twimg.com/profile_background_images/656927849/miyt9dpjz77sc0w3d4vj.png\",\n" +
                    "      \"profile_background_tile\": true,\n" +
                    "      \"profile_image_url\": \"http://pbs.twimg.com/profile_images/2284174872/7df3h38zabcvjylnyfe3_normal.png\",\n" +
                    "      \"profile_image_url_https\": \"https://pbs.twimg.com/profile_images/1293217550050799617/8LFWTYD3_400x400.jpg\",\n" +
                    "      \"profile_banner_url\": \"https://pbs.twimg.com/profile_banners/6253282/1431474710\",\n" +
                    "      \"profile_link_color\": \"0084B4\",\n" +
                    "      \"profile_sidebar_border_color\": \"C0DEED\",\n" +
                    "      \"profile_sidebar_fill_color\": \"DDEEF6\",\n" +
                    "      \"profile_text_color\": \"333333\",\n" +
                    "      \"profile_use_background_image\": true,\n" +
                    "      \"has_extended_profile\": false,\n" +
                    "      \"default_profile\": false,\n" +
                    "      \"default_profile_image\": false,\n" +
                    "      \"following\": true,\n" +
                    "      \"follow_request_sent\": false,\n" +
                    "      \"notifications\": false,\n" +
                    "      \"translator_type\": \"regular\"\n" +
                    "    },\n" +
                    "    \"geo\": null,\n" +
                    "    \"coordinates\": null,\n" +
                    "    \"place\": null,\n" +
                    "    \"contributors\": null,\n" +
                    "    \"retweeted_status\": {\n" +
                    "      \"created_at\": \"Thu Apr 06 15:24:15 +0000 2017\",\n" +
                    "      \"id\": 850006245121695744,\n" +
                    "      \"id_str\": \"850006245121695744\",\n" +
                    "      \"text\": \"1/ Today we’re sharing our vision for the future of the Twitter API platform!nhttps://t.co/XweGngmxlP\",\n" +
                    "      \"truncated\": false,\n" +
                    "      \"entities\": {\n" +
                    "        \"hashtags\": [],\n" +
                    "        \"symbols\": [],\n" +
                    "        \"user_mentions\": [],\n" +
                    "        \"urls\": [\n" +
                    "          {\n" +
                    "            \"url\": \"https://t.co/XweGngmxlP\",\n" +
                    "            \"expanded_url\": \"https://cards.twitter.com/cards/18ce53wgo4h/3xo1c\",\n" +
                    "            \"display_url\": \"cards.twitter.com/cards/18ce53wg…\",\n" +
                    "            \"indices\": [\n" +
                    "              78,\n" +
                    "              101\n" +
                    "            ]\n" +
                    "          }\n" +
                    "        ]\n" +
                    "      },\n" +
                    "      \"in_reply_to_status_id\": null,\n" +
                    "      \"in_reply_to_status_id_str\": null,\n" +
                    "      \"in_reply_to_user_id\": null,\n" +
                    "      \"in_reply_to_user_id_str\": null,\n" +
                    "      \"in_reply_to_screen_name\": null,\n" +
                    "      \"user\": {\n" +
                    "        \"id\": 2244994945,\n" +
                    "        \"id_str\": \"2244994945\",\n" +
                    "        \"name\": \"TwitterDev\",\n" +
                    "        \"screen_name\": \"TwitterDev\",\n" +
                    "        \"location\": \"Internet\",\n" +
                    "        \"description\": \"Your official source for Twitter Platform news, updates & events. Need technical help? Visit https://t.co/mGHnxZCxkt ⌨️  #TapIntoTwitter\",\n" +
                    "        \"url\": \"https://t.co/66w26cua1O\",\n" +
                    "        \"entities\": {\n" +
                    "          \"url\": {\n" +
                    "            \"urls\": [\n" +
                    "              {\n" +
                    "                \"url\": \"https://t.co/66w26cua1O\",\n" +
                    "                \"expanded_url\": \"https://dev.twitter.com/\",\n" +
                    "                \"display_url\": \"dev.twitter.com\",\n" +
                    "                \"indices\": [\n" +
                    "                  0,\n" +
                    "                  23\n" +
                    "                ]\n" +
                    "              }\n" +
                    "            ]\n" +
                    "          },\n" +
                    "          \"description\": {\n" +
                    "            \"urls\": [\n" +
                    "              {\n" +
                    "                \"url\": \"https://t.co/mGHnxZCxkt\",\n" +
                    "                \"expanded_url\": \"https://twittercommunity.com/\",\n" +
                    "                \"display_url\": \"twittercommunity.com\",\n" +
                    "                \"indices\": [\n" +
                    "                  93,\n" +
                    "                  116\n" +
                    "                ]\n" +
                    "              }\n" +
                    "            ]\n" +
                    "          }\n" +
                    "        },\n" +
                    "        \"protected\": false,\n" +
                    "        \"followers_count\": 465425,\n" +
                    "        \"friends_count\": 1523,\n" +
                    "        \"listed_count\": 1168,\n" +
                    "        \"created_at\": \"Sat Dec 14 04:35:55 +0000 2013\",\n" +
                    "        \"favourites_count\": 2098,\n" +
                    "        \"utc_offset\": -25200,\n" +
                    "        \"time_zone\": \"Pacific Time (US & Canada)\",\n" +
                    "        \"geo_enabled\": true,\n" +
                    "        \"verified\": true,\n" +
                    "        \"statuses_count\": 3031,\n" +
                    "        \"lang\": \"en\",\n" +
                    "        \"contributors_enabled\": false,\n" +
                    "        \"is_translator\": false,\n" +
                    "        \"is_translation_enabled\": false,\n" +
                    "        \"profile_background_color\": \"FFFFFF\",\n" +
                    "        \"profile_background_image_url\": \"http://abs.twimg.com/images/themes/theme1/bg.png\",\n" +
                    "        \"profile_background_image_url_https\": \"https://abs.twimg.com/images/themes/theme1/bg.png\",\n" +
                    "        \"profile_background_tile\": false,\n" +
                    "        \"profile_image_url\": \"https://pbs.twimg.com/profile_images/1293217550050799617/8LFWTYD3_400x400.jpg\",\n" +
                    "        \"profile_image_url_https\": \"https://pbs.twimg.com/profile_images/1293217550050799617/8LFWTYD3_400x400.jpg\",\n" +
                    "        \"profile_banner_url\": \"https://pbs.twimg.com/profile_banners/2244994945/1396995246\",\n" +
                    "        \"profile_link_color\": \"0084B4\",\n" +
                    "        \"profile_sidebar_border_color\": \"FFFFFF\",\n" +
                    "        \"profile_sidebar_fill_color\": \"DDEEF6\",\n" +
                    "        \"profile_text_color\": \"333333\",\n" +
                    "        \"profile_use_background_image\": false,\n" +
                    "        \"has_extended_profile\": false,\n" +
                    "        \"default_profile\": false,\n" +
                    "        \"default_profile_image\": false,\n" +
                    "        \"following\": true,\n" +
                    "        \"follow_request_sent\": false,\n" +
                    "        \"notifications\": false,\n" +
                    "        \"translator_type\": \"regular\"\n" +
                    "      },\n" +
                    "      \"geo\": null,\n" +
                    "      \"coordinates\": null,\n" +
                    "      \"place\": null,\n" +
                    "      \"contributors\": null,\n" +
                    "      \"is_quote_status\": false,\n" +
                    "      \"retweet_count\": 284,\n" +
                    "      \"favorite_count\": 399,\n" +
                    "      \"favorited\": false,\n" +
                    "      \"retweeted\": false,\n" +
                    "      \"possibly_sensitive\": false,\n" +
                    "      \"lang\": \"en\"\n" +
                    "    },\n" +
                    "    \"is_quote_status\": false,\n" +
                    "    \"retweet_count\": 284,\n" +
                    "    \"favorite_count\": 0,\n" +
                    "    \"favorited\": false,\n" +
                    "    \"retweeted\": false,\n" +
                    "    \"possibly_sensitive\": false,\n" +
                    "    \"lang\": \"en\"\n" +
                    "  },\n" +
                    "  {\n" +
                    "    \"created_at\": \"Mon Apr 03 16:09:50 +0000 2017\",\n" +
                    "    \"id\": 848930551989915648,\n" +
                    "    \"id_str\": \"848930551989915648\",\n" +
                    "    \"text\": \"RT @TwitterMktg: Starting today, businesses can request and share locations when engaging with people in Direct Messages. https://t.co/rpYn…\",\n" +
                    "    \"truncated\": false,\n" +
                    "    \"entities\": {\n" +
                    "      \"hashtags\": [],\n" +
                    "      \"symbols\": [],\n" +
                    "      \"user_mentions\": [\n" +
                    "        {\n" +
                    "          \"screen_name\": \"TwitterMktg\",\n" +
                    "          \"name\": \"Twitter Marketing\",\n" +
                    "          \"id\": 357750891,\n" +
                    "          \"id_str\": \"357750891\",\n" +
                    "          \"indices\": [\n" +
                    "            3,\n" +
                    "            15\n" +
                    "          ]\n" +
                    "        }\n" +
                    "      ],\n" +
                    "      \"urls\": []\n" +
                    "    },\n" +
                    "    \"in_reply_to_status_id\": null,\n" +
                    "    \"in_reply_to_status_id_str\": null,\n" +
                    "    \"in_reply_to_user_id\": null,\n" +
                    "    \"in_reply_to_user_id_str\": null,\n" +
                    "    \"in_reply_to_screen_name\": null,\n" +
                    "    \"user\": {\n" +
                    "      \"id\": 6253282,\n" +
                    "      \"id_str\": \"6253282\",\n" +
                    "      \"name\": \"Twitter API\",\n" +
                    "      \"screen_name\": \"twitterapi\",\n" +
                    "      \"location\": \"San Francisco, CA\",\n" +
                    "      \"description\": \"The Real Twitter API. I tweet about API changes, service issues and happily answer questions about Twitter and our API. Don't get an answer? It's on my website.\",\n" +
                    "      \"url\": \"http://t.co/78pYTvWfJd\",\n" +
                    "      \"entities\": {\n" +
                    "        \"url\": {\n" +
                    "          \"urls\": [\n" +
                    "            {\n" +
                    "              \"url\": \"http://t.co/78pYTvWfJd\",\n" +
                    "              \"expanded_url\": \"https://dev.twitter.com\",\n" +
                    "              \"display_url\": \"dev.twitter.com\",\n" +
                    "              \"indices\": [\n" +
                    "                0,\n" +
                    "                22\n" +
                    "              ]\n" +
                    "            }\n" +
                    "          ]\n" +
                    "        },\n" +
                    "        \"description\": {\n" +
                    "          \"urls\": []\n" +
                    "        }\n" +
                    "      },\n" +
                    "      \"protected\": false,\n" +
                    "      \"followers_count\": 6172353,\n" +
                    "      \"friends_count\": 46,\n" +
                    "      \"listed_count\": 13091,\n" +
                    "      \"created_at\": \"Wed May 23 06:01:13 +0000 2007\",\n" +
                    "      \"favourites_count\": 26,\n" +
                    "      \"utc_offset\": -25200,\n" +
                    "      \"time_zone\": \"Pacific Time (US & Canada)\",\n" +
                    "      \"geo_enabled\": true,\n" +
                    "      \"verified\": true,\n" +
                    "      \"statuses_count\": 3583,\n" +
                    "      \"lang\": \"en\",\n" +
                    "      \"contributors_enabled\": false,\n" +
                    "      \"is_translator\": false,\n" +
                    "      \"is_translation_enabled\": false,\n" +
                    "      \"profile_background_color\": \"C0DEED\",\n" +
                    "      \"profile_background_image_url\": \"http://pbs.twimg.com/profile_background_images/656927849/miyt9dpjz77sc0w3d4vj.png\",\n" +
                    "      \"profile_background_image_url_https\": \"https://pbs.twimg.com/profile_background_images/656927849/miyt9dpjz77sc0w3d4vj.png\",\n" +
                    "      \"profile_background_tile\": true,\n" +
                    "      \"profile_image_url\": \"http://pbs.twimg.com/profile_images/2284174872/7df3h38zabcvjylnyfe3_normal.png\",\n" +
                    "      \"profile_image_url_https\": \"https://pbs.twimg.com/profile_images/1293217550050799617/8LFWTYD3_400x400.jpg\",\n" +
                    "      \"profile_banner_url\": \"https://pbs.twimg.com/profile_banners/6253282/1431474710\",\n" +
                    "      \"profile_link_color\": \"0084B4\",\n" +
                    "      \"profile_sidebar_border_color\": \"C0DEED\",\n" +
                    "      \"profile_sidebar_fill_color\": \"DDEEF6\",\n" +
                    "      \"profile_text_color\": \"333333\",\n" +
                    "      \"profile_use_background_image\": true,\n" +
                    "      \"has_extended_profile\": false,\n" +
                    "      \"default_profile\": false,\n" +
                    "      \"default_profile_image\": false,\n" +
                    "      \"following\": true,\n" +
                    "      \"follow_request_sent\": false,\n" +
                    "      \"notifications\": false,\n" +
                    "      \"translator_type\": \"regular\"\n" +
                    "    },\n" +
                    "    \"geo\": null,\n" +
                    "    \"coordinates\": null,\n" +
                    "    \"place\": null,\n" +
                    "    \"contributors\": null,\n" +
                    "    \"retweeted_status\": {\n" +
                    "      \"created_at\": \"Mon Apr 03 16:05:05 +0000 2017\",\n" +
                    "      \"id\": 848929357519241216,\n" +
                    "      \"id_str\": \"848929357519241216\",\n" +
                    "      \"text\": \"Starting today, businesses can request and share locations when engaging with people in Direct Messages. https://t.co/rpYndqWfQw\",\n" +
                    "      \"truncated\": false,\n" +
                    "      \"entities\": {\n" +
                    "        \"hashtags\": [],\n" +
                    "        \"symbols\": [],\n" +
                    "        \"user_mentions\": [],\n" +
                    "        \"urls\": [\n" +
                    "          {\n" +
                    "            \"url\": \"https://t.co/rpYndqWfQw\",\n" +
                    "            \"expanded_url\": \"https://cards.twitter.com/cards/5wzucr/3x700\",\n" +
                    "            \"display_url\": \"cards.twitter.com/cards/5wzucr/3…\",\n" +
                    "            \"indices\": [\n" +
                    "              105,\n" +
                    "              128\n" +
                    "            ]\n" +
                    "          }\n" +
                    "        ]\n" +
                    "      },\n" +
                    "      \"in_reply_to_status_id\": null,\n" +
                    "      \"in_reply_to_status_id_str\": null,\n" +
                    "      \"in_reply_to_user_id\": null,\n" +
                    "      \"in_reply_to_user_id_str\": null,\n" +
                    "      \"in_reply_to_screen_name\": null,\n" +
                    "      \"user\": {\n" +
                    "        \"id\": 357750891,\n" +
                    "        \"id_str\": \"357750891\",\n" +
                    "        \"name\": \"Twitter Marketing\",\n" +
                    "        \"screen_name\": \"TwitterMktg\",\n" +
                    "        \"location\": \"Twitter HQ \",\n" +
                    "        \"description\": \"Twitter’s place for marketers, agencies, and creative thinkers ⭐ Bringing you insights, news, updates, and inspiration. Visit @TwitterAdsHelp for Ads support.\",\n" +
                    "        \"url\": \"https://t.co/Tfo4moo92y\",\n" +
                    "        \"entities\": {\n" +
                    "          \"url\": {\n" +
                    "            \"urls\": [\n" +
                    "              {\n" +
                    "                \"url\": \"https://t.co/Tfo4moo92y\",\n" +
                    "                \"expanded_url\": \"https://marketing.twitter.com\",\n" +
                    "                \"display_url\": \"marketing.twitter.com\",\n" +
                    "                \"indices\": [\n" +
                    "                  0,\n" +
                    "                  23\n" +
                    "                ]\n" +
                    "              }\n" +
                    "            ]\n" +
                    "          },\n" +
                    "          \"description\": {\n" +
                    "            \"urls\": []\n" +
                    "          }\n" +
                    "        },\n" +
                    "        \"protected\": false,\n" +
                    "        \"followers_count\": 924546,\n" +
                    "        \"friends_count\": 661,\n" +
                    "        \"listed_count\": 3893,\n" +
                    "        \"created_at\": \"Thu Aug 18 21:08:15 +0000 2011\",\n" +
                    "        \"favourites_count\": 1934,\n" +
                    "        \"utc_offset\": -25200,\n" +
                    "        \"time_zone\": \"Pacific Time (US & Canada)\",\n" +
                    "        \"geo_enabled\": true,\n" +
                    "        \"verified\": true,\n" +
                    "        \"statuses_count\": 6329,\n" +
                    "        \"lang\": \"en\",\n" +
                    "        \"contributors_enabled\": false,\n" +
                    "        \"is_translator\": false,\n" +
                    "        \"is_translation_enabled\": false,\n" +
                    "        \"profile_background_color\": \"C0DEED\",\n" +
                    "        \"profile_background_image_url\": \"http://pbs.twimg.com/profile_background_images/662767273/jvmxdpdrplhxcw8yvkv2.png\",\n" +
                    "        \"profile_background_image_url_https\": \"https://pbs.twimg.com/profile_background_images/662767273/jvmxdpdrplhxcw8yvkv2.png\",\n" +
                    "        \"profile_background_tile\": true,\n" +
                    "        \"profile_image_url\": \"http://pbs.twimg.com/profile_images/800953549697888256/UlXXL5h5_normal.jpg\",\n" +
                    "        \"profile_image_url_https\": \"https://pbs.twimg.com/profile_images/800953549697888256/UlXXL5h5_normal.jpg\",\n" +
                    "        \"profile_banner_url\": \"https://pbs.twimg.com/profile_banners/357750891/1487188210\",\n" +
                    "        \"profile_link_color\": \"19CF86\",\n" +
                    "        \"profile_sidebar_border_color\": \"FFFFFF\",\n" +
                    "        \"profile_sidebar_fill_color\": \"DDEEF6\",\n" +
                    "        \"profile_text_color\": \"333333\",\n" +
                    "        \"profile_use_background_image\": true,\n" +
                    "        \"has_extended_profile\": false,\n" +
                    "        \"default_profile\": false,\n" +
                    "        \"default_profile_image\": false,\n" +
                    "        \"following\": false,\n" +
                    "        \"follow_request_sent\": false,\n" +
                    "        \"notifications\": false,\n" +
                    "        \"translator_type\": \"none\"\n" +
                    "      },\n" +
                    "      \"geo\": null,\n" +
                    "      \"coordinates\": null,\n" +
                    "      \"place\": null,\n" +
                    "      \"contributors\": null,\n" +
                    "      \"is_quote_status\": false,\n" +
                    "      \"retweet_count\": 111,\n" +
                    "      \"favorite_count\": 162,\n" +
                    "      \"favorited\": false,\n" +
                    "      \"retweeted\": false,\n" +
                    "      \"possibly_sensitive\": false,\n" +
                    "      \"lang\": \"en\"\n" +
                    "    },\n" +
                    "    \"is_quote_status\": false,\n" +
                    "    \"retweet_count\": 111,\n" +
                    "    \"favorite_count\": 0,\n" +
                    "    \"favorited\": false,\n" +
                    "    \"retweeted\": false,\n" +
                    "    \"lang\": \"en\"\n" +
                    "  }\n" +
                    "]";
            //endregion
            return s;
        }
    }
}
