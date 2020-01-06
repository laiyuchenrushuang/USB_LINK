package com.seatrend.usb_online

import android.annotation.SuppressLint
import android.os.Handler
import android.os.Message
import com.seatrend.usb_online.broadcast.UsbBroatcastReceiver
import com.seatrend.usb_online.receive.MyReceiveThread
import com.seatrend.usb_online.send.MySendThread
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.Exception
import java.net.ServerSocket
import android.content.IntentFilter
import android.os.RemoteException
import android.os.ServiceManager
import android.support.v7.widget.LinearLayoutManager
import android.text.TextUtils
import com.seatrend.usb_online.adapter.MyAdapter
import com.seatrend.usb_online.broadcast.ScanResultReceiver
import com.seatrend.usb_online.enity.DataEnity
import com.seatrend.usb_online.util.Constants
import com.seatrend.usb_online.util.GsonUtils
import device.scanner.DecodeResult
import device.scanner.IScannerService
import device.scanner.ScanConst
import device.scanner.ScannerService
import java.util.*
import kotlin.collections.ArrayList
import android.util.DisplayMetrics
import com.seatrend.usb_online.util.ActivityManager
import com.seatrend.usb_online.util.LanguageUtil


/**
 * 方案有三，
 * 方案一 soket通信
 * 方案二 通过adb命令，pc可以更改手机sdcard文件，通过文件流通信
 * 方案三 掌机可以通过网线，进行网络通信
 */
class MainActivity : BaseActivty(), MyAdapter.Dback {


    //无网条件下的通信
    private var mHandler: MessageHandler? = null
    private var mMyReceiveThread: MyReceiveThread? = null  //接收的线程
    private var mMySendThread: MySendThread? = null //发送的线程
    private var serverSocket: ServerSocket? = null
    private var mMybroatcast: UsbBroatcastReceiver? = null //USB 的广播
    private var mScanbroatcast: ScanResultReceiver? = null//红外线扫描的广播 为什么分开？
    private var iScanner: IScannerService? = null
    private val mDecodeResult = DecodeResult()
    private var adapter: MyAdapter? = null //列表的适配器
    private var ll: LinearLayoutManager? = null
    private var flag = false  //区分是普通扫描识别还是扫描添加
    private var sendData: DataEnity? = null

    override fun initView() {
        mHandler = MessageHandler()
        mMybroatcast = UsbBroatcastReceiver(mHandler!!)
        mScanbroatcast = ScanResultReceiver(mHandler!!)

        initScanner() //init
        initRecycleView()
        bindEvent()
        registerUSBBroadcast()
        registerScanBroadcast()
    }

    private fun initRecycleView() {
        ll = LinearLayoutManager(this)
        m_recycler_view!!.layoutManager = ll
        if("1".equals(LanguageUtil.getBJFromSP(this))){ //1 证明是点击切换中英按钮来的
            mData = LanguageUtil.getDataFromSP(this)
        }
        adapter = MyAdapter(this, mData)
        adapter!!.setLisDataback(this)
        m_recycler_view.adapter = adapter
        LanguageUtil.setBJToSp(this,"0")
    }

    private fun registerScanBroadcast() {
        val filter = IntentFilter()
        filter.addAction(ScanConst.INTENT_USERMSG)
        filter.addAction(ScanConst.INTENT_EVENT)
        registerReceiver(mScanbroatcast, filter, "com.example.vendor.permission.SCANNER_RESULT_RECEIVER", null)
    }

    private fun registerUSBBroadcast() {
        val filter = IntentFilter()
        filter.addAction(Constants.USB_ACTION)
        registerReceiver(mMybroatcast, filter)
    }

    private fun initSoket() {
        try {
            if (serverSocket == null || serverSocket!!.isClosed) {
                serverSocket = ServerSocket(9000)
                soketConnect()
            }
        } catch (e: Exception) {
            showLog(e.message.toString())
        }
    }

    private fun deleteSoket() {
        if (serverSocket != null) {
            serverSocket!!.close()
        }
    }


