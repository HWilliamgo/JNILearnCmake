package com.hwilliam.jnilearncmake

import com.example.lame.Mp3Encoder
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.io.File

/**
 * date: 2020-01-26
 * author: hwj
 * description:
 */
class AudioCodec {
    companion object {
        const val BITRATE = 16
        const val DEFAULT_SAMPLE_RATE = 44100
    }

    fun pcmToMp3(
        pcmFilePath: String,
        mp3FilePath: String,
        onSuccess: () -> Unit,
        onFailed: (t: Throwable) -> Unit
    ): Disposable {
        return Observable
            .just(1)
            .subscribeOn(Schedulers.io())
            .doOnNext {
                if (!File(pcmFilePath).exists() || !File(pcmFilePath).exists()) {
                    throw Throwable("媒体文件不存在")
                }
                Mp3Encoder.init(pcmFilePath, mp3FilePath, DEFAULT_SAMPLE_RATE, 1, BITRATE)
                Mp3Encoder.encode()
            }
            .subscribeOn(AndroidSchedulers.mainThread())
            .doFinally {
                Mp3Encoder.destroy()
            }
            .subscribe({
                onSuccess()
            }, {
                onFailed(it)
            })
    }
}