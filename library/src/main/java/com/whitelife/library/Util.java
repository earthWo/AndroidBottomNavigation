package com.whitelife.library;

import android.content.Context;
import android.util.DisplayMetrics;

/**
 * Created by wuzefeng on 16/8/24.
 */

public class Util {

    public static int px2dp(Context context, int px){
        try {
            DisplayMetrics dm = context.getResources().getDisplayMetrics();
            float density=dm.density;
            return (int) (px / density + 0.5f);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static int dp2px(Context context,int dp){
        try {
            DisplayMetrics dm = context.getResources().getDisplayMetrics();
            float density=dm.density;
            return (int) (dp * density + 0.5f);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

}
