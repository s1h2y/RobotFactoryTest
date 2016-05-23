package com.alpha.smart.factorytest.utils;

import android.net.wifi.ScanResult;
import android.os.StrictMode;

import com.alpha.smart.factorytest.R;
import com.alpha.smart.factorytest.beans.FragmentBean;
import com.alpha.smart.factorytest.fragments.BatteryFragment;
import com.alpha.smart.factorytest.fragments.BodyShakeFragment;
import com.alpha.smart.factorytest.fragments.ButtonsFragment;
import com.alpha.smart.factorytest.fragments.CameraFragment;
import com.alpha.smart.factorytest.fragments.DDRFragment;
import com.alpha.smart.factorytest.fragments.GsensorFragment;
import com.alpha.smart.factorytest.fragments.HandFragment;
import com.alpha.smart.factorytest.fragments.HeadLightFragment;
import com.alpha.smart.factorytest.fragments.HeadShakeFragment;
import com.alpha.smart.factorytest.fragments.LCDFragment;
import com.alpha.smart.factorytest.fragments.LightnessFragment;
import com.alpha.smart.factorytest.fragments.MicFragment;
import com.alpha.smart.factorytest.fragments.MonitorFragment;
import com.alpha.smart.factorytest.fragments.RTCFragment;
import com.alpha.smart.factorytest.fragments.SpeakerFragment;
import com.alpha.smart.factorytest.fragments.SpeechFragment;
import com.alpha.smart.factorytest.fragments.StandbyFragment;
import com.alpha.smart.factorytest.fragments.StorageFragment;
import com.alpha.smart.factorytest.fragments.TouchFragment;
import com.alpha.smart.factorytest.fragments.VersionFragment;
import com.alpha.smart.factorytest.fragments.WifiFragment;

/**
 * Created by shy on 16-5-16.
 */
public class Constant {

    public static final String PASSWORD = "12345678";
    public static final FragmentBean[] fragments = {new FragmentBean(R.drawable.list_version, R.drawable.version_grey, R.string.list_version, "version", VersionFragment.class.getName()),
            new FragmentBean(R.drawable.list_lcd, R.drawable.lcd_grey, R.string.list_lcd, "LCD", LCDFragment.class.getName()),
            new FragmentBean(R.drawable.list_button, R.drawable.button_grey, R.string.list_button, "buttons", ButtonsFragment.class.getName()),
            new FragmentBean(R.drawable.list_lightness, R.drawable.lightness_grey, R.string.list_lightness, "lightness", LightnessFragment.class.getName()),
            new FragmentBean(R.drawable.list_touch, R.drawable.touch_grey, R.string.list_touch, "touch", TouchFragment.class.getName()),
            new FragmentBean(R.drawable.list_speaker, R.drawable.speaker_grey, R.string.list_speaker, "speaker", SpeakerFragment.class.getName()),
            new FragmentBean(R.drawable.list_mic, R.drawable.mic_grey, R.string.list_mic, "microphone", MicFragment.class.getName()),
            new FragmentBean(R.drawable.list_battery, R.drawable.battery_grey, R.string.list_battery, "battery", BatteryFragment.class.getName()),
            new FragmentBean(R.drawable.list_standby, R.drawable.standby_grey, R.string.list_standby, "standby", StandbyFragment.class.getName()),
            new FragmentBean(R.drawable.list_wifi, R.drawable.wifi_grey, R.string.list_wifi, "wifi", WifiFragment.class.getName()),
            new FragmentBean(R.drawable.list_camera, R.drawable.camera_grey, R.string.list_camera, "camera", CameraFragment.class.getName()),
            new FragmentBean(R.drawable.list_head_light, R.drawable.head_light_grey, R.string.list_head_light, "head light", HeadLightFragment.class.getName()),
            new FragmentBean(R.drawable.list_head_shake, R.drawable.head_shake_grey, R.string.list_head_shake, "head shake", HeadShakeFragment.class.getName()),
            new FragmentBean(R.drawable.list_body_shake, R.drawable.body_shake_grey, R.string.list_body_shake, "body shake", BodyShakeFragment.class.getName()),
            new FragmentBean(R.drawable.list_hand, R.drawable.hand_grey, R.string.list_hand, "hand", HandFragment.class.getName()),
            new FragmentBean(R.drawable.list_gsensor, R.drawable.gsensor_grey, R.string.list_gsensor, "gsensor", GsensorFragment.class.getName()),
            new FragmentBean(R.drawable.list_rtc, R.drawable.rtc_grey, R.string.list_rtc, "RTC", RTCFragment.class.getName()),
            new FragmentBean(R.drawable.list_ddr, R.drawable.ddr_grey, R.string.list_ddr, "DDR", DDRFragment.class.getName()),
            new FragmentBean(R.drawable.list_storage, R.drawable.storage_grey, R.string.list_storage, "storage", StorageFragment.class.getName()),
            new FragmentBean(R.drawable.list_monitor, R.drawable.monitor_grey, R.string.list_monitor_light, "monitor light", MonitorFragment.class.getName()),
            new FragmentBean(R.drawable.list_mic, R.drawable.mic_grey, R.string.list_speech_recognize, "speech recognize", SpeechFragment.class.getName()),
    };
    public static final String PASSED = "passed";
    public static final String FAILED = "failed";

