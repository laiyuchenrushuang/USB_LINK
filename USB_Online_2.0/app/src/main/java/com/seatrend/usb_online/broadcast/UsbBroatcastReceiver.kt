package com.seatrend.usb_online.broadcast

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Message
import android.util.Log
import android.widget.Toast
import com.seatrend.usb_online.MainActivity
import com.seatrend.usb_online.util.Constants

/**
 * Created by ly on 2019/11/20 18:09
 *
 * Copyright is owned by chengdu haicheng technology
 * co., LTD. The code is only for learning and sharing.
 * It is forbidden to make profits by spreading the code.
 */
class UsbBroatcastReceiver(var mHandler: MainActivity.MessageHandler) : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        if (null != intent && Constants.USB_ACTION.equals(intent.action)) {
            if (intent.extras.getBoolean("connected")) {
                Toast.makeText(context,"Usb connection successful.",Toast.LENGTH_SHORT).show()
                var msg = Message.obtain()
                msg.what = Constants.USB_CONNECTED
                mHandler.sendMessage(msg)
            } else {
                Toast.makeText(context,"Usb disconnect.",Toast.LENGTH_SHORT).show()
                var msg = Message.obtain()
                msg.what = Constants.USB_EXIT
                mHandler.sendMessage(msg)
            }
        }
    }
}