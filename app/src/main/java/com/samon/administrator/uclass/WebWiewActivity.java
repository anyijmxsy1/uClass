package com.samon.administrator.uclass;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class WebWiewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_wiew);
        WebView webView = (WebView) findViewById(R.id.web_view);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());//确保网页跳转时目标网页仍在当前webwiev中显示，而不是打开系统浏览器
        Intent intent = getIntent();
        String courseName = intent.getStringExtra("courseName");
        switch (courseName){
            case "物理":
                webView.loadUrl("http://www.geiliweike.com/content/%E7%89%A9%E7%90%86%E9%A2%98%E5%BA%93%EF%BC%88%E6%8F%90%E9%AB%98%E5%9E%8B%EF%BC%89");
                break;
            case "数学":
                webView.loadUrl("http://www.geiliweike.com/content/%E6%95%B0%E5%AD%A6%E9%A2%98%E5%BA%93%EF%BC%88%E9%9A%BE%E5%BA%A6%E7%B3%BB%E6%95%B0%EF%BC%9A%E2%98%85%E2%98%85%E2%98%85%EF%BC%89");
                break;
            case "化学":
                webView.loadUrl("http://www.geiliweike.com/content/%E5%8C%96%E5%AD%A6%E9%A2%98%E5%BA%93%EF%BC%88%E9%9A%BE%E5%BA%A6%E7%B3%BB%E6%95%B0%EF%BC%9A%E2%98%85%E2%98%85%E2%98%85%EF%BC%89");
                break;
            case "语文":
                webView.loadUrl("http://www.geiliweike.com/content/%E9%A2%98%E5%BA%93");
                break;
            case "英语":
                webView.loadUrl("http://www.geiliweike.com/content/%E9%A2%98%E5%BA%93");
                break;
            case "历史":
                webView.loadUrl("http://www.geiliweike.com/content/%E9%A2%98%E5%BA%93");
                break;
            case "政治":
                webView.loadUrl("http://www.geiliweike.com/content/%E9%A2%98%E5%BA%93");
                break;
            default:
                break;
        }
    }
}
