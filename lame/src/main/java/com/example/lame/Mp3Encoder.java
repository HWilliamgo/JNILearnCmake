package com.example.lame;

/**
 * date: 2020-01-20
 * author: hwj
 * description:
 */
public class Mp3Encoder {
    static {
//        System.loadLibrary("lame");
    }

    public native static void init(String pcmFilePath, String mp3FilePath, int sampleRate, int channels, int bitrate);
    public native static void encode();
    public native static void destroy();
}
