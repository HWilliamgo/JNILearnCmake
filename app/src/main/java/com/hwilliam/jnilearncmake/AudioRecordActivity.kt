package com.hwilliam.jnilearncmake

import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.blankj.utilcode.util.FileUtils
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.ToastUtils
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_audio_record.*
import java.io.File

class AudioRecordActivity : AppCompatActivity() {
    private val audioCodec = AudioCodec()
    private val pcmPlayer = SimplePcmPlayer()
    private var mp3Player: MediaPlayer? = null

    private var pcmToMp3Disposable: Disposable? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_audio_record)


        val pcmFilePath = File(externalCacheDir, "audio_record_pcm_file.pcm").absolutePath
        val mp3FilePath = File(externalCacheDir, "audio_record_pcm_file.mp3").absolutePath

        val pcmRecorder = PcmRecorder(pcmFilePath)

        btn_audio_record_start.setOnClickListener {
            FileUtils.createFileByDeleteOldFile(pcmFilePath)
            pcmRecorder.start()
        }
        btn_audio_record_stop.setOnClickListener {
            pcmRecorder.stop()
        }

        btn_audio_record_start_convert.setOnClickListener {
            FileUtils.createFileByDeleteOldFile(mp3FilePath)
            pcmToMp3Disposable = audioCodec.pcmToMp3(pcmFilePath, mp3FilePath, {
                LogUtils.d("onSuccess")
            }, {
                LogUtils.e(it)
                ToastUtils.showShort(it.message)
            })
        }

        btn_audio_record_play_pcm.setOnClickListener {
            pcmPlayer.prepare(pcmFilePath)
            pcmPlayer.play()
        }
        btn_audio_record_play_mp3.setOnClickListener {
            mp3Player = MediaPlayer.create(this@AudioRecordActivity, Uri.parse(mp3FilePath))
            mp3Player?.start()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        pcmToMp3Disposable?.dispose()
        pcmPlayer.destroy()
        mp3Player?.stop()
        mp3Player?.release()
    }
}
