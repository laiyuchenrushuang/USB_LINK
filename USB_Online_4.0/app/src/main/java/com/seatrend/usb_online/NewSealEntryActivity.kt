package com.seatrend.usb_online

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Handler
import android.os.Message
import android.support.v7.widget.LinearLayoutManager
import android.text.TextUtils
import android.view.View
import android.view.ViewStub
import com.seatrend.usb_online.adapter.Adapter1
import com.seatrend.usb_online.adapter.Adapter2
import com.seatrend.usb_online.enity.NewData
import com.seatrend.usb_online.util.CheckBoxUtils
import com.seatrend.usb_online.util.Constants
import com.seatrend.usb_online.util.DMZUtils
import com.seatrend.usb_online.util.SharePreferenceUtils
import kotlinx.android.synthetic.main.activity_new.*


class NewSealEntryActivity : BaseActivty(), Adapter2.Dback {


    private var ll: LinearLayoutManager? = null
    private var adapter: Adapter2? = null
    private var mData = ArrayList<NewData.DATA_NEW>()


    override fun getAdapterData(position: Int, itemStr: String, spLx: String) {

    }

    override fun getCurrentList(dataList: List<NewData.DATA_NEW>?) {
        runOnUiThread {
            if(dataList != null){
                count.text = dataList!!.size.toString()
            }else{
                count.text = "0"
            }

        }
    }

    var mHandler = @SuppressLint("HandlerLeak")
    object : Handler() {
        override fun handleMessage(msg: Message?) {
            super.handleMessage(msg)

            //============================重要
            mDecodeResult.recycle()
            iScanner!!.aDecodeGetResult(mDecodeResult)

            //==============================国土局的
            if (Constants.READ_FAIL != mDecodeResult.toString() && !TextUtils.isEmpty(mDecodeResult.toString())) {
                runOnUiThread {
                    var enity = NewData.DATA_NEW()
                    var type =""
                    if (Constants.TAXI == SharePreferenceUtils.getSettingFromSP(this@NewSealEntryActivity, Constants.CLLX)) {

                        if (transducer.isChecked) {
                            type = "T1"
                        }
                        if (meter.isChecked) {
                            type = "M5"
                        }
                        if(taxi_null.isChecked){
                            type = ""
                        }
                    } else {
                        if (sdd.isChecked) {
                            type = "S3"
                        }
                        if (sld.isChecked) {
                            type = "S0"
                        }
                        if(edrd.isChecked){
                            type = "S6"
                        }
                        if(taxi_null.isChecked){
                            type = ""
                        }
                    }

                    enity.seaL_NO = mDecodeResult.toString()
                    enity.insP_ITEM = type
                    if (mData.size > 0) {
                        for (index in 0 until mData.size) { //去重
                            if (enity.seaL_NO == mData[index].seaL_NO) {
                                break
                            }
                            if (index == mData.size - 1) {
                                mData.add(enity)
                                adapter!!.setData(mData)
                            }
                        }
                    } else {
                        mData.add(enity)
                        adapter!!.setData(mData)
                    }
                }
            } else {
                showToast(resources.getString(R.string.read_failed))
            }
        }
    }

    override fun initView() {
        DMZUtils.getDmzMap(this)
        mScanbroatcast = ScanResultReceiver(mHandler)
        registerScanBroadcast(mScanbroatcast!!)
        initRecycleView()
        initData()
        bindEvent()
    }

    private fun initData() {
        if (Constants.TAXI.equals(SharePreferenceUtils.getSettingFromSP(this, Constants.CLLX))) {
            //的士
            ll_bus.visibility = View.GONE
            ll_taxi.visibility = View.VISIBLE

            CheckBoxUtils.setMucherListener(transducer,meter,taxi_null)
        } else {
            //bus
            ll_taxi.visibility = View.GONE
            ll_bus.visibility = View.VISIBLE
            CheckBoxUtils.setMucherListenerBus(sdd,sld,edrd,bus_null)
        }
    }

    private fun initRecycleView() {
        ll = LinearLayoutManager(this)
        m_recycler_view!!.layoutManager = ll
        adapter = Adapter2(this, mData)
        m_recycler_view.adapter = adapter
        adapter!!.setLisDataback(this)
    }

    private fun bindEvent() {
        f8.setOnClickListener {
            intent.putExtra(Constants.NEW_LIST,mData)
            intent.setClass(this,RemindActivity::class.java)
            startActivity(intent)
        }
        f5.setOnClickListener {
            finish()
        }
        f1.setOnClickListener {
            if(mData.size >0){
                mData.remove(mData.get(mData.size-1))
                adapter!!.setData(mData)
            }
        }
    }

    override fun getLayout(): Int {
        return R.layout.activity_new
    }
}