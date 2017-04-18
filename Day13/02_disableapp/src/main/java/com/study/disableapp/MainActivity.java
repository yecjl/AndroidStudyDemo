package com.study.disableapp;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.stericson.RootTools.RootTools;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

import static android.R.attr.name;


public class MainActivity extends AppCompatActivity {

    @Bind(R.id.listView)
    ListView listView;
    private List<String> pNameList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        try {
            Runtime.getRuntime().exec("su");
        } catch (Exception e) {
            e.printStackTrace();
        }

        pNameList = new ArrayList<>();
        PackageManager pm = getPackageManager();
        List<PackageInfo> piList = pm.getInstalledPackages(PackageManager.GET_ACTIVITIES);
        for (PackageInfo packageInfo : piList) {
            pNameList.add(packageInfo.packageName);
        }
        listView.setAdapter(mAdapter);
    }

    private BaseAdapter mAdapter = new BaseAdapter() {
        TextView tvName;
        Button btnFreeze;
        Button btnUnfreeze;

        @Override
        public int getCount() {
            return pNameList.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = null;
            if (convertView != null) {
                view = convertView;
            } else {
                view = View.inflate(MainActivity.this, R.layout.item, null);
            }

            tvName = (TextView) view.findViewById(R.id.tv_name);
            btnFreeze = (Button) view.findViewById(R.id.btn_freeze);
            btnUnfreeze = (Button) view.findViewById(R.id.btn_unfreeze);

            final String name = pNameList.get(position);
            tvName.setText(name);
            btnFreeze.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        RootTools.sendShell("pm disable " + name, 30000);
                        Toast.makeText(MainActivity.this, name + "冻结成功！", Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(MainActivity.this, name + "冻结失败！", Toast.LENGTH_SHORT).show();
                    }
                }
            });
            btnUnfreeze.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        RootTools.sendShell("pm enable " + name, 30000);
                        Toast.makeText(MainActivity.this, name + "解冻成功！", Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(MainActivity.this, name + "解冻失败！", Toast.LENGTH_SHORT).show();
                    }
                }
            });
            return view;
        }
    };
}
