package com.example.a27299.cats.home

import android.arch.lifecycle.Observer
import android.content.Context
import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.a27299.cats.R
import com.example.a27299.cats.module.MyLiveData
import com.example.a27299.cats.module.PicsBean
import kotlinx.android.synthetic.main.fragment_home_pics.*
import kotlinx.android.synthetic.main.fragment_home_pics.view.*
import kotlinx.android.synthetic.main.fragment_home_pics.view.rv_home_fragment_pics
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PicsFragment : HomeFragment() {
    val PAGE_SIZE = 50
    var num = 0
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_home_pics, container, false)
        val adapter = MyAdapter(context)
        val layoutManager =  StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        view.rv_home_fragment_pics.apply {
            this.adapter = adapter
            addItemDecoration(MyItemDecoration(context,10f,2))
            this.layoutManager = layoutManager
        }
        MyLiveData.mutableLiveData.observe(this, Observer<ArrayList<PicsBean>> {
            it?.let {
                if(it.size>num){
                    adapter.notifyItemRangeInserted(num,it.size-num)

                }else {
                    adapter.notifyDataSetChanged()

                }
                num = it.size
            }
        })
        view.srl_home_fragment_pics.setOnRefreshListener {
            GlobalScope.launch {
                MyLiveData.clear()
                MyLiveData.getPics(limit = PAGE_SIZE)
                GlobalScope.launch(Main) {
                    view.srl_home_fragment_pics.isRefreshing = false
                }
            }
        }
        view.rv_home_fragment_pics.addOnScrollListener(object : EndlessScrollListener() {
            override fun loadMore() {
                GlobalScope.launch {
                    MyLiveData.getPics(limit = PAGE_SIZE)
                }
            }

        }
        )
        GlobalScope.launch {
            MyLiveData.getPics(limit = PAGE_SIZE)

        }

        return view
    }
}

abstract class EndlessScrollListener : RecyclerView.OnScrollListener() {
    private var slidingUp = false
    override fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int) {
        super.onScrollStateChanged(recyclerView, newState)
        val manager = recyclerView?.layoutManager as StaggeredGridLayoutManager
        if (newState == RecyclerView.SCROLL_STATE_IDLE) {
            val array = manager.findLastCompletelyVisibleItemPositions(null)
            val last = array.max()
            val count = manager.itemCount
            if (last == (count - 1) && slidingUp) {
                loadMore()
            }
        }

    }

    override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        slidingUp = dy > 0
    }

    abstract fun loadMore()
}