package com.study.studentinfosystem_db;

import android.content.DialogInterface;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.study.studentinfosystem_db.dao.StudentDao;
import com.study.studentinfosystem_db.domain.Student;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.et_name)
    EditText etName;
    @Bind(R.id.rb_male)
    RadioButton rbMale;
    @Bind(R.id.rb_female)
    RadioButton rbFemale;
    @Bind(R.id.rg_sex)
    RadioGroup rgSex;
    @Bind(R.id.btn_save)
    Button btSave;
    @Bind(R.id.activity_main)
    LinearLayout activityMain;
    @Bind(R.id.listView)
    ListView listView;
    private StudentDao dao;
    private MyAdapter mAdapter;
    private List<Student> students;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        dao = new StudentDao(this);
        refreshData();
    }

    @OnClick(R.id.btn_save)
    public void save(View view) {
        final String name = etName.getText().toString().trim();
        if (TextUtils.isEmpty(name)) {
            Toast.makeText(this, "学生姓名不能为空！", Toast.LENGTH_SHORT).show();
            return;
        }

        String sex = null;
        switch (rgSex.getCheckedRadioButtonId()) {
            case R.id.rb_male:
                sex = "male";
                break;
            case R.id.rb_female:
                sex = "female";
                break;
        }

        // 判断是否已经存在这个学生
        final String daoSex = dao.find(name);
        if (!TextUtils.isEmpty(daoSex)) {
            final String finalSex = sex;
            new AlertDialog.Builder(this)
                    .setTitle("提醒")
                    .setMessage("已经存在该学生，是否覆盖信息？")
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if (!daoSex.equals(finalSex)) {
                                int result = dao.update(name, finalSex);
                                if (result == 0) {
                                    Toast.makeText(MainActivity.this, "学生信息保存失败！", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                                refreshData();
                            }
                            Toast.makeText(MainActivity.this, "学生信息保存成功！", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .setNegativeButton("取消", null)
                    .show();
        } else {
            long result = dao.add(name, sex);
            if (result == -1) {
                Toast.makeText(this, "学生信息保存失败", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "学生信息保存成功！保存到第" + result + "行", Toast.LENGTH_SHORT).show();
                refreshData();
            }
        }

    }

    /**
     * 获取学生信息，并显示
     */
    private void refreshData() {
        students = dao.findAll();
//        llResult.removeAllViews();
//        for (Student student : students) {
//            TextView tv = new TextView(this);
//            tv.setText(student.toString());
//            llResult.addView(tv);
//        }

        // if adapter not exist, then new it. Opposite refresh the view.
        if (mAdapter == null) {
            mAdapter = new MyAdapter();
            listView.setAdapter(mAdapter);
        } else {
            mAdapter.notifyDataSetChanged();
        }
    }

    public class MyAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return students.size();
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
//                // 1、directly use TextView to show students' data
//                TextView textView = null;
//                if (convertView == null) {
//                    textView = new TextView(MainActivity.this);
//                } else {
//                    textView = (TextView) convertView;
//                }
//                textView.setText(students.get(position).toString());
//                textView.setTextSize(14);
//                Log.d("BaseAdapter", "position: " + position);
//                Log.d("BaseAdapter", "convertView: " + convertView);
//                Log.d("BaseAdapter", "--------------");
//                return textView;

//                // 2、Use code to new LinearLayout include ImageView and TextView
//                LinearLayout linearLayout = null;
//                ImageView imageView = null;
//                TextView textView = null;
//                if (convertView == null) {
//                    linearLayout = new LinearLayout(MainActivity.this);
//                    linearLayout.setOrientation(LinearLayout.HORIZONTAL);
//                    linearLayout.setGravity(Gravity.CENTER_VERTICAL);
//                    imageView = new ImageView(MainActivity.this);
//                    textView = new TextView(MainActivity.this);
//                    linearLayout.addView(imageView, 30, 30);
//                    linearLayout.addView(textView);
//                } else {
//                    linearLayout = (LinearLayout) convertView;
//                    imageView = (ImageView) linearLayout.getChildAt(0);
//                    textView = (TextView) linearLayout.getChildAt(1);
//                }
//
//                Student student = students.get(position);
//                if ("male".equals(student.getSex())) {
//                    imageView.setImageResource(R.mipmap.nan);
//                } else {
//                    imageView.setImageResource(R.mipmap.nv);
//                }
//                textView.setText(student.getName());
//                return linearLayout;

            // 3、Use the inflate to build a view
            View view = null;
            if (convertView == null) {
                view = View.inflate(MainActivity.this, R.layout.item, null);
            } else {
                view = convertView;
            }
            TextView tvName = (TextView) view.findViewById(R.id.tv_name);
            ImageView ivSex = (ImageView) view.findViewById(R.id.iv_sex);
            ImageView ivDelete = (ImageView) view.findViewById(R.id.iv_delete);

            Student student = students.get(position);
            if ("male".equals(student.getSex())) {
                ivSex.setImageResource(R.mipmap.nan);
            } else {
                ivSex.setImageResource(R.mipmap.nv);
            }
            tvName.setText(student.getName());

            // delete operator
            ivDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new AlertDialog.Builder(MainActivity.this)
                            .setTitle("删除提醒")
                            .setMessage("是否删除这条数据？")
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    // delete this student from database
                                    int result = dao.delete(students.get(position).getName());
                                    if (result == 0) {
                                        Toast.makeText(MainActivity.this, "学生信息删除失败", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(MainActivity.this, "学生信息删除成功，删除了" + result + "行", Toast.LENGTH_SHORT).show();
                                        // refresh view
                                        refreshData();
                                    }
                                }
                            })
                            .setNegativeButton("取消", null)
                            .show();
                }
            });
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
