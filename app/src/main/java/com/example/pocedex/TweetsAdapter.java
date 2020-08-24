package com.example.pocedex;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.pocedex.databinding.TweetItemBinding;

import java.util.List;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

public class TweetsAdapter extends RecyclerView.Adapter<TweetsAdapter.ViewHolder> {

    private List<PokeTweet> tweets;

    TweetsAdapter(Context context, List<PokeTweet> tweets) {
        this.tweets = tweets;
    }

    @Override
    public TweetsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater  = LayoutInflater.from(parent.getContext());
        TweetItemBinding binding = TweetItemBinding.inflate(inflater, parent, false);
        return new ViewHolder(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(TweetsAdapter.ViewHolder holder, int position) {
        PokeTweet twe = tweets.get(position);
        if (twe.getRetweeted_status()!=null)
        {
            PokeTweet retwe=twe.getRetweeted_status();
            String s="RT @" + retwe.getUserSName() + ": " + retwe.getText();
            if (s.length()>=139) {
                s = s.substring(0,139);
                s+="…";
            }
            if (s.equals(twe.getText()))
            {
                twe = twe.getRetweeted_status();
            }
            else
            {
                twe.setShortTxt(twe.getText().substring(0, twe.getText().indexOf("RT @")));

                holder.ret.setVisibility(View.VISIBLE);

                holder.binding.setRetweet(retwe);
            }

        }
        holder.binding.setTweet(twe);
    }

    @Override
    public int getItemCount() {
        return tweets.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        final LinearLayout ret;
        TweetItemBinding binding;
        ViewHolder(View view){
            super(view);
            binding= DataBindingUtil.bind(view);
            ret=(LinearLayout) view.findViewById(R.id.retweet);

        }
    }
}