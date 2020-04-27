package com.example.a27299.cats.home.fragment.pics

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.example.a27299.cats.R
import com.example.a27299.cats.module.Module
import kotlinx.android.synthetic.main.item_home_pic.view.*

class PicsViewHolder(itemView: View, val ivPic: ImageView) : RecyclerView.ViewHolder(itemView)
class PicsAdapter(val context: Context?) : RecyclerView.Adapter<PicsViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PicsViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_home_pic, null)
        return PicsViewHolder(view, view.iv_item_pic)
    }

    override fun getItemCount(): Int {
        return Module.mutableLiveData.value?.size ?: 0
    }
    override fun onBindViewHolder(holder: PicsViewHolder, position: Int) {

        //固定item大小 防止瀑布流重新计算导致item位置变化
        val screenWidth: Int = context?.resources?.displayMetrics?.widthPixels ?: 0
        var width = 0f
        var height = 0f
        Module.mutableLiveData.value?.get(position)?.apply {
            width = screenWidth / 2f
            height = width / this.width.toFloat() * this.height + 0.5f
        }
        val lp =  StaggeredGridLayoutManager.LayoutParams(width.toInt(),height.toInt())
        holder.itemView.layoutParams = lp

        holder.itemView.setOnClickListener {

        }

        Module.mutableLiveData.value?.let {
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