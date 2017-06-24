package com.micutu.trafictube.Fragments.Player;

import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.micutu.trafictube.Data.Video;
import com.micutu.trafictube.R;
import com.vimeo.android.deeplink.VimeoDeeplink;

import java.util.List;

public class InstallPlayerFragment extends Fragment implements View.OnClickListener {

    private Integer videoType = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.player_install_app, container, false);
    }

    public void showInstall(Integer videoType) {
        this.videoType = videoType;

        View root = getView();
        TextView textView = (TextView) root.findViewById(R.id.install_text);
        textView.setText(getStringIdFromVideoType(videoType));

        root.findViewById(R.id.install_button).setOnClickListener(this);
    }

    private int getStringIdFromVideoType(Integer videoType) {
        if (videoType == Video.TYPE_YOUTUBE) {
            return R.string.youtube_install_text;
        }

        return R.string.vimeo_install_text;
    }

    @Override
    public void onClick(View v) {
        if (videoType == null) {
            return;
        }

        if (videoType == Video.TYPE_VIMEO) {
            VimeoDeeplink.viewVimeoAppInAppStore(getContext());
            return;
        }

        openPlayStore("com.google.android.youtube");
    }

    private void openPlayStore(String packageName) {
        Intent rateIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + packageName));
        boolean marketFound = false;

        final List<ResolveInfo> otherApps = getContext().getPackageManager().queryIntentActivities(rateIntent, 0);
        for (ResolveInfo otherApp : otherApps) {
            if (otherApp.activityInfo.applicationInfo.packageName.equals("com.android.vending")) {
                ActivityInfo otherAppActivity = otherApp.activityInfo;
                ComponentName componentName = new ComponentName(otherAppActivity.applicationInfo.packageName, otherAppActivity.name);
                rateIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                rateIntent.addFlags(Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
                rateIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                rateIntent.setComponent(componentName);
                getContext().startActivity(rateIntent);
                marketFound = true;
                break;

            }
        }

        if (!marketFound) {
            Intent webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + packageName));
            getContext().startActivity(webIntent);
        }
    }
}
