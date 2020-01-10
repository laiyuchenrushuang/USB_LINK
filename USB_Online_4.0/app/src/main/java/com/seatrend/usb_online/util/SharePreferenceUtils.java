package com.seatrend.usb_online.util;

import android.content.Context;
import android.content.SharedPreferences;

public class SharePreferenceUtils {


    private static String SETTING_DATA = "SETTING_DATA";

    public static String getSettingFromSP(Context context, String key) {
        SharedPreferences spLocale = context.getSharedPreferences(SETTING_DATA, Context.MODE_PRIVATE);
        return spLocale.getString(key, "--");
    }

    public static void setSettingToSp(Context pContext, String key,String data) {
        SharedPreferences spLocal = pContext.getSharedPreferences(SETTING_DATA, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = spLocal.edit();
        edit.putString(key, data);
        edit.apply();
    }
}
