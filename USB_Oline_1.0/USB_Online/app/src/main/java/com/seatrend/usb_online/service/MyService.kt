package com.seatrend.usb_online.service

import android.app.Service
import android.content.Intent
import android.os.IBinder

/**
 * Created by ly on 2019/11/20 18:36
 *
 * Copyright is owned by chengdu haicheng technology
 * co., LTD. The code is only for learning and sharing.
 * It is forbidden to make profits by spreading the code.
 */
class MyService : Service() {
    override fun onBind(p0: Intent?): IBinder? {
        return null
    }
}