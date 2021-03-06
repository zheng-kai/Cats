package com.example.a27299.cats.home.fragment.pics

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager
import android.view.*
import android.widget.ImageView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.a27299.cats.R
import com.example.a27299.cats.home.PermissionListener
import com.example.a27299.cats.module.Module
import kotlinx.android.synthetic.main.dialog_item_home_pic.view.*
import kotlinx.android.synthetic.main.item_home_pic.view.*

class PicsViewHolder(itemView: View, val ivPic: ImageView) : RecyclerView.ViewHolder(itemView)
class PicsAdapter(val context: Context?,private val listener:PermissionListener) : RecyclerView.Adapter<PicsViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PicsViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_home_pic, parent, false)
        return PicsViewHolder(view, view.iv_item_pic)
    }

    override fun getItemCount(): Int {
        return Module.picsLiveData.value?.size ?: 0
    }

    override fun onBindViewHolder(holder: PicsViewHolder, position: Int) {

        //固定item大小 防止瀑布流重新计算导致item位置变化
        val screenWidth: Int = context?.resources?.displayMetrics?.widthPixels ?: 0
        var width = 0f
        var height = 0f
        Module.picsLiveData.value?.get(position)?.apply {
            width = screenWidth / 2f
            height = width / this.width.toFloat() * this.height + 0.5f
        }
        val lp = StaggeredGridLayoutManager.LayoutParams(width.toInt(), height.toInt())
        holder.itemView.layoutParams = lp

        Module.picsLiveData.value?.let {
            context?.apply {
                val url = it[position].url
                val picId = it[position].id
                Glide.with(this)
                        .load(url)
                        .placeholder(R.drawable.loading)
                        .into(holder.ivPic)

                val dialogView = View.inflate(context, R.layout.dialog_item_home_pic, null)

                val d = AlertDialog.Builder(context)
                        .setView(dialogView)
                        .create()
                d.window?.setBackgroundDrawableResource(android.R.color.transparent)
                dialogView.iv_dialog_img.setOnClickListener {
                    d.dismiss()
                }

                dialogView.iv_dialog_download.setOnClickListener {
                    listener.requestPermission("需要文件读写权限", arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)){
                        val intent = Intent(context,DownloadService::class.java)
                        intent.putExtra("pic_url",url)
                        intent.putExtra("pic_id",picId)
                        startService(intent)
                        Toast.makeText(context,"开始下载",Toast.LENGTH_SHORT).show()

                    }
                }
                d.window?.attributes = d.window?.attributes.apply {
                    width = (context.resources.displayMetrics.widthPixels * 0.6).toFloat()
                    height = (context.resources.displayMetrics.heightPixels * 0.4).toFloat()
                }
                holder.itemView.setOnClickListener {
                    Glide.with(this).load(url)
                            .placeholder(R.drawable.loading)
                            .into(dialogView.iv_dialog_img)
                    d.show()

                }
            }

        }


    }

}