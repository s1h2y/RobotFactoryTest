package cn.alpha.intell.factory.check;

import android.app.Application;
import android.content.Context;
import android.os.PowerManager;

/**
 * Created by shy on 16-5-24.
 */
public class MyApplication extends Application {

    PowerManager.WakeLock wl;

    @Override
    public void onCreate() {
        super.onCreate();
        PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
        wl = pm.newWakeLock(PowerManager.FULL_WAKE_LOCK, "MyTag");
        wl.acquire();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        wl.release();
    }
}
