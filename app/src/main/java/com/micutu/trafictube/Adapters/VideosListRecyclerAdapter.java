package com.micutu.trafictube.Adapters;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.micutu.trafictube.R;

import java.util.Random;

/**
 * Created by micku7zu on 9/20/2016.
 */

public class VideosListRecyclerAdapter extends RecyclerView.Adapter<VideosListRecyclerAdapter.ViewHolder> {
    private String[] mDataset;
    private int number = 10;
    private Random random;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        TextView title;
        TextView desc;
        TextView more;
        ImageView photo;

        public ViewHolder(View itemView) {
            super(itemView);
            cardView = (CardView)itemView.findViewById(R.id.card_view);
            title = (TextView)itemView.findViewById(R.id.title);
            desc = (TextView)itemView.findViewById(R.id.desc);
            more = (TextView)itemView.findViewById(R.id.more);
            photo = (ImageView)itemView.findViewById(R.id.image);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public VideosListRecyclerAdapter(String[] myDataset) {
        mDataset = myDataset;
        random = new Random();
    }

    public void addMoreItems() {
        number = number + 10;
        notifyDataSetChanged();
    }

    // Create new views (invoked by the layout manager)
    @Override
    public VideosListRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                                   int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cardview_item, parent, false);
        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.title.setText(random.nextInt() + "");
        holder.desc.setText(random.nextInt() + "");
        holder.more.setText(random.nextInt() + "");
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return number;
    }
}