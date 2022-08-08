package com.lizuibai.lzbutil;

import android.util.Log;

import java.io.File;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class FLog {

    private static Executor EXECUTOR = Executors.newSingleThreadExecutor();
    private static File dir = new File(U.CONTEXT.getExternalFilesDir(null), "onlineLog");

    private static Object writeLock = new Object();

    private static SimpleDateFormat logFileFormatter = new SimpleDateFormat("yy.MM.dd", Locale.US);
    private static boolean IS_DEBUG = U.isDebug;
    private static String logPath = StorageUtil.getCacheRootDir() + "/log";
    private static String processName = "Main";
    private static long todayTick = 0L;
    private static String curFileName = "";

    private static FileWriter writer;
    private static long tickPerHour = 24 * 60 * 60 * 1000;

    public static final SimpleDateFormat timeFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS", Locale.US);

    private static String getDayString(long nowCurrentMillis) {
        if ((nowCurrentMillis - todayTick) > tickPerHour) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(nowCurrentMillis);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MILLISECOND, 0);
            todayTick = calendar.getTimeInMillis();

            String dayString = logFileFormatter.format(calendar.getTime());

            return dayString;
        } else {
            return curFileName;
        }
    }

    private static void logToFile(String tag, String level, String msg, Throwable tr) {
        EXECUTOR.execute(new FlogTask(level, tag, msg, tr));
    }

    public static void e(String tag, String msg) {
        e(tag, msg, null);
    }

    public static void e(String tag, String msg, Throwable tr) {
        if (!IS_DEBUG) {
            return;
        }

        if (null == tr) {
            Log.e(tag, msg);
        } else {
            Log.e(tag, msg, tr);
        }
        logToFile(tag, "[e]", msg, tr);
    }

    public static void w(String tag, String msg) {
        w(tag, msg, null);
    }

    public static void w(String tag, String msg, Throwable tr) {
        if (!IS_DEBUG) {
            return;
        }

        if (null == tr) {
            Log.w(tag, msg);
        } else {
            Log.w(tag, msg, tr);
        }
        logToFile(tag, "[w]", msg, tr);
    }

    public static void i(String tag, String msg) {
        i(tag, msg, null);
    }

    public static void i(String tag, String msg, Throwable tr) {
        if (!IS_DEBUG) {
            return;
        }

        if (null == tr) {
            Log.i(tag, msg);
        } else {
            Log.i(tag, msg, tr);
        }
        logToFile(tag, "[i]", msg, tr);
    }

    public static void d(String tag, String msg) {
        d(tag, msg, null);
    }

    public static void d(String tag, String msg, Throwable tr) {
        if (!IS_DEBUG) {
            return;
        }

        if (null == tr) {
            Log.d(tag, msg);
        } else {
            Log.d(tag, msg, tr);
        }
        logToFile(tag, "[d]", msg, tr);
    }

    public static void v(String tag, String msg) {
        v(tag, msg, null);
    }

    public static void v(String tag, String msg, Throwable tr) {
        if (!IS_DEBUG) {
            return;
        }

        logToFile(tag, "[v]", msg, tr);
    }

    public static void printStack() {
        if (!IS_DEBUG) {
            return;
        }

        StackTraceElement[] ste = new Throwable().getStackTrace();
        if (ste != null && ste.length > 1) {
            for (int i = 1; i < ste.length; i++) {
                i("Trace", ste[i].toString());
            }
        }
    }

    private static void write(String tag, String msg) {
        if (!dir.exists()) {
            if (!dir.mkdirs()) {
                return;
            }
        }
    }

    private static class FlogTask implements Runnable {

        private String level;
        private String tag;
        private String msg;
        private Throwable tr;

        public FlogTask(String level, String tag, String msg, Throwable tr) {
            this.level = level;
            this.tag = tag;
            this.msg = msg;
            this.tr = tr;
        }

        @Override
        public void run() {
            try {
                File logDir = new File(logPath);
                if (!logDir.exists()) {
                    logDir.mkdirs();
                }

                String dayString = getDayString(System.currentTimeMillis());
                if (!dayString.equals(curFileName)) {
                    String logFileString = logPath + processName + dayString + ".txt";
                    File logFile = new File(logFileString);
                    if (writer != null) {
                        U.close(writer);
                    }
                    if (!logFile.exists()) {
                        logFile.createNewFile();
                        writer = new FileWriter(logFile);
                    } else {
                        writer = new FileWriter(logFile, true);
                    }
                    curFileName = logFileString;
                }

                String logTime = timeFormatter.format(Long.valueOf(System.currentTimeMillis()));
                writer.write(logTime + "|" + processName + "|" + level + "|" + tag + "|");
                writer.write(msg);
                writer.write("\r\n");

                if (null != tr) {
                    writer.write(Log.getStackTraceString(tr) + "\r\n");
                }
                writer.flush();
            } catch (Throwable e) {
                Log.d("FLog", "ERROR");
            }
        }
    }
}
