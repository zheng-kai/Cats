package com.example.a27299.cats.home

import android.app.ActionBar
import android.content.Context
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
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

        //固定item大小 防止瀑布流重新计算导致item位置变化
        val screenWidth: Int = context?.resources?.displayMetrics?.widthPixels ?: 0
        var width = 0f
        var height = 0f
        MyLiveData.mutableLiveData.value?.get(position)?.apply {
            width = screenWidth / 2f
            height = width / this.width.toFloat() * this.height + 0.5f
        }
        val lp =  StaggeredGridLayoutManager.LayoutParams(width.toInt(),height.toInt())
        holder.itemView.layoutParams = lp

        holder.itemView.setOnClickListener {
//            Toast.makeText(context, "${holder.itemView.width},${holder.itemView.height}", Toast.LENGTH_SHORT).show()
//            Log.d("iv_pic", "${holder.itemView.width},${holder.itemView.height}")
//            Log.d("iv_pic_data", "${MyLiveData.mutableLiveData.value?.get(position)?.width},${MyLiveData.mutableLiveData.value?.get(position)?.height}")
//            Log.d("iv_pic_cal", "$width,${(height+0.5f).toInt()}")
        }

        MyLiveData.mutableLiveData.value?.let {
            context?.apply {
                Glide.with(this)
                        .load(it[position].url)
                        .placeholder(R.drawable.loading)
//                        .override(width,height)
                        .into(holder.iv_pic)
            }
        }

    }

    private fun dip2px(context: Context?, dpValue: Float): Int {
        context?.apply {
            val scale = context.resources.displayMetrics.density;
            return (dpValue * scale + 0.5f).toInt();
        }
        return 0
    }
    private fun px2dip( context:Context?,  pxValue:Float) :Int{
        context?.apply {
            val scale = context.resources.displayMetrics.density;
            return (pxValue / scale + 0.5f).toInt();
        }
        return 0
    }
}