package com.example.internetworm;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    final static String url = "https://www.smzdm.com/";
    final static String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Test();
    }

    private Handler handler = new Handler(Looper.getMainLooper()) {
        public void handleMessage(Message msg) {
            Log.i(TAG, "handleMessage: "+ "爬结束");

            switch (msg.what) {
                case 1:
                    Log.i(TAG, "handleMessage: "+ "开始展示数据");
                    ArrayList<Article> articles = (ArrayList<Article>)msg.obj;
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