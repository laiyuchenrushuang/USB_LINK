package com.seatrend.usb_online.util;

import android.content.Context;
import android.text.TextUtils;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import com.seatrend.usb_online.MyApplication;
import com.seatrend.usb_online.R;

import java.util.HashMap;

public class DMZUtils {
    private static DMZUtils dmzUtils;
    private static Context mContext;

    public static DMZUtils getInstance() {

        if (dmzUtils == null) {
            synchronized (DMZUtils.class) {
                if (dmzUtils == null) {
                    dmzUtils = new DMZUtils();
                }
            }
        }
        return dmzUtils;
    }

    public static HashMap<String, String> getDmzMap(Context context) {
        mContext = context;
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("X", context.getString(R.string.x));
        map.put(context.getString(R.string.x), "X");

        map.put("Y", context.getString(R.string.y));
        map.put(context.getString(R.string.y), "Y");

        map.put("R", context.getString(R.string.r));
        map.put(context.getString(R.string.r), "R");

        map.put("N", context.getString(R.string.n));
        map.put(context.getString(R.string.n), "N");

//        map.put("CH",context.getString(R.string.dipan));
//        map.put(context.getString(R.string.dipan),"CH");

        map.put("S0", context.getString(R.string.sld));
//        map.put(context.getString(R.string.sld), "S0");

        map.put("S3", context.getString(R.string.sdd));
//        map.put(context.getString(R.string.sdd), "S3");

        map.put("M5", context.getString(R.string.tx));
//        map.put(context.getString(R.string.tx), "M5");

        map.put("S6", context.getString(R.string.jly));
//        map.put(context.getString(R.string.jly), "S6");

        map.put("T1", context.getString(R.string.transducer));
        map.put(context.getString(R.string.transducer), "T1");
//
//        map.put("PA", context.getString(R.string.paper));
//        map.put(context.getString(R.string.paper), "PA");
//
//        map.put("EL", context.getString(R.string.electronic));
//        map.put(context.getString(R.string.electronic), "EL");
        return map;
    }

    /**
     * 取得代码名称
     *
     * @param key
     * @return
     */
    public static String getDmmc(String key) {
        HashMap<String, String> map = getDmzMap(mContext);
        return map.get(key);
    }

    /**
     * 取得代码值
     *
     * @param dmmc
     * @return
     */
    public static String getDmz(String dmmc) {
        HashMap<String, String> map = getDmzMap(mContext);
        return map.get(dmmc);
    }

    /**
     * spinner 设置为指定数据
     *
     * @param dmsm
     * @param spinner
     */
    public static void setSpinner2Dmsm(String dmsm, Spinner spinner) {
        if (TextUtils.isEmpty(dmsm)) {
            return;
        }
        ArrayAdapter<String> adapter = (ArrayAdapter<String>) spinner.getAdapter();
        int count = adapter.getCount();
        for (int i = 0; i < count; i++) {
            String item = adapter.getItem(i);
            if (dmsm.equals(item)) {
                spinner.setSelection(i);
                break;
            }
        }
    }
}
