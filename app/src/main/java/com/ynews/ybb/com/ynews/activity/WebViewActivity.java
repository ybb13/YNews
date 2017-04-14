package com.ynews.ybb.com.ynews.activity;

/**
 * Created by Administrator on 2017/4/12 0012.
 */


import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Build.VERSION;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.DownloadListener;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import me.imid.swipebacklayout.lib.app.SwipeBackActivity;

import com.ynews.ybb.com.ynews.R;


public class WebViewActivity extends SwipeBackActivity {
    private WebView mWebView;
    private ImageView ivLeftBack;
    private TextView mTitle;
    private ProgressBar mProgressBar;
    //private ProgressBar mProgress;
    private static final String TAG = "MainActivity----->";
    private WebSettings mWebViewSettings;
    private String mUrl = "http://www.jianshu.com/u/d36586119d8c";

    public WebViewActivity() {
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_webview);
        this.mUrl = this.getIntent().getStringExtra("url");
        this.initView();
        this.initWebView();
        this.initWebSettings();
        this.initWebViewClient();
        this.initWebChromeClient();
    }

    public static void startUrl(Context context, String url) {
        Intent intent = new Intent(context, WebViewActivity.class);
        intent.putExtra("url", url);
        context.startActivity(intent);
    }

    private void initView() {
        this.mWebView = (WebView) this.findViewById(R.id.web_view);
        this.ivLeftBack = (ImageView) this.findViewById(R.id.btn_left_back);
        this.mTitle = (TextView) this.findViewById(R.id.tv_title);
        this.mProgressBar = (ProgressBar) this.findViewById(R.id.progressbar);
        // this.mProgress = (ProgressBar)this.findViewById(id.pb_progress);
        this.ivLeftBack.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                WebViewActivity.this.finish();
            }
        });
    }

    private void initWebSettings() {
        mWebViewSettings.setJavaScriptEnabled(true);
        mWebViewSettings.setDomStorageEnabled(true);
        mWebViewSettings.setDatabaseEnabled(true);
        mWebViewSettings.setDefaultTextEncodingName("utf-8");
        mWebViewSettings.setUseWideViewPort(false);
        mWebViewSettings.setSupportZoom(true);
        mWebViewSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        mWebViewSettings.setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN);
        mWebViewSettings.setSupportMultipleWindows(true);
        mWebViewSettings.setAllowFileAccess(true);
        mWebViewSettings.setNeedInitialFocus(true);
        mWebViewSettings.setBuiltInZoomControls(true);
        mWebViewSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        mWebViewSettings.setLoadWithOverviewMode(true);
    }

    private void initWebView() {
        mWebView.loadUrl(mUrl);
        mWebViewSettings = mWebView.getSettings();
        if (VERSION.SDK_INT >= 19) {
            mWebViewSettings.setLoadsImagesAutomatically(true);
        } else {
            mWebViewSettings.setLoadsImagesAutomatically(false);
        }

        //this.mWebView.setDownloadListener(new WebViewActivity.MyDownloadListener());
    }

    private void initWebChromeClient() {
        this.mWebView.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView view, int newProgress) {
                WebViewActivity.this.mProgressBar.setProgress(newProgress);
            }
        });
    }

    private void initWebViewClient() {
        this.mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            public WebResourceResponse shouldInterceptRequest(WebView view, String url) {
                Object response = null;
                if (url.contains("logo")) {
                    ;
                }

                return (WebResourceResponse) response;
            }

            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                WebViewActivity.this.mTitle.setText("正在加载中...");
                WebViewActivity.this.mProgressBar.setVisibility(View.VISIBLE);
               // WebViewActivity.this.mProgress.setVisibility(0);
            }

            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                WebViewActivity.this.mProgressBar.setVisibility(View.GONE);
                //WebViewActivity.this.mProgress.setVisibility(8);
                WebViewActivity.this.mTitle.setText(view.getTitle());
                if (!WebViewActivity.this.mWebViewSettings.getLoadsImagesAutomatically()) {
                    WebViewActivity.this.mWebViewSettings.setLoadsImagesAutomatically(true);
                }

            }

            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
                Toast.makeText(WebViewActivity.this, "出错了", Toast.LENGTH_LONG).show();
                Log.i("MainActivity----->", "onReceivedError: ");
            }
        });
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == 4 && this.mWebView.canGoBack()) {
            this.mWebView.goBack();
            return true;
        } else {
            return super.onKeyDown(keyCode, event);
        }
    }

    private class MyDownloadListener implements DownloadListener {
        private MyDownloadListener() {
        }

        public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype, long contentLength) {
            Uri uri = Uri.parse(url);
            Intent intent = new Intent("android.intent.action.VIEW", uri);
            WebViewActivity.this.startActivity(intent);
        }
    }
}
