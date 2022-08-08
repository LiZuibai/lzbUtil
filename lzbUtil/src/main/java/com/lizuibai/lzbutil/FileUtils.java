package com.lizuibai.lzbutil;

import android.graphics.Bitmap;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;

public class FileUtils {
    private static final String[] SIZE_UNIT = {"B", "K", "M", "G", "T"};
    private static final int BUFF_SIZE = 4096;

    public static File saveBitmapFile(Bitmap bitmap, String filepath) {
        File file = new File(filepath);//将要保存图片的路径
        BufferedOutputStream bos = null;
        try {
            bos = new BufferedOutputStream(new FileOutputStream(file));
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
            bos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            LzbU.close(bos);
        }
        return file;
    }

    public static void copy(File src, File dest) {
        if (src.isFile()) {
            copySingleFile(src, dest);
            return;
        }
        File[] arr = src.listFiles();
        if (!dest.exists()) {
            dest.mkdirs();
        }
        Arrays.sort(arr, (o1, o2) -> {
            if (o1.isFile()) {
                if (o2.isFile()) {
                    return o1.getName().compareTo(o2.getName());
                }
                return -1;
            }
            if (o2.isFile()) {
                return 1;
            }
            return o1.getName().compareTo(o2.getName());
        });
        for (File f : arr) {
            copy(f, new File(dest, f.getName()));
        }
    }

    private static Throwable copySingleFile(File in, File out) {
        FileInputStream inputStream = null;
        FileOutputStream outStream = null;
        try {
            inputStream = new FileInputStream(in);
            outStream = new FileOutputStream(out);
            byte[] buff = new byte[BUFF_SIZE];
            final long size = in.length();
            long writeLen = 0;
            int len;
            while ((len = inputStream.read(buff)) >= 0) {
                outStream.write(buff, 0, len);
                writeLen += len;
                if (writeLen * 10 > size) {
                    System.out.print(".");
                    writeLen -= size / 10;
                }
            }
            outStream.flush();
            inputStream.close();
            outStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return e;
        } catch (IOException e) {
            e.printStackTrace();
            return e;
        } finally {
            LzbU.close(outStream);
            LzbU.close(inputStream);
        }

        return null;
    }

    public static long statFile(File file, int level) {
        long size = 0;
        if (file.isFile()) {
            size = file.length();
        } else {
            File[] files = file.listFiles();
            for (File item : files) {
                size += statFile(item, level + 1);
            }
        }
        System.out.println(blank(level) + file.getName() + " " + readableSize(size));
        return size;
    }

    public static String blank(int level) {
        StringBuilder builder = new StringBuilder();
        while (level-- > 0) {
            builder.append("  ");
        }
        return builder.toString();
    }

    public static String readableSize(long size) {
        if (size <= 0) {
            return "0";
        }
        long[] sizeStr = new long[SIZE_UNIT.length];
        int i = 0;
        while (size > 0) {
            sizeStr[i++] = size % 1024;
            size = size >> 10;
        }
        i--;
        return sizeStr[i] + SIZE_UNIT[i] + (i > 0 ? sizeStr[i - 1] + SIZE_UNIT[i - 1] : "");
    }

}
