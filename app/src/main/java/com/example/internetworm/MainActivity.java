package com.example.internetworm;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.webkit.JavascriptInterface;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import com.example.internetworm.databinding.ActivityMainBinding;

import java.util.ArrayList;

/**
 * Dmeo说明: 当WebView加载网页时获取该网页中的内容.
 */
public class MainActivity extends AppCompatActivity {

    final static String url = "https://www.smzdm.com/";
    final static String TAG = "MainActivity";

    private ArrayList<Article> articles = new ArrayList<>();
    private ActivityMainBinding binding;
    private GoodsAdapter goodsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(LayoutInflater.from(this));

        setContentView(binding.getRoot());


        Test();
    }

    private Handler handler = new Handler(Looper.getMainLooper()) {
        public void handleMessage(Message msg) {
            Log.i(TAG, "handleMessage: "+ "爬结束");

            switch (msg.what) {
                case 1:
                    Log.i(TAG, "handleMessage: "+ "开始展示数据");
                    articles = (ArrayList<Article>)msg.obj;
                    goodsAdapter = new GoodsAdapter(articles,getApplicationContext());
                    binding.recycleView.setAdapter(goodsAdapter);
                    binding.recycleView.setLayoutManager(new GridLayoutManager(getApplicationContext(),2));
                    Log.i(TAG, "handleMessage:articles.size() "+ articles.size());

                    for (Article item:articles) {
                        Log.i(TAG, "handleMessage: "+ item.toString());
                    }
                    break;
                default:
                    break;
            }
        }
    };



    private void Test(){


        new Thread(){
            public void run(){
                String html = OkHttpUtils.OkGetArt(url);
                ArrayList<Article> articles = GetData.spiderArticle(html);
                //发送信息给handler用于更新UI界面
                Message message = handler.obtainMessage();
                message.what = 1;
                message.obj = articles;
                handler.sendMessage(message);
            }
        }.start();
    }

}
//public class MainActivity extends Activity {
//    private WebView mWebView;
//    String na;
//
//    private static final String url = "https://www.smzdm.com/";
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//        init();
//
////        webView =  new WebView(this);
////        webView.getSettings().setJavaScriptEnabled(true);
////        webView.addJavascriptInterface(new MyJavaScriptInterface(),  "HTMLOUT");
////        webView.setWebViewClient(new WebViewClient(){
////            @Override
////            public  void onPageFinished(WebView view,  String url) {
////                webView.loadUrl("https://ddrk.me/hakozume/?ep=1");
////            }
////        });
//
//    }
//
//    /**
//     * 逻辑处理
//     * @author linzewu
//     */
//    final class InJavaScriptLocalObj {
//
//        @JavascriptInterface
//        public void getSource(String html) {
//
//
//            ArrayList<Article> articles = GetData.spiderArticle(html);
//
//        }
//    }
//
//
//    @SuppressLint("SetJavaScriptEnabled")
//    private void init() {
//        mWebView =  new WebView(this);
//        // 开启JavaScript支持
//        mWebView.getSettings().setJavaScriptEnabled(true);
//        mWebView.addJavascriptInterface(new InJavaScriptLocalObj(), "java_obj");
//        mWebView.loadUrl(url);
//        mWebView.setWebViewClient(new WebViewClient(){
//            @Override
//            public void onPageStarted(WebView view, String url, Bitmap favicon) {
//                super.onPageStarted(view, url, favicon);
//            }
//
//            @Override
//            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
//                super.onReceivedError(view, request, error);
//            }
//
//
//
//            @Override
//            public void onPageFinished(WebView view, String url) {
//                view.loadUrl("javascript:window.java_obj.getSource('<head>'+" +
//                        "document.getElementsByTagName('html')[0].innerHTML+'</head>');");
//                super.onPageFinished(view, url);
//            }
//        });
//
//
//
//    }
//
//
//
//}