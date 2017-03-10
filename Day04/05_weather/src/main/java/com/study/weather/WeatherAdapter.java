package com.study.weather;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * 功能：
 * Created by danke on 2017/3/7.
 */
public class WeatherAdapter extends BaseAdapter {
    private Context mContext;
    private List<WeatherBean> mWeatherList;

    public WeatherAdapter(Context context) {
        this.mContext = context;
    }

    public void setList(List<WeatherBean> weatherList) {
        this.mWeatherList = weatherList;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        if (mWeatherList == null || mWeatherList.size() <= 0) {
            return 0;
        }
        return mWeatherList.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = null;
        if (convertView == null) {
            view = View.inflate(mContext, R.layout.item_weather, null);
        } else {
            view = convertView;
        }

        TextView tvDate = (TextView) view.findViewById(R.id.tv_date);
        TextView tvWeek = (TextView) view.findViewById(R.id.tv_week);
        TextView tvDegree = (TextView) view.findViewById(R.id.tv_degree);
        TextView tvFengxiang = (TextView) view.findViewById(R.id.tv_fengxiang);
        TextView tvFengli = (TextView) view.findViewById(R.id.tv_fengli);
        ImageView ivWeather = (ImageView) view.findViewById(R.id.iv_weather);

        if (mWeatherList != null && mWeatherList.size() > 0) {
            WeatherBean weather = mWeatherList.get(position);
            String[] date = weather.getDate().split("星期");
            tvDate.setText(date[0]);
            String week;
            switch (date[1]) {
                case "一":
                    week = "Mon.";
                    break;
                case "二":
                    week = "Tues.";
                    break;
                case "三":
                    week = "Wed.";
                    break;
                case "四":
                    week = "Thur.";
                    break;
                case "五":
                    week = "Fri.";
                    break;
                case "六":
                    week = "Sat.";
                    break;
                case "日":
                    week = "Sun.";
                    break;
                default:
                    week = "";
                    break;
            }
            tvWeek.setText(week);
            String low = weather.getLow().split(" ")[1];
            String high = weather.getHigh().split(" ")[1];
            tvDegree.setText(low + "~" + high);
            tvFengxiang.setText(weather.getFengxiang());
            tvFengli.setText(weather.getFengli());
            switch(weather.getType()) {
                case "多云":
                    ivWeather.setImageResource(R.mipmap.cloud_sun);
                    break;
                case "阴":
                    ivWeather.setImageResource(R.mipmap.cloud);
                    break;
                case "晴":
                    ivWeather.setImageResource(R.mipmap.sun);
                    break;
                case "阵雨":
                    ivWeather.setImageResource(R.mipmap.rain);
                    break;
            }

        }
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
