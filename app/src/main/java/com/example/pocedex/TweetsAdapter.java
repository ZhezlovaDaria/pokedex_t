package com.example.pocedex;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
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
        //holder.ava.setImageResource(twe.user.profile_image_url_https);
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
        final ImageView ava, media;
        final TextView name, named, des, dnt, rets, likes;
        ViewHolder(View view){
            super(view);
            ava = (ImageView)view.findViewById(R.id.twiava);
            media = (ImageView)view.findViewById(R.id.media1);
            name = (TextView) view.findViewById(R.id.name1);
            named = (TextView) view.findViewById(R.id.name2);
            des = (TextView) view.findViewById(R.id.des);
            dnt = (TextView) view.findViewById(R.id.twdata);
            rets = (TextView) view.findViewById(R.id.retwe);
            likes = (TextView) view.findViewById(R.id.like);
        }
    }
}