    public static int DEFAULT_LIGHTNESS = 70;
    public static int RECORD_DURATION = 5000;

    public static final String LCD = "lcd";
    public static final String BUTTON_VOL_UP = "button_vol_up";
    public static final String BUTTON_VOL_DOWN = "button_vol_down";
    public static final String BUTTON_REC = "button_rec";
    public static final String BUTTON_BACK = "button_back";
    public static final String BUTTON_HOME = "button_home";
    public static final String BUTTON_POWER = "button_power";
    public static final String LIGHTNESS = "lightness";
    public static final String TOUCH = "touch";
    public static final String SPEAKER_NO_SOUND = "speaker_no_sound";
    public static final String SPEAKER_BOOM = "speaker_boom";
    public static final String SPEAKER_NO_CHANGE = "speaker_no_change";
    public static final String MIC = "mic";
    public static final String STANDBY = "standby";
    public static final String WIFI_SEARCH = "wifi_search";
    public static final String WIFI_CONNECT = "wifi_connect";
    public static final String CAMERA_SHOT = "camera_shot";
    public static final String CAMERA_FOCUS = "camera_focus";
    public static final String HEAD_LIGHT_ORIGIN = "head_light_origin";
    public static final String HEAD_LIGHT_RED = "head_light_red";
    public static final String HEAD_LIGHT_GREEN = "head_light_green";
    public static final String HEAD_SHAKE = "head_shake";
    public static final String BODY_SHAKE = "body_shake";
    public static final String HAND_LEFT = "hand_left";
    public static final String HAND_RIGHT = "hand_right";
    public static final String GSENSOR_X = "gsensor_x";
    public static final String GSENSOR_Y = "gsensor_y";
    public static final String GSENSOR_Z = "gsensor_z";
    public static final String RTC = "rtc";
    public static final String DDR = "ddr";
    public static final String MONITOR = "monitor";
    public static final String SPEECH = "speech";

    public static final String RECORD_FILE_NAME = "/audiorecordtest.3gp";

    public static final String TEST_FILE = "factory_test_result_path";

    //baidu speech start
    public static final String EXTRA_KEY = "key";
    public static final String EXTRA_SECRET = "secret";
    public static final String EXTRA_SAMPLE = "sample";
    public static final String EXTRA_SOUND_START = "sound_start";
    public static final String EXTRA_SOUND_END = "sound_end";
    public static final String EXTRA_SOUND_SUCCESS = "sound_success";
    public static final String EXTRA_SOUND_ERROR = "sound_error";
    public static final String EXTRA_SOUND_CANCEL = "sound_cancel";
    public static final String EXTRA_INFILE = "infile";
    public static final String EXTRA_OUTFILE = "outfile";

    public static final String EXTRA_LANGUAGE = "language";
    public static final String EXTRA_NLU = "nlu";
    public static final String EXTRA_VAD = "vad";
    public static final String EXTRA_PROP = "prop";

    public static final String EXTRA_OFFLINE_ASR_BASE_FILE_PATH = "asr-base-file-path";
    public static final String EXTRA_LICENSE_FILE_PATH = "license-file-path";
    public static final String EXTRA_OFFLINE_LM_RES_FILE_PATH = "lm-res-file-path";
    public static final String EXTRA_OFFLINE_SLOT_DATA = "slot-data";
    public static final String EXTRA_OFFLINE_SLOT_NAME = "name";
    public static final String EXTRA_OFFLINE_SLOT_SONG = "song";
    public static final String EXTRA_OFFLINE_SLOT_ARTIST = "artist";
    public static final String EXTRA_OFFLINE_SLOT_APP = "app";
    public static final String EXTRA_OFFLINE_SLOT_USERCOMMAND = "usercommand";

    public static final int SAMPLE_8K = 8000;
    public static final int SAMPLE_16K = 16000;

    public static final String VAD_SEARCH = "search";
    public static final String VAD_INPUT = "input";
    //baidu speech end
}
