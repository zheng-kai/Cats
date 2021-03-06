package com.example.a27299.cats.home.fragment.pics

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v7.widget.StaggeredGridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.a27299.cats.R
import com.example.a27299.cats.home.HomeActivity
import com.example.a27299.cats.home.Refreshable
import com.example.a27299.cats.home.fragment.HomeFragment
import com.example.a27299.cats.home.fragment.util.EndlessScrollListener
import com.example.a27299.cats.module.Module
import com.example.a27299.cats.module.PicsBean
import kotlinx.android.synthetic.main.fragment_home_pics.view.*
import kotlinx.android.synthetic.main.fragment_home_pics.view.rv_home_fragment_pics
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class PicsFragment : HomeFragment(), Refreshable {
    val PAGE_SIZE = 50
    var num = 0
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_home_pics, container, false)
        val adapter = PicsAdapter(context, activity as HomeActivity)
        val layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        view.rv_home_fragment_pics.apply {
            this.adapter = adapter
            addItemDecoration(MyItemDecoration(context, 10f, 2))
            this.layoutManager = layoutManager
        }
        Module.picsLiveData.observe(this, Observer<ArrayList<PicsBean>> {
            it?.let {
                if (it.size == 0) {
                    view.tv_home_fragment_loading_pics.visibility = View.VISIBLE
                } else {
                    view.tv_home_fragment_loading_pics.visibility = View.INVISIBLE
                }
                if (it.size > num) {
                    adapter.notifyItemRangeInserted(num, it.size - num)

                } else {
                    adapter.notifyDataSetChanged()

                }
                num = it.size
            }
        })
        view.srl_home_fragment_pics.setOnRefreshListener {
            GlobalScope.launch(IO) {
                Module.getNewPics()
                GlobalScope.launch(Main) {
                    view.srl_home_fragment_pics.isRefreshing = false
                }
            }
        }
        view.rv_home_fragment_pics.addOnScrollListener(object : EndlessScrollListener() {
            override fun loadMore() {
                GlobalScope.launch(IO) {
                    Module.getPics(limit = PAGE_SIZE)
                    enable()
                }
            }

        }
        )
        GlobalScope.launch(IO) {
            Module.getPics(limit = PAGE_SIZE)

        }

        return view
    }

    override fun setRefreshing(enable: Boolean) {
        view?.srl_home_fragment_pics?.isRefreshing = enable

    }
}

