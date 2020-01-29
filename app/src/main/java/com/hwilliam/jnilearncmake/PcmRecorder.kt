package com.hwilliam.jnilearncmake

import android.media.AudioFormat
import android.media.AudioRecord
import android.media.MediaRecorder
import com.blankj.utilcode.util.FileUtils
import com.blankj.utilcode.util.LogUtils
import java.io.File
import java.io.FileOutputStream
import kotlin.concurrent.thread

/**
 * date: 2020-01-20
 * author: hwj
 * description:
 */
class PcmRecorder(private val pcmFilePath: String) {
    companion object {
        const val DEFAULT_SAMPLING_RATE = 44100
    }

    //录音器
    private var audioRecorder: AudioRecord? = null
    //flag
    @Volatile
    private var isStop = false

    fun start() {
        isStop = false

        audioRecorder = createRecorder()

        //创建写入的pcm文件
        val status = FileUtils.createFileByDeleteOldFile(pcmFilePath)
        if (!status) {
            LogUtils.d("创建文件错误")
            return
        }
        val pcmFile = File(pcmFilePath)

        //开始录音
        audioRecorder?.apply {
            LogUtils.d(state)
            startRecording()

            val byteArray = ByteArray(
                2 * AudioRecord.getMinBufferSize(
                    DEFAULT_SAMPLING_RATE,
                    AudioFormat.CHANNEL_IN_MONO,
                    AudioFormat.ENCODING_PCM_16BIT
                )
            )

            thread(start = true) {
                val fos = FileOutputStream(pcmFile, true)
                while (!isStop) {
                    val readLength = read(byteArray, 0, byteArray.size)
                    fos.write(byteArray, 0, readLength)
                }
            }
        }
    }

    fun stop() {
        isStop = true

        //销毁和释放
        audioRecorder?.stop()
        audioRecorder?.release()
        audioRecorder = null
    }

    private fun createRecorder(): AudioRecord = AudioRecord(
        MediaRecorder.AudioSource.MIC,
        DEFAULT_SAMPLING_RATE,
        AudioFormat.CHANNEL_IN_STEREO,
        AudioFormat.ENCODING_PCM_16BIT,
        2 * AudioRecord.getMinBufferSize(
            DEFAULT_SAMPLING_RATE,
            AudioFormat.CHANNEL_IN_STEREO,
            AudioFormat.ENCODING_PCM_16BIT
        )
    )
}