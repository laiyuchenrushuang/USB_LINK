package com.seatrend.usb_online.util;

public class Constants {

    public static final int USB_CONNECTED = 0x1; //usb 连接成功
    public static final int USB_EXIT = 0x2; //usb断开

    public static final int GET_DATA = 0x3;//获取pc消息
    public static final int POST_DATA = 0x4;//发送消息
    public static final int EX_ERROR = 0x5;//解析Error

    public static final int SCANER_RESULT =0xA1; //扫码成功

    public static String USB_ACTION = "android.hardware.usb.action.USB_STATE";
    public static String HEADER = "application/json";
    public static String TOKEN = "";
    public static String GET = "GET";
    public static String POST = "POST";
    public static String IP;
    public static String PORT;
    public static String READ_FAIL = "READ_FAIL";
}