    private fun bindEvent() {

//        btnconnect.setOnClickListener {
//            if (serverSocket != null && !serverSocket!!.isClosed) {
//                mMyReceiveThread = MyReceiveThread(mHandler!!, serverSocket!!)
//                mMyReceiveThread!!.start()
//            } else {
//                showToast("Please check whether USB is connected.")
//            }
//        }

//        btncommit.setOnClickListener {
//            if (serverSocket != null && !serverSocket!!.isClosed && sendData != null) {
//                sendData!!.data = mData
//                showLog(GsonUtils.toJson(sendData))
//                mMySendThread = MySendThread(mHandler!!, GsonUtils.toJson(sendData), serverSocket!!)
//                mMySendThread!!.start()
//            } else {
//                showToast("Please check whether USB is connected.")
//            }
//        }

        btn_scan.setOnClickListener {
            flag = false
            if (iScanner != null) {
                try {
                    iScanner!!.aDecodeSetTriggerOn(1)
                    iScanner!!.aDecodeSetBeepEnable(1)
                } catch (e: RemoteException) {
                    e.printStackTrace()
                }
            }
        }

        btn_add.setOnClickListener {
            flag = true
            if (iScanner != null) {
                try {
                    iScanner!!.aDecodeSetTriggerOn(1)
                    iScanner!!.aDecodeSetBeepEnable(1)
                } catch (e: RemoteException) {
                    e.printStackTrace()
                }
            }
        }

        btn_change_Languege.setOnClickListener {

            val config = resources.configuration
            val dm = resources.displayMetrics// 获得屏幕参数：主要是分辨率，像素等。

            if (LanguageUtil.getCurrentLocale(this).equals(Locale.ENGLISH)) {
                showLog("ENGLISH")
                LanguageUtil.updateLocale(this, LanguageUtil.LOCALE_CHINESE)
                LanguageUtil.setDataToSp(this,mData)
                LanguageUtil.setBJToSp(this,"1")
                this.recreate()
            } else {
                showLog("CHINESE")
                LanguageUtil.updateLocale(this, LanguageUtil.LOCALE_ENGLISH)
                LanguageUtil.setDataToSp(this,mData)
                LanguageUtil.setBJToSp(this,"1")
                this.recreate()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        resetState()
    }

    private fun resetState() {
        unregisterReceiver(mMybroatcast)
        unregisterReceiver(mScanbroatcast)
        if (serverSocket != null) {
            serverSocket!!.close()
        }
        if (iScanner != null) {
            try {
                iScanner!!.aDecodeAPIDeinit()
            } catch (e: RemoteException) {
                e.printStackTrace()
            }
        }
        iScanner = null
    }

    @SuppressLint("HandlerLeak")
    inner class MessageHandler : Handler() {

        override fun handleMessage(msg: Message?) {
            when (msg!!.what) {
                Constants.USB_CONNECTED -> {
                    initSoket()
                }

                Constants.USB_EXIT -> {
                    deleteSoket()
                }

                Constants.GET_DATA -> {

                    try {
                        if (null != msg.obj) {
                            //                        resetData()
                            val enity = GsonUtils.gson(msg.obj.toString(), DataEnity::class.java)
                            if ("1" == enity.status) {  //通知需要上传
                                showLog("发送数据")
                                if (null != sendData) {
                                    sendData!!.data = mData
                                    showLog(GsonUtils.toJson(sendData))
                                    soketCommit(GsonUtils.toJson(sendData))
                                }
                            } else if ("0" == enity.status) { //获取数据
                                showLog("接收数据")
                                soketGetData(enity)
                                soketCommit(GsonUtils.toJson("success"))
                            }
                        } else {
                            showToast(resources.getString(R.string.receivefailed))
                        }
                    } catch (e: Exception) {
                        showToast(e.message!!)
                    }
                }

                Constants.EX_ERROR -> {
//                    showLog("EX_ERROR")
                }

                Constants.POST_DATA -> {
                    showToast(resources.getString(R.string.send_ok))
                    showLog(if (null == msg.obj) "send null" else msg.obj.toString())
                }

                Constants.SCANER_RESULT -> {
                    if (!flag) {
                        mDecodeResult.recycle()
                        iScanner!!.aDecodeGetResult(mDecodeResult)
                        if (Constants.READ_FAIL != mDecodeResult.toString() && !TextUtils.isEmpty(mDecodeResult.toString())) {
                            for (index in 0 until mData.size) {
                                if (mDecodeResult.toString() == mData[index].seaL_NO && "X" != mData[index].seaL_TYPE) { //确保不是新增的
                                    mData[index].seaL_TYPE = "Y" //已经扫描
                                    break
                                }
                                if (index == mData.size - 1) {//最后一个了
                                    showToast(resources.getString(R.string.no_cun_zai))
                                }
                            }
                            adapter!!.setData(mData)
                        } else {
                            showToast(resources.getString(R.string.read_failed))
                        }
                        tv!!.text = mDecodeResult.toString()
                    } else {
                        mDecodeResult.recycle()
                        iScanner!!.aDecodeGetResult(mDecodeResult)
                        if (Constants.READ_FAIL != mDecodeResult.toString() && !TextUtils.isEmpty(mDecodeResult.toString())) {
                            val enity = DataEnity.DATA()
                            enity.seaL_NO = mDecodeResult.toString()
                            enity.seaL_TYPE = "X"//新增的
                            enity.insP_ITEM = "CH" //底盘
                            enity.speciaL_TYPE = "PA" //纸质
                            if(mData.size > 0){
                                for (index in 0 until mData.size) { //去重
                                    if (enity.seaL_NO == mData[index].seaL_NO) {
                                        break
                                    }
                                    if(index == mData.size -1){
                                        mData.add(enity)
                                        adapter!!.setData(mData)
                                    }
                                }
                            }else{
                                mData.add(enity)
                                adapter!!.setData(mData)
                            }

                        } else {
                            showToast(resources.getString(R.string.read_failed))
                        }
                        flag = false
                    }
                }
            }
        }
    }


    private fun soketConnect() {
        if (serverSocket != null && !serverSocket!!.isClosed) {
            mMyReceiveThread = MyReceiveThread(mHandler!!, serverSocket!!)
            mMyReceiveThread!!.start()
        } else {
            showToast(resources.getString(R.string.checkusb))
        }
    }

    private fun soketGetData(enity: DataEnity?) {
        if (enity != null && enity.data != null && enity.data.size > 0) {
            mData.clear() //每次获取，清MySendThread空原来的
            mData = enity.data
            adapter!!.setData(mData)
            sendData = enity
        }
    }

    private fun soketCommit(msg: String) {
        if (serverSocket != null && !serverSocket!!.isClosed) {

            mMySendThread = MySendThread(mHandler!!, msg, serverSocket!!)
            mMySendThread!!.start()
        } else {
            showToast(resources.getString(R.string.checkusb))
        }
    }

    @Throws(RemoteException::class)
    private fun initScanner() {

        iScanner = IScannerService.Stub.asInterface(
            ServiceManager
                .getService("ScannerService")
        )
        if (iScanner != null) {
            iScanner!!.aDecodeAPIInit()
            try {
                Thread.sleep(500)
            } catch (e: InterruptedException) {
            }

            iScanner!!.aDecodeSetDecodeEnable(1)
            iScanner!!.aDecodeSetResultType(ScannerService.ResultType.DCD_RESULT_USERMSG)
        }
    }

    override fun getAdapterData(position: Int, itemStr: String, spLx: String) {
        when (spLx) {
            "insP_ITEM" -> {
                mData[position].insP_ITEM = itemStr
            }
            "speciaL_TYPE" -> {
                mData[position].speciaL_TYPE = itemStr
            }
        }

    }

    override fun getLayout(): Int {
        return R.layout.activity_main
    }
}




