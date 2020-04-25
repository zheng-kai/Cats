package com.example.a27299.cats.home

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter


class HomeFragmentPagerAdapter(fm: FragmentManager, private val fragments:List<HomeFragment>): FragmentPagerAdapter(fm) {
    override fun getItem(position: Int): Fragment = fragments[position]

    override fun getCount(): Int = fragments.size

    override fun getPageTitle(position: Int): CharSequence? = fragments[position].title

}