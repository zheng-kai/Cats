package com.example.a27299.cats.home.fragment.selector

import android.os.Bundle
import android.support.v4.app.BundleCompat
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.a27299.cats.R
import kotlinx.android.synthetic.main.fragment_home_right_choices.view.*
import java.util.*


class ChoicesFragment() : Fragment() {
    companion object {
        fun newInstance(data: Array<String>) = ChoicesFragment().apply {
            arguments = Bundle().apply {
                putStringArray("data", data)
                Log.d("MyData", data.contentToString())
            }
        }

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_home_right_choices, container, false)
        val data=arguments?.getStringArray("data")?: arrayOf()
        view.rv_home_right_choices.layoutManager = GridLayoutManager(context, 2)
        view.rv_home_right_choices.adapter = ChoicesAdapter(context, data)
        Log.d("MyData",data.contentToString())

        return view


    }
}