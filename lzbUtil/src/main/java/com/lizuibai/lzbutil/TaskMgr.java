package com.lizuibai.lzbutil;

import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class TaskMgr {

    //创建三个线程池，分别处理不同的工作
    public static final int IO_OPERATION = 1;//io读写
    public static final int HEAVY_OPERATION = 2;//重量级操作
    public static final int LIGHT_OPERATION = 3;//轻量级操作

    private static int maxSize = 16;// >1

    public static final Handler HANDLER = new Handler(Looper.getMainLooper());

    private static ArrayBlockingQueue<Runnable> ioQueue = new ArrayBlockingQueue<Runnable>(maxSize);
    private static ArrayBlockingQueue<Runnable> heavyQueue = new ArrayBlockingQueue<Runnable>(maxSize);
    private static ArrayBlockingQueue<Runnable> lightQueue = new ArrayBlockingQueue<Runnable>(maxSize);

    private static ThreadPoolExecutor ioExecutor = new ThreadPoolExecutor(2, 4, 2L, TimeUnit.SECONDS, ioQueue);
    private static ThreadPoolExecutor heavyExecutor = new ThreadPoolExecutor(2, 4, 2L, TimeUnit.SECONDS, heavyQueue);
    private static ThreadPoolExecutor lightExecutor = new ThreadPoolExecutor(2, 4, 2L, TimeUnit.SECONDS, lightQueue);

    private TaskMgr() {
    }

    public static void addTaskIntoPool(Runnable runnable, int type) {
        if (runnable == null) {
            return;
        }
        switch (type) {
            case IO_OPERATION:
                ioExecutor.execute(runnable);
                break;
            case HEAVY_OPERATION:
                heavyExecutor.execute(runnable);
                break;
            case LIGHT_OPERATION:
                lightExecutor.execute(runnable);
                break;
            default:
                lightExecutor.execute(runnable);
        }
    }

    public static void runOnUIThread(Runnable runnable) {
        HANDLER.post(runnable);
    }

    public static void runOnUIThread(Runnable runnable, long delay) {
        HANDLER.postDelayed(runnable, delay);
    }

    public static ThreadPoolExecutor getExecutor(int type) {
        switch (type) {
            case IO_OPERATION:
                return ioExecutor;
            case HEAVY_OPERATION:
                return heavyExecutor;
            case LIGHT_OPERATION:
                return lightExecutor;
            default:
                return heavyExecutor;
        }
    }

    public static void addLightTask(Runnable task) {
        lightExecutor.execute(task);
    }

    public static void addIOTask(Runnable task) {
        lightExecutor.execute(task);
    }

    public static void addHeaveTask(Runnable task) {
        heavyExecutor.execute(task);
    }
}
