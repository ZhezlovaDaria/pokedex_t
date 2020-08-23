package com.example.pocedex;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

public class TweetsAdapter extends RecyclerView.Adapter<TweetsAdapter.ViewHolder> {

    private List<PokeTweet> tweets;

    TweetsAdapter(Context context, List<PokeTweet> tweets) {
        this.tweets = tweets;
    }

    @Override
    public TweetsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.tweet_item,parent,false);
        return  new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(TweetsAdapter.ViewHolder holder, int position) {
        PokeTweet twe = tweets.get(position);
        if (twe.retweeted_status!=null)
        {
            String s="RT @" + twe.retweeted_status.user.screen_name + ": " + twe.retweeted_status.text;
            if (s.length()>=139) {
                s = s.substring(0,139);
                s+="…";
            }
            if (s.equals(twe.text))
            {
                twe = twe.retweeted_status;
            }
            else
            {
                twe.text=twe.text.substring(0, twe.text.indexOf("RT @"));

                holder.ret.setVisibility(View.VISIBLE);

                Picasso.get().load(twe.retweeted_status.user.profile_image_url_https).into(holder.rava);
                try {
                        Picasso.get().load(twe.retweeted_status.entities.medias[0].media_url).into(holder.mediar1);
                        Picasso.get().load(twe.retweeted_status.entities.medias[1].media_url).into(holder.mediar2);
                        Picasso.get().load(twe.retweeted_status.entities.medias[2].media_url).into(holder.mediar3);
                        Picasso.get().load(twe.retweeted_status.entities.medias[3].media_url).into(holder.mediar4);

                }
                catch (Exception e)
                {
                    Log.d("Image", e.getMessage());
                }
                holder.rname.setText(twe.retweeted_status.user.name);
                holder.rtext.setText(twe.retweeted_status.text);
            }

        }
        Picasso.get().load(twe.user.profile_image_url_https).into(holder.ava);

        try {

            Picasso.get().load(twe.entities.medias[0].media_url).into(holder.media1);
            Picasso.get().load(twe.entities.medias[1].media_url).into(holder.media2);
            Picasso.get().load(twe.entities.medias[2].media_url).into(holder.media3);
            Picasso.get().load(twe.entities.medias[3].media_url).into(holder.media4);
        }
        catch (Exception e)
        {
            Log.d("Image", e.getMessage());
        }



        //holder.media.setImageResource(twe.getImage());
        holder.name.setText(twe.user.name);
        holder.named.setText("@"+twe.user.screen_name);
        holder.des.setText(twe.text);
        holder.dnt.setText(twe.getCreated_at());
        holder.rets.setText(""+twe.retweet_count);
        holder.likes.setText(""+twe.favorite_count);
    }

    @Override
    public int getItemCount() {
        return tweets.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        final ImageView ava, rava, media1, media2, media3, media4, mediar1,mediar2,mediar3,mediar4;
        final TextView name, named, des, dnt, rets, likes, rname, rtext;
        final LinearLayout ret;
        ViewHolder(View view){
            super(view);
            ava = (ImageView)view.findViewById(R.id.twiava);
            rava = (ImageView)view.findViewById(R.id.rtwiava);
            media1 = (ImageView)view.findViewById(R.id.media1);
            media2 = (ImageView)view.findViewById(R.id.media2);
            media3 = (ImageView)view.findViewById(R.id.media3);
            media4 = (ImageView)view.findViewById(R.id.media4);
            mediar1 = (ImageView)view.findViewById(R.id.rmedia1);
            mediar2 = (ImageView)view.findViewById(R.id.rmedia2);
            mediar3 = (ImageView)view.findViewById(R.id.rmedia3);
            mediar4 = (ImageView)view.findViewById(R.id.rmedia4);
            name = (TextView) view.findViewById(R.id.name1);
            named = (TextView) view.findViewById(R.id.name2);
            des = (TextView) view.findViewById(R.id.des);
            dnt = (TextView) view.findViewById(R.id.twdata);
            rets = (TextView) view.findViewById(R.id.retwe);
            likes = (TextView) view.findViewById(R.id.like);
            rname = (TextView) view.findViewById(R.id.namere);
            rtext = (TextView) view.findViewById(R.id.redes);
            ret=(LinearLayout) view.findViewById(R.id.retweet);
        }
    }
}