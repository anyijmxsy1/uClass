package com.samon.administrator.uclass;

import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;

import com.baidubce.auth.DefaultBceCredentials;
import com.baidubce.services.bos.BosClient;
import com.baidubce.services.bos.BosClientConfiguration;
import com.baidubce.services.bos.model.BosObjectSummary;
import com.baidubce.services.bos.model.ListObjectsResponse;
import com.bumptech.glide.Glide;
import com.samon.administrator.uclass.util.BosUtil;

public class MainActivity extends AppCompatActivity {

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

        Toolbar toolbar = (Toolbar) findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar!= null){
            actionBar.setDefaultDisplayHomeAsUpEnabled(true);
        }
        collapsingToolbar.setTitle("优课堂");
        String url = "http://img5.imgtn.bdimg.com/it/u=2941079711,2736454066&fm=11&gp=0.jpg" ;
        Glide.with( this ).load(url).placeholder( R.drawable.banner2 ).error( R.drawable.banner2 ).into( mainImageView ) ;

        //Glide.with(this).load().into(mainImageView);






    }
}
