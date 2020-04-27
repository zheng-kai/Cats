package com.example.a27299.cats.home.fragment

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.example.a27299.cats.home.fragment.HomeFragment


class HomeFragmentPagerAdapter(fm: FragmentManager, private val fragments:List<HomeFragment>): FragmentPagerAdapter(fm) {
    override fun getItem(position: Int): Fragment = fragments[position]

    override fun getCount(): Int = fragments.size

    override fun getPageTitle(position: Int): CharSequence? = fragments[position].title

}