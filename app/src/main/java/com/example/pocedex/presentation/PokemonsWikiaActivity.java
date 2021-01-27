package com.example.pocedex.presentation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.view.View;

import com.example.pocedex.R;
import com.example.pocedex.domain.LocalSave;
import com.example.pocedex.domain.Network;
import com.example.pocedex.domain.Utils;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.List;

public class PokemonsWikiaActivity extends AppCompatActivity {

    static final int PAGE_COUNT = 2;
    List<Fragment> allFragments;
    boolean listvisible = false;
    private ViewPager2 viewPager;
    private FragmentStateAdapter pagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!Utils.isOnline(this)) {
            setContentView(R.layout.offline);
            return;
        }
        new Network().resetList();
        setOnline();
        listvisible = true;

    }

    public void checkOnline(View view) {
        if (Utils.isOnline(this)) {
            if (!listvisible)
                setOnline();
            else {
                findViewById(R.id.tryreconnect).setVisibility(View.INVISIBLE);
                if (allFragments != null && allFragments.size() > 0)
                    ((PageFragment) allFragments.get(0)).updateConnection();
            }
        }
    }

    public void setOffline() {
        findViewById(R.id.tryreconnect).setVisibility(View.VISIBLE);
    }

    private void setOnline() {
        listvisible = true;
        setContentView(R.layout.activity_poce_wikia);
        findViewById(R.id.tryreconnect).setVisibility(View.INVISIBLE);
        Utils.setLocalSave(new LocalSave(this));
        commAndFavList();

        viewPager = findViewById(R.id.pager);
        pagerAdapter = new ScreenSlidePagerAdapter(this);
        viewPager.setAdapter(pagerAdapter);

        TabLayout tabLayout = findViewById(R.id.tab_layout);
        new TabLayoutMediator(tabLayout, viewPager,
                (tab, position) -> {
                    if (position == 0)
                        tab.setText("All");
                    else tab.setText("Favorite");
                }
        ).attach();

    }

    @Override
    protected void onStart() {
        super.onStart();
        allFragments = getSupportFragmentManager().getFragments();
        if (allFragments.size() > 0)
            ((PageFragment) allFragments.get(1)).updateFavList();
    }

    @Override
    protected void onStart() {
        super.onStart();
        allFragments = getSupportFragmentManager().getFragments();
        if (allFragments.size() > 1)
            ((PageFragment) allFragments.get(1)).updateFavList();
    }

    private void commAndFavList() {
        Utils.getLocalSave().open();
    }

    private class ScreenSlidePagerAdapter extends FragmentStateAdapter {
        public ScreenSlidePagerAdapter(FragmentActivity fa) {
            super(fa);
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            Fragment fragment = new PageFragment();
            Bundle args = new Bundle();
            args.putInt("arg_page_number", position);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public int getItemCount() {
            return PAGE_COUNT;
        }
    }
}