package com.samon.administrator.uclass;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

public class ChapterActivity extends AppCompatActivity {

    public DrawerLayout drawerLayout;
    public SwipeRefreshLayout swipeRefreshLayout;
    private VideoView videoView;
    private TextView chaptertitleText;
    private Button chapterbackButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chapter);
        Intent intent = getIntent();

        //网络视频
        String videoUrl1= intent.getStringExtra("selectedChapterName");
        String videoUrl3=videoUrl1.substring(32);
        //Log.d("xsy6", "onCreate: "+videoUrl3);
        Uri uri = Uri.parse( videoUrl1 );

        videoView = (VideoView)this.findViewById(R.id.mVideoView );
        chaptertitleText = (TextView) this.findViewById(R.id.chapter_title);
        chapterbackButton = (Button) this.findViewById(R.id.chapter_back_button);
        drawerLayout = (DrawerLayout) this.findViewById(R.id.drawer_layout);

        chaptertitleText.setText(videoUrl3);

        chapterbackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //drawerLayout.openDrawer(GravityCompat.START);
                Intent intent = new Intent(ChapterActivity.this,Secondctivity.class);
                startActivity(intent);
                finish();

            }
        });

        //设置视频控制器
        videoView.setMediaController(new MediaController(this));

        //播放完成回调
        videoView.setOnCompletionListener( new MyPlayerOnCompletionListener());

        //设置视频路径
        videoView.setVideoURI(uri);

        //开始播放视频
        videoView.start();
    }

    private class MyPlayerOnCompletionListener implements MediaPlayer.OnCompletionListener {
        @Override
        public void onCompletion(MediaPlayer mp) {
            Toast.makeText( ChapterActivity.this, "播放完成了", Toast.LENGTH_SHORT).show();

        }
    }
}
