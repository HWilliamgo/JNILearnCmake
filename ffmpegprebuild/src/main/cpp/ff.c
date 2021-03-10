#include <stdio.h>
#include <jni.h>
#include <malloc.h>
#include <android/log.h>
#include "libavformat/avformat.h"


int a() {
    return 100;
}

JNIEXPORT jint JNICALL
Java_com_hwilliamgo_ffmpegprebuild_FFMpegUtils_getVersion(JNIEnv *env, jclass clazz) {
    return a();
}

