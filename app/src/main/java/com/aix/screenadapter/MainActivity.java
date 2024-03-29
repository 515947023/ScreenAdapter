package com.aix.screenadapter;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.view.DisplayCutout;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowInsets;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class MainActivity extends Activity {

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // Density.setDensity(getApplication(),this);
        //1、设置全屏 默认向下移动 空出黑边
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        Window window = getWindow();
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //1、判断手机厂商 2,判断手机是否有刘海 3，设置是否让内容区域延伸进刘海 4，设置控件是否避开刘海区域



        //判断手机是不是刘海
        boolean hasDisplayCutout = hasDisplayCutout(window);
        if (hasDisplayCutout) {
            //2、让内容延伸进刘海
            WindowManager.LayoutParams params = window.getAttributes();
            /**
             * @see #LAYOUT_IN_DISPLAY_CUTOUT_MODE_DEFAULT 全屏模式，内容下移，非全屏不受影响
             * @see #LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES 允许内容去延伸进刘海区
             * @see #LAYOUT_IN_DISPLAY_CUTOUT_MODE_NEVER 不允许内容延伸进刘海区
             */
            params.layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES;

            window.setAttributes(params);

            //3、设置成沉浸式
            int flags = View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;
            int visibility = window.getDecorView().getSystemUiVisibility();
            visibility |= flags; //追加沉浸式设置
            window.getDecorView().setSystemUiVisibility(visibility);

        }
        setContentView(R.layout.activity_main);

        ImageView button = findViewById(R.id.button);
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) button.getLayoutParams();
        layoutParams.topMargin = heightForDisplayCutout();
        button.setLayoutParams(layoutParams);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private boolean hasDisplayCutout(Window window) {
        //判断手机是不是刘海
        DisplayCutout displayCutout;
        View rootView = window.getDecorView();
        WindowInsets insets = rootView.getRootWindowInsets();
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.P && insets != null){
            displayCutout = insets.getDisplayCutout();
            if(displayCutout.getBoundingRects() != null &&displayCutout.getBoundingRects().size()>0 && displayCutout.getSafeInsetTop()>0){
                return true;
            }
        }
        return true;//记得返回false
    }

    //通常情况下，刘海的高就是状态栏的高
    public int heightForDisplayCutout(){
        int resID = getResources().getIdentifier("status_bar_height","dimen","android");
        if (resID>0){
            return getResources().getDimensionPixelSize(resID);
        }

        return 0;
    }
}
