package com.example.lame;

/**
 * date: 2020-01-20
 * author: hwj
 * description:
 */
public class Mp3Encoder {
    static {
        System.loadLibrary("lame");
    }

    public native static void init();
}
