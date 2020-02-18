package com.seatrend.usb_online

import android.app.Application
import android.content.Context
import com.seatrend.usb_online.enity.LoadDayDataEnity
import com.seatrend.usb_online.enity.NewData
import com.seatrend.usb_online.enity.SocketEnity
import com.seatrend.usb_online.util.LanguageUtil


/**
 * Created by ly on 2020/1/3 13:18
 *
 * Copyright is owned by chengdu haicheng technology
 * co., LTD. The code is only for learning and sharing.
 * It is forbidden to make profits by spreading the code.
 */
class MyApplication : Application() {
    companion object {
        var mContext: Context? = null

        var mAllData = ArrayList<NewData>()

        var mGetTodayData = ArrayList<LoadDayDataEnity.DATA_LIST>()


        var CLLX = ""  // 0 预约  |||||     1 车牌
        var HM_CODE = ""

        @Synchronized
        fun getContext(): Context {
            return mContext!!
        }
    }

    override fun onCreate() {
        super.onCreate()
        mContext = this
        languageWork()
    }

    private fun languageWork() {
        val locale = LanguageUtil.getLocale(this)
        LanguageUtil.updateLocale(this, locale)
    }
}