package com.hwilliam.jnilearncmake

import android.Manifest
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.blankj.utilcode.util.LogUtils
import com.example.lame.Mp3Encoder
import com.william.fastpermisssion.FastPermission
import com.william.fastpermisssion.OnPermissionCallback
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {

    companion object {
        const val BITRATE = 32
        const val DEFAULT_SAMPLE_REATE = 44100
    }

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

        val pcmFilePath = ""
        val mp3FilePath = ""
        Mp3Encoder.init(pcmFilePath, mp3FilePath, DEFAULT_SAMPLE_REATE, 1, BITRATE)

        btn_main_go_audio_record.setOnClickListener {
            FastPermission.getInstance()
                .start(
                    this@MainActivity,
                    arrayListOf(Manifest.permission.RECORD_AUDIO),
                    object : OnPermissionCallback {
                        override fun onGranted(grantedPermissions: ArrayList<String>?) {

                        }

                        override fun onAllGranted() {
                            startActivity(
                                Intent(
                                    this@MainActivity,
                                    AudioRecordActivity::class.java
                                )
                            )
                        }

                        override fun onDenied(deniedPermissions: ArrayList<String>?) {
                        }

                        override fun onDeniedForever(deniedForeverP: ArrayList<String>?) {
                        }
                    })
        }
    }
}
