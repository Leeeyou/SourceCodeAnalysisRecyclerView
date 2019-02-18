package com.leeeyou.source.code.analysis.recyclerview.adapter

import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.leeeyou.source.code.analysis.recyclerview.R
import com.leeeyou.source.code.analysis.recyclerview.widget.RcyLog

class SongAdapter(private var dataList: MutableList<String>, val orientation: Int = RecyclerView.VERTICAL, private val tvCreateAndBind: TextView) : RecyclerView.Adapter<SongAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        RcyLog.log(tvCreateAndBind, "onCreate")
        return if (orientation == RecyclerView.VERTICAL) {
            Log.e("SongAdapter1", "onCreate , vertical")
            ViewHolder(LayoutInflater.from(parent?.context).inflate(R.layout.item_song_vertical, parent, false))
        } else {
            Log.e("SongAdapter1", "onCreate , horizontal")
            ViewHolder(LayoutInflater.from(parent?.context).inflate(R.layout.item_song_horizontal, parent, false))
        }
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
//        Log.e("SongAdapter2", "onBind   , current position is $position")
        RcyLog.log(tvCreateAndBind, "onBind   , current position is $position")
        holder?.tvSongName?.text = dataList[position]
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvSongName: TextView = itemView.findViewById(R.id.tv_song_name)
    }

    fun addData(position: Int) {
        dataList.add(position, "Insert One")
        notifyItemInserted(position)
    }

    fun removeData(position: Int) {
        dataList.removeAt(position)
        notifyItemRemoved(position)
    }

}