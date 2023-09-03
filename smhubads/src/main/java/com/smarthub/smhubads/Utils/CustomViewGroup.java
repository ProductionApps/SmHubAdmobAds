package com.smarthub.smhubads.Utils;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import androidx.annotation.Nullable;

public class CustomViewGroup extends FrameLayout {
    public CustomViewGroup(Context context) {
        super(context);
    }

    public CustomViewGroup(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomViewGroup(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void getLocationInWindow(int[] outLocation) {
        if (outLocation!=null && outLocation.length>0)
            super.getLocationInWindow(outLocation);
    }
}
