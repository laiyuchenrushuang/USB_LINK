package com.seatrend.usb_online.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.seatrend.usb_online.R
import com.seatrend.usb_online.enity.DataEnity
import com.seatrend.usb_online.enity.NewData


class Adapter1(private var mContext: Context? = null, private var mData: List<NewData.DATA_CHECK>? = null) :
    RecyclerView.Adapter<Adapter1.MyHolder>() {
    var listener: Dback? = null

    fun setLisDataback(ls: Dback) {
        this.listener = ls
    }

    fun setData(data: ArrayList<NewData.DATA_CHECK>?) {
        this.mData = data
        notifyDataSetChanged()
        listener!!.getCurrentList(this.mData)
    }


    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): MyHolder {
        val view: View = LayoutInflater.from(mContext).inflate(R.layout.recyclerview1, parent, false)
        return MyHolder(view)
    }

    override fun getItemCount(): Int {
        return mData!!.size
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        holder.setItemView(position)
    }


    inner class MyHolder(view: View) : RecyclerView.ViewHolder(view) {
        var item: TextView = view.findViewById(R.id.seal_id)
        @SuppressLint("ResourceAsColor")
        fun setItemView(position: Int) {
            item.text = mData!![position].seaL_NO
        }
    }

    interface Dback {
        fun getAdapterData(position: Int, itemStr: String, spLx: String)
        fun getCurrentList(dataList:List<NewData.DATA_CHECK>?)
    }
}