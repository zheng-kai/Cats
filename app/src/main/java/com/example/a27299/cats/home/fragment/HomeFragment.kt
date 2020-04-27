package com.example.a27299.cats.home.fragment

import android.support.v4.app.Fragment
import com.example.a27299.cats.home.fragment.pics.PicsFragment
import com.example.a27299.cats.home.fragment.species.SpeciesFragment

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