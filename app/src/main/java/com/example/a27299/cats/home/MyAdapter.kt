package com.example.a27299.cats.home

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
class MyAdapter(val context: Context?) : RecyclerView.Adapter<MyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_home_pic, null)
//        val lp = view.layoutParams
//        lp.width = (context?.resources?.displayMetrics?.widthPixels ?: 0) / 2
//        view.layoutParams = lp
        return MyViewHolder(view, view.iv_item_pic)
    }

    override fun getItemCount(): Int {
        return MyLiveData.mutableLiveData.value?.size ?: 0
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val screenWidth: Int = context?.resources?.displayMetrics?.widthPixels ?: 0
        var width = 0
        var height = 0
        MyLiveData.mutableLiveData.value?.get(position)?.apply {
            width = screenWidth / 2
            height = this.width / width * this.height
        }
//        val lp = holder.iv_pic.layoutParams
//        lp.width = width
//        lp.height = height
//        holder.iv_pic.layoutParams = lp
        MyLiveData.mutableLiveData.value?.let {
            context?.apply {
                Glide.with(this)
                        .load(it[position].url)
//                        .override(width,height)
                        .into(holder.iv_pic)
            }
        }

    }
}