//
// Created by HWilliam on 2019-12-20.
//


#include <jni.h>
#include <stdio.h>
#include <malloc.h>
#include <android/log.h>
#include <string.h>
#include <stdlib.h>
#include "const.h"
#include "libB.c"

#define TAG "JNITEST"


//定义TAG之后，我们可以在LogCat通过TAG过滤出NDK打印的日志
// 定义info信息
#define LOGI(...) __android_log_print(ANDROID_LOG_INFO,TAG,__VA_ARGS__)
// 定义debug信息
#define LOGD(...) __android_log_print(ANDROID_LOG_DEBUG, TAG, __VA_ARGS__)
// 定义error信息
#define LOGE(...) __android_log_print(ANDROID_LOG_ERROR,TAG,__VA_ARGS__)


JNIEXPORT void JNICALL
Java_com_hwilliam_jnilearncmake_NDKTools_inputInt(JNIEnv *env, jclass clazz, jint int_data) {
    int data = int_data;
    LOGD("input int data from java = %d", data);
}

JNIEXPORT void JNICALL
Java_com_hwilliam_jnilearncmake_NDKTools_inputString(JNIEnv *env, jclass clazz,
                                                     jstring string_data) {
    //获取java中的string_data，转换成c中的字符串
    const char *string = (*env)->GetStringUTFChars(env, string_data, NULL);
    LOGD("input string data from java = %s", string);
    //释放java中的string_data的引用。
    (*env)->ReleaseStringUTFChars(env, string_data, string);
}

JNIEXPORT jint JNICALL
Java_com_hwilliam_jnilearncmake_NDKTools_getIntFromCSync(JNIEnv *env, jclass clazz) {
    int data = 100;
    jint data2Java = data;
    return data2Java;
}

JNIEXPORT void JNICALL
Java_com_hwilliam_jnilearncmake_NDKTools_getIntFromCAsync(JNIEnv *env, jclass clazz) {
    jint data2Java = 200;

    jmethodID onGetIntFromC = (*env)->GetStaticMethodID(env, clazz, "onGetIntFromC", "(I)V");
    (*env)->CallStaticVoidMethod(env, clazz, onGetIntFromC, data2Java);

}

JNIEXPORT jstring JNICALL
Java_com_hwilliam_jnilearncmake_NDKTools_getStringFromCSync(JNIEnv *env, jclass clazz) {
    char *string = "abcdefg";
    jstring result = (*env)->NewStringUTF(env, string);
    return result;
}

JNIEXPORT void JNICALL
Java_com_hwilliam_jnilearncmake_NDKTools_getStringFromCAsync(JNIEnv *env, jclass clazz) {
    //获取字符串
    char *string = TAG_LIB_B;
    jstring result = (*env)->NewStringUTF(env, string);
    //获取要回调的java方法
    jmethodID onGetStringFromC = (*env)->GetStaticMethodID(env, clazz, "onGetStringFromC",
                                                           "(Ljava/lang/String;)V");
    //回调
    (*env)->CallStaticVoidMethod(env, clazz, onGetStringFromC, result);
    //release
    (*env)->DeleteLocalRef(env, result);

    //以下调用会出错：JNI DETECTED ERROR IN APPLICATION: use of deleted local reference 0x71
//    jboolean isNull = (*env)->IsSameObject(env, result, NULL);
//    if (isNull==1){
//        LOGD("isNULL");
//    } else{
//        LOGD("is Not NULL");
//    }
}

int a() {
    return 666;
}

JNIEXPORT jint JNICALL
Java_com_hwilliam_jnilearncmake_NDKTools_testColideFunction(JNIEnv *env, jclass clazz) {
    return a();
}

static int b(JNIEnv *env) {
    int64_t ptr = (int64_t) (intptr_t) env;
    char valuestr[22];
    snprintf(valuestr, sizeof(valuestr), "%lld", ptr);

    long long ptr2 = strtoll(valuestr, NULL, 10);
    int *p2 = (intptr_t) ptr2;
    LOGD("value of p2=%d", *p2);

    double d = (double) (intptr_t) env;
    JNIEnv *envptr = (JNIEnv *) (intptr_t) d;

    return 1;
}

typedef struct Happy {
    char *cptr;
    int a;
    long b;
} Happy;

JNIEXPORT jint JNICALL
Java_com_hwilliam_jnilearncmake_NDKTools_testErrorFunction(JNIEnv *env, jclass clazz) {
    int age = 25;
    int *p = &age;

    long long ptr = p;
    char valuestr[22];
    snprintf(valuestr, sizeof(valuestr), "%lld", ptr);
    // not crash. maybe it will crash after the ptr is retrieved from valuestr.

    // 可能是因为：指针变量前几位被操作系统打了tag，后面存到long long里面的时候，tag被擦除掉了，转回指针的时候被操作系统检查到了，就报错。

    long long ptr2 = strtoll(valuestr, NULL, 10);
    int *p2 = (intptr_t) ptr2;
    LOGD("value of p2=%d", *p2);

    Happy happy = {
            .cptr="I am Happy",
            .a=10,
            .b=100,
    };
    Happy *hptr = &happy;
    return b(env);
}

JNIEXPORT jlong JNICALL
Java_com_hwilliam_jnilearncmake_NDKToolsObject_testGetFiledFromSelf(JNIEnv *env, jobject thiz) {
    jclass NDKToolsObject = (*env)->FindClass(env, "com/hwilliam/jnilearncmake/NDKToolsObject");
    jfieldID jfieldId = (*env)->GetFieldID(env, NDKToolsObject, "paramA", "J");
    if ((*env)->ExceptionCheck(env)) {
        (*env)->ExceptionDescribe(env);
        (*env)->ExceptionClear(env);
    }
    if (jfieldId == NULL) {
        LOGD("jfieldId==NULL");
        return 0;
    }
    jlong result = (*env)->GetLongField(env, thiz, jfieldId);
    return result;
}