package com.seatrend.usb_online.util;

import android.text.TextUtils;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.HashMap;

/**
 * Created by ly on 2019/12/17 15:04
 * <p>
 * Copyright is owned by chengdu haicheng technology
 * co., LTD. The code is only for learning and sharing.
 * It is forbidden to make profits by spreading the code.
 */
public class DMZUtils {
    private static HashMap<String, String> getDmzMap() {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("X", "新标记");
        map.put("新标记", "X");

        map.put("Y", "已核实");
        map.put("已核实", "Y");

        map.put("R", "重新核实");
        map.put("已核实", "R");

        map.put("N", "未核实");
        map.put("未核实", "N");

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
