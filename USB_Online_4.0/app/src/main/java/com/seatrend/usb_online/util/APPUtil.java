package com.seatrend.usb_online.util;

import android.os.Environment;
import android.util.Log;

import java.io.*;

/**
 * Created by ly on 2019/7/30 10:59
 * <p>
 * Copyright is owned by chengdu haicheng technology
 * co., LTD. The code is only for learning and sharing.
 * It is forbidden to make profits by spreading the code.
 */
public class APPUtil {
    public static void write(String content) {
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) { // 如果sdcard存在
            File file = new File(Environment.getExternalStorageDirectory()
                    .toString()
                    + "/USB_ONLINE");
            if (!file.exists()) {
                file.mkdirs();
            }
            File f = new File(file, "usb_log.txt");
            OutputStream out = null; // 打印流对象用于输出
            try {
                out = new FileOutputStream(f, true); // 追加文件
                out.write(content.getBytes());
                out.write("\r\n".getBytes());//写入换行
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (out != null) {
                    try {
                        out.close(); // 关闭打印流
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public static void clearLogfile() {
        Log.d("lylog","delete file ");
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) { // 如果sdcard存在
            File filed = new File(Environment.getExternalStorageDirectory()
                    .toString()
                    + "/USB_ONLINE");
            File file = new File(filed, "usb_log.txt");
            if (file.exists()) {
                FileWriter fileWriter = null;
                try {
                    fileWriter = new FileWriter(file);
                    fileWriter.write("");
                    fileWriter.flush();
                    fileWriter.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
