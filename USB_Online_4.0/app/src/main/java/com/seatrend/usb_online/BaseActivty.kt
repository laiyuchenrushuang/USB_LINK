package com.seatrend.usb_online

import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.app.Dialog
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.*
import android.os.*
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.seatrend.usb_online.enity.DataEnity
import com.seatrend.usb_online.util.ActivityManager
import java.util.*
import kotlin.collections.ArrayList
import android.support.v4.app.ActivityCompat
import android.text.TextUtils
import android.view.View
import android.widget.*
import com.seatrend.usb_online.broadcast.UsbBroatcastReceiver
import com.seatrend.usb_online.enity.NewData
import com.seatrend.usb_online.enity.SocketEnity
import com.seatrend.usb_online.receive.MyReceiveThread
import com.seatrend.usb_online.send.MySendThread
import com.seatrend.usb_online.util.Constants
import com.seatrend.usb_online.util.GsonUtils
import com.seatrend.usb_online.util.LanguageUtil
import device.scanner.DecodeResult
import device.scanner.IScannerService
import device.scanner.ScanConst
import device.scanner.ScannerService
import java.net.ServerSocket


abstract class BaseActivty : AppCompatActivity() {

    var ivBack: ImageView? = null
    var ivRight: ImageView? = null
    var tvPageTitle: TextView? = null
    var tvRight: TextView? = null
    var rlParent: RelativeLayout? = null
    var newLocal: Locale? = null
    protected var mDialogListenr: DialogListener? = null
    var mScanbroatcast: ScanResultReceiver? = null//红外线扫描的广播 为什么分开？
    var iScanner: IScannerService? = null
    val mDecodeResult = DecodeResult()
    private var mMybroatcast: UsbBroatcastReceiver? = null //USB 的广播
    private var mBHandler: MessageHandler? = null
    private var mMyReceiveThread: MyReceiveThread? = null  //接收的线程
    private var mMySendThread: MySendThread? = null //发送的线程
    private var serverSocket: ServerSocket? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayout())
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT//竖屏
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            requestPermission()
//        }
        initCommonTitle()
        initUSB()

        registerUSBBroadcast()
        initScanner()
        initView()
        ActivityManager.getInstance().addActivity(this);
        // setStatusBarColor(resources.getColor(R.color.white))

    }

    private fun initUSB() {
        showLog("aaaaaaa")
        mBHandler = MessageHandler()
        mMybroatcast = UsbBroatcastReceiver(mBHandler!!)
    }

    @SuppressLint("HandlerLeak")
    inner class MessageHandler : Handler() {

        override fun handleMessage(msg: Message?) {
            when (msg!!.what) {
                Constants.USB_CONNECTED -> {
                    initSoket()
                }

                Constants.GET_DATA -> {

                    try {
                        if (null != msg.obj) {

                            var sendData = MyApplication.mAllData

                            val enity = GsonUtils.gson(msg.obj.toString(), SocketEnity::class.java)
                            if ("1" == enity.status) {  //通知需要上传
                                if (sendData.size > 0) {
                                    showToast(resources.getString(R.string.fssj))
                                    showLog(GsonUtils.toJson(sendData))
                                    soketCommit(GsonUtils.toJson(sendData))
                                }else{
                                    showToast("Nothing")
                                }
                            }
//                            else if ("0" == enity.status) { //获取数据
//                                showToast(resources.getString(R.string.jssj))
//                                showLog(GsonUtils.toJson(enity))
//                                soketCommit(GsonUtils.toJson("success"))
//                            }
                            else if ("2" == enity.status) { //删除数据
                                showToast(resources.getString(R.string.qcsj))
                                MyApplication.mAllData.clear()
                                soketCommit(GsonUtils.toJson("success"))
                            }
                        } else {
                            showToast(resources.getString(R.string.receivefailed))
                        }
                    } catch (e: Exception) {
                        showToast("ERROR1--" + e.message!!)
                    }
                }
            }
        }
    }

    private fun soketCommit(msg: String) {
        if (serverSocket != null && !serverSocket!!.isClosed) {

            mMySendThread = MySendThread(mBHandler!!, msg, serverSocket!!)
            mMySendThread!!.start()
        } else {
            showToast(resources.getString(R.string.checkusb))
        }
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

    private fun soketConnect() {
        if (serverSocket != null && !serverSocket!!.isClosed) {
            mMyReceiveThread = MyReceiveThread(mBHandler!!, serverSocket!!)
            mMyReceiveThread!!.start()
        } else {
            showToast(resources.getString(R.string.checkusb))
        }
    }

    fun updateActivity(s: String) {
        val myLocale = Locale(s)
        val res = resources// 获得res资源对象
        val dm = res.displayMetrics// 获得屏幕参数：主要是分辨率，像素等。
        val conf = res.configuration// 获得设置对象
        conf.locale = myLocale// 简体中文
        res.updateConfiguration(conf, dm)

    }
//
//    //屏幕顶部同色
//    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
//    private fun setStatusBarColor(statusColor: Int) {
//        val window = window
//        //取消状态栏透明
//        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
//        //添加Flag把状态栏设为可绘制模式
//        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
//        //设置状态栏颜色
//        window.statusBarColor = statusColor
//        //设置系统状态栏处于可见状态
//        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_VISIBLE
//        //让view不根据系统窗口来调整自己的布局
//        val mContentView = window.findViewById<View>(Window.ID_ANDROID_CONTENT) as ViewGroup
//        val mChildView = mContentView.getChildAt(0)
//        if (mChildView != null) {
//            ViewCompat.setFitsSystemWindows(mChildView, false)
//            ViewCompat.requestApplyInsets(mChildView)
//        }
//    }

    //请求权限
    @TargetApi(Build.VERSION_CODES.M)
    private fun requestPermission() {
        var permissions = ArrayList<String>()
        if (checkSelfPermission("android.permission.WRITE_EXTERNAL_STORAGE") != PackageManager.PERMISSION_GRANTED) {
            permissions.add("android.permission.WRITE_EXTERNAL_STORAGE")
        }

        if (checkSelfPermission("android.permission.READ_EXTERNAL_STORAGE") != PackageManager.PERMISSION_GRANTED) {
            permissions.add("android.permission.READ_EXTERNAL_STORAGE")
        }
        if (permissions.size > 0) {
            ActivityCompat.requestPermissions(this@BaseActivty, permissions.toTypedArray(), 1)
        }
    }

    //权限检查
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when (requestCode) {
            1 -> {
                if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    finish()
                }
            }
        }
    }

    fun setPageTitle(pageTitle: String) {
        if (tvPageTitle != null) {
            tvPageTitle!!.text = pageTitle
        }
    }

    @SuppressLint("WrongViewCast")
    fun initCommonTitle() {
        ivBack = findViewById(R.id.iv_back)
        ivRight = findViewById(R.id.iv_right)
        tvPageTitle = findViewById(R.id.tv_titile)
        tvRight = findViewById(R.id.tv_right)
        rlParent = findViewById(R.id.rl_parent)

        if (ivBack != null) {
            ivBack!!.setOnClickListener { finish() }
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        ActivityManager.getInstance().removeActivity(this);
    }

    abstract fun initView()

    abstract fun getLayout(): Int

    @SuppressLint("ShowToast")
    fun showToast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    fun showLog(msg: String) {
        Log.d("[lylog]-->>", msg)
    }
//    private fun languageWork(context: Context): Context {
//        // 8.0及以上使用createConfigurationContext设置configuration
//        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            updateResources(context)
//        } else {
//            context
//        }
//    }

    //    @RequiresApi(api = Build.VERSION_CODES.O)
//    private fun updateResources(context: Context): Context {
//        val resources = context.getResources()
//        val locale = LanguageUtil.getLocale(context) ?: return context
//        val configuration = resources.getConfiguration()
//        configuration.setLocale(locale)
//        configuration.setLocales(LocaleList(locale))
//        return context.createConfigurationContext(configuration)
//    }
//
    override fun attachBaseContext(newBase: Context) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            super.attachBaseContext(newBase);
        } else {
            //zh：中文
            super.attachBaseContext(LanguageUtil.initAppLanguage(newBase, "" + newLocal));
        }

    }

    fun showTipDialog(titleS: String?, contentS: String, flag: Int) {
        val dialog = Dialog(this)
        // MAlertDialog dialog=new MAlertDialog(this);
        dialog.setContentView(R.layout.dialog_tip_picker)
        dialog.setCanceledOnTouchOutside(false)

        val btnCancel = dialog.findViewById<Button>(R.id.btn_cancel)
        val btnOk = dialog.findViewById<Button>(R.id.btn_ok)
        val title = dialog.findViewById<TextView>(R.id.title)
        val content = dialog.findViewById<TextView>(R.id.content)
        if (TextUtils.isEmpty(titleS)) {
            title.visibility = View.GONE
        } else {
            title.visibility = View.VISIBLE
        }
        content.text = contentS
        dialog.show()
        btnCancel.setOnClickListener {
            dialog.dismiss()
            mDialogListenr!!.tipDialogCancelListener(flag)
        }
        btnOk.setOnClickListener {
            dialog.dismiss()
            mDialogListenr!!.tipDialogOKListener(flag)
        }
    }

    fun setListener(listenr: DialogListener) {
        mDialogListenr = listenr
    }

    class ScanResultReceiver(var mHandler: Handler) : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            if (ScanConst.INTENT_USERMSG == intent!!.action) {
                var msg = Message.obtain()
                msg.what = Constants.SCANER_RESULT
                msg.obj = intent
                mHandler.sendMessage(msg)
            }
        }
    }

    @Throws(RemoteException::class)
    fun initScanner() {

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

    fun registerScanBroadcast(broatcast: ScanResultReceiver) {
        val filter = IntentFilter()
        filter.addAction(ScanConst.INTENT_USERMSG)
        filter.addAction(ScanConst.INTENT_EVENT)
        registerReceiver(broatcast, filter, "com.example.vendor.permission.SCANNER_RESULT_RECEIVER", null)
    }

    interface DialogListener {
        fun tipDialogOKListener(flag: Int)
        fun tipDialogCancelListener(flag: Int)
    }

}
