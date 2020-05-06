package com.example.subwayhelper.data

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.subwayhelper.R
import com.example.subwayhelper.SubwayHelperApplication
import com.example.subwayhelper.activity.AskActivity
import com.example.subwayhelper.activity.MainActivity
import kotlinx.android.synthetic.main.content_main.view.*
import kotlinx.android.synthetic.main.item_latest.view.*

class LatestAdapter(private val list: MutableList<LatestData>) :
    RecyclerView.Adapter<LatestViewHolder>() {
    lateinit var itemClickListener:(itemId:String)->Unit
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LatestViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_latest, parent, false)

        view.setOnClickListener{
            itemClickListener?.run{
                val dataId = it.tag as String
                this(dataId)
            }
        }

        return LatestViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.count()
    }

    override fun onBindViewHolder(holder: LatestViewHolder, position: Int) {

        if (list[position].line.isNotEmpty()) {
            holder.containerView.lineText.visibility = View.VISIBLE
            holder.containerView.stationText.text = list[position].station
        } else {
            holder.containerView.lineText.visibility = View.GONE
        }


        holder.containerView.lineText.text = "${list[position].line}"
        holder.containerView.stationText.text = "${list[position].station}"
        holder.containerView.tag = list[position].id
    }


}