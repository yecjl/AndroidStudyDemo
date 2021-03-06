package com.study.fragmentlivecycle;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * 功能：
 * Created by danke on 2017/4/14.
 */

public class Fragment01 extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d("fragmentlivecycle", "Fragment01---onCreateView");
        return inflater.inflate(R.layout.fragment_01, null);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.d("fragmentlivecycle", "Fragment01---onCreate");
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onStart() {
        Log.d("fragmentlivecycle", "Fragment01---onStart");
        super.onStart();
    }

    @Override
    public void onResume() {
        Log.d("fragmentlivecycle", "Fragment01---onResume");
        super.onResume();
    }

    @Override
    public void onPause() {
        Log.d("fragmentlivecycle", "Fragment01---onPause");
        super.onPause();
    }

    @Override
    public void onStop() {
        Log.d("fragmentlivecycle", "Fragment01---onStop");
        super.onStop();
    }

    @Override
    public void onDestroy() {
        Log.d("fragmentlivecycle", "Fragment01---onDestroy");
        super.onDestroy();
    }
}
