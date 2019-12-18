package com.seatrend.usb_online.util;

/**
 * Created by ly on 2019/11/18 17:48
 * <p>
 * Copyright is owned by chengdu haicheng technology
 * co., LTD. The code is only for learning and sharing.
 * It is forbidden to make profits by spreading the code.
 */
public class Constants {

    public static final int USB_CONNECTED = 0x1;
    public static final int USB_EXIT = 0x2;

    public static final int GET_DATA = 0x3;
    public static final int POST_DATA = 0x4;
    public static final int EX_ERROR = 0x5;

    public static final int SCANER_RESULT =0xA1;

    public static String USB_ACTION = "android.hardware.usb.action.USB_STATE";
    public static String HEADER = "application/json";
    public static String TOKEN = "";
    public static String GET = "GET";
    public static String POST = "POST";
    public static String IP;
    public static String PORT;
    public static String READ_FAIL = "READ_FAIL";


}
