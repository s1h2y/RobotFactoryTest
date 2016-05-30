package cn.alpha.intell.factory.check.fragments;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import cn.alpha.intell.factory.check.R;
import cn.alpha.intell.factory.check.utils.Constant;
import cn.alpha.intell.factory.check.utils.Result;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link GsensorFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link GsensorFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GsensorFragment extends Fragment implements CompoundButton.OnCheckedChangeListener, SensorEventListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private CheckBox mXCheck, mYCheck, mZCheck;
    private TextView mXValue, mYValue, mZValue;

    private SensorManager mSensorManager;
    private Sensor mSensor;

    public GsensorFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment GsensorFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static GsensorFragment newInstance(String param1, String param2) {
        GsensorFragment fragment = new GsensorFragment();
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
        mSensorManager = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);
        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mSensorManager.registerListener(this, mSensor, SensorManager.SENSOR_DELAY_NORMAL);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_gsensor, container, false);
        initView(root);
        return root;
    }

    private void initView(View root) {
        mXCheck = (CheckBox) root.findViewById(R.id.x_check);
        mYCheck = (CheckBox) root.findViewById(R.id.y_check);
        mZCheck = (CheckBox) root.findViewById(R.id.z_check);
        mXValue = (TextView) root.findViewById(R.id.x_value);
        mYValue = (TextView) root.findViewById(R.id.y_value);
        mZValue = (TextView) root.findViewById(R.id.z_value);
        mXCheck.setOnCheckedChangeListener(this);
        mYCheck.setOnCheckedChangeListener(this);
        mZCheck.setOnCheckedChangeListener(this);
        checkResult();
    }

    private void checkResult() {
        boolean checked = Constant.FAILED.equals(Result.get(Constant.GSENSOR_X)) ? true : false;
        mXCheck.setChecked(checked);
        checked = Constant.FAILED.equals(Result.get(Constant.GSENSOR_Y)) ? true : false;
        mYCheck.setChecked(checked);
        checked = Constant.FAILED.equals(Result.get(Constant.GSENSOR_Z)) ? true : false;
        mZCheck.setChecked(checked);
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked) {
            switch (buttonView.getId()) {
                case R.id.x_check:
                    Result.failed(Constant.GSENSOR_X);
                    break;
                case R.id.y_check:
                    Result.failed(Constant.GSENSOR_Y);
                    break;
                case R.id.z_check:
                    Result.failed(Constant.GSENSOR_Z);
                    break;
            }
        } else {
            switch (buttonView.getId()) {
                case R.id.x_check:
                    Result.passed(Constant.GSENSOR_X);
                    break;
                case R.id.y_check:
                    Result.passed(Constant.GSENSOR_Y);
                    break;
                case R.id.z_check:
                    Result.passed(Constant.GSENSOR_Z);
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
        mSensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (null == event.sensor) {
            return;
        }
        if (Sensor.TYPE_ACCELEROMETER == event.sensor.getType()) {
            mXValue.setText(String.valueOf(event.values[0]));
            mYValue.setText(String.valueOf(event.values[1]));
            mZValue.setText(String.valueOf(event.values[2]));
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

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
