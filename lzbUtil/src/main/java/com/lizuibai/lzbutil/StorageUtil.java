package com.lizuibai.lzbutil;

import android.content.Context;
import android.os.Environment;
import android.text.TextUtils;

import java.io.File;

public class StorageUtil {
    private static String mCacheRootDir = null;

    private static boolean mUseExternalCache = true;

    public static String getCacheRootDir() {
        if (TextUtils.isEmpty(mCacheRootDir)) {
            mCacheRootDir = setCacheRootDir();
        }

        return mCacheRootDir;
    }

    public static String setCacheRootDir() {
        setCacheRootDir(LzbU.CONTEXT, mUseExternalCache, true);
        return mCacheRootDir;
    }

    private static void setCacheRootDir(Context context, boolean preferExternal, boolean withLastSeparator) {
        File appCacheDir = null;

        if (preferExternal && hasSDCardMounted()) {
            appCacheDir = getExternalCacheDir(context);
        }

        if (appCacheDir == null) {
            appCacheDir = getInternalCacheDir(context);
        }

        mCacheRootDir = (appCacheDir != null ? appCacheDir.getAbsolutePath() : "");

        if (withLastSeparator) {
            mCacheRootDir = appendWithSeparator(mCacheRootDir);
        }
    }

    public static synchronized File getRootFile() {
        File appCacheDir = null;

        if (appCacheDir == null) {
            //if (Version.hasKitKat()) {
            if ((appCacheDir = LzbU.CONTEXT.getExternalFilesDir(null)) == null) {
                appCacheDir = new File(new File(Environment.getExternalStorageDirectory(), "Android"),
                        "data");
                appCacheDir = new File(new File(appCacheDir, LzbU.CONTEXT.getPackageName()),
                        "files");
            }
            //}
        }

        if (appCacheDir == null) {
            appCacheDir = new File(Environment.getExternalStorageDirectory(), LzbU.CONTEXT.getPackageName());
        }

        return appCacheDir;
    }

    public static File getExternalCacheDir(Context context) {
        File appCacheDir = null;

        appCacheDir = getRootFile();

        if (appCacheDir != null && !appCacheDir.exists()) {
            if (!appCacheDir.mkdirs()) {
                return createRootFile();
            }
        }

        return appCacheDir;
    }

    public static synchronized File createRootFile() {
        File rootFile = getRootFile();
        if (!rootFile.isDirectory()) {
            //deleteRootFile();
            rootFile.delete();
        }
        if (!rootFile.exists()) {
            File rootParent = rootFile.getParentFile();
            File tempFile = new File(rootParent, "temp");
            if (tempFile.exists()) {
                tempFile.delete();
            }

            if (tempFile.mkdirs()) {
                if (tempFile.renameTo(rootFile)) {
                    return tempFile;
                }
            }
        }
        return null;
    }

    public static String getInternalCacheDir() {
        File appCacheDir = getInternalCacheDir(LzbU.CONTEXT);
        String path = (appCacheDir != null ? appCacheDir.getAbsolutePath() : "");
        return appendWithSeparator(path);
    }

    public static File getInternalCacheDir(Context context) {
        File appCacheDir = null;

        // Android Issue 8886:
        if ((appCacheDir = context.getFilesDir()) == null) {
            String cacheDirPath = "/data/data/" + context.getPackageName() + "/files/";
//			String cacheDirPath = context.getFilesDir().getPath()+"/";
            appCacheDir = new File(cacheDirPath);
        }

        if (appCacheDir != null && !appCacheDir.exists()) {
            if (!appCacheDir.mkdirs() && !appCacheDir.isDirectory()) {
                return null;
            }
        }

        return appCacheDir;
    }

    public static boolean hasSDCardMounted() {
        String state = Environment.getExternalStorageState();
        if (state != null && state.equals(Environment.MEDIA_MOUNTED)) {
            return true;
        } else {
            return false;
        }
    }

    private static String appendWithSeparator(String path) {
        if (!TextUtils.isEmpty(path)) {
            if (!path.substring(path.length() - 1).equals(File.separator)) {
                path += File.separator;
            }
        }

        return path;
    }

    public static boolean isUseExternalCache() {
        return mUseExternalCache;
    }

    public static void setUseExternalCache(boolean mUseExternalCache) {
        StorageUtil.mUseExternalCache = mUseExternalCache;
    }
}
