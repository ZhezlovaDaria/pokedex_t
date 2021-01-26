package com.example.pocedex.presentation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.pocedex.R;
import com.example.pocedex.domain.LocalSave;
import com.example.pocedex.domain.Network;
import com.example.pocedex.domain.Utils;

import java.util.List;

public class PokemonsWikiaActivity extends AppCompatActivity {

    ViewPager pager;
    PagerAdapter pagerAdapter;
    static final int PAGE_COUNT = 2;
    List<Fragment> allFragments;
    boolean listvisible = false;

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

        pager = findViewById(R.id.pager);
        pagerAdapter = new WikiaFragmentPagerAdapter(getSupportFragmentManager());
        pager.setAdapter(pagerAdapter);


        pager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                Log.d("", "position = " + position);
            }

            @Override
            public void onPageScrolled(int position, float positionOffset,
                                       int positionOffsetPixels) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        allFragments = getSupportFragmentManager().getFragments();
        if (allFragments.size() > 0)
            ((PageFragment) allFragments.get(1)).updateFavList();
    }

    private void commAndFavList() {
        Utils.getLocalSave().open();
    }

    public class WikiaFragmentPagerAdapter extends FragmentPagerAdapter {

        private WikiaFragmentPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return PageFragment.newInstance(position);
        }

        @Override
        public int getCount() {
            return PAGE_COUNT;
        }

        public CharSequence getPageTitle(int position) {
            if (position == 0)
                return "All";
            else return "Favorite";
        }

    }
}