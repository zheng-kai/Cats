package com.example.a27299.cats.home

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.a27299.cats.R

class HomeFragment : Fragment() {
    var title = ""

    companion object {
        const val SPECIES_TITLE = "类别"
        const val CATS_TITLE = "图片"
        fun newInstance(title: String): HomeFragment = HomeFragment().apply {
            this.title = title
        }
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view :View? = null
        when(title){
            SPECIES_TITLE->{
               view = inflater.inflate(R.layout.fragment_home_species, container, false)
            }
            CATS_TITLE->{
                view = inflater.inflate(R.layout.fragment_home_pics, container, false)

            }
        }
        return view
    }
}