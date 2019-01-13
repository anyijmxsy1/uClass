package com.samon.administrator.uclass;

import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Display;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.baidubce.auth.DefaultBceCredentials;
import com.baidubce.services.bos.BosClient;
import com.baidubce.services.bos.BosClientConfiguration;
import com.baidubce.services.bos.model.BosObjectSummary;
import com.baidubce.services.bos.model.ListObjectsResponse;
import com.bumptech.glide.Glide;
import com.samon.administrator.uclass.util.BosUtil;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /*
        标题栏的操作
         */
        Toolbar toolbar = (Toolbar) findViewById(R.id.main_toolbar);
        CollapsingToolbarLayout collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        ImageView imageView = (ImageView) findViewById(R.id.main_image_view);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        Glide.with(this).load(R.drawable.banner2).into(imageView);
        collapsingToolbar.setTitle(" ");

        /*
        内容区的操作
         */
//        TextView courseContentText = (TextView) findViewById(R.id.course_content_text);
//        courseContentText.setText("微课内容");
//        Display display = getWindowManager().getDefaultDisplay();
//        if (display.getWidth() > display.getHeight()) {
//            ChooseAreaFragment chooseAreaFragment = new ChooseAreaFragment();
//            getSupportFragmentManager().beginTransaction().replace(R.id.main_linearLayout, chooseAreaFragment).commit();
//        } else {
//            ChooseAreaFragment chooseAreaFragment = new ChooseAreaFragment();
//            getSupportFragmentManager().beginTransaction().replace(R.id.main_linearLayout, chooseAreaFragment).commit();
//        }

        /*
        底部导航区的操作
         */
        BottomNavigationBar bottomNavigationBar = (BottomNavigationBar) findViewById(R.id.bottom_navigation_bar);

        bottomNavigationBar
                 .setInActiveColor("#929292") // 未选中状态颜色
                .addItem(new BottomNavigationItem(R.drawable.find, "发现"))
                .addItem(new BottomNavigationItem(R.drawable.course, "课程"))
                .addItem(new BottomNavigationItem(R.drawable.personal, "我的"))
                .initialise();//所有的设置需在调用该方法前完成
        bottomNavigationBar.setTabSelectedListener(new BottomNavigationBar.OnTabSelectedListener(){
            @Override
            public void onTabSelected(int position) {
                //未选中->选中
                if (position == 0){
                    Toast.makeText(MainActivity.this, "内容"+position, Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MainActivity.this,Secondctivity.class);
                    startActivity(intent);
                }else if (position == 1){
                    ChooseAreaFragment chooseAreaFragment = new ChooseAreaFragment();
                    getSupportFragmentManager().beginTransaction().replace(R.id.main_linearLayout, chooseAreaFragment).commit();
                }else if (position ==2){
                    Toast.makeText(MainActivity.this, "内容"+position, Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onTabUnselected(int position) {
                //选中->未选中
            }
            @Override
            public void onTabReselected(int position) {
                //选中->选中
            }
         });


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
