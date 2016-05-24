package com.alpha.smart.factorytest.fragments;

import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;

import com.alpha.smart.factorytest.R;

import java.lang.reflect.Method;

/**
 * Created by shy on 16-5-19.
 */
public class PowerDialogFragment extends DialogFragment {

    private Button mShutdown, mReboot, mCancel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        View root = inflater.inflate(R.layout.power_dialog, container);
        initView(root);
        return root;
    }

    private void initView(View root) {
        mShutdown = (Button) root.findViewById(R.id.shutdown);
        mReboot = (Button) root.findViewById(R.id.reboot);
        mCancel = (Button) root.findViewById(R.id.cancel);
        mShutdown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        mReboot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        mCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }
}




