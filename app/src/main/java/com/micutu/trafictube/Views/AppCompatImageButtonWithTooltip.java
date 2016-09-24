package com.micutu.trafictube.Views;

import android.content.Context;
import android.support.v7.widget.AppCompatImageButton;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Toast;

public class AppCompatImageButtonWithTooltip extends AppCompatImageButton {
    public AppCompatImageButtonWithTooltip(Context context) {
        super(context);
        init(context);
    }

    public AppCompatImageButtonWithTooltip(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public AppCompatImageButtonWithTooltip(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public void init(final Context context) {
        this.setOnLongClickListener(new OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Toast.makeText(context, view.getContentDescription(), Toast.LENGTH_SHORT).show();
                return false;
            }
        });
    }
}
