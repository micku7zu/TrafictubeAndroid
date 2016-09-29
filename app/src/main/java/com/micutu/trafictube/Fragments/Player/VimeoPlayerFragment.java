package com.micutu.trafictube.Fragments.Player;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.widget.Toast;

public class VimeoPlayerFragment extends Fragment implements PlayerFragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Toast.makeText(getActivity(), "Vimeo not supported yet.", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void initialization(InitializationListener initializationListener) {
    }

    @Override
    public void playVideo(String id) {

    }

    @Override
    public void setFullscreen(Boolean fullscreen) {

    }

    @Override
    public Boolean isFullscreen() {
        return null;
    }
}
