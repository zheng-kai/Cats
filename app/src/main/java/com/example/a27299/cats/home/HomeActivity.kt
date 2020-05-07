package com.example.a27299.cats.home

import android.arch.lifecycle.Observer
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.view.Gravity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.example.a27299.cats.R
import com.example.a27299.cats.home.fragment.HomeFragment
import com.example.a27299.cats.home.fragment.HomeFragmentPagerAdapter
import com.example.a27299.cats.home.fragment.choices.ChoicesFragment
import com.example.a27299.cats.home.fragment.pics.PicsFragment
import com.example.a27299.cats.login.LoginActivity
import com.example.a27299.cats.module.Category
import com.example.a27299.cats.module.Module
import com.example.a27299.cats.module.ServiceApi
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.activity_home_right.*
import kotlinx.android.synthetic.main.activity_home_right.view.*
import kotlinx.android.synthetic.main.navigation_head.view.*
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import pub.devrel.easypermissions.EasyPermissions
import q.rorbin.verticaltablayout.VerticalTabLayout
import q.rorbin.verticaltablayout.adapter.TabAdapter
import q.rorbin.verticaltablayout.widget.ITabView
import q.rorbin.verticaltablayout.widget.ITabView.TabTitle
import q.rorbin.verticaltablayout.widget.TabView


class HomeActivity : AppCompatActivity(), EasyPermissions.PermissionCallbacks, PermissionListener {
    private lateinit var verticalTabLayout: VerticalTabLayout
    private val tabTitleList = listOf("种类", "品种")
    private lateinit var tabFragmentList: List<ChoicesFragment>
    private lateinit var rightAdapter: SelectedAdapter
    private val PERMISSION_CODE = 111
    private lateinit var refreshableListener: Refreshable
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        initHome()
        initRight()
        initLeft()
    }

    private fun initLeft() {
        val headerView = nv_home_left.getHeaderView(0)
        nv_home_left.menu.getItem(0).setOnMenuItemClickListener {
            Toast.makeText(this, "待开发功能", Toast.LENGTH_SHORT).show()
            true
        }
        headerView.civ_home_left_avatar.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }

    private fun initRight() {

        val categories = Module.getCategories() ?: arrayListOf()

        tabFragmentList = listOf(
                ChoicesFragment.newInstance(categories),
                ChoicesFragment.newInstance(arrayListOf()))

        if (categories.isEmpty()) {
            GlobalScope.launch(Main) {
                val data = withContext(IO) {
                    val data = ServiceApi.service.getCategories().execute().body()?.also {
                        if (it != Module.getCategories()) {
                            Module.saveCategories(it)
                        }
                    }
                    data ?: arrayListOf()
                }
                tabFragmentList[0].setAdapterData(data)
            }
        }
        val transaction = supportFragmentManager.beginTransaction()
        transaction.add(layout_right.fl_home_right_choices.id, tabFragmentList[0])
        transaction.commit()

        rv_home_right_selected.layoutManager = GridLayoutManager(this, 2)
        rightAdapter = SelectedAdapter(this)
        rv_home_right_selected.adapter = rightAdapter
        Module.selectedLiveData.observe(this, Observer {
            rightAdapter.notifyDataSetChanged()
        })
        verticalTabLayout = layout_right.findViewById(R.id.vtl_home_right)
        verticalTabLayout.addOnTabSelectedListener(object : VerticalTabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabView?, position: Int) {

            }

            override fun onTabSelected(tab: TabView?, position: Int) {
                val t = supportFragmentManager.beginTransaction()
                t.replace(layout_right.fl_home_right_choices.id, tabFragmentList[position])
                t.commit()
            }

        })
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

        btn_home_right_confirm.setOnClickListener {
            GlobalScope.launch(Main) {
                refreshableListener.setRefreshing(true)
                withContext(IO) {
                    Module.getNewPics()
                }
                refreshableListener.setRefreshing(false)

            }
            drawer_home.closeDrawer(Gravity.RIGHT)
        }
    }

    private fun initHome() {
        iv_home_menu.setOnClickListener {
            drawer_home.openDrawer(Gravity.START)
        }
        iv_home_filter.setOnClickListener {
            drawer_home.openDrawer(Gravity.RIGHT)
        }
        val homeFragment = HomeFragment.newInstance(HomeFragment.PICS_TITLE) as PicsFragment
        refreshableListener = homeFragment
        vp_home.adapter = HomeFragmentPagerAdapter(supportFragmentManager,
                listOf(homeFragment, HomeFragment.newInstance(HomeFragment.SPECIES_TITLE)))
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


    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {
        Toast.makeText(this, "授权失败", Toast.LENGTH_SHORT).show()

    }

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {
        Toast.makeText(this, "授权成功", Toast.LENGTH_SHORT).show()
    }

    override fun requestPermission(description: String, permissions: Array<String>, block: () -> Unit) {
        if (EasyPermissions.hasPermissions(this, *permissions)) {
            block()
        } else {
            EasyPermissions.requestPermissions(this, description, PERMISSION_CODE, *permissions)
        }
    }
}