package com.alpha.smart.factorytest.fragments;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.TextView;

import com.alpha.smart.factorytest.R;
import com.alpha.smart.factorytest.utils.Constant;
import com.alpha.smart.factorytest.utils.Result;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link LightnessFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link LightnessFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LightnessFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private CheckBox mCheck;
    private SeekBar mSeekbar;
    private TextView mCur;
    private Context mContext;

    public LightnessFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LightnessFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LightnessFragment newInstance(String param1, String param2) {
        LightnessFragment fragment = new LightnessFragment();
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
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_lightness, container, false);
        initView(root);
        return root;
    }

    private void initView(View root) {
        mCheck = (CheckBox)root.findViewById(R.id.lightness_check_box);
        mSeekbar = (SeekBar)root.findViewById(R.id.seekbar);
        mCur = (TextView)root.findViewById(R.id.current);

        mCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    Result.failed(Constant.LIGHTNESS);
                } else {
                    Result.passed(Constant.LIGHTNESS);
                }
            }
        });

        mSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                changeAppBrightness(mContext, progress);
                changeCurText(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        mCur.setText(getString(R.string.cur_lightness) + Constant.DEFAULT_LIGHTNESS);
        mSeekbar.setProgress((int)(Constant.DEFAULT_LIGHTNESS / 100f * mSeekbar.getMax()));
        checkResult();
    }

    private void checkResult() {
        boolean checked = Constant.FAILED.equals(Result.get(Constant.LIGHTNESS)) ? true : false;
        mCheck.setChecked(checked);
    }

    private void changeCurText(int cur) {
        mCur.setText(getString(R.string.cur_lightness) +  (int)(cur / 255f * 100));
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

    public void changeAppBrightness(Context context, int brightness) {
        Window window = ((Activity) context).getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        if (brightness == -1) {
            lp.screenBrightness = WindowManager.LayoutParams.BRIGHTNESS_OVERRIDE_NONE;
        } else {
            lp.screenBrightness = (brightness <= 0 ? 1 : brightness) / 255f;
        }
        window.setAttributes(lp);
    }

    public int getScreenBrightness(Context activity) {
        int value = 0;
        ContentResolver cr = activity.getContentResolver();
        try {
            value = (int)(Settings.System.getInt(cr, Settings.System.SCREEN_BRIGHTNESS) / 255f * 100);
        } catch (Settings.SettingNotFoundException e) {

        }
        return value;
    }
}
