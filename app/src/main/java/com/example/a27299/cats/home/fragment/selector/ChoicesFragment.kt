package com.example.a27299.cats.home.fragment.selector

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.a27299.cats.R
import kotlinx.android.synthetic.main.fragment_home_right_choices.view.*


class ChoicesFragment: Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_home_right_choices,container,false)

        view.rv_home_right_choices.layoutManager = GridLayoutManager(context,3)
        view.rv_home_right_choices.adapter = ChoicesAdapter(context, listOf("aaa","bbb"))
        return view


    }
}