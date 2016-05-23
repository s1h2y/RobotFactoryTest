package com.alpha.smart.factorytest.activitys;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.speech.RecognitionListener;
import android.speech.SpeechRecognizer;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.alpha.smart.factorytest.R;
import com.alpha.smart.factorytest.utils.Constant;
import com.baidu.speech.VoiceRecognitionService;

import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by weilikai01 on 2015/5/12.
 */
public class ActivityTouch extends Activity implements RecognitionListener {
    private static final String TAG = "Touch";
    private static final int EVENT_ERROR = 11;
    private TextView txtResult;
    private TextView txtLog;
    private Button btn;
    private Button setting;

    View speechTips;

    View speechWave;
    private SpeechRecognizer speechRecognizer;
    private long speechEndTime = -1;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sdk2_api);
        mContext = this;
        txtResult = (TextView) findViewById(R.id.txtResult);
        txtLog = (TextView) findViewById(R.id.txtLog);
        btn = (Button) findViewById(R.id.btn);

        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(this, new ComponentName(this, VoiceRecognitionService.class));
        speechRecognizer.setRecognitionListener(this);

        speechTips = View.inflate(this, R.layout.bd_asr_popup_speech, null);
        speechWave = speechTips.findViewById(R.id.wave);
        speechTips.setVisibility(View.GONE);
        addContentView(speechTips, new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        btn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        speechTips.setVisibility(View.VISIBLE);
                        speechRecognizer.cancel();
                        Intent intent = new Intent();
                        bindParams(intent);
                        intent.putExtra("vad", "touch");
                        txtResult.setText("");
                        txtLog.setText("");
                        speechRecognizer.startListening(intent);
                        return true;
                    case MotionEvent.ACTION_UP:
                        speechRecognizer.stopListening();
                        speechTips.setVisibility(View.GONE);
                        break;
                }
                return false;
            }
        });
        btn.setEnabled(false);
        btn.setText(R.string.wait_copy);
        mHandler.sendEmptyMessage(0);
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    new Thread() {
                        @Override
                        public void run() {
                            if (createDir("/sdcard/easr")) {
                                assetsToPath(mContext, "temp_license_2016-05-23", "/sdcard/easr/temp_license_2016-05-23");
                                assetsToPath(mContext, "s_1", "/sdcard/easr/s_1");
                                assetsToPath(mContext, "s_2_InputMethod", "/sdcard/easr/s_2_InputMethod");
                                sendEmptyMessage(1);
                            } else {
                                sendEmptyMessage(2);
                            }
                        }
                    }.start();
                    break;
                case 1:
                    btn.setText("按住识别");
                    btn.setEnabled(true);
                    break;
                case 2:
                    btn.setText("复制文件失败");
                    break;
            }
        }
    };

    private boolean createDir(String easr) {
        File dir = new File(easr);
        if (dir.exists()) {
            return true;
        } else {
            dir.mkdirs();
            if (dir.exists()) {
                return true;
            }
        }
        return false;
    }

    public void assetsToPath(Context ctxDealFile, String from, String to) {
        if (new File(to).exists()) {
            return;
        }
        try {
            InputStream is = ctxDealFile.getAssets().open(from);
            FileOutputStream fos = new FileOutputStream(to);
            byte[] buffer = new byte[1024];
            int count = 0;
            while (true) {
                count++;
                int len = is.read(buffer);
                if (len == -1) {
                    break;
                }
                fos.write(buffer, 0, len);
            }
            is.close();
            fos.close();

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void bindParams(Intent intent) {
//        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
//        if (sp.getBoolean("tips_sound", true)) {
//            intent.putExtra(Constant.EXTRA_SOUND_START, R.raw.bdspeech_recognition_start);
//            intent.putExtra(Constant.EXTRA_SOUND_END, R.raw.bdspeech_speech_end);
//            intent.putExtra(Constant.EXTRA_SOUND_SUCCESS, R.raw.bdspeech_recognition_success);
//            intent.putExtra(Constant.EXTRA_SOUND_ERROR, R.raw.bdspeech_recognition_error);
//            intent.putExtra(Constant.EXTRA_SOUND_CANCEL, R.raw.bdspeech_recognition_cancel);
//        }
//        if (sp.contains(Constant.EXTRA_INFILE)) {
//            String tmp = sp.getString(Constant.EXTRA_INFILE, "").replaceAll(",.*", "").trim();
//            intent.putExtra(Constant.EXTRA_INFILE, tmp);
//        }
//        if (sp.getBoolean(Constant.EXTRA_OUTFILE, false)) {
//            intent.putExtra(Constant.EXTRA_OUTFILE, "sdcard/outfile.pcm");
//        }
//        if (sp.contains(Constant.EXTRA_SAMPLE)) {
//            String tmp = sp.getString(Constant.EXTRA_SAMPLE, "").replaceAll(",.*", "").trim();
//            if (null != tmp && !"".equals(tmp)) {
//                intent.putExtra(Constant.EXTRA_SAMPLE, Integer.parseInt(tmp));
//            }
//        }
//        if (sp.contains(Constant.EXTRA_LANGUAGE)) {
//            String tmp = sp.getString(Constant.EXTRA_LANGUAGE, "").replaceAll(",.*", "").trim();
//            if (null != tmp && !"".equals(tmp)) {
//                intent.putExtra(Constant.EXTRA_LANGUAGE, tmp);
//            }
//        }
//        if (sp.contains(Constant.EXTRA_NLU)) {
//            String tmp = sp.getString(Constant.EXTRA_NLU, "").replaceAll(",.*", "").trim();
//            if (null != tmp && !"".equals(tmp)) {
//                intent.putExtra(Constant.EXTRA_NLU, tmp);
//            }
//        }
//
//        if (sp.contains(Constant.EXTRA_VAD)) {
//            String tmp = sp.getString(Constant.EXTRA_VAD, "").replaceAll(",.*", "").trim();
//            if (null != tmp && !"".equals(tmp)) {
//                intent.putExtra(Constant.EXTRA_VAD, tmp);
//            }
//        }
//        String prop = null;
//        if (sp.contains(Constant.EXTRA_PROP)) {
//            String tmp = sp.getString(Constant.EXTRA_PROP, "").replaceAll(",.*", "").trim();
//            if (null != tmp && !"".equals(tmp)) {
//                intent.putExtra(Constant.EXTRA_PROP, Integer.parseInt(tmp));
//                prop = tmp;
//            }
//        }
        // offline asr
        {
            intent.putExtra(Constant.EXTRA_OFFLINE_ASR_BASE_FILE_PATH, "/sdcard/easr/s_1");
            intent.putExtra(Constant.EXTRA_LICENSE_FILE_PATH, "/sdcard/easr/temp_license_2016-05-23");
            intent.putExtra(Constant.EXTRA_OFFLINE_LM_RES_FILE_PATH, "/sdcard/easr/s_2_InputMethod");
            intent.putExtra("sample", 16000); // 离线仅支持16000采样率
            intent.putExtra("language", "cmn-Hans-CN"); // 离线仅支持中文普通话
            intent.putExtra("prop", 20000); // 输入
        }
    }

    @Override
    public void onReadyForSpeech(Bundle params) {

    }

    @Override
    public void onBeginningOfSpeech() {

    }

    @Override
    public void onRmsChanged(float rmsdB) {
        final int VTAG = 0xFF00AA01;
        Integer rawHeight = (Integer) speechWave.getTag(VTAG);
        if (rawHeight == null) {
            rawHeight = speechWave.getLayoutParams().height;
            speechWave.setTag(VTAG, rawHeight);
        }

        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) speechWave.getLayoutParams();
        params.height = (int) (rawHeight * rmsdB * 0.01);
        params.height = Math.max(params.height, speechWave.getMeasuredWidth());
        speechWave.setLayoutParams(params);
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
        print("识别失败：" + sb.toString());
    }

    @Override
    public void onResults(Bundle results) {
        long end2finish = System.currentTimeMillis() - speechEndTime;
        ArrayList<String> nbest = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
        print("识别成功：" + Arrays.toString(nbest.toArray(new String[nbest.size()])));
        String json_res = results.getString("origin_result");
        try {
            print("origin_result=\n" + new JSONObject(json_res).toString(4));
        } catch (Exception e) {
            print("origin_result=[warning: bad json]\n" + json_res);
        }
        btn.setText("按住开始");
        String strEnd2Finish = "";
        if (end2finish < 60 * 1000) {
            strEnd2Finish = "(waited " + end2finish + "ms)";
        }
        txtResult.setText(nbest.get(0) + strEnd2Finish);
    }

    @Override
    public void onPartialResults(Bundle partialResults) {
        ArrayList<String> nbest = partialResults.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
        if (nbest.size() > 0) {
            print("~临时识别结果：" + Arrays.toString(nbest.toArray(new String[0])));
            txtResult.setText(nbest.get(0));
        }
    }

    @Override
    public void onEvent(int eventType, Bundle params) {
        switch (eventType) {
            case EVENT_ERROR:
                String reason = params.get("reason") + "";
                print("EVENT_ERROR, " + reason);
                break;
        }
    }

    private void print(String msg) {
        txtLog.append(msg + "\n");
        ScrollView sv = (ScrollView) txtLog.getParent();
        sv.smoothScrollTo(0, 1000000);
        Log.d(TAG, "----" + msg);
    }
}
