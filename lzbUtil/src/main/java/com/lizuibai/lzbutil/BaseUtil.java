package com.lizuibai.lzbutil;

import java.util.List;
import java.util.Map;

public class BaseUtil {

    public boolean isEmpty(List list) {
        return list == null || list.isEmpty();
    }

    public boolean isEmpty(Map map) {
        return map == null || map.isEmpty();
    }

    public boolean isEmpty(String[] arr) {
        return arr == null || arr.length == 0;
    }

    public boolean isEmpty(int[] arr) {
        return arr == null || arr.length == 0;
    }

    public boolean isEmpty(float[] arr) {
        return arr == null || arr.length == 0;
    }

    public boolean isEmpty(double[] arr) {
        return arr == null || arr.length == 0;
    }

    public boolean isEmpty(long[] arr) {
        return arr == null || arr.length == 0;
    }
}
