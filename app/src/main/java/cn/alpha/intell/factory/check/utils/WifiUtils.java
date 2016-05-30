package cn.alpha.intell.factory.check.utils;

import android.content.Context;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Handler;

import cn.alpha.intell.factory.check.beans.WifiBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shy on 16-5-16.
 */
public class WifiUtils {

    Context mContext;
    Handler mHandler;
    WifiManager mWifiManager;
    WifiConfiguration mConfig;

    private WifiUtils() {

    }

    public WifiUtils(Context ctx, Handler h) {
        mContext = ctx;
        mHandler = h;
        mWifiManager = (WifiManager) ctx.getSystemService(Context.WIFI_SERVICE);
        mConfig = new WifiConfiguration();
        mConfig.preSharedKey = "\"" + Constant.PASSWORD + "\"";
    }

    public void openWifi() {
        if (!mWifiManager.isWifiEnabled())
            mWifiManager.setWifiEnabled(true);
    }

    public void closeWifi() {
        if (mWifiManager.isWifiEnabled())
            mWifiManager.setWifiEnabled(false);
    }

    public List<ScanResult> getWifiList() {
        List<ScanResult> list = null;
        if (mWifiManager.isWifiEnabled()) {
            list = mWifiManager.getScanResults();
        }
        return list;
    }

    public void startScan() {
        mWifiManager.startScan();
        String a = WifiManager.SCAN_RESULTS_AVAILABLE_ACTION;
    }


    public List<WifiBean> getWifiInfo() {
        List<ScanResult> list = getWifiList();
        List<WifiBean> wifiBeanList = new ArrayList<WifiBean>();
        for (int i = 0; i < list.size(); i++) {
            ScanResult res = list.get(i);
            WifiBean wb = new WifiBean();
            wb.ssid = res.SSID;
            wb.level = res.level;
            wifiBeanList.add(i, wb);
            wb = null;
        }
        return wifiBeanList;
    }

    public void connectWifi(WifiBean wb) {
        mConfig.SSID = "\"" + wb.ssid + "\"";
        mConfig.preSharedKey = "\"" + Constant.PASSWORD + "\"";
        mConfig.status = WifiConfiguration.Status.ENABLED;
        int wifiId = mWifiManager.addNetwork(mConfig);
        if (wifiId != -1) {
            mWifiManager.enableNetwork(wifiId, true);
        }
    }

    public String getCurSSID() {
        int wifiState = mWifiManager.getWifiState();
        WifiInfo info = mWifiManager.getConnectionInfo();
        String wifiId = info != null ? info.getSSID() : null;
        return wifiId;
    }
}
