package com.ping.bannerdemo.activity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.ping.bannerdemo.R;
import com.ping.bannerdemo.adpater.MyAdapter;
import com.ping.bannerdemo.global.App;
import com.ping.bannerdemo.loader.GlideImageLoader;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.transformer.AccordionTransformer;
import com.youth.banner.transformer.BackgroundToForegroundTransformer;
import com.youth.banner.transformer.CubeInTransformer;
import com.youth.banner.transformer.CubeOutTransformer;
import com.youth.banner.transformer.DefaultTransformer;
import com.youth.banner.transformer.DepthPageTransformer;
import com.youth.banner.transformer.FlipHorizontalTransformer;
import com.youth.banner.transformer.FlipVerticalTransformer;
import com.youth.banner.transformer.ForegroundToBackgroundTransformer;
import com.youth.banner.transformer.RotateDownTransformer;
import com.youth.banner.transformer.RotateUpTransformer;
import com.youth.banner.transformer.ScaleInOutTransformer;
import com.youth.banner.transformer.StackTransformer;
import com.youth.banner.transformer.TabletTransformer;
import com.youth.banner.transformer.ZoomInTransformer;
import com.youth.banner.transformer.ZoomOutSlideTransformer;
import com.youth.banner.transformer.ZoomOutTranformer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements MyAdapter.OnItemClickListener {

    private MyAdapter myAdapter;
    static final int REFRESH_COMPLETE = 0X1112;

    Banner mBanner;
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout mSwipeRefreshLayout;

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case REFRESH_COMPLETE:
                    String[] urls = getResources().getStringArray(R.array.url);
                    List list = Arrays.asList(urls);
                    List arrayList = new ArrayList(list);
                    //把新的图片地址加载到Banner
                    mBanner.update(arrayList);
                    //下拉刷新控件隐藏
                    mSwipeRefreshLayout.setRefreshing(false);
                    break;
            }
        }
    };
    String[] data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        View header = LayoutInflater.from(this).inflate(R.layout.header, null);
        mBanner = (Banner) header.findViewById(R.id.banner);
        mBanner.setLayoutParams(new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, App.H / 4));

        data = getResources().getStringArray(R.array.demo_list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        myAdapter = new MyAdapter(this, data);
        myAdapter.setHeaderView(mBanner);
        myAdapter.setmOnItemClickListener(this);
        mRecyclerView.setAdapter(myAdapter);

        initListener();
        initMap();

        ArrayList<String> titles = new ArrayList<>(Arrays.asList(new String[]{"first title", "second title", "third title", "fourth title"}));
        mBanner.setImages(App.images)
                .setBannerAnimation(ScaleInOutTransformer.class)
                .setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE)
                .setBannerTitles(titles)
                .setImageLoader(new GlideImageLoader())
                .start();
    }

    private void initListener() {
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mHandler.sendEmptyMessageDelayed(REFRESH_COMPLETE, 2000);
            }
        });
    }

    Map<String, Class<? extends ViewPager.PageTransformer>> map = new HashMap<>();
    String[] animation;

    private void initMap() {
        map.put("AccordionTransformer", AccordionTransformer.class);
        map.put("BackgroundToForegroundTransformer", BackgroundToForegroundTransformer.class);
        map.put("CubeInTransformer", CubeInTransformer.class);
        map.put("CubeOutTransformer", CubeOutTransformer.class);
        map.put("DefaultTransformer", DefaultTransformer.class);
        map.put("DepthPageTransformer", DepthPageTransformer.class);
        map.put("FlipHorizontalTransformer", FlipHorizontalTransformer.class);
        map.put("FlipVerticalTransformer", FlipVerticalTransformer.class);
        map.put("ForegroundToBackgroundTransformer", ForegroundToBackgroundTransformer.class);
        map.put("RotateDownTransformer", RotateDownTransformer.class);
        map.put("RotateUpTransformer", RotateUpTransformer.class);
        map.put("ScaleInOutTransformer", ScaleInOutTransformer.class);
        map.put("StackTransformer", StackTransformer.class);
        map.put("TabletTransformer", TabletTransformer.class);
        map.put("ZoomInTransformer", ZoomInTransformer.class);
        map.put("ZoomOutSlideTransformer", ZoomOutSlideTransformer.class);
        map.put("ZoomOutTranformer", ZoomOutTranformer.class);

        animation = new String[map.size()];
        int i = 0;
        for (String key : map.keySet()) {
            animation[i] = key;
            i += 1;
        }
    }

    AlertDialog alertDialog;

    @Override
    public void onItemClick(View view, int position, String str) {
        alertDialog = new AlertDialog.Builder(MainActivity.this)
                .setTitle("动画列表")
                .setItems(animation, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mBanner.setBannerAnimation(map.get(animation[which])).start();
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        alertDialog.dismiss();
                    }
                })
                .create();
        alertDialog.show();
    }
}
