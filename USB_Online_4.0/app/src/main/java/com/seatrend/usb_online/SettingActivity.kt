package com.seatrend.usb_online

import com.seatrend.usb_online.util.CheckBoxUtils
import com.seatrend.usb_online.util.Constants
import com.seatrend.usb_online.util.SharePreferenceUtils
import kotlinx.android.synthetic.main.activity_setting.*
import android.content.Intent
import com.seatrend.usb_online.util.LanguageUtil
import java.lang.Exception
import java.util.*


class SettingActivity : BaseActivty(), BaseActivty.DialogListener {

    override fun tipDialogCancelListener(flag: Int) {

    }

    override fun tipDialogOKListener(flag: Int) {

        try {
            if (LanguageUtil.getCurrentLocale(this).equals(Locale.ENGLISH)) {
                showLog("英->中  == " + LanguageUtil.getCurrentLocale(this)+"->"+ LanguageUtil.LOCALE_CHINESE)
                LanguageUtil.updateLocale(this, LanguageUtil.LOCALE_CHINESE)
                newLocal = LanguageUtil.LOCALE_CHINESE
                recreateApp()
            } else {
                showLog("中->英" + LanguageUtil.getCurrentLocale(this)+"->"+ LanguageUtil.LOCALE_ENGLISH)
                LanguageUtil.updateLocale(this, LanguageUtil.LOCALE_ENGLISH)
                newLocal = LanguageUtil.LOCALE_ENGLISH
                recreateApp()
            }
        } catch (e: Exception) {
            showToast("ERROR2--" + e.message.toString())
        }


    }

    private fun recreateApp() {
        val intent = baseContext.packageManager.getLaunchIntentForPackage(baseContext.packageName)
        intent!!.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        //与正常页面跳转一样可传递序列化数据,在Launch页面内获得
        intent.putExtra("REBOOT", "reboot")
        startActivity(intent)
    }

    override fun initView() {
        initData()
        bindEvent()
    }

    private fun initData() {
        setListener(this)
        if (LanguageUtil.getCurrentLocale(this) == Locale.ENGLISH) {
            yw.isChecked = true
            SharePreferenceUtils.setSettingToSp(this,Constants.YYLX,Constants.EH) // 语言类型
        }else{
            zw.isChecked = true
            SharePreferenceUtils.setSettingToSp(this,Constants.YYLX,Constants.ZH) // 语言类型
        }

        if(Constants.BUS == SharePreferenceUtils.getSettingFromSP(this,Constants.CLLX)){
            bus.isChecked = true
            SharePreferenceUtils.setSettingToSp(this,Constants.CLLX,Constants.BUS) // 车辆类型
        }else{

            taxi.isChecked = true
            SharePreferenceUtils.setSettingToSp(this,Constants.CLLX,Constants.TAXI) // 车辆类型
        }

    }

    private fun bindEvent() {
        CheckBoxUtils.setListener(taxi,bus)
        CheckBoxUtils.setListener(zw,yw)

        f5.setOnClickListener {

            if(!taxi.isChecked && !bus.isChecked){
                showToast(resources.getString(R.string.setting_toast))
                return@setOnClickListener
            }

            if(taxi.isChecked){
                SharePreferenceUtils.setSettingToSp(this,Constants.CLLX,Constants.TAXI) // 车辆类型
            }else{
                SharePreferenceUtils.setSettingToSp(this,Constants.CLLX,Constants.BUS) // 车辆类型
            }

            if(zw.isChecked){
                if(Constants.ZH == SharePreferenceUtils.getSettingFromSP(this,Constants.YYLX)){
                    SharePreferenceUtils.setSettingToSp(this,Constants.YYLX,Constants.ZH) // 语言类型
                    finish()
                }else{
                    showTipDialog(null,resources.getString(R.string.tip_content),0)
                }

            }else{
                if(Constants.EH == SharePreferenceUtils.getSettingFromSP(this,Constants.YYLX)){
                    SharePreferenceUtils.setSettingToSp(this,Constants.YYLX,Constants.EH) // 语言类型
                    finish()
                }else{
                    showTipDialog(null,resources.getString(R.string.tip_content),1)
                }
            }
        }
    }

    override fun getLayout(): Int {
        return R.layout.activity_setting
    }
}