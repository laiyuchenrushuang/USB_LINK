package com.seatrend.usb_online.broadcast

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Message
import com.seatrend.usb_online.MainActivity
import com.seatrend.usb_online.util.Constants
import device.scanner.ScanConst

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