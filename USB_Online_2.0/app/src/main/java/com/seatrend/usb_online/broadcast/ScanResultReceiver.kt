package com.seatrend.usb_online.broadcast

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Message
import com.seatrend.usb_online.MainActivity
import com.seatrend.usb_online.util.Constants
import device.scanner.ScanConst

/**
 * Created by ly on 2019/11/26 13:34
 *
 * Copyright is owned by chengdu haicheng technology
 * co., LTD. The code is only for learning and sharing.
 * It is forbidden to make profits by spreading the code.
 */
class ScanResultReceiver(var mHandler: MainActivity.MessageHandler) : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        if (ScanConst.INTENT_USERMSG == intent!!.action) {
            var msg = Message.obtain()
            msg.what = Constants.SCANER_RESULT
            msg.obj = intent
            mHandler.sendMessage(msg)
        }
    }
}