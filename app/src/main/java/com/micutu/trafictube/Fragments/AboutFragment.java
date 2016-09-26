package com.micutu.trafictube.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;

import com.micutu.trafictube.R;

public class AboutFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ((Toolbar) getActivity().findViewById(R.id.toolbar)).animate().translationY(0).setInterpolator(new DecelerateInterpolator(2));
        return inflater.inflate(R.layout.about_fragment, container, false);
    }
}
