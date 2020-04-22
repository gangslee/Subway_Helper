package com.example.subwayhelper.data

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.subwayhelper.R
import kotlinx.android.synthetic.main.item_latest.view.*

class LatestAdapter (val list: List<LatestData>): RecyclerView.Adapter<LatestViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LatestViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_latest, parent, false)
        return LatestViewHolder(view)
    }

    override fun getItemCount(): Int {
       return list.count()
    }

    override fun onBindViewHolder(holder: LatestViewHolder, position: Int) {

        holder.containerView.lineText.text = "${list[position].line} 호선"
        holder.containerView.stationText.text = "${list[position].station} 역"
    }


}