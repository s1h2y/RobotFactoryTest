package com.alpha.smart.factorytest.fragments;

import android.app.Fragment;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.service.voice.VoiceInteractionService;
import android.speech.RecognitionListener;
import android.speech.SpeechRecognizer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.alpha.smart.factorytest.R;
import com.alpha.smart.factorytest.activitys.ActivityTouch;
import com.alpha.smart.factorytest.utils.Constant;
import com.alpha.smart.factorytest.utils.Result;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SpeechFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SpeechFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SpeechFragment extends Fragment implements RecognitionListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private CheckBox mCheck;
    private TextView mTips;
    private Button mBtn;
    private Context mContext;
    private SpeechRecognizer mSpeech;

    public SpeechFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SpeechFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SpeechFragment newInstance(String param1, String param2) {
        SpeechFragment fragment = new SpeechFragment();
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
//        initRecognize();
    }

    private void initView(View root) {
        mCheck = (CheckBox)root.findViewById(R.id.check);
        mCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    Result.failed(Constant.SPEECH);
                } else {
                    Result.passed(Constant.SPEECH);
                }
            }
        });
        mBtn = (Button)root.findViewById(R.id.button);
        mBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if (mBtn.getText().equals(getString(R.string.start_speech))) {
//                    mBtn.setText(R.string.stop_speech);
//                    mTips.setText(R.string.recognizing);
//                    mSpeech.cancel();
//                    Intent intent = new Intent();
//                    bindParams(intent);
//                    mSpeech.startListening(intent);
//                } else {
//                    mBtn.setText(R.string.start_speech);
//                    mTips.setText(R.string.recognizing);
//                    mSpeech.stopListening();
//                }
                startActivity(new Intent(mContext, ActivityTouch.class));
            }
        });
        mTips = (TextView)root.findViewById(R.id.tips);
    }

    private void initRecognize() {
        mSpeech = SpeechRecognizer.createSpeechRecognizer(mContext, new ComponentName(mContext, VoiceInteractionService.class));
        mSpeech.setRecognitionListener(this);

    }

    private void bindParams(Intent intent) {
        intent.putExtra(Constant.EXTRA_OFFLINE_ASR_BASE_FILE_PATH, "/sdcard/easr/s1");
        intent.putExtra(Constant.EXTRA_LICENSE_FILE_PATH, "/sdcard/easr/license");
        intent.putExtra(Constant.EXTRA_SAMPLE, 16000); // 离线仅支持16000采样率
        intent.putExtra(Constant.EXTRA_LANGUAGE, "cmn-Hans-CN"); // 离线仅支持中文普通话
        intent.putExtra(Constant.EXTRA_PROP, 20000); // 输入
        intent.putExtra(Constant.EXTRA_OFFLINE_LM_RES_FILE_PATH, "/sdcard/easr/s2");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_speech, container, false);
        initView(root);
        return root;
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
        mSpeech.destroy();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onReadyForSpeech(Bundle params) {

    }

    @Override
    public void onBeginningOfSpeech() {

    }

    @Override
    public void onRmsChanged(float rmsdB) {

    }

    @Override
    public void onBufferReceived(byte[] buffer) {

    }

    @Override
    public void onEndOfSpeech() {

    }

    @Override
    public void onError(int error) {
        StringBuilder sb = new StringBuilder();
        switch (error) {
            case SpeechRecognizer.ERROR_AUDIO:
                sb.append("音频问题");
                break;
            case SpeechRecognizer.ERROR_SPEECH_TIMEOUT:
                sb.append("没有语音输入");
                break;
            case SpeechRecognizer.ERROR_CLIENT:
                sb.append("其它客户端错误");
                break;
            case SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS:
                sb.append("权限不足");
                break;
            case SpeechRecognizer.ERROR_NETWORK:
                sb.append("网络问题");
                break;
            case SpeechRecognizer.ERROR_NO_MATCH:
                sb.append("没有匹配的识别结果");
                break;
            case SpeechRecognizer.ERROR_RECOGNIZER_BUSY:
                sb.append("引擎忙");
                break;
            case SpeechRecognizer.ERROR_SERVER:
                sb.append("服务端错误");
                break;
            case SpeechRecognizer.ERROR_NETWORK_TIMEOUT:
                sb.append("连接超时");
                break;
        }
        sb.append(":" + error);
        Log.d("", "识别失败：" + sb.toString());
    }

    @Override
    public void onResults(Bundle results) {
        ArrayList<String> nbest = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
        mTips.setText(getString(R.string.speech_result) + Arrays.toString(nbest.toArray(new String[nbest.size()])));
        mBtn.setText(R.string.start_speech);
    }

    @Override
    public void onPartialResults(Bundle partialResults) {

    }

    @Override
    public void onEvent(int eventType, Bundle params) {

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
