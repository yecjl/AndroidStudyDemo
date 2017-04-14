package com.study.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 功能：声音
 * Created by danke on 2017/4/14.
 */

public class SoundFragment extends Fragment {
    @Bind(R.id.tv_show_activity_content)
    TextView tvActivityContent;
    private EditText etName;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragement_sound, null);
        ButterKnife.bind(this, view);
        Activity activity = getActivity();
        etName = (EditText) activity.findViewById(R.id.et_name);
        etName.addTextChangedListener(mTextWatcher);
        tvActivityContent.setText(etName.getText());
        return view;
    }

    TextWatcher mTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            tvActivityContent.setText(s.toString());
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
        etName.removeTextChangedListener(mTextWatcher);
    }
}
