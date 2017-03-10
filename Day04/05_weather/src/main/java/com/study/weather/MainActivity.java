package com.study.weather;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Xml;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.study.weather.Util.StreamUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.R.attr.data;

public class MainActivity extends AppCompatActivity {

    private static final int SUCCESS = 1;
    private static final int ERROR = 2;
    @Bind(R.id.et_location)
    EditText etLocation;
    @Bind(R.id.btn_search)
    Button btnSearch;
    @Bind(R.id.tv_wendu)
    TextView tvWendu;
    @Bind(R.id.tv_ganmao)
    TextView tvGanmao;
    @Bind(R.id.listView)
    ListView listView;

    private List<WeatherBean> mWeather;
    private WeatherAdapter mAdapter;

    private ProgressDialog mPd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mWeather = new ArrayList<>();
        mAdapter = new WeatherAdapter(this);
        listView.setAdapter(mAdapter);
        mAdapter.setList(mWeather);
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            mPd.dismiss();

            switch (msg.what) {
                case SUCCESS:
                    String result = (String) msg.obj;
                    try {
                        JSONObject jsonObject = new JSONObject(result);
                        String desc = jsonObject.getString("desc");
                        if ("OK".equals(desc)) {
                            mWeather.clear();

                            JSONObject data = jsonObject.getJSONObject("data");
                            tvWendu.setText("温度：" + data.getString("wendu"));
                            tvGanmao.setText("提示：" + data.getString("ganmao"));

                            // 前天
                            JSONObject yesterday = data.getJSONObject("yesterday");
                            WeatherBean weatherYesterday = new WeatherBean();
                            weatherYesterday.setDate(yesterday.getString("date"));
                            weatherYesterday.setFengli(yesterday.getString("fl"));
                            weatherYesterday.setFengxiang(yesterday.getString("fx"));
                            weatherYesterday.setHigh(yesterday.getString("high"));
                            weatherYesterday.setLow(yesterday.getString("low"));
                            weatherYesterday.setType(yesterday.getString("type"));
                            weatherYesterday.setTypeDate(WeatherBean.TypeDate.YESTERDAY);
                            mWeather.add(weatherYesterday);

                            // 当天 + 未来4天
                            JSONArray forecast = data.getJSONArray("forecast");
                            for (int i = 0; i < forecast.length(); i++) {
                                JSONObject weather = (JSONObject) forecast.get(i);
                                WeatherBean weatherForecast = new WeatherBean();
                                weatherForecast.setDate(weather.getString("date"));
                                weatherForecast.setFengli(weather.getString("fengli"));
                                weatherForecast.setFengxiang(weather.getString("fengxiang"));
                                weatherForecast.setHigh(weather.getString("high"));
                                weatherForecast.setLow(weather.getString("low"));
                                weatherForecast.setType(weather.getString("type"));
                                if (i == 0) {
                                    weatherYesterday.setTypeDate(WeatherBean.TypeDate.TODAY);
                                } else {
                                    weatherYesterday.setTypeDate(WeatherBean.TypeDate.FUTURE);
                                }
                                mWeather.add(weatherForecast);
                            }

                            mAdapter.setList(mWeather);

                        } else {
                            Toast.makeText(MainActivity.this, "数据解析失败", Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(MainActivity.this, "数据解析失败", Toast.LENGTH_SHORT).show();
                    }
                    break;
                case ERROR:
                    Toast.makeText(MainActivity.this, "数据加载失败", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    @OnClick(R.id.btn_search)
    public void onClick() {
        final String location = etLocation.getText().toString();
        if (TextUtils.isEmpty(location)) {
            Toast.makeText(this, "请输入正确的地址", Toast.LENGTH_SHORT).show();
            return;
        }

        if (mPd == null) {
            mPd = new ProgressDialog(this);
        }
        mPd.setMessage("正在加载中");
        mPd.show();

        new Thread(new Runnable() {
            @Override
            public void run() {
                InputStream is = null;
                try {
                    URL url = new URL("http://wthrcdn.etouch.cn/weather_mini?city=" + URLEncoder.encode(location, "utf-8"));
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("GET");
                    conn.setConnectTimeout(5000);
                    if (conn.getResponseCode() == 200) {
                        is = conn.getInputStream();
                        String result = StreamUtil.readStream(is);
                        Message msg = Message.obtain();
                        msg.what = SUCCESS;
                        msg.obj = result;
                        handler.sendMessage(msg);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    Message msg = Message.obtain();
                    msg.what = ERROR;
                    handler.sendMessage(msg);
                } finally {
                    if (is != null) {
                        try {
                            is.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else {
                        Message msg = Message.obtain();
                        msg.what = ERROR;
                        handler.sendMessage(msg);
                    }
                }
            }
        }).start();
    }
}
