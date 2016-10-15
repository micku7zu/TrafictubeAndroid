package com.micutu.trafictube.Activities;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import com.micutu.trafictube.Adapters.ViewHolders.PostsListViewHolder.PostsActionsListener;
import com.micutu.trafictube.Data.User;
import com.micutu.trafictube.Fragments.AboutFragment;
import com.micutu.trafictube.Fragments.PostsListFragment;
import com.micutu.trafictube.R;

public class MainActivity extends AppCompatPlayVideoActivity implements PostsActionsListener, PostsListFragment.OnSearchDialogShow, NavigationView.OnNavigationItemSelectedListener {
    private final static String TAG = MainActivity.class.getSimpleName();

    public static String YOUTUBE_DEVELOPER_KEY = "AIzaSyBfF9G0xNvqZSg_h81X67jGqt5rAxFNWqs";

    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        switchFragment(R.id.latest);
    }

    public void init() {
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_menu);

        navigationView = (NavigationView) findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(this);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
    }

    private void switchFragment(int itemId) {
        switchFragment(itemId, null);
    }

    private void switchFragment(int itemId, String search) {
        Fragment fragment = new PostsListFragment();
        Bundle args = new Bundle();
        args.putInt(PostsListFragment.MENU_ID, itemId);

        if (search != null && search.length() > 0) {
            args.putString(PostsListFragment.SEARCH, search);
        }

        fragment.setArguments(args);
        showFragment(fragment);
        navigationView.getMenu().findItem(itemId).setChecked(true);
        drawerLayout.closeDrawers();
    }

    private void showAboutFragment() {
        showFragment(new AboutFragment());
        navigationView.getMenu().findItem(R.id.about).setChecked(true);
        drawerLayout.closeDrawers();
    }

    private void showFragment(Fragment fragment) {
        getFragmentManager().beginTransaction()
                .setCustomAnimations(R.animator.fade_in, R.animator.fade_out)
                .replace(R.id.frame_layout, fragment)
                .commit();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.search:
                this.showSearchDialog();
                return false;
            case R.id.about:
                this.showAboutFragment();
                return false;
        }

        switchFragment(item.getItemId());
        return false;
    }

    @Override
    public void showSearchDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        @SuppressLint("InflateParams") final View searchDialog = this.getLayoutInflater().inflate(R.layout.dialog_search, null);

        final EditText searchEditText = (EditText) searchDialog.findViewById(R.id.search_edit_text);
        builder.setTitle(R.string.search_dialog_title)
                .setView(searchDialog)
                .setPositiveButton(getResources().getString(R.string.search_dialog_title), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        switchFragment(R.id.search, searchEditText.getText().toString());
                    }
                })
                .setNegativeButton(getResources().getString(R.string.cancel), null);
        final AlertDialog dialog = builder.create();
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        dialog.show();

        searchEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                switchFragment(R.id.search, searchEditText.getText().toString());
                dialog.dismiss();
                return false;
            }
        });
    }

    @Override
    public boolean showUsersPostsButton() {
        return true;
    }

    @Override
    public void showUserPosts(User user) {
        Intent intent = new Intent(this, UserPostsActivity.class);
        intent.putExtra(UserPostsActivity.USERNAME, user.getUsername());
        intent.putExtra(UserPostsActivity.USER_NAME, user.getName());
        startActivity(intent);
    }
}
