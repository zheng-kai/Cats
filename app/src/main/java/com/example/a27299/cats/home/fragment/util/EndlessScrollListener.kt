package com.example.a27299.cats.home.fragment.util

import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager

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