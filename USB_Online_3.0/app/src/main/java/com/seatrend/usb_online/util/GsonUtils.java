package com.seatrend.usb_online.util;

import android.text.TextUtils;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;


public class GsonUtils {

    private static Gson gson = new Gson();

    public static <T> T gson(String json, Class<T> clas) throws JsonSyntaxException {
        return gson.fromJson(json, clas);
    }

    public static String toJson(Object o) {
        return gson.toJson(o);
    }

    public static <T> ArrayList<T> jsonToArrayList(String json, Class<T> clazz) {
        ArrayList<T> arrayList = new ArrayList<>();
        if (TextUtils.isEmpty(json) || json == null) {
            return arrayList;
        }
        Type type = new TypeToken<ArrayList<JsonObject>>() {
        }.getType();
        ArrayList<JsonObject> jsonObjects = new Gson().fromJson(json, type);

        for (JsonObject jsonObject : jsonObjects) {
            arrayList.add(new Gson().fromJson(jsonObject, clazz));
        }
        return arrayList;
    }
}
