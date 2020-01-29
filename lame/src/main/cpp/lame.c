//
// Created by HWilliam on 2020-01-20.
//

// <editor-fold defaultstate="collapsed" desc="NDK通用引入">
#include <jni.h>
#include <stdio.h>
#include <malloc.h>
#include <android/log.h>
// </editor-fold>

// <editor-fold defaultstate="collapsed" desc="LAME">
#include "libmp3lame/lame.h"
// </editor-fold>

#include "mp3Encoder.h"


#define TAG "LAME"


//定义TAG之后，我们可以在LogCat通过TAG过滤出NDK打印的日志
// 定义info信息
#define LOGI(...) __android_log_print(ANDROID_LOG_INFO,TAG,__VA_ARGS__)
// 定义debug信息
#define LOGD(...) __android_log_print(ANDROID_LOG_DEBUG, TAG, __VA_ARGS__)
// 定义error信息
#define LOGE(...) __android_log_print(ANDROID_LOG_ERROR,TAG,__VA_ARGS__)


JNIEXPORT void JNICALL
Java_com_example_lame_Mp3Encoder_init(JNIEnv *env, jclass clazz, jstring pcm_file_path,
                                      jstring mp3_file_path, jint sample_rate, jint channels,
                                      jint bitrate) {
    const char *pcmPath = (*env)->GetStringUTFChars(env, pcm_file_path, NULL);
    const char *mp3Path = (*env)->GetStringUTFChars(env, mp3_file_path, NULL);
    init(pcmPath, mp3Path, sample_rate, channels, bitrate);
    (*env)->ReleaseStringUTFChars(env, pcm_file_path, pcmPath);
    (*env)->ReleaseStringUTFChars(env, mp3_file_path, mp3Path);
}


JNIEXPORT void JNICALL
Java_com_example_lame_Mp3Encoder_encode(JNIEnv *env, jclass clazz) {
    LOGD("encode start");
    encode();
    LOGD("encode finish");
}

JNIEXPORT void JNICALL
Java_com_example_lame_Mp3Encoder_destroy(JNIEnv *env, jclass clazz) {
    destroy();
}