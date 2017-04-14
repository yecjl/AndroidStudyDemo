package com.study.fragmentlivecycle;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends FragmentActivity {

    @Bind(R.id.fl_container)
    FrameLayout flContainer;
    @Bind(R.id.btn_fragment01)
    Button btnFragment01;
    @Bind(R.id.btn_fragment02)
    Button btnFragment02;
    private FragmentManager fm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        fm = getSupportFragmentManager();
    }

    @OnClick({R.id.btn_fragment01, R.id.btn_fragment02})
    public void onClick(View view) {
        String tag = null;
        switch (view.getId()) {
            case R.id.btn_fragment01:
                tag = "fragment01";
                Fragment01 fragment01 = null;
                if ((fragment01 = (Fragment01) fm.findFragmentByTag(tag)) == null) {
                    fragment01 = new Fragment01();
                    Log.d("fragmentlivecycle", "Fragment01---new");
                }
                inflater(fragment01, tag);
                break;
            case R.id.btn_fragment02:
                tag = "fragment02";
                Fragment02 fragment02 = null;
                if ((fragment02 = (Fragment02) fm.findFragmentByTag(tag)) == null) {
                    fragment02 = new Fragment02();
                    Log.d("fragmentlivecycle", "Fragment02---new");
                }
                inflater(fragment02, tag);
                break;
        }
    }

    private void inflater(Fragment fragment, String tag) {
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.fl_container, fragment, tag);
        transaction.commit();
    }
}
