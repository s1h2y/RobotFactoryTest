package com.alpha.smart.factorytest.utils;

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
import com.alpha.smart.factorytest.fragments.StandbyFragment;
import com.alpha.smart.factorytest.fragments.StorageFragment;
import com.alpha.smart.factorytest.fragments.TouchFragment;
import com.alpha.smart.factorytest.fragments.VersionFragment;
import com.alpha.smart.factorytest.fragments.WifiFragment;

/**
 * Created by shy on 16-5-16.
 */
public class Constant {
    public static final String password = "12345678";
    public static final FragmentBean[] fragments = {new FragmentBean(R.drawable.list_version, R.string.list_version, "version", VersionFragment.class.getName()),
            new FragmentBean(R.drawable.list_lcd, R.string.list_lcd, "LCD", LCDFragment.class.getName()),
            new FragmentBean(R.drawable.list_button, R.string.list_button, "buttons", ButtonsFragment.class.getName()),
            new FragmentBean(R.drawable.list_lightness, R.string.list_lightness, "lightness", LightnessFragment.class.getName()),
            new FragmentBean(R.drawable.list_touch, R.string.list_touch, "touch", TouchFragment.class.getName()),
            new FragmentBean(R.drawable.list_speaker, R.string.list_speaker, "speaker", SpeakerFragment.class.getName()),
            new FragmentBean(R.drawable.list_mic, R.string.list_mic, "microphone", MicFragment.class.getName()),
            new FragmentBean(R.drawable.list_battery, R.string.list_battery, "battery", BatteryFragment.class.getName()),
            new FragmentBean(R.drawable.list_standby, R.string.list_standby, "standby", StandbyFragment.class.getName()),
            new FragmentBean(R.drawable.list_wifi, R.string.list_wifi, "wifi", WifiFragment.class.getName()),
            new FragmentBean(R.drawable.list_camera, R.string.list_camera, "camera", CameraFragment.class.getName()),
            new FragmentBean(R.drawable.list_head_light, R.string.list_head_light, "head light", HeadLightFragment.class.getName()),
            new FragmentBean(R.drawable.list_head_shake, R.string.list_head_shake, "head shake", HeadShakeFragment.class.getName()),
            new FragmentBean(R.drawable.list_body_shake, R.string.list_body_shake, "body shake", BodyShakeFragment.class.getName()),
            new FragmentBean(R.drawable.list_hand, R.string.list_hand, "hand", HandFragment.class.getName()),
            new FragmentBean(R.drawable.list_gsensor, R.string.list_gsensor, "gsensor", GsensorFragment.class.getName()),
            new FragmentBean(R.drawable.list_rtc, R.string.list_rtc, "RTC", RTCFragment.class.getName()),
            new FragmentBean(R.drawable.list_ddr, R.string.list_ddr, "DDR", DDRFragment.class.getName()),
            new FragmentBean(R.drawable.list_storage, R.string.list_storage, "storage", StorageFragment.class.getName()),
            new FragmentBean(R.drawable.list_monitor, R.string.list_monitor_light, "monitor light", MonitorFragment.class.getName()),
    };

    public static int curLightness = 70;
}
