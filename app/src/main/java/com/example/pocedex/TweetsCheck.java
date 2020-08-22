package com.example.pocedex;

import android.app.PendingIntent;
import android.content.Intent;
import android.util.Log;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.work.Worker;

public class TweetsCheck extends Worker {

    static final String TAG = "tweet";
    private static final int NOTIFY_ID = 101;
    private static String CHANNEL_ID = "Pokedex";
    private static int nn=0;
    public static ArrayList<PokeTweet> pokeTweets=new ArrayList<>();

    @NonNull
    @Override
    public Result doWork() {
        if (!isStopped()) {
            Log.d(TAG, "check");
            String s=ExmpStr();
            GsonBuilder builder = new GsonBuilder();
            Gson gson = builder.create();
            PokeTweet[] p = gson.fromJson(s, PokeTweet[].class);
            for (int i=0; i<p.length;i++)
            {
                //if(p[i].id!=pokeTweets.get(pokeTweets.size()-1).id)
                pokeTweets.add(p[i]);
                //else {break;}
            }
            nn=pokeTweets.size();
            Log.d("Create Response", "");
            if (nn>0)
                SetNot();
        }
        return Result.SUCCESS;
    }

    private void SetNot()
    {
        Intent resultIntent = new Intent(getApplicationContext(), UnreadNews.class);
        PendingIntent resultPendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, resultIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(getApplicationContext(), CHANNEL_ID)
                        .setSmallIcon(R.drawable.ic_launcher_background)
                        .setContentTitle("Pocedex")
                        .setContentText("You have "+nn+" unread news")
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                        .setContentIntent(resultPendingIntent)
                        .setAutoCancel(true);

        NotificationManagerCompat notificationManager =
                NotificationManagerCompat.from(getApplicationContext());
        notificationManager.notify(NOTIFY_ID, builder.build());
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