package com.seatrend.usb_online

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Handler
import android.os.Message
import android.text.TextUtils
import com.seatrend.usb_online.util.Constants
import com.seatrend.usb_online.util.LanguageUtil
import com.seatrend.usb_online.util.SharePreferenceUtils
import device.scanner.DecodeResult
import device.scanner.IScannerService
import kotlinx.android.synthetic.main.activity_login.*


class LoginActivity : BaseActivty() {

    private var mHandler = @SuppressLint("HandlerLeak")
    object : Handler() {

        override fun handleMessage(msg: Message?) {
            mDecodeResult.recycle()
            iScanner!!.aDecodeGetResult(mDecodeResult)
            if (Constants.READ_FAIL != mDecodeResult.toString() && !TextUtils.isEmpty(mDecodeResult.toString())) {
                runOnUiThread {
                    edit_cphm.setText(mDecodeResult.toString())
                }
            } else {
                showToast(resources.getString(R.string.read_failed))
            }
            showLog(mDecodeResult.toString())
        }
    }

    override fun initView() {
        mScanbroatcast = ScanResultReceiver(mHandler!!)
        registerScanBroadcast(mScanbroatcast!!)
        if ("1" == SharePreferenceUtils.getSettingFromSP(this, "yy_model")) {
            MyApplication.CLLX = "0"       //预约号码查询
            login_tishi.text = resources.getString(R.string.login_tishi2)
            change.text = resources.getString(R.string.login_change_c)
            login_tishi1.text = resources.getString(R.string.login_yyhm)
        }else if("0" == SharePreferenceUtils.getSettingFromSP(this, "yy_model")){
            MyApplication.CLLX = "1"        //车牌号码查询
            login_tishi.text = resources.getString(R.string.login_tishi1)
            change.text = resources.getString(R.string.login_change_y)
            login_tishi1.text = resources.getString(R.string.login_cphm)
        }
        bindEvent()
    }

    override fun onStart() {
        super.onStart()
        initData()
    }

    private fun initData() {

        if (Constants.TAXI == SharePreferenceUtils.getSettingFromSP(this, Constants.CLLX)) {
            cllx.text = resources.getString(R.string.taxi)
        } else if (Constants.BUS == SharePreferenceUtils.getSettingFromSP(this, Constants.CLLX)) {
            cllx.text = resources.getString(R.string.bus)
        } else {
            cllx.text = "--"
        }
    }

    private fun bindEvent() {
        f8.setOnClickListener {
            if ("--" == cllx.text.toString()) {
                showToast(resources.getString(R.string.cllx_tip))
                return@setOnClickListener
            }
            if ("" == edit_cphm.text.toString() || TextUtils.isEmpty(edit_cphm.text.toString())) {
                showToast(resources.getString(R.string.cllx_tip1))
                return@setOnClickListener
            }

            MyApplication.HM_CODE = edit_cphm.text.toString()

            startActivity(Intent(this, EntryActivity::class.java))
        }

        change.setOnClickListener {
            edit_cphm.setText("")
            if (resources.getString(R.string.login_change_y) == change.text.toString()) {
                MyApplication.CLLX = "0"       //预约号码查询
                login_tishi.text = resources.getString(R.string.login_tishi2)
                change.text = resources.getString(R.string.login_change_c)
                login_tishi1.text = resources.getString(R.string.login_yyhm)
                SharePreferenceUtils.setSettingToSp(this, "yy_model", "1")
            } else {
                MyApplication.CLLX = "1"        //车牌号码查询
                login_tishi.text = resources.getString(R.string.login_tishi1)
                change.text = resources.getString(R.string.login_change_y)
                login_tishi1.text = resources.getString(R.string.login_cphm)
                SharePreferenceUtils.setSettingToSp(this, "yy_model", "0")
            }

        }

        setting.setOnClickListener {
            startActivity(Intent(this, SettingActivity::class.java))
        }
        f5.setOnClickListener { finish() }
    }


//    @SuppressLint("HandlerLeak")
//    inner class MessageHandler : Handler() {
//
//        override fun handleMessage(msg: Message?) {
//            mDecodeResult.recycle()
//            iScanner!!.aDecodeGetResult(mDecodeResult)
//
//            showLog(mDecodeResult.toString())
//        }
//    }

    override fun getLayout(): Int {
        return R.layout.activity_login
    }
}