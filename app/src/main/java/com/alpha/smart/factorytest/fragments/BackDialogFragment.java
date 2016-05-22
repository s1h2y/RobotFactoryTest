package com.alpha.smart.factorytest.fragments;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import com.alpha.smart.factorytest.R;


/**
 * Created by gs on 2016/5/21.
 */
public class BackDialogFragment extends DialogFragment {

    private TextView mConfirm, mCancel;
    private Activity mContext;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        View root = inflater.inflate(R.layout.back_dialog, container);
        mContext = getActivity();
        initView(root);
        return root;
    }

    private void initView(View root) {
        mConfirm = (TextView) root.findViewById(R.id.confirm);
        mCancel = (TextView) root.findViewById(R.id.cancel);
        mConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContext.finish();
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
