package com.example.a27299.cats.home.fragment.species

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.a27299.cats.R
import com.example.a27299.cats.module.Module
import kotlinx.android.synthetic.main.item_home_pic.view.*
import kotlinx.android.synthetic.main.item_home_species.view.*

class SpeciesViewHolder(itemView: View, val ivPic: ImageView,val tvName:TextView) : RecyclerView.ViewHolder(itemView)
class SpeciesAdapter(val context: Context?) : RecyclerView.Adapter<SpeciesViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SpeciesViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_home_species, null)
        return SpeciesViewHolder(view, view.iv_item_pic, view.tv_item_species_name)
    }

    override fun getItemCount(): Int {
        return Module.speciesLiveData.value?.size ?: 0
    }
    override fun onBindViewHolder(holder: SpeciesViewHolder, position: Int) {

        Module.speciesLiveData.value?.let {
            context?.apply {
                Glide.with(this)
                        .load(it[position].url)
                        .placeholder(R.drawable.loading)
                        .into(holder.ivPic)
            }
        }

    }

//    private fun dip2px(context: Context?, dpValue: Float): Int {
//        context?.apply {
//            val scale = context.resources.displayMetrics.density;
//            return (dpValue * scale + 0.5f).toInt();
//        }
//        return 0
//    }
//    private fun px2dip( context:Context?,  pxValue:Float) :Int{
//        context?.apply {
//            val scale = context.resources.displayMetrics.density;
//            return (pxValue / scale + 0.5f).toInt();
//        }
//        return 0
//    }
}