package com.seatrend.usb_online.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import com.seatrend.usb_online.R
import com.seatrend.usb_online.enity.DataEnity
import com.seatrend.usb_online.util.DMZUtils
import java.util.*
import kotlin.collections.ArrayList
import java.util.Comparator as Comparator1

/**
 * Created by ly on 2019/11/26 15:36
 *
 * Copyright is owned by chengdu haicheng technology
 * co., LTD. The code is only for learning and sharing.
 * It is forbidden to make profits by spreading the code.
 */
class MyAdapter(private var mContext: Context? = null, private var mData: List<DataEnity.DATA>? = null) :
    RecyclerView.Adapter<MyAdapter.MyHolder>() {
    var listener: MyAdapter.Dback? = null

    companion object {
        val mCompareX: Comparator<DataEnity.DATA> = object : Comparator<DataEnity.DATA> {
            override fun compare(p0: DataEnity.DATA?, p1: DataEnity.DATA?): Int {
                if ("X" == p0!!.seaL_TYPE) {
                    return -1
                } else {
                    return 0
                }
            }
        }
        val mCompareN: Comparator<DataEnity.DATA> = object : Comparator<DataEnity.DATA> {
            override fun compare(p0: DataEnity.DATA?, p1: DataEnity.DATA?): Int {
                if ("N" == p0!!.seaL_TYPE) {
                    return -1
                } else {
                    return 0
                }
            }
        }
        val mCompareR: Comparator<DataEnity.DATA> = object : Comparator<DataEnity.DATA> {
            override fun compare(p0: DataEnity.DATA?, p1: DataEnity.DATA?): Int {
                if ("R" == p0!!.seaL_TYPE) {
                    return -1
                } else {
                    return 0
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): MyHolder {
        var view: View = LayoutInflater.from(mContext).inflate(R.layout.recyclerview_item, parent, false)
        return MyHolder(view)
    }

    override fun getItemCount(): Int {
        return mData!!.size
    }

    fun setLisDataback(ls: Dback) {
        this.listener = ls
    }

    fun setData(data: ArrayList<DataEnity.DATA>?) {

        Collections.sort(data, mCompareR)
        Collections.sort(data, mCompareN)
        Collections.sort(data, mCompareX)
        this.mData = data
        notifyDataSetChanged()
    }

    fun getData(): List<DataEnity.DATA>? {
        return this.mData
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        holder.setItemView(position)
    }

    inner class MyHolder(view: View) : RecyclerView.ViewHolder(view) {
        var item = view.findViewById<TextView>(R.id.item)
        var fqbj = view.findViewById<TextView>(R.id.fqbj)
        var sp_fqwz = view.findViewById<Spinner>(R.id.sp_fqwz)
        var sp_fqlx = view.findViewById<Spinner>(R.id.sp_fqlx)

        init {
            initSp(sp_fqwz)
            initSp(sp_fqlx)
            bindEvent()
        }

        private fun bindEvent() {
            sp_fqwz.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(p0: AdapterView<*>?) {}
                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                    mData!![adapterPosition].insP_ITEM = sp_fqwz.selectedItem.toString()
                    listener!!.getAdapterData(adapterPosition, sp_fqwz.selectedItem.toString(), "insP_ITEM")
                }
            }
            sp_fqlx.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(p0: AdapterView<*>?) {}
                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                    mData!![adapterPosition].speciaL_TYPE = sp_fqlx.selectedItem.toString()
                    listener!!.getAdapterData(adapterPosition, sp_fqlx.selectedItem.toString(), "speciaL_TYPE")
                }
            }
        }

        fun initSp(spiner: Spinner) {
            val adapter = ArrayAdapter<String>(mContext, R.layout.my_simple_spinner_item)
            adapter.setDropDownViewResource(R.layout.item_spinner__down_common)
            when (spiner) {
                sp_fqwz -> {
                    adapter.clear()
                    adapter.add("底盘")
                    adapter.add("的士表")
                    spiner.adapter = adapter
                }
                sp_fqlx -> {
                    adapter.clear()
                    adapter.add("纸质")
                    adapter.add("电子")
                    spiner.adapter = adapter
                }
            }
        }

        @SuppressLint("ResourceAsColor")
        fun setItemView(position: Int) {
            item.text = mData!![position].seaL_NO
            fqbj.text =
                if (TextUtils.isEmpty(mData!![position].seaL_TYPE)) mData!![position].seaL_TYPE else DMZUtils.getDmmc(
                    mData!![position].seaL_TYPE
                )
            if ("X" != mData!![position].seaL_TYPE) { //不是新增的
                sp_fqwz.isEnabled = false
                sp_fqlx.isEnabled = false
                sp_fqwz.setBackgroundColor(Color.WHITE)
                sp_fqlx.setBackgroundColor(Color.WHITE)
                DMZUtils.setSpinner2Dmsm(mData!![position].speciaL_TYPE, sp_fqwz)
                DMZUtils.setSpinner2Dmsm(mData!![position].insP_ITEM, sp_fqlx)
            } else {
                sp_fqwz.isEnabled = true
                sp_fqlx.isEnabled = true
            }
        }
    }

    interface Dback {
        fun getAdapterData(position: Int, itemStr: String, spLx: String)
    }
}