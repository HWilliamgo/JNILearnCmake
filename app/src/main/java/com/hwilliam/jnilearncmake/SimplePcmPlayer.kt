package com.hwilliam.jnilearncmake

import android.media.AudioFormat
import android.media.AudioManager
import android.media.AudioTrack
import com.blankj.utilcode.util.FileIOUtils
import java.io.File

/**
 * date: 2020-01-27
 * author: hwj
 * description:
 */
class SimplePcmPlayer {
    private var audioTrack: AudioTrack? = null
    private var onPcmPlayErrorListener: OnPcmPlayErrorListener? = null
    private var pcmFilePath: String = ""

    fun play() {
        if (!File(pcmFilePath).exists()) {
            onPcmPlayErrorListener?.onError(Throwable("{$pcmFilePath}文件不存在"))
        }
        if (audioTrack == null) {
            onPcmPlayErrorListener?.onError(Throwable("请先调用prepare()方法"))
            return
        }

        val pcmDataBytes: ByteArray = FileIOUtils.readFile2BytesByStream(pcmFilePath)
        audioTrack?.write(pcmDataBytes, 0, pcmDataBytes.size)
        audioTrack?.play()
    }

    fun setErrorListener(onPcmPlayErrorListener: OnPcmPlayErrorListener) {
        this.onPcmPlayErrorListener = onPcmPlayErrorListener
    }

    fun stop() {
        audioTrack?.stop()
        audioTrack?.release()
        audioTrack = null
    }

    fun prepare(pcmFilePath: String) {
        // 音频流的类型
        // STREAM_ALARM：警告声
        // STREAM_MUSIC：音乐声
        // STREAM_RING：铃声
        // STREAM_SYSTEM：系统声音，例如低电提示音，锁屏音等
        // STREAM_VOCIE_CALL：通话声
        val streamType = AudioManager.STREAM_MUSIC;

        // 采样率 Hz
        val sampleRate = 44100;
        // 单声道
        val channelConfig = AudioFormat.CHANNEL_OUT_STEREO;

        // 音频数据表示的格式
        val audioFormat = AudioFormat.ENCODING_PCM_16BIT;

        // MODE_STREAM：在这种模式下，通过write一次次把音频数据写到AudioTrack中。这和平时通过
        // write系统调用往文件中写数据类似，但这种工作方式每次都需要把数据从用户提供的Buffer中拷贝到
        // AudioTrack内部的Buffer中，这在一定程度上会使引入延时。为解决这一问题，AudioTrack就引入
        // 了第二种模式。

        // MODE_STATIC：这种模式下，在play之前只需要把所有数据通过一次write调用传递到AudioTrack
        // 中的内部缓冲区，后续就不必再传递数据了。这种模式适用于像铃声这种内存占用量较小，延时要求较
        // 高的文件。但它也有一个缺点，就是一次write的数据不能太多，否则系统无法分配足够的内存来存储
        // 全部数据。
        val mode = AudioTrack.MODE_STATIC

//        val minBufferSize = AudioTrack.getMinBufferSize(sampleRate, channelConfig, audioFormat)

        this.pcmFilePath = pcmFilePath

        audioTrack = AudioTrack(
            streamType,
            sampleRate,
            channelConfig,
            audioFormat,
            File(pcmFilePath).length().toInt(),
            mode
        )
    }

    fun destroy() {
        stop()
    }

    interface OnPcmPlayErrorListener {
        fun onError(t: Throwable)
    }
}