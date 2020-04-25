package com.example.a27299.cats.home

import android.app.ActionBar
import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.example.a27299.cats.R
import com.example.a27299.cats.module.MyLiveData
import kotlinx.android.synthetic.main.item_home_pic.view.*

class MyViewHolder(itemView: View, val iv_pic: ImageView) : RecyclerView.ViewHolder(itemView)
class MyAdapter(val context:Context?) : RecyclerView.Adapter<MyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_home_pic,null)
        return MyViewHolder(view,view.iv_item_pic)
    }

    override fun getItemCount(): Int {
        return MyLiveData.mutableLiveData.value?.size ?: 0
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        MyLiveData.mutableLiveData.value?.let {
            context?.apply {
                Glide.with(this).load(it[position].url)
                        .fitCenter()
                        .into(holder.iv_pic)
            }
        }
    }

}