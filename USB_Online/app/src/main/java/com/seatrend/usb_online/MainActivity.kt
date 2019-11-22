package com.seatrend.usb_online

import android.os.Handler
import android.os.Message
import android.util.Log
import java.io.*
import java.net.Socket
import android.widget.TextView
import com.seatrend.usb_online.broadcast.UsbBroatcastReceiver
import com.seatrend.usb_online.receive.MyReceiveThread
import com.seatrend.usb_online.send.MySendThread
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.Exception
import java.net.ServerSocket
import android.content.IntentFilter
import com.seatrend.usb_online.util.Constants


class MainActivity : BaseActivty() {

    //无网条件下的通信
    var tv1: TextView? = null// 用来显示服务器返回的消息 ：“您好，客户端！”
    var mHandler: MessageHandler? = null
    var mMyReceiveThread: MyReceiveThread? = null
    var mMySendThread: MySendThread? = null
    var serverSocket: ServerSocket? = null
    var socket: Socket? = null
    var mMybroatcast: UsbBroatcastReceiver? = null

    var countT = 0;
    override fun initView() {
        setPageTitle("掌机Demo")
        tv1 = findViewById(R.id.tv)
        mHandler = MessageHandler()
        mMybroatcast = UsbBroatcastReceiver(mHandler!!)
        bindEvent()
        registerBroadcast()
    }

    private fun registerBroadcast() {
        val filter = IntentFilter()
        filter.addAction(Constants.USB_ACTION)
        registerReceiver(mMybroatcast, filter)
    }

    private fun initSoket() {
        try {
            if (serverSocket == null || serverSocket!!.isClosed) {
                serverSocket = ServerSocket(9000)
                socket = serverSocket!!.accept()
            }
        } catch (e: Exception) {
            showToast(e.message.toString())
        }
    }

    private fun deleteSoket() {
        if (serverSocket != null) {
            serverSocket!!.close()
        }
    }


    private fun bindEvent() {

        btnconnect.setOnClickListener {
            if (serverSocket != null && !serverSocket!!.isClosed) {
                mMyReceiveThread = MyReceiveThread(mHandler!!, serverSocket!!)
                mMyReceiveThread!!.start()
            } else {
                showToast("请查看USB是否连接？")
            }
        }
        btncommit.setOnClickListener {
            if (serverSocket != null && !serverSocket!!.isClosed) {
                mMySendThread = MySendThread(mHandler!!, "你好粑粑" + countT++, serverSocket!!)
                mMySendThread!!.start()
            } else {
                showToast("请查看USB是否连接？")
            }
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        resetState()
    }

    private fun resetState() {
        unregisterReceiver(mMybroatcast)
        if (serverSocket != null) {
            serverSocket!!.close()
        }
    }

    inner class MessageHandler : Handler() {

        override fun handleMessage(msg: Message?) {
            when (msg!!.what) {
                Constants.USB_CONNECTED -> {
                    initSoket()
//                    soketConnect()
                }

                Constants.USB_EXIT -> {
                    deleteSoket()
                }

                Constants.GET_DATA -> {
                    tv1!!.text = msg.obj.toString()//接收客户端线程发来的Message对象，用来显示
                }
                Constants.EX_ERROR -> {
                    showToast(if (msg?.obj == null) "EX_ERROR" else msg.obj.toString())
                }
                Constants.POST_DATA -> {
                    showToast("数据发送成功")
                }
            }

        }
    }

    private fun soketConnect() {
        if (serverSocket != null && !serverSocket!!.isClosed) {
            mMyReceiveThread = MyReceiveThread(mHandler!!, serverSocket!!)
            mMyReceiveThread!!.start()
        }
    }


    override fun getLayout(): Int {
        return R.layout.activity_main
    }
}




