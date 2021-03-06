package com.example.a27299.cats.home.fragment.species

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.a27299.cats.R
import com.example.a27299.cats.home.fragment.HomeFragment
import com.example.a27299.cats.home.fragment.util.EndlessScrollListener
import com.example.a27299.cats.module.Module
import kotlinx.android.synthetic.main.fragment_home_species.view.*
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.concurrent.atomic.AtomicInteger

class SpeciesFragment : HomeFragment() {
    private var breedIdList = ArrayList<String>()
    private lateinit var adapter: SpeciesAdapter
    private var num: AtomicInteger = AtomicInteger(10)
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view = inflater.inflate(R.layout.fragment_home_species, container, false)
        view.rv_home_fragment_species.layoutManager = LinearLayoutManager(context).apply {
            orientation = LinearLayoutManager.VERTICAL
        }
        adapter = SpeciesAdapter(context)
        view.rv_home_fragment_species.adapter = adapter
        view.rv_home_fragment_species.addOnScrollListener(object : EndlessScrollListener() {
            override fun loadMore() {
                GlobalScope.launch(Main) {
                    adapter.setLoading("正在加载")

                    withContext(IO){
                        val n = num.get()
                        if (breedIdList.size > n + 10) {
                            num.addAndGet(10)
                            Module.addBreedDetail(breedIdList.subList(n, n + 10))
                            withContext(Main){
                                adapter.setLoading("加载更多")
                            }
                        } else if (breedIdList.size > n) {
                            num.addAndGet(breedIdList.size - n)
                            Module.addBreedDetail(breedIdList.subList(n, breedIdList.size))
                            withContext(Main){
                                adapter.setLoading("加载更多")
                            }
                        }else{
                            withContext(Main){
                                adapter.setLoading("已经到底了")
                            }
                        }
                        enable()
                    }
                }

            }

        })
        Module.speciesLiveData.observe(this, Observer {
            it?.let {
                if(it.size == 0){
                    view.tv_home_fragment_loading_species.visibility = View.VISIBLE
                }else{
                    view.tv_home_fragment_loading_species.visibility = View.INVISIBLE
                }
                val r = num.get().rem(10)
                if (r == 0) {
                    adapter.notifyItemRangeInserted(num.get() - 10, 10)

                } else {
                    adapter.notifyItemRangeInserted(num.get() - r, r)
                }
            }
        })
        GlobalScope.launch(IO) {
            val res = Module.getAllBreeds().execute().body() ?: ArrayList()
            breedIdList.addAll(res.map {
                it.id
            })
            Module.addBreedDetail(breedIdList.subList(0, 10))
        }

        return view
    }
}