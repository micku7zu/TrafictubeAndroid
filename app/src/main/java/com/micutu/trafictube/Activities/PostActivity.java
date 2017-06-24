package com.micutu.trafictube.Activities;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.micutu.trafictube.Activities.Abstracts.PlayVideoActivity;
import com.micutu.trafictube.Activities.Abstracts.ThemeActivity;
import com.micutu.trafictube.Data.Post;
import com.micutu.trafictube.R;

public class PostActivity extends PlayVideoActivity {
    public static final String BUNDLE_POST = "post";

    private Toolbar toolbar = null;
    private Post post = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_layout);

        post = (Post) getIntent().getExtras().getSerializable(BUNDLE_POST);

        init();
    }

    private void init() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ((TextView) findViewById(R.id.post_title)).setText(post.toString());
        findViewById(R.id.post_play).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showVideoDialog(post);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
