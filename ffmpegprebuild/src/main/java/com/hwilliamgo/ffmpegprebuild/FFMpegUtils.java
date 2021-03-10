package com.hwilliamgo.ffmpegprebuild;

/**
 * date: 2020-02-04
 * author: hwj
 * description:
 */
public class FFMpegUtils {
    static {
        System.loadLibrary("avformat");
        System.loadLibrary("ff");
    }
    public static native int getVersion();
}
