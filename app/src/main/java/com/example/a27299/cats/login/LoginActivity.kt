package com.example.a27299.cats.login

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.example.a27299.cats.R
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        iv_login.setOnClickListener {
            onBackPressed()
        }
        initUI()
        setClickListeners()

    }

    private fun initUI() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            tv_login.setTextColor(resources.getColor(R.color.colorPrimary, null))
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            tv_login.background = resources.getDrawable(R.drawable.btn_selected_background, null)
        }
    }

    private fun setClickListeners() {
        tv_login.setOnClickListener {
            til_login_confirm_pwd.visibility = View.INVISIBLE

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                tv_login.setTextColor(resources.getColor(R.color.colorPrimary, null))
                tv_register.setTextColor(Color.BLACK)
                tv_register.setBackgroundColor(resources.getColor(R.color.background, null))
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                it.background = resources.getDrawable(R.drawable.btn_selected_background, null)
            }
        }
        tv_register.setOnClickListener {
            til_login_confirm_pwd.visibility = View.VISIBLE
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                tv_login.setTextColor(Color.BLACK)
                tv_register.setTextColor(resources.getColor(R.color.colorPrimary, null))
                tv_login.setBackgroundColor(resources.getColor(R.color.background, null))
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                it.background = resources.getDrawable(R.drawable.btn_selected_background, null)
            }
        }
    }
}