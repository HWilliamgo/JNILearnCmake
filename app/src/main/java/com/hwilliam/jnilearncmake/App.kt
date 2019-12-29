package com.hwilliam.jnilearncmake

import android.app.Application
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
    }
}