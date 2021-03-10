package com.hwilliam.jnilearncmake

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {

    companion object {
        const val BITRATE = 32
        const val DEFAULT_SAMPLE_REATE = 44100
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_make_jni_crash.setOnClickListener {
            thread {
                NDKTools.testErrorFunction()
            }
        }
    }
}
