package com.example.favoritefilm;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.design.widget.TabLayout;

import com.example.favoritefilm.adapter.SectionsPageAdapter;

public class MainActivity extends AppCompatActivity {
    ViewPager mViewPager;
    TabLayout tableLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tableLayout = findViewById(R.id.tablayout_favorite);
        mViewPager = findViewById(R.id.vp_favorite);
        setupViewPager(mViewPager);
        tableLayout.setupWithViewPager(mViewPager);
    }

    private void setupViewPager(ViewPager viewPager){
        SectionsPageAdapter adapter = new SectionsPageAdapter(getSupportFragmentManager());
        adapter.addFragment(new NowPlayingFavoriteFragment(),"Now Playing");
        adapter.addFragment(new TvShowFavoriteFragment(),"Tv Show");
        viewPager.setAdapter(adapter);
    }
}
