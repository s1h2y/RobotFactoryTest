package cn.alpha.intell.factory.check.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;

import cn.alpha.intell.factory.check.R;
import cn.alpha.intell.factory.check.utils.Constant;
import cn.alpha.intell.factory.check.utils.Result;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link HandFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link HandFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HandFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private CheckBox mCheckLeft;
    private CheckBox mCheckRight;
    private ImageView mImage;

    public HandFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HandFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HandFragment newInstance(String param1, String param2) {
        HandFragment fragment = new HandFragment();
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
        View root = inflater.inflate(R.layout.fragment_hand, container, false);
        initView(root);
        return root;
    }

    private void initView(View root) {
        mImage = (ImageView)root.findViewById(R.id.image);
        mCheckLeft = (CheckBox)root.findViewById(R.id.left);
        mCheckRight = (CheckBox)root.findViewById(R.id.right);
        mCheckLeft.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    Result.failed(Constant.HAND_LEFT);
                } else {
                    Result.passed(Constant.HAND_LEFT);
                }
            }
        });
        mCheckRight.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    Result.failed(Constant.HAND_RIGHT);
                } else {
                    Result.passed(Constant.HAND_RIGHT);
                }
            }
        });
        checkResult();
    }

    private void checkResult() {
        boolean checked = Constant.FAILED.equals(Result.get(Constant.HAND_LEFT)) ? true : false;
        mCheckLeft.setChecked(checked);
        checked = Constant.FAILED.equals(Result.get(Constant.HAND_RIGHT)) ? true : false;
        mCheckRight.setChecked(checked);
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

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_J:
                mImage.setImageResource(R.drawable.hand_left);
                mCheckLeft.setEnabled(false);
                return true;
            case KeyEvent.KEYCODE_H:
                mCheckRight.setEnabled(false);
                mImage.setImageResource(R.drawable.hand_right);
                return true;
        }
        return false;
    }

    public boolean onKeyUp(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_H:
            case KeyEvent.KEYCODE_J:
                mImage.setImageResource(R.drawable.body);
                return true;
        }
        return false;
    }
}