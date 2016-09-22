package com.micutu.trafictube;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.micutu.trafictube.Crawler.TopVideosSingleton;
import com.micutu.trafictube.Crawler.VideoListResponse;
import com.micutu.trafictube.Data.Video;
import com.micutu.trafictube.Fragments.VideosListFragment;

import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private final static String TAG = MainActivity.class.getSimpleName();
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();

        switchFragment(R.id.latest);

        TopVideosSingleton.getVideoOfTheDay(getApplicationContext(), new VideoListResponse() {
            @Override
            public void onResponse(Video video, Map<String, Object> extra) {
                if (extra.containsKey("error")) {
                    System.out.println(extra.get("error"));
                    return;
                }

                System.out.println(video);
            }
        });
    }

    public void init() {
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_menu);

        navigationView = (NavigationView) findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switchFragment(item.getItemId());
                if (item.isChecked()) {
                    item.setChecked(false);
                } else {
                    item.setChecked(true);
                }
                drawerLayout.closeDrawers();
                return false;
            }
        });

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
    }

    private void switchFragment(int itemId) {
        Fragment fragment = new VideosListFragment();
        Bundle args = new Bundle();
        args.putInt(VideosListFragment.MENU_ID, itemId);
        fragment.setArguments(args);

        getFragmentManager().beginTransaction()
                .setCustomAnimations(R.animator.enter, R.animator.exit)
                .replace(R.id.frame_layout, fragment)
                .commit();
    }
}
