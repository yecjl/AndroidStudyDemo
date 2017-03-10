package com.study.news;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.image.SmartImageView;
import com.study.news.domain.NewsItem;
import com.study.news.service.NewInfoService;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    private static final int SUCCESS = 1;
    private static final int ERROR = 2;
    @Bind(R.id.listView)
    ListView listView;
    @Bind(R.id.ll_loading)
    LinearLayout llLoading;
    private List<NewsItem> newsInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                    newsInfo = NewInfoService.getNewsInfo(getResources().getString(R.string.path));
                    Message msg = Message.obtain();
                    msg.what = SUCCESS;
                    handler.sendMessage(msg);
                } catch (Exception e) {
                    e.printStackTrace();
                    Message msg = Message.obtain();
                    msg.what = ERROR;
                    handler.sendMessage(msg);
                }
            }
        }).start();
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            llLoading.setVisibility(View.GONE);
            switch (msg.what) {
                case SUCCESS:
                    listView.setAdapter(new MyAdapter());
                    break;
                case ERROR:
                    Toast.makeText(MainActivity.this, "数据加载失败", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    class MyAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            if (newsInfo == null || newsInfo.size() <= 0) {
                return 0;
            }
            return newsInfo.size();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = null;
            if (convertView == null) {
                view = View.inflate(MainActivity.this, R.layout.item_listview, null);
            } else {
                view = convertView;
            }

            SmartImageView ivImg = (SmartImageView) view.findViewById(R.id.iv_img);
            TextView tvTitle = (TextView) view.findViewById(R.id.tv_title);
            TextView tvDesc = (TextView) view.findViewById(R.id.tv_desc);
            TextView tvComment = (TextView) view.findViewById(R.id.tv_comment);

            NewsItem newsItem = newsInfo.get(position);
            ivImg.setImageUrl(newsItem.getImageUrl());
            tvTitle.setText(newsItem.getTitle());
            tvDesc.setText(newsItem.getDescription());
            tvComment.setText("评论数：" + newsItem.getComments() + "条");
            return view;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }
    }

}
