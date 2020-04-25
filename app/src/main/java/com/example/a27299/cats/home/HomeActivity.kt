package com.example.a27299.cats.home

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Gravity
import com.example.a27299.cats.R
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity (){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        iv_home_menu.setOnClickListener {
            drawer_home.openDrawer(Gravity.START)
        }
        vp_home.adapter = HomeFragmentPagerAdapter(supportFragmentManager,
                listOf(HomeFragment.newInstance(HomeFragment.SPECIES_TITLE),HomeFragment.newInstance(HomeFragment.PICS_TITLE)))
        tab_home.setupWithViewPager(vp_home)
    }
}