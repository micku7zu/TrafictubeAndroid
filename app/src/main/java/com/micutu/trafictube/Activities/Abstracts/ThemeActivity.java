package com.micutu.trafictube.Activities.Abstracts;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ContextThemeWrapper;

import com.micutu.trafictube.R;

public abstract class ThemeActivity extends AppCompatActivity {
    private final static String SHARED_PREFERENCES_KEY = "theme";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(getThemeRId(this));
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

    public static int getThemeRId(Context context) {
        switch (getTheme(context)) {
            case 0:
                return R.style.Theme1;
            case 1:
                return R.style.Theme2;
            case 2:
                return R.style.Theme3;
        }

        return R.style.Theme1;
    }

    public static int getDialogThemeRId(Context context) {
        switch (getTheme(context)) {
            case 0:
                return R.style.Theme1Dialog;
            case 1:
                return R.style.Theme2Dialog;
            case 2:
                return R.style.Theme3Dialog;
        }

        return R.style.Theme1Dialog;
    }

    public static ContextThemeWrapper getAlertDialogThemedContext(Context context) {
        return new ContextThemeWrapper(context, getDialogThemeRId(context));
    }
}
