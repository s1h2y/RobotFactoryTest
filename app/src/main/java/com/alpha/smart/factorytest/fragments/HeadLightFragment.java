package com.alpha.smart.factorytest.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.alpha.smart.factorytest.R;
import com.alpha.smart.factorytest.activitys.HeadLightActivity;
import com.alpha.smart.factorytest.utils.Constant;
import com.alpha.smart.factorytest.utils.Result;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link HeadLightFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link HeadLightFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HeadLightFragment extends Fragment implements CompoundButton.OnCheckedChangeListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private Button mBtn;
    private Context mContext;
    private CheckBox mCheckOrigin;
    private CheckBox mCheckRed;
    private CheckBox mCheckGreen;

    public HeadLightFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HeadLightFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HeadLightFragment newInstance(String param1, String param2) {
        HeadLightFragment fragment = new HeadLightFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        mContext = getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_head_light, container, false);
        initView(root);
        return root;
    }

    private void initView(View root) {
        mBtn = (Button)root.findViewById(R.id.button);
        mBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mContext, HeadLightActivity.class));
            }
        });
        mCheckOrigin = (CheckBox)root.findViewById(R.id.origin);
        mCheckRed = (CheckBox)root.findViewById(R.id.red);
        mCheckGreen = (CheckBox)root.findViewById(R.id.red);
        mCheckOrigin.setOnCheckedChangeListener(this);
        mCheckRed.setOnCheckedChangeListener(this);
        mCheckGreen.setOnCheckedChangeListener(this);
        checkResult();
    }

    private void checkResult() {
        boolean checked = Constant.FAILED.equals(Result.get(Constant.HEAD_LIGHT_ORIGIN)) ? true : false;
        mCheckOrigin.setChecked(checked);
        checked = Constant.FAILED.equals(Result.get(Constant.HEAD_LIGHT_GREEN)) ? true : false;
        mCheckRed.setChecked(checked);
        checked = Constant.FAILED.equals(Result.get(Constant.HEAD_LIGHT_RED)) ? true : false;
        mCheckGreen.setChecked(checked);
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked) {
            switch (buttonView.getId()) {
                case R.id.origin:
                    Result.failed(Constant.HEAD_LIGHT_ORIGIN);
                    break;
                case R.id.red:
                    Result.failed(Constant.HEAD_LIGHT_RED);
                    break;
                case R.id.green:
                    Result.failed(Constant.HEAD_LIGHT_GREEN);
                    break;
            }
        } else {
            switch (buttonView.getId()) {
                case R.id.origin:
                    Result.passed(Constant.HEAD_LIGHT_ORIGIN);
                    break;
                case R.id.red:
                    Result.passed(Constant.HEAD_LIGHT_RED);
                    break;
                case R.id.green:
                    Result.passed(Constant.HEAD_LIGHT_GREEN);
                    break;
            }
        }
    }


    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
