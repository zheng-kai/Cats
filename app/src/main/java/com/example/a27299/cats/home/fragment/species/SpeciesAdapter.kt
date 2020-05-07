package com.example.a27299.cats.home.fragment.species

import android.app.ActionBar
import android.content.Context
import android.content.Intent
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.a27299.cats.R
import com.example.a27299.cats.module.Module
import kotlinx.android.synthetic.main.item_footer.view.*
import kotlinx.android.synthetic.main.item_home_pic.view.*
import kotlinx.android.synthetic.main.item_home_species.view.*

open class BaseViewHolder(itemView: View):RecyclerView.ViewHolder(itemView)
class SpeciesViewHolder(itemView: View, val ivPic: ImageView, val tvName: TextView) : BaseViewHolder(itemView)
class FooterViewHolder(itemView: View,val content:TextView):BaseViewHolder(itemView)
class SpeciesAdapter(val context: Context?) : RecyclerView.Adapter<BaseViewHolder>() {
    private val FOOTER = 1
    private val HOLDER = 0
    private var footerContent = "加载更多"
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        if(viewType == 1){
            val view = LayoutInflater.from(context)
                    .inflate(R.layout.item_footer, parent, false)
            return FooterViewHolder(view,view.tv_footer)
        }
        val view = LayoutInflater.from(context)
                .inflate(R.layout.item_home_species, parent, false)
        return SpeciesViewHolder(view, view.iv_item_species_pic, view.tv_item_species_name)
    }

    override fun getItemCount(): Int {
        Module.speciesLiveData.value?.apply {
            if(size == 0){
                return 0
            }
            return size + 1
        }
        return 0
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {

        if(holder is SpeciesViewHolder){
            Module.speciesLiveData.value?.let {
                holder.tvName.text = it[position].breeds[0].name
                context?.apply {
                    Glide.with(this)
                            .load(it[position].url)
                            .placeholder(R.drawable.loading)
                            .into(holder.ivPic)
                }

            }
            holder.itemView.setOnClickListener {
                val intent = Intent(context, SpeciesDetail::class.java)
                intent.putExtra("position", position)
                context?.startActivity(intent)
            }
        }
        else if(holder is FooterViewHolder){
            holder.content.text = footerContent
        }

    }

    override fun getItemViewType(position: Int): Int {
        Module.speciesLiveData.value?.apply {
            return if(position == size ){
                FOOTER
            }else{
                HOLDER
            }
        }
        return HOLDER
    }
    public fun setLoading(content:String){
        footerContent = content
        notifyItemChanged(itemCount-1)
    }
}