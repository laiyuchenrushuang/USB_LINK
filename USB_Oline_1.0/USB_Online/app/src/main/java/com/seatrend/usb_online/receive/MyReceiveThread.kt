package com.seatrend.usb_online.receive

import android.util.Log
import android.widget.Toast
import com.seatrend.usb_online.MainActivity
import com.seatrend.usb_online.util.Constants
import java.io.DataInputStream
import java.net.ServerSocket

/**
 * Created by ly on 2019/11/20 17:55
 *
 * Copyright is owned by chengdu haicheng technology
 * co., LTD. The code is only for learning and sharing.
 * It is forbidden to make profits by spreading the code.
 */
class MyReceiveThread(var mHandler: MainActivity.MessageHandler, var serverSocket: ServerSocket) : Thread() {

    override fun run() {
        try {
            Log.d("lylog->[receive]", "running")
            val socket = serverSocket.accept()
            Log.d("lylog->[receive]", "accept")
            val dis = DataInputStream(socket.getInputStream())
            Log.d("lylog->[receive]", "receive data")
            val msg = mHandler.obtainMessage()
            msg.what = Constants.GET_DATA
            msg.obj = dis.readUTF()
            mHandler.sendMessage(msg)
            socket.close()
        } catch (e: Exception) {
            val msg = mHandler.obtainMessage()
            msg.what = Constants.EX_ERROR
            msg.obj = e.message
            mHandler.sendMessage(msg)
        }
    }
}