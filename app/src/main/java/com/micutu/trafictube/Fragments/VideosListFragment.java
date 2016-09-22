package com.micutu.trafictube.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.micutu.trafictube.MainRecyclerAdapter;
import com.micutu.trafictube.R;

public class VideosListFragment extends Fragment {
    public static final String MENU_ID = "menu_id";

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    public VideosListFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.videos_list_fragment, container, false);
        int menuId = getArguments().getInt(MENU_ID);

        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);

        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new MainRecyclerAdapter(new String[]{"TEst", "2"});
        mRecyclerView.setAdapter(mAdapter);

        return rootView;
    }
}
