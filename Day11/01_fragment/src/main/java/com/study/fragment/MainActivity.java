package com.study.fragment;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.btn_sound)
    Button btnSound;
    @Bind(R.id.btn_storage)
    Button btnStorage;
    @Bind(R.id.btn_show)
    Button btnShow;
    @Bind(R.id.fl_content)
    FrameLayout flContent;
    @Bind(R.id.et_name)
    EditText etName;
    private FragmentManager fm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        fm = getFragmentManager();

        btnSound.performClick();
    }

    @OnClick({R.id.btn_sound, R.id.btn_storage, R.id.btn_show})
    public void onClick(View view) {
        String tag = null;
        switch (view.getId()) {
            case R.id.btn_sound:
                tag = "soundFragment";
                SoundFragment soundFragment = null;
                if (fm.findFragmentByTag(tag) == null) {
                    soundFragment = new SoundFragment();
                } else {
                    soundFragment = (SoundFragment) fm.findFragmentByTag(tag);
                }
                inflater(soundFragment, tag);
                break;
            case R.id.btn_storage:
                tag = "storageFragment";
                StorageFragment storageFragment = null;
                if (fm.findFragmentByTag(tag) == null) {
                    storageFragment = new StorageFragment();
                } else {
                    storageFragment = (StorageFragment) fm.findFragmentByTag(tag);
                }
                inflater(storageFragment, tag);
                break;
            case R.id.btn_show:
                tag = "showFragment";
                ShowFragment showFragment = null;
                if (fm.findFragmentByTag(tag) == null) {
                    showFragment = new ShowFragment();
                } else {
                    showFragment = (ShowFragment) fm.findFragmentByTag(tag);
                }
                inflater(showFragment, tag);
                break;
        }
    }

    private void inflater(Fragment fragment, String tag) {
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.fl_content, fragment, tag);
        transaction.commit();
    }
}
