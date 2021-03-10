package com.hwilliam.jnilearncmake

import android.app.ActivityManager
import android.app.Application
import android.content.Context
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.Utils

/**
 * date: 2019-12-20
 * author: hwj
 * description:
 */
class App : Application() {
    override fun onCreate() {
        super.onCreate()
        Utils.init(this)
        LogUtils.getConfig().setBorderSwitch(false)

        LogUtils.d("App.onCreate " + getCurrentProcessName(this))
    }

    private fun getCurrentProcessName(context: Context): String {
        val pid = android.os.Process.myPid()
        val mActivityManager =
            context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager;

//        for (ActivityManager.android.app.ActivityManager.RunningAppProcessInfo appProcess : mActivityManager
//                .getRunningAppProcesses()) {
//            if (appProcess.pid == pid) {
//                return appProcess.processName;
//            }
//        }
        for (runningAppProcess in mActivityManager.runningAppProcesses) {
            if (runningAppProcess.pid == pid) {
                return runningAppProcess.processName;
            }
        }
        return ""
    }
}