package com.seatrend.usb_online.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.LocaleList;
import android.support.annotation.RequiresApi;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import com.google.gson.Gson;
import com.seatrend.usb_online.enity.DataEnity;

import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by ly on 2020/1/3 14:46
 * <p>
 * Copyright is owned by chengdu haicheng technology
 * co., LTD. The code is only for learning and sharing.
 * It is forbidden to make profits by spreading the code.
 */
public class LanguageUtil {
    /**
     * 中文
     */
    public static final Locale LOCALE_CHINESE = Locale.CHINESE;
    /**
     * 英文
     */
    public static final Locale LOCALE_ENGLISH = Locale.ENGLISH;
    /**
     * 俄文
     */
    public static final Locale LOCALE_RUSSIAN = new Locale("ru");

    private static final String LOCALE_SP = "LOCALE_SP";
    private static final String LOCALE_SP_KEY = "LOCALE_SP_KEY";
    private static final String LOCALE_SP_DATA = "LOCALE_SP_DATA";
    private static final String LOCALE_DATA = "LOCALE_DATA";
    private static final String LOCALE_SP_BJ = "LOCALE_SP_BJ";




    public static Locale getLocale(Context context) {
        SharedPreferences spLocale = context.getSharedPreferences(LOCALE_SP, Context.MODE_PRIVATE);
        String localeJson = spLocale.getString(LOCALE_SP_KEY, "");
        Gson gson = new Gson();
        return gson.fromJson(localeJson, Locale.class);
    }


    private static void setLocale(Context pContext, Locale pUserLocale) {
        SharedPreferences spLocal = pContext.getSharedPreferences(LOCALE_SP, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = spLocal.edit();
        String json = new Gson().toJson(pUserLocale);
        edit.putString(LOCALE_SP_KEY, json);
        edit.apply();
    }

    public static ArrayList<DataEnity.DATA> getDataFromSP(Context context) {
        SharedPreferences spLocale = context.getSharedPreferences(LOCALE_DATA, Context.MODE_PRIVATE);
        String data = spLocale.getString(LOCALE_SP_DATA, "");
        return GsonUtils.jsonToArrayList(data,DataEnity.DATA.class);
    }


    public static void setDataToSp(Context pContext, ArrayList<DataEnity.DATA> data) {
        SharedPreferences spLocal = pContext.getSharedPreferences(LOCALE_DATA, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = spLocal.edit();
        String json = new Gson().toJson(data);
        edit.putString(LOCALE_SP_DATA, json);
        edit.apply();
    }

    public static String getBJFromSP(Context context) {
        SharedPreferences spLocale = context.getSharedPreferences(LOCALE_DATA, Context.MODE_PRIVATE);
        return spLocale.getString(LOCALE_SP_BJ, "");
    }



    public static void setBJToSp(Context pContext, String data) {
        SharedPreferences spLocal = pContext.getSharedPreferences(LOCALE_DATA, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = spLocal.edit();
        edit.putString(LOCALE_SP_BJ, data);
        edit.apply();
    }

    public static String getTitleFromSP(Context context,String key) {
        SharedPreferences spLocale = context.getSharedPreferences(LOCALE_DATA, Context.MODE_PRIVATE);
        return spLocale.getString(key, "");
    }

    public static void setTitleToSp(Context pContext, String key,String data) {
        SharedPreferences spLocal = pContext.getSharedPreferences(LOCALE_DATA, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = spLocal.edit();
        edit.putString(key, data);
        edit.apply();
    }

    public static boolean updateLocale(Context context, Locale locale) {
        if (needUpdateLocale(context, locale)) {
            Configuration configuration = context.getResources().getConfiguration();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                configuration.setLocale(locale);
            } else {
                configuration.locale = locale;
            }
            DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
            context.getResources().updateConfiguration(configuration, displayMetrics);
            setLocale(context, locale);
            return true;
        }
        return false;
    }

    public static boolean needUpdateLocale(Context pContext, Locale newUserLocale) {
        return newUserLocale != null && !getCurrentLocale(pContext).equals(newUserLocale);
    }
    public static Locale getCurrentLocale(Context context) {
        Locale locale;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) { //7.0有多语言设置获取顶部的语言
            locale = context.getResources().getConfiguration().getLocales().get(0);
        } else {
            locale = context.getResources().getConfiguration().locale;
        }
        return locale;
    }


    //========================方案二========
    public static Context initAppLanguage(Context context, String language) {

        if (currentLanguageIsSimpleChinese(context)) {
            Log.e("LanguageUtil", "当前是简体中文");
            return context;
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            //7.0及以上的方法
            Log.e("LanguageUtil", "7.0及以上");
            return createConfiguration(context, language);
        } else {
            Log.e("LanguageUtil", "7.0以下");
            updateConfiguration(context, language);
            return context;
        }
    }

    /**
     * 7.0及以上的修改app语言的方法
     *
     * @param context  context
     * @param language language
     * @return context
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    private static Context createConfiguration(Context context, String language) {
        Resources resources = context.getResources();
        Locale locale = new Locale(language);
        Configuration configuration = resources.getConfiguration();
        configuration.setLocale(locale);
        LocaleList localeList = new LocaleList(locale);
        LocaleList.setDefault(localeList);
        configuration.setLocales(localeList);
        return context.createConfigurationContext(configuration);
    }

    /**
     * 7.0以下的修改app语言的方法
     *
     * @param context  context
     * @param language language
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    private static void updateConfiguration(Context context, String language) {
        Resources resources = context.getResources();
        Locale locale = new Locale(language);
        Locale.setDefault(locale);
        Configuration configuration = resources.getConfiguration();
        configuration.setLocale(locale);
        DisplayMetrics displayMetrics = resources.getDisplayMetrics();
        resources.updateConfiguration(configuration, displayMetrics);
    }

    /**
     * 判断当前的语言是否是简体中文
     *
     * @param context context
     * @return boolean
     */
    private static boolean currentLanguageIsSimpleChinese(Context context) {
        String language;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            language = context.getResources().getConfiguration().getLocales().get(0).getLanguage();
        } else {
            language = context.getResources().getConfiguration().locale.getLanguage();
        }
        Log.e("LanguageUtil", "language = " + language);
        return TextUtils.equals("zh", language);
    }



}