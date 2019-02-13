package com.leeeyou.source.code.analysis.recyclerview

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

class SongAdapter(private val dataList: List<String>, val orientation: Int = RecyclerView.VERTICAL) : RecyclerView.Adapter<SongAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        return if (orientation == RecyclerView.VERTICAL) {
            ViewHolder(LayoutInflater.from(parent?.context).inflate(R.layout.item_song_vertical, parent, false))
        } else {
            ViewHolder(LayoutInflater.from(parent?.context).inflate(R.layout.item_song_horizontal, parent, false))
        }
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        holder?.tvSongName?.text = dataList[position]
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvSongName: TextView = itemView.findViewById(R.id.tv_song_name)
    }

}