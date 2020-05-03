package com.example.a27299.cats.home.fragment.species

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.HeaderViewListAdapter
import android.widget.TextView
import com.example.a27299.cats.R
import kotlinx.android.synthetic.main.item_detail_header.view.*
import kotlinx.android.synthetic.main.item_detail_level.view.*

open class DetailViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
class DetailHeader(itemView: View, val description: TextView) : DetailViewHolder(itemView)
class DetailLevel(itemView: View, val type: TextView, val level: TextView) : DetailViewHolder(itemView)
class DetailAdapter(private val data: ArrayList<Pair<String, Int>>, private val description: String) : RecyclerView.Adapter<DetailViewHolder>() {
    private val HEADER = 0
    private val LEVEL = 1
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailViewHolder {
        return when (viewType) {
            HEADER -> {
                val view = LayoutInflater.from(parent.context)
                        .inflate(R.layout.item_detail_header, parent, false)
                DetailHeader(view, view.tv_detail_description)
            }
            else -> {
                val view = LayoutInflater.from(parent.context)
                        .inflate(R.layout.item_detail_level, parent, false)
                DetailLevel(view, view.tv_detail_level_name, view.tv_detail_level)
            }

        }
    }

    override fun getItemCount(): Int {
        return data.size + 1
    }

    override fun onBindViewHolder(holder: DetailViewHolder, position: Int) {
        if (holder is DetailHeader) {
            holder.description.text = description
        } else if (holder is DetailLevel) {
            holder.type.text = "${data[position - 1].first}: "
            holder.level.text = data[position - 1].second.toString()
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == 0) {
            HEADER
        } else {
            LEVEL
        }
    }
}