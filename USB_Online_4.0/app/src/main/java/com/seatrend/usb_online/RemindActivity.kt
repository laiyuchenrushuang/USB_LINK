package com.seatrend.usb_online

import android.content.Intent
import com.seatrend.usb_online.enity.NewData
import com.seatrend.usb_online.util.Constants
import com.seatrend.usb_online.util.GsonUtils
import kotlinx.android.synthetic.main.activity_remind.*

/**
 * Created by ly on 2020/1/9 13:27
 *
 * Copyright is owned by chengdu haicheng technology
 * co., LTD. The code is only for learning and sharing.
 * It is forbidden to make profits by spreading the code.
 */
class RemindActivity : BaseActivty() {
    override fun initView() {
        bindEvent()
    }

    private fun bindEvent() {
        f5.setOnClickListener {
            finish()
        }
        f8.setOnClickListener {
            val data1 = intent.getSerializableExtra(Constants.CHECK_LIST) as ArrayList<NewData.DATA_CHECK>
            val data2 = intent.getSerializableExtra(Constants.NEW_LIST) as ArrayList<NewData.DATA_NEW>

            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            intent.setClass(this, LoginActivity::class.java)
            startActivity(intent)

            //封装数据
            var dataC = NewData()
            dataC.datA_CHECK = data1
            dataC.datA_NEW = data2

            if ("0" == MyApplication.CLLX) {
                dataC.appoinT_NO = MyApplication.HM_CODE
            } else {
                dataC.veH_Reg_Mark = MyApplication.HM_CODE
            }

            MyApplication.mAllData.add(dataC)

            showLog("" + MyApplication.mAllData.size + "  " + GsonUtils.toJson(MyApplication.mAllData))
        }
    }

    override fun getLayout(): Int {
        return R.layout.activity_remind
    }
}