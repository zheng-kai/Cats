package com.example.a27299.cats.home

import android.graphics.Color
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v7.app.AppCompatActivity
import android.view.Gravity
import android.view.View
import com.example.a27299.cats.R
import com.example.a27299.cats.home.fragment.HomeFragment
import com.example.a27299.cats.home.fragment.HomeFragmentPagerAdapter
import com.example.a27299.cats.module.Module
import kotlinx.android.synthetic.main.activity_home.*
import q.rorbin.verticaltablayout.VerticalTabLayout
import q.rorbin.verticaltablayout.adapter.TabAdapter
import q.rorbin.verticaltablayout.widget.ITabView
import q.rorbin.verticaltablayout.widget.ITabView.TabTitle


class HomeActivity : AppCompatActivity() {
    private lateinit var verticalTabLayout: VerticalTabLayout
    private val tabTitleList = listOf("图片种类","品种")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        verticalTabLayout = layout_right.findViewById(R.id.vtl_home_right)
        verticalTabLayout.setTabAdapter(object : TabAdapter {
            override fun getIcon(position: Int): ITabView.TabIcon? = null
            override fun getBadge(position: Int): ITabView.TabBadge? = null

            override fun getBackground(position: Int): Int {
                return R.drawable.tab_selected_background
            }

            override fun getTitle(position: Int): ITabView.TabTitle {
                return TabTitle.Builder()
                        .setContent(tabTitleList[position]) //设置数据   也有设置字体颜色的方法
                        .setTextColor(R.color.colorAccent, Color.BLACK)
                        .build()
            }

            override fun getCount(): Int = tabTitleList.size

        })
        iv_home_menu.setOnClickListener {
            drawer_home.openDrawer(Gravity.START)
        }
        iv_home_filter.setOnClickListener {
            drawer_home.openDrawer(Gravity.RIGHT)
        }
        vp_home.adapter = HomeFragmentPagerAdapter(supportFragmentManager,
                listOf(HomeFragment.newInstance(HomeFragment.PICS_TITLE), HomeFragment.newInstance(HomeFragment.SPECIES_TITLE)))
        tab_home.setupWithViewPager(vp_home)
        Module.init()
        tab_home.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab?) {
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabSelected(tab: TabLayout.Tab?) {
                tab?.let {
                    when (it.text) {
                        HomeFragment.PICS_TITLE -> {
                            iv_home_filter.visibility = View.VISIBLE
                        }
                        HomeFragment.SPECIES_TITLE -> {
                            iv_home_filter.visibility = View.INVISIBLE
                        }
                    }
                }

            }

        })
    }
}