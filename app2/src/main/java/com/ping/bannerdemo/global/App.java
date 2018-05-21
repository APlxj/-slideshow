package com.ping.bannerdemo.global;

import android.app.Application;
import android.content.Context;
import android.util.DisplayMetrics;

import com.ping.bannerdemo.R;
import com.ping.bannerdemo.activity.MainActivity;
import com.zxy.recovery.core.Recovery;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Mr.sorrow on 2017/5/3.
 */

public class App extends Application{

    public static int H;
    public static List<?> images=new ArrayList<>();
    public static List<String> titles=new ArrayList<>();

    @Override
    public void onCreate() {
        super.onCreate();

        initBanner();
    }

    private void initBanner() {
        H = getScreenH(this);

        Recovery.getInstance()
                .debug(true)
                .recoverInBackground(false)
                .recoverStack(true)
                .mainPage(MainActivity.class)
                .init(this);

        String[] urls = getResources().getStringArray(R.array.url4);
        String[] tips = getResources().getStringArray(R.array.title);
        List list = Arrays.asList(urls);
        images = new ArrayList<>(list);
        titles= Arrays.asList(tips);
    }

    /**
     * 获取屏幕高度
     * @param aty
     * @return
     */
    public int getScreenH(Context aty){
        DisplayMetrics dm = aty.getResources().getDisplayMetrics();
        return dm.heightPixels;
    }
}
