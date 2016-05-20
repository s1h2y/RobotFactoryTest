package com.alpha.smart.factorytest.activitys;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.alpha.smart.factorytest.R;
import com.alpha.smart.factorytest.fragments.MyDialogFragment;

public class MainActivity extends Activity {

    private LinearLayout mTestResult;
    private Button mPower;
    Button mStart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        mStart = (Button) findViewById(R.id.main_start_test);
        mTestResult = (LinearLayout) findViewById(R.id.main_test_result);
        mPower = (Button) findViewById(R.id.main_power);
        mStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, TestActivity.class));
            }
        });
        mTestResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, TestActivity.class));
            }
        });

        mPower = (Button) findViewById(R.id.main_power);
        mPower.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPowerDialog();
            }
        });
    }

    private void showPowerDialog() {
        new MyDialogFragment().show(getFragmentManager(), "power");
    }

}
