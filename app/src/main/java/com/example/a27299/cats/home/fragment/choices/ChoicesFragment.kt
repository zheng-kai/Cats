package com.example.a27299.cats.home.fragment.choices

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.a27299.cats.R
import com.example.a27299.cats.module.Category
import kotlinx.android.synthetic.main.fragment_home_right_choices.view.*
import java.util.*


class ChoicesFragment() : Fragment() {
    private lateinit var adapter: ChoicesAdapter

    companion object {
        fun newInstance(data: ArrayList<Category>) = ChoicesFragment().apply {
            arguments = Bundle().apply {
                putParcelableArrayList("data", data)
            }
        }

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_home_right_choices, container, false)
        val data = arguments?.getParcelableArrayList<Category>("data") ?: arrayListOf()
        view.rv_home_right_choices.layoutManager = GridLayoutManager(context, 2)
        adapter = ChoicesAdapter(context, data)
        view.rv_home_right_choices.adapter = adapter

        return view
    }

    public fun setAdapterData(data: ArrayList<Category>) {
        adapter.setData(data)
        arguments = Bundle().apply {
            putParcelableArrayList("data", data)
        }
    }
}