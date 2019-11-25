package com.seatrend.usb_online.send

import android.util.Log
import com.seatrend.usb_online.MainActivity
import com.seatrend.usb_online.util.Constants
import java.io.DataInputStream
import java.io.DataOutputStream
import java.net.ServerSocket

/**
 * Created by ly on 2019/11/20 18:01
 *
 * Copyright is owned by chengdu haicheng technology
 * co., LTD. The code is only for learning and sharing.
 * It is forbidden to make profits by spreading the code.
 */


class MySendThread(var mHandler: MainActivity.MessageHandler, var msg: String, var serverSocket: ServerSocket) : Thread() {

    override fun run() {
        try {
            Log.d("lylog->[send]", "running")
            val socket = serverSocket.accept()
            Log.d("lylog->[send]", "accept")
            val dos = DataOutputStream(socket.getOutputStream())
            dos.writeUTF(msg)
            val msg = mHandler.obtainMessage()
            msg.what = Constants.POST_DATA
            mHandler.sendMessage(msg)
            Log.d("lylog->[send]", "send data")
            socket.close()
        } catch (e: Exception) {
            val msg = mHandler.obtainMessage()
            msg.what = Constants.EX_ERROR
            msg.obj = e.message
            mHandler.sendMessage(msg)
        }
    }
}