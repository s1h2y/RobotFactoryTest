package cn.alpha.intell.factory.check.fragments;

import android.app.Activity;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import cn.alpha.intell.factory.check.R;


/**
 * Created by gs on 2016/5/21.
 */
public class BackDialogFragment extends DialogFragment {

    private TextView mYes, mNo;
    private Activity mContext;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mContext = getActivity();
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        View root = inflater.inflate(R.layout.back_dialog, container);
        initView(root);
        return root;
    }

    private void initView(View root) {
        mYes = (TextView) root.findViewById(R.id.confirm);
        mNo = (TextView) root.findViewById(R.id.cancel);
        mYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContext.finish();
            }
        });
        mNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }
}
