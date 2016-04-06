package me.aj.ablum.utils;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

/**
 * 设备相关的工具类
 */
public final class DeviceUtils {

    private static int screenWidth = -1;
    private static int screenHeight = -1;

    /**
     * 获取屏幕分辨率宽度
     */
    public static int getScreenWidth(Context context) {
        if (screenWidth == -1) {
            DisplayMetrics dm = new DisplayMetrics();
            WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
            wm.getDefaultDisplay().getMetrics(dm);
            screenWidth = dm.widthPixels;
        }
        return screenWidth;
    }

    /**
     * 获取屏幕分辨率宽度
     */
    public static int getScreenHeight(Context context) {
        if (screenHeight == -1) {
            DisplayMetrics dm = new DisplayMetrics();
            WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
            wm.getDefaultDisplay().getMetrics(dm);
            screenHeight = dm.heightPixels;
        }
        return screenHeight;
    }

}
