package com.seatrend.usb_online.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Resources
import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.util.Log
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

class MyAdapter(private var mContext: Context? = null, private var mData: List<DataEnity.DATA>? = null) :
    RecyclerView.Adapter<MyAdapter.MyHolder>() {
    var listener: Dback? = null

    companion object {
        val mCompareX: Comparator<DataEnity.DATA> =
            Comparator { p0, p1 ->
                if ("X" == p0!!.seaL_TYPE) { //新的
                    -1
                } else {
                    0
                }
            }
        val mCompareN: Comparator<DataEnity.DATA> =
            Comparator { p0, p1 ->
                if ("N" == p0!!.seaL_TYPE) { //待做的
                    -1
                } else {
                    0
                }
            }
        val mCompareR: Comparator<DataEnity.DATA> =
            Comparator { p0, p1 ->
                if ("R" == p0!!.seaL_TYPE) { //重做的
                    -1
                } else {
                    0
                }
            }
    }

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): MyHolder {
        val view: View = LayoutInflater.from(mContext).inflate(R.layout.recyclerview_item, parent, false)
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
        var item: TextView = view.findViewById<TextView>(R.id.item)
        var fqbj: TextView = view.findViewById<TextView>(R.id.fqbj)
        var sp_fqwz: Spinner = view.findViewById<Spinner>(R.id.sp_fqwz)
        var sp_fqlx: Spinner = view.findViewById<Spinner>(R.id.sp_fqlx)

        init {
            initSp(sp_fqwz)
            initSp(sp_fqlx)
            bindEvent()
        }

        private fun bindEvent() {
            sp_fqwz.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(p0: AdapterView<*>?) {}
                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                    mData!![adapterPosition].insP_ITEM = DMZUtils.getDmz(sp_fqwz.selectedItem.toString())
                    listener!!.getAdapterData(adapterPosition,  DMZUtils.getDmz(sp_fqwz.selectedItem.toString()), "insP_ITEM")
                }
            }
            sp_fqlx.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(p0: AdapterView<*>?) {}
                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                    mData!![adapterPosition].speciaL_TYPE = DMZUtils.getDmz(sp_fqlx.selectedItem.toString())
                    listener!!.getAdapterData(adapterPosition,  DMZUtils.getDmz(sp_fqlx.selectedItem.toString()), "speciaL_TYPE")
                }
            }
        }

        fun initSp(spiner: Spinner) {
            val adapter = ArrayAdapter<String>(mContext, R.layout.my_simple_spinner_item)
            adapter.setDropDownViewResource(R.layout.item_spinner__down_common)
            when (spiner) {
                sp_fqwz -> {
                    adapter.clear()
                    adapter.add(mContext!!.resources.getString(R.string.dipan))
                    adapter.add(mContext!!.resources.getString(R.string.sdd))
                    adapter.add(mContext!!.resources.getString(R.string.sld))
                    adapter.add(mContext!!.resources.getString(R.string.tx))
                    spiner.adapter = adapter
                }
                sp_fqlx -> {
                    adapter.clear()
                    adapter.add(mContext!!.resources.getString(R.string.paper))
                    adapter.add(mContext!!.resources.getString(R.string.electronic))
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
                DMZUtils.setSpinner2Dmsm(DMZUtils.getDmmc(mData!![position].speciaL_TYPE), sp_fqlx)
                DMZUtils.setSpinner2Dmsm(DMZUtils.getDmmc(mData!![position].insP_ITEM), sp_fqwz)
            } else {
                sp_fqwz.isEnabled = true
                sp_fqlx.isEnabled = true
                DMZUtils.setSpinner2Dmsm(DMZUtils.getDmmc(mData!![position].speciaL_TYPE), sp_fqlx)
                DMZUtils.setSpinner2Dmsm(DMZUtils.getDmmc(mData!![position].insP_ITEM), sp_fqwz)
            }
        }
    }

    interface Dback {
        fun getAdapterData(position: Int, itemStr: String, spLx: String)
    }
}