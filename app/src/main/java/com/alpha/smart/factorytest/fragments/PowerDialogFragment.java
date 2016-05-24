package com.alpha.smart.factorytest.fragments;

import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.alpha.smart.factorytest.R;

/**
 * Created by shy on 16-5-19.
 */
public class PowerDialogFragment extends DialogFragment{

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        View root = inflater.inflate(R.layout.power_dialog, container);
        return root;
    }
}
