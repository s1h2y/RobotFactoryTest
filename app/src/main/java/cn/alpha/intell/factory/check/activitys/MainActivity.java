package cn.alpha.intell.factory.check.activitys;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import cn.alpha.intell.factory.check.R;
import cn.alpha.intell.factory.check.fragments.PowerDialogFragment;
import cn.alpha.intell.factory.check.utils.MyLog;
import cn.alpha.intell.factory.check.utils.Result;

public class MainActivity extends Activity {

    private LinearLayout mTestResult;
    private Button mPower;
    Button mStart;
    private ImageView mResultImage;
    private TextView mResultText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        checkResult();
    }

    private void checkResult() {
        if (Result.isChecked(this)) {
            if (Result.isOK(this)) {
                showResultOK();
            } else {
                showResultFail();
            }
        } else {
            mTestResult.setVisibility(View.INVISIBLE);
        }
    }

    private void initView() {

        mStart = (Button) findViewById(R.id.main_start_test);
        mTestResult = (LinearLayout) findViewById(R.id.main_test_result);
        mPower = (Button) findViewById(R.id.main_power);
        mResultImage = (ImageView) findViewById(R.id.main_test_result_image);
        mResultText = (TextView) findViewById(R.id.main_test_result_text);
        mStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyLog.d("clear last result!!");
                Result.clear();
                startActivity(new Intent(MainActivity.this, TestActivity.class));
            }
        });
        mTestResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ResultActivity.class));
            }
        });

        mPower = (Button) findViewById(R.id.main_power);
        mPower.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPowerDialog();
            }
        });
        mPower.setVisibility(View.INVISIBLE);
    }

    private void showPowerDialog() {
        new PowerDialogFragment().show(getFragmentManager(), "power");
    }

    private void showResultOK() {
        mResultImage.setImageResource(R.drawable.right);
        mResultText.setText(R.string.main_test_pass);
        mResultText.setTextColor(Color.GREEN);
        mTestResult.setVisibility(View.VISIBLE);
    }

    private void showResultFail() {
        mResultImage.setImageResource(R.drawable.wrong);
        mResultText.setText(R.string.main_test_fail);
        mResultText.setTextColor(Color.RED);
        mTestResult.setVisibility(View.VISIBLE);
    }

}
