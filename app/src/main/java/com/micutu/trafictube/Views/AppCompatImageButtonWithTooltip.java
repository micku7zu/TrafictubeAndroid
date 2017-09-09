package com.micutu.trafictube.Views;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.widget.AppCompatImageButton;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

import com.micutu.trafictube.R;
import com.micutu.trafictube.Utils;

import io.github.douglasjunior.androidSimpleTooltip.SimpleTooltip;

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

                int backgroundColor = Utils.getColorFromAttribute(context, R.attr.attrCardTitleTransparentBackgroundColor);

                new SimpleTooltip.Builder(context)
                        .anchorView(view)
                        .text(view.getContentDescription())
                        .gravity(Gravity.TOP)
                        .animated(true)
                        .backgroundColor(backgroundColor)
                        .arrowColor(backgroundColor)
                        .textColor(Utils.getColorFromAttribute(context, R.attr.attrCardTitleTextColor))
                        .build()
                        .show();

                return true;
            }
        });
    }
}
