package com.seatrend.usb_online

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Handler
import android.os.Message
import android.support.v7.widget.LinearLayoutManager
import android.text.TextUtils
import com.seatrend.usb_online.adapter.Adapter1
import com.seatrend.usb_online.enity.NewData
import com.seatrend.usb_online.util.Constants
import kotlinx.android.synthetic.main.activity_entry.*
import kotlinx.android.synthetic.main.activity_entry.m_recycler_view

class EntryActivity : BaseActivty(), Adapter1.Dback {

    private var ll: LinearLayoutManager? = null
    private var adapter: Adapter1? = null
    private var mData = ArrayList<NewData.DATA_CHECK>()

    override fun getAdapterData(position: Int, itemStr: String, spLx: String) {

    }

    override fun getCurrentList(dataList: List<NewData.DATA_CHECK>?) {
        runOnUiThread {
            if (dataList != null) {
                count.text = dataList!!.size.toString()
            } else {
                count.text = "0"
            }

        }
    }

    var mHandler = @SuppressLint("HandlerLeak")
    object : Handler() {
        override fun handleMessage(msg: Message?) {
            super.handleMessage(msg)
            when (msg!!.what) {
                Constants.SCANER_RESULT -> {
                    //============================重要
                    mDecodeResult.recycle()
                    iScanner!!.aDecodeGetResult(mDecodeResult)

                    //==============================国土局的
                    if (Constants.READ_FAIL != mDecodeResult.toString() && !TextUtils.isEmpty(mDecodeResult.toString())) {
                        runOnUiThread {
                            var enity = NewData.DATA_CHECK()
                            enity.seaL_NO = mDecodeResult.toString()
                            enity.insP_ITEM = ""
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

        }
    }

    override fun initView() {
        mScanbroatcast = ScanResultReceiver(mHandler)
        registerScanBroadcast(mScanbroatcast!!)
        initRecycleView()
        bindEvent()
    }

    private fun initRecycleView() {
        ll = LinearLayoutManager(this)
        m_recycler_view!!.layoutManager = ll
        adapter = Adapter1(this, mData)
        m_recycler_view.adapter = adapter
        adapter!!.setLisDataback(this)
    }

    private fun bindEvent() {
        f8.setOnClickListener {
            intent.putExtra(Constants.CHECK_LIST,mData)
            intent.setClass(this,NewSealEntryActivity::class.java)
            startActivity(intent)
        }
        f5.setOnClickListener {
            finish()
        }
        f1.setOnClickListener {
            if (mData.size > 0) {
                mData.remove(mData.get(mData.size - 1))
                adapter!!.setData(mData)
            }
        }
    }

    override fun getLayout(): Int {
        return R.layout.activity_entry
    }
}