package com.micutu.trafictube.Adapters.ViewHolders;

import android.content.Context;
import android.support.v7.widget.AppCompatImageButton;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.micutu.trafictube.Crawler.VolleySingleton;
import com.micutu.trafictube.R;


public class VideosListViewHolder extends RecyclerView.ViewHolder {
    private TextView title = null;
    private TextView more = null;
    private NetworkImageView image = null;

    public VideosListViewHolder(final View itemView) {
        super(itemView);

        title = (TextView) itemView.findViewById(R.id.title);
        more = (TextView) itemView.findViewById(R.id.more);
        image = (NetworkImageView) itemView.findViewById(R.id.image);
    }

    public void setTitle(String text) {
        this.title.setText(text);
    }

    public void setMore(String text) {
        this.more.setText(text);
    }

    public void setImage(Context context, String url) {
        this.image.setImageUrl(url, VolleySingleton.getImageLoader(context));
    }
}