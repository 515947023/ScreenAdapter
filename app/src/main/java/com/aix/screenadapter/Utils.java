package com.aix.screenadapter;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

public class Utils {

    private static Utils utils;

    //参考宽高
    private static final float STANDARD_WIDTH = 1080;
    private static final float STANDARD_HEIGHT = 1920;

    //这里是屏幕显示的宽高
    private int mDisplayWidth;
    private int mDisplayHeight;
    private Utils(Context context){
        //获取屏幕的宽高
        if (mDisplayHeight == 0 || mDisplayWidth == 0){
            WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
            if (manager != null){
                DisplayMetrics displayMetrics = new DisplayMetrics();
                manager.getDefaultDisplay().getMetrics(displayMetrics);
                if (displayMetrics.widthPixels > displayMetrics.heightPixels){
                    //横屏
                    mDisplayWidth =  displayMetrics.heightPixels;
                    mDisplayHeight =  displayMetrics.widthPixels;
                }else{
                    mDisplayWidth =  displayMetrics.widthPixels;
                    mDisplayHeight =  displayMetrics.heightPixels - getStatusBarHeight(context);
                }
            }
        }
    }

    public int getStatusBarHeight(Context context){
        int resId = context.getResources().getIdentifier("status_bar_height","dimen","android");
        if (resId > 0){
            return context.getResources().getDimensionPixelSize(resId);
        }
        return 0;
    }

    public static Utils getInstance(Context context){
        if (utils == null){
            utils = new Utils(context.getApplicationContext());
        }
        return utils;
    }

    //获取水平方向缩放比例
    public float getHorizontalScale(){
        return mDisplayWidth/STANDARD_WIDTH;
    }

    //获取垂直方向缩放比例
    public float getVerticalScale(){
        return mDisplayHeight/STANDARD_HEIGHT;
    }
}
