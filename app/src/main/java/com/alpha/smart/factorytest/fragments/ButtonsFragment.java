package com.alpha.smart.factorytest.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.alpha.smart.factorytest.R;
import com.alpha.smart.factorytest.utils.Constant;
import com.alpha.smart.factorytest.utils.Result;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ButtonsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ButtonsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ButtonsFragment extends Fragment implements CompoundButton.OnCheckedChangeListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public ButtonsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ButtonsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ButtonsFragment newInstance(String param1, String param2) {
        ButtonsFragment fragment = new ButtonsFragment();
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_buttons, container, false);
        intiView(root);
        return root;
    }

    private void intiView(View root) {
        CheckBox checkUp = (CheckBox)root.findViewById(R.id.volume_up_check_box);
        CheckBox checkDown = (CheckBox)root.findViewById(R.id.volume_down_check_box);
        CheckBox checkRec = (CheckBox)root.findViewById(R.id.record_check_box);
        CheckBox checkBack = (CheckBox)root.findViewById(R.id.back_check_box);
        CheckBox checkHome = (CheckBox)root.findViewById(R.id.home_check_box);
        CheckBox checkPower = (CheckBox)root.findViewById(R.id.power_check_box);
        checkUp.setOnCheckedChangeListener(this);
        checkDown.setOnCheckedChangeListener(this);
        checkRec.setOnCheckedChangeListener(this);
        checkBack.setOnCheckedChangeListener(this);
        checkHome.setOnCheckedChangeListener(this);
        checkPower.setOnCheckedChangeListener(this);
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

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked) {
            switch (buttonView.getId()) {
                case R.id.volume_up_check_box:
                    Result.failed(Constant.BUTTON_VOL_UP);
                    break;
                case R.id.volume_down_check_box:
                    Result.failed(Constant.BUTTON_VOL_DOWN);
                    break;
                case R.id.record_check_box:
                    Result.failed(Constant.BUTTON_REC);
                    break;
                case R.id.back_check_box:
                    Result.failed(Constant.BUTTON_BACK);
                    break;
                case R.id.home_check_box:
                    Result.failed(Constant.BUTTON_HOME);
                    break;
                case R.id.power_check_box:
                    Result.failed(Constant.BUTTON_POWER);
                    break;
            }
        } else {
            switch (buttonView.getId()) {
                case R.id.volume_up_check_box:
                    Result.passed(Constant.BUTTON_VOL_UP);
                    break;
                case R.id.volume_down_check_box:
                    Result.passed(Constant.BUTTON_VOL_DOWN);
                    break;
                case R.id.record_check_box:
                    Result.passed(Constant.BUTTON_REC);
                    break;
                case R.id.back_check_box:
                    Result.passed(Constant.BUTTON_BACK);
                    break;
                case R.id.home_check_box:
                    Result.passed(Constant.BUTTON_HOME);
                    break;
                case R.id.power_check_box:
                    Result.passed(Constant.BUTTON_POWER);
                    break;
            }
        }
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
