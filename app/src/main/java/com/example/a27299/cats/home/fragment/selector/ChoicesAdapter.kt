package com.example.a27299.cats.home.fragment.selector

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import com.example.a27299.cats.R
import com.example.a27299.cats.module.Module
import kotlinx.android.synthetic.main.item_home_right_choice.view.*

class ChoicesViewHolder(itemView: View, val cb: CheckBox) : RecyclerView.ViewHolder(itemView)
class ChoicesAdapter(private val context: Context?, private val data: ArrayList<String>) : RecyclerView.Adapter<ChoicesViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChoicesViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_home_right_choice, parent, false)
        return ChoicesViewHolder(view, view.cb_item_home_right_choice)
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: ChoicesViewHolder, position: Int) {
        holder.cb.text = data[position]
        holder.cb.isChecked = Module.selectedLiveData.value?.contains(data[position]) ?: false
        holder.cb.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                Module.addSelected(holder.cb.text.toString())
            } else {
                Module.remove(holder.cb.text.toString())
            }
        }
    }

}