package com.example.a27299.cats.home.fragment.species

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.HeaderViewListAdapter
import android.widget.TextView

open class DetailViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
class DetailHeader(itemView: View, val description: TextView) : DetailViewHolder(itemView)
class DetailLevel(itemView: View, val type: TextView, val level: TextView) : DetailViewHolder(itemView)
class DetailAdapter : RecyclerView.Adapter<DetailViewHolder>() {
    private val HEADER = 0
    private val LEVEL = 1
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailViewHolder {
        when (viewType) {
            HEADER -> {

            }
            else -> {

            }

        }
    }

    override fun getItemCount(): Int {

    }

    override fun onBindViewHolder(holder: DetailViewHolder, position: Int) {

    }

    override fun getItemViewType(position: Int): Int {
        return if (position == 0) {
            HEADER
        } else {
            LEVEL
        }
    }
}