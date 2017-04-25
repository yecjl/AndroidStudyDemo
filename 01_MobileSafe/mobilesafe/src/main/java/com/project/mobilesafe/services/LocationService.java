package com.project.mobilesafe.services;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.telephony.SmsManager;

import java.util.List;

/**
 * 功能：位置的服务
 * Created by danke on 2017/4/25.
 */

public class LocationService extends Service {

    private LocationManager lm;
    private MyLocationListener mLocationListener;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        lm = (LocationManager) getSystemService(LOCATION_SERVICE);
        List<String> allProviders = lm.getAllProviders();
        if (allProviders != null && allProviders.size() > 0 && allProviders.contains("gps")) {
            mLocationListener = new MyLocationListener();
            lm.requestLocationUpdates("gps", 0, 0, mLocationListener);
        }
    }

    private class MyLocationListener implements LocationListener {

        @Override
        public void onLocationChanged(Location location) {
            double latitude = location.getLatitude();
            double longitude = location.getLongitude();

            SmsManager sm = SmsManager.getDefault();
            String safePhone = getSharedPreferences("config", MODE_PRIVATE).getString("safePhone", null);
            sm.sendTextMessage(safePhone, null, "维度：" + latitude + "\n 经度：" + longitude, null, null);

            // 关闭位置监听器
            lm.removeUpdates(mLocationListener);
            mLocationListener = null;
            // 把自身的服务停止掉
            stopSelf();
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
