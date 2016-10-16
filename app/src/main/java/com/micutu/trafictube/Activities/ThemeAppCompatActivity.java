package com.micutu.trafictube.Activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;

import com.micutu.trafictube.R;

public abstract class ThemeAppCompatActivity extends AppCompatActivity {
    private final static String SHARED_PREFERENCES_KEY = "theme";
    private final static int THEME1 = 0;
    private final static int THEME2 = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        switch (getTheme(this)) {
            case 0:
                setTheme(R.style.Theme1);
                break;
            case 1:
                setTheme(R.style.Theme2);
                break;
            default:
                setTheme(R.style.Theme1);
                break;
        }
        super.onCreate(savedInstanceState);
    }

    public static void setTheme(Activity activity, int theme) {
        setTheme(activity, theme, true);
    }

    public static void setTheme(Activity activity, int theme, boolean restart) {
        PreferenceManager.getDefaultSharedPreferences(activity).edit().putInt(SHARED_PREFERENCES_KEY, theme).apply();

        if (restart) {
            Intent intent = activity.getIntent();
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            activity.finish();
            activity.overridePendingTransition(0, 0);
            activity.startActivity(intent);
            activity.overridePendingTransition(0, 0);
        }
    }

    public static int getTheme(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context).getInt(SHARED_PREFERENCES_KEY, 0);
    }
}
