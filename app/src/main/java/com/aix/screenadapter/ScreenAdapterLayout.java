package com.aix.screenadapter;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;

/**
 * 自定义像素
 */
public class ScreenAdapterLayout extends RelativeLayout {

    private boolean flag;

    public ScreenAdapterLayout(Context context) {
        super(context);
    }

    public ScreenAdapterLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ScreenAdapterLayout(Context context, AttributeSet attrs, int defStyleAttr) {

        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (!flag) {
            float scaleX = Utils.getInstance(getContext()).getHorizontalScale();
            float scaleY = Utils.getInstance(getContext()).getVerticalScale();
            int count = getChildCount();
            for (int i = 0; i < count; i++) {
                View child = getChildAt(i);
                LayoutParams params = (LayoutParams) child.getLayoutParams();
                params.width = (int) (params.width * scaleX);
                params.height = (int) (params.height * scaleY);
                params.leftMargin *= scaleX;
                params.rightMargin *= scaleX;
                params.topMargin *= scaleY;
                params.bottomMargin *= scaleY;

            }
            flag = true;
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
