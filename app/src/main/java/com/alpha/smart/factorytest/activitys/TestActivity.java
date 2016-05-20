package com.alpha.smart.factorytest.activitys;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.alpha.smart.factorytest.R;
import com.alpha.smart.factorytest.utils.Constant;
import com.alpha.smart.factorytest.utils.MyLog;
import com.alpha.smart.factorytest.utils.Result;

import java.util.HashSet;

public class TestActivity extends Activity {

    Context mContext;
    ListView mTestList;
    Button mBack;
    FragmentManager mFragManager;
    private PanelAdapter mAdapter;
    private HashSet<String> mItems;
    private Button mPower;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        mContext = this;
        mItems = new HashSet<String>();
        for (int i = 0; i < Constant.fragments.length; i++) {
            mItems.add(Constant.fragments[i].className);
        }
        mFragManager = getFragmentManager();
        initView();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mItems.isEmpty()) {
            Result.saveResult(this);
        }
    }

    private void showDialog() {
        MyLog.d("not finish");
        new AlertDialog.Builder(this).setTitle(R.string.test_not_finish_title)
                .setMessage(R.string.test_not_finish_alert)
                .setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        MyLog.d("no store data");
                        finish();
                    }
                })
                .setNegativeButton(R.string.cancel, null).show();
    }


    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    public void onBackPressed() {
        if (mItems.isEmpty()) {
            super.onBackPressed();
        } else {
            showDialog();
        }
    }

    private void initView() {
        mTestList = (ListView) findViewById(R.id.test_list_panel);
        mTestList.setAdapter(mAdapter = new PanelAdapter(LayoutInflater.from(this)));

        mTestList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                FragmentTransaction fragmentTransaction = mFragManager.beginTransaction();
                Fragment n = null;
                Class<?> clazz = null;
                try {
                    clazz = mContext.getClassLoader().loadClass(Constant.fragments[position].className);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                try {
                    n = (Fragment) clazz.newInstance();
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                fragmentTransaction.replace(R.id.fragments, n, Constant.fragments[position].tag);
                fragmentTransaction.commit();
                mAdapter.setSelectedItem(position);
                mAdapter.notifyDataSetChanged();
                mItems.remove(Constant.fragments[position].className);
//                mAdapter.notifyDataSetInvalidated();
            }
        });

        mBack = (Button) findViewById(R.id.test_back);
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mItems.isEmpty()) {
                    finish();
                } else {
                    showDialog();
                }
            }
        });
        initFirstFragment();
    }

    private void initFirstFragment() {
        FragmentTransaction fragmentTransaction = mFragManager.beginTransaction();
        Fragment n = null;
        Class<?> clazz = null;
        try {
            clazz = mContext.getClassLoader().loadClass(Constant.fragments[0].className);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            n = (Fragment) clazz.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        fragmentTransaction.replace(R.id.fragments, n, Constant.fragments[0].tag);
        fragmentTransaction.commit();
        mItems.remove(Constant.fragments[0].className);
        mAdapter.setSelectedItem(0);
        mTestList.smoothScrollToPosition(0);
    }

    public class PanelAdapter extends BaseAdapter {

        LayoutInflater mInflater;

        public PanelAdapter(LayoutInflater mInflater) {
            this.mInflater = mInflater;
        }

        @Override
        public int getCount() {
            return Constant.fragments.length;
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (null == convertView) {
                convertView = mInflater.inflate(R.layout.test_item, null);
                holder = new ViewHolder();
                holder.image = (ImageView) convertView.findViewById(R.id.test_item_image);
                holder.text = (TextView) convertView.findViewById(R.id.test_item_text);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            if (mItems.contains(Constant.fragments[position].className)) {
                holder.image.setImageResource(Constant.fragments[position].image);
            } else {
                holder.image.setImageResource(Constant.fragments[position].greyImage);
            }
            holder.text.setText(Constant.fragments[position].title);
            if (position == selectItem) {
                convertView.setBackgroundColor(Color.parseColor("#EFEFEF"));
            } else {
                convertView.setBackgroundColor(Color.TRANSPARENT);
            }
            return convertView;
        }

        class ViewHolder {
            ImageView image;
            TextView text;
        }

        public void setSelectedItem(int pos) {
            selectItem = pos;
        }

        private int selectItem = -1;
    }


}
