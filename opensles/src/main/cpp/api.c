#include <SLES/OpenSLES.h>
#include <SLES/OpenSLES_Android.h>
#include <jni.h>
#include <stdint.h>

/**
 * 创建opensles引擎
 * @return SLObjectItf
 */

SLObjectItf createSLEngine() {
    SLObjectItf engineObject;
    SLEngineOption engineOptions[] = {{(SLuint32) SL_ENGINEOPTION_THREADSAFE, (SLuint32) SL_BOOLEAN_TRUE}};
    slCreateEngine(&engineObject, sizeof(engineOptions), engineOptions, 0, 0, 0);
    return engineObject;
}

SLresult realizeObject(SLObjectItf object) {
    return (*object)->Realize(object, SL_BOOLEAN_FALSE);
}

void useCase() {
    //创建SL引擎
    SLObjectItf engineObject = createSLEngine();
    realizeObject(engineObject);

    //创建接口引擎
    SLEngineItf engineEngine;
    (*engineObject)->GetInterface(engineObject, SL_IID_ENGINE, &engineEngine);

    //创建Mix对象
    SLObjectItf outputMixObject;
    (*engineEngine)->CreateOutputMix(engineEngine, &outputMixObject, 0, 0, 0);

    //创建音频播放器
    SLObjectItf audioPlayerObject;
    (*engineEngine)->CreateAudioPlayer(engineEngine, &audioPlayerObject, NULL, NULL, 0, 0, 0);



    realizeObject(outputMixObject);


}

void destroy() {

}
