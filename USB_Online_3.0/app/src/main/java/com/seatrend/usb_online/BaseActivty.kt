package com.seatrend.usb_online

import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.content.Context
import android.content.pm.*
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import com.seatrend.usb_online.enity.DataEnity
import com.seatrend.usb_online.util.ActivityManager
import java.util.*
import kotlin.collections.ArrayList
import android.os.Build
import android.support.v4.app.ActivityCompat
import com.seatrend.usb_online.util.LanguageUtil


abstract class BaseActivty : AppCompatActivity() {

    var ivBack: ImageView? = null
    var ivRight: ImageView? = null
    var tvPageTitle: TextView? = null
    var tvRight: TextView? = null
    var rlParent: RelativeLayout? = null
    var mData = ArrayList<DataEnity.DATA>()
    var newLocal :Locale?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayout())
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT//竖屏
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            requestPermission()
//        }
        initCommonTitle()
        initView()
        ActivityManager.getInstance().addActivity(this);
        // setStatusBarColor(resources.getColor(R.color.white))

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
            super.attachBaseContext(LanguageUtil.initAppLanguage(newBase, ""+newLocal));
        }

    }

}
