package com.example.pocedex;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import androidx.databinding.BindingAdapter;

public class Utils {

    public static final String APP_PREFERENCES = "commandfav";
    public static List<PokeTweet> NewTweets = new ArrayList<>();

    @BindingAdapter({"app:url"})
    public static void loadImage(ImageView view, String url) {
        try {
            Picasso.get().load(url).into(view);
        } catch (Exception e) {
            Log.d("Image", e.getMessage());
        }
    }

    public static String ExmpStr() {
        //region JSON example
        String s = "[\n" +
                "  {\n" +
                "    \"created_at\": \"Thu Apr 06 15:28:43 +0000 2017\",\n" +
                "    \"id\": 850007368138018817,\n" +
                "    \"id_str\": \"850007368138018817\",\n" +
                "    \"text\": \"lala RT @TwitterDev: 1/ Today we’re sharing our vision for the future of the Twitter API platform!nhttps://t.co/XweGngmxlP\",\n" +
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

    public static String DateFormat(String date) {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", Locale.getDefault());
        try {
            cal.setTime(sdf.parse(date));
            Date date1 = cal.getTime();
            sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
            return sdf.format(date1);
        } catch (Exception e) {
            Log.d("Fail Date", "");
        }
        return date;
    }

    public static boolean isOnline(Context context) {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        }
        return false;
    }
}
