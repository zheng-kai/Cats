package com.example.a27299.cats.home

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.a27299.cats.R

abstract class HomeFragment : Fragment() {
    var title = ""

    companion object {
        const val SPECIES_TITLE = "类别"
        const val PICS_TITLE = "图片"
        fun newInstance(title: String): HomeFragment {
            when (title) {
                SPECIES_TITLE -> {
                    return SpeciesFragment().apply {
                        this.title = title
                    }
                }
                PICS_TITLE -> {
                    return PicsFragment().apply {
                        this.title = title

                    }
                }

            }
            return PicsFragment().apply {
                this.title = title

            }
        }
    }

}