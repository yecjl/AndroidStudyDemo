package com.study.gps_demo;

import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity_location";
    @Bind(R.id.tv_location)
    TextView tvLocation;
    private LocationManager lm;
    private InputStream is;
    private ModifyOffset modifyOffset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        lm = (LocationManager) getSystemService(LOCATION_SERVICE);

        List<String> allProviders = lm.getAllProviders();
        for (int i = 0; i < allProviders.size(); i++) {
            String provider = allProviders.get(i);
            Log.i(TAG, "provider：" + provider);
        }

        Location location = lm.getLastKnownLocation("gps");
        double latitude = location.getLatitude(); // 维度
        double longitude = location.getLongitude(); // 经度
        Log.i(TAG, "location： " + latitude + "," + longitude);
        tvLocation.setText("【location】\n维度：" + latitude + "\n经度：" + longitude);

        try {
            is = getAssets().open("axisoffset.dat");
            modifyOffset = ModifyOffset.getInstance(is);
            PointDouble pointDouble = modifyOffset.s2c(new PointDouble(longitude, latitude));

            Log.i(TAG, "location chinese： " + pointDouble.y + "," + pointDouble.x);
            tvLocation.setText(tvLocation.getText() + "\n【location chinese】\n维度：" + pointDouble.y + "\n经度：" + pointDouble.x);

        } catch (Exception e) {
            e.printStackTrace();
        }


        lm.requestLocationUpdates("gps", 0, 0, mLocationListener);
    }

    private LocationListener mLocationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            Log.i(TAG, "onLocationChanged");
            double latitude = location.getLatitude(); // 维度
            double longitude = location.getLongitude(); // 经度
            Log.i(TAG, "location： " + latitude + "," + longitude);
            tvLocation.setText("【location】\n维度：" + latitude + "\n经度：" + longitude);

            PointDouble pointDouble = modifyOffset.s2c(new PointDouble(longitude, latitude));

            Log.i(TAG, "location chinese： " + pointDouble.y + "," + pointDouble.x);
            tvLocation.setText(tvLocation.getText() + "【location chinese】\n维度：" + pointDouble.y + "\n经度：" + pointDouble.x);
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            Log.i(TAG, "onStatusChanged");

        }

        @Override
        public void onProviderEnabled(String provider) {
            Log.i(TAG, "onProviderEnabled");

        }

        @Override
        public void onProviderDisabled(String provider) {
            Log.i(TAG, "onProviderDisabled");

        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        lm.removeUpdates(mLocationListener);
        mLocationListener = null;
        try {
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
