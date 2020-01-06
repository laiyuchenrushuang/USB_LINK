package com.seatrend.usb_online.util;

import android.text.TextUtils;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import com.seatrend.usb_online.MyApplication;
import com.seatrend.usb_online.R;

import java.util.HashMap;

public class DMZUtils {
    private static HashMap<String, String> getDmzMap() {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("X", MyApplication.Companion.getContext().getResources().getString(R.string.x));
        map.put(MyApplication.Companion.getContext().getResources().getString(R.string.x), "X");

        map.put("Y", MyApplication.Companion.getContext().getResources().getString(R.string.y));
        map.put(MyApplication.Companion.getContext().getResources().getString(R.string.y), "Y");

        map.put("R", MyApplication.Companion.getContext().getResources().getString(R.string.r));
        map.put(MyApplication.Companion.getContext().getResources().getString(R.string.r), "R");

        map.put("N", MyApplication.Companion.getContext().getResources().getString(R.string.n));
        map.put(MyApplication.Companion.getContext().getResources().getString(R.string.n), "N");

        map.put("CH",MyApplication.Companion.getContext().getResources().getString(R.string.dipan));
        map.put(MyApplication.Companion.getContext().getResources().getString(R.string.dipan),"CH");

        map.put("SL",MyApplication.Companion.getContext().getResources().getString(R.string.sld));
        map.put(MyApplication.Companion.getContext().getResources().getString(R.string.sld),"SL");

        map.put("SD",MyApplication.Companion.getContext().getResources().getString(R.string.sdd));
        map.put(MyApplication.Companion.getContext().getResources().getString(R.string.sdd),"SD");

        map.put("TA",MyApplication.Companion.getContext().getResources().getString(R.string.tx));
        map.put(MyApplication.Companion.getContext().getResources().getString(R.string.tx),"TA");

        map.put("PA",MyApplication.Companion.getContext().getResources().getString(R.string.paper));
        map.put(MyApplication.Companion.getContext().getResources().getString(R.string.paper),"PA");

        map.put("EL",MyApplication.Companion.getContext().getResources().getString(R.string.electronic));
        map.put(MyApplication.Companion.getContext().getResources().getString(R.string.electronic),"EL");
        return map;
    }

    /**
     * 取得代码名称
     * @param key
     * @return
     */
    public static String getDmmc(String key){
        HashMap<String,String> map = getDmzMap();
        return map.get(key);
    }

    /**
     * 取得代码值
     * @param dmmc
     * @return
     */
    public static String getDmz(String dmmc){
        HashMap<String,String> map = getDmzMap();
        return map.get(dmmc);
    }

    /**
     * spinner 设置为指定数据
     * @param dmsm
     * @param spinner
     */
    public static void setSpinner2Dmsm(String dmsm, Spinner spinner){
        if(TextUtils.isEmpty(dmsm)){return;}
        ArrayAdapter<String> adapter = (ArrayAdapter<String>) spinner.getAdapter();
        int count = adapter.getCount();
        for (int i = 0; i <count; i++) {
            String item = adapter.getItem(i);
            if(dmsm.equals(item)){
                spinner.setSelection(i);
                break;
            }
        }
    }
}
