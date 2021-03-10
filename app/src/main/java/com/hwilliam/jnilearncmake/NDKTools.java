package com.hwilliam.jnilearncmake;

import com.blankj.utilcode.util.LogUtils;

public class NDKTools {

    static {
        System.loadLibrary("native-lib");
    }

    // <editor-fold defaultstate="collapsed" desc="从java传递数据到c">

    public static native void inputInt(int intData);

    public static native void inputString(String stringData);
    // </editor-fold>


    // <editor-fold defaultstate="collapsed" desc="从c传递数据到java">

    public static native int getIntFromCSync();

    public static native void getIntFromCAsync();

    public static void onGetIntFromC(int dataFromC) {
        LogUtils.d(dataFromC);
    }

    public static native String getStringFromCSync();

    public static native void getStringFromCAsync();

    public static native int testColideFunction();

    public static native int testErrorFunction();

    public static void onGetStringFromC(String dataFromC) {
        LogUtils.d(dataFromC);
    }

    // </editor-fold>
}