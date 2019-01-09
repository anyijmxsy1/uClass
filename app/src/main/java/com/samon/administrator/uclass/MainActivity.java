package com.samon.administrator.uclass;

import android.graphics.Color;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baidubce.auth.DefaultBceCredentials;
import com.baidubce.services.bos.BosClient;
import com.baidubce.services.bos.BosClientConfiguration;
import com.baidubce.services.bos.model.BosObjectSummary;
import com.baidubce.services.bos.model.ListObjectsResponse;
import com.bumptech.glide.Glide;
import com.samon.administrator.uclass.util.BosUtil;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{


    //先实例化控件，那我给出自己打的实例化代码
    private TextView tv_main_title;//标题
    private TextView tv_back;//返回按钮
    private RelativeLayout title_bar;
    //来自activity_main.xml
    private RelativeLayout main_body;
    private TextView bottom_bar_text_1;
    private TextView bottom_bar_text_2;
    private TextView bottom_bar_text_3;
    private ImageView bottom_bar_image_1;
    private ImageView bottom_bar_image_2;
    private ImageView bottom_bar_image_3;

    ...
    private LinearLayout main_bottom_bar;
    private RelativeLayout bottom_bar_1_btn;
    private RelativeLayout bottom_bar_2_btn;
    private RelativeLayout bottom_bar_3_btn;


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bottom_bar_1_btn:
                setSelectStatus(0);
                break;
            case R.id.bottom_bar_2_btn:
                setSelectStatus(1);
                break;
            case R.id.bottom_bar_3_btn:
                setSelectStatus(2);
                break;
        }
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CollapsingToolbarLayout collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        ImageView mainImageView = (ImageView) findViewById(R.id.main_image_view);
        /*
            启动Toolbar标题栏
        */
        Toolbar toolbar = (Toolbar) findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar!= null){
            actionBar.setDefaultDisplayHomeAsUpEnabled(true);
        }
        collapsingToolbar.setTitle(".");
        String url = "http://img5.imgtn.bdimg.com/it/u=2941079711,2736454066&fm=11&gp=0.jpg" ;
        Glide.with( this ).load(url).placeholder( R.drawable.banner2 ).error( R.drawable.banner2 ).into( mainImageView ) ;

        initView();
        setSelectStatus(int index);









    }

    private void initView() {
        //底部导航栏
        main_body=findViewById(R.id.main_body);
        bottom_bar_text_1=findViewById(R.id.bottom_bar_text_1);
        bottom_bar_image_1=findViewById(R.id.bottom_bar_image_1);
        ...
        //包含底部 android:id="@+id/main_bottom_bar"
        main_bottom_bar=findViewById(R.id.main_bottom_bar);
        //private RelativeLayout bottom_bar_1_btn;
        bottom_bar_1_btn=findViewById(R.id.bottom_bar_1_btn);
        bottom_bar_2_btn=findViewById(R.id.bottom_bar_2_btn);
        bottom_bar_3_btn=findViewById(R.id.bottom_bar_3_btn);

        //添加点击事件
        bottom_bar_1_btn.setOnClickListener(this);
        bottom_bar_1_btn.setOnClickListener(this);
        bottom_bar_1_btn.setOnClickListener(this);


        bottom_bar_1_btn.setOnClickListener(this);

    }

    private void setSelectStatus(int index) {
        switch (index){
            case 0:
                //图片点击选择变换图片，颜色的改变，其他变为原来的颜色，并保持原有的图片
                bottom_bar_image_1.setImageResource(R.drawable.find2);
                bottom_bar_text_1.setTextColor(Color.parseColor("#0097F7"));
                //其他的文本颜色不变
                bottom_bar_text_2.setTextColor(Color.parseColor("#666666"));
                bottom_bar_text_3.setTextColor(Color.parseColor("#666666"));
                //图片也不变
                bottom_bar_image_2.setImageResource(R.drawable.course);
                bottom_bar_image_3.setImageResource(R.drawable.personal);
                break;
            case 1://同理如上
             ... //图片点击选择变换图片，颜色的改变，其他变为原来的颜色，并保持原有的图片
                bottom_bar_image_1.setImageResource(R.drawable.find);
                bottom_bar_text_1.setTextColor(Color.parseColor("#666666"));
                //其他的文本颜色不变
                bottom_bar_text_2.setTextColor(Color.parseColor("#0097F7"));
                bottom_bar_text_3.setTextColor(Color.parseColor("#666666"));
                //图片也不变
                bottom_bar_image_2.setImageResource(R.drawable.course2);
                bottom_bar_image_3.setImageResource(R.drawable.personal);
                break;
            case 2://同理如上
             ... //图片点击选择变换图片，颜色的改变，其他变为原来的颜色，并保持原有的图片
                bottom_bar_image_1.setImageResource(R.drawable.find);
                bottom_bar_text_1.setTextColor(Color.parseColor("#666666"));
                //其他的文本颜色不变
                bottom_bar_text_2.setTextColor(Color.parseColor("#666666"));
                bottom_bar_text_3.setTextColor(Color.parseColor("#0097F7"));
                //图片也不变
                bottom_bar_image_2.setImageResource(R.drawable.course);
                bottom_bar_image_3.setImageResource(R.drawable.personal3);
                break;

        }
    }



}
