package com.seatrend.usb_online.receive

import android.util.Log
import android.widget.Toast
import com.seatrend.usb_online.util.Constants
import java.net.ServerSocket
import android.net.Proxy.getPort
import android.os.Handler
import android.system.Os.accept
import java.io.*

class MyReceiveThread(var mHandler: Handler, var serverSocket: ServerSocket) : Thread() {

    override fun run() {
        //=====One====   本地测试
//        while (true) {
//            try {
//                sleep(3000)
//                Log.d("lylog->[receive]", "running")
//                val socket = serverSocket.accept()
//                Log.d("lylog->[receive]", "accept")
//                val dis = DataInputStream(socket.getInputStream())
//                Log.d("lylog->[receive]", "receive data")
//                val msg = mHandler.obtainMessage()
//                msg.what = Constants.GET_DATA
//                msg.obj = dis.readUTF()
//                mHandler.sendMessage(msg)
//                socket.close()
//            } catch (e: Exception) {
//                val msg = mHandler.obtainMessage()
//                msg.what = Constants.EX_ERROR
//                msg.obj = e.message
//                mHandler.sendMessage(msg)
//            }
//        }

        //======TWO====== 可行方案

        while (true) {

            var inR: BufferedReader? = null
            try {
                sleep(3000)
                val socket = serverSocket.accept()
                inR = BufferedReader(InputStreamReader(socket.getInputStream()))
                val r = inR.readLine()
                val msg = mHandler.obtainMessage()
                msg.what = Constants.GET_DATA
                msg.obj = r
                mHandler.sendMessage(msg)
                socket.close()
            } catch (e:IOException) {
                val msg = mHandler.obtainMessage()
                msg.what = Constants.EX_ERROR
                msg.obj = e.message
                mHandler.sendMessage(msg)
            } finally {
                inR?.close()
            }
        }

    }
}