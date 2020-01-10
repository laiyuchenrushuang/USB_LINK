package com.seatrend.usb_online.broadcast

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Handler
import android.os.Message
import android.util.Log
import android.widget.Toast
import com.seatrend.usb_online.R
import com.seatrend.usb_online.util.Constants

class UsbBroatcastReceiver(var mHandler: Handler) : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        if (null != intent && Constants.USB_ACTION.equals(intent.action)) {
            if (intent.extras.getBoolean("connected")) {
                Log.d("lylog","aaaaaa")
                Toast.makeText(context, R.string.usb_connected,Toast.LENGTH_SHORT).show()
                var msg = Message.obtain()
                msg.what = Constants.USB_CONNECTED
                mHandler.sendMessage(msg)
            } else {
                Log.d("lylog","BBBBB")
                Toast.makeText(context,R.string.usb_off,Toast.LENGTH_SHORT).show()
                var msg = Message.obtain()
                msg.what = Constants.USB_EXIT
                mHandler.sendMessage(msg)
            }
        }
    }
}