package com.hwilliam.jnilearncmake;

import com.blankj.utilcode.util.LogUtils;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * date: 2020-06-06
 * author: hwj
 * description:
 */
public class NDKToolsTest {


    @Test
    public void inputInt() {
        LogUtils.d("NDKToolsTest.inputInt");
        NDKTools.inputInt(123);
    }

    @Test
    public void inputString() {
        LogUtils.d("NDKToolsTest.inputString");
    }

    @Test
    public void getIntFromCSync() {
        LogUtils.d("NDKToolsTest.getIntFromCSync");
    }

    @Test
    public void getIntFromCAsync() {
        LogUtils.d("NDKToolsTest.getIntFromCAsync");
    }

    @Test
    public void onGetIntFromC() {
        LogUtils.d("NDKToolsTest.onGetIntFromC");
    }

    @Test
    public void getStringFromCSync() {
        LogUtils.d("NDKToolsTest.getStringFromCSync");
    }

    @Test
    public void getStringFromCAsync() {
        LogUtils.d("NDKToolsTest.getStringFromCAsync");
    }

    @Test
    public void testColideFunction() {
        LogUtils.d("NDKToolsTest.testColideFunction");
    }

    @Test
    public void testErrorFunction() {
        LogUtils.d("NDKToolsTest.testErrorFunction");
    }

    @Test
    public void onGetStringFromC() {
        LogUtils.d("NDKToolsTest.onGetStringFromC");
    }
}