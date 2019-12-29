package com.hwilliam.jnilearncmake

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.blankj.utilcode.util.LogUtils

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        NDKTools.inputInt(10)
        NDKTools.inputString("inputString")

        val syncIntResult = NDKTools.getIntFromCSync()
        LogUtils.d("syncIntResult=$syncIntResult")
        NDKTools.getIntFromCAsync()

        val syncStringResult = NDKTools.getStringFromCSync()
        LogUtils.d("syncStringResult=$syncStringResult")
        NDKTools.getStringFromCAsync()
    }
}
