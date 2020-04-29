package com.example.a27299.cats.home

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.a27299.cats.R
import com.example.a27299.cats.module.Module
import kotlinx.android.synthetic.main.item_home_right_selected.view.*

class SelectedViewHolder(itemView: View, val tvType: TextView) : RecyclerView.ViewHolder(itemView)
class SelectedAdapter(private val context: Context) : RecyclerView.Adapter<SelectedViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SelectedViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_home_right_selected, parent, false)
        return SelectedViewHolder(view, view.tv_item_home_right_selected)
    }

    override fun getItemCount(): Int = Module.selectedLiveData.value?.size ?: 0

    override fun onBindViewHolder(holder: SelectedViewHolder, position: Int) {
        holder.tvType.text = Module.selectedLiveData.value?.get(position) ?: ""
    }

}